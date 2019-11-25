package gdx.lessons.lesson7.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

//import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.base.ScaledTouchUpButton;
import gdx.lessons.lesson7.hw.math.Rect;
//import gdx.lessons.lesson7.hw.StarGame;
import gdx.lessons.lesson7.hw.screen.GameScreen;

/**
 * Класс кнопки новая игра на экране игрового поля.
 */
public class ButtonNewGame extends ScaledTouchUpButton {
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.05f;
    //инициируем константу вектора начальной позиции спрайта
    private static final Vector2 pos0 = new Vector2(0, 0);

//    //принимаем объект игры
//    private StarGame game;
//    //принимаем объект скрина
//    private GameScreen screen;

//    public ButtonNewGame(TextureAtlas atlas, StarGame game, GameScreen screen) {
//        super(atlas.findRegion("button_new_game"));
////        this.game = game;
//        this.game = screen.getGame();
//        this.screen = screen;
//    }

    public ButtonNewGame(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("button_new_game"), screen);
    }

    @Override
    public void action() {
        //запускаем новую игру
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(INITIAL_HEIGHT);
        pos.set(pos0);
    }

}
