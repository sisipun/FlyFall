package io.cucumber.model.characters;

import com.badlogic.gdx.graphics.Texture;
import io.cucumber.model.base.DynamicActor;

import static io.cucumber.utils.constant.GameConstants.DOWN_DIRECTION;

public class Hero extends DynamicActor {

    private byte direction;

    public Hero(float x, float y, float size, float horizontalVelocity, float verticalVelocity, Texture texture) {
        super(x, y, size, horizontalVelocity, verticalVelocity, texture);
        this.direction = DOWN_DIRECTION;
    }

    @Override
    public void update(float delta) {
        bound.y += velocity.y * delta;
    }

    public void moveLeft() {
        bound.x += velocity.x * -1;
    }

    public void moveRight() {
        bound.x += velocity.x;
    }

    public void setDirection(byte direction) {
        if (this.direction != direction) {
            velocity.y = velocity.y * -1;
            this.direction = direction;
        }
    }
}
