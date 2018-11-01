package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class GameOverScreen(game: Game) : BaseScreen(game) {

    override fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) game.screen = GameScreen(game)
    }

}