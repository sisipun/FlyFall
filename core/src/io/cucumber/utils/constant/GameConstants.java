package io.cucumber.utils.constant;

public class GameConstants {

    // Bonus
    public static final float BONUS_SIZE = 50f;
    public static final float BONUS_CHANCE = 0.05f;
    public static final float BONUS_LIFESPAN = 5000f;

    // Button
    public static final float START_GAME_BUTTON_WIDTH = 80f;
    public static final float START_GAME_BUTTON_HEIGHT = 80f;
    public static final float CHOOSE_LEVEL_BUTTON_WIDTH = 80f;
    public static final float CHOOSE_LEVEL_BUTTON_HEIGHT = 80f;
    public static final float SOUND_OFF_BUTTON_WIDTH = 80f;
    public static final float SOUND_OFF_BUTTON_HEIGHT = 80f;
    public static final float HOME_BUTTON_WIDTH = 80f;
    public static final float HOME_BUTTON_HEIGHT = 80f;
    public static final float CHOOSE_BUTTON_WIDTH = 80f;
    public static final float CHOOSE_BUTTON_HEIGHT = 80f;
    public static final float PAUSE_BUTTON_WIDTH = 40f;
    public static final float PAUSE_BUTTON_HEIGHT = 40f;
    public static final float RESUME_BUTTON_WIDTH = 80f;
    public static final float RESUME_BUTTON_HEIGHT = 80f;

    // Enemy
    public static final float ENEMY_MIN_HORIZONTAL_VELOCITY = 400f;
    public static final float ENEMY_MAX_HORIZONTAL_VELOCITY = 800f;
    public static final float ENEMY_VELOCITY_DELTA = 0.5f;
    public static final float ENEMY_SIZE = 50f;

    // Hero
    public static final float HERO_HORIZONTAL_VELOCITY = 9f;
    public static final float HERO_VERTICAL_VELOCITY = 450f;
    public static final float HERO_SIZE = 50f;

    // Preferences
    public static final String PREFERENCE_NAME = "preferences";
    public static final String HIGH_SCORE = "high_score";
    public static final String BONUSES_COUNT = "bonuses_count";
    public static final String IS_SOUND_ENABLED = "is_sound_enabled";
    public static final String TEXTURE_LEVEL = "texture_level";
    public static final String IS_ACTIVE_PATTERN = "is_active_%d";

    // Screen
    public static final float SCREEN_WIDTH = 1200f;
    public static final float SCREEN_HEIGHT = 600f;
    public static final float HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2;
    public static final float HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2;
    public static final float WALL_HEIGHT = 50f;
    public static final float ENEMY_RESPAWN_BORDER = 50f;
    public static final float ENEMY_DISTANCE = 200f;
    public static final float SCORE_WIDTH = 20f;
    public static final float SCORE_HEIGHT = 20f;
    public static final float TIMER_HEIGHT = 15f;
    public static final float TIMER_MARGIN_WIDTH = 40f;

    // Group types
    public static final byte GROUP_TYPES_COUNT = 6;
    public static final byte SMALL_SNAKE_GROUP = 0;
    public static final byte LADDER_GROUP = 1;
    public static final byte SNAKE_GROUP = 2;
    public static final byte LADDER_SNAKE_GROUP = 3;
    public static final byte SMALL_WALL_GROUP = 4;
    public static final byte WALL_GROUP = 5;

    // Orientations
    public static final byte LEFT_ORIENTATION = -1;
    public static final byte RIGHT_ORIENTATION = 1;

    // Directions
    public static final byte DOWN_DIRECTION = -1;
    public static final byte UP_DIRECTION = 1;

    // Time
    public static final short MILLIS_IN_SECOND = 1000;

    // Control
    public static final short MIN_FLING_DISTANCE = 100;
    public static final short MAX_POINTERS_COUNT = 4;

    private GameConstants() {
    }
}