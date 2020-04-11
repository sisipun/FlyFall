package io.cucumber.utils.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.List;

public class NumbersHelper {

    private static List<Texture> numbersSymbols = Arrays.asList(
            new Texture("0.png"),
            new Texture("1.png"),
            new Texture("2.png"),
            new Texture("3.png"),
            new Texture("4.png"),
            new Texture("5.png"),
            new Texture("6.png"),
            new Texture("7.png"),
            new Texture("8.png"),
            new Texture("9.png")
    );

    public static Array<Texture> getTextures(int value) {
        Array<Texture> textures = new Array<Texture>();
        if (value == 0) {
            textures.add(numbersSymbols.get(0));
        }

        while (value > 0) {
            int number = value % 10;
            textures.add(numbersSymbols.get(number));
            value /= 10;
        }
        textures.reverse();

        return textures;
    }

    private NumbersHelper() {
    }
}
