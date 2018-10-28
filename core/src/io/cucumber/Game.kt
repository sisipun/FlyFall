package io.cucumber

import com.badlogic.gdx.Game
import io.cucumber.view.GameScreen

class Game : Game() {

    override fun create() {
        setScreen(GameScreen(this))
    }

}
