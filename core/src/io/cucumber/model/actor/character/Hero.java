package io.cucumber.model.actor.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.base.AnimationActor;

import static io.cucumber.utils.constant.GameConstants.DEFAULT_ACCELERATION;
import static io.cucumber.utils.constant.GameConstants.DOWN_DIRECTION;
import static io.cucumber.utils.constant.GameConstants.LEFT_DIRECTION;
import static io.cucumber.utils.constant.GameConstants.NOT_MOVE;
import static io.cucumber.utils.constant.GameConstants.RIGHT_DIRECTION;

public class Hero extends AnimationActor {

    private byte directionY;
    private byte directionX;
    private byte previousDirectionX;
    private float acceleration;
    private Animation<TextureRegion> explodeAnimation;
    private boolean explode;

    public Hero(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                Animation<TextureRegion> animation, Animation<TextureRegion> explodeAnimation) {
        super(x, y, size, horizontalVelocity, verticalVelocity, animation, RIGHT_DIRECTION);
        this.directionY = DOWN_DIRECTION;
        this.directionX = NOT_MOVE;
        this.previousDirectionX = NOT_MOVE;
        this.acceleration = DEFAULT_ACCELERATION;
        this.explodeAnimation = explodeAnimation;
        this.explode = false;
        this.setScale(1f, 1f);
    }

    public Hero init(float x, float y, float size, float horizontalVelocity, float verticalVelocity,
                     Animation<TextureRegion> animation, Animation<TextureRegion> explodeAnimation) {
        super.init(x, y, size, horizontalVelocity, verticalVelocity, animation, RIGHT_DIRECTION);
        this.directionY = DOWN_DIRECTION;
        this.directionX = NOT_MOVE;
        this.previousDirectionX = NOT_MOVE;
        this.acceleration = DEFAULT_ACCELERATION;
        this.explodeAnimation = explodeAnimation;
        this.explode = false;
        this.setScale(1f, 1f);
        return this;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() + velocity.y * delta);
        setX(getX() + velocity.x * delta * directionX * acceleration);
    }

    public void moveLeft() {
        if (this.explode) {
            return;
        }
        this.previousDirectionX = this.directionX;
        this.directionX = LEFT_DIRECTION;
        this.acceleration = DEFAULT_ACCELERATION;
        this.orientation = LEFT_DIRECTION;
    }

    public void moveLeft(float acceleration) {
        if (this.explode) {
            return;
        }
        moveLeft();
        this.acceleration = acceleration;
    }

    public void moveRight() {
        if (this.explode) {
            return;
        }
        this.previousDirectionX = this.directionX;
        this.directionX = RIGHT_DIRECTION;
        this.acceleration = DEFAULT_ACCELERATION;
        this.orientation = RIGHT_DIRECTION;
    }

    public void moveRight(float acceleration) {
        if (this.explode) {
            return;
        }
        moveRight();
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

    public void explode() {
        this.explode = true;
        setAnimation(this.explodeAnimation);
        this.setScale(2f, 2f);
    }

    public boolean isExplode() {
        return this.explode && this.isAnimationFinished();
    }

    @Override
    public void reset() {
        super.reset();
        directionY = 0;
        directionX = 0;
        previousDirectionX = 0;
        acceleration = 0f;
    }
}
