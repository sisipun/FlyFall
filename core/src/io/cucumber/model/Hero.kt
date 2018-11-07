package io.cucumber.model

import io.cucumber.model.Hero.Direction.DOWN_DIRECTION
import io.cucumber.model.base.Actor

class Hero(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    horizontalVelocity: Float,
    verticalVelocity: Float,
    direction: Direction = DOWN_DIRECTION
) : Actor(x, y, height, width, horizontalVelocity, verticalVelocity, "hero.png") {

    var direction = direction
        set(value) {
            if (field != value) {
                velocity.y = velocity.y * -1
                field = value
            }
        }

    override fun update(delta: Float) {
        position.add(0f, velocity.y * delta)
    }

    fun moveLeft() {
        position.add(velocity.x * -1, 0f)
    }

    fun moveRight() {
        position.add(velocity.x, 0f)
    }

    enum class Direction {
        UP_DIRECTION,
        DOWN_DIRECTION
    }

}