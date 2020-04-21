package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.model.actor.character.Enemy;
import io.cucumber.model.actor.character.EnemyGroup;
import io.cucumber.pool.EnemyGroupPool;
import io.cucumber.pool.EnemyPool;

import static io.cucumber.utils.constant.GameConstants.ENEMY_DISTANCE;
import static io.cucumber.utils.constant.GameConstants.ENEMY_SIZE;
import static io.cucumber.utils.constant.GameConstants.HALF_SCREEN_HEIGHT;
import static io.cucumber.utils.constant.GameConstants.HALF_SCREEN_WIDTH;
import static io.cucumber.utils.constant.GameConstants.LADDER_GROUP;
import static io.cucumber.utils.constant.GameConstants.LADDER_SNAKE_GROUP;
import static io.cucumber.utils.constant.GameConstants.SMALL_SNAKE_GROUP;
import static io.cucumber.utils.constant.GameConstants.SMALL_WALL_GROUP;
import static io.cucumber.utils.constant.GameConstants.SNAKE_GROUP;
import static io.cucumber.utils.constant.GameConstants.WALL_GROUP;


public class EnemyGroupFactory {
    
    public static void initFactory() {
        Pools.set(Enemy.class, new EnemyPool());
        Pools.set(EnemyGroup.class, new EnemyGroupPool());
    }

    public static EnemyGroup create(byte type, byte orientation, float velocity, TextureRegion region) {
        switch (type) {
            case SMALL_SNAKE_GROUP:
                return createSmallSnake(orientation, velocity, region);
            case LADDER_GROUP:
                return createLadder(orientation, velocity, region);
            case SNAKE_GROUP:
                return createSnake(orientation, velocity, region);
            case LADDER_SNAKE_GROUP:
                return createLadderSnake(orientation, velocity, region);
            case SMALL_WALL_GROUP:
                return createSmallWall(orientation, velocity, region);
            case WALL_GROUP:
                return createWall(orientation, velocity, region);
        }

        return null;
    }

    public static void free(EnemyGroup group) {
        for (Enemy enemy : group.getEnemies()) {
            Pools.free(enemy);
        }
        Pools.free(group);
        group.getEnemies().clear();
    }

    private static EnemyGroup createSmallSnake(byte orientation, float velocity, TextureRegion region) {
        Array<Enemy> enemies = new Array<>(2);
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH + orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createLadder(byte orientation, float velocity, TextureRegion region) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - ENEMY_DISTANCE, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH + orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT - ((ENEMY_DISTANCE) / 3), ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH + 2 * orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT + ((ENEMY_DISTANCE) / 3), ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH + 3 * orientation * ENEMY_DISTANCE,
                HALF_SCREEN_HEIGHT + ENEMY_DISTANCE, ENEMY_SIZE, velocity, region, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createSnake(byte orientation, float velocity, TextureRegion region) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH +
                orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH +
                2 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH +
                3 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createLadderSnake(byte orientation, float velocity, TextureRegion region) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT + ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH +
                orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH +
                2 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT + ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH +
                3 * orientation * ENEMY_DISTANCE, HALF_SCREEN_HEIGHT - ENEMY_DISTANCE / 2, ENEMY_SIZE, velocity, region, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createSmallWall(byte orientation, float velocity, TextureRegion region) {
        Array<Enemy> enemies = new Array<>(2);
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT + HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, region, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createWall(byte orientation, float velocity, TextureRegion region) {
        Array<Enemy> enemies = new Array<>(2);
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT + 2 * HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT, ENEMY_SIZE, velocity, region, orientation));
        enemies.add(Pools.obtain(Enemy.class).init(HALF_SCREEN_WIDTH + orientation * HALF_SCREEN_WIDTH,
                HALF_SCREEN_HEIGHT - 2 * HALF_SCREEN_HEIGHT / 3, ENEMY_SIZE, velocity, region, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private EnemyGroupFactory() {
    }
}
