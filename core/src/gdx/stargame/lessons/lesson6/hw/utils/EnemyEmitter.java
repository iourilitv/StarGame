package gdx.stargame.lessons.lesson6.hw.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson6.hw.math.Rect;
import gdx.stargame.lessons.lesson6.hw.math.Rnd;
import gdx.stargame.lessons.lesson6.hw.pool.EnemyPool;
import gdx.stargame.lessons.lesson6.hw.sprite.Enemy;

public class EnemyEmitter {
    //инициируем константы для маленького корабля
    private static final float SMALL_BULLET_HEIGHT = 0.01f;//высота тектуры снаряда
    private static final float SMALL_BULLET_VY = -0.3f;//скорость снаряда
    private static final int SMALL_BULLET_DAMAGE = 1;//размер наносимого снарядом урона
    private static final float SMALL_RELOAD_INTERVAL = 3f;//интервал между выпуском снарядов
    private static final float SMALL_HEIGHT = 0.1f;//высота текстуры корабля
    private static final int SMALL_HP = 1;//значение жизни корабля
    //ускорение в начале(BEGINNING_MODE)
    private static float SMALL_BEGINNING_MODE_VY_VALUE = -0.5f;
    //объявляем константы параметров звука маленького корабля
    private static final float SMALL_SOUND_VOLUME = 0.2f;//уровень громкости звука
    private static final float SMALL_SOUND_PITCH = 0.5f;//уровень тона звука

    //инициируем константы для среднего корабля
    private static final float MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float MIDDLE_BULLET_VY = -0.25f;
    private static final int MIDDLE_BULLET_DAMAGE = 5;
    private static final float MIDDLE_RELOAD_INTERVAL = 4f;
    private static final float MIDDLE_HEIGHT = 0.15f;
    private static final int MIDDLE_HP = 5;
    //ускорение в начале(BEGINNING_MODE)
    private static float MIDDLE_BEGINNING_MODE_VY_VALUE = -0.2f;
    //объявляем константы параметров звука среднего корабля
    private static final float MIDDLE_SOUND_VOLUME = 0.5f;//уровень громкости звука
    private static final float MIDDLE_SOUND_PITCH = 1f;//уровень тона звука

    //***инициируем константы для большого корабля***
    private static final float BIG_BULLET_HEIGHT = 0.03f;
    private static final float BIG_BULLET_VY = -0.2f;
    private static final int BIG_BULLET_DAMAGE = 10;
    private static final float BIG_RELOAD_INTERVAL = 2f;
    private static final float BIG_HEIGHT = 0.2f;
    private static final int BIG_HP = 10;
    //ускорение в начале(BEGINNING_MODE)
    private static final float BIG_BEGINNING_MODE_VY_VALUE = -0.05f;
    //объявляем константы параметров звука большого корабля
    private static final float BIG_SOUND_VOLUME = 1f;//уровень громкости звука
    private static final float BIG_SOUND_PITCH = 0.5f;//уровень тона звука

    private float generateInterval = 4f;//интервал между выпуском кораблей
    private float generateTimer;//таймер выпуска кораблей

    private EnemyPool enemyPool;
    private Rect worldBounds;

    private TextureRegion[] enemySmallRegions;//текстуры-регион маленького корабля
    private TextureRegion[] enemyMiddleRegions;
    private TextureRegion[] enemyBigRegions;
    private TextureRegion bulletRegion;//текстура снаряда противника

    private Vector2 enemySmallV0 = new Vector2(0, -0.2f);//начальная скорость маленького корабля
    private Vector2 enemyMiddleV0 = new Vector2(0, -0.03f);
    private Vector2 enemyBigV0 = new Vector2(0, -0.005f);

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

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            //генерируем маленькие корабли с вероятностью 50%
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV0,
                        bulletRegion,
                        SMALL_BULLET_HEIGHT,
                        SMALL_BULLET_VY,
                        SMALL_BULLET_DAMAGE,
                        SMALL_RELOAD_INTERVAL,
                        sound,
                        SMALL_HEIGHT,
                        SMALL_HP
                );
                //устанавливаем начальные параметры маленького корабля
                enemy.setBeginningMode(SMALL_BEGINNING_MODE_VY_VALUE);
                //устанавливаем параметры звука выстрелов маленького корабля
                enemy.setSound(SMALL_SOUND_VOLUME, SMALL_SOUND_PITCH, 0f);
            //генерируем средние корабли с вероятностью 30%
            } else if (type < 0.8f) {
                enemy.set(
                        enemyMiddleRegions,
                        enemyMiddleV0,
                        bulletRegion,
                        MIDDLE_BULLET_HEIGHT,
                        MIDDLE_BULLET_VY,
                        MIDDLE_BULLET_DAMAGE,
                        MIDDLE_RELOAD_INTERVAL,
                        sound,
                        MIDDLE_HEIGHT,
                        MIDDLE_HP
                );
                //устанавливаем начальные параметры среднего корабля
                enemy.setBeginningMode(MIDDLE_BEGINNING_MODE_VY_VALUE);
                //устанавливаем параметры звука выстрелов среднего корабля
                enemy.setSound(MIDDLE_SOUND_VOLUME, MIDDLE_SOUND_PITCH, 0f);
            //генерируем большие корабли с вероятностью 20%
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV0,
                        bulletRegion,
                        BIG_BULLET_HEIGHT,
                        BIG_BULLET_VY,
                        BIG_BULLET_DAMAGE,
                        BIG_RELOAD_INTERVAL,
                        sound,
                        BIG_HEIGHT,
                        BIG_HP
                );
                //устанавливаем начальные параметры большого корабля
                enemy.setBeginningMode(BIG_BEGINNING_MODE_VY_VALUE);
                //устанавливаем параметры звука выстрелов большого корабля
                enemy.setSound(BIG_SOUND_VOLUME, BIG_SOUND_PITCH, 0f);
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public void dispose() {
        sound.dispose();
    }
}
