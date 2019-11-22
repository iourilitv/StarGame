package gdx.lessons.lesson7.hw.pool;

import gdx.lessons.lesson7.hw.base.SpritesPool;
import gdx.lessons.lesson7.hw.math.Rect;
import gdx.lessons.lesson7.hw.sprite.Enemy;

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
