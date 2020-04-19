package io.cucumber.model.actor.character;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import io.cucumber.model.base.DynamicActor;

public class EnemyGroup extends Group {

    private final Array<io.cucumber.model.actor.character.Enemy> enemies;

    public EnemyGroup(Array<io.cucumber.model.actor.character.Enemy> enemies) {
        this.enemies = enemies;
        for(io.cucumber.model.actor.character.Enemy enemy: enemies) {
            addActor(enemy);
        }
    }

    public boolean isCollides(DynamicActor actor) {
        boolean isCollides;
        for (io.cucumber.model.actor.character.Enemy enemy : enemies) {
            isCollides = enemy.isCollides(actor);
            if (isCollides) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean remove() {
        enemies.clear();
        return super.remove();
    }

    public Array<io.cucumber.model.actor.character.Enemy> getEnemies() {
        return enemies;
    }
}
