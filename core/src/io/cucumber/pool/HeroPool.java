package io.cucumber.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import io.cucumber.model.actor.character.Hero;

public class HeroPool extends Pool<Hero> {

    public HeroPool() {
        super(1, 1);
    }

    @Override
    protected Hero newObject() {
        return new Hero(0f, 0f, 0f, 0f, 0f, new TextureRegion());
    }
}
