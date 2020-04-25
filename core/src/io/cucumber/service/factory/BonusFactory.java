package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.model.actor.character.Bonus;
import io.cucumber.pool.BonusPool;

import static io.cucumber.utils.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.utils.constant.GameConstants.BONUS_SIZE;

public class BonusFactory {

    public static void initFactory() {
        Pools.set(Bonus.class, new BonusPool());
    }

    public static Bonus create(float x, float y, Animation<TextureRegion> animation,
                               TextureRegion timerRegion) {
        return Pools.obtain(Bonus.class).init(x, y, BONUS_SIZE, animation, timerRegion, BONUS_LIFESPAN);
    }

    public static void free(Bonus bonus) {
        Pools.free(bonus);
    }

    private BonusFactory() {
    }
}
