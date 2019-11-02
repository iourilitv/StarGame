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
    //объявляем переменную вектора ускорения(acceleration)
    private final float SPEED_FACTOR = 2f;

    //private Direction forward = Direction.FORWARD;

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

        System.out.println("Direction.FORWARD.keyCode()=" + Direction.FORWARD.keyCode());
        System.out.println("Direction.BACK.keyCode()=" + Direction.BACK.keyCode());
        System.out.println("Direction.LEFT.keyCode()=" + Direction.LEFT.keyCode());
        System.out.println("Direction.RIGHT.keyCode()=" + Direction.RIGHT.keyCode());

        Direction.RIGHT.setKeyCode(99);
        System.out.println("Direction.RIGHT.keyCode()=" + Direction.RIGHT.keyCode());

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
        //ограничиваем движение точкой назначения
        if (Math.abs((int)(posDest.x - pos.x)) > 0 && Math.abs((int)(posDest.y - pos.y)) > 0) {
            //пересчитываем позицию объекта для следующей итерации
            // к вектору позиции прибавляем вектор скорости при каждом обновлении экрана
            pos.add(v);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
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

        //System.out.println("v.scl(speedFactor); v.x=" + v.x + "; " + "v.y=" + v.y + "; v.len()=" + v.len());
        //System.out.println();

        return false;
    }
}


//выводим пересчитанную по y координату клика
//Важно! Координаты пользовательских событий рассчитываются от левого верхнего угла,
//а не от левого нижное, как в координатах скрина
//Gdx.graphics.getHeight() - размер текущего скрина
//System.out.println(screenX + "; " + (Gdx.graphics.getHeight() - screenY));


