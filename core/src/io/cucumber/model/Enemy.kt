package io.cucumber.model

import com.badlogic.gdx.graphics.Texture
import io.cucumber.model.base.Actor

class Enemy(
    x: Float,
    y: Float,
    size: Float,
    horizontalVelocity: Float,
    texture: Texture,
    private val orientation: Byte
) : Actor(x, y, size, horizontalVelocity, 0F, texture) {

    override fun update(delta: Float) {
        position.add(velocity.x * -1 * orientation * delta, 0F)
    }

}