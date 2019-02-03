package io.cucumber.model

import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.GameConstants.DOWN_DIRECTION
import io.cucumber.model.base.Actor

class Hero(
    x: Float,
    y: Float,
    size: Float,
    horizontalVelocity: Float,
    verticalVelocity: Float,
    texture: Texture,
    direction: Byte = DOWN_DIRECTION
) : Actor(x, y, size, horizontalVelocity, verticalVelocity, texture) {

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

}