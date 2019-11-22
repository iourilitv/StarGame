package gdx.lessons.lesson3.classbook;

import com.badlogic.gdx.math.Vector2;

/**
 * Класс Bullet отвечает за работу с пулями. Они могут быть активными (лететь по экрану) либо
 * неактивными (просто лежать в запасе). При выполнении метода setup() пуля активируется и вылетает
 * из указанной точки. Метод destroy() выполняется для деактивации пули (например, в случае, когда
 * она вылетела за экран).
 */
public class Bullet {
    Vector2 position;
    Vector2 velocity;
    boolean active;

    public Bullet () {
        this .position = new Vector2( 0 , 0 );
        this .velocity = new Vector2( 0 , 0 );
        this .active = false ;
    }

    public void setup ( float x, float y, float vx, float vy) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true ;
    }

    public void destroy () {
        active = false ;
    }

    public void update ( float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < - 20 || position.x > 1300 || position.y < - 20 ||
                position.y > 740 ) {
            destroy();
        }
    }
}
