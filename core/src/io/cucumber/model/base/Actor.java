package io.cucumber.model.base;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.model.bound.Bound2D;

public abstract class Actor<T extends Shape2D> extends com.badlogic.gdx.scenes.scene2d.Actor {

    private Bound2D<T> bound;

    public Actor(Bound2D<T> bound) {
        this.bound = bound;
        setBounds(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
    }

    public boolean overlaps(Actor<T> actor) {
        return bound.overlaps(actor.bound);
    }

    public boolean contains(Vector2 point) {
        return bound.contains(point);
    }

    @Override
    public void setX(float x) {
        bound.setX(x);
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        bound.setY(y);
        super.setY(y);
    }

    @Override
    public void setWidth(float width) {
        bound.setWidth(width);
        super.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        bound.setHeight(height);
        super.setHeight(height);
    }

    public void dispose() {
    }
}
