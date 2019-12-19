package ru.geekbrains.stargame.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Vector2 pos;
    private Vector2 touch;
    private Vector2 vector;
    private Vector2 between;
    private float speed = 2;

    private Background background;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        img = new Texture("badlogic.jpg");
        pos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 	0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.draw(img, pos.x, pos.y, 0.25f, 0.25f);
        batch.end();

        between = touch.cpy().sub(pos);
        if (between.len() > vector.len()){
            // System.out.println("between.len() - " + between.len() + " vector.len() - " + vector.len());
            pos.add(vector);
        } else pos = touch;
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        vector = new Vector2(screenX,Gdx.graphics.getHeight() - screenY);
        vector.set(vector.cpy().sub(pos).nor().scl(speed));
        return false;
    }
}
