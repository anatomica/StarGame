package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;

public class Revision extends Ship {

    protected Rect worldBounds;
    public enum StateRevision {GIFT, LEVELUP, MAGNETO, REPAIR, SHIELD, SLOW, UPGRADE, MEDAL}

   // private StateRevision stateRevision;
    private Object owner;

    protected Vector2 v;
    protected Vector2 v0;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int damage;
    protected int hp;

    private MainShip mainShip;

    public Revision(Rect worldBounds, MainShip mainShip) {
        this.v = new Vector2();
        this.v0 = new Vector2();
        //v0 = new Vector2(0.0f, -0.5f);
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    public void update(float delta) {

        if (getBottom() < worldBounds.getBottom()) {
            destroy();
            mainShip.damage(damage);
        }

    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            int damage,
            float reloadInterval,
            float height,
            int hp//,
           // StateRevision stateRevision
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
       // this.stateRevision = stateRevision;

    }

    public boolean isRevisionCollision(Rect revision) {
        return !(
                revision.getRight() < getLeft()
                || revision.getLeft() > getRight()
                || revision.getBottom() > getTop()
                || revision.getTop() < pos.y
        );
    }
    public Object getOwner() {
        return owner;
    }
}
