package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.IntMap;
import io.cucumber.model.texture.TextureLevel;

import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class TextureLevelManager {

    private static final int TEXTURE_LEVELS_COUNT = 4;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);

    private static IntMap<TextureLevel> levels = new IntMap<TextureLevel>(TEXTURE_LEVELS_COUNT);

    static {
        levels.put(0, new TextureLevel(0, "hero.png", "enemy.png", "bonus.png", "white_background.png", 0, true));
        levels.put(1, new TextureLevel(1, "hero.png", "bonus.png", "enemy.png", "gray_background.png", 100, isActive(1)));
        levels.put(2, new TextureLevel(2, "bonus.png", "enemy.png", "hero.png", "timer.png", 100, isActive(2)));
        levels.put(3, new TextureLevel(3, "ghost_hero.png", "ghost_enemy.png", "ghost_bonus.png", "ghost_background.png", 100, isActive(3)));
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

    public static void activate(int id) {
        get(id).activate();
        preferences.putBoolean(String.format(IS_ACTIVE_PATTERN, id), true);
        preferences.flush();
    }

    private static boolean isActive(int id) {
        return preferences.getBoolean(String.format(IS_ACTIVE_PATTERN, id));
    }
}
