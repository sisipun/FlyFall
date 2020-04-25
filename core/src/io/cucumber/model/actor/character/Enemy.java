package io.cucumber.model.actor.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.AnimationActor;

public class Enemy extends AnimationActor {

    public Enemy(float x, float y, float size, float horizontalVelocity,
                 Animation<TextureRegion> animation, byte orientation) {
        super(x, y, size, horizontalVelocity, 0, animation, orientation);
    }

    public Enemy init(float x, float y, float size, float horizontalVelocity,
                      Animation<TextureRegion> animation, byte orientation) {
        super.init(x, y, size, horizontalVelocity, 0, animation, orientation);
        return this;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX() + velocity.x * orientation * delta);
    }

    @Override
    public void reset() {
        super.reset();
        orientation = 0;
    }
}
