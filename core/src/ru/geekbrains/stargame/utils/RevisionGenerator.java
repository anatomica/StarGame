package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.RevisionPool;
import ru.geekbrains.stargame.sprite.Revision;

public class RevisionGenerator {

    private static final float REVISION_HEIGHT = 0.1f;
    //private enum StateRevision {GIFT, LEVELUP, MAGNETO, REPAIR, SHIELD, SLOW, UPGRADE, MEDAL}

    private Revision.StateRevision stateRevision;

    private static final float REVISION_GIFT_HEIGHT = 0.1f;
    private static final int REVISION_GIFT_DAMAGE = 1;
    private static final float REVISION_GIFT_RELOAD_INTERVAL = 3f;
    private static final int REVISION_GIFT_HP = 1;

    private static final float REVISION_LEVELUP_HEIGHT = 0.1f;
    private static final int REVISION_LEVELUP_DAMAGE = 1;
    private static final float REVISION_LEVELUP_RELOAD_INTERVAL = 3f;
    private static final int REVISION_LEVELUP_HP = 1;

    private static final float REVISION_MAGNETO_HEIGHT = 0.1f;
    private static final int REVISION_MAGNETO_DAMAGE = 1;
    private static final float REVISION_MAGNETO_RELOAD_INTERVAL = 3f;
    private static final int REVISION_MAGNETO_HP = 1;

    private static final float REVISION_REPAIR_HEIGHT = 0.1f;
    private static final int REVISION_REPAIR_DAMAGE = 1;
    private static final float REVISION_REPAIR_RELOAD_INTERVAL = 3f;
    private static final int REVISION_REPAIR_HP = 1;

    private static final float REVISION_SHIELD_HEIGHT = 0.1f;
    private static final int REVISION_SHIELD_DAMAGE = 1;
    private static final float REVISION_SHIELD_RELOAD_INTERVAL = 3f;
    private static final int REVISION_SHIELD_HP = 1;

    private static final float REVISION_SLOW_HEIGHT = 0.1f;
    private static final int REVISION_SLOW_DAMAGE = 1;
    private static final float REVISION_SLOW_RELOAD_INTERVAL = 3f;
    private static final int REVISION_SLOW_HP = 1;

    private static final float REVISION_UPGRADE_HEIGHT = 0.1f;
    private static final int REVISION_UPGRADE_DAMAGE = 1;
    private static final float REVISION_UPGRADE_RELOAD_INTERVAL = 3f;
    private static final int REVISION_UPGRADE_HP = 1;

    private static final float REVISION_MEDAL_HEIGHT = 0.1f;
    private static final int REVISION_MEDAL_DAMAGE = 1;
    private static final float REVISION_MEDAL_RELOAD_INTERVAL = 3f;
    private static final int REVISION_MEDAL_HP = 1;

    private Rect worldBounds;

    private float generateInterval = 4f;
    private float generateTimer;

    private final TextureRegion[] gift;
    private final TextureRegion[] levelUp;
    private final TextureRegion[] magneto;
    private final TextureRegion[] repair;
    private final TextureRegion[] shield;
    private final TextureRegion[] slow;
    private final TextureRegion[] upgrade;
    private final TextureRegion[] medal;

    private final Vector2 revisionV = new Vector2(0f, -0.1f);
    private final Vector2 revisionV2 = new Vector2(0f, -0.1f);
    private final Vector2 revisionV3 = new Vector2(0f, -0.1f);
    private final Vector2 revisionV4 = new Vector2(0f, -0.1f);
    private final Vector2 revisionV5 = new Vector2(0f, -0.1f);
    private final Vector2 revisionV6 = new Vector2(0f, -0.1f);
    private final Vector2 revisionV7 = new Vector2(0f, -0.1f);
    private final Vector2 revisionV8 = new Vector2(0f, -0.1f);




//    private final Vector2 revisionV2 = new Vector2(0f, -0.2f);
//    private final Vector2 revisionV3 = new Vector2(0f, -0.3f);

    private RevisionPool revisionPool;

    private int level;

