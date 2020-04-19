package io.cucumber.model.component.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import io.cucumber.model.bound.Bound2D;
import io.cucumber.model.bound.RectangleBound;
import io.cucumber.utils.helper.TextureHelper;

public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton {

    public ImageButton(float x, float y, float width, float height, Texture texture) {
        super(TextureHelper.toDrawable(texture));
        Bound2D<Rectangle> bound = new RectangleBound(x, y, width, height);
        setBounds(
                bound.getAlignX(),
                bound.getAlignY(),
                bound.getWidth(),
                bound.getHeight()
        );
    }
}
