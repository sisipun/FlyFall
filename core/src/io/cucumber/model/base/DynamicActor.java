package io.cucumber.model.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.model.bound.CircleBound;

public abstract class DynamicActor extends Actor<Circle> {

    protected final Vector2 velocity;

    protected Texture currentTexture;

    public DynamicActor(float x, float y, float size, float horizontalVelocity, float verticalVelocity, Texture currentTexture) {
        super(new CircleBound(x, y, size / 2));
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.currentTexture = currentTexture;
    }

    public boolean isCollides(DynamicActor actor) {
        return overlaps(actor);
    }
}
