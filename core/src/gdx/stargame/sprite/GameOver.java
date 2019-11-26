package gdx.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.base.Sprite;

/**
 * Класс организует режим "конец игры".
 */
public class GameOver extends Sprite {
    //инициируем константу начального размера спрайта
    private static final float INITIAL_HEIGHT = 0.05f;
    //инициируем константу начального угла поворота спрайта
    private static final float INITIAL_ANGLE = 0f;
    //инициируем константу вектора начальной позиции спрайта
    private static final Vector2 pos0 = new Vector2(0, 0);
    //инициируем константу начального размера спрайта
    public static final int LAST_FRAME = 18;

    //инициируем переменные интервала и таймера для анимации попадания снаряда в корабль
    private float animateStartInterval = 10f;
    private float animateTimer;
    //объявляем объект музыки
    private Music music;

//    public GameOver(TextureRegion region) {
//        super(region);
//        pos.set(pos0);
//        setHeightProportion(INITIAL_HEIGHT);
//        setAngle(INITIAL_ANGLE);
//        //инициируем объект музыки
//        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/dead-march.mp3"));
//    }
    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        pos.set(pos0);
        setHeightProportion(INITIAL_HEIGHT);
        setAngle(INITIAL_ANGLE);
        //инициируем объект музыки
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/dead-march.mp3"));
    }

    @Override
    public void update(float delta) {
        //инкрементируем таймер анимации попадания снаряда в корабль
        animateTimer += delta;
        //если начальный период(показа статичной картинки GameOver) еще не закончился
        if(animateTimer < animateStartInterval){
            //выходим не обновляя остальное
            return;
        }
        //пока спрайта еще виден
        if (getHeight() > 0){
            //пересчитываем(уменьшаем) высоту текстуры
            setHeight(getHeight() - (animateTimer - animateStartInterval) / 10000f);
            //устанавливаем новый размер спрайта
            setHeightProportion(getHeight());
            //поворачиваем спрайт против часовой стрелки
            setAngle(angle + (animateTimer - animateStartInterval) * 3);
        //если картинка совсем пропала
        } else {
            //уничтожаем объект анимации
            destroy();
        }
    }

    /**
     * Метод обработки начала анимации
     */
    public void start(){
        //если музыка еще не играет
        if (!music.isPlaying()){
            //запускаем музыку
            music.play();
            //закольцовываем музыку
            music.setLooping(true);
        }
    }

    /**
     * Метод устанавливает начальные настройки объекта конца игры
     */
    public void startNewGame(){
        //останавливаем воспроизведение музыки конца игры
        music.stop();
        //сбрасываем флаг трупа объекта конца игры
        destroyed = false;
        //сбрасываем таймер и угол
        animateTimer = 0;
        setAngle(INITIAL_ANGLE);
        setHeightProportion(INITIAL_HEIGHT);
    }

//    /**
//     * Метод останавливает воспроизведение музыки конца игры
//     */
//    public void stopMusic(){
//        music.stop();
//    }

    public void dispose(){
        music.dispose();
    }
}
