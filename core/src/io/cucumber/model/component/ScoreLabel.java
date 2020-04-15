package io.cucumber.model.component;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ScoreLabel extends TextLabel {

    private int score;

    public ScoreLabel(float x, float y, int score, BitmapFont font) {
        super(x, y, String.valueOf(score), font);
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
        setText(String.valueOf(this.score));
    }

    public int getScore() {
        return score;
    }
}