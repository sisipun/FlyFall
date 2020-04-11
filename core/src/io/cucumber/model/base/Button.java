package io.cucumber.model.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.cucumber.model.bound.RectangleBound;

public class Button extends Actor<Rectangle> {

    protected Texture currentTexture;

    public Button(float x, float y, float width, float height, Texture currentTexture) {
        super(new RectangleBound(x, y, width, height));
        this.currentTexture = currentTexture;
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

    public boolean isTouched(Vector2 position) {
        return contains(position);
    }
}
