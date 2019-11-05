package gdx.stargame.lessons.lesson3.classbook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/** NOT COMPLETED CODE!
 * Класс Hero описывает космический корабль игрока, который имеет следующие свойства: координаты,
 * скорость, мощность двигателя, угол и скорость поворота, текстуру, область поражения (hit box),
 * частоту стрельбы.
 * Ниже представлен полный листинг данного класса. В конструкторе задаем начальное состояние
 * корабля. Метод render() занимается отрисовкой игрока, при этом текстура центрируется по
 * отношению к Vector2 position, и учитывается его угол поворота. При смене кадров происходит
 * обновление логики корабля с помощью метода update(). К вектору положения прибавляется вектор
 * скорости, после чего вектор скорости умножается на скаляр 0.97 для постепенного затухания
 * движения. При нажатии на экран корабль старается развернуться в сторону нажатия с включенными
 * двигателями. Угол поворота всегда находится в пределах от -pi до pi радиан. При изменении
 * координат корабля меняем и координаты области поражения. Если игра запущена на Android,
 * работаем с тачскрином, в противном случае с клавиатурой.
 */
public class Hero {

    Circle hitArea;
    Vector2 velocity;
    Vector2 position;
    private float fireCounter;
    private float currentEnginePower;
    private float rotationSpeed;
    private float maxEnginePower;
    private float lowEnginePower;
    private float fireRate;
    private float angle;
    private float ang;


    void method1(float dt){
        {

        // Если угол до точки отличается от текущего угла корабля, стараемся
        //развернуться в нужную сторону
        if (angle > ang) {
            if (angle - ang < PI) {
                angle -= rotationSpeed * dt;
            } else {
                angle += rotationSpeed * dt;
            }
        }

        if (angle < ang) {
            if (ang - angle < PI) {
                angle += rotationSpeed * dt;
            } else {
                angle -= rotationSpeed * dt;
            }
        }

        // Увеличиваем мощность двигателя
        currentEnginePower += 100 * dt;
        if (currentEnginePower > maxEnginePower) currentEnginePower = maxEnginePower;
        velocity.add(( float ) (currentEnginePower * cos(angle) * dt),
                ( float ) (currentEnginePower * sin(angle) * dt));
        }
    }

    void method2(float dt){
        // Если игра запущена на десктопе,
        if (!StarGame.isAndroid) {
            // управление реализуется на клавиатуре
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                currentEnginePower = lowEnginePower;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                currentEnginePower += 100 * dt;
                if (currentEnginePower > maxEnginePower) currentEnginePower = maxEnginePower;
                velocity.add((float) (currentEnginePower * cos(angle) * dt),
                        (float) (currentEnginePower * sin(angle) * dt));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                angle += rotationSpeed * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                angle -= rotationSpeed * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                fireCounter += dt;
                if (fireCounter > fireRate) {
                    fireCounter = 0;
                    fire();
                }
            }
        }
    }

    void method3(){

        // Угол корабля держим в пределах от -PI до PI
        if (angle < -PI) angle += 2 * PI;
        if (angle > PI) angle -= 2 * PI;
        // Если корабль улетел за экран, перебрасываем его на другую сторону
        if (position.y > 752 ) position.y = - 32 ;
        if (position.y < - 32 ) position.y = 752 ;
        if (position.x > 1312 ) position.x = - 32 ;
        if (position.x < - 32 ) position.x = 1312 ;

        // Перемещаем хитбокс за кораблем
        hitArea.x = position.x;
        hitArea.y = position.y;
    }

    // Метод, который занимается выстреливанием пули
    public void fire () {
        Bullet[] bl = BulletEmitter.getInstance().bullets;
        for (Bullet o : bl) {
            if (!o.active) {
                o.setup(position.x, position.y, 400 * ( float ) cos(angle), 400 *
                ( float ) sin(angle));
                break ;
            }
        }
    }

    public void render(SpriteBatch batch) {
    }


    public void update(float dt) {

    }
}
