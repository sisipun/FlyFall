package io.cucumber.pool;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import io.cucumber.model.actor.character.Enemy;

public class EnemyPool extends Pool<Enemy> {

    public EnemyPool() {
        super(2, 10);
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(0f, 0f, 0f, 0f, new TextureRegion(), (byte) 0);
    }
}
