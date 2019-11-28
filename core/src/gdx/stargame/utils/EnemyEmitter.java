package gdx.stargame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.math.Rect;
import gdx.stargame.math.Rnd;
import gdx.stargame.pool.EnemyPool;
import gdx.stargame.sprite.Enemy;

/**
 * Класс организует вызов кораблей противника из пула и настройки их параметров.
 */
public class EnemyEmitter {

    private static final float SMALL_BULLET_HEIGHT = 0.01f;
    private static final float SMALL_BULLET_VY = -0.3f;
    private static final int SMALL_BULLET_DAMAGE = 1;
    private static final float SMALL_RELOAD_INTERVAL = 3f;
    private static final float SMALL_HEIGHT = 0.1f;
    private static final int SMALL_HP = 1;
    //объявляем константы параметров звука маленького корабля
    private static final float SMALL_SOUND_VOLUME = 0.2f;//уровень громкости звука
    private static final float SMALL_SOUND_PITCH = 2;//уровень тона звука

    private static final float MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float MIDDLE_BULLET_VY = -0.25f;
    private static final int MIDDLE_BULLET_DAMAGE = 5;
    private static final float MIDDLE_RELOAD_INTERVAL = 4f;
    private static final float MIDDLE_HEIGHT = 0.15f;
    private static final int MIDDLE_HP = 5;
    //объявляем константы параметров звука среднего корабля
    private static final float MIDDLE_SOUND_VOLUME = 0.5f;//уровень громкости звука
    private static final float MIDDLE_SOUND_PITCH = 1f;//уровень тона звука

    private static final float BIG_BULLET_HEIGHT = 0.03f;
    private static final float BIG_BULLET_VY = -0.2f;
    private static final int BIG_BULLET_DAMAGE = 10;
    private static final float BIG_RELOAD_INTERVAL = 2f;
    private static final float BIG_HEIGHT = 0.2f;
    private static final int BIG_HP = 10;
    //объявляем константы параметров звука большого корабля
    private static final float BIG_SOUND_VOLUME = 1f;//уровень громкости звука
    private static final float BIG_SOUND_PITCH = 0.5f;//уровень тона звука

    //инициируем константные значения векторов скорости для кораблей противника
    private final Vector2 enemySmallV = new Vector2(0, -0.2f);
    private final Vector2 enemyMiddleV = new Vector2(0, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0, -0.005f);

    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;
    private Rect worldBounds;

    private TextureRegion[] enemySmallRegions;
    private TextureRegion[] enemyMiddleRegions;
    private TextureRegion[] enemyBigRegions;
    private TextureRegion bulletRegion;

    //инициируем прямоугольник зоны действия корабля
    private Rect coverageArea = new Rect();
    //инициируем константу делителя верхней границы начальной скорости корабля
    private final int LOW_V0_DENOMINATOR = 5;
    private final int HIGH_V0_DENOMINATOR = 10;

    private Sound sound;

    public EnemyEmitter(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMiddleRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    /**
     * Метод вызова кораблей противника.
     * @param delta - период времени между кадрами
     * @param level- номер текущего уровня игры
     */
    public void generate(float delta, int level) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            //генерирует и устанавливаем начальные параметры любому типу корабля противника
            generateShipProperties(enemy);
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        SMALL_BULLET_HEIGHT,
                        SMALL_BULLET_VY,
                        SMALL_BULLET_DAMAGE,
                        SMALL_RELOAD_INTERVAL,
                        sound,
                        SMALL_HEIGHT,
                        SMALL_HP
                );
//                //меняем настройки корабля противника в зависимости от уровня игры
//                enemy.changeShipSettingsByLevel(level);//FIXME

                //устанавливаем параметры звука выстрелов маленького корабля
                enemy.setSound(SMALL_SOUND_VOLUME, SMALL_SOUND_PITCH);
            } else if (type < 0.8f) {
                enemy.set(
                        enemyMiddleRegions,
                        enemyMiddleV,
                        bulletRegion,
                        MIDDLE_BULLET_HEIGHT,
                        MIDDLE_BULLET_VY,
                        MIDDLE_BULLET_DAMAGE,
                        MIDDLE_RELOAD_INTERVAL,
                        sound,
                        MIDDLE_HEIGHT,
                        MIDDLE_HP
                );
//                //меняем настройки корабля противника в зависимости от уровня игры
//                enemy.changeShipSettingsByLevel(level);//FIXME

                //устанавливаем параметры звука выстрелов среднего корабля
                enemy.setSound(MIDDLE_SOUND_VOLUME, MIDDLE_SOUND_PITCH);
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        BIG_BULLET_HEIGHT,
                        BIG_BULLET_VY,
                        BIG_BULLET_DAMAGE,
                        BIG_RELOAD_INTERVAL,
                        sound,
                        BIG_HEIGHT,
                        BIG_HP
                );
