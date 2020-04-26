package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.actor.shape.SimpleRectangle
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.LevelManager
import io.cucumber.utils.constant.GameConstants.*


abstract class BaseScreen(
        protected val game: Game,
        levelAssets: LevelAssets?
) : ScreenAdapter() {

    protected var levelAssets: LevelAssets = levelAssets
            ?: LevelManager.get(this.game.preferences.getInteger(TEXTURE_LEVEL))
    private var background: SimpleRectangle = SimpleRectangle(
            SCREEN_WIDTH / 2,
            SCREEN_HEIGHT / 2,
            SCREEN_WIDTH,
            SCREEN_HEIGHT,
            this.levelAssets.background
    )

    override fun show() {
        background.setRegion(levelAssets.background)
        addActor(background)
        game.stage.keyboardFocus = background
    }

    override fun hide() {
        game.stage.clear()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        game.stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
//        game.fpsLogger.log()
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        act(delta)
        game.stage.draw()
        stateCheck()
        super.render(delta)
    }

    protected open fun act(delta: Float) {
        game.stage.act(delta)
    }

    protected open fun stateCheck() {}

    protected fun addActor(actor: Actor) {
        game.stage.addActor(actor)
    }

    protected fun addActors(actors: Array<Actor>) {
        for (actor in actors) {
            addActor(actor)
        }
    }

    protected fun reloadLevelAssets(levelAssets: LevelAssets) {
        this.levelAssets = levelAssets
        this.background.setRegion(levelAssets.background)
    }

    protected fun addBackgroundListener(listener: EventListener) {
        background.addListener(listener)
    }

    protected fun clearBackgroundListeners() {
        background.clearListeners()
    }

    protected fun setScreen(screen: BaseScreen) {
        game.screen = screen
    }
}