    public RevisionGenerator(Rect worldBounds, RevisionPool revisionPool, TextureAtlas revision) {
        this.worldBounds = worldBounds;
        this.revisionPool = revisionPool;

        this.gift = new TextureRegion[1];
        this.gift[0] = revision.findRegion("gift");
        this.levelUp = new TextureRegion[1];
        this.levelUp[0] = revision.findRegion("levelUp");
        this.magneto = new TextureRegion[1];
        this.magneto[0] =revision.findRegion("magneto");
        this.repair = new TextureRegion[1];
        this.repair[0] = revision.findRegion("repair");
        this.shield = new TextureRegion[1];
        this.shield[0] = revision.findRegion("shield");
        this.slow = new TextureRegion[1];
        this.slow[0] = revision.findRegion("slow");
        this.upgrade = new TextureRegion[1];
        this.upgrade[0] = revision.findRegion("upgrade");
        this.medal = new TextureRegion[1];
        this.medal[0] = revision.findRegion("medal");
    }

    public void generate(float delta, int frags) {
        level = frags / 10 + 1;
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Revision revision = revisionPool.obtain();

            float type = (float) Math.random();
            if (type < 0.05f) {
                revision.set(
                        gift,
                        revisionV,
                        REVISION_GIFT_DAMAGE,
                        REVISION_GIFT_RELOAD_INTERVAL,
                        REVISION_GIFT_HEIGHT,
                        REVISION_GIFT_HP//,
                        //stateRevision.GIFT
                );
            } else if (type < 0.10f) {
                revision.set(
                        levelUp,
                        revisionV2,
                        REVISION_LEVELUP_DAMAGE,
                        REVISION_LEVELUP_RELOAD_INTERVAL,
                        REVISION_LEVELUP_HEIGHT,
                        REVISION_LEVELUP_HP//,
                       // stateRevision.LEVELUP
                );
            } else if (type < 0.15f) {
                revision.set(
                        magneto,
                        revisionV3,
                        REVISION_MAGNETO_DAMAGE,
                        REVISION_MAGNETO_RELOAD_INTERVAL,
                        REVISION_MAGNETO_HEIGHT,
                        REVISION_MAGNETO_HP//,
                       // stateRevision.MAGNETO
                );
            } else if (type < 0.20f) {
                revision.set(
                        repair,
                        revisionV4,
                        REVISION_REPAIR_DAMAGE,
                        REVISION_REPAIR_RELOAD_INTERVAL,
                        REVISION_REPAIR_HEIGHT,
                        REVISION_REPAIR_HP//,
                       // stateRevision.REPAIR
                );
            } else if (type < 0.25f) {
                revision.set(
                        shield,
                        revisionV5,
                        REVISION_SHIELD_DAMAGE,
                        REVISION_SHIELD_RELOAD_INTERVAL,
                        REVISION_SHIELD_HEIGHT,
                        REVISION_SHIELD_HP//,
                       // stateRevision.SHIELD
                );
            } else if (type < 0.30f) {
                revision.set(
                        slow,
                        revisionV6,
                        REVISION_SLOW_DAMAGE,
                        REVISION_SLOW_RELOAD_INTERVAL,
                        REVISION_SLOW_HEIGHT,
                        REVISION_SLOW_HP//,
                       // stateRevision.SLOW
                );
            } else if (type < 0.35f) {
                revision.set(
                        upgrade,
                        revisionV7,
                        REVISION_UPGRADE_DAMAGE,
                        REVISION_UPGRADE_RELOAD_INTERVAL,
                        REVISION_UPGRADE_HEIGHT,
                        REVISION_UPGRADE_HP//,
                       // stateRevision.UPGRADE
                );
            }else if (type < 0.40f) {
                revision.set(
                        medal,
                        revisionV8,
                        REVISION_MEDAL_DAMAGE,
                        REVISION_MEDAL_RELOAD_INTERVAL,
                        REVISION_MEDAL_HEIGHT,
                        REVISION_MEDAL_HP//,
                       // stateRevision.MEDAL
                );
            }
            revision.pos.x = Rnd.nextFloat(worldBounds.getLeft() + revision.getHalfWidth(), worldBounds.getRight() - revision.getHalfWidth());
            revision.setBottom(worldBounds.getTop());
        }
    }

    public int getLevel() {
        return level;
    }
}
