package gdx.stargame.lessons.lesson6.hw.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson6.hw.math.Rect;
import gdx.stargame.lessons.lesson6.hw.pool.BulletPool;
import gdx.stargame.lessons.lesson6.hw.sprite.Bullet;

public abstract class Ship extends Sprite {

    protected final Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected Sound sound;
    protected float bulletHeight;
    protected int damage;

    protected int hp;

    protected float reloadInterval = 0f;
    protected float reloadTimer = 0f;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        pos.mulAdd(v, delta);
    }

    protected void shoot() {
        sound.play(0.3f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }
}
