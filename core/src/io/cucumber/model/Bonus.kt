package io.cucumber.model

import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.GameConstants.MILLIS_IN_SECOND
import io.cucumber.model.base.Actor

class Bonus(
    x: Float,
    y: Float,
    size: Float,
    texture: Texture,
    var lifespan: Float
) : Actor(x, y, size, 0F, 0F, texture) {

    override fun update(delta: Float) {
        lifespan -= delta * MILLIS_IN_SECOND
    }

}