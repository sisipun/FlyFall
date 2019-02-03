package io.cucumber.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import io.cucumber.model.Enemy;
import io.cucumber.model.EnemyGroup;

import static io.cucumber.constant.GameConstants.*;


public class EnemyGroupFactory {

    public static EnemyGroup create(byte type, byte orientation, float velocity, Texture texture) {
        switch (type) {
            case SMALL_SNAKE_GROUP:
                return createSmallSnake(orientation, velocity, texture);
            case LADDER_GROUP:
                return createLadder(orientation, velocity, texture);
            case SNAKE_GROUP:
                return createSnake(orientation, velocity, texture);
            case LADDER_SNAKE_GROUP:
                return createLadderSnake(orientation, velocity, texture);
            case SMALL_WALL_GROUP:
                return createSmallWall(orientation, velocity, texture);
            case WALL_GROUP:
                return createWall(orientation, velocity, texture);
        }

        return null;
    }

    private static EnemyGroup createSmallSnake(byte orientation, float velocity, Texture texture) {
        Array<Enemy> enemies = new Array<Enemy>(2);
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH + orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        return new EnemyGroup(enemies);
    }

    private static EnemyGroup createLadder(byte orientation, float velocity, Texture texture) {
        Array<Enemy> enemies = new Array<Enemy>(4);
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_DISTANCE - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH + orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT - ((ENEMY_DISTANCE) / 3) - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH + 2 * orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT + ((ENEMY_DISTANCE) / 3) - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH + 3 * orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT + ENEMY_DISTANCE - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        return new EnemyGroup(enemies);
    }

    private static EnemyGroup createSnake(byte orientation, float velocity, Texture texture) {
        Array<Enemy> enemies = new Array<Enemy>(4);
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH +
                orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH +
                2 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH +
                3 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        return new EnemyGroup(enemies);
    }

    private static EnemyGroup createLadderSnake(byte orientation, float velocity, Texture texture) {
        Array<Enemy> enemies = new Array<Enemy>(4);
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH +
                orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH +
                2 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH +
                3 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, texture, orientation));
        return new EnemyGroup(enemies);
    }

    private static EnemyGroup createSmallWall(byte orientation, float velocity, Texture texture) {
        Array<Enemy> enemies = new Array<Enemy>(2);
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, texture, orientation));
        return new EnemyGroup(enemies);
    }

    private static EnemyGroup createWall(byte orientation, float velocity, Texture texture) {
        Array<Enemy> enemies = new Array<Enemy>(2);
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 + 2 * HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2, ENEMY_SIZE, velocity, texture, orientation));
        enemies.add(new Enemy(HALF_SCREEN_WIDTH - ENEMY_SIZE / 2 + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_SIZE / 2 - 2 * HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, texture, orientation));
        return new EnemyGroup(enemies);
    }

    private EnemyGroupFactory() {
    }
}
