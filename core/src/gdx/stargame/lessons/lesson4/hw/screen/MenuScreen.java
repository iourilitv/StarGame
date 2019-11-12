package gdx.stargame.lessons.lesson4.hw.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson4.hw.base.BaseScreen;
import gdx.stargame.lessons.lesson4.hw.math.Rect;
import gdx.stargame.lessons.lesson4.hw.sprite.Background;
import gdx.stargame.lessons.lesson4.hw.sprite.ButtonExit;
import gdx.stargame.lessons.lesson4.hw.sprite.ButtonPlay;
import gdx.stargame.lessons.lesson4.hw.sprite.Star;

public class MenuScreen extends BaseScreen {

    private Game game;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    //объявляем объект кнопки Выйти из игры
    private ButtonExit buttonExit;
    //объявляем объект кнопки Начать игру
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        //инициируем объекты кнопок Выйти из игры и Начать игру
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //передаем координатную сетку игрового мира во все спрайты
        background.resize(worldBounds);

        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //передаем нажатие в спрайты кнопок
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return false;
    }

    private void update(float delta) {
        //вызываем методы обновления фона со звездами
        background.update(delta);

    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //вызываем методы прорисовки у каждого спрайта
        background.draw(batch);

        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}
