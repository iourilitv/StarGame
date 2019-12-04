package gdx.lessons.lesson3.classbook1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Background представляет собой класс, отвечающий за отображение заднего фона. Он содержит в
 * себе внутренний класс Star для описания звезд. При выполнении методов render() и update()
 * происходит просчет состояния заднего фона.
 */
public class Background {
    class Star {
        Vector2 position;
        Vector2 velocity;
        float scl;

        public Star () {
            position = new Vector2(( float ) Math.random() * 1280 , ( float )
                    Math.random() * 720 );
            velocity = new Vector2(( float ) (Math.random() - 0.5 ) * 5f , ( float )
                    (Math.random() - 0.5 ) * 5f );
            scl = 0.5f + ( float ) Math.random() / 4.0f ;
        }

        public void update (Hero hero, float dt) {
            position.mulAdd(velocity, dt);
            position.mulAdd(hero.velocity, - 0.001f );
            float half = textureStar.getWidth() * scl;
            if (position.x < -half) position.x = 1280 + half;
            if (position.x > 1280 + half) position.x = -half;
            if (position.y < -half) position.y = 720 + half;
            if (position.y > 720 + half) position.y = -half;
        }
    }

    Texture texture;
    Texture textureStar;
    Star[] stars;

    public Background () {
        texture = new Texture( "bg.png" );
        textureStar = new Texture( "star16.png" );
        stars = new Star[ 250 ];
        for ( int i = 0 ; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, 0 , 0 );
        for (Star s : stars) {
            batch.draw(textureStar, s.position.x - 8 , s.position.y - 8 ,
                    8 , 8 , 16 ,16 , s.scl, s.scl,0 ,
                    0 , 0 , 16 , 16 , false , false );
        }
    }

    public void update (Hero hero, float dt) {
        for (Star s : stars) {
            s.update(hero, dt);
        }
    }

    public void dispose () {
        texture.dispose();
        textureStar.dispose();
    }
}
