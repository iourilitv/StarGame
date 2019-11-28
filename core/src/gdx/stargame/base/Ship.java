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

    protected Vector2 v0 = new Vector2();
    //объявляем переменную для значения константы начальной скорости корабля в активном режиме
    protected final Vector2 constV0 = new Vector2();

    protected final Vector2 v = new Vector2();

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    //объявляем переменную для значения константы скорости выпускаемых кораблем снарядов
    protected Vector2 constBulletV = new Vector2();
    protected Sound sound;
    protected float bulletHeight;
    //объявляем переменную для значения повреждения наносимого при столкновении
    // с кораблем или с его снарядом
    protected int damage;
    //объявляем переменную для константы значения повреждения наносимого при столкновении с кораблем
    protected int constDamage;
    //объявляем переменную для значения жизни корабля
    protected int hp;
    //объявляем переменную для значения константы жизни корабля
    protected int constHp;

    protected float reloadInterval = 0f;
    //объявляем переменную для константы значения интервала перезагрузки снарядов корабля
    protected float constReloadInterval;

    protected float reloadTimer = 0f;

    //инициируем переменные интервала и таймера для анимации попадания снаряда в корабль
    private float animateInterval = 0.05f;
    private float animateTimer = animateInterval;

    //объявляем переменные параметров звука
    protected float soundVolume;//уровень громкости звука
    protected float soundPitch;//уровень тона звука

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        reloadTimer += delta;
        //если пора выпускать снаряд
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            //если корабль не вышел на половину за левую и правую границы игрового поля
            if(getLeft() + getHalfWidth() > worldBounds.getLeft() &&
                    getRight() - getHalfWidth() < worldBounds.getRight()){
                //корабль стреляет
                shoot();
            }
        }
        //инкрементируем таймер анимации попадания снаряда в корабль
        animateTimer += delta;
        //если таймер стал больше интервала
        if (animateTimer > animateInterval) {
            //показываем основную картинку корабля
            frame = 0;
        }
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
