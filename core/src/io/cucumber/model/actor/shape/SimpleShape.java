package io.cucumber.model.actor.shape;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;

import io.cucumber.model.base.Actor;
import io.cucumber.model.bound.Bound2D;

public class SimpleShape<T extends Shape2D> extends Actor<T> {

    private final Texture texture;

    public SimpleShape(Bound2D<T> bound, Texture texture) {
        super(bound);
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                texture,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                1F,
                1F,
                getRotation(),
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false
        );
    }
}
