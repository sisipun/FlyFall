package io.cucumber

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Pools
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.model.dto.HighScore
import io.cucumber.pool.HighScorePool
import io.cucumber.service.factory.BonusFactory
import io.cucumber.service.factory.EnemyGroupFactory
import io.cucumber.service.factory.HeroFactory
import io.cucumber.service.manager.FontManager
import io.cucumber.service.manager.LevelManager
import io.cucumber.service.manager.ScreenManager
import io.cucumber.utils.constant.GameConstants.*
import io.cucumber.view.LoadScreen

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

    fun init() {
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME)
        fpsLogger = FPSLogger()
        Gdx.input.inputProcessor = stage
        LevelManager.loadLevels()
        FontManager.loadFonts()
        EnemyGroupFactory.initFactory()
        BonusFactory.initFactory()
        HeroFactory.initFactory()
        Pools.set(HighScore::class.java, HighScorePool());
        setScreen(ScreenManager.getStartScreen(this, null, null, null, null, null))
    }

    override fun dispose() {
        super.dispose()
        LevelManager.removeLevels()
        FontManager.removeFonts()
    }
}
