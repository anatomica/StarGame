package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.base.Sprite;

public class NameGame extends Sprite {

    public NameGame(TextureAtlas revision) {
        super(revision.findRegion("SpaceBattle"));
        setHeightProportion(0.3f);
        setBottom(-0.1f);
    }
}
