package gdx.lessons.lesson8.classfiles.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson8.classfiles.base.ScaledTouchUpButton;
import gdx.lessons.lesson8.classfiles.math.Rect;
import gdx.lessons.lesson8.classfiles.screen.GameScreen;

public class StartNewGame extends ScaledTouchUpButton {

    private GameScreen screen;

    public StartNewGame(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("button_new_game"));
        this.screen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setBottom(-0.08f);
    }

    @Override
    public void action() {
        screen.startNewGame();
    }
}
