package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.Texture;
import io.cucumber.model.characters.Bonus;

import static io.cucumber.utils.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.utils.constant.GameConstants.BONUS_SIZE;

public class BonusFactory {

    public static Bonus create(float x, float y, Texture texture) {
        return new Bonus(x, y, BONUS_SIZE, texture, BONUS_LIFESPAN);
    }

    private BonusFactory() {
    }
}
