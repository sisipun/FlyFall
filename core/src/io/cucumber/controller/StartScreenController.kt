package io.cucumber.controller

import io.cucumber.utils.InputHelper.MIN_FLING_DISTANCE
import io.cucumber.view.StartScreen

class StartScreenController(private val screen: StartScreen) : BaseController<StartScreen>(screen) {

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (velocityX > MIN_FLING_DISTANCE) {
            screen.setPreviousTextureLevel()
        }
        if (velocityX < -1 * MIN_FLING_DISTANCE){
            screen.setNextTextureLevel()
        }
        return super.fling(velocityX, velocityY, button)
    }

}