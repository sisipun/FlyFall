package io.cucumber.model.button;

import com.badlogic.gdx.graphics.Texture;

import io.cucumber.utils.helper.TextureHelper;

public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton {

    public ImageButton(float x, float y, float width, float height, Texture texture) {
        super(TextureHelper.toDrawable(texture));
        setBounds(x, y, width, height);
    }

    public ImageButton(float x, float y, float width, float height, ImageButtonStyle style) {
        super(style);
        setBounds(x, y, width, height);
    }
}
