package io.cucumber.model.texture;

import com.badlogic.gdx.graphics.Texture;

public class TextureLevel {

    private int id;
    private Texture hero;
    private Texture enemy;
    private Texture bonus;
    private Texture background;
    private int cost;
    private boolean active;

    public TextureLevel(int id, String hero, String enemy, String bonus, String background, int cost, boolean active) {
        this.id = id;
        this.hero = new Texture(hero);
        this.enemy = new Texture(enemy);
        this.bonus = new Texture(bonus);
        this.background = new Texture(background);
        this.cost = cost;
        this.active = active;
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
