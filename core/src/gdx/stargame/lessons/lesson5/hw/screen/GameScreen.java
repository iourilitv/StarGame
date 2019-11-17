package gdx.stargame.lessons.lesson5.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.lessons.lesson5.hw.base.BaseScreen;
import gdx.stargame.lessons.lesson5.hw.base.ShipEmitter;
import gdx.stargame.lessons.lesson5.hw.math.Rect;
import gdx.stargame.lessons.lesson5.hw.pool.BulletPool;
import gdx.stargame.lessons.lesson5.hw.settings.InputControl;
import gdx.stargame.lessons.lesson5.hw.settings.SoundSettings;
import gdx.stargame.lessons.lesson5.hw.settings.Source;
import gdx.stargame.lessons.lesson5.hw.sprite.Background;
import gdx.stargame.lessons.lesson5.hw.sprite.MainShip;
import gdx.stargame.lessons.lesson5.hw.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    //инициируем объект музыки фона
    private final Music backgroundMusic = Gdx.audio.newMusic(
            Gdx.files.internal(Source.MUSIC_BACKGROUND1.sourceName()));

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;

    //объявляем пул для снарядов
    private BulletPool bulletPool;

    //объявляем пул для снарядов
    private ShipEmitter shipEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        //инициируем пул для снарядов
        bulletPool = new BulletPool();//FIXME rename to bulletMainShip
        mainShip = new MainShip(atlas, bulletPool);

        //инициируем объект генератора генератора вражеских кораблей
        shipEmitter = new ShipEmitter();//ShipEmitter.getInstance();

        //запускаем вопроизведение фоновой музыки
        backgroundMusic.play();
        //устанавливаем уровень громкости по общему уровню громкости для музыки
        backgroundMusic.setVolume(SoundSettings.SOUND_MUSIC_LEVEL_MAIN.getPower());
        //установим закольцовывание музыки
        backgroundMusic.setLooping(true);

//        System.out.println("music.isPlaying()=" + backgroundMusic.isLooping());

    }

    @Override
    public void render(float delta) {
        update(delta);
        //переносим помеченные на удаление объекты игры из пула активных объектов в пул свободных
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);

        //передаем размеры мира в генератор врагов
        shipEmitter.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);

        //если нажата клавиша остановки музыки
        if(keycode == InputControl.MUSIC_PLAY_PAUSE_KEY.keyCode()){
            //и если музыка воспроизводится
            if (backgroundMusic.isPlaying()) {
                //поставить на паузу
                backgroundMusic.pause();
            //если не воспроизводится
            } else {
                //воспроизвести
                backgroundMusic.play();
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        //обновляем коллекцию активных объектов на каждом такте отрисовки экрана
        bulletPool.updateActiveSprites(delta);
        //обновляем объекты противника
        shipEmitter.update(delta);
    }

    /**
     * Метод переноса помеченных на удаление объектов игры из пула активных объектов в пул свободных.
     */
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();

        //освобождаем удаленные корабли противника
        shipEmitter.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        //отрисовываем пул действующих объектов
        bulletPool.drawActiveSprites(batch);
        //отрисовываем все элементы противника
        shipEmitter.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        //выгружаем из памяти ресурсы главного корабля
        mainShip.dispose();
        //выгружаем из памяти объект фоновой музыки
        backgroundMusic.dispose();

        //выгружаем из памяти ресурсы генератора врагов
        shipEmitter.dispose();
        super.dispose();
    }
}

//music.setVolume(0.5f);                 // устанавливает громкость на половину максимального объема
//music.setLooping(true);                // повторное воспроизведение, пока не будет вызван music.stop()
//music.stop();                          // останавливает воспроизведение
//music.pause();                         // приостанавливает воспроизведение
//music.play();                          // возобновляет воспроизведение
//boolean isPlaying = music.isPlaying(); // проверка воспроизводится ли музыка
//boolean isLooping = music.isLooping(); // проверка установлено ли повторение
//float position = music.getPosition();  // возвращает позицию воспроизведения в секундах