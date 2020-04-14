package io.cucumber.model.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import io.cucumber.model.bound.CircleBound;

public class SimpleCircle extends SimpleShape<Circle> {

    public SimpleCircle(float x, float y, float radius, Texture texture) {
        super(new CircleBound(x, y, radius), texture);
    }
}
