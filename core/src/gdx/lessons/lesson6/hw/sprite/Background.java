package gdx.lessons.lesson6.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.lessons.lesson6.hw.base.Sprite;
import gdx.lessons.lesson6.hw.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
        setHeightProportion(1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
