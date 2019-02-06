package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import io.cucumber.model.base.Button
import io.cucumber.model.characters.Bonus
import io.cucumber.model.characters.EnemyGroup
import io.cucumber.model.characters.Hero
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.factory.BonusFactory
import io.cucumber.service.factory.EnemyGroupFactory
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.utils.helper.InputHelper
import io.cucumber.utils.helper.NumbersHelper
import io.cucumber.view.GameScreen.Stage.GAME
import io.cucumber.view.GameScreen.Stage.PAUSE
import java.util.*

class GameScreen(
    game: Game,
    private var bonusesCount: Int,
    private val highScore: Int,
    private val isSoundEnabled: Boolean,
    private val textureLevel: TextureLevel
) : BaseScreen(game) {

    private val random = Random()

    private var stage: Stage = GAME

    private var score: Int = 0
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
    private val pauseButton: Button = Button(
        SCREEN_WIDTH - 1.5F * PAUSE_BUTTON_WIDTH,
        SCREEN_HEIGHT - 1.1F * PAUSE_BUTTON_HEIGHT,
        PAUSE_BUTTON_WIDTH,
        PAUSE_BUTTON_HEIGHT,
        timerTexture
    )
    private val resumeButton: Button = Button(
        SCREEN_WIDTH / 2 - RESUME_BUTTON_WIDTH,
        SCREEN_HEIGHT / 2 - RESUME_BUTTON_HEIGHT / 2,
        RESUME_BUTTON_WIDTH,
        RESUME_BUTTON_HEIGHT,
        wallTexture
    )
    private val homeButton: Button = Button(
        SCREEN_WIDTH / 2 + RESUME_BUTTON_WIDTH,
        SCREEN_HEIGHT / 2 - RESUME_BUTTON_HEIGHT / 2,
        HOME_BUTTON_WIDTH,
        HOME_BUTTON_HEIGHT,
        wallTexture
    )


    init {
        if (preferences.getBoolean(IS_SOUND_ENABLED)) {
            flipSound = Gdx.audio.newSound(Gdx.files.internal("flip.wav"))
            bonusSound = Gdx.audio.newSound(Gdx.files.internal("bonus.wav"))
            deathSound = Gdx.audio.newSound(Gdx.files.internal("death.mp3"))
        }
    }

    override fun update(delta: Float) {
        if (stage == PAUSE) {
            return
        }
        hero.update(delta)
        enemyGroup.update(delta)
        bonus?.update(delta)
        score += (delta * 1000).toInt()
    }

    override fun render() {
        batch.draw(
            textureLevel.background,
            0F,
            0F,
            SCREEN_WIDTH,
            SCREEN_HEIGHT
        )
        batch.draw(
            hero.texture,
            hero.bound.x,
            hero.bound.y,
            2 * hero.bound.radius,
            2 * hero.bound.radius
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
            val lifespanFactor = (BONUS_LIFESPAN - it.lifespan) / BONUS_LIFESPAN
            batch.draw(
                it.texture,
                it.bound.x,
                it.bound.y,
                2 * it.bound.radius,
                2 * it.bound.radius
            )
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
        batch.draw(
            pauseButton.texture,
            pauseButton.bound.x,
            pauseButton.bound.y,
            pauseButton.bound.width,
            pauseButton.bound.height
        )
        if (stage == PAUSE) {
            batch.draw(
                resumeButton.texture,
                resumeButton.bound.x,
                resumeButton.bound.y,
                resumeButton.bound.width,
                resumeButton.bound.height
            )
            batch.draw(
                homeButton.texture,
                homeButton.bound.x,
                homeButton.bound.y,
                homeButton.bound.width,
                homeButton.bound.height
            )
        }
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && pauseButton.isTouched(getTouchPosition())) stage = PAUSE
        if (Gdx.input.justTouched() && resumeButton.isTouched(getTouchPosition())) stage = GAME
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game, bonusesCount, highScore, isSoundEnabled, textureLevel)
        if (stage == PAUSE) {
            return
        }
        if (Gdx.input.isKeyPressed(LEFT) && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.bound.x + 2 * hero.bound.radius < SCREEN_WIDTH) hero.moveRight()
        if (Gdx.input.isTouched && InputHelper.getLastTouchX() < SCREEN_WIDTH / 2 && hero.bound.x > 0) hero.moveLeft()
        if (Gdx.input.isTouched && InputHelper.getLastTouchX() > SCREEN_WIDTH / 2 && hero.bound.x + 2 * hero.bound.radius < SCREEN_WIDTH) hero.moveRight()
    }

    override fun stateCheck() {
        if (stage == PAUSE) {
            return
        }
        if (hero.bound.y + 2 * hero.bound.radius + WALL_HEIGHT >= SCREEN_HEIGHT) {
            hero.setDirection(DOWN_DIRECTION)
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        } else if (hero.bound.y - WALL_HEIGHT <= 0) {
            hero.setDirection(UP_DIRECTION)
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
            if (it.lifespan <= 0) {
                bonus = null
            }
        }
        if (enemyGroup.isCollides(hero)) {
            deathSound?.play()
            game.screen = GameOverScreen(game, score, bonusesCount, highScore, isSoundEnabled, textureLevel)
        }

        val first = enemyGroup.enemies.first()
        val last = enemyGroup.enemies.last()
        if ((first.bound.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || first.bound.x + first.bound.radius + ENEMY_RESPAWN_BORDER < 0) &&
            (last.bound.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || last.bound.x + last.bound.radius + ENEMY_RESPAWN_BORDER < 0)) {
            enemyGroup.enemies.clear()
            enemyGroup = generateEnemy()
        }
    }

    override fun screenDispose() {
        flipSound?.dispose()
        bonusSound?.dispose()
        deathSound?.dispose()
        wallTexture.dispose()
        timerTexture.dispose()
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
        random.nextInt(GROUP_TYPES_COUNT.toInt()).toByte(),
        Math.pow(-1.0, (random.nextInt(2) + 1.0)).toByte(),
        enemyVelocity,
        textureLevel.enemy
    )

    private fun raiseEnemyVelocity() {
        if (enemyVelocity >= ENEMY_MAX_HORIZONTAL_VELOCITY) {
            return
        }
        enemyVelocity += ENEMY_VELOCITY_DELTA
    }

    enum class Stage {
        GAME,
        PAUSE
    }
}