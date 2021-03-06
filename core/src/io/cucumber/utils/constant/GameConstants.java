package io.cucumber.utils.constant;

public class GameConstants {

    // Screen
    public static final float SCREEN_WIDTH = 1200f;
    public static final float SCREEN_HEIGHT = 600f;
    public static final float WALL_HEIGHT = 70f;
    public static final float ENEMY_RESPAWN_BORDER = 50f;
    public static final float ENEMY_DISTANCE = 200f;
    public static final float SCORE_WIDTH = 20f;
    public static final float SCORE_HEIGHT = 20f;
    public static final float TIMER_HEIGHT = 20f;
    public static final float TIMER_MARGIN_WIDTH = 40f;

    // Hero
    public static final float HERO_HORIZONTAL_VELOCITY = 200f;
    public static final float HERO_VERTICAL_VELOCITY = 450f;
    public static final float DEFAULT_ACCELERATION = 2f;
    public static final float HERO_SIZE = 50f;

    // Enemy
    public static final float ENEMY_MIN_HORIZONTAL_VELOCITY = 400f;
    public static final float ENEMY_MAX_HORIZONTAL_VELOCITY = 600f;
    public static final float ENEMY_VELOCITY_DELTA = 4f;
    public static final float ENEMY_SIZE = 50f;

    // Enemy group types
    public static final byte GROUP_TYPES_COUNT = 12;
    public static final byte SMALL_SNAKE_GROUP = 0;
    public static final byte LADDER_GROUP = 1;
    public static final byte SNAKE_GROUP = 2;
    public static final byte LADDER_SNAKE_GROUP = 3;
    public static final byte SMALL_WALL_GROUP = 4;
    public static final byte WALL_GROUP = 5;
    public static final byte RHOMBUS_GROUP = 6;
    public static final byte BOX_GROUP = 7;
    public static final byte REVERSE_LADDER_GROUP = 8;
    public static final byte CROSS_GROUP = 9;
    public static final byte TRIANGLE_GROUP = 10;
    public static final byte REVERSE_TRIANGLE_GROUP = 11;

    // Bonus
    public static final float BONUS_SIZE = 50f;
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
    public static final float PAUSE_BUTTON_WIDTH = 50f;
    public static final float PAUSE_BUTTON_HEIGHT = 50f;
    public static final float RESUME_BUTTON_WIDTH = 80f;
    public static final float RESUME_BUTTON_HEIGHT = 80f;
    public static final float RESTART_BUTTON_WIDTH = 80f;
    public static final float RESTART_BUTTON_HEIGHT = 80f;
    public static final float NORMAL_DIFFICULTY_BUTTON_WIDTH = 160f;
    public static final float NORMAL_DIFFICULTY_BUTTON_HEIGHT = 80f;
    public static final float HARD_DIFFICULTY_BUTTON_WIDTH = 160f;
    public static final float HARD_DIFFICULTY_BUTTON_HEIGHT = 80f;

    // Preferences
    public static final String PREFERENCE_NAME = "preferences";
    public static final String HIGH_SCORE = "high_score";
    public static final String BONUSES_COUNT = "bonuses_count";
    public static final String IS_SOUND_DISABLED = "is_sound_disabled";
    public static final String IS_ACCELERATION_ENABLED = "is_acceleration_enabled";
    public static final String TEXTURE_LEVEL = "texture_level";
    public static final String IS_ACTIVE_PATTERN = "is_active_%d";

    // Directions
    public static final byte DOWN_DIRECTION = -1;
    public static final byte UP_DIRECTION = 1;
    public static final byte LEFT_DIRECTION = -1;
    public static final byte RIGHT_DIRECTION = 1;
    public static final byte NOT_MOVE = 0;

    // Time
    public static final short MILLIS_IN_SECOND = 1000;
    public static final int COUNTDOWN_COUNT = 3;
    public static final float COUNTDOWN_INTERVAL = 1f;
    public static final float COUNTDOWN_DELAY = 0f;

    // Control
    public static final short MIN_FLING_DISTANCE = 100;

    // Difficulty
    public static final int HARD_DIFFICULTY_SCORE = 100000;
    public static final int NORMAL_DIFFICULTY_SCORE_MULTIPLIER = 1;
    public static final int HARD_DIFFICULTY_SCORE_MULTIPLIER = 2;
    public static final float NORMAL_DIFFICULTY_BONUS_CHANCE = 0.1f;
    public static final float HARD_DIFFICULTY_BONUS_CHANCE = 0.3f;

    // Texts
    public static final String TITLE_LABEL_TEXT = "Fly Fall".toUpperCase();
    public static final String LOADING_LABEL_TEXT = "Loading...".toUpperCase();
    public static final String PAUSE_LABEL_TEXT = "Pause".toUpperCase();
    public static final String GAME_DIFFICULTY_LABEL_TEXT = "Game difficulty".toUpperCase();
    public static final String SCORE_LABEL_TEXT = "Score - ".toUpperCase();
    public static final String HIGH_SCORE_LABEL_TEXT = "High score - ".toUpperCase();
    public static final String BONUS_LABEL_TEXT = "Bonus - ".toUpperCase();
    public static final String COST_LABEL_TEXT = "Cost - ".toUpperCase();

    // Fonts
    public static final String DEFAULT_FONT = "fonts/font.ttf";

    // Animation
    public static final float HERO_ANIMATION_FRAME_DURATION = 0.033f;
    public static final float EXPLOSION_ANIMATION_FRAME_DURATION = 0.033f;
    public static final float ENEMY_ANIMATION_FRAME_DURATION = 0.033f;
    public static final float BONUS_ANIMATION_FRAME_DURATION = 0.033f;

    // Http
    public static final int DEFAULT_HIGH_SCORE_PAGE = 0;
    public static final int DEFAULT_HIGH_SCORE_SIZE = 10;
    public static final String HOST = "http://192.168.0.104:8080";
    public static final String SCORE_PATH = "/scores";

    // Scale
    public static final float BONUS_SCALE = 1f;
    public static final float ENEMY_SCALE = 3f;

    private GameConstants() {
    }
}
