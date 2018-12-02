package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import io.cucumber.constant.ButtonConstants.SOUND_OFF_BUTTON_HEIGHT
import io.cucumber.constant.ButtonConstants.SOUND_OFF_BUTTON_WIDTH
import io.cucumber.constant.ButtonConstants.START_GAME_BUTTON_HEIGHT
import io.cucumber.constant.ButtonConstants.START_GAME_BUTTON_WIDTH
import io.cucumber.constant.PreferenceConstants.BONUSES_COUNT
import io.cucumber.constant.PreferenceConstants.HIGH_SCORE
import io.cucumber.constant.PreferenceConstants.IS_SOUND_ENABLED
import io.cucumber.constant.ScreenConstants.SCORE_HEIGHT
import io.cucumber.constant.ScreenConstants.SCORE_WIDTH
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.model.base.Button
import io.cucumber.model.texture.TextureLevel
import io.cucumber.model.texture.TextureLevelPack.COMMON
import io.cucumber.utils.NumbersHelper

class StartScreen(game: Game) : BaseScreen(game) {

    private var isSoundEnabled: Boolean = preferences.getBoolean(IS_SOUND_ENABLED)
    private var highScoreTextures: List<Texture> = NumbersHelper.getTextures(preferences.getInteger(HIGH_SCORE))
    private var bonusesCountTextures: List<Texture> = NumbersHelper.getTextures(preferences.getInteger(BONUSES_COUNT))

    private val startButton: Button = Button(
        SCREEN_WIDTH / 2 - START_GAME_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - START_GAME_BUTTON_HEIGHT / 2,
        START_GAME_BUTTON_WIDTH,
        START_GAME_BUTTON_HEIGHT,
        "wall.png"
    )
    private val soundOffButton: Button = Button(
        SCREEN_WIDTH / 2 - SOUND_OFF_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - 2 * SOUND_OFF_BUTTON_HEIGHT,
        SOUND_OFF_BUTTON_WIDTH,
        SOUND_OFF_BUTTON_HEIGHT,
        "wall.png"
    )

    private var textureLevel: TextureLevel = COMMON.value


    init {
        if (!preferences.contains(IS_SOUND_ENABLED)) {
            preferences.putBoolean(IS_SOUND_ENABLED, true)
            preferences.flush()
        }
    }

    override fun render() {
        batch.draw(
            startButton.texture,
            startButton.bound.x,
            startButton.bound.y,
            startButton.bound.width,
            startButton.bound.height
        )
        batch.draw(
            soundOffButton.texture,
            soundOffButton.bound.x,
            soundOffButton.bound.y,
            soundOffButton.bound.width,
            soundOffButton.bound.height
        )
        highScoreTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                SCREEN_WIDTH / 2 + (index - highScoreTextures.size / 2) * SCORE_WIDTH,
                SCREEN_HEIGHT - 2 * SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
        bonusesCountTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                SCREEN_WIDTH / 2 + (index - bonusesCountTextures.size / 2) * SCORE_WIDTH,
                SCREEN_HEIGHT - 4 * SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
    }

    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            val touchPosition = camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F))
            if (startButton.isTouched(touchPosition.x, touchPosition.y)) game.screen = GameScreen(game, textureLevel)
            if (soundOffButton.isTouched(touchPosition.x, touchPosition.y)) soundOff()
        }
    }

    override fun screenDispose() {
        highScoreTextures.forEach { it.dispose() }
        bonusesCountTextures.forEach { it.dispose() }
    }

    private fun soundOff() {
        isSoundEnabled = !isSoundEnabled
        preferences.putBoolean(IS_SOUND_ENABLED, isSoundEnabled)
        preferences.flush()
    }

}