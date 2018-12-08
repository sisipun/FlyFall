package io.cucumber.model.texture

enum class TextureLevelPack(val id: Int, val value: TextureLevel, val previous: Int, val next: Int) {

    COMMON(1, TextureLevel("hero.png", "enemy.png", "bonus.png"), 3, 2),
    CUSTOM_1(2, TextureLevel("hero.png", "bonus.png", "enemy.png"), 1, 3),
    CUSTOM_2(3, TextureLevel("enemy.png", "bonus.png", "hero.png"), 2, 1)
    ;


    companion object {
        fun getById(id: Int): TextureLevelPack {
            for (pack in values()) {
                if (pack.id == id) {
                    return pack
                }
            }
            throw IllegalArgumentException("TextureLevelPack with id $id not found")
        }
    }

}