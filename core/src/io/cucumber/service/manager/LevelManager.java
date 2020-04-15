package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.IntMap;
import io.cucumber.model.level.LevelAssets;
import io.cucumber.model.level.LevelInfo;

import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class LevelManager {

    private static final int LEVEL_ASSETS_COUNT = 4;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);

    private static IntMap<LevelInfo> levels = new IntMap<LevelInfo>(LEVEL_ASSETS_COUNT);
    private static IntMap<LevelAssets> levelAssets = new IntMap<LevelAssets>(LEVEL_ASSETS_COUNT);

    static {
        levels.put(0, new LevelInfo(0, "hero.png", "enemy.png", "bonus.png", "white_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", "title_font.ttf"));
        levels.put(1, new LevelInfo(1, "hero.png", "bonus.png", "enemy.png", "gray_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", "title_font.ttf", 100));
        levels.put(2, new LevelInfo(2, "bonus.png", "enemy.png", "hero.png", "timer.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", "title_font.ttf", 150));
        levels.put(3, new LevelInfo(3, "ghost_hero.png", "ghost_enemy.png", "ghost_bonus.png", "ghost_background.png", "timer.png", "wall.png", "ok_button.png", "play_button.png", "choose_button.png", "not_button.png", "sound_off_button.png", "sound_on_button.png", "pause_button.png", "buy_button.png", "flip.wav", "bonus.wav", "death.mp3", "title_font.ttf", 200));
    }

    public static void loadLevels() {
        for (LevelInfo levelInfo : levels.values()) {
            LevelAssets level = new LevelAssets(levelInfo, isActive(levelInfo.getId()));
            levelAssets.put(levelInfo.getId(), level);
        }
    }

    public static void removeLevels() {
        for (LevelAssets levelAsset : levelAssets.values()) {
            levelAsset.dispose();
        }
    }

    public static LevelAssets get(int id) {
        if (id >= LEVEL_ASSETS_COUNT) {
            id = 0;
        }

        return levelAssets.get(id);
    }

    public static LevelAssets getNext(LevelAssets current) {
        int nextId = current.getId() + 1;

        if (nextId >= LEVEL_ASSETS_COUNT) {
            nextId = 0;
        }

        return get(nextId);
    }

    public static LevelAssets getPrevious(LevelAssets current) {
        int previousId = current.getId() - 1;

        if (previousId < 0) {
            previousId = LEVEL_ASSETS_COUNT - 1;
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
