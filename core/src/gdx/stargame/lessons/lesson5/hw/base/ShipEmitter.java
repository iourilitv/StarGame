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
//    private static final ShipEmitter ourInstance = new ShipEmitter();

//    public static ShipEmitter getInstance() {
//        return ourInstance;
//    }
    //bulletEnemy
    private TextureAtlas atlas;

    //объявляем пул для снарядов
    private EnemyShipPool enemyShipPool;

//    //объявляем пул для снарядов
    private BulletPool bulletPool;

    //TODO temporarily
    EnemyShip enemyShip;

//    Bullet[] bullets;

    public ShipEmitter() {
        //создаем объект атласа
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        //создаем объект пула для вражеских кораблей
        enemyShipPool = new EnemyShipPool();
        bulletPool = new BulletPool();

        enemyShip = enemyShipPool.newObject(atlas, bulletPool);

//        bullets = new Bullet[200];
//        for (int i = 0; i < bullets.length; i++) {
//            bullets[i] = new Bullet();
//        }
    }

    public void resize(Rect worldBounds) {
        enemyShip.resize(worldBounds);
    }

    public void draw(SpriteBatch batch) {
        enemyShip.draw(batch);
    }

    public void update(float delta) {
        enemyShip.update(delta);
//        for (Bullet o : bullets) {
//            if (o.active) {
//                o.update(dt);
//            }
//        }
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


}

