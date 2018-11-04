package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import io.cucumber.constant.HeroConstants.HERO_HEIGHT
import io.cucumber.constant.HeroConstants.HERO_WIDTH
import io.cucumber.constant.HeroConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.HeroConstants.VERTICAL_VELOCITY
import io.cucumber.constant.ScreenConstants.ENEMY_RESPAWN_BORDER
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.factory.EnemyGroupFactory
import io.cucumber.factory.EnemyGroupFactory.GroupType.SIMPLE
import io.cucumber.model.EnemyGroup
import io.cucumber.model.Hero
import java.util.*

class GameScreen(
    game: Game
) : BaseScreen(game) {

    private val hero: Hero = Hero(
        SCREEN_WIDTH / 2,
        SCREEN_HEIGHT / 2,
        HERO_WIDTH,
        HERO_HEIGHT,
        HORIZONTAL_VELOCITY,
        VERTICAL_VELOCITY
    )
    private var enemyGroup: EnemyGroup = EnemyGroupFactory.create(SIMPLE)
    private var score: Int = 0


    override fun update(delta: Float) {
        hero.update(delta)
        enemyGroup.update(delta)
        score += (delta * 1000).toInt()
    }

    override fun render() {
        batch.draw(
            hero.texture,
            hero.bound.x,
            hero.bound.y,
            hero.bound.width,
            hero.bound.height
        )
        enemyGroup.enemies.forEach {
            batch.draw(
                it.texture,
                it.bound.x,
                it.bound.y,
                it.bound.width,
                it.bound.height
            )
        }
    }

    override fun handleInput() {
        if (hero.bound.y + hero.bound.height >= camera.position.y + (SCREEN_HEIGHT / 2)) {
            hero.direction = Hero.Direction.DOWN
        } else if (hero.bound.y <= 0) {
            hero.direction = Hero.Direction.UP
        }

        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + hero.bound.width < camera.position.x + (SCREEN_WIDTH / 2)) hero.moveRight()

        if (enemyGroup.isCollides(hero.bound)) {
            game.screen = GameOverScreen(game)
        }

        val first = enemyGroup.enemies.first()
        val last = enemyGroup.enemies.last()
        if ((first.bound.x > camera.position.x + ENEMY_RESPAWN_BORDER + (SCREEN_WIDTH / 2) ||
                first.bound.x + first.bound.width + ENEMY_RESPAWN_BORDER < 0) &&
            (last.bound.x > camera.position.x + ENEMY_RESPAWN_BORDER + (SCREEN_WIDTH / 2) ||
                last.bound.x + last.bound.width + ENEMY_RESPAWN_BORDER < 0)) {
            enemyGroup = EnemyGroupFactory.create(EnemyGroupFactory.GroupType.values()[Random().nextInt(EnemyGroupFactory.GroupType.values().size)])
        }
    }

}