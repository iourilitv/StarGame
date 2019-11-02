package gdx.stargame.lessons.lesson2.hw.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Суперклас для всех экранов, т.к. операции с экраном в общем одни и теже.
 * Реализует методы интерфейсов: Screen для операций с экраном и
 * InputProcessor для обработки входных событий от мыши, клавиатуры, touchscreen etc.
 */
public class BaseScreen implements Screen, InputProcessor {
    //объявляем переменную батчера
    protected SpriteBatch batch;

    @Override
    //Метод(интерфейс Screen) подготовки(аналогично методу create())
    public void show() {
        System.out.println("show");

        //устанавливаем в качестве входного процессора(перехватчика событий)
        // самого себя(текущий экран?), чтобы перехватывать и обрабатывать
        // пользовательские события на экране
        Gdx.input.setInputProcessor(this);
        //инициализируем объект батчера
        this.batch = new SpriteBatch();
    }

    @Override
    //Метод(интерфейс Screen) покадровой прорисовки экрана с частотой кадра 60Гц(по умолчанию).
    //Здесь реализуется вся логика отображения игры.
    //delta - это отрезок времени при обновлении кадров. Нужен для организации таймеров.
    public void render(float delta) {

    }

    @Override
    //Метод(интерфейс Screen) определяет действия при изменении размера экрана,
    // в том числе при разворачивании свернутого экрана
    //width, height - новые значения ширины и высоты, которые сюда автоматически передаются
    //системой при любом изменении экрана
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
    }

    @Override
    //Метод(интерфейс Screen) определяет действия на сворачивание экрана
    public void pause() {
        System.out.println("pause");
    }

    @Override
    //Метод(интерфейс Screen) определяет действия на разворачивание экрана
    public void resume() {
        System.out.println("resume");
    }

    @Override
    //Метод(интерфейс Screen) определяет действия на закрытие экрана
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    //Метод(интерфейс Screen) выгрузки из памяти не использующихся объектов
    public void dispose() {
        System.out.println("dispose");
    }

    @Override
    //Метод(интерфейс InputProcessor) обрабатывающий нажатие любой клавиши на клавиатуре
    //keycode - код нажатой клавиши или комбинации клавиш. Не путать с кодом символа на клавише!
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    //Метод(интерфейс InputProcessor) обрабатывающий отпускание нажатой ранее клавиши на клавиатуре
    //keycode - код нажатой клавиши или комбинации клавиш. Не путать с кодом символа на клавише!
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    //Метод(интерфейс InputProcessor) обрабатывающий нажатие конкретной клавиши на клавиатуре
    //character - код символа на клавише
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    //Кроссплатформенный метод(интерфейс InputProcessor) обрабатывающий клик мыши,
    // прикосновение к экрану и т.п. в других манипуляторах
    //screenX, screenY - координаты точки клика
    //pointer - номер пальца, которым прикоснулись
    //button - номер кнопки мыши(правая, левая и т.п.)
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    //Кроссплатформенный метод(интерфейс InputProcessor) обрабатывающий отпускания кнопки мыши,
    // отпускания экрана и т.п. в других манипуляторах
    //screenX, screenY - координаты точки клика
    //pointer - номер пальца, которым прикоснулись
    //button - номер кнопки мыши(правая, левая и т.п.)
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    //?Кроссплатформенный? метод(интерфейс InputProcessor) обрабатывающий протаскивание объекта по экрану
    //screenX, screenY - координаты точки клика
    //pointer - номер пальца, которым прикоснулись
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        return false;
    }

    @Override
    //Метод(интерфейс InputProcessor) обрабатывающий любое движение мыши
    //screenX, screenY - новые значения координат мыши?
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    //Метод(интерфейс InputProcessor) обрабатывающий движение колесика мыши
    //amount - направление(1 и -1) скролинга(пролистывания)
    public boolean scrolled(int amount) {
        System.out.println("scrolled amount = " + amount);
        return false;
    }
}
