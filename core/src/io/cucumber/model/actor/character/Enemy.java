package io.cucumber.model.actor.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.AnimationActor;

import static io.cucumber.utils.constant.GameConstants.ENEMY_SCALE;

public class Enemy extends AnimationActor {

    public Enemy(float x, float y, float size, float horizontalVelocity,
                 Animation<TextureRegion> animation, byte orientation) {
        super(x, y, size / ENEMY_SCALE, horizontalVelocity, 0, animation, orientation);
        setScale(ENEMY_SCALE, ENEMY_SCALE);
    }

    public Enemy init(float x, float y, float size, float horizontalVelocity,
                      Animation<TextureRegion> animation, byte orientation) {
        super.init(x, y, size / ENEMY_SCALE, horizontalVelocity, 0, animation, orientation);
        setScale(ENEMY_SCALE, ENEMY_SCALE);
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
