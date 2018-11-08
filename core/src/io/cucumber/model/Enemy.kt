package io.cucumber.model

import io.cucumber.model.base.Actor

class Enemy(
    x: Float,
    y: Float,
    size: Float,
    horizontalVelocity: Float
): Actor(x, y, size, horizontalVelocity, 0F, "hero.png") {

    override fun update(delta: Float) {
        position.add(velocity.x * delta, velocity.y * delta)
    }

}