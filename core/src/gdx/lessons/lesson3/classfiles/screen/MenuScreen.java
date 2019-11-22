package gdx.lessons.lesson3.classfiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson3.classfiles.base.BaseScreen;
import gdx.lessons.lesson3.classfiles.math.Rect;
import gdx.lessons.lesson3.classfiles.sprite.Background;
import gdx.lessons.lesson3.classfiles.sprite.Logo;

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

        //TODO not necessary.Tested OpenGL coordinate grid.
        //чтобы применить координатную сетку OpenGL применяем единичную матрицу
        // (в главной диагонале 1, остальные 0)
        //batch.getProjectionMatrix().idt();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);

        //TODO not necessary.Tested OpenGL coordinate grid.
        //картинка выводится в правую верхнюю четверть координатной сетки
        //Внимание! Пропорции картинки НЕ сохраняются!
        //batch.draw(img, pos.x, pos.y, 1f, 1f);

        batch.end();
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
        return false;
    }
}
