package io.cucumber.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerHelper {

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        settings.paddingX = 0;
        settings.paddingY = 0;
        TexturePacker.process(settings, "android/assets/images", "android/assets/atlas", "game");
    }
}
