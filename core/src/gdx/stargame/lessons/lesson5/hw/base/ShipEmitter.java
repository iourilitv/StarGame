package gdx.stargame.lessons.lesson5.hw.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.stargame.lessons.lesson3.classbook.Bullet;
import gdx.stargame.lessons.lesson5.hw.math.Rect;
import gdx.stargame.lessons.lesson5.hw.pool.BulletPool;
import gdx.stargame.lessons.lesson5.hw.pool.EnemyShipPool;
import gdx.stargame.lessons.lesson5.hw.settings.Source;
import gdx.stargame.lessons.lesson5.hw.sprite.EnemyShip;

/**
 * Класс ShipEmitter занимается управлением кораблями и является синглтоном.
 */
public class ShipEmitter {
    //принимаем границы игрового мира
    private Rect worldBounds;
    //инициируем константу времени между выстрелами(зарядки)
    private final float RELOAD_SHIP_INTERVAL = 0.2f;
    //инициируем переменную таймера времени между выстрелами(зарядки)
    private float reloadShipTimer = 0f;
    //инициируем констанму максимального количества кораблей на игровом поле
    private final int MAX_SHIP_COUNT_ON_SCREEN = 2;

    //объявляем переменную атласа
    private TextureAtlas atlas;
    //объявляем пул для кораблей
    private EnemyShipPool enemyShipPool;
    //объявляем пул для снарядов
    private BulletPool bulletPool;//FIXME     //bulletEnemy

    public ShipEmitter() {
        //создаем объект атласа
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        //создаем объект пула для вражеских кораблей
        enemyShipPool = new EnemyShipPool();
        bulletPool = new BulletPool();
    }

    public void resize(Rect worldBounds) {
        //принимаем границы игрового мира
        this.worldBounds = worldBounds;
        //устанавливаем размеры кораблей
        enemyShipPool.resize(worldBounds);
    }

    public void draw(SpriteBatch batch) {
        //прорисовываем корабли
        enemyShipPool.drawActiveSprites(batch);
    }

    public void update(float delta) {
        //обновляем корабли
        enemyShipPool.updateActiveSprites(delta);
        //вызываем корабли
        obtainShips(delta);
    }

    /**
     * Метод вызова автоматического вызова кораблей
     * @param delta - период времени между кадрами
     */
    private void obtainShips(float delta) {
        //***автоматический вызов корабля***
        //инкрементируем переменную таймера вызова
        reloadShipTimer += delta;
        //если значение таймера превысило значение установленной константы
        if (reloadShipTimer > RELOAD_SHIP_INTERVAL) {
            //обнуляем таймер
            reloadShipTimer = 0f;

            //если на поле кораблей меньше максимального
            if(!isMaximumShips()) {
                //вызываем корабль
                EnemyShip enemyShip = enemyShipPool.obtain(atlas, bulletPool);
                enemyShip.resize(worldBounds);
            }
        }
    }

    /**
     * МЕтод проверки не находится ли на поле кораблей больше или равно заданного максимума.
     * @return - true - количество кораблей на поле не меньше разрешенного максимума
     */
    private boolean isMaximumShips() {
        return enemyShipPool.activeObjects.size() >= MAX_SHIP_COUNT_ON_SCREEN;
    }

    /**
     * Метод переноса помеченных на удаление объектов игры из пула активных объектов в пул свободных.
     */
    public void freeAllDestroyedActiveSprites() {

//        bulletPool.freeAllDestroyedActiveSprites();

        //освобождаем удаленные корабли противника
        enemyShipPool.freeAllDestroyedActiveSprites();
    }

    public void dispose() {
        //выгружаем из памяти объект атласа
        atlas.dispose();

    }


    /**
     * Метод обработки паузы игры
     */
    public void pause(){

    }
}

