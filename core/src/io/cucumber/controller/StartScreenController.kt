package io.cucumber.controller

import com.badlogic.gdx.Gdx
import io.cucumber.utils.InputHelper.MIN_FLING_DISTANCE
import io.cucumber.view.StartScreen

class StartScreenController(private val screen: StartScreen) : BaseController<StartScreen>(screen) {

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        Gdx.app.log("1", "$velocityX - $velocityY")
        if (velocityX > MIN_FLING_DISTANCE) {
            Gdx.app.log("2", "pr")
            screen.setPreviousTextureLevel()
        }
        if (velocityX < -1 * MIN_FLING_DISTANCE){
            Gdx.app.log("2", "nx")
            screen.setNextTextureLevel()
        }
        return super.fling(velocityX, velocityY, button)
    }

}