package gdx.stargame.lessons.lesson4.classfiles.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.stargame.lessons.lesson4.classfiles.base.Sprite;
import gdx.stargame.lessons.lesson4.classfiles.math.Rect;

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
