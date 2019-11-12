package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class Background extends Sprite {
    //инициализируем константу количества звезд
    public static int STAR_COUNT = 256;
    //объявляем объект атласа текструры
    private TextureAtlas atlas;
    //объявляем массив звезд
    private Star[] stars;

    public Background(TextureRegion region) {
        super(region);
        setHeightProportion(1f);
        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        //инициализируем массив звезд
        stars = new Star[STAR_COUNT];
        //наполняем массив звезд объектами звезд
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
    }

    public Star[] getStars() {
        return stars;
    }
}
