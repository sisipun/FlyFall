package io.cucumber.model.character;

import com.badlogic.gdx.graphics.Texture;

import io.cucumber.model.base.DynamicActor;

import static io.cucumber.utils.constant.GameConstants.DOWN_DIRECTION;
import static io.cucumber.utils.constant.GameConstants.LEFT_DIRECTION;
import static io.cucumber.utils.constant.GameConstants.NOT_MOVE;
import static io.cucumber.utils.constant.GameConstants.RIGHT_DIRECTION;

public class Hero extends DynamicActor {

    private byte directionY;
    private byte directionX;

    public Hero(float x, float y, float size, float horizontalVelocity, float verticalVelocity, Texture texture) {
        super(x, y, size, horizontalVelocity, verticalVelocity, texture);
        this.directionY = DOWN_DIRECTION;
        this.directionX = NOT_MOVE;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() + velocity.y * delta);
        setX(getX() + velocity.x * delta * directionX);
    }

    public void moveLeft() {
        this.directionX = LEFT_DIRECTION;
    }

    public void moveRight() {
        this.directionX = RIGHT_DIRECTION;
    }

    public void stop() {
        this.directionX = NOT_MOVE;
    }

    public byte getMoveDirection() {
        return directionX;
    }

    public void setDirectionY(byte directionY) {
        if (this.directionY != directionY) {
            velocity.y = velocity.y * -1;
            this.directionY = directionY;
        }
    }
}
