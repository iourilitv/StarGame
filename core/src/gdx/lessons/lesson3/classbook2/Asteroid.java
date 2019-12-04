package gdx.lessons.lesson3.classbook2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {
    private static Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float scl;
    private float angle;
    private int hp;
    private int hpMax;
    private Circle hitArea;

    public boolean isActive() {
        return hp > 0;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Asteroid() {
        if (texture == null) {
            texture = new Texture("asteroid.png");
        }
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.scl = 1.0f;
        this.hitArea = new Circle(position.x, position.y, 120 * scl);
    }

    public void setup(float x, float y, float vx, float vy, float scl, int hpMax) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.hpMax = hpMax;
        this.hp = this.hpMax;
        this.scl = scl;
        this.hitArea.radius = 120 * scl;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 128, position.y - 128, 128, 128, 256, 256, scl, scl, angle, 0, 0, 256, 256, false, false);
    }

    // При получении урона метод takeDamage вернет boolean, который показывает, уничтожен ли астероид или нет
    public boolean takeDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            return true;
        }
        return false;
    }

    // Как и игрок, астероид на каждом кадре пролетает какое-то расстояние, производит проверку вылета за экран, двигает за собой область поражения
    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < -128 * scl) {
            position.x = 1280 + 128 * scl;
        }
        if (position.x > 1280 + 128 * scl) {
            position.x = -128 * scl;
        }
        if (position.y < -128 * scl) {
            position.y = 720 + 128 * scl;
        }
        if (position.y > 720 + 128 * scl) {
            position.y = -128 * scl;
        }
        hitArea.setPosition(position);
    }
}