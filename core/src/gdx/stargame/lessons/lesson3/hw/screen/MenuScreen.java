package gdx.stargame.lessons.lesson3.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson3.hw.base.BaseScreen;
import gdx.stargame.lessons.lesson3.hw.math.Rect;
import gdx.stargame.lessons.lesson3.hw.sprite.Background;
import gdx.stargame.lessons.lesson3.hw.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Logo logo;
    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bg = new Texture("textures/bg.png");
        //TODO L3hw update.Deleted
//        //инициируем спрайт для лого в виде одной картинки
//        logo = new Logo(new TextureRegion(img), worldBounds);
//        //инициируем спрайт для заднего фона в виде одной картинки
//        background = new Background(new TextureRegion(bg), worldBounds);
        //TODO L3hw update.Deleted
        //инициируем спрайт для лого в виде одной картинки
        logo = new Logo(new TextureRegion(img));
        //инициируем спрайт для заднего фона в виде одной картинки
        background = new Background(new TextureRegion(bg));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //TODO L3hw update.Deleted
//        Gdx.gl.glClearColor(1, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        background.draw(batch);
//        logo.draw(batch);
//        batch.end();
        //TODO L3hw update.Added
        draw(batch);

        //пересчитываем вектора скорости для следующего шага
        //TODO L3hw update.Deleted
//        logo.update(delta);
        //TODO L3hw update.Added
        update(delta);
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //пересчитываем положение фона под актуальные границы(размеры) мира
        background.resize(worldBounds);

        //TODO L3hw update.Deleted
        //устанавливаем лого новые размеры
//        logo.setHeightProportion(logo.SPRITE_HEIGHT / screenProportion);
        //TODO L3hw update.Added
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //System.out.println("MS2. touchDown touchX = " + touch.x + " touchY = " + touch.y);

        //передаем вектор позиции касания в объект лого
        logo.touchDown(touch, pointer);
        return false;
    }

    //TODO L3hw update.Added
    public void update(float delta) {
        logo.update(delta);
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }
}
