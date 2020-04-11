package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
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
    protected val camera: OrthographicCamera = OrthographicCamera()
    private val logger: FPSLogger = FPSLogger()
    private val stage: Stage = Stage()

    protected var textureLevel: TextureLevel = textureLevel
            ?: TextureLevelManager.get(preferences.getInteger(TEXTURE_LEVEL))
    private var background: Image = Image(this.textureLevel.background)

    init {
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
        background.setBounds(0F, 0F, SCREEN_WIDTH, SCREEN_HEIGHT)
        addActor(background)
    }

    protected open fun update(delta: Float) {
        stage.act(delta)
    }

    protected open fun handleInput() {}

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

    override fun render(delta: Float) {
        logger.log()
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        update(delta)
        camera.update()
        stage.draw()
        handleInput()
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

    fun getTouchPosition(): Vector2 {
        val unproject = camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0F))
        return Vector2(unproject.x, unproject.y)
    }
}