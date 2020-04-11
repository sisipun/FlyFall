package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.IntMap;
import io.cucumber.model.texture.TextureLevel;
import io.cucumber.model.texture.TextureLevelInfo;

import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class TextureLevelManager {

    private static final int TEXTURE_LEVELS_COUNT = 4;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);

    private static IntMap<TextureLevelInfo> levels = new IntMap<TextureLevelInfo>(TEXTURE_LEVELS_COUNT);

    static {
        levels.put(0, new TextureLevelInfo(0, "hero.png", "enemy.png", "bonus.png", "white_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png"));
        levels.put(1, new TextureLevelInfo(1, "hero.png", "bonus.png", "enemy.png", "gray_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", 100));
        levels.put(2, new TextureLevelInfo(2, "bonus.png", "enemy.png", "hero.png", "timer.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", 150));
        levels.put(3, new TextureLevelInfo(3, "ghost_hero.png", "ghost_enemy.png", "ghost_bonus.png", "ghost_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", 200));

        for (TextureLevelInfo levelInfo : levels.values()) {
            TextureLevel level = get(levelInfo.getId());
            level.dispose();
        }
    }

    public static TextureLevel get(int id) {
        if (id >= TEXTURE_LEVELS_COUNT) {
            id = 0;
        }

        TextureLevelInfo levelInfo = levels.get(id);
        return new TextureLevel(levelInfo, isActive(levelInfo.getId()));
    }

    public static TextureLevel getNext(TextureLevel current) {
        int nextId = current.getId() + 1;

        if (nextId >= TEXTURE_LEVELS_COUNT) {
            nextId = 0;
        }

        return get(nextId);
    }

    public static TextureLevel getPrevious(TextureLevel current) {
        int previousId = current.getId() - 1;

        if (previousId < 0) {
            previousId = TEXTURE_LEVELS_COUNT - 1;
        }
        return get(previousId);
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
