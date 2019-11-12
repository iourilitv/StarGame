package gdx.stargame.lessons.lesson3.hw.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson3.hw.math.Rect;

/**
 * Родительский класс для всех объектов игры. Все объекты - по сути, прямоугольники.
 */
public class Sprite extends Rect {
    //TODO L3hw update.Deleted
//    //принимаем объект скрина
//    protected final Rect screenBounds;

    protected float angle;//угол поворота
    protected float scale = 1f;//масштабирование (по умолчанию 100%)
    //массив картинок кадров объекта для анимации(раскадровки) взрыва(длина 1 - объект целый)
    protected TextureRegion[] regions;
    //текущий кадр массива regions
    protected int frame;

    //TODO L3hw update.Deleted
//    /**
//     Конструктор для объекта в виде одной картинки с рамками экрана
//     * @param region - картинка объекта
//     * @param screen - объект скрина
//     */
//    public Sprite(TextureRegion region, Rect screenBounds) {
//        this.screenBounds = screenBounds;
//
//        if (region == null) {
//            throw new NullPointerException("region is null");
//        }
//        regions = new TextureRegion[1];
//        regions[0] = region;
//    }
    //TODO L3hw update.Added
    /**
     Конструктор для объекта в виде одной картинки с рамками экрана
     * @param region - картинка объекта
     */
    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("region is null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    /**
     * Метод отрисовки объекта(спрайт сам себя отрисовывает)
     * @param batch - текущий батч
     */
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),/*координаты левого нижнего угла картинки*/
                halfWidth, halfHeight,/*координаты точки вращения(по центру)*/
                getWidth(), getHeight(),
                scale, scale,/*масштаб*/
                angle/*угол попорота объекта*/
        );
    }

    /**
     * Метод установки размеров объекта с учетом соотношения ширины к высоте
     * @param height - новая высота объекта
     */
    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    /**
     * Метод преобразования спрайта
     * @param delta - отрезок времени между кадрами
     */
    public void update(float delta) {

    }

    /**
     * Метод обработки события
     * @param worldBounds - новые
     */
    public void resize(Rect worldBounds) {

    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
