package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import io.cucumber.constant.ButtonConstants.SOUND_OFF_BUTTON_HEIGHT
import io.cucumber.constant.ButtonConstants.SOUND_OFF_BUTTON_WIDTH
import io.cucumber.constant.ButtonConstants.START_GAME_BUTTON_HEIGHT
import io.cucumber.constant.ButtonConstants.START_GAME_BUTTON_WIDTH
import io.cucumber.constant.PreferenceConstants.IS_SOUND_ENABLED
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.model.base.Button

class StartScreen(game: Game) : BaseScreen(game) {

    private var isSoundEnabled: Boolean = preferences.getBoolean(IS_SOUND_ENABLED)

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
    }

    override fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) game.screen = GameScreen(game)
        if (Gdx.input.justTouched() && startButton.isTouched(Gdx.input.x.toFloat(), SCREEN_HEIGHT - Gdx.input.y.toFloat())) game.screen = GameScreen(game)
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) soundOff()
        if (Gdx.input.justTouched() && soundOffButton.isTouched(Gdx.input.x.toFloat(), SCREEN_HEIGHT - Gdx.input.y.toFloat())) soundOff()
    }

    private fun soundOff() {
        isSoundEnabled = !isSoundEnabled
        preferences.putBoolean(IS_SOUND_ENABLED, isSoundEnabled)
        preferences.flush()
    }

}