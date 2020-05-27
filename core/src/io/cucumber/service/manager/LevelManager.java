package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.IntMap;

import io.cucumber.model.level.CommonAssets;
import io.cucumber.model.level.LevelAssets;

import static io.cucumber.utils.constant.GameConstants.BONUS_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.ENEMY_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.HERO_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class LevelManager {

    private static final int LEVEL_ASSETS_COUNT = 6;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
    private static TextureAtlas atlas;
    private static CommonAssets commonAssets;

    private static IntMap<LevelAssets> levelAssets = new IntMap<LevelAssets>(LEVEL_ASSETS_COUNT);

    public static void loadLevels() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        commonAssets = new CommonAssets(
                atlas,
                "timer",
                "sounds/flip.wav",
                "sounds/bonus.wav",
                "sounds/death.mp3",
                "sounds/background.mp3"
        );
        levelAssets.put(0, new LevelAssets(
                atlas,
                commonAssets,
                0,
                "hero",
                HERO_ANIMATION_FRAME_DURATION,
                "enemy",
                ENEMY_ANIMATION_FRAME_DURATION,
                false,
                "bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "background",
                "wall",
                "yellow_ok_button",
                "yellow_play_button",
                "yellow_choose_button",
                "yellow_not_button",
                "yellow_sound_off_button",
                "yellow_sound_on_button",
                "yellow_pause_button",
                "yellow_buy_button",
                "gray_buy_button",
                "yellow_left_button",
                "yellow_right_button",
                "yellow_restart_button",
                "yellow_home_button",
                "yellow_acc_button",
                "yellow_tap_button",
                true
        ));
        levelAssets.put(1, new LevelAssets(
                atlas,
                commonAssets,
                1,
                "ghost_hero",
                HERO_ANIMATION_FRAME_DURATION,
                "ghost_enemy",
                ENEMY_ANIMATION_FRAME_DURATION,
                true,
                "ghost_bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "ghost_background",
                "ghost_wall",
                "red_ok_button",
                "red_play_button",
                "red_choose_button",
                "red_not_button",
                "red_sound_off_button",
                "red_sound_on_button",
                "red_pause_button",
                "red_buy_button",
                "gray_buy_button",
                "red_left_button",
                "red_right_button",
                "red_restart_button",
                "red_home_button",
                "red_acc_button",
                "red_tap_button",
                false
        ));
        levelAssets.put(2, new LevelAssets(
                atlas,
                commonAssets,
                2,
                "sea_hero",
                HERO_ANIMATION_FRAME_DURATION,
                "sea_enemy",
                ENEMY_ANIMATION_FRAME_DURATION,
                true,
                "sea_bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "sea_background",
                "sea_wall",
                "yellow_ok_button",
                "yellow_play_button",
                "yellow_choose_button",
                "yellow_not_button",
                "yellow_sound_off_button",
                "yellow_sound_on_button",
                "yellow_pause_button",
                "yellow_buy_button",
                "gray_buy_button",
                "yellow_left_button",
                "yellow_right_button",
                "yellow_restart_button",
                "yellow_home_button",
                "yellow_acc_button",
                "yellow_tap_button",
                60,
                isActive(2),
                false
        ));
        levelAssets.put(3, new LevelAssets(
                atlas,
                commonAssets,
                3,
                "sky_hero",
                HERO_ANIMATION_FRAME_DURATION,
                "sky_enemy",
                ENEMY_ANIMATION_FRAME_DURATION,
                false,
                "sky_bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "sky_background",
                "sky_wall",
                "green_ok_button",
                "green_play_button",
                "green_choose_button",
                "green_not_button",
                "green_sound_off_button",
                "green_sound_on_button",
                "green_pause_button",
                "green_buy_button",
                "gray_buy_button",
                "green_left_button",
                "green_right_button",
                "green_restart_button",
                "green_home_button",
                "green_acc_button",
                "green_tap_button",
                90,
                isActive(3),
                false
        ));
        levelAssets.put(4, new LevelAssets(
                atlas,
                commonAssets,
                4,
                "space_hero",
                HERO_ANIMATION_FRAME_DURATION,
                "space_enemy",
                ENEMY_ANIMATION_FRAME_DURATION,
                false,
                "space_bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "space_background",
                "space_wall",
                "gray_ok_button",
                "gray_play_button",
                "gray_choose_button",
                "gray_not_button",
                "gray_sound_off_button",
                "gray_sound_on_button",
                "gray_pause_button",
                "gray_buy_button",
                "red_buy_button",
                "gray_left_button",
                "gray_right_button",
                "gray_restart_button",
                "gray_home_button",
                "gray_acc_button",
                "gray_tap_button",
                120,
                isActive(4),
                false
        ));
        levelAssets.put(5, new LevelAssets(
                atlas,
                commonAssets,
                5,
                "vika_hero",
                HERO_ANIMATION_FRAME_DURATION,
                "vika_enemy",
                ENEMY_ANIMATION_FRAME_DURATION,
                true,
                "vika_bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "vika_background",
                "vika_wall",
                "yellow_ok_button",
                "yellow_play_button",
                "yellow_choose_button",
                "yellow_not_button",
                "yellow_sound_off_button",
                "yellow_sound_on_button",
                "yellow_pause_button",
                "yellow_buy_button",
                "gray_buy_button",
                "yellow_left_button",
                "yellow_right_button",
                "yellow_restart_button",
                "yellow_home_button",
                "yellow_acc_button",
                "yellow_tap_button",
                true
        ));
    }

    public static void removeLevels() {
        commonAssets.dispose();
        atlas.dispose();
    }

    public static LevelAssets get(int id) {
        return get(id, 1);
    }

    public static LevelAssets get(int id, int skipBy) {
        if (id >= LEVEL_ASSETS_COUNT) {
            id = 0;
        } else if (id < 0) {
            id = LEVEL_ASSETS_COUNT - 1;
        }

        LevelAssets levelAssets = LevelManager.levelAssets.get(id);

        if (levelAssets.isHide()) {
            return get(id + skipBy, skipBy);
        }

        return levelAssets;
    }

    public static LevelAssets getNext(LevelAssets current) {
        int nextId = current.getId() + 1;

        if (nextId >= LEVEL_ASSETS_COUNT) {
            nextId = 0;
        }

        return get(nextId, 1);
    }

    public static LevelAssets getPrevious(LevelAssets current) {
        int previousId = current.getId() - 1;

        if (previousId < 0) {
            previousId = LEVEL_ASSETS_COUNT - 1;
        }

        return get(previousId, -1);
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
