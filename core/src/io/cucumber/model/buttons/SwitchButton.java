package io.cucumber.model.buttons;

import com.badlogic.gdx.graphics.Texture;

import io.cucumber.model.base.Button;

public class SwitchButton extends Button {

    private final Texture firstTexture;
    private final Texture secondTexture;
    private boolean switcher;

    public SwitchButton(float x, float y, float width, float height, Texture firstTexture, Texture secondTexture, boolean switcher) {
        super(x, y, width, height, switcher ? firstTexture : secondTexture);
        this.switcher = switcher;
        this.firstTexture = firstTexture;
        this.secondTexture = secondTexture;
    }

    public void setSwitcher(boolean switcher) {
        if (this.switcher != switcher) {
            this.switcher = switcher;
            this.currentTexture = switcher ? firstTexture : secondTexture;
        }
    }
}
