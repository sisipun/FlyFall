package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.PreferenceConstants.BONUSES_COUNT
import io.cucumber.constant.PreferenceConstants.HIGH_SCORE
import io.cucumber.constant.ScoreConstants.SCORE_HEIGHT
import io.cucumber.constant.ScoreConstants.SCORE_WIDTH
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.utils.NumbersHelper

class GameOverScreen(game: Game, score: Int, bonusesCount: Int) : BaseScreen(game) {

    private var highScore: Int = preferences.getInteger(HIGH_SCORE)
    private var scoreTextures: List<Texture> = NumbersHelper.getTextures(score)
    private var highScoreTextures: List<Texture> = NumbersHelper.getTextures(highScore)
    private var bonusesCountTextures: List<Texture> = NumbersHelper.getTextures(bonusesCount)


    init {
        if (score > highScore) {
            highScore = score
            preferences.putInteger(HIGH_SCORE, score)
        }
        preferences.putInteger(BONUSES_COUNT, bonusesCount)
        preferences.flush()
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
    }

    override fun handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) game.screen = GameScreen(game)
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) game.screen = SettingScreen(game)
    }

}