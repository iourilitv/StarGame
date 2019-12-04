package gdx.lessons.lesson3.classbook1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter {
    public static boolean isAndroid = false ;
    SpriteBatch batch;
    Background background;
    Hero hero;

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Background();
        hero = new Hero();
        for ( int i = 0 ; i < 4 ; i++) {
            AsteroidEmitter.getInstance().addAsteroid(
                    new Vector2(( float )Math.random() * 1280 , ( float ) Math.random() * 720 ),
                    new Vector2(( float )(Math.random() - 0.5 ) * 200 ,
                            ( float ) (Math.random() - 0.5 ) * 200 ), 1.0f , 100 );
        }
    }

    @Override
    public void render () {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor( 1 , 1 , 1 , 1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.render(batch);
        hero.render(batch);
        AsteroidEmitter.getInstance().render(batch);
        BulletEmitter.getInstance().render(batch);
        batch.end();
    }

    public void update ( float dt) {
        background.update(hero, dt);
        hero.update(dt);
        AsteroidEmitter.getInstance().update(dt);
        BulletEmitter.getInstance().update(dt);
        checkCollision();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    // Метод checkCollision занимается проверкой столкновений. Первый цикл за
    //столкновения игрока с астероидами, второй - пуль с астероидами. Проверка
    //осуществляется за счет сравнения окружностей (построенных вокруг объектов), если
    //две окружности пересекаются, значит столкновение есть.
    public void checkCollision () {
        for (Asteroid o : AsteroidEmitter.getInstance().asteroids) {
            if (hero.hitArea.overlaps(o.hitArea)) {
                Vector2 acc = hero.position.cpy().sub(o.position).nor();
                hero.velocity.mulAdd(acc, 20 );
                o.velocity.mulAdd(acc, - 20 );
            }
        }
        for (Bullet b : BulletEmitter.getInstance().bullets) {
            if (b.active) {
                for (Asteroid a : AsteroidEmitter.getInstance().asteroids) {
                    if (a.hitArea.contains(b.position)) {
                        a.takeDamage( 50 );
                        b.destroy();
                    }
                }
            }
        }
    }
}
