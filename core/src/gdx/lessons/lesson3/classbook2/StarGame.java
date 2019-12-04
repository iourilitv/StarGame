package gdx.lessons.lesson3.classbook2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Background background;
    private Hero hero;
    private BulletEmitter bulletEmitter;
    private AsteroidEmitter asteroidEmitter;

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Background();
        asteroidEmitter = new AsteroidEmitter();
        bulletEmitter = new BulletEmitter();
        hero = new Hero(this);
        for (int i = 0; i < 4; i++) {
            asteroidEmitter.addAsteroid((float) Math.random() * 1280, (float) Math.random() * 720, (float) (Math.random() - 0.5) * 200, (float) (Math.random() - 0.5) * 200, 1.0f, 10);
        }
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.render(batch);
        hero.render(batch);
        asteroidEmitter.render(batch);
        bulletEmitter.render(batch);
        batch.end();
    }

    public void update(float dt) {
        background.update(hero, dt);
        hero.update(dt);
        asteroidEmitter.update(dt);
        bulletEmitter.update(dt);
        checkCollision();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // Метод checkCollision занимается проверкой столкновений. Первый цикл за столкновения игрока с астероидами, второй - пуль с астероидами. Проверка осуществляется за счет сравнения окружностей (построенных вокруг объектов), если две окружности пересекаются, значит столкновение есть.
    public void checkCollision() {
		for (int i = 0; i < asteroidEmitter.getAsteroids().length; i++) {
			Asteroid a = asteroidEmitter.getAsteroids()[i];
			if (a.isActive() && hero.getHitArea().overlaps(a.getHitArea())) {
				Vector2 acc = hero.getPosition().cpy().sub(a.getPosition()).nor();
				hero.getVelocity().mulAdd(acc, 20);
				a.getVelocity().mulAdd(acc, -20);
			}
		}

		for (int i = 0; i < bulletEmitter.getBullets().length; i++) {
			Bullet b = bulletEmitter.getBullets()[i];
			if (b.isActive()) {
				for (int j = 0; j < asteroidEmitter.getAsteroids().length; j++) {
					Asteroid a = asteroidEmitter.getAsteroids()[j];
					if(a.isActive() && a.getHitArea().contains(b.getPosition())) {
						a.takeDamage(1);
						b.destroy();
					}
				}
			}
		}
    }
}
