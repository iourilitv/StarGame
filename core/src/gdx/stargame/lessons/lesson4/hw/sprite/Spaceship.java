package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.constants.Direction;
import gdx.stargame.lessons.lesson4.hw.constants.ScreenSettings;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class Spaceship extends Sprite {
    //инициируем константу длины вектора скорости
    //если 0 - мгновенный переход в точку назначения
    private final float VELOCITY_LENGTH = 0.02f;
    //инициируем константу высоты спрайта
    private final float SPRITE_HEIGHT = 0.2f;
    //инициируем константу вектора пропорций начальной позиции относительно центра мира
    private final Vector2 START_POSITION = new Vector2(0, -0.66f);

    //инициируем константу количества состояний спрайта(сколько его картинок в атласе)
    private static final int IMAGES_NUMBER = 2;
    //инициируем константу индекса стартового элемента массива образов состояний корабля
    private static final int START_FRAME_INDEX = 0;

    //объявляем вектор позиции назначения
    private Vector2 destination;
    //объявляем временный вектор скорости(сколько осталось)
    private Vector2 restDistance;
    //объявляем вектор скорости
    private Vector2 v;

    //принимаем границы экрана
    private Rect screenBounds = new Rect();
    //объявим переменную для хранения пропорции экрана для подгонки спрайтов под разные экраны
    private float screenProportion;

    //объявляем переменную вида управления движением клавишами
    private boolean keyControl;

    public Spaceship(TextureAtlas atlas) {
        super(new TextureRegion[IMAGES_NUMBER]);
        //сохраняем фрагмент с образами корабля из атласа во временную переменную
        TextureRegion shipImages = new TextureRegion(atlas.findRegion("main_ship"));
        //нарезаем фрагмент атласа корабля на части по количеству образов его состояний
        int widthStep = shipImages.getRegionWidth() / IMAGES_NUMBER;
        for (int i = 0; i < regions.length; i++) {
            regions[i] = new TextureRegion(shipImages, i * widthStep, 0,
                    widthStep, shipImages.getRegionHeight());
        }
        //устанавливаем индекс образа в массиве образов корабля
        frame = START_FRAME_INDEX;
        //инициируем вектор позиции назначения
        destination = new Vector2();
        //инициируем вектор скорости
        v = new Vector2();
        //инициируем временный вектор скорости(сколько осталось)
        restDistance = new Vector2();
    }

    public void update(float delta) {
        super.update(delta);

        //если управление от клавиатуры
        if(keyControl){
            //корректируем вектор точки назначения, чтобы спрайт не выходил за границы скрина
            fitDestinationToScreenBounds(pos.add(v), screenBounds);
            return;
        }

        //если скорость равна нулю и
        // если еще не достигли точки назначения
        if (v.len() != 0 && !isAboutDestination(restDistance, v)){
            //перемещаем объект картинки на один шаг
            pos.add(v);
            //обновляем остаток пути до точки назначения
            restDistance.sub(v);
        } else{
            //устанавливаем текущую позицию на точку назначения
            pos.set(destination);
        }
    }

    public boolean keyDown(int keycode) {
        System.out.println("Spaceship.keyDown. keycode=" + keycode);

        //передаем управление от мыши к клавиатуре
        keyControl = true;
        //устанавливаем значения вектора скорости в зависимости от нажатой клавиши
        //ВНИМАНИЕ! switch/case использовать нельзя, т.к. case требует константу!
        if(keycode == Direction.FORWARD.keyCode()){
            v.set(0f, VELOCITY_LENGTH);
        } else if(keycode == Direction.BACK.keyCode()){
            v.set(0f, -VELOCITY_LENGTH);
        } else if(keycode == Direction.LEFT.keyCode()){
            v.set(-VELOCITY_LENGTH, 0f);
        } else if(keycode == Direction.RIGHT.keyCode()){
            v.set(VELOCITY_LENGTH, 0f);
        } else {
            System.out.println("Not supported key for moving direction: " + keycode);
            //НЕ передаем управление от мыши к клавиатуре
            keyControl = false;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        System.out.println("Spaceship.keyUp. keycode=" + keycode);

        //если клавиша отпущена, обнуляем вектор скорости
        v.scl(0);
        //возвращаем управление мыши
        keyControl = false;
        //устанавливаем позиции назначения значения текущей позиции, чтобы не было скачков
        destination.set(pos);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        //устанавливаем вектор позиции назначения по переданному вектору касания
        destination.set(touch);
        //корректируем вектор точки назначения, чтобы спрайт не выходил за границы скрина
        fitDestinationToScreenBounds(destination, screenBounds);
        //рассчитываем временного вектора остаточного растояния до точки назначения
        restDistance.set(destination).sub(pos);
        //расчитываем вектора скорости для перемещения
        v.set(restDistance).setLength(VELOCITY_LENGTH);
        return false;
    }

    /**
     * Метод корректирует вектор точки назначения, чтобы спрайт не выходил за границы скрина
     * @param destination - вектор точки назначения
     * @param screenBounds - прямоугольник скрина
     */
    private void fitDestinationToScreenBounds(Vector2 destination, Rect screenBounds) {
        if(destination.x - halfWidth < screenBounds.getLeft()){
            destination.x = screenBounds.getLeft() + halfWidth;
        }
        if(destination.x + halfWidth > screenBounds.getRight()){
            destination.x = screenBounds.getRight() - halfWidth;
        }
        if(destination.y + halfHeight > screenBounds.getTop()){
            destination.y = screenBounds.getTop() - halfHeight;
        }
        if(destination.y - halfHeight < screenBounds.getBottom()){
            destination.y = screenBounds.getBottom() + halfHeight;
        }
    }

    private boolean isAboutDestination(Vector2 restDistance, Vector2 velocity) {
        //если длина вектора остатка скорости меньше или равна длине вектора скорости
        return restDistance.len() <= velocity.len();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        //устанавливаем границы скрина
        screenBounds.set(worldBounds);

//        //инициализируем переменную для хранения пропорции экрана для подгонки спрайтов
//        // под экраны с разными пропорциями
//        screenProportion = screenBounds.getHeight() / screenBounds.getWidth();
//
//        //System.out.println("Spaceship.resize() screenProportion= " + screenProportion);
//
//        //устанавливаем лого новые размеры
//        setHeightProportion(SPRITE_HEIGHT / screenProportion);

        //устанавливаем новые размеры фона в зависимости от пропорций экрана
        setHeightProportion(SPRITE_HEIGHT /
                ScreenSettings.SCREEN_PROPORTION.getScreenProportion());

        //устанавливаем корабль в начальную позицию
        setStartPosition(screenBounds, START_POSITION.x, START_POSITION.y);

        //System.out.println("Spaceship.resize() getBottom()=" + getBottom());

    }

    /**
     * Метод устанавливает корабль в начальную позицию
     * @param screenBounds - границы игрового мира
     * @param shiftX - пропорция сдвига по X относительно середины ширины мира
     * @param shiftY - пропорция сдвига по Y относительно середины высоты мира
     */
    private void setStartPosition(Rect screenBounds, float shiftX, float shiftY){
        Vector2 start = new Vector2(screenBounds.getHalfWidth() * shiftX,
                screenBounds.getHalfHeight() * shiftY);
        destination.set(start);
    }

}
