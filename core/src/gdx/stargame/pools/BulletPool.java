package gdx.stargame.pools;

import gdx.stargame.base.SpritesPool;
import gdx.stargame.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
