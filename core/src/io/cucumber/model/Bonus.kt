package io.cucumber.model

import com.badlogic.gdx.graphics.Texture
import io.cucumber.model.base.Actor
import io.cucumber.utils.TimeUtils

class Bonus(
    x: Float,
    y: Float,
    size: Float,
    texture: Texture,
    var lifespan: Float
) : Actor(x, y, size, 0F, 0F, texture) {

    override fun update(delta: Float) {
        lifespan -= TimeUtils.secondsToMillis(delta)
    }

}