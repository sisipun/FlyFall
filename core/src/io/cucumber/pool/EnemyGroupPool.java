package io.cucumber.pool;

import com.badlogic.gdx.utils.Pool;

import io.cucumber.model.actor.character.EnemyGroup;

public class EnemyGroupPool extends Pool<EnemyGroup> {

    public EnemyGroupPool() {
        super(1, 3);
    }

    @Override
    protected EnemyGroup newObject() {
        return new EnemyGroup();
    }
}
