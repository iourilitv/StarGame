package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;
import java.util.concurrent.ExecutionException;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class BackgroundGalaxy extends Sprite {
    //инициализируем константы количества фрагментов по X и Y всего
    private static final int TILES_X_NUMBER = 1;//FIXME add condition for X
    private static final int TILES_Y_NUMBER = 3;//16;//32;
    //инициализируем константы количества фрагментов по X и Y в отступе с каждой стороны
    //0, если TILES_X_NUMBER меньше 3
    private static final int TILES_IN_MARGE_A_SIDE_X = 0;//1//FIXME add condition for X
    private static final int TILES_IN_MARGE_A_SIDE_Y = 1;

//    //объявляем переменные размеров фрагмента текторы по ширине и высоте
//    // в размерах экрана в пикселях
//    private int tileWidth;
//    private int tileHeight;

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

//    //объявляем вектор скачка скорости
//    private Vector2 jumpV;

    //временная переменная для зацикливания картинки фона
    private float counterY;
    //инициируем начальный индекс показа массива фрагментов
    // (начинаем показывать со второго фрагмента)
    private int startIndex = 0;
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

//        this.jumpV = new Vector2();

        //инициируем массив звезд с общим константным вектором скорости
        init(shiftVelocity);
    }

    private void init(Vector2 shiftVelocity){
        //если параметры противоречат друг другу//FIXME add condition for X
        if(/*TILES_IN_MARGE_A_SIDE_X < 1 ||
                TILES_X_NUMBER < (TILES_IN_MARGE_A_SIDE_X * 2 + 1) ||*/
                TILES_IN_MARGE_A_SIDE_Y < 1 ||
                        TILES_Y_NUMBER < (TILES_IN_MARGE_A_SIDE_Y * 2 + 1)){
            throw new IllegalArgumentException("Wrong connection between " +
                    "shiftVelocity, TILES_..._NUMBER, TILES_IN_MARGE_A_SIDE_...!");
        }

        this.v = shiftVelocity;

        //инициируем массив фрагментов фона
        tiles = galaxy.split(galaxy.getRegionWidth() / TILES_X_NUMBER,
                galaxy.getRegionHeight() / TILES_Y_NUMBER);

        //заменяет тоже самое
//        tiles = new TextureRegion[TILES_Y_NUMBER][TILES_X_NUMBER];
//        int heightStep = galaxy.getRegionHeight() / TILES_Y_NUMBER;
//        for (int i = 0; i < TILES_Y_NUMBER; i++) {
//            tiles[i][0] = new TextureRegion(galaxy, 0, i * heightStep,
//                    0, heightStep);
//        }


        //устанавливаем значения вектора скачка скорости в зависимости от нарезки тектуры
//        jumpV.x = TILES_X_NUMBER > 1 ? tile.getRegionWidth() : 0;
//        jumpV.y = TILES_Y_NUMBER > 1 ? tile.getRegionHeight() : 0;
//        jumpV.set(tileWorldWidth, tileWorldHeight);

//        System.out.println("galaxy.getRegionHeight()= " + galaxy.getRegionHeight());
//        System.out.println("jumpV " + jumpV);

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

//        System.out.println("BgGal.resize screenBounds.getWidth()= " + screenBounds.getWidth() +
//                ", screenBounds.getHeight()= " + screenBounds.getHeight());

        //инициируем переменные размеров фрагмента текторы по ширине и высоте
        // в размерах экрана в пикселях
//        tileWidth = (int)(Gdx.graphics.getWidth() / (float)(TILES_X_NUMBER - 2 * 0));
//        tileHeight = (int)(Gdx.graphics.getHeight() / (float)(TILES_Y_NUMBER - 2));

//        System.out.println("BgGal.resize tileWidth= " + tileWidth +
//                ", tileHeight= " + tileHeight);

        //инициируем переменные размеров фрагмента текторы по ширине и высоте
        // в мировой системе координат
        tileWorldWidth = screenBounds.getWidth() /
                (float)(TILES_X_NUMBER - 2 * TILES_IN_MARGE_A_SIDE_X);
        tileWorldHeight = screenBounds.getHeight() /
                (float)(TILES_Y_NUMBER - 2 * TILES_IN_MARGE_A_SIDE_Y);


        System.out.println("BgGal.resize tileWorldWidth= " + tileWorldWidth +
                ", tileWorldHeight= " + tileWorldHeight);

        //устанавливаем размеры спрайта
        // (выводим по одному фрагменту по ширине и высоте за видимое поле скрина)
        setWidth(tileWorldWidth * TILES_X_NUMBER);
        setHeight(tileWorldHeight * TILES_Y_NUMBER);


//        setHeightProportion(1f);

//        System.out.println("BgGal.resize getWidth()= " + getWidth() +
//                ", getHeight()= " + getHeight());

//        for (int i = 0; i < tiles.length; i++) {
//
////            System.out.println("tiles[" + i + "][0].getRegionWidth()=" +
////                    tiles[i][0].getRegionWidth() + ", .getRegionHeight()=" +
////                    tiles[i][0].getRegionHeight());
//
//            tiles[i][0].setRegionWidth(tileWidth);
//            tiles[i][0].setRegionHeight(tileHeight);
//
////            System.out.println("tiles[" + i + "][0].setRegionWidth(tileWidth)=" +
////                    tiles[i][0].getRegionWidth() + ", .setRegionHeight(tileHeight)=" +
////                    tiles[i][0].getRegionHeight());
//
//        }

//        System.out.println("BgGal.resize tiles[0][0]. " +
//                "getRegionWidth()= " + tiles[0][0].getRegionWidth() +
//                ", getRegionHeight()= " + tiles[0][0].getRegionHeight());

        //устанавливаем значения вектора скачка скорости в зависимости от нарезки тектуры
//        jumpV.set(tileWorldWidth * 0, tileWorldHeight);//FIXME tileWorldWidth * 0

//        System.out.println("jumpV.=" + jumpV);

        this.pos.set(worldBounds.pos);

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
                //вычисляем переменную шага координаты позиции фрагмента
//                d = (i - startIndex) % TILES_Y_NUMBER;

//                System.out.println("startIndex=" + startIndex + ", i= " + i +
//                    ", d= " + d + ", y=" + (getTop() - tileWorldHeight * d + counterY));

                batch.draw(
                        //текстура регион фрагмента фона
                        tiles[i % tiles.length][0],
                        //координаты левого-нижнего угла фрагмента от точки в середине экрана
                        // (здесь - в мировой системе координат 1f x 1f)
                        getLeft(), getTop() - tileWorldHeight * d++ + counterY,
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
            //двигаем фон вниз(корабль летит вверх)
            for (int i = startIndex; i > startIndex - tiles.length; i--) {

//            System.out.println("startIndex=" + startIndex + ", i= " + i +
//                    ", d= " + d + ", y=" + (getBottom() + tileWorldHeight * d + counterY));

                batch.draw(
                        //текстура регион фрагмента фона
                        tiles[(i + tiles.length) % tiles.length][0],
                        //координаты левого-нижнего угла фрагмента от точки в середине экрана
                        // (здесь - в мировой системе координат 1f x 1f)
                        getLeft(), getBottom() + tileWorldHeight * d++ + counterY,
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

    private void moveBackgroundUp(){
        //если счетчик меньше высоты фрагмента
//        if(counterY < tileWorldHeight){
        if(counterY < 0){
            //инкрементируем счетчик на величину сторости сдвига фона
            counterY += Math.abs(v.y);

//            System.out.println("counterY= " + counterY);

        } else {
//            startIndex++;
//            startIndex %= TILES_Y_NUMBER;

            startIndex = deque.removeFirst();
            deque.addLast(startIndex);

            System.out.println("deque= " + deque);

            counterY = - tileWorldHeight;
//            counterY = 0;

//            System.out.println("jumpV=" + jumpV);
//            System.out.println("startIndex=" + startIndex);

        }
    }

    private void moveBackgroundDown(){
        //если счетчик меньше высоты фрагмента
//        if(counterY < tileWorldHeight){
        if(counterY > 0){
            //инкрементируем счетчик на величину сторости сдвига фона
            counterY -= Math.abs(v.y);

//            System.out.println("counterY= " + counterY);

        } else {
//            startIndex++;
//            startIndex %= TILES_Y_NUMBER;

//            startIndex = deque.removeFirst();
//            deque.addLast(startIndex);
            startIndex = deque.removeLast();
            deque.addFirst(startIndex);

            System.out.println("deque= " + deque);

//            counterY = - tileWorldHeight;
            counterY = tileWorldHeight;

//            System.out.println("jumpV=" + jumpV);
//            System.out.println("startIndex=" + startIndex);

        }
    }
}
