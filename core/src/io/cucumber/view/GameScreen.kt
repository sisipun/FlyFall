package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import io.cucumber.constant.HeroConstants
import io.cucumber.constant.HeroConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.HeroConstants.VERTICAL_VELOCITY
import io.cucumber.constant.ScreenConstants
import io.cucumber.factory.EnemyGroupFactory
import io.cucumber.factory.EnemyGroupFactory.GroupType.SIMPLE
import io.cucumber.model.EnemyGroup
import io.cucumber.model.Hero
import java.util.*

class GameScreen(
    game: Game
) : BaseScreen(game) {

    private val hero: Hero = Hero(
        ScreenConstants.SCREEN_WIDTH / 2,
        ScreenConstants.SCREEN_HEIGHT / 2,
        HeroConstants.WIDTH,
        HeroConstants.HEIGHT,
        HORIZONTAL_VELOCITY,
        VERTICAL_VELOCITY
    )
    private var enemyGroup: EnemyGroup = EnemyGroupFactory.create(SIMPLE)


    override fun update(delta: Float) {
        hero.update(delta)
        enemyGroup.update(delta)
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
        if (hero.bound.y + hero.bound.height >= camera.position.y + (ScreenConstants.SCREEN_HEIGHT / 2)) {
            hero.direction = Hero.Direction.DOWN
        } else if (hero.bound.y <= 0) {
            hero.direction = Hero.Direction.UP

        }

        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + hero.bound.width < camera.position.x + (ScreenConstants.SCREEN_WIDTH / 2)) hero.moveRight()

        if (enemyGroup.isCollides(hero.bound)) {
            game.screen = GameOverScreen(game)
        }

        val first = enemyGroup.enemies.first()
        val last = enemyGroup.enemies.last()
        if ((first.bound.x > camera.position.x + ScreenConstants.ENEMY_RESPAWN_BORDER + (ScreenConstants.SCREEN_WIDTH / 2) ||
                first.bound.x + first.bound.width + ScreenConstants.ENEMY_RESPAWN_BORDER < 0) &&
            (last.bound.x > camera.position.x + ScreenConstants.ENEMY_RESPAWN_BORDER + (ScreenConstants.SCREEN_WIDTH / 2) ||
                last.bound.x + last.bound.width + ScreenConstants.ENEMY_RESPAWN_BORDER < 0)) {
            enemyGroup = EnemyGroupFactory.create(EnemyGroupFactory.GroupType.values()[Random().nextInt(EnemyGroupFactory.GroupType.values().size)])
        }
    }

}