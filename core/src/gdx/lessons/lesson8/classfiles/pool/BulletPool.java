package gdx.lessons.lesson8.classfiles.pool;

import gdx.lessons.lesson8.classfiles.base.SpritesPool;
import gdx.lessons.lesson8.classfiles.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
