package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.Texture;

public class TextureLevel {

    private int id;
    private Texture hero;
    private Texture enemy;
    private Texture bonus;
    private Texture background;

    public TextureLevel(int id, TextureLevelInfo info) {
        this.id = id;
        this.hero = new Texture(info.getHero());
        this.enemy = new Texture(info.getEnemy());
        this.bonus = new Texture(info.getBonus());
        this.background = new Texture(info.getBackground());
    }

    public void dispose() {
        hero.dispose();
        enemy.dispose();
        bonus.dispose();
        background.dispose();
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
}
