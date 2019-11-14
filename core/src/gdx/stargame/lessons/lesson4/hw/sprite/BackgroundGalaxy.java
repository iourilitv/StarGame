package gdx.stargame.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson4.hw.base.Sprite;
import gdx.stargame.lessons.lesson4.hw.math.Rect;

public class BackgroundGalaxy extends Sprite {
    //инициализируем константу количества звезд
    private static int TILES_X_NUMBER = 1;
    private static int TILES_Y_NUMBER = 16;//32;

    //объявляем переменные размеров фрагмента текторы по ширине и высоте
    // в размерах экрана в пикселях
    private int tileWidth;
    private int tileHeight;
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
    //объявляем вектор скачка скорости
    private Vector2 jumpV;
    //временная переменная для зацикливания картинки фона
    private float counterY;
    //инициируем начальный индекс показа массива фрагментов
    // (начинаем показывать со второго фрагмента)
    private int startIndex = 1;

    public BackgroundGalaxy(TextureRegion region) {
        super(region);
        this.galaxy = region;
        //инициируем массив звезд с разными векторами скорости
//        init(null);
    }

    public BackgroundGalaxy(TextureRegion region, Vector2 shiftVelocity) {
        super(region);
        this.galaxy = region;
        this.v = shiftVelocity;
        this.jumpV = new Vector2();
        //инициируем массив звезд с общим константным вектором скорости
        init(shiftVelocity);
    }

    private void init(Vector2 shiftVelocity){
        //инициируем массив фрагментов фона
        tiles = galaxy.split(galaxy.getRegionWidth() / TILES_X_NUMBER,
                galaxy.getRegionHeight() / TILES_Y_NUMBER);

        //устанавливаем значения вектора скачка скорости в зависимости от нарезки тектуры
//        jumpV.x = TILES_X_NUMBER > 1 ? tile.getRegionWidth() : 0;
//        jumpV.y = TILES_Y_NUMBER > 1 ? tile.getRegionHeight() : 0;
        jumpV.set(tileWidth, tileHeight);

//        System.out.println("galaxy.getRegionHeight()= " + galaxy.getRegionHeight());
//        System.out.println("jumpV " + jumpV);

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
        tileWidth = (int)(Gdx.graphics.getWidth() / (float)(TILES_X_NUMBER - 2 * 0));
        tileHeight = (int)(Gdx.graphics.getHeight() / (float)(TILES_Y_NUMBER - 2));

//        System.out.println("BgGal.resize tileWidth= " + tileWidth +
//                ", tileHeight= " + tileHeight);

        //инициируем переменные размеров фрагмента текторы по ширине и высоте
        // в мировой системе координат
        tileWorldWidth = screenBounds.getWidth() / (float)(TILES_X_NUMBER - 2 * 0);
        tileWorldHeight = screenBounds.getHeight() / (float)(TILES_Y_NUMBER - 2);


        System.out.println("BgGal.resize tileWorldWidth= " + tileWorldWidth +
                ", tileWorldHeight= " + tileWorldHeight);

        //устанавливаем размеры спрайта
        // (выводим по одному фрагменту по ширине и высоте за видимое поле скрина)
        setWidth(tileWorldWidth * TILES_X_NUMBER);
        setHeight(tileWorldHeight * TILES_Y_NUMBER);

//        System.out.println("BgGal.resize getWidth()= " + getWidth() +
//                ", getHeight()= " + getHeight());

        for (int i = 0; i < tiles.length; i++) {
            tiles[i][0].setRegionWidth(tileWidth);
            tiles[i][0].setRegionHeight(tileHeight);
        }

//        System.out.println("BgGal.resize tiles[0][0]. " +
//                "getRegionWidth()= " + tiles[0][0].getRegionWidth() +
//                ", getRegionHeight()= " + tiles[0][0].getRegionHeight());

        this.pos.set(worldBounds.pos);

    }

    @Override
    public void draw(SpriteBatch batch) {
        //перебираем массив фрагментов картинки фона
//        int n;
//        for (int i = 0; i < tiles.length; i++) {
//
//            n = (startIndex + i) % TILES_Y_NUMBER;
//            batch.draw(
//                    tiles[n][0],
//                    getLeft(), getTop(),
////                    0, SPRITE_HEIGHT + (3 - i) * getHeight() / TILES_Y_NUMBER,
//                    0, 0 + ( - n) * getHeight() / TILES_Y_NUMBER,
//                    getWidth(), getHeight(),
//                    1f, 1f / (float) TILES_Y_NUMBER,
//                    angle
//            );
//        }
//        batch.draw(
//                    tiles[0][0],
//                    getLeft(), getBottom(),
//                    0, SPRITE_HEIGHT + 2 * getHeight() / TILES_Y_NUMBER,
//                    getWidth(), getHeight(),
//                    1f, 1 / (float)TILES_Y_NUMBER,
//                    angle
//            );
        batch.draw(
                //текстура регион фрагмента фона
                tiles[0][0],
                //координаты левого-нижнего угла фрагмента от точки в середине экрана
                // (здесь - в мировой системе координат 1f x 1f)
                getLeft(), getTop() - tileWorldHeight,
                //
                0, 0,
                //ширина и высота поля отрисовки фрагмента от
                // координаты левого-нижнего угла фрагмента(отрицательные - влево и вверх)
                tileWorldWidth,
                tileWorldHeight,
                //масштаб фрагмента по ширине и высоте
                scale, scale,
                angle
        );

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        //инкрементируем вектор позиции спрайта на величину сторости сдвига фона
        pos.add(v);
        //если счетчик меньше высоты фрагмента
        if(counterY < tiles[0][0].getRegionHeight()){
            //инкрементируем счетчик на величину сторости сдвига фона
//            counterY += v.len();
            counterY += Math.abs(v.y);

//            System.out.println("counterY= " + counterY);

        } else {
            startIndex++;
//            startIndex %= TILES_Y_NUMBER;
            counterY = 0;
            pos.add(jumpV);
        }

    }

}
