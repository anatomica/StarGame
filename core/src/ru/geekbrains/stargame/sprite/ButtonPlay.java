package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.ScaledButton;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
        setHeightProportion(0.19f);
    }

    @Override
    public void resize(Rect wordBounds) {
        setLeft(wordBounds.getLeft() + 0.05f);
        setBottom(wordBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}