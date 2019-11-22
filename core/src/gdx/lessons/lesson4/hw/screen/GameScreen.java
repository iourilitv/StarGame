package gdx.lessons.lesson4.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson4.hw.base.BaseScreen;
import gdx.lessons.lesson4.hw.constants.ScreenSettings;
import gdx.lessons.lesson4.hw.math.Rect;
import gdx.lessons.lesson4.hw.sprite.BackgroundGalaxy;
import gdx.lessons.lesson4.hw.sprite.Spaceship;

/**
 * Скрин игрового поля.
 */
public class GameScreen extends BaseScreen {

    //объявляем тектуру картинки фона с галактикой
    private Texture bgGalaxy;
//    private Texture bg;

    //объявляем объект атласа текструры
    private TextureAtlas atlas;

    //объявляем объект спрайта фона
//    private Background background;
    private BackgroundGalaxy background;

    //объявляем объект спрайта корабля
    private Spaceship main_ship;

//    //инициируем константу скорости для всех звезд
//    private final Vector2 STAR_VELOCITY = new Vector2(0, -0.001f);
    //инициируем константный вектор скорости сдвига фона
    // (отрицательная - корабль движется вниз или лево)
    private final Vector2 SHIFT_VELOCITY = new Vector2(0, 0.0005f);//0.0005f

    @Override
    public void show() {
        super.show();
//        bg = new Texture("textures/bg.png");
//        //инициируем объект фона со звездами с заданным общим константным вектором скорости
//        background = new Background(new TextureRegion(bg), STAR_VELOCITY);

        //инициируем текстуру картинки фона галактики
        bgGalaxy = new Texture(ScreenSettings.CURRENT_BACKGROUND.getPicture().sourceName());
        //инициируем объект региона всей картинки фона
        // с заданным константным вектором скорости сдвига
        background = new BackgroundGalaxy(new TextureRegion(bgGalaxy), SHIFT_VELOCITY);

        //инициируем объект атласа текструры
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        //объявляем объект спрайта корабля
        main_ship = new Spaceship(atlas);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //передаем координатную сетку игрового мира во все спрайты
        background.resize(worldBounds);

        //передаем размеры мира в объект спрайта корабля
        main_ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
//        bg.dispose();
        bgGalaxy.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("GameScreen.keyDown. keycode=" + keycode);

        //передаем код нажатой клавиши клавиатуры в объект корабля
        main_ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("GameScreen.keyUp. keycode=" + keycode);

        //передаем код отпущенной клавиши клавиатуры в объект корабля
        main_ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //передаем вектор позиции касания в объект лого
        main_ship.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    private void update(float delta) {
        //вызываем методы обновления фона со звездами
        background.update(delta);

        //запускаем метод пересчета позиции корабля
        main_ship.update(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //вызываем методы прорисовки у каждого спрайта
        background.draw(batch);

        //запускаем метод прорисовки корабля
        main_ship.draw(batch);
        batch.end();
    }
}
