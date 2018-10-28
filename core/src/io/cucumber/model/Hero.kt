package io.cucumber.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class Hero(
    x: Float,
    y: Float,
    private val height: Float,
    private val width: Float,
    horizontalVelocity: Float,
    verticalVelocity: Float
) {

    private val position: Vector2 = Vector2(x, y)
    private val velocity: Vector2 = Vector2(horizontalVelocity, verticalVelocity)

    val texture: Texture = Texture("hero.png")
    val bound: Rectangle get() = Rectangle(position.x, position.y, width, height)


    fun update(delta: Float) {
        position.add(0f, velocity.y * delta)
    }

    fun moveLeft() {
        position.add(velocity.x * -1, 0f)
    }

    fun moveRight() {
        position.add(velocity.x, 0f)
    }

    fun reverse() {
        velocity.y = velocity.y * -1
    }

}