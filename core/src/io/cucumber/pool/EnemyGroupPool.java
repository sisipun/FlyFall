package io.cucumber.pool;

import com.badlogic.gdx.utils.Array;

import io.cucumber.model.actor.character.Enemy;
import io.cucumber.model.actor.character.EnemyGroup;

public class EnemyGroupPool extends BasePool<EnemyGroup> {

    public EnemyGroupPool() {
        super(1, 3);
    }

    @Override
    protected EnemyGroup newObject() {
        return new EnemyGroup(Array.<Enemy>with());
    }
}
