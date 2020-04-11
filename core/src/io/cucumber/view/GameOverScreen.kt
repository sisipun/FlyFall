package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import io.cucumber.model.base.Button
import io.cucumber.model.texture.Score
import io.cucumber.model.texture.TextureLevel
import io.cucumber.utils.constant.GameConstants.*

class GameOverScreen(
        game: Game, score: Int,
        private var bonusesCount: Int,
        private var highScore: Int,
        private val isSoundEnabled: Boolean,
        textureLevel: TextureLevel
) : BaseScreen(game, textureLevel) {

    private val background: Image

    private val highScoreActor: Score
    private val scoreActor: Score = Score(0F, SCREEN_HEIGHT / 2 + SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, score)
    private val bonusesCountActor: Score = Score(0F, SCREEN_HEIGHT / 2 - SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, bonusesCount)

    private val homeButton: Button = Button(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.textureLevel.notButton
    )
    private val restartButton: Button = Button(
            SCREEN_WIDTH / 2 - HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.textureLevel.playButton
    )

    init {
        if (score > highScore) {
            highScore = score
            preferences.putInteger(HIGH_SCORE, score)
        }
        preferences.putInteger(BONUSES_COUNT, bonusesCount)
        preferences.flush()
        highScoreActor = Score(0F, SCREEN_HEIGHT / 2, SCORE_WIDTH, SCORE_HEIGHT, highScore)

        this.background = Image(textureLevel.background)
        this.background.setBounds(0F, 0F, SCORE_WIDTH, SCORE_HEIGHT)

        addActors(Array.with(homeButton, restartButton, scoreActor, highScoreActor, bonusesCountActor))
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game, bonusesCount, highScore, isSoundEnabled, textureLevel)
        if (Gdx.input.justTouched() && restartButton.isTouched(getTouchPosition())) game.screen = GameScreen(game, bonusesCount, highScore, isSoundEnabled, textureLevel)
    }

    override fun screenDispose() {
        scoreActor.dispose()
        highScoreActor.dispose()
        bonusesCountActor.dispose()
    }
}