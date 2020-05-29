package io.cucumber.view

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.actor.shape.AnimatedCircle
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.button.SwitchImageButton
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.COST
import io.cucumber.service.manager.FontManager.FontType.LABEL
import io.cucumber.service.manager.LevelManager
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.view.ChooseLevelScreen.Switcher.*

class ChooseLevelScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private var isSoundOn: Boolean,
        private var isAcceleratorOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Actors
    private var homeButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth / 2 + HOME_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.notButton
    )
    private var chooseButton: SwitchImageButton<Switcher> = SwitchImageButton<Switcher>(
            game.stage.camera.viewportWidth / 2 - CHOOSE_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            getSwitcher(),
            mapOf(
                    CANT_BUY to this.levelAssets.cantBuyButton,
                    CAN_BUY to this.levelAssets.buyButton,
                    ACTIVE to this.levelAssets.okButton
            )
    )
    private var leftButton: ImageButton = ImageButton(
            2 * CHOOSE_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            this.levelAssets.leftButton
    )
    private var rightButton: ImageButton = ImageButton(
            game.stage.camera.viewportWidth - 2 * CHOOSE_BUTTON_WIDTH,
            game.stage.camera.viewportHeight / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            this.levelAssets.rightButton
    )
    private var hero: AnimatedCircle = AnimatedCircle(
            game.stage.camera.viewportWidth / 2 - game.stage.camera.viewportWidth / 8,
            game.stage.camera.viewportHeight / 2 - game.stage.camera.viewportHeight / 4,
            HERO_SIZE,
            this.levelAssets.hero
    )
    private var enemy: AnimatedCircle = AnimatedCircle(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight / 2 - game.stage.camera.viewportHeight / 4,
            ENEMY_SIZE,
            this.levelAssets.enemy
    )
    private var bonus: AnimatedCircle = AnimatedCircle(
            game.stage.camera.viewportWidth / 2 + game.stage.camera.viewportWidth / 8,
            game.stage.camera.viewportHeight / 2 - game.stage.camera.viewportHeight / 4,
            BONUS_SIZE,
            this.levelAssets.bonus
    )
    private var costLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            game.stage.camera.viewportHeight - 6 * SCORE_HEIGHT,
            COST_LABEL_TEXT + this.levelAssets.cost.toString(),
            FontManager.get(COST)
    )
    private val highScoreLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            4 * SCORE_HEIGHT,
            HIGH_SCORE_LABEL_TEXT + this.highScore.toString(),
            FontManager.get(LABEL)
    )
    private val bonusCountLabel: TextLabel = TextLabel(
            game.stage.camera.viewportWidth / 2,
            2 * SCORE_HEIGHT,
            BONUS_LABEL_TEXT + this.bonusCount.toString(),
            FontManager.get(LABEL)
    )

    init {
        this.homeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                back()
            }
        })
        this.chooseButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                chooseLevel()
            }
        })
        this.leftButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                setPreviousLevel()
            }
        })
        this.rightButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                setNextLevel()
            }
        })
        addBackgroundListener(object : ActorGestureListener() {
            override fun fling(event: InputEvent?, velocityX: Float, velocityY: Float, button: Int) {
                if (velocityX > MIN_FLING_DISTANCE) {
                    setPreviousLevel()
                }
                if (velocityX < -1 * MIN_FLING_DISTANCE) {
                    setNextLevel()
                }
            }
        })
        addBackgroundListener(object: InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (LEFT == keycode) setPreviousLevel()
                if (RIGHT == keycode) setNextLevel()
                if (ENTER == keycode) chooseLevel()
                if (ESCAPE == keycode) back()
                return true
            }
        })
    }

    fun init(bonusCount: Int, highScore: Int, isSoundOn: Boolean,
             isAcceleratorOn: Boolean, levelAssets: LevelAssets): ChooseLevelScreen {
        this.isSoundOn = isSoundOn
        this.isAcceleratorOn = isAcceleratorOn
        this.highScore = highScore
        this.bonusCount = bonusCount
        this.levelAssets = levelAssets
        return this
    }

    override fun show() {
        super.show()
        reloadLevel()

        addActors(Array.with(homeButton, chooseButton, leftButton, rightButton, hero, enemy, bonus))
    }

    private fun reloadLevel() {
        chooseButton.switcher = getSwitcher()
        chooseButton.setTexture(mapOf(
                CANT_BUY to this.levelAssets.cantBuyButton,
                CAN_BUY to this.levelAssets.buyButton,
                ACTIVE to this.levelAssets.okButton
        ))
        hero.setAnimation(levelAssets.hero)
        enemy.setAnimation(levelAssets.enemy)
        bonus.setAnimation(levelAssets.bonus)
        homeButton.setTexture(levelAssets.homeButton)
        leftButton.setTexture(levelAssets.leftButton)
        rightButton.setTexture(levelAssets.rightButton)

        highScoreLabel.setText(HIGH_SCORE_LABEL_TEXT + this.highScore.toString())
        bonusCountLabel.setText(BONUS_LABEL_TEXT + this.bonusCount.toString())
        addActors(Array.with(highScoreLabel))
        addActors(Array.with(bonusCountLabel))
        if (!levelAssets.isActive) {
            costLabel.setText(COST_LABEL_TEXT + levelAssets.cost.toString())
            addActors(Array.with(costLabel))
        } else {
            costLabel.remove()
        }
    }

    private fun setPreviousLevel() {
        reloadLevelAssets(LevelManager.getPrevious(levelAssets))
        reloadLevel()
    }

    private fun setNextLevel() {
        reloadLevelAssets(LevelManager.getNext(levelAssets))
        reloadLevel()
    }

    private fun chooseLevel() {
        if (levelAssets.isActive) {
            game.preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
            game.preferences.flush()
            setScreen(ScreenManager.getStartScreen(
                    game,
                    bonusCount,
                    highScore,
                    isSoundOn,
                    isAcceleratorOn,
                    levelAssets
            ))
        } else if (bonusCount >= levelAssets.cost) {
            bonusCount -= levelAssets.cost
            bonusCountLabel.setText(BONUS_LABEL_TEXT + this.bonusCount.toString())
            game.preferences.putInteger(BONUSES_COUNT, bonusCount)
            game.preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
            LevelManager.activate(levelAssets.id)
            game.preferences.flush()
            chooseButton.switcher = ACTIVE
            costLabel.remove()
        }
    }

    private fun back() {
        setScreen(ScreenManager.getStartScreen(
                game,
                bonusCount,
                highScore,
                isSoundOn,
                isAcceleratorOn,
                null
        ))
    }

    private fun getSwitcher(): Switcher = when {
        this.levelAssets.isActive -> {
            ACTIVE
        }
        this.bonusCount >= this.levelAssets.cost -> {
            CAN_BUY
        }
        else -> {
            CANT_BUY
        }
    }

    private enum class Switcher {
        ACTIVE,
        CAN_BUY,
        CANT_BUY
    }
}