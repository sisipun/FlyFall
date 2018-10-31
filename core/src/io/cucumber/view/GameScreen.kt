package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.utils.Array
import io.cucumber.constant.HeroConstants
import io.cucumber.constant.HeroConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.HeroConstants.VERTICAL_VELOCITY
import io.cucumber.constant.ScreenConstants
import io.cucumber.factory.EnemyGroupFactory
import io.cucumber.factory.EnemyGroupFactory.GroupType.SIMPLE
import io.cucumber.model.EnemyGroup
import io.cucumber.model.Hero

class GameScreen(
    game: Game
) : BaseScreen(game) {

    private val hero: Hero = Hero(
        ScreenConstants.WIDTH / 2,
        ScreenConstants.HEIGHT / 2,
        HeroConstants.WIDTH,
        HeroConstants.HEIGHT,
        HORIZONTAL_VELOCITY,
        VERTICAL_VELOCITY
    )
    private val enemies: Array<EnemyGroup> = Array.with(EnemyGroupFactory.create(SIMPLE))


    override fun update(delta: Float) {
        hero.update(delta)
        enemies.forEach { group -> group.update(delta) }
    }

    override fun render() {
        batch.draw(
            hero.texture,
            hero.bound.x,
            hero.bound.y,
            hero.bound.width,
            hero.bound.height
        )
        enemies.forEach { group -> group.enemies.forEach { enemy ->
            batch.draw(
                enemy.texture,
                enemy.bound.x,
                enemy.bound.y,
                enemy.bound.width,
                enemy.bound.height
            )
        }}
    }

    override fun handleInput() {
        if (hero.bound.y + hero.bound.height >= camera.position.y + (ScreenConstants.HEIGHT / 2) || hero.bound.y <= 0) {
            hero.reverse()
        }

        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + hero.bound.width < camera.position.x + (ScreenConstants.WIDTH / 2)) hero.moveRight()

        if (enemies.any { it.isCollides(hero.bound) }) {
            // TODO add death logic
            enemies.add(EnemyGroupFactory.create(SIMPLE))
        }
    }

}