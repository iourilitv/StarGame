package gdx.lessons.lesson7.hw.sprite;

//import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;

//import gdx.lessons.lesson7.hw.StarGame;
import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.base.ScaledTouchUpButton;
import gdx.lessons.lesson7.hw.math.Rect;
import gdx.lessons.lesson7.hw.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

//    private StarGame game;

//    public ButtonPlay(TextureAtlas atlas, StarGame game) {
//        super(atlas.findRegion("btPlay"));
//        this.game = game;
//    }

    public ButtonPlay(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("btPlay"), screen);
    }

    @Override
    public void action() {

        System.out.println("ButtonPlay.action");

        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.19f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
