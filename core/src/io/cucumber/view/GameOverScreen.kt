package io.cucumber.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.base.GameDifficulty
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*

class GameOverScreen(
        game: Game,
        private var score: Int,
        private var bonusCount: Int,
        private var highScore: Int,
        private var isSoundOn: Boolean,
        private var isAcceleratorOn: Boolean,
        private var gameDifficulty: GameDifficulty,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Actors
    private val homeButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 + HOME_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.homeButton
    )
    private val restartButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 - RESTART_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2,
            RESTART_BUTTON_WIDTH,
            RESTART_BUTTON_HEIGHT,
            this.levelAssets.restartButton
    )
    private val scoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            6 * SCORE_HEIGHT,
            SCORE_LABEL_TEXT + this.score,
            this.levelAssets.labelFont
    )
    private val highScoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            4 * SCORE_HEIGHT,
            HIGH_SCORE_LABEL_TEXT + this.highScore,
            this.levelAssets.labelFont
    )
    private val bonusCountLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            2 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT + this.bonusCount,
            this.levelAssets.labelFont
    )

    init {
        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                home()
            }
        })
        restartButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                restart()
            }
        })
        addBackgroundListener(object: InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (Input.Keys.ENTER == keycode) restart()
                if (Input.Keys.ESCAPE == keycode) home()
                return true
            }
        })
    }

    fun init(score: Int, bonusCount: Int, highScore: Int, isSoundOn: Boolean,
             isAcceleratorOn: Boolean, gameDifficulty: GameDifficulty, levelAssets: LevelAssets): GameOverScreen {
        this.score = score
        this.isSoundOn = isSoundOn
        this.isAcceleratorOn = isAcceleratorOn
        this.highScore = highScore
        this.bonusCount = bonusCount
        this.gameDifficulty = gameDifficulty
        this.levelAssets = levelAssets

        this.homeButton.setTexture(this.levelAssets.homeButton)
        this.restartButton.setTexture(this.levelAssets.restartButton)


        this.scoreLabel.setFont(this.levelAssets.labelFont)
        this.scoreLabel.setText(SCORE_LABEL_TEXT + score)
        this.highScoreLabel.setFont(this.levelAssets.labelFont)
        this.highScoreLabel.setText(HIGH_SCORE_LABEL_TEXT + highScore)
        this.bonusCountLabel.setFont(this.levelAssets.labelFont)
        this.bonusCountLabel.setText(BONUS_LABEL_TEXT + bonusCount)

        return this
    }

    override fun show()  {
        super.show()
        if (score > highScore) {
            highScore = score
            game.preferences.putInteger(HIGH_SCORE, score)
        }
        game.preferences.putInteger(BONUSES_COUNT, bonusCount)
        game.preferences.flush()

        addActors(Array.with(homeButton, restartButton, scoreLabel, highScoreLabel,
                bonusCountLabel))
    }

    private fun restart() {
        setScreen(ScreenManager.getGameScreen(
                game,
                bonusCount,
                highScore,
                isSoundOn,
                isAcceleratorOn,
                gameDifficulty,
                levelAssets
        ))
    }

    private fun home() {
        setScreen(ScreenManager.getStartScreen(
                game,
                bonusCount,
                highScore,
                isSoundOn,
                isAcceleratorOn,
                levelAssets
        ))
    }
}