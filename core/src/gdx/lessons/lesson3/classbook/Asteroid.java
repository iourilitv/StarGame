package gdx.lessons.lesson3.classbook;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Астероиды представлены в игре в качестве «врагов» игрока. Для перехода на новый уровень
 * необходимо уничтожить все астероиды. Когда астероид уничтожается, он создает четыре астероида
 * меньшего размера, самые маленькие астероиды не распадаются на части. У астероидов есть
 * уровень здоровья, при падении которого до нуля происходит его уничтожение. Основная часть логики
 * очень схожа с работой игрового корабля, за тем лишь исключением, что астероид сам управляет
 * собой.
 */
public class Asteroid {
    static Texture texture;
    Vector2 position;
    Vector2 velocity;
    float scl;
    float angle;
    int hp;
    int hpMax;
    Circle hitArea;

    public Asteroid (Vector2 position, Vector2 velocity, float scl, int hpMax) {
        if (texture == null ) {
            texture = new Texture( "asteroid.png" );
        }
        this .position = position;
        this .velocity = velocity;
        this .scl = scl;
        this .hpMax = hpMax;
        this .hp = hpMax;
        this .angle = 0.0f ;
        this .hitArea = new Circle(position.x, position.y, 120 * scl);
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, position.x - 128 , position.y - 128 , 128 , 128 ,
                256 ,256 , scl, scl, angle,
                0 , 0 , 256 , 256 , false , false );
    }

    // При получении урона метод takeDamage вернет boolean, который показывает,
    //уничтожен ли астероид или нет
    public boolean takeDamage ( int dmg) {
        hp -= dmg;
        if (hp <= 0 ) {
            return true ;
        }
        return false ;
    }

    // Как и игрок, астероид на каждом кадре пролетает какое-то расстояние,
    //производит проверку вылета за экран, двигает за собой область поражения
    public void update ( float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < - 128 * scl) position.x = 1280 + 128 * scl;
        if (position.x > 1280 + 128 * scl) position.x = - 128 * scl;
        if (position.y < - 128 * scl) position.y = 720 + 128 * scl;
        if (position.y > 720 + 128 * scl) position.y = - 128 * scl;
        hitArea.x = position.x;
        hitArea.y = position.y;
    }
}
