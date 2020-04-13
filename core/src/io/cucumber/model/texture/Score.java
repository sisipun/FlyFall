package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import io.cucumber.model.base.Actor;
import io.cucumber.model.bound.RectangleBound;
import io.cucumber.utils.helper.NumbersHelper;

public class Score extends Actor<Rectangle> {

    private final Array<Texture> scoreTextures;
    private final ScoreItemDrawer itemDrawer;
    private int score;

    public Score(float x, float y, float width, float height, int score) {
        this(x, y, width, height, score, ScoreItemAlign.RIGHT);
    }

    public Score(float x, float y, float width, float height, int score, ScoreItemAlign align) {
        super(new RectangleBound(x, y, width, height));
        this.score = score;
        this.scoreTextures = NumbersHelper.getTextures(score);
        if (ScoreItemAlign.CENTER.equals(align)) {
            this.itemDrawer = new CenterScoreItemDrawer();
        } else {
            this.itemDrawer = new RightScoreItemDrawer();
        }
    }

    public void addScore(int score) {
        this.score += score;
        this.scoreTextures.clear();
        this.scoreTextures.addAll(NumbersHelper.getTextures(this.score));
    }

    public int getScore() {
        return score;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < scoreTextures.size; i++) {
            itemDrawer.drawItem(batch, scoreTextures, i, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public boolean remove() {
        for (int i = 0; i < scoreTextures.size; i++) {
            scoreTextures.get(i).dispose();
        }
        return super.remove();
    }

    public enum ScoreItemAlign {
        CENTER,
        RIGHT
    }

    interface ScoreItemDrawer {
        void drawItem(Batch batch, Array<Texture> scoreTextures, int index, float x, float y, float width, float height);
    }

    static class CenterScoreItemDrawer implements ScoreItemDrawer {

        public void drawItem(Batch batch, Array<Texture> scoreTextures, int index, float x, float y, float width, float height) {
            batch.draw(
                    scoreTextures.get(index),
                    x + (index - scoreTextures.size / 2) * width,
                    y,
                    width,
                    height
            );
        }
    }

    static class RightScoreItemDrawer implements ScoreItemDrawer {

        public void drawItem(Batch batch, Array<Texture> scoreTextures, int index, float x, float y, float width, float height) {
            batch.draw(
                    scoreTextures.get(index),
                    x + (index + 1) * width,
                    y,
                    width,
                    height
            );
        }
    }

}