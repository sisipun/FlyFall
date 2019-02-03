package io.cucumber.model.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public abstract class Actor {

    protected final Vector2 position;
    protected final Vector2 velocity;
    private final float size;
    private final Texture texture;

    public Actor(float x, float y, float size, float horizontalVelocity, float verticalVelocity, Texture texture) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(horizontalVelocity, verticalVelocity);
        this.size = size;
        this.texture = texture;
    }

    public Circle getBound() {
        return new Circle(position.x, position.y, size / 2);
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isCollides(Actor actor) {
        return this.getBound().overlaps(actor.getBound());
    }

    public abstract void update(float delta);

    public void dispose() {
        texture.dispose();
    }
}
