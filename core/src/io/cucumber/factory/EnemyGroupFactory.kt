package io.cucumber.factory

import com.badlogic.gdx.utils.Array
import io.cucumber.constant.EnemyConstants.ENEMY_HEIGHT
import io.cucumber.constant.EnemyConstants.ENEMY_WIDTH
import io.cucumber.constant.EnemyConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.ScreenConstants.ENEMY_DISTANCE
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.factory.EnemyGroupFactory.GroupType.*
import io.cucumber.model.Enemy
import io.cucumber.model.EnemyGroup

object EnemyGroupFactory {

    fun create(type: GroupType): EnemyGroup = when (type) {
        SIMPLE -> createSimple()
        LADDER -> createLadder()
        SNAKE -> createSnake()
    }

    private fun createSimple(): EnemyGroup {
        val enemy = Enemy(SCREEN_WIDTH, SCREEN_HEIGHT / 2, ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY)
        return EnemyGroup(Array.with(enemy))
    }

    private fun createLadder(): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(SCREEN_WIDTH, SCREEN_HEIGHT / 2 - ((3 * ENEMY_DISTANCE) / 2), ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        enemies.add(Enemy(SCREEN_WIDTH + ENEMY_DISTANCE, SCREEN_HEIGHT / 2 - ((ENEMY_DISTANCE) / 2), ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        enemies.add(Enemy(SCREEN_WIDTH + 2 * ENEMY_DISTANCE, SCREEN_HEIGHT / 2 + ((ENEMY_DISTANCE) / 2), ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        enemies.add(Enemy(SCREEN_WIDTH + 3 * ENEMY_DISTANCE, SCREEN_HEIGHT / 2 + ((3 * ENEMY_DISTANCE) / 2), ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        return EnemyGroup(enemies)
    }

    private fun createSnake(): EnemyGroup {
        val enemies = Array.of(Enemy::class.java)
        enemies.add(Enemy(SCREEN_WIDTH, SCREEN_HEIGHT / 2, ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        enemies.add(Enemy(SCREEN_WIDTH + ENEMY_DISTANCE, SCREEN_HEIGHT / 2, ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        enemies.add(Enemy(SCREEN_WIDTH + 2 * ENEMY_DISTANCE, SCREEN_HEIGHT / 2, ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        enemies.add(Enemy(SCREEN_WIDTH + 3 * ENEMY_DISTANCE, SCREEN_HEIGHT / 2, ENEMY_WIDTH, ENEMY_HEIGHT, HORIZONTAL_VELOCITY))
        return EnemyGroup(enemies)
    }

    enum class GroupType {
        SIMPLE,
        LADDER,
        SNAKE
    }

}