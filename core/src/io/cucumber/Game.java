package io.cucumber;

import io.cucumber.service.manager.LevelManager;
import io.cucumber.view.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        LevelManager.loadLevels();
        setScreen(new StartScreen(this, null, null, null, null, null));
    }

    @Override
    public void dispose() {
        LevelManager.removeLevels();
    }
}
