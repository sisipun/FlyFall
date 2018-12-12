package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.input.GestureDetector
import io.cucumber.constant.ButtonConstants.HOME_BUTTON_HEIGHT
import io.cucumber.constant.ButtonConstants.HOME_BUTTON_WIDTH
import io.cucumber.constant.PreferenceConstants.TEXTURE_LEVEL
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.controller.ChooseLevelScreenController
import io.cucumber.model.base.Button
import io.cucumber.model.texture.TextureLevelPack

class ChooseLevelScreen(
    game: Game
) : BaseScreen(game) {

    private val controller: ChooseLevelScreenController = ChooseLevelScreenController(this)

    private var textureLevel: TextureLevelPack = TextureLevelPack.getById(preferences.getInteger(TEXTURE_LEVEL))

    private val homeButton: Button = Button(
        SCREEN_WIDTH / 2 - HOME_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
        HOME_BUTTON_WIDTH,
        HOME_BUTTON_HEIGHT,
        "wall.png"
    )


    init {
        Gdx.input.inputProcessor = GestureDetector(controller)
    }

    override fun render() {
        batch.draw(
            textureLevel.value.background,
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
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game)
    }

    fun setNextTextureLevel() {
        textureLevel = TextureLevelPack.getById(textureLevel.next)
        preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
        preferences.flush()
    }

    fun setPreviousTextureLevel() {
        textureLevel = TextureLevelPack.getById(textureLevel.previous)
        preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
        preferences.flush()
    }

}