package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class BackgroundGalaxy extends Sprite {
    //инициализируем константы количества фрагментов по X и Y всего
    private static final int TILES_X_NUMBER = 3;//FIXME add condition for X
    private static final int TILES_Y_NUMBER = 3;//16;//32;
    //инициализируем константы количества фрагментов по X и Y в отступе с каждой стороны
    //0, если TILES_X_NUMBER меньше 3
    private static final int TILES_IN_MARGE_A_SIDE_X = 1;//1//FIXME add condition for X
    private static final int TILES_IN_MARGE_A_SIDE_Y = 1;

    //объявляем переменные размеров фрагмента текторы по ширине и высоте
    // в мировой системе координат
    private float tileWorldWidth;
    private float tileWorldHeight;

    //объявляем объект фрагмента в виде полной текстуры
    private TextureRegion galaxy;
    //объявляем массив плиток текстуры
    private TextureRegion[][] tiles;
    //принимаем границы экрана
    private Rect screenBounds = new Rect();
    //объявляем вектор скорости
    private Vector2 v;
    //временная переменная счетчика приращения позиции фрагментов по Y от скорости сдвига
    // для зацикливания картинки фона
    private float counterPosY = 0;
    //инициируем начальный индекс показа массива фрагментов
    // (начинаем показывать со второго фрагмента)
    private int startIndex = 0;//0;
    //временная переменная счетчика инкремена индекса фрагмента по Y
    private int counterY = 0;
    //очередь индексов фрагментов
    ArrayDeque<Integer> deque;

    public BackgroundGalaxy(TextureRegion region) {
        super(region);
        this.galaxy = region;
        //инициируем массив звезд с разными векторами скорости
        init(new Vector2());
    }

    public BackgroundGalaxy(TextureRegion region, Vector2 shiftVelocity) {
        super(region);
        this.galaxy = region;
        //инициируем массив звезд с общим константным вектором скорости
        init(shiftVelocity);
    }

    private void init(Vector2 shiftVelocity){
        //если параметры противоречат друг другу//FIXME add condition for X
        if(TILES_IN_MARGE_A_SIDE_X < 1 ||
                TILES_X_NUMBER < (TILES_IN_MARGE_A_SIDE_X * 2 + 1) ||
                TILES_IN_MARGE_A_SIDE_Y < 1 ||
                        TILES_Y_NUMBER < (TILES_IN_MARGE_A_SIDE_Y * 2 + 1)){
            throw new IllegalArgumentException("Wrong connection between " +
                    "shiftVelocity, TILES_..._NUMBER, TILES_IN_MARGE_A_SIDE_...!");
        }
        this.v = shiftVelocity;
        //инициируем массив фрагментов фона
        tiles = galaxy.split(galaxy.getRegionWidth() / TILES_X_NUMBER,
                galaxy.getRegionHeight() / TILES_Y_NUMBER);

//        //TODO temporarily
//        System.out.println("BackgroundGalaxy.init tiles:");
//        for (TextureRegion[] t: tiles) {
//            System.out.println(Arrays.toString(t));
//        }

        //FIXME
        deque = new ArrayDeque<>();
        for (int i = 0; i < tiles.length; i++) {
            deque.add(i);
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //устанавливаем границы скрина
        screenBounds.set(worldBounds);

        System.out.println("BgGal.resize screenBounds.getWidth()= " + screenBounds.getWidth() +
                ", .getHeight()= " + screenBounds.getHeight());
        System.out.println("BgGal.resize screenBounds.getLeft()= " + screenBounds.getLeft() +
                ", .getBottom()= " + screenBounds.getBottom());

        //инициируем переменные размеров фрагмента текторы по ширине и высоте
        // в мировой системе координат
        tileWorldWidth = screenBounds.getWidth() /
                (float)(TILES_X_NUMBER - 2 * TILES_IN_MARGE_A_SIDE_X);
        tileWorldHeight = screenBounds.getHeight() /
                (float)(TILES_Y_NUMBER - 2 * TILES_IN_MARGE_A_SIDE_Y);

        System.out.println("BgGal.resize tileWorldWidth= " + tileWorldWidth +
                ", tileWorldHeight= " + tileWorldHeight);

        //устанавливаем размеры спрайта
        // (выводим за видимое поле скрина фрагменты на полях по ширине и высоте)
        setWidth(tileWorldWidth * TILES_X_NUMBER);
        setHeight(tileWorldHeight * TILES_Y_NUMBER);
        //устанавливаем начальную позицию текстуры в ноль в мировых координатах(центр скрина)
        this.pos.set(worldBounds.pos);

        System.out.println("2BgGal.resize Width= " + getWidth() +
                ", Height= " + getHeight());
        System.out.println("BgGal.resize getLeft()= " + getLeft() +
                ", .getBottom()= " + getBottom());

    }

    @Override
    public void draw(SpriteBatch batch) {
        //перебираем массив фрагментов картинки фона
        int d = 0;
        //если заданная скорость движения фона по вертикали отрицательная
        if(v.y <= 0) {
            if(v.y == 0){
                d = TILES_IN_MARGE_A_SIDE_Y;
            }
            //двигаем фон вверх(корабль летит вниз)
            for (int i = startIndex; i < tiles.length + startIndex; i++) {
                batch.draw(
                        //текстура регион фрагмента фона
                        tiles[i % tiles.length][1],//[0]
                        //координаты левого-нижнего угла фрагмента от точки в середине экрана
                        // (здесь - в мировой системе координат 1f x 1f)
                        screenBounds.getLeft(), getTop() - tileWorldHeight * d++ + counterPosY,
                        //FIXME что это?
                        0, 0, //одинаково halfWidth, halfHeight,//tileWorldWidth, tileWorldHeight,
//                        tileWorldWidth / 2, tileWorldHeight / 2,
                        //ширина и высота поля отрисовки фрагмента от
                        // координаты левого-нижнего угла фрагмента(отрицательные - влево и вверх)
                        tileWorldWidth, tileWorldHeight,
                        //масштаб фрагмента по ширине и высоте
                        scale, scale,
                        angle);
            }
        }
        //если заданная скорость движения фона по вертикали положительная
        if(v.y > 0) {
//            d = 0;
//            //двигаем фон вниз(корабль летит вверх)
//            for (int i = startIndex; i > startIndex - tiles.length; i--) {
//                batch.draw(
//                        //текстура регион фрагмента фона
//                        tiles[(i + tiles.length) % tiles.length][1],//[0]
//                        //координаты левого-нижнего угла фрагмента от точки в середине экрана
//                        // (здесь - в мировой системе координат 1f x 1f)
////                        screenBounds.getLeft(), getBottom() + tileWorldHeight * d++ + counterPosY,
//                        screenBounds.getLeft(), screenBounds.getBottom() + tileWorldHeight * d++ + counterPosY,
//                        //FIXME что это?
//                        0, 0, //одинаково halfWidth, halfHeight,//tileWorldWidth, tileWorldHeight,
//                        //ширина и высота поля отрисовки фрагмента от
//                        // координаты левого-нижнего угла фрагмента(отрицательные - влево и вверх)
//                        tileWorldWidth, tileWorldHeight,
//                        //масштаб фрагмента по ширине и высоте
//                        scale, scale,
//                        angle);
//            }

            //двигаем фон вниз(корабль летит вверх)
            d = 0;
            for (int i = startIndex; i < startIndex + tiles.length; i++) {
                //вычисляем координаты левого нижнего угла фрагмента от аналогичного его текстуры
                float y = getTop() - tileWorldHeight * (TILES_IN_MARGE_A_SIDE_Y + d++) - counterPosY;
                float x = getLeft() + tileWorldWidth * TILES_IN_MARGE_A_SIDE_X;
                batch.draw(
                        //текстура регион фрагмента фона
                        tiles[i% tiles.length][1],//[0]
                        //координаты левого-нижнего угла фрагмента от точки в середине экрана
                        // (здесь - в мировой системе координат 1f x 1f)
                        x, y,
                        //FIXME что это?
                        0, 0, //одинаково halfWidth, halfHeight,//tileWorldWidth, tileWorldHeight,
                        //ширина и высота поля отрисовки фрагмента от
                        // координаты левого-нижнего угла фрагмента(отрицательные - влево и вверх)
                        tileWorldWidth, tileWorldHeight,
                        //масштаб фрагмента по ширине и высоте
                        scale, scale,
                        angle);
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //если задано движение по Y
        if(v.y != 0){
            //если заданная скорость движения фона по вертикали отрицательная
            if(v.y < 0){
                //двигаем фон вверх(корабль летит вниз)
                moveBackgroundUp();
            } else {
                //двигаем фон вниз(корабль летит вверх)
                moveBackgroundDown();
            }
        }
    }

    /**
     * Метод двигает фон вверх(корабль летит вниз)
     */
    private void moveBackgroundUp(){
        //если счетчик меньше высоты фрагмента
        if(counterPosY < 0){
            //инкрементируем счетчик на величину сторости сдвига фона
            counterPosY += Math.abs(v.y);

//            System.out.println("counterPosY= " + counterPosY);

        } else {
//            startIndex++;
//            startIndex %= TILES_Y_NUMBER;

            startIndex = deque.removeFirst();
            deque.addLast(startIndex);

            System.out.println("deque= " + deque);

            counterPosY = - tileWorldHeight;

        }
    }

    /**
     * Метод двигает фон вниз(корабль летит вверх)
     */
//    private void moveBackgroundDown(){
//        //если счетчик меньше высоты фрагмента
//        if(counterPosY > 0){
//            //инкрементируем счетчик на величину сторости сдвига фона
//            counterPosY -= Math.abs(v.y);
//
////            System.out.println("counterPosY= " + counterPosY);
//
//        } else {
////            startIndex++;
////            startIndex %= TILES_Y_NUMBER;
//
//            startIndex = deque.removeLast();
//            deque.addFirst(startIndex);
//
////            System.out.println("deque= " + deque);
//
//            counterPosY = tileWorldHeight;
//
//        }
//    }
//    private void moveBackgroundDown(){
//        //если счетчик меньше высоты фрагмента
//        //декрементируем счетчик на величину скорости сдвига фона, пока
//        // он не станет равен шагу(высоте фрагмента)
//        if((counterPosY -= Math.abs(v.y)) <= -tileWorldHeight){
//
//            System.out.println("Before startIndex= " + startIndex + ", counterY= " + counterY);
//
//            //инкрементируем стартовый индекс
//            //и берем остаток по количеству арагментов по вертикали
//            startIndex = (TILES_Y_NUMBER - ++counterY) % TILES_Y_NUMBER;
//
//            System.out.println("After startIndex= " + startIndex + ", counterY= " + counterY);
//
//            //обнуляем счетчик приращения
//            counterPosY = 0;
//        }
//    }
    private void moveBackgroundDown(){
        //если счетчик меньше высоты фрагмента
        //декрементируем счетчик на величину скорости сдвига фона, пока
        // он не станет равен шагу(высоте фрагмента)
        if((counterPosY += v.y) >= tileWorldHeight){

            System.out.println("Before startIndex= " + startIndex + ", counterY= " + counterY);

            //инкрементируем стартовый индекс
            //и берем остаток по количеству арагментов по вертикали
            counterY = (++counterY % TILES_Y_NUMBER);
            startIndex = (TILES_Y_NUMBER - counterY) % TILES_Y_NUMBER;

            System.out.println("After startIndex= " + startIndex + ", counterY= " + counterY);

            //обнуляем счетчик приращения
            counterPosY = 0;
        }
    }
}
