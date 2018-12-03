package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.BonusConstants.BONUS_CHANCE
import io.cucumber.constant.BonusConstants.BONUS_LIFESPAN
import io.cucumber.constant.BonusConstants.BONUS_SIZE
import io.cucumber.constant.EnemyConstants.ENEMY_MAX_HORIZONTAL_VELOCITY
import io.cucumber.constant.EnemyConstants.ENEMY_MIN_HORIZONTAL_VELOCITY
import io.cucumber.constant.EnemyConstants.ENEMY_VELOCITY_DELTA
import io.cucumber.constant.HeroConstants.HERO_HORIZONTAL_VELOCITY
import io.cucumber.constant.HeroConstants.HERO_SIZE
import io.cucumber.constant.HeroConstants.HERO_VERTICAL_VELOCITY
import io.cucumber.constant.PreferenceConstants.BONUSES_COUNT
import io.cucumber.constant.PreferenceConstants.IS_SOUND_ENABLED
import io.cucumber.constant.ScreenConstants.ENEMY_RESPAWN_BORDER
import io.cucumber.constant.ScreenConstants.SCORE_HEIGHT
import io.cucumber.constant.ScreenConstants.SCORE_WIDTH
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.constant.ScreenConstants.TIMER_HEIGHT
import io.cucumber.constant.ScreenConstants.TIMER_MARGIN_WIDTH
import io.cucumber.constant.ScreenConstants.WALL_HEIGHT
import io.cucumber.factory.BonusFactory
import io.cucumber.factory.EnemyGroupFactory
import io.cucumber.model.Bonus
import io.cucumber.model.Enemy
import io.cucumber.model.EnemyGroup
import io.cucumber.model.Hero
import io.cucumber.model.Hero.Direction.DOWN_DIRECTION
import io.cucumber.model.Hero.Direction.UP_DIRECTION
import io.cucumber.model.texture.TextureLevel
import io.cucumber.utils.InputHelper
import io.cucumber.utils.NumbersHelper
import java.util.*

