package io.cucumber.model.component.button;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.Map;

public class SwitchImageButton<T> extends ImageButton {

    private T switcher;
    private Map<T, ImageButtonStyle> switcherValue;

    public SwitchImageButton(float x, float y, float width, float height, T switcher, Map<T, TextureRegion> switcherValue) {
        super(x, y, width, height, switcherValue.get(switcher));
        this.switcher = switcher;
        this.switcherValue = new HashMap<>(switcherValue.size());
        for (Map.Entry<T, TextureRegion> entry : switcherValue.entrySet()) {
            this.switcherValue.put(entry.getKey(), new ImageButtonStyle(null, null, null, new TextureRegionDrawable(entry.getValue()), null, null));
        }
    }

    public void setSwitcher(T switcher) {
        if (this.switcher != switcher) {
            this.switcher = switcher;
            setStyle(switcherValue.get(switcher));
        }
    }

    public void setTexture(Map<T, TextureRegion> switcherValue) {
        this.switcherValue.clear();
        for (Map.Entry<T, TextureRegion> entry : switcherValue.entrySet()) {
            this.switcherValue.put(entry.getKey(), new ImageButtonStyle(null, null, null, new TextureRegionDrawable(entry.getValue()), null, null));
        }
        setStyle(this.switcherValue.get(switcher));
    }
}
