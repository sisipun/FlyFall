package io.cucumber.model.component.button;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.cucumber.model.bound.Bound2D;
import io.cucumber.model.bound.RectangleBound;

public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton {

    public ImageButton(float x, float y, float width, float height, TextureRegion region) {
        super(new TextureRegionDrawable(region));
        Bound2D<Rectangle> bound = new RectangleBound(x, y, width, height);
        setBounds(
                bound.getAlignX(),
                bound.getAlignY(),
                bound.getWidth(),
                bound.getHeight()
        );
    }

    public void setTexture(TextureRegion region) {
        setStyle(new ImageButtonStyle(null, null, null, new TextureRegionDrawable(region), null, null));
    }
}
