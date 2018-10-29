package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import io.cucumber.constant.HeroConstants.HEIGHT
import io.cucumber.constant.HeroConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.HeroConstants.VERTICAL_VELOCITY
import io.cucumber.constant.HeroConstants.WIDTH
import io.cucumber.model.Hero

class GameScreen(
    game: Game
) : BaseScreen(game) {

    private val hero: Hero = Hero(
        camera.viewportWidth / 2,
        camera.viewportHeight / 2,
        HEIGHT,
        WIDTH,
        HORIZONTAL_VELOCITY,
        VERTICAL_VELOCITY
    )

    override fun update(delta: Float) {
        hero.update(delta)
    }

    override fun render() {
        batch.draw(
            hero.texture,
            hero.bound.x,
            hero.bound.y,
            hero.bound.width,
            hero.bound.height
        )
    }

    override fun handleInput() {
        if (hero.bound.y + hero.bound.height >= camera.position.y + (camera.viewportHeight / 2) || hero.bound.y <= 0) {
            hero.reverse()
        }

        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + hero.bound.width < camera.position.x + (camera.viewportWidth / 2)) hero.moveRight()
    }

}