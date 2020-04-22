package io.cucumber.model.level;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LevelAssets {

    private final int id;
    private final TextureAtlas.AtlasRegion hero;
    private final TextureAtlas.AtlasRegion enemy;
    private final TextureAtlas.AtlasRegion bonus;
    private final TextureAtlas.AtlasRegion background;
    private final CommonAssets commonAssets;
    private final int cost;
    private boolean active;

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       String enemy, String bonus, String background, int cost, boolean active) {
        this.id = id;
        this.hero = atlas.findRegion(hero);
        this.enemy = atlas.findRegion(enemy);
        this.bonus = atlas.findRegion(bonus);
        this.background = atlas.findRegion(background);
        this.commonAssets = commonAssets;

        this.cost = cost;
        this.active = cost == 0 || active;
    }

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       String enemy, String bonus, String background) {
        this(atlas, commonAssets, id, hero, enemy, bonus, background, 0, true);
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
        return commonAssets.getTimer();
    }

    public TextureAtlas.AtlasRegion getWall() {
        return commonAssets.getWall();
    }

    public TextureAtlas.AtlasRegion getOkButton() {
        return commonAssets.getOkButton();
    }

    public TextureAtlas.AtlasRegion getPlayButton() {
        return commonAssets.getPlayButton();
    }

    public TextureAtlas.AtlasRegion getChooseButton() {
        return commonAssets.getChooseButton();
    }

    public TextureAtlas.AtlasRegion getNotButton() {
        return commonAssets.getNotButton();
    }

    public TextureAtlas.AtlasRegion getSoundOffButton() {
        return commonAssets.getSoundOffButton();
    }

    public TextureAtlas.AtlasRegion getSoundOnButton() {
        return commonAssets.getSoundOnButton();
    }

    public TextureAtlas.AtlasRegion getPauseButton() {
        return commonAssets.getPauseButton();
    }

    public TextureAtlas.AtlasRegion getBuyButton() {
        return commonAssets.getBuyButton();
    }

    public TextureAtlas.AtlasRegion getLeftButton() {
        return commonAssets.getLeftButton();
    }

    public TextureAtlas.AtlasRegion getRightButton() {
        return commonAssets.getRightButton();
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
