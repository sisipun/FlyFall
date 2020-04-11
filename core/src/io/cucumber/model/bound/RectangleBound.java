package io.cucumber.model.bound;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class RectangleBound extends Bound2D<Rectangle> {

    private final Rectangle bound;

    public RectangleBound(float x, float y, float width, float height) {
        this.bound = new Rectangle(x, y, width, height);
    }

    @Override
    public float getX() {
        return bound.x;
    }

    @Override
    public float getY() {
        return bound.y;
    }

    @Override
    public float getWidth() {
        return bound.width;
    }

    @Override
    public float getHeight() {
        return bound.height;
    }

    @Override
    public void setX(float x) {
        bound.x = x;
    }

    @Override
    public void setY(float y) {
        bound.y = y;
    }

    @Override
    public void setWidth(float width) {
        bound.width = width;
    }

    @Override
    public void setHeight(float height) {
        bound.height = height;
    }

    @Override
    public boolean overlaps(Bound2D<Rectangle> otherBound) {
        return bound.overlaps(otherBound.getBound());
    }

    @Override
    public boolean contains(Vector2 point) {
        return bound.contains(point);
    }

    @Override
    protected Rectangle getBound() {
        return bound;
    }
}
