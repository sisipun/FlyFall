package io.cucumber.pool;

import io.cucumber.model.actor.character.EnemyGroup;

public class EnemyGroupPool extends ActorPool<EnemyGroup> {

    public EnemyGroupPool() {
        super(1, 3);
    }

    @Override
    protected EnemyGroup newObject() {
        return new EnemyGroup();
    }
}
