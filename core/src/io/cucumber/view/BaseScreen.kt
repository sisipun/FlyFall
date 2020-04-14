package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import io.cucumber.model.level.LevelAssets
import io.cucumber.service.manager.LevelManager
import io.cucumber.utils.constant.GameConstants.*


abstract class BaseScreen(
        protected val game: Game,
        levelAssets: LevelAssets?
) : ScreenAdapter() {

    protected val preferences: Preferences = Gdx.app.getPreferences(PREFERENCE_NAME)
    private val logger: FPSLogger = FPSLogger()
    private val stage: Stage = Stage()

    protected var levelAssets: LevelAssets = levelAssets
            ?: LevelManager.get(preferences.getInteger(TEXTURE_LEVEL))
    private var background: Image = Image(this.levelAssets.background)

    init {
        background.setBounds(0F, 0F, SCREEN_WIDTH, SCREEN_HEIGHT)
        addActor(background)
        stage.keyboardFocus = background
        Gdx.input.inputProcessor = stage
    }

    protected open fun act(delta: Float) {
        stage.act(delta)
    }

    protected open fun stateCheck() {}

    protected fun addActor(actor: Actor) {
        stage.addActor(actor)
    }

    protected fun addActors(actors: Array<Actor>) {
        for (actor in actors) {
            addActor(actor)
        }
    }

    protected fun reloadLevelAssets(levelAssets: LevelAssets) {
        this.levelAssets.dispose()
        this.levelAssets = levelAssets

        val newBackground = Image(levelAssets.background)
        newBackground.setBounds(0F, 0F, SCREEN_WIDTH, SCREEN_HEIGHT)
        stage.actors[0] = newBackground
        this.background.remove()
        this.background = newBackground
    }

    protected fun addBackgroundListener(listener: EventListener) {
        background.addListener(listener)
    }

    override fun render(delta: Float) {
        logger.log()
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        act(delta)
        stage.draw()
        stateCheck()
        super.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        super.dispose()
        stage.actors.forEach { it.remove() }
        stage.clear()
    }
}