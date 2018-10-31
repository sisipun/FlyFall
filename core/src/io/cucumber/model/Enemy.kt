package io.cucumber.model

import io.cucumber.model.base.Actor

class Enemy(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    horizontalVelocity: Float
): Actor(x, y, height, width, horizontalVelocity, 0F, "hero.png") {

    override fun update(delta: Float) {
        position.add(velocity.x * delta, velocity.y * delta)
    }

}