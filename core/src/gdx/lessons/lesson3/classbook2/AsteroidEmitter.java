package gdx.lessons.lesson3.classbook2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AsteroidEmitter {
    private Asteroid[] asteroids;

    public Asteroid[] getAsteroids() {
        return asteroids;
    }

    public AsteroidEmitter() {
        asteroids = new Asteroid[100];
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid();
        }
    }

    public void addAsteroid(float x, float y, float vx, float vy, float scl, int hpMax) {
        for (int i = 0; i < asteroids.length; i++) {
            if(!asteroids[i].isActive()) {
                asteroids[i].setup(x, y, vx, vy, scl, hpMax);
                break;
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i].isActive()) {
                asteroids[i].update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i].isActive()) {
                asteroids[i].render(batch);
            }
        }
    }
}
