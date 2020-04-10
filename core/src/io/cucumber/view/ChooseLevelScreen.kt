package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.input.GestureDetector
import io.cucumber.controller.ChooseLevelScreenController
import io.cucumber.model.base.Button
import io.cucumber.model.buttons.SwitchButton
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.manager.TextureLevelManager
import io.cucumber.utils.constant.GameConstants.*

class ChooseLevelScreen(
        game: Game,
        private var bonusesCount: Int,
        private var highScore: Int,
        private val isSoundEnabled: Boolean,
        private var textureLevel: TextureLevel
) : BaseScreen(game) {

    private val controller: ChooseLevelScreenController = ChooseLevelScreenController(this)

    private val homeButton: Button = Button(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            Texture("not_button.png")
    )
    private val chooseButton: SwitchButton = SwitchButton(
            SCREEN_WIDTH / 2 - CHOOSE_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            Texture("ok_button.png"),
            Texture("buy_button.png"),
            textureLevel.isActive
    )


    init {
        Gdx.input.inputProcessor = GestureDetector(controller)
    }

    override fun render() {
        batch.draw(
                textureLevel.background,
                0F,
                0F,
                SCREEN_WIDTH,
                SCREEN_HEIGHT
        )
        batch.draw(
                homeButton.texture,
                homeButton.bound.x,
                homeButton.bound.y,
                homeButton.bound.width,
                homeButton.bound.height
        )
        batch.draw(
                chooseButton.texture,
                chooseButton.bound.x,
                chooseButton.bound.y,
                chooseButton.bound.width,
                chooseButton.bound.height
        )
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game, bonusesCount, highScore, isSoundEnabled)
        if (Gdx.input.justTouched() && chooseButton.isTouched(getTouchPosition())) {
            if (textureLevel.isActive) {
                preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
                preferences.flush()
                game.screen = StartScreen(game, bonusesCount, highScore, isSoundEnabled)
            } else if (bonusesCount >= textureLevel.cost) {
                TextureLevelManager.activate(textureLevel.id)
                bonusesCount -= textureLevel.cost
                preferences.putInteger(BONUSES_COUNT, bonusesCount)
                preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
                preferences.flush()
                chooseButton.setSwitcher(textureLevel.isActive)
            }
        }
    }

    fun setNextTextureLevel() {
        textureLevel = TextureLevelManager.getNext(textureLevel)
        chooseButton.setSwitcher(textureLevel.isActive)
    }

    fun setPreviousTextureLevel() {
        textureLevel = TextureLevelManager.getPrevious(textureLevel)
        chooseButton.setSwitcher(textureLevel.isActive)
    }

    override fun screenDispose() {
        chooseButton.dispose()
        homeButton.dispose()
    }
}