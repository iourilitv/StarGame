package gdx.stargame.lessons.lesson5.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson5.hw.base.Sprite;
import gdx.stargame.lessons.lesson5.hw.math.Rect;
import gdx.stargame.lessons.lesson5.hw.math.Rnd;
import gdx.stargame.lessons.lesson5.hw.pool.BulletPool;

/**
 * Класс корабля противника
 */
public class EnemyShip extends Sprite {


    //инициируем константу делителя верхней границы начальной скорости корабля
    private final int LOW_V0_DENOMINATOR = 5;
    private final int HIGH_V0_DENOMINATOR = 10;

    //принимаем прямоугольник игрового поля
    private Rect worldBounds;
//    //принимаем объект атласа
//    private TextureAtlas atlas;
//    //объявляем переменную для тектуры картинки корабля
//    TextureRegion region;

    //объявляем тип корабля //FIXME added new class Ship?

    //инициируем константу высоты объекта корабля
    private final float SHIP_HEIGHT = 0.15f;
    //объявляем вектор стартовой позиции корабля
    //объявляем границы зоны действия корабля
    private Rect coverageArea = new Rect();
    private Vector2 pos0 = new Vector2();
    //инициируем переменную вектора начальной скорости корабля
    private Vector2 v0 = new Vector2();//0.05f, -0.02f
    //инициируем переменную вектора текущей скорости корабля
    private final Vector2 v = new Vector2();

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
//        this.atlas = atlas;

//        this.bulletPool = bulletPool;
//        bulletRegion = atlas.findRegion("bulletMainShip");
//        //устанавливаем текущим звуком выстрела выстрел снарядом
//        currentShotSound = bulletShot;

    }

    /**
     * Метод генерирует начальные параметры корабля.
     */
    private void generateShipProperties() {
        //        region = atlas.findRegion("enemy0");

        //генерируем случайние значения координат по X и Y в рамках игрового поля
        float rndX = Rnd.nextFloat(- worldBounds.getHalfWidth(), worldBounds.getHalfWidth());
//        float rndY = Rnd.nextFloat(- worldBounds.getHalfHeight(), worldBounds.getHalfHeight());

        //устанавливаем значения зоны действия корабля
        coverageArea.setSize(worldBounds.getWidth(), worldBounds.getHeight());
        //устанавливаем положение зоны действия по X
        coverageArea.pos.x = rndX;

        //устанавливаем значения вектора начальной позиции корабля
        //все выплывают сверху экрана
        pos0.set(rndX, worldBounds.getTop() + getHeight());

        //генерируем случайные значения знака скорости
        int minus = Rnd.nextFloat(- 1f, 1f) < 0 ? - 1 : 1;
        //генерируем значения модуля начальной скорости по X и Y
        rndX = Rnd.nextFloat(worldBounds.getHalfWidth() / HIGH_V0_DENOMINATOR,
                worldBounds.getHalfWidth() / LOW_V0_DENOMINATOR);
        float rndY = Rnd.nextFloat(worldBounds.getHalfHeight() / HIGH_V0_DENOMINATOR,
                worldBounds.getHalfHeight() / LOW_V0_DENOMINATOR);
        //устанавливаем значения вектора начальной скорости корабля
        v0.set(minus * rndX, - rndY);//(0.05f, -0.02f)

    }

    /**
     * Метод установки параметров корабля.
     * //@param region - картинка корабля
     * @param pos0 - вектор стартовой позиции корабля
     * @param v0 - вектор скорости корабля
     * //@param height - высота картинки корабля
     * @param coverageArea - прямоугольник границ зоны действия корабля
     */
    public void set(
//            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
//            float height,
            Rect coverageArea
    ) {
        //инициируем переменные снаряда по полученным параметрам
//        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        this.coverageArea = coverageArea;
        setHeightProportion(SHIP_HEIGHT);//(height)
    }

    /**
     * Метод установки размера корабля при каждом изменении размеров игрового поля.
     * @param worldBounds - прамоугольник игрового мира.
     */
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        //генерируем настройки корабля
        generateShipProperties();
        //устанавливаем параметры корабля
        set(pos0, v0, coverageArea);
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

        //если корабль вышел за правый край своей зоны действия
        if (getRight() > coverageArea.getRight()) {
            //устанавливаем его правый край по краю своей зоны действия
            setRight(coverageArea.getRight());
            //меняем направление движения
            changeDirectionX();
        }
        //тоже самое для движение влево
        if (getLeft() < coverageArea.getLeft()) {
            setLeft(coverageArea.getLeft());
            changeDirectionX();
        }

        //если корабль вышел за нижнюю границу
        if(getTop() < worldBounds.getBottom()){
//        if(getBottom() < worldBounds.getBottom()){
            //удаляем корабль
            destroy();
        }
        //FIXME добавить только если корабли будут появляться сбоку и двигаться вверх
//        if(getTop() > worldBounds.getTop()){
//            //удаляем корабль
//            destroy();
//        }
    }

    private void changeDirectionX() {
        //устанавливаем вектору скорости значение заданной константы скорости и
        // меняем направление на обратное
        v.x *= - 1;
    }

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
