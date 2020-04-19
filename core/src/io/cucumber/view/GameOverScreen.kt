package io.cucumber.view

import com.badlogic.gdx.Input.Keys.ENTER
import com.badlogic.gdx.Input.Keys.ESCAPE
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.LABEL
import io.cucumber.utils.constant.GameConstants.*

class GameOverScreen(
        game: Game,
        private val score: Int,
        private val bonusCount: Int,
        private var highScore: Int,
        private val isSoundOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Actors
    private val homeButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.notButton
    )
    private val restartButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - RESTART_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2,
            RESTART_BUTTON_WIDTH,
            RESTART_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val scoreLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            6 * SCORE_HEIGHT,
            SCORE_LABEL_TEXT + this.score,
            FontManager.get(LABEL)
    )
    private val highScoreLabel: TextLabel
    private val bonusCountLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            2 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT + this.bonusCount,
            FontManager.get(LABEL)
    )

    init {
        if (score > highScore) {
            highScore = score
            game.preferences.putInteger(HIGH_SCORE, score)
        }
        game.preferences.putInteger(BONUSES_COUNT, bonusCount)
        game.preferences.flush()

        highScoreLabel = TextLabel(
                SCREEN_WIDTH / 2,
                4 * SCORE_HEIGHT,
                HIGH_SCORE_LABEL_TEXT + highScore,
                FontManager.get(LABEL)
        )

        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                home()
            }
        })
        restartButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                restart()
            }
        })
        addBackgroundListener(object: InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (ENTER == keycode) restart()
                if (ESCAPE == keycode) home()
                return true
            }
        })

        addActors(Array.with(homeButton, restartButton, scoreLabel, highScoreLabel,
                bonusCountLabel))
    }

    private fun restart() {
        game.screen = GameScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.levelAssets
        )
    }

    private fun home() {
        game.screen = StartScreen(
                this.game,
                this.bonusCount,
                this.highScore,
                this.isSoundOn,
                this.levelAssets
        )
    }
}