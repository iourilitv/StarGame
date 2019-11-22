package gdx.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson4.hw.base.ScaledTouchUpButton;
import gdx.lessons.lesson4.hw.math.Rect;
import gdx.lessons.lesson4.hw.screen.GameScreen;

/**
 * Спрайт кнопки Начать игру.
 */
public class ButtonPlay extends ScaledTouchUpButton {
    //принимаем объект игры, чтобы переключать скрины
    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void action() {
        //вызываем скрин игрового поля через объект игры
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.19f);
        //устанавливаем позицию на скрине (в левом нижнем углу)
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
