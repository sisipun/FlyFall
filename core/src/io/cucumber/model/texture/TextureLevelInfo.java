package io.cucumber.model.texture;

public class TextureLevelInfo {

    private String hero;
    private String enemy;
    private String bonus;
    private String background;
    private int cost;
    private boolean hasAccess;

    public TextureLevelInfo(String hero, String enemy, String bonus, String background, int cost) {
        this.hero = hero;
        this.enemy = enemy;
        this.bonus = bonus;
        this.background = background;
        this.cost = cost;
        this.hasAccess = false;
    }

    public String getHero() {
        return hero;
    }

    public String getEnemy() {
        return enemy;
    }

    public String getBonus() {
        return bonus;
    }

    public String getBackground() {
        return background;
    }

    public int getCost() {
        return cost;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }
}
