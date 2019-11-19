package gdx.stargame.lessons.lesson5.hw.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson5.hw.base.Sprite;
import gdx.stargame.lessons.lesson5.hw.math.Rect;
import gdx.stargame.lessons.lesson5.hw.pool.BulletPool;
import gdx.stargame.lessons.lesson5.hw.settings.SoundSettings;
import gdx.stargame.lessons.lesson5.hw.settings.Source;

public class MainShip extends Sprite {

    private static final float BOTTOM_MARGIN = 0.05f;//константа сдвига начальной позиции
    private static final int INVALID_POINTER = -1;//константа несуществующего пальца
    //инициируем константу вектора начальной скорости корабля
    private final Vector2 v0 = new Vector2(0.5f, 0);
    //инициируем переменную вектора текущей скорости корабля
    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    //инициируем константу вектора начальной скорости снаряда этого корабля
    private final Vector2 bulletV = new Vector2(0, 0.5f);
    //флаги "нажато" для кнопок движения вправо и влево
    private boolean pressedLeft;
    private boolean pressedRight;
    //инициируем переменные для хранения текущего пальца
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    //инициируем константу времени между выстрелами(зарядки)
//    private final float reloadInterval = 0.2f;
    private float reloadInterval = 0.2f;

    //инициируем переменную таймера времени между выстрелами(зарядки)
    private float reloadTimer = 0f;

