package io.cucumber.model.texture

enum class TexturePacks(val value: TexturePack) {

    COMMON(TexturePack("hero.png", "enemy.png", "bonus.png")),
    CUSTOM(TexturePack("hero.png", "bonus.png", "enemy.png"))

}