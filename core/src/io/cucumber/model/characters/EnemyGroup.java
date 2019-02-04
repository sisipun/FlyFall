package io.cucumber.model.characters;

import com.badlogic.gdx.utils.Array;
import io.cucumber.model.base.DynamicActor;

public class EnemyGroup {

    private final Array<Enemy> enemies;

    public EnemyGroup(Array<Enemy> enemies) {
        this.enemies = enemies;
    }

    public boolean isCollides(DynamicActor actor) {
        boolean isCollides;
        for (Enemy enemy : enemies) {
            isCollides = enemy.isCollides(actor);
            if (isCollides) {
                return true;
            }
        }

        return false;
    }

    public void update(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
    }

    public void dispose() {
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        enemies.clear();
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
