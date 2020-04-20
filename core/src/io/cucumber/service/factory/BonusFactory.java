package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.actor.character.Bonus;

import static io.cucumber.utils.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.utils.constant.GameConstants.BONUS_SIZE;

public class BonusFactory {

    public static Bonus create(float x, float y, TextureRegion region, TextureRegion timerRegion) {
        return new Bonus(x, y, BONUS_SIZE, region, timerRegion, BONUS_LIFESPAN);
    }

    private BonusFactory() {
    }
}
