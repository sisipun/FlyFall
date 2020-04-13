package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.button.ImageButton
import io.cucumber.model.button.SwitchImageButton
import io.cucumber.model.texture.LevelAssets
import io.cucumber.service.manager.LevelManager
import io.cucumber.utils.constant.GameConstants.*

class ChooseLevelScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private val isSoundOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Actors
    private var homeButton: ImageButton? = null
    private var chooseButton: SwitchImageButton? = null

    init {
        reloadButtons()
    }

    private fun reloadButtons() {
        homeButton?.remove()
        chooseButton?.remove()

        homeButton = ImageButton(
                SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
                HOME_BUTTON_WIDTH,
                HOME_BUTTON_HEIGHT,
                this.levelAssets.notButton
        )
        chooseButton = SwitchImageButton(
                SCREEN_WIDTH / 2 - CHOOSE_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
                CHOOSE_BUTTON_WIDTH,
                CHOOSE_BUTTON_HEIGHT,
                this.levelAssets.okButton,
                this.levelAssets.buyButton,
                this.levelAssets.isActive
        )

        homeButton?.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = StartScreen(
                        game,
                        bonusCount,
                        highScore,
                        isSoundOn,
                        levelAssets
                )
            }
        })
        chooseButton?.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (levelAssets.isActive) {
                    preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
                    preferences.flush()
                    game.screen = StartScreen(
                            game,
                            bonusCount,
                            highScore,
                            isSoundOn
                    )
                } else if (bonusCount >= levelAssets.cost) {
                    bonusCount -= levelAssets.cost
                    preferences.putInteger(BONUSES_COUNT, bonusCount)
                    preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
                    LevelManager.activate(levelAssets.id)
                    preferences.flush()
                    chooseButton?.setSwitcher(levelAssets.isActive)
                }
            }
        })
        addBackgroundListener(object : ActorGestureListener() {
            override fun fling(event: InputEvent?, velocityX: Float, velocityY: Float, button: Int) {
                if (velocityX > MIN_FLING_DISTANCE) {
                    changeTextureLevel(LevelManager.getPrevious(levelAssets))
                    chooseButton?.setSwitcher(levelAssets.isActive)
                    reloadButtons()
                }
                if (velocityX < -1 * MIN_FLING_DISTANCE) {
                    changeTextureLevel(LevelManager.getNext(levelAssets))
                    chooseButton?.setSwitcher(levelAssets.isActive)
                    reloadButtons()
                }
            }
        })

        addActors(Array.with(homeButton, chooseButton))
    }
}