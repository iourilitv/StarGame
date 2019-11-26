package gdx.stargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.StarGame;

public abstract class ScaledTouchUpButton extends Sprite {

    //принимаем объект игры
    protected StarGame game;
    //принимаем объект скрина
    protected BaseScreen screen;

    private int pointer;
    private boolean pressed;

    //объявляем переменую флага показа кнопки на экране
    private boolean isShowing;

    public ScaledTouchUpButton(TextureRegion region, BaseScreen screen) {
        super(region);
        this.game = screen.getGame();
        this.screen = screen;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) {
            return false;
        }
        pressed = true;
        scale = 0.9f;
        this.pointer = pointer;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {

//        System.out.println("ScaledTouchUpButton.touchUp this.getClass().getSimpleName()= " +
//                this.getClass().getSimpleName() +
//                ". touch= " + touch);

        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 1;
        return false;
    }

    public abstract void action();

    //геттер на переменую флага показа кнопки на экране
    public boolean isShowing() {
        return isShowing;
    }

    //сеттер на переменую флага показа кнопки на экране
    public void setShowing(boolean showing) {
        isShowing = showing;
    }
}
