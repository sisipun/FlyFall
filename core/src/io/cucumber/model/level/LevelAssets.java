package io.cucumber.model.level;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;

public class LevelAssets {

    private final int id;
    private final Animation<TextureRegion> hero;
    private final Animation<TextureRegion> enemy;
    private final boolean enemyRotate;
    private final Animation<TextureRegion> bonus;
    private final TextureAtlas.AtlasRegion background;
    private final TextureAtlas.AtlasRegion wall;
    private final TextureAtlas.AtlasRegion okButton;
    private final TextureAtlas.AtlasRegion playButton;
    private final TextureAtlas.AtlasRegion chooseButton;
    private final TextureAtlas.AtlasRegion notButton;
    private final TextureAtlas.AtlasRegion soundOffButton;
    private final TextureAtlas.AtlasRegion soundOnButton;
    private final TextureAtlas.AtlasRegion pauseButton;
    private final TextureAtlas.AtlasRegion buyButton;
    private final TextureAtlas.AtlasRegion cantBuyButton;
    private final TextureAtlas.AtlasRegion leftButton;
    private final TextureAtlas.AtlasRegion rightButton;
    private final TextureAtlas.AtlasRegion restartButton;
    private final TextureAtlas.AtlasRegion homeButton;
    private final TextureAtlas.AtlasRegion accButton;
    private final TextureAtlas.AtlasRegion tapButton;
    private final TextureAtlas.AtlasRegion normalDifficultyButton;
    private final TextureAtlas.AtlasRegion hardDifficultyButton;
    private final TextureAtlas.AtlasRegion cantHardDifficultyButton;
    private final BitmapFont titleFont;
    private final BitmapFont labelFont;
    private final BitmapFont costFont;
    private final CommonAssets commonAssets;
    private final int cost;
    private boolean active;
    private boolean hide;

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       float heroFrameDuration, String enemy, float enemyFrameDuration, boolean enemyRotate,
                       String bonus, float bonusFrameDuration, String background, String wall,
                       String okButton, String playButton, String chooseButton, String notButton,
                       String soundOffButton, String soundOnButton, String pauseButton,
                       String buyButton, String cantBuyButton, String leftButton, String rightButton,
                       String restartButton, String homeButton, String accButton, String tapButton,
                       String normalDifficultyButton, String hardDifficultyButton,
                       String cantHardDifficultyButton, BitmapFont titleFont, BitmapFont labelFont,
                       BitmapFont costFont, int cost, boolean active, boolean hide) {
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
        this.enemyRotate = enemyRotate;
        this.bonus = new Animation<TextureRegion>(
                bonusFrameDuration,
                atlas.findRegions(bonus),
                LOOP
        );
        this.background = atlas.findRegion(background);
        this.wall = atlas.findRegion(wall);
        this.okButton = atlas.findRegion(okButton);
        this.playButton = atlas.findRegion(playButton);
        this.chooseButton = atlas.findRegion(chooseButton);
        this.notButton = atlas.findRegion(notButton);
        this.soundOffButton = atlas.findRegion(soundOffButton);
        this.soundOnButton = atlas.findRegion(soundOnButton);
        this.pauseButton = atlas.findRegion(pauseButton);
        this.buyButton = atlas.findRegion(buyButton);
        this.cantBuyButton = atlas.findRegion(cantBuyButton);
        this.leftButton = atlas.findRegion(leftButton);
        this.rightButton = atlas.findRegion(rightButton);
        this.restartButton = atlas.findRegion(restartButton);
        this.homeButton = atlas.findRegion(homeButton);
        this.accButton = atlas.findRegion(accButton);
        this.tapButton = atlas.findRegion(tapButton);
        this.normalDifficultyButton = atlas.findRegion(normalDifficultyButton);
        this.hardDifficultyButton = atlas.findRegion(hardDifficultyButton);
        this.cantHardDifficultyButton = atlas.findRegion(cantHardDifficultyButton);
        this.titleFont = titleFont;
        this.labelFont = labelFont;
        this.costFont = costFont;
        this.commonAssets = commonAssets;

        this.cost = cost;
        this.active = cost == 0 || active;
        this.hide = hide;
    }

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       float heroFrameDuration, String enemy, float enemyFrameDuration, boolean enemyRotate,
                       String bonus, float bonusFrameDuration, String background, String wall,
                       String okButton, String playButton, String chooseButton, String notButton,
                       String soundOffButton, String soundOnButton, String pauseButton,
                       String buyButton, String cantBuyButton, String leftButton, String rightButton,
                       String restartButton, String homeButton, String accButton, String tapButton,
                       String normalDifficultyButton, String hardDifficultyButton,
                       String cantHardDifficultyButton, BitmapFont titleFont, BitmapFont labelFont,
                       BitmapFont costFont, boolean hide) {
        this(atlas, commonAssets, id, hero, heroFrameDuration, enemy, enemyFrameDuration, enemyRotate,
                bonus, bonusFrameDuration, background, wall, okButton, playButton, chooseButton,
                notButton, soundOffButton, soundOnButton, pauseButton, buyButton, cantBuyButton,
                leftButton, rightButton, restartButton, homeButton, accButton, tapButton,
                normalDifficultyButton, hardDifficultyButton, cantHardDifficultyButton, titleFont,
                labelFont, costFont,0, true, hide);
    }

    public int getId() {
        return id;
    }

    public Animation<TextureRegion> getHero() {
        return hero;
    }

    public Animation<TextureRegion> getExplosion() {
        return commonAssets.getExplosion();
    }

    public Animation<TextureRegion> getEnemy() {
        return enemy;
    }

    public boolean isEnemyRotate() {
        return enemyRotate;
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
        return wall;
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

    public TextureAtlas.AtlasRegion getCantBuyButton() {
        return cantBuyButton;
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

    public TextureAtlas.AtlasRegion getNormalDifficultyButton() {
        return normalDifficultyButton;
    }

    public TextureAtlas.AtlasRegion getHardDifficultyButton() {
        return hardDifficultyButton;
    }

    public TextureAtlas.AtlasRegion getCantHardDifficultyButton() {
        return cantHardDifficultyButton;
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

    public BitmapFont getTitleFont() {
        return titleFont;
    }

    public BitmapFont getLabelFont() {
        return labelFont;
    }

    public BitmapFont getCostFont() {
        return costFont;
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

    public boolean isHide() {
        return hide;
    }

    public void dispose() {
        titleFont.dispose();
        labelFont.dispose();
        costFont.dispose();
    }

}
