package io.cucumber.model.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.model.bound.RectangleBound;

public class SimpleRectangle extends SimpleShape<Rectangle> {

    public SimpleRectangle(float x, float y, float width, float height, Texture texture) {
        super(new RectangleBound(x, y, width, height), texture);
    }
}
