package io.cucumber

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.LevelManager
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.view.LoadScreen
import io.cucumber.view.StartScreen

class Game : com.badlogic.gdx.Game() {

    lateinit var preferences: Preferences
        private set
    lateinit var fpsLogger: FPSLogger
        private set
    lateinit var stage: Stage
        private set

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        setScreen(LoadScreen(this))
    }

    fun loadAssets() {
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME)
        fpsLogger = FPSLogger()
        Gdx.input.inputProcessor = stage
        LevelManager.loadLevels()
        FontManager.loadFonts()
        setScreen(StartScreen(this, null, null, null, null))
    }

    override fun dispose() {
        super.dispose()
        LevelManager.removeLevels()
        FontManager.removeFonts()
    }
}
