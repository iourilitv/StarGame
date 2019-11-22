package gdx.lessons.lesson6.classfiles.pool;

import gdx.lessons.lesson6.classfiles.base.SpritesPool;
import gdx.lessons.lesson6.classfiles.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
