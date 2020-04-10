package io.cucumber.model.texture;

public class TextureLevelInfo {

    private int id;
    private String heroTexturePath;
    private String enemyTexturePath;
    private String bonusTexturePath;
    private String backgroundTexturePath;
    private int cost;
    private boolean starterPack;

    public TextureLevelInfo(int id, String heroTexturePath, String enemyTexturePath, String bonusTexturePath, String backgroundTexturePath, int cost) {
        this.id = id;
        this.heroTexturePath = heroTexturePath;
        this.enemyTexturePath = enemyTexturePath;
        this.bonusTexturePath = bonusTexturePath;
        this.backgroundTexturePath = backgroundTexturePath;
        this.cost = cost;
        this.starterPack = false;
    }

    public TextureLevelInfo(int id, String heroTexturePath, String enemyTexturePath, String bonusTexturePath, String backgroundTexturePath) {
        this.id = id;
        this.heroTexturePath = heroTexturePath;
        this.enemyTexturePath = enemyTexturePath;
        this.bonusTexturePath = bonusTexturePath;
        this.backgroundTexturePath = backgroundTexturePath;
        this.cost = 0;
        this.starterPack = true;
    }


    public int getId() {
        return id;
    }

    public String getHeroTexturePath() {
        return heroTexturePath;
    }

    public String getEnemyTexturePath() {
        return enemyTexturePath;
    }

    public String getBonusTexturePath() {
        return bonusTexturePath;
    }

    public String getBackgroundTexturePath() {
        return backgroundTexturePath;
    }

    public int getCost() {
        return cost;
    }

    public boolean isStarterPack() {
        return starterPack;
    }
}
