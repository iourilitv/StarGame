package gdx.lessons.lesson6.hw.pool;

import gdx.lessons.lesson6.hw.base.SpritesPool;
import gdx.lessons.lesson6.hw.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
