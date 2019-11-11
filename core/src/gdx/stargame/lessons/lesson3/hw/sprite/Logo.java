package gdx.stargame.lessons.lesson3.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson3.hw.base.Mobility;
import gdx.stargame.lessons.lesson3.hw.base.Sprite;

public class Logo extends Sprite implements Mobility {
    //инициируем константу длины вектора скорости
    //если 0 - мгновенный переход в точку назначения
    private final float SPEED_LENGTH = 0.02f;//1f, 5f, 2f - Слишком большие!;

    //объявляем вектор позиции назначения
    private Vector2 destination;
    //объявляем вектор скорости
    private Vector2 v;
    //объявляем временный вектор скорости(сколько осталось)
    private Vector2 restDistance;

    public Logo(TextureRegion region) {
        super(region);
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

//            System.out.println("Logo.update. buffer.x= " + restDistance.x + ", " + "buffer.y= " + restDistance.y +
//                    ", buffer.len()= " + restDistance.len());

        } else{
            //устанавливаем текущую позицию на точку назначения
            pos.x = destination.x;
            pos.y = destination.y;

//            System.out.println("Logo.update. destination.x= " + destination.x + ", " + "destination.y= " + destination.y);
//            System.out.println("Logo.update. pos.x= " + pos.x + ", " + "pos.y= " + pos.y);
        }

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        //устанавливаем вектор позиции назначения по переданному вектору касания
        destination = setDestination(touch);
        //рассчитываем временного вектора остаточного растояния до точки назначения
        restDistance = calculateRestDistance(pos, destination);
        //расчитываем вектора скорости для перемещения
        v = calculateVelocity(restDistance, SPEED_LENGTH);
        return false;
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
