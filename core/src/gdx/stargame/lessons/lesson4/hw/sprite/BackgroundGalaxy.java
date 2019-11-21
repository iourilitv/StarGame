package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class BackgroundGalaxy extends Sprite {
    //инициализируем константы количества фрагментов по X и Y всего
    private static final int TILES_X_NUMBER = 3;
    private static final int TILES_Y_NUMBER = 3;
    //инициализируем константы количества фрагментов по X и Y в отступе с каждой стороны
    //0, если TILES_X_NUMBER меньше 3
    private static final int TILES_IN_MARGE_A_SIDE_X = 1;
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
    //инициируем индексы фрагмента по X и Y, с которых начинаем прорисовку массива фрагментов
    private int startIndexX = 0;
    private int startIndexY = 0;
    //временная переменная счетчика инкремена индекса фрагмента по Y
    private int counterY = 0;

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
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //устанавливаем границы скрина
        screenBounds.set(worldBounds);
        //инициируем переменные размеров фрагмента текторы по ширине и высоте
        // в мировой системе координат
        tileWorldWidth = screenBounds.getWidth() /
                (float)(TILES_X_NUMBER - 2 * TILES_IN_MARGE_A_SIDE_X);
        tileWorldHeight = screenBounds.getHeight() /
                (float)(TILES_Y_NUMBER - 2 * TILES_IN_MARGE_A_SIDE_Y);
        //устанавливаем размеры спрайта
        // (выводим за видимое поле скрина фрагменты на полях по ширине и высоте)
        setWidth(tileWorldWidth * TILES_X_NUMBER);
        setHeight(tileWorldHeight * TILES_Y_NUMBER);
        //устанавливаем начальную позицию текстуры в ноль в мировых координатах(центр скрина)
        this.pos.set(worldBounds.pos);

        //TODO temporarily
        System.out.println("2BgGal.resize Width= " + getWidth() +
                ", Height= " + getHeight());
        System.out.println("BgGal.resize getLeft()= " + getLeft() +
                ", .getBottom()= " + getBottom());

    }

    @Override
    public void draw(SpriteBatch batch) {
        //перебираем массив фрагментов картинки фона
        int dY = 0;
        int dX;
        //опрашиваем массив фрагментов по строкам, начиная со стартового индекса по Y
        for (int i = startIndexY; i < startIndexY + tiles.length; i++) {
            //вычисляем координаты нижнего края фрагмента от аналогичного его текстуры
            float y = getTop() - tileWorldHeight * (TILES_IN_MARGE_A_SIDE_Y + dY++) - counterPosY;
            dX = 0;
            //опрашиваем массив фрагментов по колонкам, начиная со стартового индекса по X
            for (int j = startIndexX; j < startIndexX + tiles[0].length; j++) {
                //вычисляем координаты левого края фрагмента от аналогичного его текстуры
                float x = getLeft() + tileWorldWidth * (TILES_IN_MARGE_A_SIDE_X - 1 + dX++);//FIXME  - counterPosX

                System.out.println("j % tiles.length= " + j % tiles.length + ", x= " + x);

                //отрисовываем фрагмент
                batch.draw(
                        //текстура регион фрагмента фона
                        tiles[i % tiles.length][j % tiles.length],//[0]
                        //координаты левого-нижнего угла фрагмента от точки в середине экрана
                        // (здесь - в мировой системе координат 1f x 1f)
                        x, y,
                        //FIXME что такое originX, originY?
                        // одинаково halfWidth, halfHeight,//tileWorldWidth, tileWorldHeight,
                        0, 0,
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
        //двигаем фон по X
        moveBackgroundX();
        //двигаем фон по Y
        moveBackgroundY();
    }

    private void moveBackgroundX() {

//        //если счетчик меньше высоты фрагмента
//        //декрементируем счетчик на величину скорости сдвига фона, пока
//        // он не станет равен шагу(высоте фрагмента)
//        if(Math.abs(counterPosX += v.x) >= tileWorldWidth){
//            //инкрементируем стартовый индекс
//            //и берем остаток по количеству арагментов по вертикали
//            counterX = ++counterX % TILES_X_NUMBER;
//            //если заданная скорость движения фона по вертикали отрицательная,
//            // двигаем фон вверх(корабль летит вниз)
//            //если положительная, двигаем фон вниз(корабль летит вверх)
//            startIndexX = v.x < 0 ? counterX : TILES_X_NUMBER - counterX;
//            //обнуляем счетчик приращения
//            counterPosX = 0;
//        }
    }

    /**
     * Метод двигает фон по вертикали в зависимости от знака скорости сдвига по Y
     * плюс - вниз(корабль летит вверх) и минус - вверх(корабль летит вниз). Ноль - стоит
     */
    private void moveBackgroundY(){
        //если счетчик меньше высоты фрагмента
        //декрементируем счетчик на величину скорости сдвига фона, пока
        // он не станет равен шагу(высоте фрагмента)
        if(Math.abs(counterPosY += v.y) >= tileWorldHeight){
            //инкрементируем стартовый индекс
            //и берем остаток по количеству арагментов по вертикали
            counterY = ++counterY % TILES_Y_NUMBER;
            //если заданная скорость движения фона по вертикали отрицательная,
            // двигаем фон вверх(корабль летит вниз)
            //если положительная, двигаем фон вниз(корабль летит вверх)
            startIndexY = v.y < 0 ? counterY : TILES_Y_NUMBER - counterY;
            //обнуляем счетчик приращения
            counterPosY = 0;
        }
    }
}
