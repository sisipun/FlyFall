package io.cucumber.model.component;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextLabel extends Label {

    public TextLabel(float x, float y, CharSequence text, BitmapFont font) {
        super(text, new Label.LabelStyle(font, font.getColor()));
        setBounds(x, y, 0F, 0F);
    }
}
