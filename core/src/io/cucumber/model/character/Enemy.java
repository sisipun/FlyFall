package io.cucumber.model.character;

import com.badlogic.gdx.graphics.Texture;

import io.cucumber.model.base.DynamicActor;

public class Enemy extends DynamicActor {

    private byte orientation;

    public Enemy(float x, float y, float size, float horizontalVelocity, Texture texture, byte orientation) {
        super(x, y, size, horizontalVelocity, 0, texture);
        this.orientation = orientation;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX() + velocity.x * -1 * orientation * delta);
    }
}
