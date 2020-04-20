package io.cucumber.model.actor.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.DynamicActor;

import static io.cucumber.utils.constant.GameConstants.DEFAULT_ACCELERATION;
import static io.cucumber.utils.constant.GameConstants.DOWN_DIRECTION;
import static io.cucumber.utils.constant.GameConstants.LEFT_DIRECTION;
import static io.cucumber.utils.constant.GameConstants.NOT_MOVE;
import static io.cucumber.utils.constant.GameConstants.RIGHT_DIRECTION;

public class Hero extends DynamicActor {

    private byte directionY;
    private byte directionX;
    private byte previousDirectionX;
    private float acceleration;

    public Hero(float x, float y, float size, float horizontalVelocity, float verticalVelocity, TextureRegion region) {
        super(x, y, size, horizontalVelocity, verticalVelocity, region);
        this.directionY = DOWN_DIRECTION;
        this.directionX = NOT_MOVE;
        this.previousDirectionX = NOT_MOVE;
        this.acceleration = DEFAULT_ACCELERATION;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() + velocity.y * delta);
        setX(getX() + velocity.x * delta * directionX * acceleration);
    }

    public void moveLeft() {
        this.previousDirectionX = this.directionX;
        this.directionX = LEFT_DIRECTION;
        this.acceleration = DEFAULT_ACCELERATION;
    }

    public void moveLeft(float acceleration) {
        this.previousDirectionX = this.directionX;
        this.directionX = LEFT_DIRECTION;
        this.acceleration = acceleration;
    }

    public void moveRight() {
        this.previousDirectionX = this.directionX;
        this.directionX = RIGHT_DIRECTION;
        this.acceleration = DEFAULT_ACCELERATION;
    }

    public void moveRight(float acceleration) {
        this.previousDirectionX = this.directionX;
        this.directionX = RIGHT_DIRECTION;
        this.acceleration = acceleration;
    }

    public void stopLeft() {
        if (this.directionX != LEFT_DIRECTION && this.previousDirectionX != LEFT_DIRECTION) {
            this.directionX = NOT_MOVE;
        }
        else if (this.directionX != RIGHT_DIRECTION) {
            if (this.previousDirectionX == RIGHT_DIRECTION) {
                this.directionX = RIGHT_DIRECTION;
            } else {
                this.directionX = NOT_MOVE;
            }
        }
        this.previousDirectionX = NOT_MOVE;
        this.acceleration = DEFAULT_ACCELERATION;
    }

    public void stopRight() {
        if (this.directionX != RIGHT_DIRECTION && this.previousDirectionX != RIGHT_DIRECTION) {
            this.directionX = NOT_MOVE;
        }
        else if (this.directionX != LEFT_DIRECTION) {
            if (this.previousDirectionX == LEFT_DIRECTION) {
                this.directionX = LEFT_DIRECTION;
            } else {
                this.directionX = NOT_MOVE;
            }
        }
        this.previousDirectionX = NOT_MOVE;
        this.acceleration = DEFAULT_ACCELERATION;
    }

    public byte getDirectionY() {
        return directionY;
    }

    public void setDirectionY(byte directionY) {
        if (this.directionY != directionY) {
            velocity.y = velocity.y * -1;
            this.directionY = directionY;
        }
    }

    public byte getDirectionX() {
        return directionX;
    }
}
