package gdx.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.math.Rect;
import gdx.stargame.pool.BulletPool;
import gdx.stargame.pool.ExplosionPool;
import gdx.stargame.sprite.Bullet;
import gdx.stargame.sprite.Explosion;

/**
 * Родительский метод спрайта корабля(главного или корабля противника).
 */
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
    //объявляем переменную для значения повреждения наносимого при столкновении с кораблем
    protected int damage;
    //объявляем переменную для значения жизни корабля
    protected int hp;
    //объявляем переменную для значения константы жизни корабля
    protected int constHp;

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

    /**
     * Геттер на наносимый урон при столкновении с кораблем
     * @return - наносимый урон при столкновении с кораблем
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Геттер на значение жизни корабля.
     * @return - значение жизни корабля
     */
    public int getHp() {
        return hp;
    }

    /**
     * Сеттер на значение жизни корабля.
     * @param hp - новое значение жизни корабля
     */
    public void setHp(int hp) {
        this.hp = hp;
    }


    /**
     * Геттер на значение константы жизни корабля
     * @return - значение константы жизни корабля
     */
    public int getConstHp() {
        return constHp;
    }

    protected void shoot() {
        sound.play(0.3f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

    /**
     * Метод отрабатывает взрыв корабля при попадании в него чего-либо
     */
    private void boom() {
        //вызываем из пула объект взрыва
        Explosion explosion = explosionPool.obtain();
        //устанавливаем настройки объекта взрыва(вектор его позиции и высоту его картинки)
        explosion.set(pos, getHeight());
    }

}
