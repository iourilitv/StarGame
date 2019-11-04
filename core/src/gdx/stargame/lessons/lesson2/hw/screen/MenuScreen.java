package gdx.stargame.lessons.lesson2.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson2.hw.Direction;
import gdx.stargame.lessons.lesson2.hw.base.BaseScreen;

/**
 * Класс экрана меню. Это стартовый экран.
 */
public class MenuScreen extends BaseScreen {
    //объявляем переменную текстуры
    private Texture img;
    //объявляем переменную вектора текущей позиции
    private Vector2 pos;

    //TODO L2hw.Added
    //объявляем переменную вектора позиции точки назначения
    private Vector2 posDest;
    //объявляем переменную вектора скорости
    private Vector2 v;
    //инициируем константу коэффициента изменения вектора скорости
    //если 0 - мгновенный переход в точку назначения
    private final float SPEED_FACTOR = 10f;//0f;//2f;
    //объявляем переменную вида управления движением клавишами
    private boolean keyControl;

    //объявляем константу точности позиционирования
    //private final float PRECISION = 1;//0.5f;

    //инициируем константу вектора ускорения(acceleration)
    private final float ACCELERATION = 1.03f;
    //объявляем константу дистанции до точки назначения для включения точного позиционирования
    private final float PRECISION_DISTANCE = 3;//2

    //объявляем флаги начала позиционирования
    private boolean isPosModeX;
    private boolean isPosModeY;

    @Override
    public void show() {
        super.show();
        //инициализируем объект текструры
        img = new Texture("badlogic.jpg");
        //инициализируем объект вектора позиции
        pos = new Vector2();

        //инициализируем объект вектора скорости
        //TODO L2hw.Deleted
//        v = new Vector2(2,1);
        //TODO L2hw.Added
        //инициализируем вектор позиции назначения и вектор скорости
        posDest = new Vector2();
        v = new Vector2();

        //тестирование eNum
//        System.out.println("Direction.FORWARD.keyCode()=" + Direction.FORWARD.keyCode());
//        System.out.println("Direction.BACK.keyCode()=" + Direction.BACK.keyCode());
//        System.out.println("Direction.LEFT.keyCode()=" + Direction.LEFT.keyCode());
//        System.out.println("Direction.RIGHT.keyCode()=" + Direction.RIGHT.keyCode());
//
//        Direction.RIGHT.setKeyCode(99);
//        System.out.println("Direction.RIGHT.keyCode()=" + Direction.RIGHT.keyCode());

    }

    @Override
    //Переопределенный родительский метод обновления экрана
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();

