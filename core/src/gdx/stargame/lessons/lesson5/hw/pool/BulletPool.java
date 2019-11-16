package gdx.stargame.lessons.lesson5.hw.pool;

import gdx.stargame.lessons.lesson5.hw.base.SpritesPool;
import gdx.stargame.lessons.lesson5.hw.sprite.Bullet;

/**
 * Класс пула для снарядов.
 */
public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
