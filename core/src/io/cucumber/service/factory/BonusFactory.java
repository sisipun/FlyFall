package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.Texture;
import io.cucumber.model.character.Bonus;

import static io.cucumber.utils.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.utils.constant.GameConstants.BONUS_SIZE;

public class BonusFactory {

    public static Bonus create(float x, float y, Texture texture, Texture timerTexture) {
        return new Bonus(x, y, BONUS_SIZE, texture, timerTexture, BONUS_LIFESPAN);
    }

    private BonusFactory() {
    }
}
