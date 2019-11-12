package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.stargame.lessons.lesson4.hw.base.ScaledTouchUpButton;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

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
