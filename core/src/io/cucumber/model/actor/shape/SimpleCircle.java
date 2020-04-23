package io.cucumber.model.actor.shape;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import io.cucumber.model.base.StaticActor;
import io.cucumber.model.bound.CircleBound;

public class SimpleCircle extends StaticActor<Circle> {

    public SimpleCircle(float x, float y, float radius, TextureRegion region) {
        super(new CircleBound(x, y, radius), region);
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }
}
