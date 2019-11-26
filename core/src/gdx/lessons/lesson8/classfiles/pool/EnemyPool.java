package gdx.lessons.lesson8.classfiles.pool;

import gdx.lessons.lesson8.classfiles.base.SpritesPool;
import gdx.lessons.lesson8.classfiles.math.Rect;
import gdx.lessons.lesson8.classfiles.sprite.Enemy;

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
}
