package gdx.lessons.lesson7.classfiles.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson7.classfiles.math.Rect;
import gdx.lessons.lesson7.classfiles.pool.BulletPool;
import gdx.lessons.lesson7.classfiles.pool.ExplosionPool;
import gdx.lessons.lesson7.classfiles.sprite.Bullet;
import gdx.lessons.lesson7.classfiles.sprite.Explosion;

public abstract class Ship extends Sprite {

    protected final Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected Sound sound;
    protected float bulletHeight;
    protected int damage;

    protected int hp;

    protected float reloadInterval = 0f;
    protected float reloadTimer = 0f;

    //инициируем переменные интервала и таймера для анимации попадания снаряда в корабль
    protected float animateInterval = 0.05f;
    protected float animateTimer = animateInterval;

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
        //инкрементируем таймер анимации попадания снаряда в корабль
        animateTimer += delta;
        //если таймер стал больше интервала
        if (animateTimer > animateInterval) {
            //показываем основную картинку корабля
            frame = 0;
        }
        pos.mulAdd(v, delta);
    }

    @Override
    public void destroy() {
        //вызываем метод взрыва
        boom();
        super.destroy();
    }

    /**
     * Метод расчета повреждения корабля при попадании снаряда или столкновения с чужим объектом
     * @param damage - размер нанесенного кораблю ущерба
     */
    public void damage(int damage) {
        //уменьшаем значение здоровья корабля на размер нанесенного ущерба
        hp -= damage;
        //если здоровье кончилось
        if (hp <= 0) {
            //вызываем метод уничтожения корабля
            destroy();
        }
        //сбрасываем таймер анимации взрыва
        animateTimer = 0f;
        //показываем светлую картинку корабля
        frame = 1;
    }

    public int getDamage() {
        return damage;
    }

    protected void shoot() {
        sound.play(0.3f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

    /**
     * Метод отрабатывает взрыв корабля при попадании в него чего-либо
     */
    protected void boom() {
        //вызываем из пула объект взрыва
        Explosion explosion = explosionPool.obtain();
        //устанавливаем настройки объекта взрыва(вектор его позиции и высоту его картинки)
        explosion.set(pos, getHeight());
    }
}
