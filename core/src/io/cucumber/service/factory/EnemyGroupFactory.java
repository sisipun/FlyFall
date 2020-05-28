package io.cucumber.service.factory;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import io.cucumber.model.actor.character.Enemy;
import io.cucumber.model.actor.character.EnemyGroup;
import io.cucumber.pool.EnemyGroupPool;
import io.cucumber.pool.EnemyPool;

import static io.cucumber.utils.constant.GameConstants.BOX_GROUP;
import static io.cucumber.utils.constant.GameConstants.CROSS_GROUP;
import static io.cucumber.utils.constant.GameConstants.REVERSE_LADDER_GROUP;
import static io.cucumber.utils.constant.GameConstants.REVERSE_TRIANGLE_GROUP;
import static io.cucumber.utils.constant.GameConstants.RHOMBUS_GROUP;
import static io.cucumber.utils.constant.GameConstants.ENEMY_DISTANCE;
import static io.cucumber.utils.constant.GameConstants.ENEMY_SIZE;
import static io.cucumber.utils.constant.GameConstants.LADDER_GROUP;
import static io.cucumber.utils.constant.GameConstants.LADDER_SNAKE_GROUP;
import static io.cucumber.utils.constant.GameConstants.SMALL_SNAKE_GROUP;
import static io.cucumber.utils.constant.GameConstants.SMALL_WALL_GROUP;
import static io.cucumber.utils.constant.GameConstants.SNAKE_GROUP;
import static io.cucumber.utils.constant.GameConstants.TRIANGLE_GROUP;
import static io.cucumber.utils.constant.GameConstants.WALL_GROUP;


public class EnemyGroupFactory {

    public static void initFactory() {
        Pools.set(Enemy.class, new EnemyPool());
        Pools.set(EnemyGroup.class, new EnemyGroupPool());
    }

    public static EnemyGroup create(byte type, byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        switch (type) {
            case SMALL_SNAKE_GROUP:
                return createSmallSnake(orientation, velocity, animation, camera);
            case LADDER_GROUP:
                return createLadder(orientation, velocity, animation, camera);
            case SNAKE_GROUP:
                return createSnake(orientation, velocity, animation, camera);
            case LADDER_SNAKE_GROUP:
                return createLadderSnake(orientation, velocity, animation, camera);
            case SMALL_WALL_GROUP:
                return createSmallWall(orientation, velocity, animation, camera);
            case WALL_GROUP:
                return createWall(orientation, velocity, animation, camera);
            case RHOMBUS_GROUP:
                return createRhombus(orientation, velocity, animation, camera);
            case BOX_GROUP:
                return createBox(orientation, velocity, animation, camera);
            case REVERSE_LADDER_GROUP:
                return createReverseLadder(orientation, velocity, animation, camera);
            case CROSS_GROUP:
                return createCross(orientation, velocity, animation, camera);
            case TRIANGLE_GROUP:
                return createTriangle(orientation, velocity, animation, camera);
            case REVERSE_TRIANGLE_GROUP:
                return createReverseTriangle(orientation, velocity, animation, camera);
        }

        return null;
    }

    public static void free(EnemyGroup group) {
        for (Enemy enemy : group.getEnemies()) {
            Pools.free(enemy);
        }
        group.getEnemies().clear();
        Pools.free(group);
    }

    private static EnemyGroup createSmallSnake(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(2);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createLadder(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) - ENEMY_DISTANCE,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - ((ENEMY_DISTANCE) / 3),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + ((ENEMY_DISTANCE) / 3),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 3 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + ENEMY_DISTANCE,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createSnake(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 3 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createLadderSnake(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + ENEMY_DISTANCE / 2,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - ENEMY_DISTANCE / 2,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + ENEMY_DISTANCE / 2,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 3 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - ENEMY_DISTANCE / 2,
                ENEMY_SIZE,
                velocity,
                animation, orientation));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createSmallWall(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(2);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + (camera.viewportHeight / 2) / 3,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) - (camera.viewportHeight / 2) / 3,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createWall(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(3);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + 2 * (camera.viewportHeight / 2) / 3,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) - 2 * (camera.viewportHeight / 2) / 3,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createRhombus(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + 2 * (camera.viewportHeight / 2) / 3,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - 2 * (camera.viewportHeight / 2) / 3,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createBox(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + camera.viewportHeight / 6,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + camera.viewportHeight / 6,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) - camera.viewportHeight / 6,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - camera.viewportHeight / 6,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createReverseLadder(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(4);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + ENEMY_DISTANCE,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + ((ENEMY_DISTANCE) / 3),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - ((ENEMY_DISTANCE) / 3),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 3 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - ENEMY_DISTANCE,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createCross(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(5);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) - camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - 2 * orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createTriangle(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(3);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) + camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2) - camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private static EnemyGroup createReverseTriangle(byte orientation, float velocity, Animation<TextureRegion> animation, Camera camera) {
        Array<Enemy> enemies = new Array<>(3);
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) + camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2),
                (camera.viewportHeight / 2) - camera.viewportHeight / 4,
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        enemies.add(Pools.obtain(Enemy.class).init(
                (camera.viewportWidth / 2) - orientation * (camera.viewportWidth / 2) - orientation * ENEMY_DISTANCE,
                (camera.viewportHeight / 2),
                ENEMY_SIZE,
                velocity,
                animation,
                orientation
        ));
        return Pools.obtain(EnemyGroup.class).init(enemies);
    }

    private EnemyGroupFactory() {
    }
}
