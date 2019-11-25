package gdx.lessons.lesson7.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.base.ScaledTouchUpButton;
import gdx.lessons.lesson7.hw.math.Rect;
import gdx.lessons.lesson7.hw.screen.GameScreen;

/**
 * Класс кнопки новая игра на экране игрового поля.
 */
public class ButtonNewGame extends ScaledTouchUpButton {
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.05f;
    //инициируем константу вектора начальной позиции спрайта
    private static final Vector2 pos0 = new Vector2(0, 0);

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
