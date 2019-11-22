package gdx.lessons.lesson4.classfiles.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson4.classfiles.base.ScaledTouchUpButton;
import gdx.lessons.lesson4.classfiles.math.Rect;

/**
 * Спрайт кнопки Выйти из игры.
 */
public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void action() {
        //закрываем приложение
        Gdx.app.exit();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        //устанавливаем позицию на скрине (в правом нижнем углу)
        setRight(worldBounds.getRight() - 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
