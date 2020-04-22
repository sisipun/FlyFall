package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.IntMap;

import io.cucumber.model.level.CommonAssets;
import io.cucumber.model.level.LevelAssets;

import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class LevelManager {

    private static final int LEVEL_ASSETS_COUNT = 4;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
    private static TextureAtlas atlas;
    private static CommonAssets commonAssets;

    private static IntMap<LevelAssets> levelAssets = new IntMap<LevelAssets>(LEVEL_ASSETS_COUNT);

    public static void loadLevels() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        commonAssets = new CommonAssets(atlas, "timer", "wall", "ok_button", "play_button", "choose_button", "not_button", "sound_off_button", "sound_on_button", "pause_button", "buy_button", "left_button", "right_button", "sounds/flip.wav", "sounds/bonus.wav", "sounds/death.mp3");
        levelAssets.put(0, new LevelAssets(atlas, commonAssets, 0, "hero", "enemy", "bonus", "white_background"));
        levelAssets.put(1, new LevelAssets(atlas, commonAssets, 1, "hero", "bonus", "enemy", "gray_background", 30, isActive(1)));
        levelAssets.put(2, new LevelAssets(atlas, commonAssets, 2, "bonus", "enemy", "hero", "timer", 60, isActive(2)));
        levelAssets.put(3, new LevelAssets(atlas, commonAssets, 3, "ghost_hero", "ghost_enemy", "ghost_bonus", "ghost_background", 90, isActive(3)));
    }

    public static void removeLevels() {
        commonAssets.dispose();
        atlas.dispose();
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
