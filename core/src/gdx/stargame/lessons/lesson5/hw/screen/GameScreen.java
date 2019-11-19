package gdx.stargame.lessons.lesson5.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gdx.stargame.lessons.lesson5.hw.base.BaseScreen;
import gdx.stargame.lessons.lesson5.hw.base.Prefs;
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
    //инициализируем объект класса предпочтений
    // устанавливаем путь к файлу для хранения предпочтений пользователя и сохранения игр
    //работает подобно хэш-таблице Map
//    private final Preferences prefs = Gdx.app.getPreferences("GameScreen_Preferences");

    //объявляем переменную для хранения статуса пауза игры
    private boolean isGamePaused;

    private Prefs conditions = new Prefs();

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

//        System.out.println("Before prefs.get()=" + prefs.get());
//        //Before prefs.get()={}

        //сохраняем в предпочтения ссылку на название файла картинки текущего фона
//        prefs.putString("CURRENT_BACKGROUND", "Source.GALAXY03_2610X3960");
        // вызов процесса сохранения изменений файла предпочтений
//        prefs.flush();
        //в папке C:\Users\iurii\.prefs создается новый файл(если его нет) с заданным названием
        // и изменение сохраняется в файл в таком виде:
        //<?xml version="1.0" encoding="UTF-8" standalone="no"?>
        //<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
        //<properties>
        //<entry key="CURRENT_BACKGROUND">Source.GALAXY03_2610X3960</entry>
        //</properties>

//        System.out.println("After prefs.get()=" + prefs.get());
        //After prefs.get()={CURRENT_BACKGROUND=Source.GALAXY03_2610X3960}

//        //выводим значение свойства, если есть, в противном случае - значение по-умолчанию
//        String name = prefs.getString("CURRENT_BACKGROUND", "No name stored");
//        //если такое свойство есть, возвращается его значение
//        System.out.println("name = " + name);
//        // name = Source.GALAXY03_2610X3960

//        //выводим отсутствующее свойство с именем "name"
//        String name = prefs.getString("name", "No name stored");
//        //если такого свойства нет, возвращается значение по-умолчанию
//        System.out.println("name = " + name);
//        //name = No name stored

//        //удаляем свойство с именем "CURRENT_BACKGROUND"
////        name = prefs.get().remove("CURRENT_BACKGROUND").toString();//TODO doesn't remove
////        System.out.println("removed name = " + name);
//        prefs.remove("CURRENT_BACKGROUND");
//        prefs.flush();
//        System.out.println("After removing. prefs.get()=" + prefs.get());

        Prefs prefs = new Prefs();
//        prefs.setStartPrefs();
//        prefs.storeStartPrefs();
//        super.saveCondition();

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

        //если нажата клавиша паузы в игре
        if(keycode == InputControl.MUSIC_GAME_PAUSE_KEY.keyCode()){
            //вызываем метод обработки паузы игры
            pause();
        }
        //если нажата клавиша продолжить в игре
        if(keycode == InputControl.MUSIC_GAME_RESUME_KEY.keyCode()){
            //вызываем метод обработки продолжения игры
            resume();
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
        //если игра не на паузе, обновляем
        if(!isGamePaused) {
            for (Star star : stars) {
                star.update(delta);
            }
            mainShip.update(delta);
            //обновляем коллекцию активных объектов на каждом такте отрисовки экрана
            bulletPool.updateActiveSprites(delta);
            //обновляем объекты противника
            shipEmitter.update(delta);
        }
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

    @Override
    public void pause() {
//        super.pause();
////        shipEmitter.pause();
////        mainShip.pause();
//        conditions.saveScreenCondition(this);

        isGamePaused = true;
        backgroundMusic.pause();
    }

    @Override
    public void resume() {
//        super.resume();
//        restoreCondition((Map<String, Float>) conditions.getPrefs().get());
        isGamePaused = false;
        backgroundMusic.play();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public Map<String, ?> saveCondition(){
        Map<String, Float> map = new HashMap<>();
        map.put("mainShip.pos.x", mainShip.pos.x);
        map.put("mainShip.pos.y", mainShip.pos.y);
        map.put("mainShip.v0.x", mainShip.getV0().x);
        map.put("mainShip.v0.y", mainShip.getV0().y);
        map.put("mainShip.v.x", mainShip.getV().x);
        map.put("mainShip.v.y", mainShip.getV().y);

        System.out.println("GameScreen.saveCondition map= " + map.toString());

//        System.out.println("this.getClass().getDeclaredFields().toString()= " +
//                Arrays.deepToString(this.getClass().getDeclaredFields()));

//        for (int i = 0; i < ; i++) {
//
//        }

        return map;
    }

    public void restoreCondition(Map<String, Float> map){
        System.out.println("Before restoreCondition mainShip.pos= " + mainShip.pos);

        mainShip.pos.set(map.get("mainShip.pos.x").floatValue(), map.get("mainShip.pos.y").floatValue());
        mainShip.getV0().set(map.get("mainShip.v0.x"), map.get("mainShip.v0.y"));
        mainShip.getV().set(map.get("mainShip.v.x"), map.get("mainShip.v.y"));

        System.out.println("After restoreCondition mainShip.pos= " + mainShip.pos);
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