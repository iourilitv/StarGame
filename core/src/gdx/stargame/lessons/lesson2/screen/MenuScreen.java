package gdx.stargame.lessons.lesson2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson2.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;

    private Vector2 pos;
    private Vector2 v;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");

        pos = new Vector2();
        v = new Vector2(2,1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if (Gdx.graphics.getHeight() > pos.y + img.getHeight()
        && Gdx.graphics.getWidth() > pos.x + img.getWidth()) {
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
        System.out.println(screenX + "; " + (Gdx.graphics.getHeight() - screenY));
        return false;
    }
}
