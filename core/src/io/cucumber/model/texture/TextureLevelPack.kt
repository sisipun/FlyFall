package io.cucumber.model.texture

enum class TextureLevelPack(val id: Int, val value: TextureLevel) {

    COMMON(1, TextureLevel("hero.png", "enemy.png", "bonus.png")),
    CUSTOM(2, TextureLevel("hero.png", "bonus.png", "enemy.png"))
    ;


    fun getById(id: Int): TextureLevelPack {
        for (pack in values()) {
            if (pack.id == id) {
                return pack
            }
        }
        throw IllegalArgumentException("TextureLevelPack with id $id not found")
    }

}