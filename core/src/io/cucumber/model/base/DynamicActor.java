package io.cucumber.model.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.model.bound.CircleBound;

public abstract class DynamicActor extends Actor<Circle> {

    protected Vector2 velocity;

    public DynamicActor(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                        TextureRegion region) {
        super(new CircleBound(x, y, size / 2), region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public DynamicActor init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                     TextureRegion region) {
        super.init(new CircleBound(x, y, size / 2), region);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        velocity.x = 0;
        velocity.y = 0;
    }
}
