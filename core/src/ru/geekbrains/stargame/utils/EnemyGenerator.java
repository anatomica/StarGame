package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.Enemy;

public class EnemyGenerator {

    private final TextureRegion[] enemyMediumRegion;
    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;
    private Rect worldBounds;
    private float generateInterval = 3f;
    private float generateTimer;

    private Vector2 enemyMediumV = new Vector2(0f, -0.15f);
    private Vector2 enemyStartV = new Vector2(0f, -0.3f);

    private static float enemyHeight = 0.1f;
    private static float enemyBulletHeight = 0.02f;
    private static float enemyMediumBullet = -0.25f;
    private static int enemyBulletDamage = 5;
    private static float enemyReloadInterval = 1f;

    public EnemyGenerator(Rect worldBounds, EnemyPool enemyPool, TextureAtlas atlas) {
        TextureRegion textureRegion1 = atlas.findRegion("enemy1");
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.enemyMediumRegion = Regions.split(textureRegion1, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    enemyMediumRegion,
                    enemyMediumV,
                    bulletRegion,
                    enemyBulletHeight,
                    enemyMediumBullet,
                    enemyBulletDamage,
                    enemyReloadInterval,
                    enemyHeight,
                    enemyStartV
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
