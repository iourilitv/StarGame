package gdx.stargame.lessons.lesson4.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson4.hw.base.BaseScreen;
import gdx.stargame.lessons.lesson4.hw.math.Rect;
import gdx.stargame.lessons.lesson4.hw.sprite.Background;
import gdx.stargame.lessons.lesson4.hw.sprite.Spaceship;
import gdx.stargame.lessons.lesson4.hw.sprite.Star;

/**
 * Скрин игрового поля.
 */
public class GameScreen extends BaseScreen {

    private Texture bg;
    //объявляем объект атласа текструры
    private TextureAtlas atlas;
    private Background background;
    //объявляем объект спрайта корабля
    private Spaceship main_ship;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
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
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
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
