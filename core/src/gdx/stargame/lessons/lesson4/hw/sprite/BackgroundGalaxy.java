package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.constants.ScreenSettings;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class BackgroundGalaxy extends Sprite {
    //инициализируем константу количества звезд
    //private static int STAR_COUNT = 256;

    //инициируем константу высоты спрайта
    private final float SPRITE_HEIGHT = 1f;//1.2f;

    //объявляем объект фрагмента в виде полной текстуры
    private TextureRegion galaxy;
    //объявляем массив плиток текстуры
    private TextureRegion[][] tiles;

    public BackgroundGalaxy(TextureRegion region) {
        super(region);
        this.galaxy = region;
        //инициируем массив звезд с разными векторами скорости
//        init(null);
    }

    public BackgroundGalaxy(TextureRegion region, Vector2 shiftVelocity) {
        super(region);
        this.galaxy = region;
        //инициируем массив звезд с общим константным вектором скорости
//        init(shiftVelocity);
    }

//    private void init(Vector2 shiftVelocity){
//        setHeightProportion(SPRITE_HEIGHT);
//
//
//    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

//        setHeightProportion(SPRITE_HEIGHT);
        //устанавливаем новые размеры фона в зависимости от пропорций экрана
        setHeightProportion(SPRITE_HEIGHT *
                ScreenSettings.SCREEN_PROPORTION.getScreenProportion());

        this.pos.set(worldBounds.pos);

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

    }

    @Override
    public void update(float delta) {
        super.update(delta);

    }

}
