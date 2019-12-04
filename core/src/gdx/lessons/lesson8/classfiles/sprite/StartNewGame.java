package gdx.lessons.lesson8.classfiles.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson8.classfiles.base.ScaledTouchUpButton;
import gdx.lessons.lesson8.classfiles.math.Rect;
import gdx.lessons.lesson8.classfiles.screen.GameScreen;

/**
 * Класс кнопки "NewGame" для организации режима игры "начать новую игру".
 */
public class StartNewGame extends ScaledTouchUpButton {
    //передаем объект скрина(экрана)
    private GameScreen screen;

    /**
     * Конструктор
     * @param atlas - атлас текстуры
     * @param screen - объект скрина(экрана)
     */
    public StartNewGame(TextureAtlas atlas, GameScreen screen) {
        //передаем в родительский класс текструру-регион картинки кнопки "NewGame"
        super(atlas.findRegion("button_new_game"));
        this.screen = screen;
    }

    /**
     * Метод установки размера и позиции кнопки
     * @param worldBounds - прямоугольник игровго мира в мировых координатах
     */
    @Override
    public void resize(Rect worldBounds) {
        //устанавливаем размер кнопки по ее заданной высоте
        setHeightProportion(0.05f);
        //уснонавливаем низ кнопки с отступом вниз от центра игрового мира
        setBottom(-0.08f);
    }

    /**
     * Метод отбрабатывает событие касание или клика на кнопку "NewGame"
     */
    @Override
    public void action() {
        //вызываем метод в сткрине, чтобы начать новую игру
        screen.startNewGame();
    }
}
