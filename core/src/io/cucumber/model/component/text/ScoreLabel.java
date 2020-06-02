package io.cucumber.model.component.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import io.cucumber.model.base.HorizontalAlign;

public class ScoreLabel extends TextLabel {

    private int score;

    public ScoreLabel(float x, float y, int score, BitmapFont font) {
        super(x, y, String.valueOf(score), font);
        this.score = score;
    }

    public ScoreLabel(float x, float y, int score, BitmapFont font, HorizontalAlign align) {
        super(x, y, String.valueOf(score), font, align);
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
        setText(String.valueOf(this.score));
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setFont(BitmapFont font) {
        super.setStyle(new Label.LabelStyle(font, font.getColor()));
    }
}