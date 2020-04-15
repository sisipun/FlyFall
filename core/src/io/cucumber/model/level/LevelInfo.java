package io.cucumber.model.level;

public class LevelInfo {

    private final int id;
    private final String hero;
    private final String enemy;
    private final String bonus;
    private final String background;
    private final String timer;
    private final String wall;
    private final String okButton;
    private final String playButton;
    private final String chooseButton;
    private final String notButton;
    private final String soundOffButton;
    private final String soundOnButton;
    private final String pauseButton;
    private final String buyButton;
    private final String flipSound;
    private final String bonusSound;
    private final String deathSound;
    private final String titleFont;
    private final int cost;
    private final boolean starterPack;

    public LevelInfo(int id, String hero, String enemy, String bonus, String background,
                     String timer, String wall, String okButton, String playButton,
                     String chooseButton, String notButton, String soundOffButton,
                     String soundOnButton, String pauseButton, String buyButton,
                     String flipSound, String bonusSound, String deathSound, String titleFont,
                     int cost) {
        this.id = id;
        this.hero = hero;
        this.enemy = enemy;
        this.bonus = bonus;
        this.background = background;
        this.timer = timer;
        this.wall = wall;
        this.okButton = okButton;
        this.playButton = playButton;
        this.chooseButton = chooseButton;
        this.notButton = notButton;
        this.soundOffButton = soundOffButton;
        this.soundOnButton = soundOnButton;
        this.pauseButton = pauseButton;
        this.buyButton = buyButton;
        this.flipSound = flipSound;
        this.bonusSound = bonusSound;
        this.deathSound = deathSound;
        this.titleFont = titleFont;
        this.cost = cost;
        this.starterPack = false;
    }

    public LevelInfo(int id, String hero, String enemy, String bonus, String background,
                     String timer, String wall, String okButton, String playButton,
                     String chooseButton, String notButton, String soundOffButton,
                     String soundOnButton, String pauseButton, String buyButton,
                     String flipSound, String bonusSound, String deathSound, String titleFont) {
        this.id = id;
        this.hero = hero;
        this.enemy = enemy;
        this.bonus = bonus;
        this.background = background;
        this.timer = timer;
        this.wall = wall;
        this.okButton = okButton;
        this.playButton = playButton;
        this.chooseButton = chooseButton;
        this.notButton = notButton;
        this.soundOffButton = soundOffButton;
        this.soundOnButton = soundOnButton;
        this.pauseButton = pauseButton;
        this.buyButton = buyButton;
        this.flipSound = flipSound;
        this.bonusSound = bonusSound;
        this.deathSound = deathSound;
        this.titleFont = titleFont;
        this.cost = 0;
        this.starterPack = true;
    }


    public int getId() {
        return id;
    }

    public String getHero() {
        return hero;
    }

    public String getEnemy() {
        return enemy;
    }

    public String getBonus() {
        return bonus;
    }

    public String getBackground() {
        return background;
    }

    public String getTimer() {
        return timer;
    }

    public String getWall() {
        return wall;
    }

    public String getOkButton() {
        return okButton;
    }

    public String getPlayButton() {
        return playButton;
    }

    public String getChooseButton() {
        return chooseButton;
    }

    public String getNotButton() {
        return notButton;
    }

    public String getSoundOffButton() {
        return soundOffButton;
    }

    public String getSoundOnButton() {
        return soundOnButton;
    }

    public String getPauseButton() {
        return pauseButton;
    }

    public String getBuyButton() {
        return buyButton;
    }

    public String getFlipSound() {
        return flipSound;
    }

    public String getBonusSound() {
        return bonusSound;
    }

    public String getDeathSound() {
        return deathSound;
    }

    public String getTitleFont() {
        return titleFont;
    }

    public int getCost() {
        return cost;
    }

    public boolean isStarterPack() {
        return starterPack;
    }
}
