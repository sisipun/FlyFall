package io.cucumber.model

import com.badlogic.gdx.utils.Array
import io.cucumber.model.base.Actor

class EnemyGroup(
    val enemies: Array<Enemy>
) {

    fun isCollides(actor: Actor): Boolean {
        return enemies.any { it.isCollides(actor) }
    }

    fun update(delta: Float) {
        enemies.forEach { it.update(delta) }
    }

    fun dispose() {
        enemies.forEach { it.dispose() }
    }

}