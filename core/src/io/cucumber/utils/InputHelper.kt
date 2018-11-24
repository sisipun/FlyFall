package io.cucumber.utils

import com.badlogic.gdx.Gdx

object InputHelper {

    private const val MAX_POINTERS_COUNT = 20


    fun getLastTouchX(): Int {
        var x = 0
        for (i in 0 until MAX_POINTERS_COUNT) {
            if (Gdx.input.isTouched(i)) {
                x = Gdx.input.getX(i)
            }
        }
        return x
    }

    fun getLastTouchY(): Int {
        var y = 0
        for (i in 0 until MAX_POINTERS_COUNT) {
            if (Gdx.input.isTouched(i)) {
                y = Gdx.input.getY(i)
            }
        }
        return y
    }

}