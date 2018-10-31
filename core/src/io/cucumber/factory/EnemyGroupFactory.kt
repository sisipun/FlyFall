package io.cucumber.factory

import com.badlogic.gdx.utils.Array
import io.cucumber.constant.EnemyConstants
import io.cucumber.constant.ScreenConstants
import io.cucumber.factory.EnemyGroupFactory.GroupType.SIMPLE
import io.cucumber.model.Enemy
import io.cucumber.model.EnemyGroup

object EnemyGroupFactory {

    fun create(type: GroupType): EnemyGroup = when (type) {
        SIMPLE -> createSimple()
    }

    private fun createSimple(): EnemyGroup {
        val enemy = Enemy(ScreenConstants.WIDTH, ScreenConstants.HEIGHT / 2, EnemyConstants.WIDTH,
            EnemyConstants.HEIGHT, EnemyConstants.HORIZONTAL_VELOCITY)
        return EnemyGroup(Array.with(enemy))
    }

    enum class GroupType {
        SIMPLE
    }

}