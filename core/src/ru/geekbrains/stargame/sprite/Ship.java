package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.RotateShip;
import ru.geekbrains.stargame.math.Rect;

public class Ship extends RotateShip {

    protected static float LEN = 0.005f;
    private Vector2 touch;
    private Vector2 v0;
    private Vector2 v;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        touch = new Vector2();
        v0 = new Vector2();
        v = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        v0.set(touch);
        if (v0.sub(pos).len() <= LEN) {
            pos.set(touch);
            setLeftHalf();
        } else {
            pos.add(v);
            setRightHalf();
        }

    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.15f);
        setBottom(wordBounds.getBottom() + 0.03f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        this.touch = touch;
        v.set(touch.cpy().sub(pos).setLength(LEN));
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

}