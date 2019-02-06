package io.cucumber.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.cucumber.Game;

import static io.cucumber.utils.constant.GameConstants.SCREEN_HEIGHT;
import static io.cucumber.utils.constant.GameConstants.SCREEN_WIDTH;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = (int) SCREEN_HEIGHT;
        config.width = (int) SCREEN_WIDTH;
        new LwjglApplication(new Game(), config);
    }
}
