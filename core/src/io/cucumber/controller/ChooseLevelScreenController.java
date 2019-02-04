package io.cucumber.controller;

import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import io.cucumber.view.ChooseLevelScreen;

import static io.cucumber.utils.constant.GameConstants.MIN_FLING_DISTANCE;

public class ChooseLevelScreenController extends GestureAdapter {

    private final ChooseLevelScreen screen;

    public ChooseLevelScreenController(ChooseLevelScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityX > MIN_FLING_DISTANCE) {
            screen.setPreviousTextureLevel();
        }
        if (velocityX < -1 * MIN_FLING_DISTANCE){
            screen.setNextTextureLevel();
        }
        return super.fling(velocityX, velocityY, button);
    }
}
