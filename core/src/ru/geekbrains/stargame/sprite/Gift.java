package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class Gift extends Sprite {

    private Vector2 v;
    private Rect worldBounds;

    public Gift(TextureAtlas atlas) {
        super(atlas.findRegion("gift"));
        float vX = Rnd.nextFloat(-0.005f, 0.005f);
        float vY = Rnd.nextFloat(-0.5f, -0.1f);
        v = new Vector2(vX, vY);
        setHeightProportion(0.01f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        this.worldBounds = wordBounds;
        float posX = Rnd.nextFloat(wordBounds.getLeft(), wordBounds.getRight());
        float posY = Rnd.nextFloat(wordBounds.getBottom(), wordBounds.getTop());
        pos.set(posX, posY);
    }
}
