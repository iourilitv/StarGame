package gdx.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.base.Ship;
import gdx.stargame.math.Rect;
import gdx.stargame.pool.BulletPool;
import gdx.stargame.pool.ExplosionPool;

/**
 * Класс спрайта корабля игрока(главного корабля).
 */
public class MainShip extends Ship {

    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    //инициируем константу значения жизни корабля главного корабля
    private static final int HP = 10;
    //инициируем константу значения урона наносимого главным кораблем
    private static final int DAMAGE = 1;

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
        damage = DAMAGE;
        //запоминаем по константу значения урона наносимого главным кораблем для доступа извне
        constDamage = DAMAGE;
        //устанавливаем по константе переменную текущего значения жизни главного корабля
        hp = HP;
        //запоминаем по константу значения жизни главного корабля для доступа извне
        constHp = HP;
        bulletV.set(0, 0.5f);
        //задаем параметры звука выстрелов корабля
        soundVolume = 0.3f;//громкость звука
        soundPitch = 1f;//тон звука
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
        pos.mulAdd(v, delta);
        //если главный корабль вышел на половину за правый край игрового поля
        if (pos.x >= worldBounds.getRight()) {
            setRight(worldBounds.getRight() + getHalfWidth() - delta);
            stop();
        } else if (pos.x <= worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft() - getHalfWidth() + delta);
            stop();
        }

//        if (getRight() > worldBounds.getRight()) {
//            setRight(worldBounds.getRight());
//            stop();
//        }
//        if (getLeft() < worldBounds.getLeft()) {
//            setLeft(worldBounds.getLeft());
//            stop();
//        }
    }

    public void keyDown(int keycode) {
//        //если главный корабль удален//FIXME
//        if(isDestroyed()){
//            //выходим, игнорируя событие управления корабля
//            return;
//        }
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
//        //если главный корабль удален//FIXME
//        if(isDestroyed()){
//            //выходим, игнорируя событие управления корабля
//            return;
//        }
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
//        //если главный корабль удален//FIXME
//        if(isDestroyed()){
//            //выходим, игнорируя событие управления корабля
//            return false;
//        }

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
//        //если главный корабль удален//FIXME
//        if(isDestroyed()){
//            //выходим, игнорируя событие управления корабля
//            return false;
//        }
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

    /**
     * Метод меняет настройки корабля противника в зависимости от текущего уровня игры
     * @param level - текущий уровень игры
     */
    public void changeShipSettingsByLevel(int level){
        //не меняем значение жизни от значения уровня
//        this.hp = constHp;
        //увеличиваем параметры корабля в зависимости от текущего уровня игры
//        this.v0.set(constV0.scl(level));
//        this.bulletV.set(0, constBulletV.y * level);
        this.damage = constDamage * level;
//        this.reloadInterval = constReloadInterval / level;
        //запоминаем новое значение константы жизни корабля
        this.hp = constHp * level;
    }

    /**
     * Метод, обрабатывающий режим игры "начать новую игру"
     * @param worldBounds - прямоугольник игрового мира в мировых координатах
     */
    public void startNewGame(Rect worldBounds) {
        //сбрасываем переменые состояния клавиш
        pressedLeft = false;
        pressedRight = false;
        //сбрасываем переменые состояния касания и кликов мыши
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        //останавливаем движение главного корабля
        stop();
        //устанавливаем по константе переменную текущего значения жизни главного корабля
        hp = HP;
        //запоминаем по константу значения жизни главного корабля для доступа извне
        constHp = HP;
        //устанавливаем начальную координату позиции корабля по горизонтали
        pos.x = worldBounds.pos.x;
        //сбрасываем флаг трупа главного корабля
        destroyed = false;
    }
}
