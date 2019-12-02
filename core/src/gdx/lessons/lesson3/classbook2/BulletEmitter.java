package gdx.lessons.lesson3.classbook2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletEmitter {
    private Texture texture;
    private Bullet[] bullets;

    public Bullet[] getBullets() {
        return bullets;
    }

    public BulletEmitter() {
        texture = new Texture("bullet.png");
        bullets = new Bullet[200];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Bullet();
        }
    }

    public void update(float dt) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                bullets[i].update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                batch.draw(texture, bullets[i].getPosition().x - 16,bullets[i].getPosition().y - 16);
            }
        }
    }
}
