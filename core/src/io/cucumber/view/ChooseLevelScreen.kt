package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.utils.Array
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
        textureLevel: TextureLevel
) : BaseScreen(game, textureLevel) {

    private val controller: ChooseLevelScreenController = ChooseLevelScreenController(this)

    private var homeButton: Button = Button(
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
        Gdx.input.inputProcessor = GestureDetector(controller)
        addActors(Array.with(homeButton, chooseButton))
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

    private fun initButtons() {
        homeButton.remove()
        chooseButton.remove()
        homeButton = Button(
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
        addActors(Array.with(homeButton, chooseButton))
    }

    fun setNextTextureLevel() {
        changeTextureLevel(TextureLevelManager.getNext(textureLevel))
        chooseButton.setSwitcher(textureLevel.isActive)
        initButtons()
    }

    fun setPreviousTextureLevel() {
        changeTextureLevel(TextureLevelManager.getPrevious(textureLevel))
        chooseButton.setSwitcher(textureLevel.isActive)
        initButtons()
    }
}