package io.cucumber.view

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.cucumber.constant.PreferenceConstants.PREFERENCE_NAME
import io.cucumber.constant.ScreenConstants.SCREEN_HEIGHT
import io.cucumber.constant.ScreenConstants.SCREEN_WIDTH


abstract class BaseScreen(
    protected val game: Game
) : ScreenAdapter() {

    protected val preferences: Preferences = Gdx.app.getPreferences(PREFERENCE_NAME)
    protected val batch: SpriteBatch = SpriteBatch()
    protected val camera: OrthographicCamera = OrthographicCamera()


    init {
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
    }

    protected open fun update(delta: Float) {}

    protected open fun render() {}

    protected open fun handleInput() {}

    protected open fun stateCheck() {}

    protected open fun screenDispose() {}

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        update(delta)
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        render()
        handleInput()
        stateCheck()
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        screenDispose()
    }

}