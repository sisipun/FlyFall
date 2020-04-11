package io.cucumber.model.characters;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import io.cucumber.model.base.DynamicActor;

public class EnemyGroup extends Group{

    private final Array<Enemy> enemies;

    public EnemyGroup(Array<Enemy> enemies) {
        this.enemies = enemies;
        for(Enemy enemy: enemies) {
            addActor(enemy);
        }
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

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
