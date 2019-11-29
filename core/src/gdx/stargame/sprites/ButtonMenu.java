package gdx.stargame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gdx.stargame.base.ScaledTouchUpButton;
import gdx.stargame.math.Rect;
import gdx.stargame.screens.GameScreen;

/**
 * Класс кнопки меню на экране игрового поля.
 */
public class ButtonMenu extends ScaledTouchUpButton {
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.03f;
    //инициируем константу отступа от низа экрана низа текстуры кнопки
    private static final float BOTTOM_MARGIN = 0.01f;

    //принимаем объект скрина(экрана)
    private GameScreen screen;

    public ButtonMenu(GameScreen screen) {
        //передаем в родительский класс текструру-регион картинки кнопки "NewGame"
        super(new TextureRegion(new Texture(
                "textures/menu-but-3lines-blackBorder-whiteFill-transp-321x254.jpg")
                ),
                screen);
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
        //устанавливаем кнопку(центр кнопки) по центру по x и с отступом от низа игрового мира
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    /**
     * Метод отбрабатывает событие касание или клика на кнопку "NewGame"
     */
    @Override
    public void action() {
        //если не включен режим паузы игры
        if(screen.getState() != GameScreen.State.PAUSE){
            //вызываем метод паузы в игре
            screen.getGame().pause();
        //если игра на паузе
        } else {
            //вызываем метод снятия игры с паузы
            screen.getGame().resume();
        }
    }

}
