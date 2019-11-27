package gdx.stargame.pool;

import gdx.stargame.base.Ship;
import gdx.stargame.base.Sprite;
import gdx.stargame.base.SpritesPool;
import gdx.stargame.math.Rect;
import gdx.stargame.sprite.Enemy;
import gdx.stargame.sprite.SmallEnemy;

public class EnemyPool extends SpritesPool<Enemy> {
//public class EnemyPool<E extends Enemy> extends SpritesPool<Enemy> {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

//    @Override
//    protected Enemy newObject() {
//        return new Enemy(bulletPool, explosionPool, worldBounds);
//    }

    @Override
    protected Enemy newObject() {
        return new SmallEnemy(bulletPool, explosionPool, worldBounds);
    }

//    @Override
//    protected BigEnemy newObject() {
//        return new BigEnemy(bulletPool, explosionPool, worldBounds);
//    }

    //меняем настройки кораблей противника при переходе на другой уровень//FIXME
    public void changeShipsSettingsByLevel(int level){
        for (Enemy enemy: activeObjects) {
            enemy.changeShipSettingsByLevel(level);
        }
    }
}
