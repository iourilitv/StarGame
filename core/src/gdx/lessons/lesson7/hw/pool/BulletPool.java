package gdx.lessons.lesson7.hw.pool;

import gdx.lessons.lesson7.hw.base.SpritesPool;
import gdx.lessons.lesson7.hw.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
