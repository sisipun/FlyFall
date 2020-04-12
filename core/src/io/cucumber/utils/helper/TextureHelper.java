package io.cucumber.utils.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureHelper {

    public static Drawable toDrawable(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private TextureHelper() {
    }
}
