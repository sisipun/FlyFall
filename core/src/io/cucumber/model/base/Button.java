package io.cucumber.model.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button extends Actor<Rectangle> {

    public Button(float x, float y, float width, float height, Texture texture) {
        super(new Rectangle(x, y, width, height), texture);
    }

    public boolean isTouched(Vector2 position) {
        return bound.contains(position);
    }
}
