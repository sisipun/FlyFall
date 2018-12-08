package io.cucumber.controller

import io.cucumber.view.StartScreen

class StartScreenController(private val screen: StartScreen) : BaseController<StartScreen>(screen) {

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (velocityX > 0) {
            screen.setPreviousTextureLevel()
        } else {
            screen.setNextTextureLevel()
        }
        return super.fling(velocityX, velocityY, button)
    }

}