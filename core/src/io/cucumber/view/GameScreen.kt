package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.LEFT
import com.badlogic.gdx.Input.Keys.RIGHT
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.buttons.ImageButton
import io.cucumber.model.characters.Bonus
import io.cucumber.model.characters.EnemyGroup
import io.cucumber.model.characters.Hero
import io.cucumber.model.texture.Score
import io.cucumber.model.texture.SimpleRectangle
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.factory.BonusFactory
import io.cucumber.service.factory.EnemyGroupFactory
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.view.GameScreen.State.GAME
import io.cucumber.view.GameScreen.State.PAUSE
import java.util.*

class GameScreen(
        game: Game,
        private var bonusCount: Int,
        private val highScore: Int,
        private val isSoundOn: Boolean,
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

    private val topWall: SimpleRectangle = SimpleRectangle(0F, 0F, SCREEN_WIDTH, WALL_HEIGHT, textureLevel.wall)
    private val bottomWall: SimpleRectangle = SimpleRectangle(0F, SCREEN_HEIGHT - WALL_HEIGHT, SCREEN_WIDTH, WALL_HEIGHT, textureLevel.wall)
    private val scoreActor: Score = Score(0F, SCREEN_HEIGHT - 2 * SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, 0)
    private val pauseButton: Button = ImageButton(
            SCREEN_WIDTH - 1.5F * PAUSE_BUTTON_WIDTH,
            SCREEN_HEIGHT - 1.1F * PAUSE_BUTTON_HEIGHT,
            PAUSE_BUTTON_WIDTH,
            PAUSE_BUTTON_HEIGHT,
            this.textureLevel.pauseButton
    )
    private val resumeButton: Button = ImageButton(
            SCREEN_WIDTH / 2 - RESUME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - RESUME_BUTTON_HEIGHT / 2,
            RESUME_BUTTON_WIDTH,
            RESUME_BUTTON_HEIGHT,
            this.textureLevel.playButton
    )
    private val homeButton: Button = ImageButton(
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

        pauseButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                gameState = PAUSE
                addActors(Array.with(resumeButton, homeButton))
            }
        })
        resumeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                gameState = GAME
                resumeButton.remove()
                homeButton.remove()
            }
        })
        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = StartScreen(this@GameScreen.game, this@GameScreen.bonusCount, this@GameScreen.highScore,
                        this@GameScreen.isSoundOn, this@GameScreen.textureLevel)
            }
        })
        addBackgroundListener(object: InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (gameState == PAUSE) return false

                if (LEFT == keycode && hero.x > 0) hero.moveLeft()
                if (RIGHT == keycode && hero.x + hero.width < SCREEN_WIDTH) hero.moveRight()
                return true
            }

            override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                if (gameState == PAUSE) return false

                if (LEFT == keycode && hero.moveDirection == LEFT_DIRECTION ||
                        RIGHT == keycode && hero.moveDirection == RIGHT_DIRECTION) hero.stop()
                return true
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (gameState == PAUSE) return false

                if (x < SCREEN_WIDTH / 2 && hero.x > 0) hero.moveLeft()
                if (x > SCREEN_WIDTH / 2 && hero.x + hero.width < SCREEN_WIDTH) hero.moveRight()

                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if (gameState == PAUSE) return

                hero.stop()
            }
        })

        addActors(Array.with(topWall, bottomWall, hero, enemyGroup, pauseButton, scoreActor))
    }

    override fun update(delta: Float) {
        if (gameState == PAUSE) {
            return
        }
        super.update(delta)
        scoreActor.addScore((delta * 1000).toInt())
    }

    override fun stateCheck() {
        if (gameState == PAUSE) {
            return
        }
        if (hero.y + hero.height + WALL_HEIGHT >= SCREEN_HEIGHT) {
            hero.setDirectionY(DOWN_DIRECTION)
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        } else if (hero.y - WALL_HEIGHT <= 0) {
            hero.setDirectionY(UP_DIRECTION)
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        }

        bonus?.let {
            if (it.isCollides(hero)) {
                bonusCount++
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
            game.screen = GameOverScreen(game, scoreActor.score, bonusCount, highScore, isSoundOn, textureLevel)
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