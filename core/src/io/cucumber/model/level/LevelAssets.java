package io.cucumber.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LevelAssets {

    private final int id;
    private final TextureAtlas.AtlasRegion hero;
    private final TextureAtlas.AtlasRegion enemy;
    private final TextureAtlas.AtlasRegion bonus;
    private final TextureAtlas.AtlasRegion background;
    private final TextureAtlas.AtlasRegion timer;
    private final TextureAtlas.AtlasRegion wall;
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
    private final Sound flipSound;
    private final Sound bonusSound;
    private final Sound deathSound;
    private final int cost;
    private boolean active;

    public LevelAssets(TextureAtlas atlas, LevelInfo levelInfo, boolean active) {
        this.id = levelInfo.getId();
        this.hero = atlas.findRegion(levelInfo.getHero());
        this.enemy = atlas.findRegion(levelInfo.getEnemy());
        this.bonus = atlas.findRegion(levelInfo.getBonus());
        this.background = atlas.findRegion(levelInfo.getBackground());
        this.timer = atlas.findRegion(levelInfo.getTimer());
        this.wall = atlas.findRegion(levelInfo.getWall());
        this.okButton = atlas.findRegion(levelInfo.getOkButton());
        this.playButton = atlas.findRegion(levelInfo.getPlayButton());
        this.chooseButton = atlas.findRegion(levelInfo.getChooseButton());
        this.notButton = atlas.findRegion(levelInfo.getNotButton());
        this.soundOffButton = atlas.findRegion(levelInfo.getSoundOffButton());
        this.soundOnButton = atlas.findRegion(levelInfo.getSoundOnButton());
        this.pauseButton = atlas.findRegion(levelInfo.getPauseButton());
        this.buyButton = atlas.findRegion(levelInfo.getBuyButton());
        this.leftButton = atlas.findRegion(levelInfo.getLeftButton());
        this.rightButton = atlas.findRegion(levelInfo.getRightButton());
        this.flipSound = Gdx.audio.newSound(Gdx.files.internal(levelInfo.getFlipSound()));
        this.bonusSound = Gdx.audio.newSound(Gdx.files.internal(levelInfo.getBonusSound()));
        this.deathSound = Gdx.audio.newSound(Gdx.files.internal(levelInfo.getDeathSound()));
        this.cost = levelInfo.getCost();
        this.active = levelInfo.isStarterPack() || active;
    }

    public void dispose() {
        flipSound.dispose();
        bonusSound.dispose();
        deathSound.dispose();
    }

    public int getId() {
        return id;
    }

    public TextureAtlas.AtlasRegion getHero() {
        return hero;
    }

    public TextureAtlas.AtlasRegion getEnemy() {
        return enemy;
    }

    public TextureAtlas.AtlasRegion getBonus() {
        return bonus;
    }

    public TextureAtlas.AtlasRegion getBackground() {
        return background;
    }

    public TextureAtlas.AtlasRegion getTimer() {
        return timer;
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

    public TextureAtlas.AtlasRegion getLeftButton() {
        return leftButton;
    }

    public TextureAtlas.AtlasRegion getRightButton() {
        return rightButton;
    }

    public Sound getFlipSound() {
        return flipSound;
    }

    public Sound getBonusSound() {
        return bonusSound;
    }

    public Sound getDeathSound() {
        return deathSound;
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
