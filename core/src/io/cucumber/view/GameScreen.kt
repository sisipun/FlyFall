package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Array
import io.cucumber.model.base.Button
import io.cucumber.model.characters.Bonus
import io.cucumber.model.characters.EnemyGroup
import io.cucumber.model.characters.Hero
import io.cucumber.model.texture.Score
import io.cucumber.model.texture.TextureLevel
import io.cucumber.model.texture.Wall
import io.cucumber.service.factory.BonusFactory
import io.cucumber.service.factory.EnemyGroupFactory
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.utils.helper.InputHelper
import io.cucumber.view.GameScreen.State.GAME
import io.cucumber.view.GameScreen.State.PAUSE
import java.util.*

class GameScreen(
        game: Game,
        private var bonusesCount: Int,
        private val highScore: Int,
        private val isSoundEnabled: Boolean,
        textureLevel: TextureLevel
) : BaseScreen(game, textureLevel) {

    private val random = Random()


    private var gameState: State = GAME
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

    private var flipSound: Sound? = null
    private var bonusSound: Sound? = null
    private var deathSound: Sound? = null

    private val topWall: Wall = Wall(0F, 0F, SCREEN_WIDTH, WALL_HEIGHT, textureLevel.wall)
    private val bottomWall: Wall = Wall(0F, SCREEN_HEIGHT - WALL_HEIGHT, SCREEN_WIDTH, WALL_HEIGHT, textureLevel.wall)
    private val scoreActor: Score = Score(0F, SCREEN_HEIGHT - 2 * SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, 0)
    private val pauseButton: Button = Button(
            SCREEN_WIDTH - 1.5F * PAUSE_BUTTON_WIDTH,
            SCREEN_HEIGHT - 1.1F * PAUSE_BUTTON_HEIGHT,
            PAUSE_BUTTON_WIDTH,
            PAUSE_BUTTON_HEIGHT,
            this.textureLevel.pauseButton
    )
    private val resumeButton: Button = Button(
            SCREEN_WIDTH / 2 - RESUME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - RESUME_BUTTON_HEIGHT / 2,
            RESUME_BUTTON_WIDTH,
            RESUME_BUTTON_HEIGHT,
            this.textureLevel.playButton
    )
    private val homeButton: Button = Button(
            SCREEN_WIDTH / 2 + RESUME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - RESUME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.textureLevel.notButton
    )

    init {
        if (preferences.getBoolean(IS_SOUND_ENABLED)) {
            flipSound = Gdx.audio.newSound(Gdx.files.internal("flip.wav"))
            bonusSound = Gdx.audio.newSound(Gdx.files.internal("bonus.wav"))
            deathSound = Gdx.audio.newSound(Gdx.files.internal("death.mp3"))
        }
        addActors(Array.with(topWall, bottomWall, hero, enemyGroup, pauseButton, scoreActor))
    }

    override fun update(delta: Float) {
        if (gameState == PAUSE) {
            return
        }
        super.update(delta)
        scoreActor.addScore((delta * 1000).toInt())
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && pauseButton.isTouched(getTouchPosition())) {
            gameState = PAUSE
            addActors(Array.with(resumeButton, homeButton))
        }
        if (Gdx.input.justTouched() && resumeButton.isTouched(getTouchPosition())) {
            gameState = GAME
            resumeButton.remove()
            homeButton.remove()
        }
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game, bonusesCount, highScore, isSoundEnabled, textureLevel)
        if (gameState == PAUSE) {
            return
        }
        if (Gdx.input.isKeyPressed(LEFT) && hero.x > 0) hero.moveLeft()
        if (Gdx.input.isKeyPressed(RIGHT) && hero.x + hero.width < SCREEN_WIDTH) hero.moveRight()
        if (Gdx.input.isTouched && InputHelper.getLastTouchX() < SCREEN_WIDTH / 2 && hero.x > 0) hero.moveLeft()
        if (Gdx.input.isTouched && InputHelper.getLastTouchX() > SCREEN_WIDTH / 2 && hero.x + hero.width < SCREEN_WIDTH) hero.moveRight()
    }

    override fun stateCheck() {
        if (gameState == PAUSE) {
            return
        }
        if (hero.y + hero.height + WALL_HEIGHT >= SCREEN_HEIGHT) {
            hero.setDirection(DOWN_DIRECTION)
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        } else if (hero.y - WALL_HEIGHT <= 0) {
            hero.setDirection(UP_DIRECTION)
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        }

        bonus?.let {
            if (it.isCollides(hero)) {
                bonusesCount++
                bonusSound?.play()
                it.remove()
                bonus = null
            }
            if (it.lifespan <= 0) {
                it.remove()
                bonus = null
            }
        }
        if (enemyGroup.isCollides(hero)) {
            deathSound?.play()
            game.screen = GameOverScreen(game, scoreActor.score, bonusesCount, highScore, isSoundEnabled, textureLevel)
        }

        val first = enemyGroup.enemies.first()
        val last = enemyGroup.enemies.last()
        if ((first.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || first.x + first.width + ENEMY_RESPAWN_BORDER < 0) &&
                (last.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || last.x + last.width + ENEMY_RESPAWN_BORDER < 0)) {
            enemyGroup.enemies.clear()
            enemyGroup.remove()
            enemyGroup = generateEnemy()
            addActor(enemyGroup)
        }
    }

    override fun screenDispose() {
        flipSound?.dispose()
        bonusSound?.dispose()
        deathSound?.dispose()
        enemyGroup.clear()
    }

    private fun generateBonus() {
        if (bonus != null || random.nextFloat() > BONUS_CHANCE) {
            return
        }
        var x = random.nextInt((SCREEN_WIDTH - 2 * BONUS_SIZE).toInt()) + BONUS_SIZE
        val y = random.nextInt((SCREEN_HEIGHT - 2 * WALL_HEIGHT - 2 * BONUS_SIZE).toInt()) + WALL_HEIGHT + BONUS_SIZE
        if (hero.x > SCREEN_WIDTH / 2) {
            x /= 2
        }
        bonus = BonusFactory.create(x, y, textureLevel.bonus, textureLevel.timer)
        bonus?.let { addActor(it) }
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

    enum class State {
        GAME,
        PAUSE
    }
}