package io.cucumber.service.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.IntMap;

import io.cucumber.model.font.FontParams;
import io.cucumber.model.level.CommonAssets;
import io.cucumber.model.level.LevelAssets;
import io.cucumber.utils.helper.FontHelper;

import static io.cucumber.utils.constant.GameConstants.BIG_HEART_SIZE;
import static io.cucumber.utils.constant.GameConstants.BONUS_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.DEFAULT_FONT;
import static io.cucumber.utils.constant.GameConstants.ENEMY_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.EXPLOSION_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.HEART_SIZE;
import static io.cucumber.utils.constant.GameConstants.HERO_ANIMATION_FRAME_DURATION;
import static io.cucumber.utils.constant.GameConstants.IS_ACTIVE_PATTERN;
import static io.cucumber.utils.constant.GameConstants.PREFERENCE_NAME;

public class LevelManager {

    private static final int LEVEL_ASSETS_COUNT = 2;
    private static Preferences preferences = Gdx.app.getPreferences(PREFERENCE_NAME);
    private static TextureAtlas atlas;
    private static CommonAssets commonAssets;

    private static IntMap<LevelAssets> levelAssets = new IntMap<LevelAssets>(LEVEL_ASSETS_COUNT);

    public static void loadLevels() {
        atlas = new TextureAtlas(Gdx.files.internal("atlas/game.atlas"));
        commonAssets = new CommonAssets(
                atlas,
                "timer",
                "heart",
                "explosion",
                EXPLOSION_ANIMATION_FRAME_DURATION,
                "sounds/flip.wav",
                "sounds/bonus.wav",
                "sounds/death.mp3",
                "sounds/background.mp3"
        );
        levelAssets.put(0, new LevelAssets(
                atlas,
                commonAssets,
                0,
                "PAST",
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
                "yellow_normal_button",
                "yellow_hard_button",
                "gray_hard_button",
                FontHelper.toFont(DEFAULT_FONT, new FontParams(50, Color.YELLOW)),
                FontHelper.toFont(DEFAULT_FONT, new FontParams(10, Color.YELLOW)),
                FontHelper.toFont(DEFAULT_FONT, new FontParams(30, Color.YELLOW)),
                HEART_SIZE,
                false
        ));
        levelAssets.put(1, new LevelAssets(
                atlas,
                commonAssets,
                1,
                "CURRENT AND FUTURE",
                "love_hero",
                HERO_ANIMATION_FRAME_DURATION,
                "bonus",
                ENEMY_ANIMATION_FRAME_DURATION,
                true,
                "love_bonus",
                BONUS_ANIMATION_FRAME_DURATION,
                "love_background",
                "love_wall",
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
                "green_normal_button",
                "green_hard_button",
                "gray_hard_button",
                FontHelper.toFont(DEFAULT_FONT, new FontParams(50, Color.FOREST)),
                FontHelper.toFont(DEFAULT_FONT, new FontParams(10, Color.FOREST)),
                FontHelper.toFont(DEFAULT_FONT, new FontParams(30, Color.FOREST)),
                BIG_HEART_SIZE,
                false
        ));
    }

    public static void removeLevels() {
        for (LevelAssets assets : levelAssets.values()) {
            assets.dispose();
        }
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
