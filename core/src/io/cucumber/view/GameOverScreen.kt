package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.buttons.ImageButton
import io.cucumber.model.texture.Score
import io.cucumber.model.texture.TextureLevel
import io.cucumber.utils.constant.GameConstants.*

class GameOverScreen(
        game: Game, score: Int,
        private var bonusCount: Int,
        private var highScore: Int,
        private val isSoundOn: Boolean,
        textureLevel: TextureLevel
) : BaseScreen(game, textureLevel) {

    private val highScoreActor: Score
    private val scoreActor: Score = Score(0F, SCREEN_HEIGHT / 2 + SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, score)
    private val bonusesCountActor: Score = Score(0F, SCREEN_HEIGHT / 2 - SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, bonusCount)

    private val homeButton: Button = ImageButton(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.textureLevel.notButton
    )
    private val restartButton: Button = ImageButton(
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
        preferences.putInteger(BONUSES_COUNT, bonusCount)
        preferences.flush()
        highScoreActor = Score(0F, SCREEN_HEIGHT / 2, SCORE_WIDTH, SCORE_HEIGHT, highScore)

        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = StartScreen(this@GameOverScreen.game, this@GameOverScreen.bonusCount, this@GameOverScreen.highScore,
                        this@GameOverScreen.isSoundOn, this@GameOverScreen.textureLevel)
            }
        })
        restartButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = GameScreen(this@GameOverScreen.game, this@GameOverScreen.bonusCount, this@GameOverScreen.highScore,
                        this@GameOverScreen.isSoundOn, this@GameOverScreen.textureLevel)
            }
        })

        addActors(Array.with(homeButton, restartButton, scoreActor, highScoreActor, bonusesCountActor))
    }

    override fun screenDispose() {
        scoreActor.dispose()
        highScoreActor.dispose()
        bonusesCountActor.dispose()
    }
}