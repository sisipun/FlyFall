package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.IntMap;
import io.cucumber.model.texture.LevelAssets;
import io.cucumber.model.texture.LevelInfo;

import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class LevelManager {

    private static final int TEXTURE_LEVELS_COUNT = 4;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);

    private static IntMap<LevelInfo> levels = new IntMap<LevelInfo>(TEXTURE_LEVELS_COUNT);

    static {
        levels.put(0, new LevelInfo(0, "hero.png", "enemy.png", "bonus.png", "white_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3"));
        levels.put(1, new LevelInfo(1, "hero.png", "bonus.png", "enemy.png", "gray_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", 100));
        levels.put(2, new LevelInfo(2, "bonus.png", "enemy.png", "hero.png", "timer.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", 150));
        levels.put(3, new LevelInfo(3, "ghost_hero.png", "ghost_enemy.png", "ghost_bonus.png", "ghost_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", 200));

        for (LevelInfo levelInfo : levels.values()) {
            LevelAssets level = get(levelInfo.getId());
            level.dispose();
        }
    }

    public static LevelAssets get(int id) {
        if (id >= TEXTURE_LEVELS_COUNT) {
            id = 0;
        }

        LevelInfo levelInfo = levels.get(id);
        return new LevelAssets(levelInfo, isActive(levelInfo.getId()));
    }

    public static LevelAssets getNext(LevelAssets current) {
        int nextId = current.getId() + 1;

        if (nextId >= TEXTURE_LEVELS_COUNT) {
            nextId = 0;
        }

        return get(nextId);
    }

    public static LevelAssets getPrevious(LevelAssets current) {
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
