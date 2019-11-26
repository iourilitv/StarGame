package gdx.lessons.lesson8.classfiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import gdx.lessons.lesson8.classfiles.base.BaseScreen;
import gdx.lessons.lesson8.classfiles.base.Font;
import gdx.lessons.lesson8.classfiles.math.Rect;
import gdx.lessons.lesson8.classfiles.pool.BulletPool;
import gdx.lessons.lesson8.classfiles.pool.EnemyPool;
import gdx.lessons.lesson8.classfiles.pool.ExplosionPool;
import gdx.lessons.lesson8.classfiles.sprite.Background;
import gdx.lessons.lesson8.classfiles.sprite.Bullet;
import gdx.lessons.lesson8.classfiles.sprite.Enemy;
import gdx.lessons.lesson8.classfiles.sprite.GameOver;
import gdx.lessons.lesson8.classfiles.sprite.MainShip;
import gdx.lessons.lesson8.classfiles.sprite.Star;
import gdx.lessons.lesson8.classfiles.sprite.StartNewGame;
import gdx.lessons.lesson8.classfiles.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private static final String FRAGS = "Frags:";
    private static final String HP = "HP:";
    private static final String LEVEL = "Level:";

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private State state;
    private State prevState;

    private Texture bg;
    private TextureAtlas atlas;

    private Music music;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;

    private GameOver gameOver;
    private StartNewGame startNewGame;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private int prevLevel = 1;

    private int frags;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        gameOver = new GameOver(atlas);
        startNewGame = new StartNewGame(atlas, this);
        music.setLooping(true);
        music.play();
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        state = State.PLAYING;
        prevState = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void pause() {
        prevState = state;
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        state = prevState;
        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        startNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
        mainShip.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        enemyEmitter.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else if (state == State.GAME_OVER) {
            startNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else if (state == State.GAME_OVER) {
            startNewGame.touchUp(touch, pointer);
        }
        return false;
    }

    public void startNewGame() {
        state = State.PLAYING;

        prevLevel = 1;
        frags = 0;
        mainShip.startNewGame(worldBounds);

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
        if (prevLevel < enemyEmitter.getLevel()) {
            prevLevel = enemyEmitter.getLevel();
            mainShip.setHp(mainShip.getHp() + 10);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                mainShip.damage(enemy.getDamage());
                enemy.destroy();
                frags++;
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip) {
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
            if (bullet.getOwner() == mainShip) {
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
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            startNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        float fragsPosX = worldBounds.getLeft() + 0.01f;
        float fragsPosY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbFrags.append(FRAGS).append(frags), fragsPosX, fragsPosY);
        sbHp.setLength(0);
        float hpPosX = worldBounds.pos.x;
        float hpPosY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), hpPosX, hpPosY, Align.center);
        sbLevel.setLength(0);
        float levelPosX = worldBounds.getRight() - 0.01f;
        float levelPosY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), levelPosX, levelPosY, Align.right);
    }

}
