package gdx.lessons.lesson7.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson7.hw.base.Sprite;

public class GameOver extends Sprite {
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.05f;
    //инициируем константу начального угла поворота спрайта
    private static final float INITIAL_ANGLE = 0f;
    //инициируем константу вектора начальной позиции спрайта
    private static final Vector2 pos0 = new Vector2(0, 0);
    //инициируем переменные интервала и таймера для анимации попадания снаряда в корабль
    private float animateGameOverInterval = 0.05f;
    private float animateTimer;

    public GameOver(TextureRegion region) {
        super(region);
        pos.set(pos0);
        setHeightProportion(INITIAL_HEIGHT);
        setAngle(INITIAL_ANGLE);

        System.out.println("GameOver.constructor new height=" + getHeight() +
                ", new angle=" + angle);
    }

    @Override
    public void update(float delta) {
        //пока спрайта еще виден
        if (getHeight() > 0){
            //инкрементируем таймер анимации попадания снаряда в корабль
            animateTimer += delta;

            System.out.println("GameOver.update new animateTimer=" + animateTimer);

            setHeight(getHeight() - animateTimer / 10000f);
            //уменьшаем размер спрайта
            setHeightProportion(getHeight());
            //поворачиваем спрайт против часовой стрелки
            setAngle(angle + animateTimer * 3);

            System.out.println("GameOver.update new height=" + getHeight() +
                    ", new angle=" + angle);
        } else {
            destroy();
        }

    }
}
