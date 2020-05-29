package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Timer
import io.cucumber.Game
import io.cucumber.model.actor.character.Bonus
import io.cucumber.model.actor.character.EnemyGroup
import io.cucumber.model.actor.character.Hero
import io.cucumber.model.actor.shape.SimpleRectangle
import io.cucumber.model.base.GameDifficulty
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.text.ScoreLabel
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.factory.BonusFactory
import io.cucumber.service.factory.EnemyGroupFactory
import io.cucumber.service.factory.HeroFactory
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.LABEL
import io.cucumber.service.manager.FontManager.FontType.TITLE
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.model.base.GameDifficulty.*
import io.cucumber.view.GameScreen.State.*
import java.util.*
import kotlin.math.pow

class GameScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private var isSoundOn: Boolean,
        private var isAcceleratorOn: Boolean,
        private var gameDifficulty: GameDifficulty,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Screen
    private val random = Random()

    private var gameState: State = GAME
    private var scoreMultiplier = if (gameDifficulty == HARD) HARD_DIFFICULTY_SCORE_MULTIPLIER else NORMAL_DIFFICULTY_SCORE_MULTIPLIER
    private var bonusChance = if (gameDifficulty == HARD) HARD_DIFFICULTY_BONUS_CHANCE else NORMAL_DIFFICULTY_BONUS_CHANCE
    private var enemyVelocity: Float = ENEMY_MIN_HORIZONTAL_VELOCITY
    private var countdownTask: Timer.Task? = null

    private var flipSound: Sound? = null
    private var bonusSound: Sound? = null
    private var deathSound: Sound? = null

    // Actors
    private var hero: Hero = HeroFactory.create(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2,
            this.levelAssets.hero,
            levelAssets.explosion
    )
    private var enemyGroups: Array<EnemyGroup> = Array(2)
    private var bonus: Bonus? = null

    private val bottomWall: SimpleRectangle = SimpleRectangle(
            game.stage.camera.viewportWidth / 2,
            WALL_HEIGHT / 2,
            game.stage.camera.viewportWidth,
            WALL_HEIGHT,
            this.levelAssets.wall
    )
    private val topWall: SimpleRectangle = SimpleRectangle(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight - WALL_HEIGHT / 2,
            game.stage.camera.viewportWidth,
            WALL_HEIGHT,
            this.levelAssets.wall,
            true
    )
    private val scoreActor: ScoreLabel = ScoreLabel(
            2 * SCORE_WIDTH,
            game.stage.camera.viewportHeight - (WALL_HEIGHT / 3),
            0,
            FontManager.get(LABEL)
    )
    private val pauseButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth - PAUSE_BUTTON_WIDTH,
            game.stage.camera.viewportHeight - (WALL_HEIGHT / 2),
            PAUSE_BUTTON_WIDTH,
            PAUSE_BUTTON_HEIGHT,
            this.levelAssets.pauseButton
    )
    private val resumeButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 - RESUME_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2 - RESUME_BUTTON_HEIGHT / 2,
            RESUME_BUTTON_WIDTH,
            RESUME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val homeButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 + HOME_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.homeButton
    )
    private val pauseTitle: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 + game.stage.camera.viewportHeight / 6,
            PAUSE_LABEL_TEXT,
            FontManager.get(TITLE)
    )
    private val highScoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 - 8 * SCORE_HEIGHT,
            HIGH_SCORE_LABEL_TEXT + this.highScore.toString(),
            FontManager.get(LABEL)
    )
    private val bonusCountLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 - 10 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT + this.bonusCount.toString(),
            FontManager.get(LABEL)
    )

    init {
        this.pauseButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                pauseGame()
            }
        })
        this.resumeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                resumeGame()
            }
        })
        this.homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                home()
            }
        })

        if (!this.isAcceleratorOn) {
            addBackgroundListener(object : InputListener() {
                override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                    if (gameState == GAME && Input.Keys.ESCAPE == keycode) pauseGame()
                    else if (gameState == PAUSE && Input.Keys.ESCAPE == keycode) home()
                    else if (gameState == PAUSE && Input.Keys.ENTER == keycode) resumeGame()
                    else if (gameState == PAUSE) return false

                    if (Input.Keys.LEFT == keycode && hero.x > 0) hero.moveLeft()
                    if (Input.Keys.RIGHT == keycode && hero.x + hero.width < game.stage.camera.viewportWidth) hero.moveRight()
                    return true
                }

                override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                    if (gameState == PAUSE) return false

                    if (Input.Keys.LEFT == keycode) hero.stopLeft()
                    if (Input.Keys.RIGHT == keycode) hero.stopRight()
                    return true
                }

                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    if (gameState == PAUSE) return false
                    else if (x < game.stage.camera.viewportWidth / 2 && hero.x > 0) hero.moveLeft()
                    else if (x > game.stage.camera.viewportWidth / 2 && hero.x + hero.width < game.stage.camera.viewportWidth) hero.moveRight()

                    return true
                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    if (gameState == PAUSE) return
                    else if (x < game.stage.camera.viewportWidth / 2 && hero.x > 0) hero.stopLeft()
                    else if (x > game.stage.camera.viewportWidth / 2 && hero.x + hero.width < game.stage.camera.viewportWidth) hero.stopRight()
                }
            })
        }
    }

    fun init(bonusCount: Int, highScore: Int, isSoundOn: Boolean, isAcceleratorOn: Boolean,
             gameDifficulty: GameDifficulty, levelAssets: LevelAssets): GameScreen {
        this.isSoundOn = isSoundOn
        this.highScore = highScore
        this.bonusCount = bonusCount
        this.levelAssets = levelAssets
        this.gameState = GAME
        this.gameDifficulty = gameDifficulty
        this.scoreMultiplier = if (gameDifficulty == HARD) HARD_DIFFICULTY_SCORE_MULTIPLIER else NORMAL_DIFFICULTY_SCORE_MULTIPLIER
        this.bonusChance = if (gameDifficulty == HARD) HARD_DIFFICULTY_BONUS_CHANCE else NORMAL_DIFFICULTY_BONUS_CHANCE
        this.enemyVelocity = ENEMY_MIN_HORIZONTAL_VELOCITY

        this.flipSound = null
        this.bonusSound = null
        this.deathSound = null

        this.scoreActor.resetScore()

        this.hero = HeroFactory.create(
                game.stage.camera.viewportWidth / 2,
                game.stage.camera.viewportHeight / 2,
                this.levelAssets.hero,
                levelAssets.explosion
        )

        this.pauseButton.setTexture(this.levelAssets.pauseButton)
        this.resumeButton.setTexture(this.levelAssets.playButton)
        this.homeButton.setTexture(this.levelAssets.homeButton)

        this.topWall.setRegion(this.levelAssets.wall)
        this.bottomWall.setRegion(this.levelAssets.wall)

        highScoreLabel.setText(HIGH_SCORE_LABEL_TEXT + this.highScore.toString())
        bonusCountLabel.setText(BONUS_LABEL_TEXT + this.bonusCount.toString())

        if (this.isAcceleratorOn != isAcceleratorOn) {
            this.isAcceleratorOn = isAcceleratorOn
            if (this.isAcceleratorOn) {
                clearBackgroundListeners()
            } else {
                addBackgroundListener(object : InputListener() {
                    override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                        if (gameState == GAME && Input.Keys.ESCAPE == keycode) pauseGame()
                        else if (gameState == PAUSE && Input.Keys.ESCAPE == keycode) home()
                        else if (gameState == PAUSE && Input.Keys.ENTER == keycode) resumeGame()
                        else if (gameState == PAUSE) return false

                        if (Input.Keys.LEFT == keycode && hero.x > 0) hero.moveLeft()
                        if (Input.Keys.RIGHT == keycode && hero.x + hero.width < game.stage.camera.viewportWidth) hero.moveRight()
                        return true
                    }

                    override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
                        if (gameState == PAUSE) return false

                        if (Input.Keys.LEFT == keycode) hero.stopLeft()
                        if (Input.Keys.RIGHT == keycode) hero.stopRight()
                        return true
                    }

                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                        if (gameState == PAUSE) return false
                        else if (x < game.stage.camera.viewportWidth / 2 && hero.x > 0) hero.moveLeft()
                        else if (x > game.stage.camera.viewportWidth / 2 && hero.x + hero.width < game.stage.camera.viewportWidth) hero.moveRight()

                        return true
                    }

                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        if (gameState == PAUSE) return
                        else if (x < game.stage.camera.viewportWidth / 2 && hero.x > 0) hero.stopLeft()
                        else if (x > game.stage.camera.viewportWidth / 2 && hero.x + hero.width < game.stage.camera.viewportWidth) hero.stopRight()
                    }
                })
            }
        }

        return this
    }

    override fun show() {
        super.show()
        if (isSoundOn) {
            flipSound = levelAssets.flipSound
            bonusSound = levelAssets.bonusSound
            deathSound = levelAssets.deathSound
        }

        addActors(Array.with(topWall, bottomWall, hero, pauseButton, scoreActor))
    }

    override fun hide() {
        removeHero()
        removeBonus()
        removeEnemyGroups()
        super.hide()
    }

    override fun pause() {
        pauseGame()
    }

    override fun act(delta: Float) {
        if (gameState == PAUSE) {
            return
        }

        if (isAcceleratorOn) {
            val accelY = Gdx.input.accelerometerY
            if (accelY > 0F && hero.x + hero.width < game.stage.camera.viewportWidth) {
                hero.stopLeft()
                hero.moveRight(accelY)
            } else if (accelY < 0F && hero.x > 0) {
                hero.stopRight()
                hero.moveLeft(-1 * accelY)
            }
        }

        super.act(delta)
        scoreActor.addScore((scoreMultiplier * delta * 1000).toInt())
    }

    override fun stateCheck() {
        if (gameState == GAME_OVER && hero.isExplode) {
            gameOver()
            return
        }

        if (gameState == PAUSE) {
            return
        }

        if (enemyGroups.size == 0) {
            generateEnemyGroups()
        }

        if (hero.y + hero.height + WALL_HEIGHT >= game.stage.camera.viewportHeight && hero.directionY == UP_DIRECTION) {
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
        } else if (hero.x + hero.width > game.stage.camera.viewportWidth) {
            hero.stopRight()
        }

        bonus?.let {
            if (it.isCollides(hero) && gameState == GAME) {
                bonusCount++
                bonusSound?.play()
                bonusCountLabel.setText(BONUS_LABEL_TEXT + this.bonusCount.toString())
                game.preferences.putInteger(BONUSES_COUNT, bonusCount)
                game.preferences.flush()
                removeBonus()
            }
            if (it.lifespan <= 0) {
                removeBonus()
            }
        }

        enemyGroups.forEach {
            if (it.isCollides(hero) && gameState == GAME) {
                hero.explode()
                deathSound?.play()
                gameState = GAME_OVER
            }
        }

        val removeGroups = enemyGroups.all {
            (it.enemies.size == 0)
                    || ((it.enemies.first().x > ENEMY_RESPAWN_BORDER + game.stage.camera.viewportWidth
                    || it.enemies.first().x + it.enemies.first().width + ENEMY_RESPAWN_BORDER < 0)
                    && (it.enemies.last().x > ENEMY_RESPAWN_BORDER + game.stage.camera.viewportWidth
                    || it.enemies.last().x + it.enemies.last().width + ENEMY_RESPAWN_BORDER < 0))
        }

        if (removeGroups) {
            removeEnemyGroups()
        }
    }

    private fun pauseGame() {
        if (gameState == GAME_OVER) {
            return
        }

        countdownTask?.let {
            it.cancel()
            pauseTitle.setText(PAUSE_LABEL_TEXT)
        }
        countdownTask = null

        gameState = PAUSE
        addActors(Array.with(resumeButton, homeButton, pauseTitle, highScoreLabel, bonusCountLabel))
    }

    private fun resumeGame() {
        countdownTask?.let {
            it.cancel()
            pauseTitle.setText(PAUSE_LABEL_TEXT)
        }
        countdownTask = null

        var countdown = 3
        countdownTask = Timer.schedule(object : Timer.Task() {
            override fun run() {
                if (countdown > 0) {
                    pauseTitle.setText(countdown.toString())
                    countdown--
                } else {
                    pauseTitle.setText(PAUSE_LABEL_TEXT)
                    pauseTitle.remove()
                    countdownTask = null
                    gameState = GAME
                }
            }
        }, COUNTDOWN_DELAY, COUNTDOWN_INTERVAL, COUNTDOWN_COUNT)

        resumeButton.remove()
        homeButton.remove()
        highScoreLabel.remove()
        bonusCountLabel.remove()
    }

    private fun home() {
        setScreen(ScreenManager.getStartScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.isAcceleratorOn,
                this.levelAssets
        ))
    }

    private fun gameOver() {
        setScreen(ScreenManager.getGameOverScreen(
                game,
                scoreActor.score,
                bonusCount,
                highScore,
                isSoundOn,
                isAcceleratorOn,
                gameDifficulty,
                levelAssets
        ))
    }

    private fun generateBonus() {
        if (bonus != null || random.nextFloat() > bonusChance) {
            return
        }

        var x = random.nextInt((game.stage.camera.viewportWidth - 4 * BONUS_SIZE).toInt()) + 2 * BONUS_SIZE
        val y = random.nextInt((game.stage.camera.viewportHeight - 2 * WALL_HEIGHT - 2 * BONUS_SIZE).toInt()) + WALL_HEIGHT + BONUS_SIZE

        if (hero.x > game.stage.camera.viewportWidth / 2) {
            x /= 2
        }
        bonus = BonusFactory.create(x, y, levelAssets.bonus, levelAssets.timer)
        bonus?.let {
            it.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.25f, 1.25f, 0.5f),
                    Actions.scaleTo(0.75f, 0.75f, 1f),
                    Actions.scaleTo(1f, 1f, 0.5f)
            )))
            addActor(it)
        }
    }

    private fun generateEnemyGroups() {
        val orientation = (-1.0).pow((random.nextInt(2) + 1.0)).toByte()
        enemyGroups.add(EnemyGroupFactory.create(
                random.nextInt(GROUP_TYPES_COUNT.toInt()).toByte(),
                orientation,
                enemyVelocity,
                levelAssets.enemy,
                game.stage.camera
        ))
        if (gameDifficulty == HARD) {
            enemyGroups.add(EnemyGroupFactory.create(
                    random.nextInt(GROUP_TYPES_COUNT.toInt()).toByte(),
                    (-1 * orientation).toByte(),
                    enemyVelocity,
                    levelAssets.enemy,
                    game.stage.camera
            ))
        }

        enemyGroups.forEach {
            addActor(it)
            if (levelAssets.isEnemyRotate) {
                it.enemies.forEach { enemy -> enemy.addAction(Actions.forever(Actions.rotateBy(360f, 1f))) }
            }
        }
    }

    private fun removeBonus() {
        bonus?.let {
            BonusFactory.free(it)
            it.remove()
        }
        bonus = null
    }

    private fun removeEnemyGroups() {
        enemyGroups.forEach {
            EnemyGroupFactory.free(it)
            it.remove()
        }
        enemyGroups.clear()
    }

    private fun removeHero() {
        HeroFactory.free(hero)
        hero.remove()
    }

    private fun raiseEnemyVelocity() {
        if (enemyVelocity >= ENEMY_MAX_HORIZONTAL_VELOCITY) {
            return
        }
        enemyVelocity += ENEMY_VELOCITY_DELTA
    }

    private enum class State {
        GAME,
        PAUSE,
        GAME_OVER
    }
}