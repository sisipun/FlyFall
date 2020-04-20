package io.cucumber.model.component.button;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SwitchImageButton extends ImageButton {

    private final ImageButtonStyle firstStyle;
    private final ImageButtonStyle secondStyle;
    private boolean switcher;

    public SwitchImageButton(float x, float y, float width, float height, TextureRegion firstRegion,
                             TextureRegion secondRegion, boolean switcher) {
        super(x, y, width, height, switcher ? firstRegion : secondRegion);
        this.switcher = switcher;
        this.firstStyle = new ImageButtonStyle(null, null, null, new TextureRegionDrawable(firstRegion), null, null);
        this.secondStyle = new ImageButtonStyle(null, null, null, new TextureRegionDrawable(secondRegion), null, null);
    }

    public void setSwitcher(boolean switcher) {
        if (this.switcher != switcher) {
            this.switcher = switcher;
            setStyle(switcher ? firstStyle : secondStyle);
        }
    }
}
