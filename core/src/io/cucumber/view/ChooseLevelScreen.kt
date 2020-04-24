package io.cucumber.view

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.actor.shape.SimpleCircle
import io.cucumber.model.component.button.ImageButton
import io.cucumber.model.component.button.SwitchImageButton
import io.cucumber.model.component.text.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.COST
import io.cucumber.service.manager.LevelManager
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*

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
            SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
            HOME_BUTTON_WIDTH,
            HOME_BUTTON_HEIGHT,
            this.levelAssets.notButton
    )
    private var chooseButton: SwitchImageButton = SwitchImageButton(
            SCREEN_WIDTH / 2 - CHOOSE_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            this.levelAssets.okButton,
            this.levelAssets.buyButton,
            this.levelAssets.isActive
    )
    private var leftButton: ImageButton = ImageButton(
            CHOOSE_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            this.levelAssets.leftButton
    )
    private var rightButton: ImageButton = ImageButton(
            SCREEN_WIDTH - CHOOSE_BUTTON_WIDTH,
            SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
            CHOOSE_BUTTON_WIDTH,
            CHOOSE_BUTTON_HEIGHT,
            this.levelAssets.rightButton
    )
    private var hero: SimpleCircle = SimpleCircle(
            SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8,
            SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4,
            HERO_SIZE / 2,
            this.levelAssets.hero
    )
    private var enemy: SimpleCircle = SimpleCircle(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4,
            ENEMY_SIZE / 2,
            this.levelAssets.enemy
    )
    private var bonus: SimpleCircle = SimpleCircle(
            SCREEN_WIDTH / 2 + SCREEN_WIDTH / 8,
            SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4,
            BONUS_SIZE / 2,
            this.levelAssets.bonus
    )
    private var costLabel: TextLabel = TextLabel(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT - 6 * SCORE_HEIGHT,
            COST_LABEL_TEXT + this.levelAssets.cost.toString(),
            FontManager.get(COST)
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
        chooseButton.setSwitcher(levelAssets.isActive)
        hero.setRegion(levelAssets.hero)
        enemy.setRegion(levelAssets.enemy)
        bonus.setRegion(levelAssets.bonus)

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
            game.preferences.putInteger(BONUSES_COUNT, bonusCount)
            game.preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
            LevelManager.activate(levelAssets.id)
            game.preferences.flush()
            chooseButton.setSwitcher(levelAssets.isActive)
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
}