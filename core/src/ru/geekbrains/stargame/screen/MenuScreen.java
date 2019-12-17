package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture background;
    private Vector2 position;
    private Vector2 endPoint;
    private Vector2 vector;
    private Vector2 between;
    private float speed = 2;

    @Override
    public void show() {
        super.show();
        background = new Texture("space.jpg");
        img = new Texture("badlogic.jpg");
        vector = new Vector2();
        position = new Vector2();
        between = new Vector2();
        endPoint = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f,     0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(img, position.x, position.y);
        batch.end();

        between = endPoint.cpy().sub(position);
        if (between.len() > vector.len()){
            // System.out.println("between.len() - " + between.len() + " vector.len() - " + vector.len());
            position.add(vector);
        } else position = endPoint;
    }

    @Override
    public void dispose() {
        img.dispose();
        background.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        endPoint = new Vector2(screenX,Gdx.graphics.getHeight() - screenY);
//        делаем копию конечной точки, вычитаем из нее координаты начальной точки,
//        нормализуем, ускоряем сколяром и устанавливаем это в вектор направления
        vector.set(endPoint.cpy().sub(position).nor().scl(speed));
        return false;
    }

}