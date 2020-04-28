package io.cucumber.pool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.actor.character.Hero;

public class HeroPool extends ActorPool<Hero> {

    public HeroPool() {
        super(1, 1);
    }

    @Override
    protected Hero newObject() {
        return new Hero(0f, 0f, 0f, 0f, 0f, new Animation<>(0f, new TextureRegion()));
    }
}
