package io.cucumber.model

import io.cucumber.model.base.Actor

class Bonus(
    x: Float,
    y: Float,
    size: Float,
    texture: String,
    val creationTime: Long
): Actor(x, y, size, 0F, 0F, texture)