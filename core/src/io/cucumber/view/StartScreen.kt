package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.buttons.ImageButton
import io.cucumber.model.buttons.SwitchButton
import io.cucumber.model.texture.Score
import io.cucumber.model.texture.TextureLevel
import io.cucumber.utils.constant.GameConstants.*

class StartScreen(
        game: Game,
        bonusCount: Int? = null,
        highScore: Int? = null,
        isSoundEnabled: Boolean? = null,
        textureLevel: TextureLevel? = null
) : BaseScreen(game, textureLevel) {

    private var isSoundOn: Boolean = isSoundEnabled ?: preferences.getBoolean(IS_SOUND_ENABLED)
    private val highScore = highScore ?: preferences.getInteger(HIGH_SCORE)
    private val bonusCount = bonusCount ?: preferences.getInteger(BONUSES_COUNT)
    private val highScoreActor: Score = Score(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 2 * SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, this.highScore, Score.ScoreItemAlign.CENTER)
    private val bonusesCountActor: Score = Score(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 4 * SCORE_HEIGHT, SCORE_WIDTH, SCORE_HEIGHT, this.bonusCount, Score.ScoreItemAlign.CENTER)

    private val startButton: Button = ImageButton(
            SCREEN_WIDTH / 2 - START_GAME_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - START_GAME_BUTTON_HEIGHT / 2,
            START_GAME_BUTTON_WIDTH,
            START_GAME_BUTTON_HEIGHT,
            this.textureLevel.playButton
    )
    private val chooseLevelButton: Button = ImageButton(
            SCREEN_WIDTH / 2 - CHOOSE_LEVEL_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - 4 * CHOOSE_LEVEL_BUTTON_HEIGHT / 2,
            CHOOSE_LEVEL_BUTTON_WIDTH,
            CHOOSE_LEVEL_BUTTON_HEIGHT,
            this.textureLevel.chooseButton
    )

    private val soundOffButton: SwitchButton = SwitchButton(
            SCREEN_WIDTH / 2 - SOUND_OFF_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - 7 * SOUND_OFF_BUTTON_HEIGHT / 2,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.textureLevel.soundOffButton,
            this.textureLevel.soundOnButton,
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

        startButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = GameScreen(this@StartScreen.game, this@StartScreen.bonusCount, this@StartScreen.highScore,
                        this@StartScreen.isSoundOn, this@StartScreen.textureLevel)
            }
        })
        chooseLevelButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = ChooseLevelScreen(this@StartScreen.game, this@StartScreen.bonusCount, this@StartScreen.highScore,
                        this@StartScreen.isSoundOn, this@StartScreen.textureLevel)
            }
        })
        soundOffButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                soundOff()
            }
        })

        addActors(Array.with(startButton, chooseLevelButton, soundOffButton, highScoreActor, bonusesCountActor))
    }

    private fun soundOff() {
        isSoundOn = !isSoundOn
        soundOffButton.setSwitcher(isSoundOn)
        preferences.putBoolean(IS_SOUND_ENABLED, isSoundOn)
        preferences.flush()
    }

    override fun screenDispose() {
        highScoreActor.dispose()
        bonusesCountActor.dispose()
    }
}