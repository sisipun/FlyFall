package io.cucumber

import com.badlogic.gdx.Game
import io.cucumber.view.StartScreen

class Game : Game() {

    override fun create() {
        setScreen(StartScreen(this))
    }

}
