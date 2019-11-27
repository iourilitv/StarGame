package gdx.stargame.pool;

import gdx.stargame.base.SpritesPool;
import gdx.stargame.math.Rect;
import gdx.stargame.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, worldBounds);
    }

    //меняем настройки кораблей противника при переходе на другой уровень//FIXME
    public void changeShipsSettingsByLevel(int level){
        for (Enemy enemy: activeObjects) {
            enemy.changeShipSettingsByLevel(level);
        }
    }
}
