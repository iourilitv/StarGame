package gdx.stargame.lessons.lesson3.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
        //инициируем спрайт для лого в виде одной картинки
        logo = new Logo(new TextureRegion(img));
        //устанавливаем лого новые размеры
        logo.setHeightProportion(0.5f);
        //инициируем спрайт для заднего фона в виде одной картинки
        background = new Background(new TextureRegion(bg));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();

        //пересчитываем вектора скорости для следующего шага
        logo.update(delta);
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
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //System.out.println("MS2. touchDown touchX = " + touch.x + " touchY = " + touch.y);

        //передаем вектор позиции касания в объект лого
        logo.touchDown(touch, pointer);
        return false;
    }
}
