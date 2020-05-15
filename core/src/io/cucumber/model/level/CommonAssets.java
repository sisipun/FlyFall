package io.cucumber.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CommonAssets {

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
    private final TextureAtlas.AtlasRegion restartButton;
    private final TextureAtlas.AtlasRegion homeButton;
    private final Sound flipSound;
    private final Sound bonusSound;
    private final Sound deathSound;
    private final Sound backgroundSound;

    public CommonAssets(TextureAtlas atlas, String timer, String wall, String okButton,
                        String playButton, String chooseButton, String notButton,
                        String soundOffButton, String soundOnButton, String pauseButton,
                        String buyButton, String leftButton, String rightButton, String restartButton,
                        String homeButton, String flipSound, String bonusSound, String deathSound,
                        String backgroundSound) {
        this.timer = atlas.findRegion(timer);
        this.wall = atlas.findRegion(wall);
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
        this.flipSound = Gdx.audio.newSound(Gdx.files.internal(flipSound));
        this.bonusSound = Gdx.audio.newSound(Gdx.files.internal(bonusSound));
        this.deathSound = Gdx.audio.newSound(Gdx.files.internal(deathSound));
        this.backgroundSound = Gdx.audio.newSound(Gdx.files.internal(backgroundSound));
    }

    public void dispose() {
        flipSound.dispose();
        bonusSound.dispose();
        deathSound.dispose();
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

    public TextureAtlas.AtlasRegion getRestartButton() {
        return restartButton;
    }

    public TextureAtlas.AtlasRegion getHomeButton() {
        return homeButton;
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

    public Sound getBackgroundSound() {
        return backgroundSound;
    }
}
