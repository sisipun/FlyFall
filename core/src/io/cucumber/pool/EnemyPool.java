package io.cucumber.pool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.cucumber.model.actor.character.Enemy;

public class EnemyPool extends ActorPool<Enemy> {

    public EnemyPool() {
        super(2, 10);
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(0f, 0f, 0f, 0f, new Animation<>(1, new TextureRegion()), (byte) 0);
    }
}
