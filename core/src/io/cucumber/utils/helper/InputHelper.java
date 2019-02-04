package io.cucumber.utils.helper;

import com.badlogic.gdx.Gdx;

import static io.cucumber.utils.constant.GameConstants.MAX_POINTERS_COUNT;

public class InputHelper {

    public static int getLastTouchX() {
        int x = 0;
        for (int i = 0; i < MAX_POINTERS_COUNT; i++) {
            if (Gdx.input.isTouched(i)) {
                x = Gdx.input.getX(i);
            }
        }
        return x;
    }

    public static int getLastTouchY() {
        int y = 0;
        for (int i = 0; i < MAX_POINTERS_COUNT; i++) {
            if (Gdx.input.isTouched(i)) {
                y = Gdx.input.getY(i);
            }
        }
        return y;
    }

    private InputHelper() {
    }
}
