package io.cucumber.model.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class TextParams {

    private final FreeTypeFontGenerator.FreeTypeFontParameter values;

    public TextParams() {
        this.values = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public TextParams(int size, Color color) {
        this.values = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.values.size = size;
        this.values.color = color;
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter getValues() {
        return values;
    }
}
