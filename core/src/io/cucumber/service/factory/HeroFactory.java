package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.model.actor.character.Hero;
import io.cucumber.pool.HeroPool;

import static io.cucumber.utils.constant.GameConstants.HERO_HORIZONTAL_VELOCITY;
import static io.cucumber.utils.constant.GameConstants.HERO_SIZE;
import static io.cucumber.utils.constant.GameConstants.HERO_VERTICAL_VELOCITY;

public class HeroFactory {

    public static void initFactory() {
        Pools.set(Hero.class, new HeroPool());
    }

    public static Hero create(float x, float y, TextureRegion region) {
        return Pools.obtain(Hero.class).init(
                x,
                y,
                HERO_SIZE,
                HERO_HORIZONTAL_VELOCITY,
                -1 * HERO_VERTICAL_VELOCITY,
                region
        );
    }

    public static void free(Hero hero) {
        Pools.free(hero);
    }

    private HeroFactory() {
    }
}
