package gdx.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.stargame.base.BaseScreen;
import gdx.stargame.base.ScaledTouchUpButton;
import gdx.stargame.math.Rect;
import gdx.stargame.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    public ButtonPlay(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("btPlay"), screen);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.19f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
