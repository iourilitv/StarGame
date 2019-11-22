package gdx.lessons.lesson4.classfiles.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Родительский класс для кнопок. Класс абстрактный,
 * поэтому конструкторы наследников могут отличаться!
 */
public abstract class ScaledTouchUpButton extends Sprite {

    //принимаем объект пальца
    private int pointer;
    //объявляем переменную маркера нажатия кнопки
    private boolean pressed;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //если кнопка уже нажата или точка касания не попала на кнопку
        if (pressed || !isMe(touch)) {
            return false;
        }
        //устанавливаем маркер нажатия кнопки
        pressed = true;
        //уменьшаем размер кнопки
        scale = 0.9f;
        //устанавливаем(запоминаем) оюъект пальца
        this.pointer = pointer;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        //если коснулся другой палец или кнопка не нажата
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        //если есть попадание на кнопку
        if (isMe(touch)) {
            //выполняем действие
            action();
        }
        //сбрасываем маркер нажатия
        pressed = false;
        //восстанавливаем размер кнопки
        scale = 1;
        return false;
    }

    public abstract void action();
}
