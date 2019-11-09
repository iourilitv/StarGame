package gdx.stargame.lessons.lesson3.hw.base;

import com.badlogic.gdx.math.Vector2;

/**
 * Интерфейс описывает мобильность.
 */
public interface Mobility{

    //Метод устанавливает вектор позиции назначения по переданному вектору касания
    Vector2 setDestination(Vector2 touch);

    //Метод рассчета временного вектора остаточного растояния до точки назначения
    Vector2 calculateRestDistance(Vector2 position, Vector2 destination);

    //Метод рассчета вектора скорости
    Vector2 calculateVelocity(Vector2 restDistance, float velocityLength);

    Vector2 updateRestDistance(Vector2 restDistance, Vector2 velocity);

    boolean isAboutDestination(Vector2 restDistance, Vector2 velocity);

}
