package io.cucumber.model.component.button;

import com.badlogic.gdx.graphics.Texture;

import io.cucumber.utils.helper.TextureHelper;

public class SwitchImageButton extends ImageButton {

    private final ImageButtonStyle firstStyle;
    private final ImageButtonStyle secondStyle;
    private boolean switcher;

    public SwitchImageButton(float x, float y, float width, float height, Texture firstTexture,
                             Texture secondTexture, boolean switcher) {
        super(x, y, width, height, switcher ? firstTexture : secondTexture);
        this.switcher = switcher;
        this.firstStyle = new ImageButtonStyle(null, null, null, TextureHelper.toDrawable(firstTexture), null, null);
        this.secondStyle = new ImageButtonStyle(null, null, null, TextureHelper.toDrawable(secondTexture), null, null);
    }

    public void setSwitcher(boolean switcher) {
        if (this.switcher != switcher) {
            this.switcher = switcher;
            setStyle(switcher ? firstStyle : secondStyle);
        }
    }
}
