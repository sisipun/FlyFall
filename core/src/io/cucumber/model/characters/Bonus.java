package io.cucumber.model.characters;

import com.badlogic.gdx.graphics.Texture;
import io.cucumber.model.base.DynamicActor;

import static io.cucumber.utils.constant.GameConstants.MILLIS_IN_SECOND;

public class Bonus extends DynamicActor {

    private float lifespan;

    public Bonus(float x, float y, float size, Texture texture, float lifespan) {
        super(x, y, size, 0, 0, texture);
        this.lifespan = lifespan;
    }

    @Override
    public void update(float delta) {
        lifespan -= delta * MILLIS_IN_SECOND;
    }

    public float getLifespan() {
        return lifespan;
    }
}
