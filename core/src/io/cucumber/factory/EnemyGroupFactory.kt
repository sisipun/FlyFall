package io.cucumber.factory

import com.badlogic.gdx.utils.Array
import io.cucumber.constant.EnemyConstants.ENEMY_SIZE
import io.cucumber.constant.EnemyConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.ScreenConstants.ENEMY_DISTANCE
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.factory.EnemyGroupFactory.GroupType.*
import io.cucumber.factory.EnemyGroupFactory.Position.LEFT_POSITION
import io.cucumber.factory.EnemyGroupFactory.Position.RIGHT_POSITION
import io.cucumber.model.Enemy
import io.cucumber.model.EnemyGroup

object EnemyGroupFactory {

    private const val HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2
    private const val HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2


    fun create(type: GroupType, position: Position): EnemyGroup {
        val positionFactor = getPositionFactor(position)
        return when (type) {
            SIMPLE_GROUP -> createSimple(positionFactor)
            LADDER_GROUP -> createLadder(positionFactor)
            SNAKE_GROUP -> createSnake(positionFactor)
        }
    }

    private fun createSimple(positionFactor: Int): EnemyGroup {
        val enemy = Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT, ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY)
        return EnemyGroup(Array.with(enemy))
    }

    private fun createLadder(positionFactor: Int): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ((3 * ENEMY_DISTANCE) / 2), ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH + positionFactor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT - ((ENEMY_DISTANCE) / 2), ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH + 2 * positionFactor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT + ((ENEMY_DISTANCE) / 2), ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH + 3 * positionFactor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT + ((3 * ENEMY_DISTANCE) / 2), ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        return EnemyGroup(enemies)
    }

    private fun createSnake(positionFactor: Int): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT, ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH + positionFactor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT, ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH + 2 * positionFactor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT, ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        enemies.add(Enemy(HALF_SCREEN_WIDTH + positionFactor * HALF_SCREEN_WIDTH + 3 * positionFactor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT, ENEMY_SIZE, positionFactor * HORIZONTAL_VELOCITY))
        return EnemyGroup(enemies)
    }

    private fun getPositionFactor(position: Position): Int = when(position) {
        LEFT_POSITION -> -1
        RIGHT_POSITION -> 1
    }

    enum class GroupType {
        SIMPLE_GROUP,
        LADDER_GROUP,
        SNAKE_GROUP
    }

    enum class Position {
        LEFT_POSITION,
        RIGHT_POSITION
    }

}