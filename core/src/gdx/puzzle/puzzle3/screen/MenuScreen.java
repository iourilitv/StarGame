package gdx.puzzle.puzzle3.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import gdx.puzzle.puzzle3.base.Act;
import gdx.puzzle.puzzle3.base.BaseScreen;
import gdx.puzzle.puzzle3.constants.ScreenSettings;

/**
 * Класс экрана меню. Это стартовый экран.
 */
public class MenuScreen extends BaseScreen {
    private Texture picture;

    public MenuScreen(Act act) {
        super(act);
    }

    @Override
    public void show() {
        super.show();
        //инициируем объекты текстур на основе картинок в папке android/assets/
        picture = new Texture(ScreenSettings.CURRENT_PICTURE.getPicture().sourceName());
    }

    @Override
    //Переопределенный родительский метод обновления экрана
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(picture, 0, 0);
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        picture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        return false;
    }
}



