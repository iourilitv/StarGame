package gdx.stargame.lessons.lesson3.classfiles.math;

import com.badlogic.gdx.math.Vector2;

/**
 * Прямоугольник. В GDX есть свой прямоугольник, но он не подходит.
 */
public class Rect {

    public final Vector2 pos = new Vector2(); // позиция по центру
    protected float halfWidth; // половина ширины
    protected float halfHeight; // половина высоты

    public Rect() {

    }

    /**
     * Конструктор позволяющий создавать копию переданного прямоугольника,
     * используя перегруженный конструктор
     * @param from - прямоугольник-образец
     */
    public Rect(Rect from) {
        this(from.pos.x, from.pos.y, from.getHalfWidth(), from.getHalfHeight());
    }

    /**
     * Конструктор принимащий координаты центра и половинный размер прямоугольника
     * @param x - координата x центра прямоугольника
     * @param y - координата y центра прямоугольника
     * @param halfWidth - половина ширины прямоугольника
     * @param halfHeight - половина высоты прямоугольника
     */
    public Rect(float x, float y, float halfWidth, float halfHeight) {
        pos.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    /**
     * Метод возвращает координату x для левого ребра прямоугольника
     * @return координату x для левого края прямоугольника
     */
    public float getLeft() {
        return pos.x - halfWidth;
    }

    /**
     * Метод возвращает координату y для верхнего ребра прямоугольника
     * @return координату y для верхнего ребра прямоугольника
     */
    public float getTop() {
        return pos.y + halfHeight;
    }

    /**
     * Метод возвращает координату x для правого ребра прямоугольника
     * @return координату x для правого ребра прямоугольника
     */
    public float getRight() {
        return pos.x + halfWidth;
    }

    /**
     * Метод возвращает координату y для нижнего ребра прямоугольника
     * @return координату y для нижнего ребра прямоугольника
     */
    public float getBottom() {
        return pos.y - halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    /**
     * Метод устанавливает координаты и размеры прямоугольника по заданному прямоугольнику-образцу
     * @param from - прямоугольник-образец
     */
    public void set(Rect from) {
        pos.set(from.pos);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setLeft(float left) {
        pos.x = left + halfWidth;
    }

    public void setTop(float top) {
        pos.y = top - halfHeight;
    }

    public void setRight(float right) {
        pos.x = right - halfWidth;
    }

    public void setBottom(float bottom) {
        pos.y = bottom + halfHeight;
    }

    public void setWidth(float width) {
        this.halfWidth = width / 2f;
    }

    public void setHeight(float height) {
        this.halfHeight = height / 2f;
    }

    /**
     * Метод устанавливает новые размеры прямоугольника
     * @param width новая ширина
     * @param height новая высота
     */
    public void setSize(float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    /**
     * Метод проверяет попал ли вектор в границы нашего прямоугольника
     * @param touch вектор позиции события
     * @return true - есть попадание - координаты вектора находятся в границах прямоугольника
     */
    public boolean isMe(Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight() &&
                touch.y >= getBottom() && touch.y <= getTop();
    }

    /**
     * Метод проверяет пересекаются ли прямоугольники
     * @param other прямоугольник, который нужно сравнить с хозяином
     * @return true - нет попадания - прямоугольники НЕ пересекаются
     */
    public boolean isOutside(Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft() ||
                getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + pos + " size(" + getWidth() + ", " + getHeight() + ")";
    }
}