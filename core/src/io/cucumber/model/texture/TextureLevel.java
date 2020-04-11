package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.Texture;

public class TextureLevel {

    private final int id;
    private final Texture hero;
    private final Texture enemy;
    private final Texture bonus;
    private final Texture background;
    private final Texture timer;
    private final Texture wall;
    private final Texture okButton;
    private final Texture playButton;
    private final Texture chooseButton;
    private final Texture notButton;
    private final Texture soundOffButton;
    private final Texture soundOnButton;
    private final Texture pauseButton;
    private final Texture buyButton;
    private final int cost;
    private boolean active;

    public TextureLevel(TextureLevelInfo levelInfo, boolean active) {
        this.id = levelInfo.getId();
        this.hero = new Texture(levelInfo.getHero());
        this.enemy = new Texture(levelInfo.getEnemy());
        this.bonus = new Texture(levelInfo.getBonus());
        this.background = new Texture(levelInfo.getBackground());
        this.timer = new Texture(levelInfo.getTimer());
        this.wall = new Texture(levelInfo.getWall());
        this.okButton = new Texture(levelInfo.getOkButton());
        this.playButton = new Texture(levelInfo.getPlayButton());
        this.chooseButton = new Texture(levelInfo.getChooseButton());
        this.notButton = new Texture(levelInfo.getNotButton());
        this.soundOffButton = new Texture(levelInfo.getSoundOffButton());
        this.soundOnButton = new Texture(levelInfo.getSoundOnButton());
        this.pauseButton = new Texture(levelInfo.getPauseButton());
        this.buyButton = new Texture(levelInfo.getBuyButton());
        this.cost = levelInfo.getCost();
        this.active = levelInfo.isStarterPack() || active;
    }

    public void dispose() {
        hero.dispose();
        enemy.dispose();
        bonus.dispose();
        background.dispose();
        timer.dispose();
        wall.dispose();
        okButton.dispose();
        playButton.dispose();
        chooseButton.dispose();
        notButton.dispose();
        soundOffButton.dispose();
        soundOnButton.dispose();
    }

    public int getId() {
        return id;
    }

    public Texture getHero() {
        return hero;
    }

    public Texture getEnemy() {
        return enemy;
    }

    public Texture getBonus() {
        return bonus;
    }

    public Texture getBackground() {
        return background;
    }

    public Texture getTimer() {
        return timer;
    }

    public Texture getWall() {
        return wall;
    }

    public Texture getOkButton() {
        return okButton;
    }

    public Texture getPlayButton() {
        return playButton;
    }

    public Texture getChooseButton() {
        return chooseButton;
    }

    public Texture getNotButton() {
        return notButton;
    }

    public Texture getSoundOffButton() {
        return soundOffButton;
    }

    public Texture getSoundOnButton() {
        return soundOnButton;
    }

    public Texture getPauseButton() {
        return pauseButton;
    }

    public Texture getBuyButton() {
        return buyButton;
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
