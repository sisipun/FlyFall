package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.model.base.Actor;
import io.cucumber.model.bound.RectangleBound;

public class SimpleRectangle extends Actor<Rectangle> {

    private final Texture texture;

    public SimpleRectangle(float x, float y, float width, float height, Texture texture) {
        super(new RectangleBound(x, y, width, height));
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                texture,
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );
    }
}
