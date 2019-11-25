package gdx.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.StarGame;
import gdx.stargame.base.BaseScreen;
import gdx.stargame.math.Rect;
import gdx.stargame.sprite.Background;
import gdx.stargame.sprite.ButtonExit;
import gdx.stargame.sprite.ButtonPlay;
//import gdx.stargame.sprite.Star;

public class MenuScreen extends BaseScreen {

//    private static final int STAR_COUNT = 256;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;

//    private Star[] stars;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(StarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

//        bg = new Texture("textures/bg.png");background-v-512-1024

        bg = new Texture("textures/galaxy02-1800x2880.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");

//        stars = new Star[STAR_COUNT];
//        for (int i = 0; i < STAR_COUNT; i++) {
//            stars[i] = new Star(atlas);
//        }

        buttonExit = new ButtonExit(atlas, this);
        buttonPlay = new ButtonPlay(atlas, this);
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
        background.resize(worldBounds);

//        for (Star star : stars) {
//            star.resize(worldBounds);
//        }

        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
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

//        for (Star star : stars) {
//            star.update(delta);
//        }

    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);

//        for (Star star : stars) {
//            star.draw(batch);
//        }

        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}
