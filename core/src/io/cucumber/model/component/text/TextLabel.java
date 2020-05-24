package io.cucumber.model.component.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import io.cucumber.model.bound.RectangleBound;

public class TextLabel extends Label {

    private RectangleBound bound;

    public TextLabel(float x, float y, CharSequence text, BitmapFont font) {
        super(text, new Label.LabelStyle(font, font.getColor()));
        this.bound = new RectangleBound(x, y, getPrefWidth(), getPrefHeight());
        setBounds(
                bound.getAlignX(),
                bound.getAlignY(),
                bound.getWidth(),
                bound.getHeight()
        );
    }

    @Override
    public void setText(CharSequence newText) {
        super.setText(newText);
        this.bound.setWidth(getPrefWidth());
        this.bound.setHeight(getPrefHeight());
        setBounds(
                bound.getAlignX(),
                bound.getAlignY(),
                bound.getWidth(),
                bound.getHeight()
        );
    }
}
