package gdx.lessons.lesson3.classbook1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * NO CODE!
 */
public class AsteroidEmitter {
    private static final AsteroidEmitter ourInstance = new AsteroidEmitter();

    Texture texture;
    Asteroid[] asteroids;

    public static AsteroidEmitter getInstance() {
        return ourInstance;
    }

    void addAsteroid(Vector2 position, Vector2 velocity, float scl, int hpMax){
        //new Asteroid(position, velocity, scl, hpMax);
    }

    public void render(SpriteBatch batch) {
        /*for (Asteroid o : asteroids) {
            if (o.active) {
                batch.draw(texture, o.position.x - 16, o.position.y - 16);
            }
        }*/
    }

    void update(float dt){
        /*for (Asteroid o : asteroids) {
            if (o.active) {
                o.update(dt);
            }
        }*/
    }
}
