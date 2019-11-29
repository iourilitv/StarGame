package gdx.stargame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.stargame.base.Sprite;
import gdx.stargame.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1.5f);
        this.pos.set(worldBounds.pos);
    }
}