        //TODO L2hw.Deleted
        /*//огранициваем движение границами скрина
        if (Gdx.graphics.getHeight() > pos.y + img.getHeight()
        && Gdx.graphics.getWidth() > pos.x + img.getWidth()) {
            //пересчитываем позицию объекта для следующей итерации
            // к вектору позиции прибавляем вектор скорости при каждом обновлении экрана
            pos.add(v);
        }*/
        //TODO L2hw.Added
        //если управление осуществляется мышью или тачэкраном
        if(!keyControl){
//            if (Math.abs((int)(posDest.x - pos.x)) > 0 && Math.abs((int)(posDest.y - pos.y)) > 0) {
//                //пересчитываем позицию объекта для следующей итерации
//                // к вектору позиции прибавляем вектор скорости при каждом обновлении экрана
//                pos.add(v);
//            }
//            if (Math.abs(Math.round((posDest.x - pos.x) * PRECISION)) > PRECISION &&
//                    Math.abs(Math.round((posDest.y - pos.y) * PRECISION)) > PRECISION) {
//                //пересчитываем позицию объекта для следующей итерации
//                // к вектору позиции прибавляем вектор скорости при каждом обновлении экрана
//                pos.add(v);
//            }

            //ограничиваем движение точкой назначения
            //ВНИМАНИЕ! При значении точности менее 0,5 возможно проскакивание точки назначения
            /*if (Math.abs((posDest.x - pos.x)) > PRECISION ||
                    Math.abs((posDest.y - pos.y)) > PRECISION) {

                System.out.println("(posDest.x - pos.x)=" +
                        ((posDest.x - pos.x)) + "(posDest.y - pos.y)=" +
                        ((posDest.y - pos.y)));

                //пересчитываем позицию объекта для следующей итерации
                // к вектору позиции прибавляем вектор скорости при каждом обновлении экрана
                pos.add(v);
            }*/
            if ((v.x > 0 && posDest.x - pos.x > 0) || (v.y > 0 && posDest.y - pos.y > 0) ||
                    (v.x < 0 && posDest.x - pos.x < 0) || (v.y < 0 && posDest.y - pos.y < 0)) {
                    //(v.x <= 0 && posDest.x - pos.x < 0) || (v.y <= 0 && posDest.y - pos.y < 0)
//                System.out.println("(posDest.x - pos.x)=" +
//                        ((posDest.x - pos.x)) + "(posDest.y - pos.y)=" +
//                        ((posDest.y - pos.y)));

                //добавляем прецизионное торможение перед точкой назначения
                /*if(Math.abs(posDest.x - pos.x) < PRECISION_DISTANCE){
                    System.out.println("posDest.x - pos.x=" +
                            (posDest.x - pos.x) + ". v.x=" + v.x);

                    v.x /= ACCELERATION;

                    System.out.println("v.x /= ACCELERATION. v.x=" + v.x + "\n");
                }
                if(Math.abs(posDest.y - pos.y) < PRECISION_DISTANCE){
                    System.out.println("posDest.y - pos.y=" +
                            (posDest.y - pos.y) + ". v.y=" + v.y);

                    v.y /= ACCELERATION;

                    System.out.println("v.y /= ACCELERATION. v.y=" + v.y + "\n");
                }*/
                if(!isPosModeX && Math.abs(posDest.x - pos.x) < Math.abs(v.x * PRECISION_DISTANCE)){

                    System.out.println("posDest.x - pos.x=" +
                            (posDest.x - pos.x) + ". v.x=" + v.x);

                    //устанавливаем признак начала торможения по X
                    isPosModeX = true;

                    v.x /= SPEED_FACTOR;

                    System.out.println("v.x /= SPEED_FACTOR. v.x=" + v.x + "\n");
                }
                if(!isPosModeY && Math.abs(posDest.y - pos.y) < Math.abs(v.y * PRECISION_DISTANCE)){

                    System.out.println("posDest.y - pos.y=" +
                            (posDest.y - pos.y) + ". v.y=" + v.y);

                    //устанавливаем признак начала торможения по Y
                    isPosModeY = true;

                    v.y /= SPEED_FACTOR;

                    System.out.println("v.y /= SPEED_FACTOR. v.y=" + v.y + "\n");
                }

                //пересчитываем позицию объекта для следующей итерации
                // к вектору позиции прибавляем вектор скорости при каждом обновлении экрана
                pos.add(v);
            //если приблизились к точке назначения и остановились
            } else {
                //устанавливаем позицию, чтобы добиться абсолютной точности
                pos.x = posDest.x;
                pos.y = posDest.y;
            }
        //если управление клавишами клавиатуры
        } else{
            //добавляем ускорение при удержании клавиши нажатой
            v.scl(ACCELERATION);
            //расчитываем координаты новой позиции, в т.ч. для отрицательных координат
            pos.x += v.x + Gdx.graphics.getWidth() - img.getWidth();
            //с зацикливанием при попытке картинки выйти за границы скрина
            pos.x %= Gdx.graphics.getWidth() - img.getWidth();
            pos.y += v.y + Gdx.graphics.getHeight() - img.getHeight();
            pos.y %= Gdx.graphics.getHeight() - img.getHeight();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        //передаем управление от мыши к клавиатуре
        keyControl = true;
        //устанавливаем значения вектора скорости в зависимости от нажатой клавиши
        //ВНИМАНИЕ! switch/case испотзовать нельзя, т.к. case требует константу!
        if(keycode == Direction.FORWARD.keyCode()){
            v.x = 0;
            v.y = 1;
        } else if(keycode == Direction.BACK.keyCode()){
            v.x = 0;
            v.y = -1;
        } else if(keycode == Direction.LEFT.keyCode()){
            v.x = -1;
            v.y = 0;
        } else if(keycode == Direction.RIGHT.keyCode()){
            v.x = 1;
            v.y = 0;
        } else {
            System.out.println("Not supported key for moving direction: " + keycode);
        }

        /*switch (keycode){
            case (Direction.FORWARD.keyCode())://19//ОШИБКА, т.к. case требует константу!
                v.x = 0;
                v.y = 1;
                break;
            case 20:
                v.x = 0;
                v.y = -1;
                break;
            case 21:
                v.x = -1;
                v.y = 0;
                break;
            case 22:
                v.x = 1;
                v.y = 0;
                break;
            default:
                System.out.println("Not supported key for moving direction: " + keycode);
        }*/

        //добавляем коэффициент изменения величины скорости
        //ВНИМАНИЕ! НЕ использовать вместе с ускорением!
        //v.scl(SPEED_FACTOR);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyUp(keycode);
        //если клавиша отпущена, обнуляем вектор скорости
        v.x = 0;
        v.y = 0;
        //возвращаем управление мыши
        keyControl = false;

        //устанавливаем позиции назначения значения текущей позиции, чтобы не было скачков
        posDest.x = pos.x;
        posDest.y = pos.y;

//        System.out.println("(posDest.x - pos.x)=" +
//                        (posDest.x - pos.x) + " (posDest.y - pos.y)=" +
//                (posDest.y - pos.y));

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        //сбрасываем признак начала торможения по X
        isPosModeX = false;
        //сбрасываем признак начала торможения по Y
        isPosModeY = false;

        //устанавливаем координаты клика конечной позицией
        posDest.x = screenX;
        posDest.y = Gdx.graphics.getHeight() - screenY;//пересчет на лево-низ сетку координат

        //System.out.println("posDest.x=" + posDest.x + "; " + "posDest.y=" + posDest.y);

        //***вычисляем вектор скорости***
        //сначала из копии вектора конечной позиции вычитаем вектор текущей и нормализуем его
        v = posDest.cpy().sub(pos).nor();

        //System.out.println("v = posDest.cpy().sub(pos); v.x=" + v.x + "; " + "v.y=" + v.y + "; v.len()=" + v.len());
        //System.out.println("/= v.len(); v.x=" + v.x + "; " + "v.y=" + v.y + "; v.len()=" + v.len());

        //добавляем коэффициент изменения величины скорости
        v.scl(SPEED_FACTOR);

//        System.out.println("v.scl(speedFactor); v.x=" + v.x + "; " + "v.y=" + v.y + "; v.len()=" + v.len());
//        System.out.println();

        return false;
    }
}


//выводим пересчитанную по y координату клика
//Важно! Координаты пользовательских событий рассчитываются от левого верхнего угла,
//а не от левого нижное, как в координатах скрина
//Gdx.graphics.getHeight() - размер текущего скрина
//System.out.println(screenX + "; " + (Gdx.graphics.getHeight() - screenY));


