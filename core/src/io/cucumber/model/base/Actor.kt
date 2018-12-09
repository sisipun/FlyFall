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
    val texture: Texture
) {

    protected val position: Vector2 = Vector2(x, y)
    protected val velocity: Vector2 = Vector2(horizontalVelocity, verticalVelocity)

    val bound: Circle get() = Circle(position.x, position.y, size / 2)


    fun isCollides(actor: Actor): Boolean {
        return this.bound.overlaps(actor.bound)
    }

    open fun update(delta: Float) {}

    fun dispose() {
        texture.dispose()
    }

}