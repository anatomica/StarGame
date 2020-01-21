package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.base.Font;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.pool.RevisionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.ButtonNewGame;
import ru.geekbrains.stargame.sprite.Enemy;
import ru.geekbrains.stargame.sprite.Explosion;
import ru.geekbrains.stargame.sprite.Gift;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.MessageGameOver;
import ru.geekbrains.stargame.sprite.Revision;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.TrackingStar;
import ru.geekbrains.stargame.utils.EnemyGenerator;
import ru.geekbrains.stargame.utils.RevisionGenerator;

public class GameScreen extends BaseScreen {

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final int STAR_COUNT = 64;

    private enum State { PLAYING, PAUSE, GAME_OVER }

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas revisionAtlas;
    private TrackingStar[] stars;
    private Gift gift;

    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private RevisionPool revisionPool;

    private Music music;
    private Sound laserSound;
    private Sound explosionSound;
    private Sound bulletSound;

    private State state;
    private EnemyGenerator enemyGenerator;
    private RevisionGenerator revisionGenerator;
    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private int timerDraw;

    @Override
    public void show() {
        super.show();
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.03f);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        bg = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        revisionAtlas = new TextureAtlas("textures/revision.tpack");
        gift = new Gift(revisionAtlas);
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds, mainShip);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
        revisionPool = new RevisionPool(worldBounds, mainShip);
        revisionGenerator = new RevisionGenerator(worldBounds, revisionPool, revisionAtlas);
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }
        frags = 0;
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        state = State.PLAYING;
        timerDraw = 0;
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PLAYING;
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveObjects();
        draw();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        //timerDraw++;

//        System.out.println("timeDraw= " + timerDraw);
//        if (timerDraw == 200) {
//            gift.update(delta);
//            System.out.println("gift 0");
//            timerDraw = 0;
//        }

        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            gift.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta, frags);
            revisionPool.updateActiveSprites(delta);
            revisionGenerator.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<Revision> revisionList = revisionPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.destroy();
                mainShip.damage(mainShip.getHp());
                state = State.GAME_OVER;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
            }
        }

        for (Revision revision : revisionList) {
            if (revision.isDestroyed()) {
                continue;
            }
            float minDist = revision.getHalfWidth() + mainShip.getHalfWidth();
            if (revision.pos.dst(mainShip.pos) < minDist) {
                revision.destroy();
                mainShip.damage(mainShip.getHp()*(-1));
            }
        }
    }

    private void freeAllDestroyedActiveObjects() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        revisionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }

        explosionPool.drawActiveSprites(batch);
        //revisionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            gift.draw(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gift.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        revisionAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        revisionPool.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        music.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void startNewGame() {
        state = State.PLAYING;

        mainShip.startNewGame();

        frags = 0;

        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        revisionPool.freeAllActiveObjects();
    }
}