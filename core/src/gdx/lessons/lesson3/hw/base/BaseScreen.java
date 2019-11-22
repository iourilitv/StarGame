package gdx.lessons.lesson3.hw.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson3.hw.math.MatrixUtils;
import gdx.lessons.lesson3.hw.math.Rect;

/**
 * Экран-родитель для всех скринов.
 */
public class BaseScreen implements Screen, InputProcessor {
    //TODO L3hw update.Deleted
//    //объявим переменную для хранения пропорции экрана для подгонки спрайтов под разные экраны
//    protected float screenProportion;

    protected SpriteBatch batch;
    //экранная(скрина) координатная сетка
    private Rect screenBounds;
    //координатная сетка мировой системы координат
    private Rect worldBounds;
    //координатная сетка системы координат OpenGL
    private Rect glBounds;

    //матрица предобразования для трехмерной системы координат(default in OpenGL)
    private Matrix4 worldToGl;
    //матрица предобразования для двумерной системы координат
    private Matrix3 screenToWorld;

    private Vector2 touch;

    public BaseScreen() {
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.touch = new Vector2();
    }

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

        //TODO L3hw update.Deleted
//        //инициализируем переменную для хранения пропорции экрана для подгонки спрайтов
//        // под экраны с разными пропорциями
//        screenProportion = height / (float)width;

        //устанавливаем размеры текущего окна размерами системы координат скрина
        screenBounds.setSize(width, height);
        //устаналиваем координаты начала системы координат скрина
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        //инициируем переменную соотношения ширины к высоте скрина - позволяет адаптировать скрин
        //к устройству с любым разрешением экрана
        float aspect = width / (float) height;
        //задаем размеры прямоугольника мировой координатной сетки
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        //вычисляем матрицу перехода из мировой системы координат в систему координат OpenGL
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        // и применяем матрицу преобразований
        batch.setProjectionMatrix(worldToGl);
        //вычисляем матрицу перехода из системы координат скрина в мировую систему координат
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        //пересчитываем размеры скрина в мировую систему координат с помощью перегруженного метода
        resize(worldBounds);
    }

    /**
     * Перегруженный метод изменения размеров скрина
     * @param worldBounds - координатная сетка мировой системы координат
     */
    public void resize(Rect worldBounds) {
        //FIXME
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //System.out.println("BS1. touchDown screenX = " + screenX + " screenY = " + screenY);
        //устанавливаем вектору точки касания новые координаты с учетом разного отсчета по y
        //и умножаем его на матрицу преобразования скрина в мировые координаты
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        //вызываем перегруженный метод активного скрина
        touchDown(touch, pointer);
        return false;
    }

    /**
     * Перегруженный метод отработки касания в мировой системе координат
     * @param touch - вектор точки касания
     * @param pointer - палец
     * @return - не важно
     */
    public boolean touchDown(Vector2 touch, int pointer) {
        //System.out.println("BS2. touchDown touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //System.out.println("BS1. touchUp screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    private boolean touchUp(Vector2 touch, int pointer) {
        //System.out.println("BS2. touchUp touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    private boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled amount = " + amount);
        return false;
    }

}
