package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class Enemy extends Ship {

    public Enemy(BulletPool bulletPool, Sound bulletSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.vY = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            float height,
            int hp,
            Vector2 vY
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        this.v.set(v0);
        this.vY.set(vY);
    }
}