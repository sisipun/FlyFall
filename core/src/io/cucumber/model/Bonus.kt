package io.cucumber.model

import io.cucumber.model.base.Actor
import io.cucumber.utils.TimeUtils

class Bonus(
    x: Float,
    y: Float,
    size: Float,
    texture: String,
    var lifespan: Float
) : Actor(x, y, size, 0F, 0F, texture) {

    override fun update(delta: Float) {
        lifespan -= TimeUtils.secondsToMillis(delta)
    }

}