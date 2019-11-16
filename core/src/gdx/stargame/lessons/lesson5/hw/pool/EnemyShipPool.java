package gdx.stargame.lessons.lesson5.hw.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.stargame.lessons.lesson5.hw.base.SpritesPool;
import gdx.stargame.lessons.lesson5.hw.sprite.Bullet;
import gdx.stargame.lessons.lesson5.hw.sprite.EnemyShip;

/**
 * Класс пула для снарядов.
 */
public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private TextureAtlas atlas;
    private BulletPool bulletPool;


//    @Override
    public EnemyShip newObject(TextureAtlas atlas, BulletPool bulletPool) {
        return new EnemyShip(atlas, bulletPool);
    }

    @Override
    protected EnemyShip newObject() {
        return null;
    }
}
