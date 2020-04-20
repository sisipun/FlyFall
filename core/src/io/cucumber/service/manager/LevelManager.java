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
        levels.put(0, new LevelInfo(0, "images/hero.png", "images/enemy.png", "images/bonus.png", "images/white_background.png", "images/timer.png", "images/wall.png", "images/ok_button.png", "images/play_button.png", "images/choose_button.png", "images/not_button.png", "images/sound_off_button.png", "images/sound_on_button.png", "images/pause_button.png", "images/buy_button.png", "images/left_button.png", "images/right_button.png", "sounds/flip.wav", "sounds/bonus.wav", "sounds/death.mp3"));
        levels.put(1, new LevelInfo(1, "images/hero.png", "images/bonus.png", "images/enemy.png", "images/gray_background.png", "images/timer.png", "images/wall.png", "images/ok_button.png", "images/play_button.png", "images/choose_button.png", "images/not_button.png", "images/sound_off_button.png", "images/sound_on_button.png", "images/pause_button.png", "images/buy_button.png", "images/left_button.png", "images/right_button.png", "sounds/flip.wav", "sounds/bonus.wav", "sounds/death.mp3", 30));
        levels.put(2, new LevelInfo(2, "images/bonus.png", "images/enemy.png", "images/hero.png", "images/timer.png", "images/timer.png", "images/wall.png", "images/ok_button.png", "images/play_button.png", "images/choose_button.png", "images/not_button.png", "images/sound_off_button.png", "images/sound_on_button.png", "images/pause_button.png", "images/buy_button.png", "images/left_button.png", "images/right_button.png", "sounds/flip.wav", "sounds/bonus.wav", "sounds/death.mp3", 60));
        levels.put(3, new LevelInfo(3, "images/ghost_hero.png", "images/ghost_enemy.png", "images/ghost_bonus.png", "images/ghost_background.png",  "images/timer.png", "images/wall.png", "images/ok_button.png", "images/play_button.png", "images/choose_button.png", "images/not_button.png", "images/sound_off_button.png", "images/sound_on_button.png", "images/pause_button.png", "images/buy_button.png", "images/left_button.png", "images/right_button.png", "sounds/flip.wav", "sounds/bonus.wav", "sounds/death.mp3", 90));
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

    private LevelManager() {
    }
}
