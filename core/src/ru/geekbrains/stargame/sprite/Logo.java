package ru.geekbrains.stargame.sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Logo extends Sprite {

    private Vector2 touch;
    private Vector2 vector;
    private Vector2 trueVector;
    private float length = 0.01f;

    public Logo(TextureRegion region) {
        super(region);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        trueVector = new Vector2();
        vector = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        trueVector.set(touch);
        if (trueVector.sub(pos).len() <= length) {
            pos.set(touch);
        } else {
            pos.add(vector);
        }
    }

    @Override
    public void resize(Rect wordBounds) {
        setHeightProportion(0.3f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        this.touch = touch;
        vector.set(touch.cpy().sub(pos).setLength(length));
        return false;
    }
}
