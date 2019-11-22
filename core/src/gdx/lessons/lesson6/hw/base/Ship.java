package gdx.lessons.lesson6.hw.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson6.hw.math.Rect;
import gdx.lessons.lesson6.hw.pool.BulletPool;
import gdx.lessons.lesson6.hw.sprite.Bullet;

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

    //объявляем переменные параметров звука
    protected float soundVolume;//уровень громкости звука
    protected float soundPitch;//уровень тона звука
    protected float soundPan;//расположение и размер сдвига источника звука(-1 слева, 1 справа, 0 по центру)


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

    /**
     * Метод стрельбы со звуковым эфектом - звук воспроизводится слева или справа
     * в зависимости от расположения корабля на поле
     */
    protected void shoot() {
        //устанавливаем значение панорамы звука в зависимости от положения корабля на поле
        //если корабль находится в левой трети поля
        float pan = 0f;
        if(pos.x < worldBounds.pos.x - worldBounds.getHalfWidth() / 3){
            //воспроизводим звук в левый динамик
            pan = -1f;
            //если корабль находится в правой трети поля
        } else if(pos.x > worldBounds.pos.x + worldBounds.getHalfWidth() / 3){
            //воспроизводим звук в правый динамик
            pan = 1f;
        }
        //воспроизводим звук выстрела по заданным параметрам
        sound.play(soundVolume, soundPitch, pan);

        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }
}
