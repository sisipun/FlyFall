package io.cucumber.model.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

abstract class Actor(
    x: Float,
    y: Float,
    private val size: Float,
    horizontalVelocity: Float,
    verticalVelocity: Float,
    texturePath: String
) {

    protected val position: Vector2 = Vector2(x, y)
    protected val velocity: Vector2 = Vector2(horizontalVelocity, verticalVelocity)

    val texture: Texture = Texture(texturePath)
    val bound: Circle get() = Circle(position.x, position.y, size / 2)

    fun isCollides(bound: Circle): Boolean {
        return this.bound.overlaps(bound)
    }

    abstract fun update(delta: Float)

}