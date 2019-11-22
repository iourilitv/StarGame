package gdx.lessons.lesson3.classfiles.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.lessons.lesson3.classfiles.base.Sprite;
import gdx.lessons.lesson3.classfiles.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
        //сразу устанавливаем размер фоновой картинки по высоте экрана
        // (за ширину экрана картинка может выходить)
        setHeightProportion(1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        //выравниваем текстуру(картинку) по центру
        this.pos.set(worldBounds.pos);
    }
}
