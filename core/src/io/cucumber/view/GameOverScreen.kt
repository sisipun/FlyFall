package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.button.ImageButton
import io.cucumber.model.level.LevelAssets
import io.cucumber.model.component.Score
import io.cucumber.utils.constant.GameConstants.*

class GameOverScreen(
        game: Game,
        private val score: Int,
        private val bonusCount: Int,
        private var highScore: Int,
        private val isSoundOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Actors
    private val highScoreActor: Score
    private val scoreActor: Score = Score(
            0F,
            SCREEN_HEIGHT / 2 + SCORE_HEIGHT,
            SCORE_WIDTH,
            SCORE_HEIGHT,
            this.score
    )
    private val bonusesCountActor: Score = Score(
            0F,
            SCREEN_HEIGHT / 2 - SCORE_HEIGHT,
            SCORE_WIDTH,
            SCORE_HEIGHT,
            this.bonusCount
    )
    private val homeButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.notButton
    )
    private val restartButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )

    init {
        if (score > highScore) {
            highScore = score
            preferences.putInteger(HIGH_SCORE, score)
        }
        preferences.putInteger(BONUSES_COUNT, bonusCount)
        preferences.flush()

        highScoreActor = Score(
                0F,
                SCREEN_HEIGHT / 2,
                SCORE_WIDTH,
                SCORE_HEIGHT,
                highScore
        )

        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = StartScreen(
                        this@GameOverScreen.game,
                        this@GameOverScreen.bonusCount,
                        this@GameOverScreen.highScore,
                        this@GameOverScreen.isSoundOn,
                        this@GameOverScreen.levelAssets
                )
            }
        })
        restartButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = GameScreen(
                        this@GameOverScreen.game,
                        this@GameOverScreen.bonusCount,
                        this@GameOverScreen.highScore,
                        this@GameOverScreen.isSoundOn,
                        this@GameOverScreen.levelAssets
                )
            }
        })

        addActors(Array.with(homeButton, restartButton, scoreActor, highScoreActor, bonusesCountActor))
    }
}