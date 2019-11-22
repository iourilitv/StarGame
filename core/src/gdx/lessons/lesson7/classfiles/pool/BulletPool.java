package gdx.lessons.lesson7.classfiles.pool;

import gdx.lessons.lesson7.classfiles.base.SpritesPool;
import gdx.lessons.lesson7.classfiles.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
