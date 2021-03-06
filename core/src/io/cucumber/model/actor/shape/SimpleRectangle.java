package io.cucumber.model.actor.shape;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.model.base.StaticActor;
import io.cucumber.model.bound.RectangleBound;

public class SimpleRectangle extends StaticActor<Rectangle> {

    public SimpleRectangle(float x, float y, float width, float height, TextureRegion region) {
        super(new RectangleBound(x, y, width, height), region);
    }

    public SimpleRectangle(float x, float y, float width, float height, TextureRegion region, boolean flipY) {
        super(new RectangleBound(x, y, width, height), region);
        if (flipY) {
            setRotation(180);
        }
    }
}
