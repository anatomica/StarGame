package ru.geekbrains.stargame.pool;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.Revision;

public class RevisionPool extends SpritesPool <Revision> {

    private Rect worldBounds;
    private MainShip mainShip;

    public RevisionPool(Rect worldBounds, MainShip mainShip) {
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }
    @Override
    protected Revision newObject() {
        return new Revision(worldBounds, mainShip);
    }
}
