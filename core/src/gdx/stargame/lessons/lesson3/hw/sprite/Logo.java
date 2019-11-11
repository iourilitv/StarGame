package gdx.stargame.lessons.lesson3.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson3.hw.base.Mobility;
import gdx.stargame.lessons.lesson3.hw.base.Sprite;
import gdx.stargame.lessons.lesson3.hw.math.Rect;

public class Logo extends Sprite implements Mobility {
    //инициируем константу длины вектора скорости
    //если 0 - мгновенный переход в точку назначения
    private final float SPEED_LENGTH = 0.02f;//1f, 5f, 2f - Слишком большие!;
    //инициируем константу высоты спрайта
    public final float SPRITE_HEIGHT = 0.2f;//0.5f;

    //объявляем вектор позиции назначения
    private Vector2 destination;
    //объявляем вектор скорости
    private Vector2 v;
    //объявляем временный вектор скорости(сколько осталось)
    private Vector2 restDistance;

    public Logo(TextureRegion region, Rect screenBounds) {
        super(region, screenBounds);
        //инициируем вектор позиции назначения
        destination = new Vector2();
        //инициируем вектор скорости
        v = new Vector2();
        //инициируем временный вектор скорости(сколько осталось)
        restDistance = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        //если скорость равна нулю и
        // если еще не достигли точки назначения
        if (v.len() != 0 && !isAboutDestination(restDistance, v)){
            //перемещаем объект картинки на один шаг
            pos.add(v);
            //обновляем остаток пути до точки назначения
            restDistance = updateRestDistance(restDistance, v);
        } else{
            //устанавливаем текущую позицию на точку назначения
            pos.x = destination.x;
            pos.y = destination.y;
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        //устанавливаем вектор позиции назначения по переданному вектору касания
        destination = setDestination(touch);

        //корректируем вектор точки назначения, чтобы спрайт не выходил за границы скрина
        fitDestinationToScreenBounds(destination, screenBounds);

        //рассчитываем временного вектора остаточного растояния до точки назначения
        restDistance = calculateRestDistance(pos, destination);
        //расчитываем вектора скорости для перемещения
        v = calculateVelocity(restDistance, SPEED_LENGTH);
        return false;
    }

    /**
     * Метод корректирует вектор точки назначения, чтобы спрайт не выходил за границы скрина
     * @param destination - вектор точки назначения
     * @param screenBounds - прямоугольник скрина
     */
    private void fitDestinationToScreenBounds(Vector2 destination, Rect screenBounds) {
        if(destination.x - halfWidth < screenBounds.getLeft()){
            destination.x = screenBounds.getLeft() + halfWidth;
        }
        if(destination.x + halfWidth > screenBounds.getRight()){
            destination.x = screenBounds.getRight() - halfWidth;
        }
        if(destination.y + halfHeight > screenBounds.getTop()){
            destination.y = screenBounds.getTop() - halfHeight;
        }
        if(destination.y - halfHeight < screenBounds.getBottom()){
            destination.y = screenBounds.getBottom() + halfHeight;
        }
    }

    @Override
    public Vector2 setDestination(Vector2 touch) {
        //возвращаем копию заданного вектора точки касания(клика)
        return touch.cpy();
    }

    @Override
    public Vector2 calculateRestDistance(Vector2 position, Vector2 destination) {
        //возвращаем вектор разницы позиции назначения и текущей
        return destination.cpy().sub(position);
    }

    @Override
    public Vector2 calculateVelocity(Vector2 restDistance, float velocityLength) {
        //копируем временный вектор в вектор текущей скорости и
        // устанавливаем его длину в соответствии с констанстой сторости
        return restDistance.cpy().setLength(velocityLength);
    }

    @Override
    public Vector2 updateRestDistance(Vector2 restDistance, Vector2 velocity) {
        //уменьшаем вектор оставшейся дистанции на вектор скорости
        return restDistance.sub(velocity);
    }

    @Override
    public boolean isAboutDestination(Vector2 restDistance, Vector2 velocity) {
        //если длина вектора остатка скорости меньше или равна длине вектора скорости
        return restDistance.len() <= velocity.len();
    }


}
