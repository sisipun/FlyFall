package io.cucumber.model

import io.cucumber.model.base.Actor

class Enemy(
    x: Float,
    y: Float,
    size: Float,
    horizontalVelocity: Float,
    private val orientation: Orientation
) : Actor(x, y, size, horizontalVelocity, 0F, "enemy.png") {

    override fun update(delta: Float) {
        position.add(velocity.x * -1 * orientation.factor * delta, 0F)
    }

    enum class Orientation(val factor: Int) {
        LEFT_ORIENTATION(-1),
        RIGHT_ORIENTATION(1)
    }

}