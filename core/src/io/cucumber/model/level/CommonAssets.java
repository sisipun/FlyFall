package io.cucumber.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL;

public class CommonAssets {

    private final TextureAtlas.AtlasRegion timer;
    private final Animation<TextureRegion> explosion;
    private final Sound flipSound;
    private final Sound bonusSound;
    private final Sound deathSound;
    private final Sound backgroundSound;

    public CommonAssets(TextureAtlas atlas, String timer, String explosion,
                        float explosionFrameDuration, String flipSound, String bonusSound,
                        String deathSound, String backgroundSound) {
        this.timer = atlas.findRegion(timer);
        this.explosion = new Animation<TextureRegion>(
                explosionFrameDuration,
                atlas.findRegions(explosion),
                NORMAL
        );
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

    public Animation<TextureRegion> getExplosion() {
        return explosion;
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
