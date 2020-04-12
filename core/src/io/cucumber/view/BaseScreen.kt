package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import io.cucumber.model.texture.TextureLevel
import io.cucumber.service.manager.TextureLevelManager
import io.cucumber.utils.constant.GameConstants.*


abstract class BaseScreen(
    protected val game: Game,
    textureLevel: TextureLevel?
) : ScreenAdapter() {

    protected val preferences: Preferences = Gdx.app.getPreferences(PREFERENCE_NAME)
    private val camera: OrthographicCamera = OrthographicCamera()
    private val logger: FPSLogger = FPSLogger()
    private val stage: Stage = Stage()

    protected var textureLevel: TextureLevel = textureLevel
            ?: TextureLevelManager.get(preferences.getInteger(TEXTURE_LEVEL))
    private var background: Image = Image(this.textureLevel.background)

    init {
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
        background.setBounds(0F, 0F, SCREEN_WIDTH, SCREEN_HEIGHT)
        addActor(background)
        stage.keyboardFocus = background
        Gdx.input.inputProcessor = stage
    }

    protected open fun update(delta: Float) {
        stage.act(delta)
    }

    protected open fun stateCheck() {}

    protected open fun screenDispose() {}

    protected fun addActor(actor: Actor) {
        stage.addActor(actor)
    }

    protected fun addActors(actors: Array<Actor>) {
        for (actor in actors) {
            addActor(actor)
        }
    }

    protected fun changeTextureLevel(textureLevel: TextureLevel) {
        this.textureLevel.dispose()
        this.textureLevel = textureLevel

        val newBackground = Image(textureLevel.background)
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

        update(delta)
        camera.update()
        stage.draw()
        stateCheck()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.actors.forEach { it.remove() }
        stage.clear()
        screenDispose()
    }
}