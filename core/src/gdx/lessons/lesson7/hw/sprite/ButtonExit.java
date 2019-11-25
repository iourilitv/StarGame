package gdx.lessons.lesson7.hw.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.base.ScaledTouchUpButton;
import gdx.lessons.lesson7.hw.math.Rect;
//import gdx.lessons.lesson7.hw.screen.GameScreen;

public class ButtonExit extends ScaledTouchUpButton {

//    public ButtonExit(TextureAtlas atlas) {
//        super(atlas.findRegion("btExit"));
//    }

    public ButtonExit(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("btExit"), screen);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setRight(worldBounds.getRight() - 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
