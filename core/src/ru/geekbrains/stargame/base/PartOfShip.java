package ru.geekbrains.stargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PartOfShip extends Sprite {

    private int halfOfWidth;
    private int height;

    public PartOfShip(TextureRegion region) {
        super(region);
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
        halfOfWidth = region.getRegionWidth() / 2;
        height = region.getRegionX();
        regions[0].setRegionX(height - halfOfWidth);
        regions[0].setRegionWidth(halfOfWidth);
    }

    public void setLeftHalf() {
        regions[0].setRegionWidth(halfOfWidth);
        regions[0].setRegionX(height);
    }

    public void setRightHalf() {
        regions[0].setRegionWidth(halfOfWidth);
        regions[0].setRegionX(height + halfOfWidth);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }
}