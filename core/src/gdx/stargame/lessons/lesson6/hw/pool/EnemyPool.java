package gdx.stargame.lessons.lesson6.hw.pool;

import gdx.stargame.lessons.lesson6.hw.base.SpritesPool;
import gdx.stargame.lessons.lesson6.hw.math.Rect;
import gdx.stargame.lessons.lesson6.hw.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private Rect worldBounds;
    private BulletPool bulletPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, worldBounds);
    }
}
