package io.cucumber.factory;

import com.badlogic.gdx.graphics.Texture;
import io.cucumber.model.Bonus;

import static io.cucumber.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.constant.GameConstants.BONUS_SIZE;

public class BonusFactory {

    public static Bonus create(float x, float y, Texture texture) {
        return new Bonus(x, y, BONUS_SIZE, texture, BONUS_LIFESPAN);
    }

    private BonusFactory() {
    }
}
