package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.model.base.Actor;
import io.cucumber.model.bound.RectangleBound;
import io.cucumber.utils.helper.NumbersHelper;

public class Score extends Actor<Rectangle> {

    protected final Array<Texture> scoreTextures;
    private int score;

    public Score(float x, float y, float width, float height, int score) {
        super(new RectangleBound(x, y, width, height));
        this.score = score;
        this.scoreTextures = NumbersHelper.getTextures(score);
    }

    public void setScore(int score) {
        this.score = score;
        this.scoreTextures.clear();
        this.scoreTextures.addAll(NumbersHelper.getTextures(score));
    }

    public void addScore(int score) {
        setScore(this.score + score);
    }

    public int getScore() {
        return score;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < scoreTextures.size; i++) {
            drawItem(batch, i);
        }
    }

    protected void drawItem(Batch batch, int index) {
        batch.draw(
                scoreTextures.get(index),
                getX() + (index + 1) * getWidth(),
                getY(),
                getWidth(),
                getHeight()
        );
    }

    @Override
    public void dispose() {
        for (int i = 0; i < scoreTextures.size; i++) {
            scoreTextures.get(i).dispose();
        }
        super.dispose();
    }
}
