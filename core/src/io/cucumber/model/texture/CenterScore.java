package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.g2d.Batch;

public class CenterScore extends Score {

    public CenterScore(float x, float y, float width, float height, int score) {
        super(x, y, width, height, score);
    }

    @Override
    protected void drawItem(Batch batch, int index) {
        batch.draw(
                scoreTextures.get(index),
                getX() + (index - scoreTextures.size / 2) * getWidth(),
                getY(),
                getWidth(),
                getHeight()
        );
    }
}
