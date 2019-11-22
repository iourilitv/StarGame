package gdx.lessons.lesson3.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.lessons.lesson3.hw.base.Sprite;
import gdx.lessons.lesson3.hw.math.Rect;

public class Background extends Sprite {

    //TODO L3hw update.Deleted
//    public Background(TextureRegion region, Rect screenBounds) {
//        super(region, screenBounds);
//        //сразу устанавливаем размер фоновой картинки по высоте экрана
//        // (за ширину экрана картинка может выходить)
//        setHeightProportion(1f);
//    }
    //TODO L3hw update.Added
    public Background(TextureRegion region) {
        super(region);
        //TODO L3hw update.Deleted
        //сразу устанавливаем размер фоновой картинки по высоте экрана
        // (за ширину экрана картинка может выходить)
//        setHeightProportion(1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //сразу устанавливаем размер фоновой картинки по высоте экрана
        // (за ширину экрана картинка может выходить)
        setHeightProportion(1f);
        //выравниваем текстуру(картинку) по центру
        this.pos.set(worldBounds.pos);
    }
}
