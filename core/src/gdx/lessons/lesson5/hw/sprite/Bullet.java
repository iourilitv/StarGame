package gdx.lessons.lesson5.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson5.hw.base.Sprite;
import gdx.lessons.lesson5.hw.math.Rect;

/**
 * Класс снаряда.
 */
public class Bullet extends Sprite {

    private Rect worldBounds;
    //скорость снаряда
    private final Vector2 v = new Vector2();
    //размер наносимого снарядом урона
    private int damage;
    //владелец снаряда
    private Sprite owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    /**
     * Метод установки параметров снаряда.
     * @param owner - владелец снаряда
     * @param region - картинка снаряда
     * @param pos0 - вектор стартовой позиции снаряда
     * @param v0 - вектор скорости снаряда
     * @param height - высота картинки снаряда
     * @param worldBounds - прямоугольник игрового поля в мировых координатах
     * @param damage - размер наносимого снарядом урона
     */
    public void set(
            Sprite owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        //инициируем переменные снаряда по полученным параметрам
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        this.worldBounds = worldBounds;
        this.damage = damage;
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        //обновляем вектор позиции снаряда
        //операция одновременного прибавления вектора и умножения на скаляр
        pos.mulAdd(v, delta);
        //если снаряд вышел за границы игрового поля
        if (isOutside(worldBounds)) {
            //помечаем его на удаление
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
