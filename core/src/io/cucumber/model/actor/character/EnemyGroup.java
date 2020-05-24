package io.cucumber.model.actor.character;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import io.cucumber.model.base.DynamicActor;

public class EnemyGroup extends Group implements Pool.Poolable {

    private Array<Enemy> enemies;

    public EnemyGroup(Array<Enemy> enemies) {
        this.enemies = enemies;
        for(Enemy enemy: enemies) {
            addActor(enemy);
        }
    }

    public EnemyGroup() {
        this.enemies = Array.with();
        for(Enemy enemy: enemies) {
            addActor(enemy);
        }
    }

    public EnemyGroup init(Array<Enemy> enemies) {
        this.enemies = enemies;
        for(Enemy enemy: enemies) {
            addActor(enemy);
        }
        return this;
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

    @Override
    public boolean remove() {
        enemies.clear();
        clearChildren();
        return super.remove();
    }

    @Override
    public void reset() {
        clearActions();
        for (Enemy enemy : enemies) {
            enemy.clearActions();
        }
        enemies.clear();
        clearChildren();
    }
}
