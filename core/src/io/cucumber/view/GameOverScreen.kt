package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.ButtonConstants.HOME_BUTTON_HEIGHT
import io.cucumber.constant.ButtonConstants.HOME_BUTTON_WIDTH
import io.cucumber.constant.PreferenceConstants.BONUSES_COUNT
import io.cucumber.constant.PreferenceConstants.HIGH_SCORE
import io.cucumber.constant.ScreenConstants.SCORE_HEIGHT
import io.cucumber.constant.ScreenConstants.SCORE_WIDTH
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH
import io.cucumber.model.base.Button
import io.cucumber.model.texture.TextureLevel
import io.cucumber.utils.NumbersHelper

class GameOverScreen(
    game: Game, score: Int,
    bonusesCount: Int,
    private val textureLevel: TextureLevel
) : BaseScreen(game) {

    private var highScore: Int = preferences.getInteger(HIGH_SCORE)
    private var scoreTextures: List<Texture> = NumbersHelper.getTextures(score)
    private var highScoreTextures: List<Texture>
    private var bonusesCountTextures: List<Texture> = NumbersHelper.getTextures(bonusesCount)

    private val homeButton: Button = Button(
        SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
        SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
        HOME_BUTTON_WIDTH,
        HOME_BUTTON_HEIGHT,
        "wall.png"
    )
    private val restartButton: Button = Button(
        SCREEN_WIDTH / 2 - HOME_BUTTON_WIDTH,
        SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
        HOME_BUTTON_WIDTH,
        HOME_BUTTON_HEIGHT,
        "wall.png"
    )


    init {
        if (score > highScore) {
            highScore = score
            preferences.putInteger(HIGH_SCORE, score)
        }
        preferences.putInteger(BONUSES_COUNT, bonusesCount)
        preferences.flush()
        highScoreTextures = NumbersHelper.getTextures(highScore)
    }

    override fun render() {
        scoreTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                (index + 1) * SCORE_WIDTH,
                SCREEN_HEIGHT / 2 + SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
        highScoreTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                (index + 1) * SCORE_WIDTH,
                SCREEN_HEIGHT / 2,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
        bonusesCountTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                (index + 1) * SCORE_WIDTH,
                SCREEN_HEIGHT / 2 - SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
        batch.draw(
            homeButton.texture,
            homeButton.bound.x,
            homeButton.bound.y,
            homeButton.bound.width,
            homeButton.bound.height
        )
        batch.draw(
            restartButton.texture,
            restartButton.bound.x,
            restartButton.bound.y,
            restartButton.bound.width,
            restartButton.bound.height
        )
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && homeButton.isTouched(getTouchPosition())) game.screen = StartScreen(game)
        if (Gdx.input.justTouched() && restartButton.isTouched(getTouchPosition())) game.screen = GameScreen(game, textureLevel)
    }

    override fun screenDispose() {
        scoreTextures.forEach { it.dispose() }
        highScoreTextures.forEach { it.dispose() }
        bonusesCountTextures.forEach { it.dispose() }
    }

}