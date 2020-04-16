package io.cucumber.view

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.button.ImageButton
import io.cucumber.model.button.SwitchImageButton
import io.cucumber.model.component.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.LABEL
import io.cucumber.service.manager.FontManager.FontType.TITLE
import io.cucumber.utils.constant.GameConstants.*


class StartScreen(
        game: Game,
        bonusCount: Int? = null,
        highScore: Int? = null,
        isSoundOn: Boolean? = null,
        levelAssets: LevelAssets? = null
) : BaseScreen(game, levelAssets) {

    // Preferences
    private var isSoundOn: Boolean = isSoundOn ?: !game.preferences.getBoolean(IS_SOUND_DISABLED)
    private val highScore = highScore ?: game.preferences.getInteger(HIGH_SCORE)
    private val bonusCount = bonusCount ?: game.preferences.getInteger(BONUSES_COUNT)

    // Actors
    private val highScoreActor: TextLabel = TextLabel(
            SCREEN_WIDTH / 2 + SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64,
            4 * SCORE_HEIGHT,
            this.highScore.toString(),
            FontManager.get(LABEL)
    )
    private val bonusCountActor: TextLabel = TextLabel(
            SCREEN_WIDTH / 2  + SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64,
            2 * SCORE_HEIGHT,
            this.bonusCount.toString(),
            FontManager.get(LABEL)
    )
    private val startButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - START_GAME_BUTTON_WIDTH / 2 - 2 * START_GAME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - START_GAME_BUTTON_HEIGHT / 2,
            START_GAME_BUTTON_WIDTH,
            START_GAME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val chooseLevelButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - SOUND_OFF_BUTTON_WIDTH / 2,
            SCREEN_HEIGHT / 2 - CHOOSE_LEVEL_BUTTON_HEIGHT / 2,
            CHOOSE_LEVEL_BUTTON_WIDTH,
            CHOOSE_LEVEL_BUTTON_HEIGHT,
            this.levelAssets.chooseButton
    )
    private val soundOffButton: SwitchImageButton = SwitchImageButton(
            SCREEN_WIDTH / 2 - CHOOSE_LEVEL_BUTTON_WIDTH / 2 + 2 * CHOOSE_LEVEL_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - SOUND_OFF_BUTTON_HEIGHT / 2,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.levelAssets.soundOffButton,
            this.levelAssets.soundOnButton,
            this.isSoundOn
    )
    private val title: TextLabel = TextLabel(
            SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8 - SCREEN_WIDTH / 16,
            SCREEN_HEIGHT / 2 + SCREEN_HEIGHT / 4,
            TITLE_LABEL_TEXT,
            FontManager.get(TITLE)
    )
    private val highScoreLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 64,
            4 * SCORE_HEIGHT,
            HIGH_SCORE_LABEL_TEXT,
            FontManager.get(LABEL)
    )
    private val bonusCountLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8  + SCREEN_WIDTH / 16 ,
            2 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT,
            FontManager.get(LABEL)
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
                this@StartScreen.isSoundOn = !this@StartScreen.isSoundOn
                soundOffButton.setSwitcher(this@StartScreen.isSoundOn)
                game.preferences.putBoolean(IS_SOUND_DISABLED, !this@StartScreen.isSoundOn)
                game.preferences.flush()
            }
        })

        addActors(Array.with(startButton, chooseLevelButton, soundOffButton, highScoreActor,
                bonusCountActor, title, highScoreLabel, bonusCountLabel))
    }
}