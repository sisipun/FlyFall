package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import io.cucumber.constant.PreferenceConstants.IS_SOUND_ENABLED

class StartScreen(game: Game) : BaseScreen(game) {

    init {
        if (!preferences.contains(IS_SOUND_ENABLED)) {
            preferences.putBoolean(IS_SOUND_ENABLED, true)
            preferences.flush()
        }
    }

    override fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) game.screen = GameScreen(game)
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) game.screen = SettingScreen(game)
    }

}