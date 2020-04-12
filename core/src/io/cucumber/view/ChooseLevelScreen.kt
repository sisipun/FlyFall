package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.buttons.ImageButton
import io.cucumber.model.buttons.SwitchButton
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.manager.TextureLevelManager
import io.cucumber.utils.constant.GameConstants.*

class ChooseLevelScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private val isSoundOn: Boolean,
        textureLevel: TextureLevel
) : BaseScreen(game, textureLevel) {

    private var homeButton: Button = ImageButton(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.textureLevel.notButton
    )
    private var chooseButton: SwitchButton = SwitchButton(
            SCREEN_WIDTH / 2 - CHOOSE_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            this.textureLevel.okButton,
            this.textureLevel.buyButton,
            textureLevel.isActive
    )

    init {
        initButtons()
    }

    private fun initButtons() {
        homeButton.remove()
        chooseButton.remove()

        homeButton = ImageButton(
                SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
                HOME_BUTTON_WIDTH,
                HOME_BUTTON_HEIGHT,
                textureLevel.notButton
        )
        chooseButton = SwitchButton(
                SCREEN_WIDTH / 2 - CHOOSE_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
                CHOOSE_BUTTON_WIDTH,
                CHOOSE_BUTTON_HEIGHT,
                textureLevel.okButton,
                textureLevel.buyButton,
                textureLevel.isActive
        )

        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = StartScreen(this@ChooseLevelScreen.game, this@ChooseLevelScreen.bonusCount, this@ChooseLevelScreen.highScore,
                        this@ChooseLevelScreen.isSoundOn, this@ChooseLevelScreen.textureLevel)
            }
        })
        chooseButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (textureLevel.isActive) {
                    preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
                    preferences.flush()
                    game.screen = StartScreen(game, bonusCount, highScore, isSoundOn)
                } else if (bonusCount >= textureLevel.cost) {
                    TextureLevelManager.activate(textureLevel.id)
                    bonusCount -= textureLevel.cost
                    preferences.putInteger(BONUSES_COUNT, bonusCount)
                    preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
                    preferences.flush()
                    chooseButton.setSwitcher(textureLevel.isActive)
                }
            }
        })
        addBackgroundListener(object : ActorGestureListener() {
            override fun fling(event: InputEvent?, velocityX: Float, velocityY: Float, button: Int) {
                if (velocityX > MIN_FLING_DISTANCE) {
                    changeTextureLevel(TextureLevelManager.getPrevious(textureLevel))
                    chooseButton.setSwitcher(textureLevel.isActive)
                    initButtons()
                }
                if (velocityX < -1 * MIN_FLING_DISTANCE) {
                    changeTextureLevel(TextureLevelManager.getNext(textureLevel))
                    chooseButton.setSwitcher(textureLevel.isActive)
                    initButtons()
                }
            }
        })

        addActors(Array.with(homeButton, chooseButton))
    }
}