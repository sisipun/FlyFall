package io.cucumber.view

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.actor.character.Bonus
import io.cucumber.model.actor.character.EnemyGroup
import io.cucumber.model.actor.character.Hero
import io.cucumber.model.actor.shape.SimpleRectangle
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.text.ScoreLabel
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.factory.BonusFactory
import io.cucumber.service.factory.EnemyGroupFactory
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.LABEL
import io.cucumber.service.manager.FontManager.FontType.TITLE
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.view.GameScreen.State.GAME
import io.cucumber.view.GameScreen.State.PAUSE
import java.util.*

class GameScreen(
        game: Game,
        private var bonusCount: Int,
        private val highScore: Int,
        private val isSoundOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Screen
    private val random = Random()
    private var gameState: State = GAME
    private var enemyVelocity: Float = ENEMY_MIN_HORIZONTAL_VELOCITY

    private var flipSound: Sound? = null
    private var bonusSound: Sound? = null
    private var deathSound: Sound? = null

    // Actors
    private val hero: Hero = Hero(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2,
            HERO_SIZE,
            HERO_HORIZONTAL_VELOCITY,
            -1 * HERO_VERTICAL_VELOCITY,
            this.levelAssets.hero
    )
    private var enemyGroup: EnemyGroup? = null
    private var bonus: Bonus? = null

    private val topWall: SimpleRectangle = SimpleRectangle(
            SCREEN_WIDTH / 2,
            WALL_HEIGHT / 2,
            SCREEN_WIDTH,
            WALL_HEIGHT,
            this.levelAssets.wall
    )
    private val bottomWall: SimpleRectangle = SimpleRectangle(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT - WALL_HEIGHT / 2,
            SCREEN_WIDTH,
            WALL_HEIGHT,
            this.levelAssets.wall
    )
    private val scoreActor: ScoreLabel = ScoreLabel(
            SCORE_WIDTH,
            SCREEN_HEIGHT - SCORE_HEIGHT,
            0,
            FontManager.get(LABEL)
    )
    private val pauseButton: ImageButton = ImageButton(
            SCREEN_WIDTH - PAUSE_BUTTON_WIDTH,
            SCREEN_HEIGHT - PAUSE_BUTTON_HEIGHT * 0.6f,
            PAUSE_BUTTON_WIDTH,
            PAUSE_BUTTON_HEIGHT,
            this.levelAssets.pauseButton
    )
    private val resumeButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - RESUME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - RESUME_BUTTON_HEIGHT / 2,
            RESUME_BUTTON_WIDTH,
            RESUME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val homeButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.notButton
    )
    private val pauseTitle: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2 + SCREEN_HEIGHT / 6,
            PAUSE_LABEL_TEXT,
            FontManager.get(TITLE)
    )

    init {
        if (isSoundOn) {
            flipSound = this.levelAssets.flipSound
            bonusSound = this.levelAssets.bonusSound
            deathSound = this.levelAssets.deathSound
        }

        pauseButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                pauseGame()
            }
        })
        resumeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                resumeGame()
            }
        })
        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                home()
            }
        })
        addBackgroundListener(object: InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (gameState == GAME && ESCAPE == keycode) pauseGame()
                else if (gameState == PAUSE && ESCAPE == keycode) home()
                else if (gameState == PAUSE && ENTER == keycode) resumeGame()
                else if (gameState == PAUSE) return false

                if (LEFT == keycode && hero.x > 0) hero.moveLeft()
                if (RIGHT == keycode && hero.x + hero.width < SCREEN_WIDTH) hero.moveRight()
                return true
            }

            override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                if (gameState == PAUSE) return false

                if (LEFT == keycode) hero.stopLeft()
                if (RIGHT == keycode) hero.stopRight()
                return true
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (gameState == PAUSE) return false
                else if (x < SCREEN_WIDTH / 2 && hero.x > 0) hero.moveLeft()
                else if (x > SCREEN_WIDTH / 2 && hero.x + hero.width < SCREEN_WIDTH) hero.moveRight()

                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if (gameState == PAUSE) return

                else if (x < SCREEN_WIDTH / 2 && hero.x > 0) hero.stopLeft()
                else if (x > SCREEN_WIDTH / 2 && hero.x + hero.width < SCREEN_WIDTH) hero.stopRight()
            }
        })

        addActors(Array.with(topWall, bottomWall, hero, pauseButton, scoreActor))
    }

    override fun act(delta: Float) {
        if (gameState == PAUSE) {
            return
        }
        super.act(delta)
        scoreActor.addScore((delta * 1000).toInt())
    }

    override fun stateCheck() {
        if (gameState == PAUSE) {
            return
        }
        if (hero.y + hero.height + WALL_HEIGHT >= SCREEN_HEIGHT && hero.directionY == UP_DIRECTION) {
            hero.directionY = DOWN_DIRECTION
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        } else if (hero.y - WALL_HEIGHT <= 0 && hero.directionY == DOWN_DIRECTION) {
            hero.directionY = UP_DIRECTION
            generateBonus()
            raiseEnemyVelocity()
            flipSound?.play()
        }

        if (hero.x < 0) {
            hero.stopLeft()
        } else if (hero.x + hero.width > SCREEN_WIDTH) {
            hero.stopRight()
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
        enemyGroup?.let {
            if (it.isCollides(hero)) {
                deathSound?.play()
                game.screen = GameOverScreen(game, scoreActor.score, bonusCount, highScore, isSoundOn, levelAssets)
            }

            val first = it.enemies.first()
            val last = it.enemies.last()
            if ((first.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || first.x + first.width + ENEMY_RESPAWN_BORDER < 0) &&
                    (last.x > ENEMY_RESPAWN_BORDER + SCREEN_WIDTH || last.x + last.width + ENEMY_RESPAWN_BORDER < 0)) {
                it.enemies.clear()
                it.remove()
                enemyGroup = null
            }
        }
        if (enemyGroup == null) {
            generateEnemy()
        }
    }

    private fun pauseGame() {
        gameState = PAUSE
        addActors(Array.with(resumeButton, homeButton, pauseTitle))
    }

    private fun resumeGame() {
        gameState = GAME
        resumeButton.remove()
        homeButton.remove()
        pauseTitle.remove()
    }

    private fun home() {
        game.screen = StartScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.levelAssets
        )
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
        bonus = BonusFactory.create(x, y, levelAssets.bonus, levelAssets.timer)
        bonus?.let { addActor(it) }
    }

    private fun generateEnemy() {
        enemyGroup = EnemyGroupFactory.create(
                random.nextInt(GROUP_TYPES_COUNT.toInt()).toByte(),
                Math.pow(-1.0, (random.nextInt(2) + 1.0)).toByte(),
                enemyVelocity,
                levelAssets.enemy
        )

        enemyGroup?.let {
            it.enemies.forEach { enemy ->
                run {
                    val action = RepeatAction()
                    action.count = RepeatAction.FOREVER
                    action.action = Actions.rotateBy(ENEMY_ROTATION_ANGEL, ENEMY_ROTATION_DURATION)
                    enemy.addAction(action)
                }
            }
            addActor(it)
        }
    }

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