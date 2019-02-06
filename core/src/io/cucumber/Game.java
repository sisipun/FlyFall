package io.cucumber;

import io.cucumber.view.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        setScreen(new StartScreen(this, null, null, null, null));
    }
}
