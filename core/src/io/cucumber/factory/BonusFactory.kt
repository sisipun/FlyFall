package io.cucumber.factory

import io.cucumber.constant.BonusConstants.BONUS_SIZE
import io.cucumber.model.Bonus

object BonusFactory {

    fun create(x: Float, y: Float, texture: String): Bonus = Bonus(x, y, BONUS_SIZE, texture, System.currentTimeMillis())

}