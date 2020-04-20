package io.cucumber.model.actor.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.DynamicActor;

public class Enemy extends DynamicActor {

    private byte orientation;

    public Enemy(float x, float y, float size, float horizontalVelocity, TextureRegion region, byte orientation) {
        super(x, y, size, horizontalVelocity, 0, region);
        this.orientation = orientation;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX() + velocity.x * -1 * orientation * delta);
    }
}