    //инициируем константы объектов звука выстрела снарядом и лазером
    private final Sound bulletShot = Gdx.audio.newSound(
            Gdx.files.internal(Source.SOUND_MAIN_SHIP_B1_SHOOT.sourceName()));
    private final Sound laserShot = Gdx.audio.newSound(
            Gdx.files.internal(Source.SOUND_MAIN_SHIP_L1_SHOOT.sourceName()));
    //объявляем переменную для объекта текущего типа звука выстрела
    private Sound currentShotSound;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        //передаем в конструктор родителя текстуру-регион, и
        // параметры нарезки(количества): строк, колонок и фрагментов
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        //устанавливаем текущим звуком выстрела выстрел снарядом
        currentShotSound = bulletShot;
    }

    /**
     * Метод установки размера корабля при каждом изменении размеров игрового поля.
     * @param worldBounds - прамоугольник игрового мира.
     */
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    /**
     * Метод обновления состояния корабля. Движение корабля, стрельба и т.п
     * @param delta - время между кадрами
     */
    @Override
    public void update(float delta) {
        //***автоматическая стрельба корабля***
        //инкрементируем переменную таймера загрузки снаряда
        reloadTimer += delta;
        //если значение таймера превысило значение установленной константы
        if (reloadTimer > reloadInterval) {
            //обнуляем таймер
            reloadTimer = 0f;
            //производим выстрел
            shoot();
        }
        //***движение корабля***
        //обновляем вектор позиции корабля
        //операция одновременного прибавления вектора и умножения на скаляр
        pos.mulAdd(v, delta);
        //если корабль вышел за правый край игрового поля
        if (getRight() > worldBounds.getRight()) {
            //устанавливаем его правый край по краю мира
            setRight(worldBounds.getRight());
            //останавливаем движение корабля
            stop();
        }
        //тоже самое для двидение влево
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    /**
     * Метод обработки нажатия клавиши. Корабль движется в направлении в зависимости от
     * нажатой клавиши
     * @param keycode - код нажатой клавиши.
     */
    public void keyDown(int keycode) {
        switch (keycode) {
            //если нажаты кнопки движения вправо
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                //сдвигаем корабль вправо
                moveRight();
                //устанавливаем флаг "нажато" у правой кнопки
                pressedRight = true;
                break;
            //тоже самое для движения влево
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft = true;
                break;
            case Input.Keys.UP:

                //TODO temporarily. Меняем тип звука выстрела с лазера на снаряд
                // по каждому нажатию клавиши
                if(currentShotSound == laserShot){
                    currentShotSound = bulletShot;
                } else {
                    currentShotSound = laserShot;
                }

                shoot();
                break;
        }
    }

    /**
     * Метод обработки отпускания нажатой клавиши.
     * @param keycode - код нажатой клавиши.
     */
    public void keyUp(int keycode) {
        switch (keycode) {
            //если отпущены кнопки движения вправо
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                //сбрасываем флаг "нажато" у правой кнопки
                pressedRight = false;
                //если нажати кнопки движения влево
                if (pressedLeft) {
                    //движемся влево
                    moveLeft();
                //если обе кнопки отпущены
                } else {
                    //останавливаем движение корабля
                    stop();
                }
                break;
            //тоже самое для движения влево
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

    /**
     * Метод обработки касания.
     * @param touch - вектор позиции касания
     * @param pointer - коснувшийся палец
     * @return - не важно
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //если координата касания по x находится левее от центра игрового поля
        if (touch.x < worldBounds.pos.x) {
            //если касание валидно
            if (leftPointer != INVALID_POINTER) return false;
            //запоминаем левый палец
            leftPointer = pointer;
            //двигаем корабль влево
            moveLeft();
        //если касание - справа, тоже самое для правого
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    /**
     * Метод обработки отпускания касания.
     * @param touch - вектор позиции касания
     * @param pointer - коснувшийся палец
     * @return - не важно
     */
    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        //если отпущен левый палец
        if (pointer == leftPointer) {
            //помечаем левый палец как не существующий
            leftPointer = INVALID_POINTER;
            //если при этом нажат правый палец
            if (rightPointer != INVALID_POINTER) {
                //двигаем объект вправо
                moveRight();
            //если оба пальца отпущены
            } else {
                //останавливаем корабль
                stop();
            }
        //тоже самое для правого пальца
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
     * Метод движения корабля вправо.
     */
    private void moveRight() {
        v.set(v0);
    }

    /**
     * Метод движения корабля влево.
     * Направление движения корабля меняется на противоположное.
     */
    private void moveLeft() {
        //устанавливаем вектору скорости значение заданной константы скорости и
        // меняем направление на обратное
        v.set(v0).rotate(180);
    }

    /**
     * Метод остановки корабля
     */
    private void stop() {
        //обнуляем значение вектора скорости
        v.setZero();
    }

    /**
     * Метод стрельбы корабля.
     */
    private void shoot() {
        //запоминаем в локальную переменную вызыванный из пула объект снаряда
        Bullet bullet = bulletPool.obtain();
        //устанавливаем параметры снаряда
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);

        //воспроизводим звук выстрела текущим типом звука
        // с уровнем громкости по значению для всех эффектов
        //и сохраняет идентификатор для дальнейших изменений с панарамой - по середине
        long id = currentShotSound.play(SoundSettings.SOUND_EFFECTS_LEVEL_MAIN.getPower());

//        //параметры: volume(громкость, 1f - default); pitch(высота тона, 1f - default) и
//        //panorama(, ? - default)
//        bulletShot.play(0.1f, 1f, 0f);

        //увеличивает высоту звука в 2 раза от оригинальной высоты
//        bulletShot.setPitch(id, 2f);

//        //устанавливает панораму звука(динамик) с определенным ID в с определенной стороны
//        // - 1f (слева), 0f (по середине), 1f (справа) на полную громкость(1f)
//        bulletShot.setPan(id, 0f, 1f);
    }

    /**
     * Метод выгружает из памяти ресурсы корабля героя
     */
    public void dispose(){
        bulletShot.dispose();
        laserShot.dispose();
    }

    public Vector2 getV0() {
        return v0;
    }

    public Vector2 getV() {
        return v;
    }

    /**
     * Метод обработки паузы игры
     */
    public void pause(){
        stop();

        stopShoot();
    }

    private void stopShoot() {
        reloadInterval = Float.MAX_VALUE;

    }

}
