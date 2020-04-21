package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.LevelManager
import io.cucumber.utils.constant.GameConstants.*


abstract class BaseScreen(
        protected val game: Game,
        levelAssets: LevelAssets?
) : ScreenAdapter() {

    protected var levelAssets: LevelAssets = levelAssets
            ?: LevelManager.get(game.preferences.getInteger(TEXTURE_LEVEL))
    private var background: Image = Image(this.levelAssets.background)

    override fun show() {
        this.background.setBounds(0F, 0F, SCREEN_WIDTH, SCREEN_HEIGHT)
        addActor(this.background)
        game.stage.keyboardFocus = this.background
    }

    override fun hide() {
        this.game.stage.clear()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        game.stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        game.fpsLogger.log()
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
        this.background = Image(levelAssets.background)
        this.background.setBounds(0F, 0F, SCREEN_WIDTH, SCREEN_HEIGHT)
        game.stage.actors[0] = this.background
    }

    protected fun addBackgroundListener(listener: EventListener) {
        background.addListener(listener)
    }

    protected fun setScreen(screen: BaseScreen) {
        hide()
        game.screen = screen
    }
}