package gdx.lessons.lesson5.classfiles.pool;

import gdx.lessons.lesson5.classfiles.base.SpritesPool;
import gdx.lessons.lesson5.classfiles.sprite.Bullet;

/**
 * Класс пула для снарядов.
 */
public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
