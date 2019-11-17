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

    private Rect worldBounds;

    //инициируем константу времени между выстрелами(зарядки)
    private final float RELOAD_SHIP_INTERVAL = 0.2f;
    //инициируем переменную таймера времени между выстрелами(зарядки)
    private float reloadShipTimer = 0f;

    //bulletEnemy
    private TextureAtlas atlas;

    //объявляем пул для снарядов
    private EnemyShipPool enemyShipPool;

//    //объявляем пул для снарядов
    private BulletPool bulletPool;

    //TODO temporarily
//    EnemyShip enemyShip;

//    Bullet[] bullets;

    public ShipEmitter() {
        //создаем объект атласа
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        //создаем объект пула для вражеских кораблей
        enemyShipPool = new EnemyShipPool();
        bulletPool = new BulletPool();

//        enemyShip = enemyShipPool.newObject(atlas, bulletPool);

//        enemyShipPool.obtain(atlas, bulletPool);
//
//        //TODO temporarily
//        System.out.println("ShipEmitter.Constructor enemyShipPool=" + enemyShipPool +
//                ", enemyShipPool.activeObjects.size()= " + enemyShipPool.activeObjects.size());

//
//        System.out.println("enemyShipPool.activeObjects.size()= " + enemyShipPool.activeObjects.size());

//        bullets = new Bullet[200];
//        for (int i = 0; i < bullets.length; i++) {
//            bullets[i] = new Bullet();
//        }
    }

    public void resize(Rect worldBounds) {
//        enemyShip.resize(worldBounds);

        this.worldBounds = worldBounds;

        enemyShipPool.resize(worldBounds);

        //TODO temporarily
        System.out.println("ShipEmitter.resize enemyShipPool=" + enemyShipPool +
                ", enemyShipPool.activeObjects.size()= " + enemyShipPool.activeObjects.size());

    }

    public void draw(SpriteBatch batch) {
//        enemyShip.draw(batch);
        enemyShipPool.drawActiveSprites(batch);
    }

    public void update(float delta) {
//        enemyShip.update(delta);

        enemyShipPool.updateActiveSprites(delta);

        //
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
            //вызываем корабль
            EnemyShip enemyShip = enemyShipPool.obtain(atlas, bulletPool);
            enemyShip.resize(worldBounds);
        }

        //TODO temporarily
//        System.out.println("ShipEmitter.obtainShip enemyShipPool=" + enemyShipPool +
//                ", enemyShipPool.activeObjects.size()= " + enemyShipPool.activeObjects.size());

    }

//    public void render(SpriteBatch batch) {
////        for (Bullet o : bullets) {
////            if (o.active) {
////                batch.draw(texture, o.position.x - 16, o.position.y - 16);
////            }
////        }
//    }

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
}

