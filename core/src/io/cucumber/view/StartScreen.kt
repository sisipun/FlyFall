package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import io.cucumber.model.base.Button
import io.cucumber.model.buttons.SwitchButton
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.manager.TextureLevelManager
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.utils.helper.NumbersHelper

class StartScreen(
        game: Game,
        bonusCount: Int? = null,
        highScore: Int? = null,
        isSoundEnabled: Boolean? = null,
        textureLevel: TextureLevel? = null
) : BaseScreen(game) {

    private var textureLevel: TextureLevel

    private var isSoundOn: Boolean = isSoundEnabled ?: preferences.getBoolean(IS_SOUND_ENABLED)
    private val highScore = highScore ?: preferences.getInteger(HIGH_SCORE)
    private var highScoreTextures: List<Texture> = NumbersHelper.getTextures(this.highScore)
    private val bonusCount = bonusCount ?: preferences.getInteger(BONUSES_COUNT)
    private var bonusesCountTextures: List<Texture> = NumbersHelper.getTextures(this.bonusCount)

    private val startButton: Button = Button(
            SCREEN_WIDTH / 2 - START_GAME_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - START_GAME_BUTTON_HEIGHT / 2,
            START_GAME_BUTTON_WIDTH,
            START_GAME_BUTTON_HEIGHT,
            Texture("play_button.png")
    )
    private val chooseLevelButton: Button = Button(
            SCREEN_WIDTH / 2 - CHOOSE_LEVEL_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - 4 * CHOOSE_LEVEL_BUTTON_HEIGHT / 2,
            CHOOSE_LEVEL_BUTTON_WIDTH,
            CHOOSE_LEVEL_BUTTON_HEIGHT,
            Texture("choose_button.png")
    )

    private val soundOffButton: SwitchButton = SwitchButton(
            SCREEN_WIDTH / 2 - SOUND_OFF_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - 7 * SOUND_OFF_BUTTON_HEIGHT / 2,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            Texture("sound_off_button.png"),
            Texture("sound_on_button.png"),
            isSoundOn
    )


    init {
        if (!preferences.contains(TEXTURE_LEVEL)) {
            preferences.putInteger(TEXTURE_LEVEL, 0)
            preferences.flush()
        }
        if (!preferences.contains(IS_SOUND_ENABLED)) {
            preferences.putBoolean(IS_SOUND_ENABLED, true)
            preferences.flush()
        }
        this.textureLevel = textureLevel
                ?: TextureLevelManager.get(preferences.getInteger(TEXTURE_LEVEL))
    }

    override fun render() {
        batch.draw(
                textureLevel.background,
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
        if (Gdx.input.justTouched() && startButton.isTouched(getTouchPosition())) game.screen = GameScreen(game, bonusCount, highScore, isSoundOn, textureLevel)
        if (Gdx.input.justTouched() && chooseLevelButton.isTouched(getTouchPosition())) game.screen = ChooseLevelScreen(game, bonusCount, highScore, isSoundOn, textureLevel)
        if (Gdx.input.justTouched() && soundOffButton.isTouched(getTouchPosition())) soundOff()
    }

    private fun soundOff() {
        isSoundOn = !isSoundOn
        soundOffButton.setSwitcher(isSoundOn)
        preferences.putBoolean(IS_SOUND_ENABLED, isSoundOn)
        preferences.flush()
    }

    override fun screenDispose() {
        highScoreTextures.forEach { it.dispose() }
        bonusesCountTextures.forEach { it.dispose() }
        startButton.dispose()
        chooseLevelButton.dispose()
        chooseLevelButton.dispose()
    }
}