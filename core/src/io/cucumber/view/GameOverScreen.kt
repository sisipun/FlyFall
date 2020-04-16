package io.cucumber.view

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.button.ImageButton
import io.cucumber.model.component.ScoreLabel
import io.cucumber.model.component.TextLabel
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
    private val highScoreActor: ScoreLabel
    private val scoreActor: ScoreLabel = ScoreLabel(
            SCREEN_WIDTH / 2 + SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64,
            6 * SCORE_HEIGHT,
            this.score,
            FontManager.get(LABEL)
    )
    private val bonusesCountActor: ScoreLabel = ScoreLabel(
            SCREEN_WIDTH / 2  + SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64,
            2 * SCORE_HEIGHT,
            this.bonusCount,
            FontManager.get(LABEL)
    )
    private val homeButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - HOME_BUTTON_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.notButton
    )
    private val restartButton: ImageButton = ImageButton(
            SCREEN_WIDTH / 2 - RESTART_BUTTON_WIDTH / 2 - RESTART_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - RESTART_BUTTON_HEIGHT / 2,
            RESTART_BUTTON_WIDTH,
            RESTART_BUTTON_HEIGHT,
            this.levelAssets.playButton
    )
    private val scoreLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8  + SCREEN_WIDTH / 16,
            6 * SCORE_HEIGHT,
            SCORE_LABEL_TEXT,
            FontManager.get(LABEL)
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
        if (score > highScore) {
            highScore = score
            game.preferences.putInteger(HIGH_SCORE, score)
        }
        game.preferences.putInteger(BONUSES_COUNT, bonusCount)
        game.preferences.flush()

        highScoreActor = ScoreLabel(
                SCREEN_WIDTH / 2 + SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64,
                4 * SCORE_HEIGHT,
                highScore,
                FontManager.get(LABEL)
        )

        homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = StartScreen(
                        this@GameOverScreen.game,
                        this@GameOverScreen.bonusCount,
                        this@GameOverScreen.highScore,
                        this@GameOverScreen.isSoundOn,
                        this@GameOverScreen.levelAssets
                )
            }
        })
        restartButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = GameScreen(
                        this@GameOverScreen.game,
                        this@GameOverScreen.bonusCount,
                        this@GameOverScreen.highScore,
                        this@GameOverScreen.isSoundOn,
                        this@GameOverScreen.levelAssets
                )
            }
        })

        addActors(Array.with(homeButton, restartButton, scoreActor, highScoreActor,
                bonusesCountActor, scoreLabel, highScoreLabel, bonusCountLabel))
    }
}