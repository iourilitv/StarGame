package gdx.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.base.BaseScreen;
import gdx.stargame.base.ScaledTouchUpButton;
import gdx.stargame.math.Rect;
import gdx.stargame.screen.GameScreen;

/**
 * Класс кнопки новая игра на экране игрового поля.
 */
public class ButtonNewGame extends ScaledTouchUpButton {
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.05f;
    //инициируем константу вектора начальной позиции спрайта
    private static final Vector2 pos0 = new Vector2(0, 0);

    //передаем объект скрина(экрана)
    private BaseScreen screen;

    /**
     * Конструктор
     * @param atlas - атлас текстуры
     * @param screen - объект скрина(экрана)
     */
    public ButtonNewGame(TextureAtlas atlas, BaseScreen screen) {
        //передаем в родительский класс текструру-регион картинки кнопки "NewGame"
        super(atlas.findRegion("button_new_game"), screen);
        this.screen = screen;
    }

    /**
     * Метод установки размера и позиции кнопки
     * @param worldBounds - прямоугольник игровго мира в мировых координатах
     */
    @Override
    public void resize(Rect worldBounds) {
        //устанавливаем размер кнопки по ее заданной высоте
        setHeightProportion(INITIAL_HEIGHT);
        //устанавливаем кнопку(центр кнопки) по центру игрового мира
        pos.set(pos0);
    }

    /**
     * Метод отбрабатывает событие касание или клика на кнопку "NewGame"
     */
    @Override
    public void action() {
//        //запускаем новую игру
//        game.setScreen(new GameScreen(game));//TODO delete

        //сбрасываем флаг показа кнопки
        setShowing(false);
        //вызываем метод в сткрине, чтобы начать новую игру
        screen.startNewGame();
    }

}
