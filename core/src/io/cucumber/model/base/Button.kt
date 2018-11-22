package io.cucumber.model.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

class Button(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    texturePath: String
) {

    val texture: Texture = Texture(texturePath)
    val bound: Rectangle = Rectangle(x, y, width, height)


    fun isTouched(x: Float, y: Float): Boolean {
        return bound.contains(x, y)
    }

}