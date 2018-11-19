package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import io.cucumber.constant.PreferenceConstants.IS_SOUND_ENABLED

class SettingScreen(
    game: Game
) : BaseScreen(game) {

    private var isSoundEnabled: Boolean = preferences.getBoolean(IS_SOUND_ENABLED)


    override fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.screen = StartScreen(game)
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            isSoundEnabled = !isSoundEnabled
            preferences.putBoolean(IS_SOUND_ENABLED, isSoundEnabled)
            preferences.flush()
        }
    }

}