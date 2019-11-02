package gdx.stargame.lessons.lesson2.classfiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson2.classfiles.base.BaseScreen;

/**
 * Класс экрана меню. Это страртовый класс.
 */
public class MenuScreen extends BaseScreen {
    //объявляем переменную текстуры
    private Texture img;
    //объявляем переменную вектора позиции
    private Vector2 pos;
    //объявляем переменную вектора скорости
    private Vector2 v;

    @Override
    public void show() {
        super.show();
        //инициализируем объект текструры
        img = new Texture("badlogic.jpg");
        //инициализируем объект вектора позиции
        pos = new Vector2();
        //инициализируем объект вектора скорости
        v = new Vector2(2,1);
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

        //огранициваем движение границами скрина
        if (Gdx.graphics.getHeight() > pos.y + img.getHeight()
        && Gdx.graphics.getWidth() > pos.x + img.getWidth()) {
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

        //выводим пересчитанную по y координату клика
        //Важно! Координаты пользовательских событий рассчитываются от левого верхнего угла,
        //а не от левого нижное, как в координатах скрина
        //Gdx.graphics.getHeight() - размер текущего скрина
        System.out.println(screenX + "; " + (Gdx.graphics.getHeight() - screenY));
        return false;
    }
}
