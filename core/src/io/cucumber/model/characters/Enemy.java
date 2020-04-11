package io.cucumber.model.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.cucumber.model.base.DynamicActor;

public class Enemy extends DynamicActor {

    private byte orientation;

    public Enemy(float x, float y, float size, float horizontalVelocity, Texture texture, byte orientation) {
        super(x, y, size, horizontalVelocity, 0, texture);
        this.orientation = orientation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                currentTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );
    }

    @Override
    public void act(float delta) {
        setX(getX() + velocity.x * -1 * orientation * delta);
    }
}