class GameScreen(
    game: Game,
    private val textureLevel: TextureLevel
) : BaseScreen(game) {

    private val random = Random()

    private var score: Int = 0
    private var bonusesCount: Int = preferences.getInteger(BONUSES_COUNT)
    private var enemyVelocity: Float = ENEMY_MIN_HORIZONTAL_VELOCITY

    private val hero: Hero = Hero(
        SCREEN_WIDTH / 2,
        SCREEN_HEIGHT / 2,
        HERO_SIZE,
        HERO_HORIZONTAL_VELOCITY,
        -1 * HERO_VERTICAL_VELOCITY,
        textureLevel.hero
    )
    private var enemyGroup: EnemyGroup = generateEnemy()
    private var bonus: Bonus? = null

    private val wallTexture: Texture = Texture("wall.png")
    private val timerTexture: Texture = Texture("timer.png")
    private var flipSound: Sound? = null
    private var bonusSound: Sound? = null
    private var deathSound: Sound? = null


    init {
        if (preferences.getBoolean(IS_SOUND_ENABLED)) {
            flipSound = Gdx.audio.newSound(Gdx.files.internal("flip.wav"))
            bonusSound = Gdx.audio.newSound(Gdx.files.internal("bonus.wav"))
            deathSound = Gdx.audio.newSound(Gdx.files.internal("death.mp3"))
        }
    }

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
        bonus?.let {
            batch.draw(
                it.texture,
                it.bound.x,
                it.bound.y,
                2 * it.bound.radius,
                2 * it.bound.radius
            )
        }
        enemyGroup.enemies.forEach {
            batch.draw(
                it.texture,
                it.bound.x,
                it.bound.y,
                2 * it.bound.radius,
                2 * it.bound.radius
            )
        }
        batch.draw(
            wallTexture,
            0F,
            0F,
            SCREEN_WIDTH,
            WALL_HEIGHT
        )
        batch.draw(
            wallTexture,
            0F,
            SCREEN_HEIGHT - WALL_HEIGHT,
            SCREEN_WIDTH,
            WALL_HEIGHT
        )
        val scoreTextures = NumbersHelper.getTextures(score)
        scoreTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                (index + 1) * SCORE_WIDTH,
                SCREEN_HEIGHT - 2 * SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
        bonus?.let {
            val lifespanFactor = (System.currentTimeMillis() - it.creationTime) / BONUS_LIFESPAN
            batch.draw(
                timerTexture,
                SCREEN_WIDTH / 2,
                TIMER_HEIGHT,
                (SCREEN_WIDTH / 2 - TIMER_MARGIN_WIDTH) * (1F - lifespanFactor),
                TIMER_HEIGHT
            )
            batch.draw(
                timerTexture,
                TIMER_MARGIN_WIDTH + ((SCREEN_WIDTH / 2 - TIMER_MARGIN_WIDTH) * lifespanFactor),
                TIMER_HEIGHT,
                (SCREEN_WIDTH / 2 - TIMER_MARGIN_WIDTH) * (1F - lifespanFactor),
                TIMER_HEIGHT
            )
        }
    }

    override fun handleInput() {
        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + 2 * hero.bound.radius < SCREEN_WIDTH) hero.moveRight()
        if (Gdx.input.isTouched && InputHelper.getLastTouchX() < SCREEN_WIDTH / 2 && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isTouched && InputHelper.getLastTouchX() > SCREEN_WIDTH / 2 && hero.bound.x + 2 * hero.bound.radius < SCREEN_WIDTH) hero.moveRight()
    }

    override fun stateCheck() {
        if (hero.bound.y + 2 * hero.bound.radius + WALL_HEIGHT >= SCREEN_HEIGHT) {
            hero.direction = DOWN_DIRECTION
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        } else if (hero.bound.y - WALL_HEIGHT <= 0) {
            hero.direction = UP_DIRECTION
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        }

        bonus?.let {
            if (it.isCollides(hero)) {
                bonusesCount++
                bonusSound?.play()
                bonus = null
            }
            if (System.currentTimeMillis() - it.creationTime >= BONUS_LIFESPAN) {
                bonus = null
            }
        }
        if (enemyGroup.isCollides(hero)) {
            deathSound?.play()
            game.screen = GameOverScreen(game, score, bonusesCount, textureLevel)
        }

        val first = enemyGroup.enemies.first()
        val last = enemyGroup.enemies.last()
        if ((first.bound.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || first.bound.x + first.bound.radius + ENEMY_RESPAWN_BORDER < 0) &&
            (last.bound.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || last.bound.x + last.bound.radius + ENEMY_RESPAWN_BORDER < 0)) {
            enemyGroup = generateEnemy()
        }
    }

    override fun screenDispose() {
        hero.dispose()
        enemyGroup.dispose()
        bonus?.dispose()
        flipSound?.dispose()
        bonusSound?.dispose()
        deathSound?.dispose()
        wallTexture.dispose()
    }

    private fun generateBonus() {
        if (bonus != null || random.nextFloat() > BONUS_CHANCE) {
            return
        }
        var x = random.nextInt((SCREEN_WIDTH - 2 * BONUS_SIZE).toInt()) + BONUS_SIZE
        val y = random.nextInt((SCREEN_HEIGHT - 2 * WALL_HEIGHT - 2 * BONUS_SIZE).toInt()) + WALL_HEIGHT + BONUS_SIZE
        if (hero.bound.x > SCREEN_WIDTH / 2) {
            x /= 2
        }
        bonus = BonusFactory.create(x, y, textureLevel.bonus)
    }

    private fun generateEnemy() = EnemyGroupFactory.create(
        EnemyGroupFactory.GroupType.values()[random.nextInt(EnemyGroupFactory.GroupType.values().size)],
        Enemy.Orientation.values()[random.nextInt(Enemy.Orientation.values().size)],
        enemyVelocity,
        textureLevel.enemy
    )

    private fun raiseEnemyVelocity() {
        if (enemyVelocity >= ENEMY_MAX_HORIZONTAL_VELOCITY) {
            return
        }
        enemyVelocity += ENEMY_VELOCITY_DELTA
    }

}