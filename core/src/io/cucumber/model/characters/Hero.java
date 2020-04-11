package io.cucumber.model.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import io.cucumber.model.base.DynamicActor;

import static io.cucumber.utils.constant.GameConstants.DOWN_DIRECTION;

public class Hero extends DynamicActor {

    private byte direction;

    public Hero(float x, float y, float size, float horizontalVelocity, float verticalVelocity, Texture texture) {
        super(x, y, size, horizontalVelocity, verticalVelocity, texture);
        this.direction = DOWN_DIRECTION;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                currentTexture,
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );
    }

    @Override
    public void act(float delta) {
        setY(getY() + velocity.y * delta);
    }

    public void moveLeft() {
        setX(getX() + velocity.x * -1);
    }

    public void moveRight() {
        setX(getX() + velocity.x);
    }

    public void setDirection(byte direction) {
        if (this.direction != direction) {
            velocity.y = velocity.y * -1;
            this.direction = direction;
        }
    }
}
