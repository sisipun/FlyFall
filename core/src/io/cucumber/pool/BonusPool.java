package io.cucumber.pool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.actor.character.Bonus;

public class BonusPool extends ActorPool<Bonus> {

    public BonusPool() {
        super(1, 3);
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(0f, 0f, 0f, new Animation<>(1f, new TextureRegion()), new TextureRegion(), 0f);
    }
}