//                //меняем настройки корабля противника в зависимости от уровня игры
//                enemy.changeShipSettingsByLevel(level);//FIXME

                //устанавливаем параметры звука выстрелов большого корабля
                enemy.setSound(BIG_SOUND_VOLUME, BIG_SOUND_PITCH);
            }

            //передаем кораблю прямоугольник зоны действия корабля
            enemy.setAdditionally(coverageArea);
            //меняем настройки корабля противника в зависимости от уровня игры
            enemy.changeShipSettingsByLevel(level);

//            //устанавливаем случайные координаты по горизонтали любому типу корабля противника
//            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),//FIXME
//                    worldBounds.getRight() - enemy.getHalfWidth());
//            //устанавливаем сразу за верхним краем игрового мира корабль противника любого типа
//            enemy.setBottom(worldBounds.getTop());

        }
    }

    /**
     * Метод генерирует и устанавливаем начальные параметры любому типу корабля противника.
     */
    private void generateShipProperties(Enemy enemy) {
        //генерируем случайние значения координат по X и Y в рамках игрового поля
        float rndX = Rnd.nextFloat(- worldBounds.getHalfWidth(), worldBounds.getHalfWidth());
//        enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
//                worldBounds.getRight() - enemy.getHalfWidth());

        //устанавливаем сразу за верхним краем игрового мира корабль противника любого типа
        enemy.setBottom(worldBounds.getTop());

//        //устанавливаем значения зоны действия корабля
//        coverageArea.setSize(worldBounds.getWidth(), worldBounds.getHeight());
        //устанавливаем положение зоны действия по X
        coverageArea.pos.x = rndX;
        //устанавливаем значения зоны действия корабля
        coverageArea.setWidth(worldBounds.getWidth());
        coverageArea.setHeight(worldBounds.getHeight());

//        //TODO temporarily
//        System.out.println("EnemyEmitter.generateShipProperties damage= " + enemy.getDamage() +
//                ", rndX= " + rndX +
//                ", coverageArea.getWidth= " + coverageArea.getWidth() +
//                ", coverageArea.getHeight()= " + coverageArea.getHeight() +
//                ", coverageArea.getLeft()= " + coverageArea.getLeft() +
//                ", coverageArea.getRight()= " + coverageArea.getRight());

        //TODO temporarily
        System.out.println("EnemyEmitter.generateShipProperties damage= " + enemy.getDamage() +
                ", coverageArea.getLeft()= " + coverageArea.getLeft() +
                ", coverageArea.getRight()= " + coverageArea.getRight());

        //устанавливаем значения вектора начальной позиции корабля по x в середине его зоны покрытия
        //все выплывают сверху экрана
//        pos0.set(rndX, worldBounds.getTop() + getHeight());//FIXME
        enemy.pos.x = rndX;

        //генерируем случайные значения знака скорости
        int minus = Rnd.nextFloat(- 1f, 1f) < 0 ? - 1 : 1;
        //генерируем значения модуля начальной скорости по X
        rndX = Rnd.nextFloat(worldBounds.getHalfWidth() / HIGH_V0_DENOMINATOR,
                worldBounds.getHalfWidth() / LOW_V0_DENOMINATOR);

//        float rndY = Rnd.nextFloat(worldBounds.getHalfHeight() / HIGH_V0_DENOMINATOR,
//                worldBounds.getHalfHeight() / LOW_V0_DENOMINATOR);
        //устанавливаем значения вектора начальной скорости корабля
//        v0.set(minus * rndX, - rndY);//(0.05f, -0.02f)

        enemySmallV.x = minus * rndX;
        enemyMiddleV.x = minus * rndX;
        enemyBigV.x = minus * rndX;
    }

    public void dispose() {
        sound.dispose();
    }
}
