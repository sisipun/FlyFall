package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.model.button.ImageButton
import io.cucumber.model.button.SwitchImageButton
import io.cucumber.model.texture.LevelAssets
import io.cucumber.model.texture.Score
import io.cucumber.model.texture.Score.ScoreItemAlign.CENTER
import io.cucumber.utils.constant.GameConstants.*

class StartScreen(
        game: Game,
        bonusCount: Int? = null,
        highScore: Int? = null,
        isSoundEnabled: Boolean? = null,
        levelAssets: LevelAssets? = null
) : BaseScreen(game, levelAssets) {

    // Preferences
    private var isSoundOn: Boolean = isSoundEnabled ?: !preferences.getBoolean(IS_SOUND_DISABLED)
    private val highScore = highScore ?: preferences.getInteger(HIGH_SCORE)
    private val bonusCount = bonusCount ?: preferences.getInteger(BONUSES_COUNT)

    // Actors
    private val highScoreActor: Score = Score(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT - 2 * SCORE_HEIGHT,
            SCORE_WIDTH,
            SCORE_HEIGHT,
            this.highScore,
            CENTER
    )
    private val bonusesCountActor: Score = Score(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT - 4 * SCORE_HEIGHT,
            SCORE_WIDTH,
            SCORE_HEIGHT,
            this.bonusCount,
            CENTER
    )
    private val startButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - START_GAME_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - START_GAME_BUTTON_HEIGHT / 2,
            START_GAME_BUTTON_WIDTH,
            START_GAME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val chooseLevelButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - CHOOSE_LEVEL_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - 4 * CHOOSE_LEVEL_BUTTON_HEIGHT / 2,
            CHOOSE_LEVEL_BUTTON_WIDTH,
            CHOOSE_LEVEL_BUTTON_HEIGHT,
            this.levelAssets.chooseButton
    )
    private val soundOffButton: SwitchImageButton = SwitchImageButton(
            SCREEN_WIDTH / 2 - SOUND_OFF_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - 7 * SOUND_OFF_BUTTON_HEIGHT / 2,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.levelAssets.soundOffButton,
            this.levelAssets.soundOnButton,
            isSoundOn
    )

    init {
        startButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = GameScreen(
                        this@StartScreen.game,
                        this@StartScreen.bonusCount,
                        this@StartScreen.highScore,
                        this@StartScreen.isSoundOn,
                        this@StartScreen.levelAssets
                )
            }
        })
        chooseLevelButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = ChooseLevelScreen(
                        this@StartScreen.game,
                        this@StartScreen.bonusCount,
                        this@StartScreen.highScore,
                        this@StartScreen.isSoundOn,
                        this@StartScreen.levelAssets
                )
            }
        })
        soundOffButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                isSoundOn = !isSoundOn
                soundOffButton.setSwitcher(isSoundOn)
                preferences.putBoolean(IS_SOUND_DISABLED, isSoundOn)
                preferences.flush()
            }
        })

        addActors(Array.with(startButton, chooseLevelButton, soundOffButton, highScoreActor, bonusesCountActor))
    }
}