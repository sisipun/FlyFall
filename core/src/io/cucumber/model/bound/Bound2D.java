package io.cucumber.model.bound;

import com.badlogic.gdx.math.Shape2D;

public abstract class Bound2D<T extends Shape2D> {

    public abstract float getX();

    public abstract float getY();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract void setX(float x);

    public abstract void setY(float y);

    public abstract void setWidth(float width);

    public abstract void setHeight(float height);

    public abstract boolean overlaps(Bound2D<T> otherBound);

    protected abstract T getBound();

}
