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

/**
 * Класс корабля противника
 */
public class EnemyShip extends Sprite {

//    private static final float BOTTOM_MARGIN = 0.05f;//константа сдвига начальной позиции
//    private static final int INVALID_POINTER = -1;//константа несуществующего пальца
    //инициируем константу вектора начальной скорости корабля
//    private final Vector2 v0 = new Vector2(0.5f, 0);
    //инициируем переменную вектора текущей скорости корабля
    private final Vector2 v = new Vector2();

    private Rect worldBounds;
//    private final TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");

    //инициируем константу высоты объекта корабля
    private final float SHIP_HEIGHT = 0.15f;
    //инициируем константу вектора начальной скорости корабля
    private final Vector2 v0 = new Vector2(0.05f, -0.02f);

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
//    //инициируем константу вектора начальной скорости снаряда этого корабля
//    private final Vector2 bulletV = new Vector2(0, - 0.5f);
//    //инициируем константу времени между выстрелами(зарядки)
//    private final float reloadInterval = 0.2f;
//    //инициируем переменную таймера времени между выстрелами(зарядки)
//    private float reloadTimer = 0f;

//    //инициируем константы объектов звука выстрела снарядом и лазером
//    private final Sound bulletShot = Gdx.audio.newSound(
//            Gdx.files.internal(Source.SOUND_ENEMY_B1_SHOOT.sourceName()));
//    private final Sound laserShot = Gdx.audio.newSound(
//            Gdx.files.internal(Source.SOUND_ENEMY_L1_SHOOT.sourceName()));
//    //объявляем переменную для объекта текущего типа звука выстрела
//    private Sound currentShotSound;


    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool) {
        //передаем в конструктор родителя текстуру-регион, и
        // параметры нарезки(количества): строк, колонок и фрагментов
        super(atlas.findRegion("enemy0"), 1, 2, 2);
        //устанавливаем текущую скорость по вектору начальной скорости
        v.set(v0);

//        this.bulletPool = bulletPool;
//        bulletRegion = atlas.findRegion("bulletMainShip");
//        //устанавливаем текущим звуком выстрела выстрел снарядом
//        currentShotSound = bulletShot;
    }

    /**
     * Метод установки размера корабля при каждом изменении размеров игрового поля.
     * @param worldBounds - прамоугольник игрового мира.
     */
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;

        setHeightProportion(SHIP_HEIGHT);
        setTop(worldBounds.getTop()/* + BOTTOM_MARGIN*/);

        //TODO temporarily
//        System.out.println("EnemyShip.resize this=" + this);
    }

    /**
     * Метод обновления состояния корабля. Движение корабля, стрельба и т.п
     * @param delta - время между кадрами
     */
    @Override
    public void update(float delta) {
//        //***автоматическая стрельба корабля***
//        //инкрементируем переменную таймера загрузки снаряда
//        reloadTimer += delta;
//        //если значение таймера превысило значение установленной константы
//        if (reloadTimer > reloadInterval) {
//            //обнуляем таймер
//            reloadTimer = 0f;
//            //производим выстрел
//            shoot();
//        }
        //***движение корабля***
        //обновляем вектор позиции корабля
        //операция одновременного прибавления вектора и умножения на скаляр
        pos.mulAdd(v, delta);

        //TODO temporarily
//        System.out.println("EnemyShip.update this= " + this);

        //если корабль вышел за правый край игрового поля
        if (getRight() > worldBounds.getRight()) {
            //устанавливаем его правый край по краю мира
            setRight(worldBounds.getRight());
            //меняем направление движения
            changeDirectionX();
//            moveLeft();
//            v.rotate(-90);
//            //останавливаем движение корабля
//            stop();
        }
        //тоже самое для двидение влево
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            changeDirectionX();
//            moveRight();
//            v.rotate(90);
//            stop();
        }
        //если корабль вышел за нижнюю границу
//        if(getTop() < worldBounds.getBottom()){
        if(getBottom() < worldBounds.getBottom()){
            //удаляем корабль
            destroy();
        }
    }

    private void changeDirectionX() {
        //устанавливаем вектору скорости значение заданной константы скорости и
        // меняем направление на обратное
//        v.set(v0).rotate(180);
        v.x *= - 1;
    }

//    /**
//     * Метод движения корабля вправо.
//     */
//    private void moveRight() {
////        v.set(v0);
//        v.rotate(90);
//    }

//    /**
//     * Метод движения корабля влево.
//     * Направление движения корабля меняется на противоположное.
//     */
//    private void moveLeft() {
//        //устанавливаем вектору скорости значение заданной константы скорости и
//        // меняем направление на обратное
////        v.set(v0).rotate(180);
//        v.x *= - 1;
//    }

    /**
     * Метод остановки корабля
     */
    private void stop() {
        //обнуляем значение вектора скорости
        v.setZero();
    }

//    /**
//     * Метод стрельбы корабля.
//     */
//    private void shoot() {
//        //запоминаем в локальную переменную вызыванный из пула объект снаряда
//        Bullet bullet = bulletPool.obtain();
//        //устанавливаем параметры снаряда
//        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
//
//        //воспроизводим звук выстрела текущим типом звука
//        // с уровнем громкости по значению для всех эффектов
//        //и сохраняет идентификатор для дальнейших изменений с панарамой - по середине
//        long id = currentShotSound.play(SoundSettings.SOUND_EFFECTS_LEVEL_MAIN.getPower());
//
////        //параметры: volume(громкость, 1f - default); pitch(высота тона, 1f - default) и
////        //panorama(, ? - default)
////        bulletShot.play(0.1f, 1f, 0f);
//
//        //увеличивает высоту звука в 2 раза от оригинальной высоты
////        bulletShot.setPitch(id, 2f);
//
////        //устанавливает панораму звука(динамик) с определенным ID в с определенной стороны
////        // - 1f (слева), 0f (по середине), 1f (справа) на полную громкость(1f)
////        bulletShot.setPan(id, 0f, 1f);
//    }

    /**
     * Метод выгружает из памяти ресурсы корабля героя
     */
    public void dispose(){
//        bulletShot.dispose();
//        laserShot.dispose();
    }
}
