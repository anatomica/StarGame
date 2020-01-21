package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.ButtonExit;
import ru.geekbrains.stargame.sprite.ButtonPlay;
import ru.geekbrains.stargame.sprite.NameGame;
import ru.geekbrains.stargame.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private Game game;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas revision;
    private Star[] starArray;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    private NameGame nameGame;

    private Music music;

    private boolean isPlaying;
    private boolean isLooping;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        System.out.println("MenuScreen_show");
        bg = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        revision = new TextureAtlas("textures/revision.tpack");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        nameGame = new NameGame(revision);
        isPlaying = music.isPlaying();
        isLooping = music.isLooping();
        music.setVolume(3f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }
    private void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
        if (!isPlaying) {
            music.play();
        }
        if (!isLooping) {
            music.setLooping(true);
        }
    }
    private void draw() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        nameGame.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        revision.dispose();
        music.dispose();
        super.dispose();
    }
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        nameGame.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }
}