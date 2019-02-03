package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import io.cucumber.constant.GameConstants.*
import io.cucumber.model.base.Button
import io.cucumber.model.texture.TextureLevelPack
import io.cucumber.model.texture.TextureLevelPack.COMMON
import io.cucumber.utils.NumbersHelper

class StartScreen(game: Game) : BaseScreen(game) {

    private var textureLevel: TextureLevelPack

    private var isSoundEnabled: Boolean = preferences.getBoolean(IS_SOUND_ENABLED)
    private var highScoreTextures: List<Texture> = NumbersHelper.getTextures(preferences.getInteger(HIGH_SCORE))
    private var bonusesCountTextures: List<Texture> = NumbersHelper.getTextures(preferences.getInteger(BONUSES_COUNT))

    private val startButton: Button = Button(
        SCREEN_WIDTH / 2 - START_GAME_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - START_GAME_BUTTON_HEIGHT / 2,
        START_GAME_BUTTON_WIDTH,
        START_GAME_BUTTON_HEIGHT,
        "wall.png"
    )
    private val chooseLevelButton: Button = Button(
        SCREEN_WIDTH / 2 - CHOOSE_LEVEL_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - 4 * CHOOSE_LEVEL_BUTTON_HEIGHT / 2,
        CHOOSE_LEVEL_BUTTON_WIDTH,
        CHOOSE_LEVEL_BUTTON_HEIGHT,
        "wall.png"
    )

    private val soundOffButton: Button = Button(
        SCREEN_WIDTH / 2 - SOUND_OFF_BUTTON_WIDTH / 2,
        SCREEN_HEIGHT / 2 - 7 * SOUND_OFF_BUTTON_HEIGHT / 2,
        SOUND_OFF_BUTTON_WIDTH,
        SOUND_OFF_BUTTON_HEIGHT,
        "wall.png"
    )


    init {
        if (!preferences.contains(TEXTURE_LEVEL)) {
            preferences.putInteger(TEXTURE_LEVEL, COMMON.id)
            preferences.flush()
        }
        if (!preferences.contains(IS_SOUND_ENABLED)) {
            preferences.putBoolean(IS_SOUND_ENABLED, true)
            preferences.flush()
        }
        textureLevel = TextureLevelPack.getById(preferences.getInteger(TEXTURE_LEVEL))
    }

    override fun render() {
        batch.draw(
            textureLevel.value.background,
            0F,
            0F,
            SCREEN_WIDTH,
            SCREEN_HEIGHT
        )
        batch.draw(
            startButton.texture,
            startButton.bound.x,
            startButton.bound.y,
            startButton.bound.width,
            startButton.bound.height
        )
        batch.draw(
            chooseLevelButton.texture,
            chooseLevelButton.bound.x,
            chooseLevelButton.bound.y,
            chooseLevelButton.bound.width,
            chooseLevelButton.bound.height
        )
        batch.draw(
            soundOffButton.texture,
            soundOffButton.bound.x,
            soundOffButton.bound.y,
            soundOffButton.bound.width,
            soundOffButton.bound.height
        )
        highScoreTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                SCREEN_WIDTH / 2 + (index - highScoreTextures.size / 2) * SCORE_WIDTH,
                SCREEN_HEIGHT - 2 * SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
        bonusesCountTextures.forEachIndexed { index, texture ->
            batch.draw(
                texture,
                SCREEN_WIDTH / 2 + (index - bonusesCountTextures.size / 2) * SCORE_WIDTH,
                SCREEN_HEIGHT - 4 * SCORE_HEIGHT,
                SCORE_WIDTH,
                SCORE_HEIGHT
            )
        }
    }

    override fun handleInput() {
        if (Gdx.input.justTouched() && startButton.isTouched(getTouchPosition())) game.screen = GameScreen(game, textureLevel.value)
        if (Gdx.input.justTouched() && chooseLevelButton.isTouched(getTouchPosition())) game.screen = ChooseLevelScreen(game)
        if (Gdx.input.justTouched() && soundOffButton.isTouched(getTouchPosition())) soundOff()
    }

    override fun screenDispose() {
        highScoreTextures.forEach { it.dispose() }
        bonusesCountTextures.forEach { it.dispose() }
    }

    private fun soundOff() {
        isSoundEnabled = !isSoundEnabled
        preferences.putBoolean(IS_SOUND_ENABLED, isSoundEnabled)
        preferences.flush()
    }

}