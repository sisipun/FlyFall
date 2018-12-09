package io.cucumber.factory

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Array
import io.cucumber.constant.EnemyConstants.ENEMY_SIZE
import io.cucumber.constant.ScreenConstants.ENEMY_DISTANCE
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.factory.EnemyGroupFactory.GroupType.*
import io.cucumber.model.Enemy
import io.cucumber.model.EnemyGroup

object EnemyGroupFactory {

    private const val HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2
    private const val HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2


    fun create(type: GroupType, orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        return when (type) {
            SMALL_SNAKE_GROUP -> createSmallSnake(orientation, horizontalSpeed, texture)
            LADDER_GROUP -> createLadder(orientation, horizontalSpeed, texture)
            SNAKE_GROUP -> createSnake(orientation, horizontalSpeed, texture)
            LADDER_SNAKE_GROUP -> createLadderSnake(orientation, horizontalSpeed, texture)
            SMALL_WALL_GROUP -> createSmallWall(orientation, horizontalSpeed, texture)
            WALL_GROUP -> createWall(orientation, horizontalSpeed, texture)
        }
    }

    private fun createSmallSnake(orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH + orientation.factor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        return EnemyGroup(enemies)
    }

    private fun createLadder(orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_DISTANCE - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH + orientation.factor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT - ((ENEMY_DISTANCE) / 3) - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH + 2 * orientation.factor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT + ((ENEMY_DISTANCE) / 3) - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH + 3 * orientation.factor * ENEMY_DISTANCE,
            HALF_SCREEN_HEIGHT + ENEMY_DISTANCE - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        return EnemyGroup(enemies)
    }

    private fun createSnake(orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH +
            orientation.factor * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH +
            2 * orientation.factor * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH +
            3 * orientation.factor * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        return EnemyGroup(enemies)
    }

    private fun createLadderSnake(orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + ENEMY_DISTANCE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH +
            orientation.factor * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - ENEMY_DISTANCE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH +
            2 * orientation.factor * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + ENEMY_DISTANCE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH +
            3 * orientation.factor * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - ENEMY_DISTANCE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        return EnemyGroup(enemies)
    }

    private fun createSmallWall(orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        return EnemyGroup(enemies)
    }

    private fun createWall(orientation: Enemy.Orientation, horizontalSpeed: Float, texture: Texture): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + 2 * HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        enemies.add(Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation.factor * HALF_SCREEN_WIDTH,
            HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - 2 * HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, horizontalSpeed, texture, orientation))
        return EnemyGroup(enemies)
    }

    enum class GroupType {
        SMALL_SNAKE_GROUP,
        LADDER_GROUP,
        SNAKE_GROUP,
        LADDER_SNAKE_GROUP,
        SMALL_WALL_GROUP,
        WALL_GROUP
    }

}