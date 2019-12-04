package gdx.lessons.lesson3.classbook2;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private StarGame game;

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;

    private int hp;
    private int hpMax;

    private float lowEnginePower;
    private float currentEnginePower;
    private float maxEnginePower;

    private float rotationSpeed;

    private float fireRate;
    private float fireCounter;

    private Circle hitArea;

    public Circle getHitArea() {
        return hitArea;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Hero(StarGame game) {
        this.game = game;
        this.texture = new Texture("ship.png");
        this.position = new Vector2(640, 360);
        this.velocity = new Vector2(0, 0);
        this.maxEnginePower = 400.0f;
        this.lowEnginePower = 200.0f;
        this.rotationSpeed = 3.14f;
        this.hpMax = 100;
        this.hp = hpMax;
        this.hitArea = new Circle(position.x, position.y, 25);
        this.fireCounter = 0;
        this.fireRate = 0.25f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, 1, 1, (float) Math.toDegrees(angle), 0, 0, 64, 64, false, false);
    }

    public void update(float dt) {
        // На каждом кадре к координатам добавляем скорость и потихоньку гасим скорость
        position.mulAdd(velocity, dt);
        velocity.scl(0.97f);

        // Если игра работает на Android устройстве
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            // При прикосновении к экрану включаем двигатель
            if (InputHandler.isJustTouched()) {
                currentEnginePower = lowEnginePower;
            }
            // При дальнейшем нажатии
            if (InputHandler.isTouched()) {
                // Определяем координаты нажатия
                float tx = InputHandler.getX();
                float ty = InputHandler.getY();
                // Рассчитываем угол между кораблем и точкой нажатия
                float ang = (float) Math.atan2(ty - position.y, tx - position.x);
                // Если угол до точки отличается от текущего угла корабля, стараемся развернуться в нужную сторону
                if (angle > ang) {
                    if (angle - ang < Math.PI) {
                        angle -= rotationSpeed * dt;
                    } else {
                        angle += rotationSpeed * dt;
                    }
                }
                if (angle < ang) {
                    if (ang - angle < Math.PI) {
                        angle += rotationSpeed * dt;
                    } else {
                        angle -= rotationSpeed * dt;
                    }
                }
                // Потихоньку увеличиваем мощность двигателя
                currentEnginePower += 100 * dt;
                if (currentEnginePower > maxEnginePower) currentEnginePower = maxEnginePower;
                velocity.add((float) (currentEnginePower * Math.cos(angle) * dt), (float) (currentEnginePower * Math.sin(angle) * dt));
            }
        }
        // Если игра запущена на десктопе
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            // Все управление реализуется на клавиатуре
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                currentEnginePower = lowEnginePower;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                currentEnginePower += 100 * dt;
                if (currentEnginePower > maxEnginePower) currentEnginePower = maxEnginePower;
                velocity.add((float) (currentEnginePower * Math.cos(angle) * dt), (float) (currentEnginePower * Math.sin(angle) * dt));
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
        // Угол корабля держим в пределах от -PI до PI
        if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        // Если корабль улетел за экран, перебрасываем его на другую сторону
        if (position.y > 752) {
            position.y = -32;
        }
        if (position.y < -32) {
            position.y = 752;
        }
        if (position.x > 1312) {
            position.x = -32;
        }
        if (position.x < -32) {
            position.x = 1312;
        }
        // Перемещаем хитбокс за кораблем
        hitArea.setPosition(position);
    }

    // Метод, который занимается выстреливанием пули
    public void fire() {
        for (int i = 0; i < game.getBulletEmitter().getBullets().length; i++) {
            Bullet b = game.getBulletEmitter().getBullets()[i];
            if (!b.isActive()) {
                b.setup(position.x, position.y, 400 * (float) Math.cos(angle), 400 * (float) Math.sin(angle));
                break;
            }
        }
    }
}
