package io.cucumber.factory

import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.BonusConstants.BONUS_LIFESPAN
import io.cucumber.constant.BonusConstants.BONUS_SIZE
import io.cucumber.model.Bonus

object BonusFactory {

    fun create(x: Float, y: Float, texture: Texture): Bonus = Bonus(x, y, BONUS_SIZE, texture, BONUS_LIFESPAN)

}