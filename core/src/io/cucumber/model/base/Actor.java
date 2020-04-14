package io.cucumber.model.base;

import com.badlogic.gdx.math.Shape2D;

import io.cucumber.model.bound.Bound2D;

public abstract class Actor<T extends Shape2D> extends com.badlogic.gdx.scenes.scene2d.Actor {

    private Bound2D<T> bound;

    public Actor(Bound2D<T> bound) {
        this.bound = bound;
        setBounds(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
        setOrigin(getWidth()/ 2, getHeight() / 2);
    }

    public boolean isCollides(Actor<T> actor) {
        return bound.overlaps(actor.bound);
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
}
