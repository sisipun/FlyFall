package io.cucumber.service.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.IntMap;

import io.cucumber.model.font.FontInfo;
import io.cucumber.model.font.FontParams;
import io.cucumber.utils.helper.FontHelper;

import static io.cucumber.utils.constant.GameConstants.DEFAULT_FONT;

public class FontManager {

    private static IntMap<FontInfo> fontsInfo = new IntMap<FontInfo>(FontType.values().length);
    private static IntMap<BitmapFont> fonts = new IntMap<BitmapFont>(FontType.values().length);

    static {
        fontsInfo.put(FontType.TITLE.ordinal(), new FontInfo(FontType.TITLE.ordinal(), DEFAULT_FONT, new FontParams(50, Color.GOLD)));
        fontsInfo.put(FontType.LABEL.ordinal(), new FontInfo(FontType.LABEL.ordinal(), DEFAULT_FONT, new FontParams(10, Color.GOLD)));
    }

    public static void loadFonts() {
        for (FontInfo fontInfo : fontsInfo.values()) {
            fonts.put(fontInfo.getId(), FontHelper.toFont(fontInfo.getFontPath(), fontInfo.getFontParams()));
        }
    }

    public static void removeFonts() {
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
    }

    public static BitmapFont get(FontType type) {
        return fonts.get(type.ordinal());
    }

    public enum FontType {
        TITLE,
        LABEL
    }

    private FontManager() {
    }
}
