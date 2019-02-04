package io.cucumber.model.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public abstract class DynamicActor extends Actor<Circle> {

    protected final Vector2 velocity;

    public DynamicActor(float x, float y, float size, float horizontalVelocity, float verticalVelocity, Texture texture) {
        super(new Circle(x, y, size / 2), texture);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
    }

    public boolean isCollides(DynamicActor actor) {
        return bound.overlaps(actor.getBound());
    }

    public abstract void update(float delta);
}
