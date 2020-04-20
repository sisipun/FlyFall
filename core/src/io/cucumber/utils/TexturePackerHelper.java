package io.cucumber.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerHelper {

    public static void main(String[] args) {
        TexturePacker.process("android/assets/images", "android/assets/atlas", "game");
    }

}
