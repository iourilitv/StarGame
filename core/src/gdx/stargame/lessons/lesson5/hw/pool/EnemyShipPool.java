package gdx.stargame.lessons.lesson5.hw.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.stargame.lessons.lesson5.hw.base.SpritesPool;
import gdx.stargame.lessons.lesson5.hw.math.Rect;
import gdx.stargame.lessons.lesson5.hw.sprite.Bullet;
import gdx.stargame.lessons.lesson5.hw.sprite.EnemyShip;

/**
 * Класс пула для снарядов.
 */
public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private TextureAtlas atlas;
    private BulletPool bulletPool;

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, bulletPool);
    }

//    @Override
//    public EnemyShip newObject(TextureAtlas atlas, BulletPool bulletPool) {
//
//        return new EnemyShip(atlas, bulletPool);
//    }

    /**
     * Метод вызова объекта игры из пула, если есть свободный объект, или создания нового,
     * если нет свободных.
     * @return - объект игры
     */
    public EnemyShip obtain(TextureAtlas atlas, BulletPool bulletPool) {
        this.atlas = atlas;
        this.bulletPool = bulletPool;

        EnemyShip object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);

        //TODO temporarily
//        System.out.println("active/free : " + activeObjects.size() + "/" + freeObjects.size());

        return object;
    }

}
