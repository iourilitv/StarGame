package gdx.stargame.pool;

import gdx.stargame.base.SpritesPool;
import gdx.stargame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
