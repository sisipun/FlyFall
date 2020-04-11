package io.cucumber.model.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.cucumber.model.base.DynamicActor;

import static io.cucumber.utils.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.utils.constant.GameConstants.MILLIS_IN_SECOND;
import static io.cucumber.utils.constant.GameConstants.SCREEN_WIDTH;
import static io.cucumber.utils.constant.GameConstants.TIMER_HEIGHT;
import static io.cucumber.utils.constant.GameConstants.TIMER_MARGIN_WIDTH;

public class Bonus extends DynamicActor {

    private float lifespan;
    private Texture timerTexture;

    public Bonus(float x, float y, float size, Texture texture, Texture timerTexture, float lifespan) {
        super(x, y, size, 0, 0, texture);
        this.timerTexture = timerTexture;
        this.lifespan = lifespan;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float lifespanFactor = (BONUS_LIFESPAN - lifespan) / BONUS_LIFESPAN;
        batch.draw(
                currentTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );
        batch.draw(
                timerTexture,
                SCREEN_WIDTH / 2,
                TIMER_HEIGHT,
                (SCREEN_WIDTH / 2 - TIMER_MARGIN_WIDTH) * (1F - lifespanFactor),
                TIMER_HEIGHT
        );
        batch.draw(
                timerTexture,
                TIMER_MARGIN_WIDTH + ((SCREEN_WIDTH / 2 - TIMER_MARGIN_WIDTH) * lifespanFactor),
                TIMER_HEIGHT,
                (SCREEN_WIDTH / 2 - TIMER_MARGIN_WIDTH) * (1F - lifespanFactor),
                TIMER_HEIGHT
        );
    }

    @Override
    public void act(float delta) {
        lifespan -= delta * MILLIS_IN_SECOND;
    }

    public float getLifespan() {
        return lifespan;
    }
}
