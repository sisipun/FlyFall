package io.cucumber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.Stage;

import io.cucumber.service.manager.FontManager;
import io.cucumber.service.manager.LevelManager;
import io.cucumber.view.StartScreen;

import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class Game extends com.badlogic.gdx.Game {

    private Preferences preferences;
    private FPSLogger logger;
    private Stage stage;

    @Override
    public void create() {
        preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
        logger = new FPSLogger();
        stage = new Stage();
        LevelManager.loadLevels();
        FontManager.loadFonts();
        Gdx.input.setInputProcessor(stage);
        setScreen(new StartScreen(this, null, null, null, null));
    }

    @Override
    public void dispose() {
        LevelManager.removeLevels();
        FontManager.removeFonts();
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public FPSLogger getFpsLogger() {
        return logger;
    }

    public Stage getStage() {
        return stage;
    }
}
