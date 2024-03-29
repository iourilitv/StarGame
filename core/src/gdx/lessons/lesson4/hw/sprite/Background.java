package gdx.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson4.hw.base.Sprite;
import gdx.lessons.lesson4.hw.math.Rect;

public class Background extends Sprite {
    //инициализируем константу количества звезд
    private static int STAR_COUNT = 256;
    //объявляем объект атласа текструры
    private TextureAtlas atlas;
    //объявляем массив звезд
    private Star[] stars;

    public Background(TextureRegion region) {
        super(region);
        //инициируем массив звезд с разными векторами скорости
        initStars(null);
    }

    public Background(TextureRegion region, Vector2 starsVelocity) {
        super(region);
        //инициируем массив звезд с общим константным вектором скорости
        initStars(starsVelocity);
    }

    private void initStars(Vector2 starsVelocity){
        setHeightProportion(1f);
        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        //инициализируем массив звезд
        stars = new Star[STAR_COUNT];
        //наполняем массив звезд объектами звезд
        for (int i = 0; i < stars.length; i++) {
            //если не задан общий вектор скорости для звед
            if(starsVelocity == null){
                //создаем звезду с произвольным вектором скорости
                stars[i] = new Star(atlas);
            //если задан общий вектор скорости для звед
            } else{
                //устанавливаем вектор скорости звезды по заданному общему вектору
                stars[i] = new Star(atlas, starsVelocity);
            }
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

}
