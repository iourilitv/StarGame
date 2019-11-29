package gdx.stargame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.base.Sprite;
import gdx.stargame.math.Rect;
import gdx.stargame.screens.GameScreen;

/**
 * Класс спрайта сообщения "О программе".
 */
public class AboutMessage extends Sprite {
    //принимаем объект скрина(экрана)
    private GameScreen screen;
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.5f;
    //инициируем константу вектора начальной позиции спрайта
    private static final Vector2 pos0 = new Vector2(0, 0);

    public AboutMessage(GameScreen screen) {
        //передаем в родительский класс текструру-регион картинки кнопки "NewGame"
        super(new TextureRegion(new Texture(
                        "textures/about.png")
                ));
        this.screen = screen;
    }

    /**
     * Метод установки размера и позиции сообщения
     * @param worldBounds - прямоугольник игровго мира в мировых координатах
     */
    @Override
    public void resize(Rect worldBounds) {
        //устанавливаем размер кнопки по ее заданной высоте
        setHeightProportion(INITIAL_HEIGHT);
        //устанавливаем сообщение по центру игрового экрана по x и y
        pos.set(pos0);
    }
}
