package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import io.cucumber.constant.BonusConstants.BONUS_SIZE
import io.cucumber.constant.HeroConstants.HERO_SIZE
import io.cucumber.constant.HeroConstants.HORIZONTAL_VELOCITY
import io.cucumber.constant.HeroConstants.VERTICAL_VELOCITY
import io.cucumber.constant.PreferenceConstants.BONUSES_COUNT
import io.cucumber.constant.ScoreConstants.SCORE_HEIGHT
import io.cucumber.constant.ScoreConstants.SCORE_WIDTH
import io.cucumber.constant.ScreenConstants.ENEMY_RESPAWN_BORDER
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.factory.BonusFactory
import io.cucumber.factory.EnemyGroupFactory
import io.cucumber.factory.EnemyGroupFactory.GroupType.SIMPLE_GROUP
import io.cucumber.factory.EnemyGroupFactory.Position.RIGHT_POSITION
import io.cucumber.model.Bonus
import io.cucumber.model.EnemyGroup
import io.cucumber.model.Hero
import io.cucumber.model.Hero.Direction.DOWN_DIRECTION
import io.cucumber.model.Hero.Direction.UP_DIRECTION
import io.cucumber.utils.ScoreHelper
import java.util.*

class GameScreen(
    game: Game
) : BaseScreen(game) {

    private val random = Random()

    private var score: Int = 0
    private var bonusesCount: Int = preferences.getInteger(BONUSES_COUNT)
    private val hero: Hero = Hero(
        SCREEN_WIDTH / 2,
        SCREEN_HEIGHT / 2,
        HERO_SIZE,
        HORIZONTAL_VELOCITY,
        VERTICAL_VELOCITY
    )
    private var enemyGroup: EnemyGroup = EnemyGroupFactory.create(SIMPLE_GROUP, RIGHT_POSITION)
    private var bonus: Bonus = BonusFactory.create(random.nextInt((SCREEN_WIDTH - 2 * BONUS_SIZE).toInt()) + BONUS_SIZE,
        random.nextInt((SCREEN_HEIGHT - 2 * BONUS_SIZE).toInt()) + BONUS_SIZE)


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
            2 * hero.bound.radius,
            2 * hero.bound.radius
        )
        batch.draw(
            bonus.texture,
            bonus.bound.x,
            bonus.bound.y,
            2 * bonus.bound.radius,
            2 * bonus.bound.radius
        )
        enemyGroup.enemies.forEach {
            batch.draw(
                it.texture,
                it.bound.x,
                it.bound.y,
                2 * it.bound.radius,
                2 * it.bound.radius
            )
        }
        val scoreTextures = ScoreHelper.getScore(score)
        scoreTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                (index + 1) * SCORE_WIDTH,
                SCREEN_HEIGHT - 2 * SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
    }

    override fun handleInput() {
        if (hero.bound.y + 2 * hero.bound.radius >= camera.position.y + (SCREEN_HEIGHT / 2)) {
            hero.direction = DOWN_DIRECTION
        } else if (hero.bound.y <= 0) {
            hero.direction = UP_DIRECTION
        }

        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + 2 * hero.bound.radius < camera.position.x + (SCREEN_WIDTH / 2)) hero.moveRight()

        if (bonus.isCollides(hero)) {
            bonusesCount++
            bonus = BonusFactory.create(random.nextInt((SCREEN_WIDTH - 2 * BONUS_SIZE).toInt()) + BONUS_SIZE,
                random.nextInt((SCREEN_HEIGHT - 2 * BONUS_SIZE).toInt()) + BONUS_SIZE)
        }
        if (enemyGroup.isCollides(hero)) {
            game.screen = GameOverScreen(game, score, bonusesCount)
        }

        val first = enemyGroup.enemies.first()
        val last = enemyGroup.enemies.last()
        if ((first.bound.x > camera.position.x + ENEMY_RESPAWN_BORDER + (SCREEN_WIDTH / 2) ||
                first.bound.x + first.bound.radius + ENEMY_RESPAWN_BORDER < 0) &&
            (last.bound.x > camera.position.x + ENEMY_RESPAWN_BORDER + (SCREEN_WIDTH / 2) ||
                last.bound.x + last.bound.radius + ENEMY_RESPAWN_BORDER < 0)) {
            enemyGroup = EnemyGroupFactory.create(
                EnemyGroupFactory.GroupType.values()[random.nextInt(EnemyGroupFactory.GroupType.values().size)],
                EnemyGroupFactory.Position.values()[random.nextInt(EnemyGroupFactory.Position.values().size)]
            )
        }
    }

}