package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.button.SwitchImageButton
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.LevelManager
import io.cucumber.service.manager.ScreenManager
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
    private val acceleratorAvailable: Boolean
    private var isSoundOn: Boolean = isSoundOn ?: !game.preferences.getBoolean(IS_SOUND_DISABLED)
    private var isAcceleratorOn: Boolean = isAcceleratorOn
            ?: game.preferences.getBoolean(IS_ACCELERATION_ENABLED)
    private var highScore = highScore ?: game.preferences.getInteger(HIGH_SCORE)
    private var bonusCount = bonusCount ?: game.preferences.getInteger(BONUSES_COUNT)

    // Actors
    private val startButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 - 2 * START_GAME_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2,
            START_GAME_BUTTON_WIDTH,
            START_GAME_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val chooseLevelButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2,
            CHOOSE_LEVEL_BUTTON_WIDTH,
            CHOOSE_LEVEL_BUTTON_HEIGHT,
            this.levelAssets.chooseButton
    )
    private val soundOffButton: SwitchImageButton<Boolean> = SwitchImageButton<Boolean>(
            game.stage.camera.viewportWidth / 2 + 2 * CHOOSE_LEVEL_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.isSoundOn,
            mapOf(false to this.levelAssets.soundOffButton, true to this.levelAssets.soundOnButton)
    )
    private val controlButton: SwitchImageButton<Boolean> = SwitchImageButton<Boolean>(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 - game.stage.camera.viewportHeight / 4,
            SOUND_OFF_BUTTON_WIDTH,
            SOUND_OFF_BUTTON_HEIGHT,
            this.isAcceleratorOn,
            mapOf(false to this.levelAssets.tapButton, true to this.levelAssets.accButton)
    )
    private val title: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 + game.stage.camera.viewportHeight / 4,
            TITLE_LABEL_TEXT,
            this.levelAssets.titleFont
    )
    private val highScoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            4 * SCORE_HEIGHT,
            HIGH_SCORE_LABEL_TEXT + this.highScore.toString(),
            this.levelAssets.labelFont
    )
    private val bonusCountLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            2 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT + this.bonusCount.toString(),
            this.levelAssets.labelFont
    )

    init {
        this.startButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                play()
            }
        })
        this.chooseLevelButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeLevel()
            }
        })
        this.soundOffButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                changeSound()
            }
        })
        addBackgroundListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (Input.Keys.ENTER == keycode) play()
                if (Input.Keys.R == keycode) changeLevel()
                if (Input.Keys.M == keycode) changeSound()
                return true
            }
        })

        this.acceleratorAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)
        if (this.acceleratorAvailable) {
            this.controlButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    changeControl()
                }
            })
        }
        if (this.isSoundOn) {
            this.levelAssets.backgroundSound.loop()
        }

        val titleActions = Actions.forever(Actions.parallel(
                Actions.sequence(
                        Actions.moveTo(title.x + game.stage.camera.viewportWidth / 32, title.y + game.stage.camera.viewportHeight / 32, 1F),
                        Actions.moveTo(title.x - game.stage.camera.viewportWidth / 32, title.y - game.stage.camera.viewportHeight / 32, 1F)
                )
        ))
        title.addAction(titleActions)
    }

    fun init(bonusCount: Int?, highScore: Int?, isSoundOn: Boolean?,
             isAcceleratorOn: Boolean?, levelAssets: LevelAssets?): StartScreen {
        this.isSoundOn = isSoundOn ?: !game.preferences.getBoolean(IS_SOUND_DISABLED)
        this.isAcceleratorOn = isAcceleratorOn
                ?: game.preferences.getBoolean(IS_ACCELERATION_ENABLED)
        this.highScore = highScore ?: game.preferences.getInteger(HIGH_SCORE)
        this.bonusCount = bonusCount ?: game.preferences.getInteger(BONUSES_COUNT)
        this.levelAssets = levelAssets
                ?: LevelManager.get(game.preferences.getInteger(TEXTURE_LEVEL))
        this.levelAssets.backgroundSound.resume()

        this.startButton.setTexture(this.levelAssets.playButton)
        this.chooseLevelButton.setTexture(this.levelAssets.chooseButton)
        this.soundOffButton.setTexture(mapOf(false to this.levelAssets.soundOffButton, true to this.levelAssets.soundOnButton))
        this.controlButton.setTexture(mapOf(false to this.levelAssets.tapButton, true to this.levelAssets.accButton))

        this.title.setFont(this.levelAssets.titleFont)
        this.highScoreLabel.setFont(this.levelAssets.labelFont)
        this.highScoreLabel.setText(HIGH_SCORE_LABEL_TEXT + this.highScore.toString())
        this.bonusCountLabel.setFont(this.levelAssets.labelFont)
        this.bonusCountLabel.setText(BONUS_LABEL_TEXT + this.bonusCount.toString())

        return this
    }

    override fun show() {
        super.show()

        soundOffButton.switcher = this.isSoundOn
        controlButton.switcher = this.isAcceleratorOn

        addActors(Array.with(startButton, chooseLevelButton, soundOffButton, title, highScoreLabel,
                bonusCountLabel))

        if (acceleratorAvailable) {
            addActor(controlButton)
        }
    }

    private fun play() {
        setScreen(ScreenManager.getGameDifficultyScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.isAcceleratorOn,
                this.levelAssets
        ))
    }

    private fun changeLevel() {
        setScreen(ScreenManager.getChooseLevelScreen(
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
        if (this.isSoundOn) {
            this.levelAssets.backgroundSound.loop()
        } else {
            this.levelAssets.backgroundSound.stop()
        }
    }

    private fun changeControl() {
        this.isAcceleratorOn = !this.isAcceleratorOn
        controlButton.switcher = this.isAcceleratorOn
        game.preferences.putBoolean(IS_ACCELERATION_ENABLED, this.isAcceleratorOn)
        game.preferences.flush()
    }
}