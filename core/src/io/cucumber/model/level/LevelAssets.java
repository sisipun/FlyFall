package io.cucumber.model.level;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

public class LevelAssets {

    private final int id;
    private final Animation<TextureRegion> hero;
    private final Animation<TextureRegion> enemy;
    private final Animation<TextureRegion> bonus;
    private final TextureAtlas.AtlasRegion background;
    private final TextureAtlas.AtlasRegion okButton;
    private final TextureAtlas.AtlasRegion playButton;
    private final TextureAtlas.AtlasRegion chooseButton;
    private final TextureAtlas.AtlasRegion notButton;
    private final TextureAtlas.AtlasRegion soundOffButton;
    private final TextureAtlas.AtlasRegion soundOnButton;
    private final TextureAtlas.AtlasRegion pauseButton;
    private final TextureAtlas.AtlasRegion buyButton;
    private final TextureAtlas.AtlasRegion leftButton;
    private final TextureAtlas.AtlasRegion rightButton;
    private final TextureAtlas.AtlasRegion restartButton;
    private final TextureAtlas.AtlasRegion homeButton;
    private final TextureAtlas.AtlasRegion accButton;
    private final TextureAtlas.AtlasRegion tapButton;
    private final CommonAssets commonAssets;
    private final int cost;
    private boolean active;

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       float heroFrameDuration, String enemy, float enemyFrameDuration, String bonus,
                       float bonusFrameDuration, String background, String okButton,
                       String playButton, String chooseButton, String notButton,
                       String soundOffButton, String soundOnButton, String pauseButton,
                       String buyButton, String leftButton, String rightButton, String restartButton,
                       String homeButton, String accButton, String tapButton, int cost, boolean active) {
        this.id = id;
        this.hero = new Animation<TextureRegion>(
                heroFrameDuration,
                atlas.findRegions(hero),
                LOOP
        );
        this.enemy = new Animation<TextureRegion>(
                enemyFrameDuration,
                atlas.findRegions(enemy),
                LOOP
        );
        this.bonus = new Animation<TextureRegion>(
                bonusFrameDuration,
                atlas.findRegions(bonus),
                LOOP
        );
        this.background = atlas.findRegion(background);
        this.okButton = atlas.findRegion(okButton);
        this.playButton = atlas.findRegion(playButton);
        this.chooseButton = atlas.findRegion(chooseButton);
        this.notButton = atlas.findRegion(notButton);
        this.soundOffButton = atlas.findRegion(soundOffButton);
        this.soundOnButton = atlas.findRegion(soundOnButton);
        this.pauseButton = atlas.findRegion(pauseButton);
        this.buyButton = atlas.findRegion(buyButton);
        this.leftButton = atlas.findRegion(leftButton);
        this.rightButton = atlas.findRegion(rightButton);
        this.restartButton = atlas.findRegion(restartButton);
        this.homeButton = atlas.findRegion(homeButton);
        this.accButton = atlas.findRegion(accButton);
        this.tapButton = atlas.findRegion(tapButton);
        this.commonAssets = commonAssets;

        this.cost = cost;
        this.active = cost == 0 || active;
    }

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       float heroFrameDuration, String enemy, float enemyFrameDuration,
                       String bonus, float bonusFrameDuration, String background, String okButton,
                       String playButton, String chooseButton, String notButton,
                       String soundOffButton, String soundOnButton, String pauseButton,
                       String buyButton, String leftButton, String rightButton, String restartButton,
                       String homeButton, String accButton, String tapButton) {
        this(atlas, commonAssets, id, hero, heroFrameDuration, enemy, enemyFrameDuration, bonus,
                bonusFrameDuration, background, okButton, playButton, chooseButton, notButton,
                soundOffButton, soundOnButton, pauseButton, buyButton, leftButton, rightButton,
                restartButton, homeButton, accButton, tapButton, 0, true);
    }

    public int getId() {
        return id;
    }

    public Animation<TextureRegion> getHero() {
        return hero;
    }

    public Animation<TextureRegion> getEnemy() {
        return enemy;
    }

    public Animation<TextureRegion> getBonus() {
        return bonus;
    }

    public TextureAtlas.AtlasRegion getBackground() {
        return background;
    }

    public TextureAtlas.AtlasRegion getTimer() {
        return commonAssets.getTimer();
    }

    public TextureAtlas.AtlasRegion getWall() {
        return commonAssets.getWall();
    }

    public TextureAtlas.AtlasRegion getOkButton() {
        return okButton;
    }

    public TextureAtlas.AtlasRegion getPlayButton() {
        return playButton;
    }

    public TextureAtlas.AtlasRegion getChooseButton() {
        return chooseButton;
    }

    public TextureAtlas.AtlasRegion getNotButton() {
        return notButton;
    }

    public TextureAtlas.AtlasRegion getSoundOffButton() {
        return soundOffButton;
    }

    public TextureAtlas.AtlasRegion getSoundOnButton() {
        return soundOnButton;
    }

    public TextureAtlas.AtlasRegion getPauseButton() {
        return pauseButton;
    }

    public TextureAtlas.AtlasRegion getBuyButton() {
        return buyButton;
    }

    public TextureAtlas.AtlasRegion getLeftButton() {
        return leftButton;
    }

    public TextureAtlas.AtlasRegion getRightButton() {
        return rightButton;
    }

    public TextureAtlas.AtlasRegion getRestartButton() {
        return restartButton;
    }

    public TextureAtlas.AtlasRegion getHomeButton() {
        return homeButton;
    }

    public TextureAtlas.AtlasRegion getAccButton() {
        return accButton;
    }

    public TextureAtlas.AtlasRegion getTapButton() {
        return tapButton;
    }

    public Sound getFlipSound() {
        return commonAssets.getFlipSound();
    }

    public Sound getBonusSound() {
        return commonAssets.getBonusSound();
    }

    public Sound getDeathSound() {
        return commonAssets.getDeathSound();
    }

    public Sound getBackgroundSound() {
        return commonAssets.getBackgroundSound();
    }

    public int getCost() {
        return cost;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }
}
