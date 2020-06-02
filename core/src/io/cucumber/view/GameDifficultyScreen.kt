package io.cucumber.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.base.GameDifficulty.HARD
import io.cucumber.model.base.GameDifficulty.NORMAL
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.button.SwitchImageButton
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*

class GameDifficultyScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private var isSoundOn: Boolean,
        private var isAcceleratorOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    private val homeButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 - 2 * HOME_BUTTON_HEIGHT,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.homeButton
    )
    private val normalDifficultyButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 - 2 * NORMAL_DIFFICULTY_BUTTON_WIDTH / 3,
            game.stage.camera.viewportHeight / 2 - NORMAL_DIFFICULTY_BUTTON_HEIGHT / 2,
            NORMAL_DIFFICULTY_BUTTON_WIDTH,
            NORMAL_DIFFICULTY_BUTTON_HEIGHT,
            this.levelAssets.normalDifficultyButton
    )
    private val hardDifficultyButton: SwitchImageButton<Boolean> = SwitchImageButton<Boolean>(
            game.stage.camera.viewportWidth / 2 + 2 * HARD_DIFFICULTY_BUTTON_WIDTH / 3,
            game.stage.camera.viewportHeight / 2 - HARD_DIFFICULTY_BUTTON_HEIGHT / 2,
            HARD_DIFFICULTY_BUTTON_WIDTH,
            HARD_DIFFICULTY_BUTTON_HEIGHT,
            highScore >= HARD_DIFFICULTY_SCORE,
            mapOf(
                    true to this.levelAssets.hardDifficultyButton,
                    false to this.levelAssets.cantHardDifficultyButton
            )
    )
    private val difficultyTitle: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 + game.stage.camera.viewportHeight / 6,
            GAME_DIFFICULTY_LABEL_TEXT,
            this.levelAssets.titleFont
    )
    private val hardLevelRequiredScoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2 + 2 * HARD_DIFFICULTY_BUTTON_WIDTH / 3,
            game.stage.camera.viewportHeight / 2 - HARD_DIFFICULTY_BUTTON_HEIGHT  - HARD_DIFFICULTY_BUTTON_HEIGHT / 32,
            String.format("%d/%d", highScore, HARD_DIFFICULTY_SCORE),
            this.levelAssets.labelFont
    )

    init {
        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                home()
            }
        })
        normalDifficultyButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                normalGame()
            }
        })
        hardDifficultyButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                hardGame()
            }
        })
        addBackgroundListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (Input.Keys.N == keycode) normalGame()
                if (Input.Keys.H == keycode) hardGame()
                if (Input.Keys.ESCAPE == keycode) home()
                return true
            }
        })
    }

    fun init(bonusCount: Int, highScore: Int, isSoundOn: Boolean,
             isAcceleratorOn: Boolean, levelAssets: LevelAssets): GameDifficultyScreen {
        this.isSoundOn = isSoundOn
        this.isAcceleratorOn = isAcceleratorOn
        this.highScore = highScore
        this.bonusCount = bonusCount
        this.levelAssets = levelAssets

        this.homeButton.setTexture(this.levelAssets.homeButton)
        this.normalDifficultyButton.setTexture(this.levelAssets.normalDifficultyButton)
        this.hardDifficultyButton.switcher = highScore >= HARD_DIFFICULTY_SCORE
        this.hardDifficultyButton.setTexture(mapOf(
                true to this.levelAssets.hardDifficultyButton,
                false to this.levelAssets.cantHardDifficultyButton
        ))

        this.difficultyTitle.setFont(this.levelAssets.titleFont)
        this.hardLevelRequiredScoreLabel.setFont(this.levelAssets.labelFont)
        this.hardLevelRequiredScoreLabel.setText(String.format("%d/%d", highScore, HARD_DIFFICULTY_SCORE))

        return this
    }

    override fun show() {
        super.show()

        addActors(Array.with(homeButton, normalDifficultyButton, hardDifficultyButton,
                difficultyTitle))

        if (!hardDifficultyButton.switcher) {
            addActor(hardLevelRequiredScoreLabel)
        }
    }

    private fun normalGame() {
        this.levelAssets.backgroundSound.pause()
        setScreen(ScreenManager.getGameScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.isAcceleratorOn,
                NORMAL,
                this.levelAssets
        ))
    }

    private fun hardGame() {
        if (!hardDifficultyButton.switcher) {
            return
        }
        this.levelAssets.backgroundSound.pause()
        setScreen(ScreenManager.getGameScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.isAcceleratorOn,
                HARD,
                this.levelAssets
        ))
    }

    private fun home() {
        setScreen(ScreenManager.getStartScreen(
                game,
                bonusCount,
                highScore,
                isSoundOn,
                isAcceleratorOn,
                levelAssets
        ))
    }
}