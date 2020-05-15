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
    private final CommonAssets commonAssets;
    private final int cost;
    private boolean active;

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       float heroFrameDuration, String enemy, float enemyFrameDuration, String bonus,
                       float bonusFrameDuration, String background, int cost, boolean active) {
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
        this.commonAssets = commonAssets;

        this.cost = cost;
        this.active = cost == 0 || active;
    }

    public LevelAssets(TextureAtlas atlas, CommonAssets commonAssets, int id, String hero,
                       float heroFrameDuration, String enemy, float enemyFrameDuration,
                       String bonus, float bonusFrameDuration, String background) {
        this(atlas, commonAssets, id, hero, heroFrameDuration, enemy, enemyFrameDuration, bonus,
                bonusFrameDuration, background, 0, true);
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

    public TextureAtlas.AtlasRegion getRestartButton() {
        return commonAssets.getRestartButton();
    }
    public TextureAtlas.AtlasRegion getHomeButton() {
        return commonAssets.getHomeButton();
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
