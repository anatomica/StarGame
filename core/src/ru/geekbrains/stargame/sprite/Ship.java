package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.PartOfShip;
import ru.geekbrains.stargame.math.Rect;

public class Ship extends PartOfShip {

    private float length = 0.005f;
    private Vector2 touch;
    private Vector2 trueVector;
    private Vector2 vector;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        touch = new Vector2();
        trueVector = new Vector2();
        vector = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        trueVector.set(touch);
        if (trueVector.sub(pos).len() <= length) {
            pos.set(touch);
            setRightHalf();
        } else {
            pos.add(vector);
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
        vector.set(touch.cpy().sub(pos).setLength(length));
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

}