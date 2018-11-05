package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import io.cucumber.constant.PreferenceConstants
import io.cucumber.constant.PreferenceConstants.HIGH_SCORE

class GameOverScreen(game: Game, score: Int) : BaseScreen(game) {

    init {
        val highScore = preferences.getInteger(PreferenceConstants.HIGH_SCORE)
        if (score > highScore) {
            preferences.putInteger(HIGH_SCORE, score)
            preferences.flush()
        }
    }

    override fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) game.screen = GameScreen(game)
    }

}