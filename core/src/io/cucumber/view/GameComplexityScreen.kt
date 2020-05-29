package io.cucumber.view

import com.badlogic.gdx.Input
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
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.view.GameScreen.GameComplexity.HARD
import io.cucumber.view.GameScreen.GameComplexity.NORMAL

class GameComplexityScreen(
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
    private val normalComplexityButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 - 2 * NORMAL_COMPLEXITY_BUTTON_WIDTH / 3,
            game.stage.camera.viewportHeight / 2 - NORMAL_COMPLEXITY_BUTTON_HEIGHT / 2,
            NORMAL_COMPLEXITY_BUTTON_WIDTH,
            NORMAL_COMPLEXITY_BUTTON_HEIGHT,
            this.levelAssets.normalComplexityButton
    )
    private val hardComplexityButton: SwitchImageButton<Boolean> = SwitchImageButton<Boolean>(
            game.stage.camera.viewportWidth / 2 + 2 * HARD_COMPLEXITY_BUTTON_WIDTH / 3,
            game.stage.camera.viewportHeight / 2 - HARD_COMPLEXITY_BUTTON_HEIGHT / 2,
            HARD_COMPLEXITY_BUTTON_WIDTH,
            HARD_COMPLEXITY_BUTTON_HEIGHT,
            highScore >= HARD_COMPLEXITY_SCORE,
            mapOf(
                    true to this.levelAssets.hardComplexityButton,
                    false to this.levelAssets.cantHardComplexityButton
            )
    )
    private val complexityTitle: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 + game.stage.camera.viewportHeight / 6,
            GAME_COMPLEXITY_LABEL_TEXT,
            FontManager.get(TITLE)
    )
    private val hardLevelRequiredScoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2 + 2 * HARD_COMPLEXITY_BUTTON_WIDTH / 3,
            game.stage.camera.viewportHeight / 2 - HARD_COMPLEXITY_BUTTON_HEIGHT  - HARD_COMPLEXITY_BUTTON_HEIGHT / 32,
            String.format("%d/%d", highScore, HARD_COMPLEXITY_SCORE),
            FontManager.get(LABEL)
    )

    init {
        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                home()
            }
        })
        normalComplexityButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                normalGame()
            }
        })
        hardComplexityButton.addListener(object : ClickListener() {
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
             isAcceleratorOn: Boolean, levelAssets: LevelAssets): GameComplexityScreen {
        this.isSoundOn = isSoundOn
        this.isAcceleratorOn = isAcceleratorOn
        this.highScore = highScore
        this.bonusCount = bonusCount
        this.levelAssets = levelAssets

        this.homeButton.setTexture(this.levelAssets.homeButton)
        this.normalComplexityButton.setTexture(this.levelAssets.normalComplexityButton)
        this.hardComplexityButton.switcher = highScore >= HARD_COMPLEXITY_SCORE
        this.hardComplexityButton.setTexture(mapOf(
                true to this.levelAssets.hardComplexityButton,
                false to this.levelAssets.cantHardComplexityButton
        ))

        this.hardLevelRequiredScoreLabel.setText(String.format("%d/%d", highScore, HARD_COMPLEXITY_SCORE))

        return this
    }

    override fun show() {
        super.show()

        addActors(Array.with(homeButton, normalComplexityButton, hardComplexityButton,
                complexityTitle))

        if (!hardComplexityButton.switcher) {
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
        if (!hardComplexityButton.switcher) {
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