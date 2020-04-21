package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.button.SwitchImageButton
import io.cucumber.model.component.text.TextLabel
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
        isAcceleratorOn: Boolean? = null,
        levelAssets: LevelAssets? = null
) : BaseScreen(game, levelAssets) {

    // Preferences
    private var isSoundOn: Boolean = isSoundOn ?: !game.preferences.getBoolean(IS_SOUND_DISABLED)
    private var isAcceleratorOn: Boolean = isAcceleratorOn ?: game.preferences.getBoolean(IS_ACCELERATION_ENABLED)
    private val highScore = highScore ?: game.preferences.getInteger(HIGH_SCORE)
    private val bonusCount = bonusCount ?: game.preferences.getInteger(BONUSES_COUNT)

    // Actors
    private val startButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - 2 * START_GAME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2,
            START_GAME_BUTTON_WIDTH,
            START_GAME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val chooseLevelButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2,
            CHOOSE_LEVEL_BUTTON_WIDTH,
            CHOOSE_LEVEL_BUTTON_HEIGHT,
            this.levelAssets.chooseButton
    )
    private val soundOffButton: SwitchImageButton = SwitchImageButton(
            SCREEN_WIDTH / 2 + 2 * CHOOSE_LEVEL_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.levelAssets.soundOffButton,
            this.levelAssets.soundOnButton,
            this.isSoundOn
    )
    private val controlButton: SwitchImageButton = SwitchImageButton(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.levelAssets.soundOffButton,
            this.levelAssets.soundOnButton,
            this.isAcceleratorOn
    )
    private val title: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2 + SCREEN_HEIGHT / 4,
            TITLE_LABEL_TEXT,
            FontManager.get(TITLE)
    )
    private val highScoreLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            4 * SCORE_HEIGHT,
            HIGH_SCORE_LABEL_TEXT + this.highScore.toString(),
            FontManager.get(LABEL)
    )
    private val bonusCountLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            2 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT + this.bonusCount.toString(),
            FontManager.get(LABEL)
    )

    override fun show() {
        super.show()
        startButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                play()
            }
        })
        chooseLevelButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeLevel()
            }
        })
        soundOffButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeSound()
            }
        })
        addBackgroundListener(object: InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (ENTER == keycode) play()
                if (R == keycode) changeLevel()
                if (M == keycode) changeSound()
                return true
            }
        })

        addActors(Array.with(startButton, chooseLevelButton, soundOffButton, title, highScoreLabel,
                bonusCountLabel))

        // TODO maybe optimize
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            addActor(controlButton)
            controlButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    changeControl()
                }
            })
        }
    }

    private fun play() {
        setScreen(GameScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.isAcceleratorOn,
                this.levelAssets
        ))
    }

    private fun changeLevel() {
        setScreen(ChooseLevelScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.isAcceleratorOn,
                this.levelAssets
        ))
    }

    private fun changeSound() {
        this.isSoundOn = !this.isSoundOn
        soundOffButton.setSwitcher(this.isSoundOn)
        game.preferences.putBoolean(IS_SOUND_DISABLED, !this.isSoundOn)
        game.preferences.flush()
    }

    private fun changeControl() {
        this.isAcceleratorOn = !this.isAcceleratorOn
        controlButton.setSwitcher(this.isAcceleratorOn)
        game.preferences.putBoolean(IS_ACCELERATION_ENABLED, this.isAcceleratorOn)
        game.preferences.flush()
    }
}