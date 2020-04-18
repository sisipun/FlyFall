package io.cucumber.view

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.button.ImageButton
import io.cucumber.model.button.SwitchImageButton
import io.cucumber.model.component.SimpleCircle
import io.cucumber.model.component.TextLabel
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.FontManager.FontType.COST
import io.cucumber.service.manager.LevelManager
import io.cucumber.utils.constant.GameConstants.*

class ChooseLevelScreen(
        game: Game,
        private var bonusCount: Int,
        private var highScore: Int,
        private val isSoundOn: Boolean,
        levelAssets: LevelAssets
) : BaseScreen(game, levelAssets) {

    // Actors
    private var homeButton: ImageButton? = null
    private var chooseButton: SwitchImageButton? = null
    private var leftButton: ImageButton? = null
    private var rightButton: ImageButton? = null
    private var hero: SimpleCircle? = null
    private var enemy: SimpleCircle? = null
    private var bonus: SimpleCircle? = null
    private var costActor: TextLabel? = null
    private var costLabel: TextLabel? = null

    init {
        reloadButtons()
    }

    private fun reloadButtons() {
        homeButton?.remove()
        chooseButton?.remove()
        leftButton?.remove()
        rightButton?.remove()
        hero?.remove()
        enemy?.remove()
        bonus?.remove()
        costActor?.remove()
        costLabel?.remove()

        homeButton = ImageButton(
                SCREEN_WIDTH / 2 + HOME_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - HOME_BUTTON_HEIGHT / 2,
                HOME_BUTTON_WIDTH,
                HOME_BUTTON_HEIGHT,
                this.levelAssets.notButton
        )
        chooseButton = SwitchImageButton(
                SCREEN_WIDTH / 2 - CHOOSE_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
                CHOOSE_BUTTON_WIDTH,
                CHOOSE_BUTTON_HEIGHT,
                this.levelAssets.okButton,
                this.levelAssets.buyButton,
                this.levelAssets.isActive
        )
        leftButton = ImageButton(
                CHOOSE_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
                CHOOSE_BUTTON_WIDTH,
                CHOOSE_BUTTON_HEIGHT,
                this.levelAssets.leftButton
        )
        rightButton = ImageButton(
                SCREEN_WIDTH - 2 * CHOOSE_BUTTON_WIDTH,
                SCREEN_HEIGHT / 2 - CHOOSE_BUTTON_HEIGHT / 2,
                CHOOSE_BUTTON_WIDTH,
                CHOOSE_BUTTON_HEIGHT,
                this.levelAssets.rightButton
        )
        hero = SimpleCircle(
                SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8 + HERO_SIZE / 4,
                SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4 - HERO_SIZE / 4,
                HERO_SIZE / 2,
                this.levelAssets.hero
        )
        enemy = SimpleCircle(
                SCREEN_WIDTH / 2 + ENEMY_SIZE / 4,
                SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4 - ENEMY_SIZE / 4,
                ENEMY_SIZE / 2,
                this.levelAssets.enemy
        )
        bonus = SimpleCircle(
                SCREEN_WIDTH / 2 + SCREEN_WIDTH / 8 + BONUS_SIZE / 4,
                SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 4 - BONUS_SIZE / 4,
                BONUS_SIZE / 2,
                this.levelAssets.bonus
        )
        homeButton?.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                back()
            }
        })
        chooseButton?.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                chooseLevel()
            }
        })
        leftButton?.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                setPreviousLevel()
            }
        })
        rightButton?.addListener(object : ClickListener() {
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

        val action = RepeatAction()
        action.count = RepeatAction.FOREVER
        action.action = Actions.rotateBy(ENEMY_ROTATION_ANGEL, ENEMY_ROTATION_DURATION)
        enemy?.addAction(action)

        addActors(Array.with(homeButton, chooseButton, leftButton, rightButton, hero, enemy, bonus))

        if (!levelAssets.isActive) {
            costActor = TextLabel(
                    SCREEN_WIDTH / 2 + SCREEN_WIDTH / 16 + SCREEN_WIDTH / 32,
                    SCREEN_HEIGHT - 6 * SCORE_HEIGHT,
                    this.levelAssets.cost.toString(),
                    FontManager.get(COST)
            )
            costLabel = TextLabel(
                    SCREEN_WIDTH / 2 - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 32,
                    SCREEN_HEIGHT - 6 * SCORE_HEIGHT,
                    COST_LABEL_TEXT,
                    FontManager.get(COST)
            )
            addActors(Array.with(costActor, costLabel))
        }
    }

    private fun setPreviousLevel() {
        reloadLevelAssets(LevelManager.getPrevious(levelAssets))
        chooseButton?.setSwitcher(levelAssets.isActive)
        reloadButtons()
    }

    private fun setNextLevel() {
        reloadLevelAssets(LevelManager.getNext(levelAssets))
        chooseButton?.setSwitcher(levelAssets.isActive)
        reloadButtons()
    }

    private fun chooseLevel() {
        if (levelAssets.isActive) {
            game.preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
            game.preferences.flush()
            game.screen = StartScreen(
                    game,
                    bonusCount,
                    highScore,
                    isSoundOn,
                    levelAssets
            )
        } else if (bonusCount >= levelAssets.cost) {
            bonusCount -= levelAssets.cost
            game.preferences.putInteger(BONUSES_COUNT, bonusCount)
            game.preferences.putInteger(TEXTURE_LEVEL, levelAssets.id)
            LevelManager.activate(levelAssets.id)
            game.preferences.flush()
            chooseButton?.setSwitcher(levelAssets.isActive)
            costActor?.remove()
            costLabel?.remove()
        }
    }

    private fun back() {
        game.screen = StartScreen(
                game,
                bonusCount,
                highScore,
                isSoundOn
        )
    }
}