package io.cucumber.model

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array

class EnemyGroup(
    val enemies: Array<Enemy>
) {

    fun isCollides(bound: Rectangle): Boolean {
        return enemies.any { it.isCollides(bound) }
    }

    fun update(delta: Float) {
        enemies.forEach { it.update(delta) }
    }

}