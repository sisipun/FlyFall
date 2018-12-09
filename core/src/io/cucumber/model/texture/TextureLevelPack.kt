package io.cucumber.model.texture

import com.badlogic.gdx.graphics.Texture

enum class TextureLevelPack(val id: Int, val value: TextureLevel, val previous: Int, val next: Int) {

    COMMON(1, TextureLevel(Texture("hero.png"), Texture("enemy.png"), Texture("bonus.png"), Texture("white_background.png")), 3, 2),
    REVERT(2, TextureLevel(Texture("hero.png"), Texture("bonus.png"), Texture("enemy.png"), Texture("gray_background.png")), 1, 3),
    GHOST(3, TextureLevel(Texture("ghost_hero.png"), Texture("ghost_enemy.png"), Texture("ghost_bonus.png"), Texture("ghost_background.png")), 2, 1),
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