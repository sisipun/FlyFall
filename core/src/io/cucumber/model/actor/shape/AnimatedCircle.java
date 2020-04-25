package io.cucumber.model.actor.shape;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.AnimationActor;

public class AnimatedCircle extends AnimationActor {

    public AnimatedCircle(float x, float y, float size, Animation<TextureRegion> animation) {
        super(x, y, size, 0f, 0f, animation, (byte) 0);
    }
}
