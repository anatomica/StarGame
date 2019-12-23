package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.ScaledButton;
import ru.geekbrains.stargame.math.Rect;

public class ButtonExit extends ScaledButton {


    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect wordBounds) {
        setRight(wordBounds.getRight() - 0.05f);
        setBottom(wordBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}