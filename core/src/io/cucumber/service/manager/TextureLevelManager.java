package io.cucumber.service.manager;

import com.badlogic.gdx.utils.IntMap;
import io.cucumber.model.texture.TextureLevel;
import io.cucumber.model.texture.TextureLevelInfo;

public class TextureLevelManager {

    private static final int TEXTURE_LEVELS_COUNT = 4;

    private static IntMap<TextureLevel> levels = new IntMap<TextureLevel>(TEXTURE_LEVELS_COUNT);

    static {
        levels.put(0, new TextureLevel(0, new TextureLevelInfo("hero.png", "enemy.png", "bonus.png", "white_background.png", 0)));
        levels.put(1, new TextureLevel(1, new TextureLevelInfo("hero.png", "bonus.png", "enemy.png", "gray_background.png", 100)));
        levels.put(2, new TextureLevel(2, new TextureLevelInfo("bonus.png", "enemy.png", "hero.png", "timer.png", 100)));
        levels.put(3, new TextureLevel(3, new TextureLevelInfo("ghost_hero.png", "ghost_enemy.png", "ghost_bonus.png", "ghost_background.png", 500)));
    }

    public static TextureLevel get(int id) {
        return levels.get(id);
    }

    public static TextureLevel getNext(int id) {
        id++;
        if (id > TEXTURE_LEVELS_COUNT - 1) {
            id = 0;
        }
        return get(id);
    }

    public static TextureLevel getPrevious(int id) {
        id--;
        if (id < 0) {
            id = TEXTURE_LEVELS_COUNT - 1;
        }
        return get(id);
    }
}
