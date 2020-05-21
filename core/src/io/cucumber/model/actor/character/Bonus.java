package io.cucumber.model.actor.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.AnimationActor;

import static io.cucumber.utils.constant.GameConstants.BONUS_LIFESPAN;
import static io.cucumber.utils.constant.GameConstants.MILLIS_IN_SECOND;
import static io.cucumber.utils.constant.GameConstants.TIMER_HEIGHT;
import static io.cucumber.utils.constant.GameConstants.TIMER_MARGIN_WIDTH;

public class Bonus extends AnimationActor {

    private float lifespan;
    private TextureRegion timerRegion;

    public Bonus(float x, float y, float size, Animation<TextureRegion> animation,
                 TextureRegion timerRegion, float lifespan) {
        super(x, y, size, 0, 0, animation, (byte) 0);
        this.timerRegion = timerRegion;
        this.lifespan = lifespan;
    }

    public Bonus init(float x, float y, float size, Animation<TextureRegion> animation,
                      TextureRegion timerRegion, float lifespan) {
        super.init(x, y, size, 0, 0, animation, (byte) 0);
        this.timerRegion = timerRegion;
        this.lifespan = lifespan;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float lifespanFactor = (BONUS_LIFESPAN - lifespan) / BONUS_LIFESPAN;
        batch.draw(
                timerRegion,
                TIMER_MARGIN_WIDTH + ((this.getStage().getCamera().viewportWidth / 2 - TIMER_MARGIN_WIDTH) * lifespanFactor),
                TIMER_HEIGHT,
                (this.getStage().getCamera().viewportWidth - TIMER_MARGIN_WIDTH) * (1F - lifespanFactor),
                TIMER_HEIGHT
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        lifespan -= delta * MILLIS_IN_SECOND;
    }

    public float getLifespan() {
        return lifespan;
    }

    @Override
    public void reset() {
        super.reset();
        lifespan = 0f;
        timerRegion = null;
    }
}
