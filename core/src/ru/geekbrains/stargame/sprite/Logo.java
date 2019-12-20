package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Logo extends Sprite {

    protected static float LEN = 0.05f;
    private Vector2 touch;
    private Vector2 v0;
    private Vector2 v;

    public Logo(TextureRegion region) {
        super(region);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
        } else {
            pos.add(v);
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.2f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        this.touch = touch;
        v.set(touch.cpy().sub(pos).setLength(LEN));
        return false;
    }
}
