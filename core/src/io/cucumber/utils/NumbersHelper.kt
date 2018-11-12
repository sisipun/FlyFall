package io.cucumber.utils

import com.badlogic.gdx.graphics.Texture

object NumbersHelper {

    private val numbersSymbols = mapOf(
        '1' to Texture("1.png"),
        '2' to Texture("2.png"),
        '3' to Texture("3.png"),
        '4' to Texture("4.png"),
        '5' to Texture("5.png"),
        '6' to Texture("6.png"),
        '7' to Texture("7.png"),
        '8' to Texture("8.png"),
        '9' to Texture("9.png"),
        '0' to Texture("0.png")
    )

    fun getTextures(value: Int): List<Texture> =
        value.toString().toCharArray().map { numbersSymbols[it]!! }

}