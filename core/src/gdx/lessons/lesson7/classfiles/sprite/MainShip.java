package gdx.lessons.lesson7.classfiles.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson7.classfiles.base.Ship;
import gdx.lessons.lesson7.classfiles.math.Rect;
import gdx.lessons.lesson7.classfiles.pool.BulletPool;
import gdx.lessons.lesson7.classfiles.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        //принимаем объект пула вызрывов
        this.explosionPool = explosionPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        v0.set(0.5f, 0);
        reloadInterval = 0.2f;
        bulletHeight = 0.01f;
        damage = 1;
        hp = 100;
        bulletV.set(0, 0.5f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                pressedRight = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft = true;
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    /**
     * Метод обработки столкновений гланого корабля со снарядом противника.
     * @param bullet - снаряд(здесь по логике - корабля противника)
     * @return - true - есть столкновение(здесь - попадание снаряда в главный корабль)
     */
    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getTop() < getBottom()
                //чтобы снаряд визуально долетал до центра главного корабля
                || bullet.getBottom() > pos.y
        );
    }

    public void dispose() {
        sound.dispose();
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

}
