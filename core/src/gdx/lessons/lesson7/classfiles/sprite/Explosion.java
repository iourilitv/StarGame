package gdx.lessons.lesson7.classfiles.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson7.classfiles.base.Sprite;

/**
 * Класс взрывов.
 */
public class Explosion extends Sprite {
    //инициируем переменные интервала и таймера анимации взрыва
    private float animateInterval = 0.01f;
    private float animateTimer;
    //принимаем объект звака взрыва
    private Sound sound;

    public Explosion(TextureAtlas atlas, Sound sound) {
        //инициируем коллекцию регионов картинки взрыва
        //нарезаем текстору анимации взрыва в атласе в классе-родителе
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.sound = sound;
    }

    @Override
    public void update(float delta) {
        //инкрементируем таймер анимации взрыва
        animateTimer += delta;
        //если таймер превисил интервал анимации
        if (animateTimer >= animateInterval) {
            //инкрементируем индекс коллекции регионов взрыва(переставляем на следующий кадр)
            frame++;
            //если дошли до конца коллекции
            if (frame == regions.length) {
                //удаляем объект взрыва
                destroy();
            }
        }
    }

    /**
     * Метод установки параметров взрыва
     * @param pos - вектор положения объекта взрыва
     * @param height - высота региона объекта взрыва
     */
    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        sound.play();
    }

    @Override
    public void destroy() {
        //сбрасываем на начальный кадр анимации
        frame = 0;
        super.destroy();
    }
}
