package io.cucumber.model.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Shape2D;

public abstract class Actor<T extends Shape2D> {

    protected T bound;
    protected Texture texture;

    public Actor(T bound, Texture texture) {
        this.bound = bound;
        this.texture = texture;
    }

    public void dispose() {
        texture.dispose();
    }

    public T getBound() {
        return bound;
    }

    public Texture getTexture() {
        return texture;
    }

    protected void setBound(T bound) {
        this.bound = bound;
    }

    protected void setTexture(Texture texture) {
        this.texture = texture;
    }
}
