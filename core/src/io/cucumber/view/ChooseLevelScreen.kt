package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.input.GestureDetector
import io.cucumber.controller.ChooseLevelScreenController
import io.cucumber.model.base.Button
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.manager.TextureLevelManager
import io.cucumber.utils.constant.GameConstants.*

class ChooseLevelScreen(game: Game, textureLevel: TextureLevel? = null) : BaseScreen(game) {

    private val controller: ChooseLevelScreenController = ChooseLevelScreenController(this)

    private var textureLevel: TextureLevel = textureLevel ?: TextureLevelManager.get(preferences.getInteger(TEXTURE_LEVEL))

    private val wallTexture: Texture = Texture("wall.png")

    private val homeButton: Button = Button(
        SCREEN_WIDTH / 2 - HOME_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
        HOME_BUTTON_WIDTH,
        HOME_BUTTON_HEIGHT,
        wallTexture
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
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game, textureLevel)
    }

    fun setNextTextureLevel() {
        textureLevel = TextureLevelManager.getNext(textureLevel.id)
        preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
        preferences.flush()
    }

    fun setPreviousTextureLevel() {
        textureLevel = TextureLevelManager.getPrevious(textureLevel.id)
        preferences.putInteger(TEXTURE_LEVEL, textureLevel.id)
        preferences.flush()
    }

    override fun screenDispose() {
        wallTexture.dispose()
    }
}