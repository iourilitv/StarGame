package gdx.lessons.lesson7.hw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import gdx.lessons.lesson7.hw.StarGame;
import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.math.Rect;
import gdx.lessons.lesson7.hw.pool.BulletPool;
import gdx.lessons.lesson7.hw.pool.EnemyPool;
import gdx.lessons.lesson7.hw.pool.ExplosionPool;
import gdx.lessons.lesson7.hw.sprite.Background;
import gdx.lessons.lesson7.hw.sprite.Bullet;
import gdx.lessons.lesson7.hw.sprite.ButtonNewGame;
import gdx.lessons.lesson7.hw.sprite.Enemy;
import gdx.lessons.lesson7.hw.sprite.GameOver;
import gdx.lessons.lesson7.hw.sprite.MainShip;
import gdx.lessons.lesson7.hw.sprite.Star;
import gdx.lessons.lesson7.hw.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    //объявляем переменную для хранения статуса пауза игры
    private boolean gamePaused;
    //объявляем переменную для хранения статуса конца игры
    private boolean gameFinished;

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;

    //объявляем регион для картинки "новая игра"
    private ButtonNewGame newGameButton;

    private Music music;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    //объявляем объект пула взрывов
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;

    //объявляем спрайт для сообщения "конец игры"
    private GameOver gameOver;

    public GameScreen(StarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        //инициируем спрайт для анимации "конец игры"
        gameOver = new GameOver(atlas.findRegion("message_game_over"));
        //инициируем объект кнопки "новая игра"
//        newGameButton = new ButtonNewGame(atlas, game, this);
        newGameButton = new ButtonNewGame(atlas, this);

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        //инициируем объект пула взрывов
        explosionPool = new ExplosionPool(atlas);
        //инициируем объект пула кораблей противника с дополнительным параметром - пулом взрывов
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        //инициируем объект гравного корабля с дополнительным параметром - пулом взрывов
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        //вызываем метод проверки столкновений объектов(снарядов, кораблей и т.п.)
        checkCollisions();
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
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
        mainShip.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        //выгружаем из памяти пул взрывов
        explosionPool.dispose();
        enemyEmitter.dispose();
        //выгружаем из памяти объекты анимации конца игры
        gameOver.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
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
        //вызываем метод отработки нажатия кнопки NewGame
        newGameButton.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        //вызываем метод отработки отпускания нажатия кнопки NewGame
        newGameButton.touchUp(touch, pointer);
        return false;
    }

    private void update(float delta) {
        //если игра поставлена на паузе
        if(gamePaused) {
            //выходим, нивего не обновляя
            return;
        }
        //если игра окончена
        if (gameFinished){
            //запускаем метод обновления анимации окончания игры  и выходим
            updateGameOverAnimation(delta);
            return;
        }
        //если игра не на паузе или главный корабль еще живой, обновляем все элементы
        if(!mainShip.isDestroyed()) {
            for (Star star : stars) {
                star.update(delta);
            }
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
            //обновляем пул взрывов
            explosionPool.updateActiveSprites(delta);
        }
        //если игра на паузе или окончена
        else {
            //устанавливаем состояние конец игры
            gameFinished = true;
        }


    }

    /**
     * Метод проверки столкновений объектов(снарядов, кораблей и т.п.)
     */
    private void checkCollisions() {
        //инициируем временные коллекции для пулов кораблей противника и снарядов
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        //листаем коллекцию кораблей противника - отрабатываем их столкновения
        for (Enemy enemy : enemyList) {
            //инициируем временную переменную для рассчета минимального расстояния между объектами
            //это нужно, чтобы соприкосновения происходили реалистично близко к центрам объектов
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            //если длина вектора между векторами позиции корабля противника и главного корабля
            // стал меньше минимального расстояния
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                //вызываем метод повреждения главного корабля
                mainShip.damage(enemy.getDamage());
                //вызываем метод уничтожения корабля противника(такая игровая логика)
                enemy.destroy();
            }
            //листаем коллекцию снарядов (главного корабля) - отрабатываем их столкновения
            for (Bullet bullet : bulletList) {
                //если пуля не принадлежит главному кораблю, значит это пуля корабля противника
                if (bullet.getOwner() != mainShip) {
                    //пропускаем остальной код на этой итерации - идем к следующей
                    continue;
                }
                //если снаряд главного корабля попал в корабль противника
                if (enemy.isBulletCollision(bullet)) {
                    //вызываем метод расчета повреждения корабля противника
                    enemy.damage(bullet.getDamage());
                    //вызываем метод уничтожения снаряда
                    bullet.destroy();
                }
            }
        }
        //листаем коллекцию снарядов (кораблей противника) - отрабатываем их столкновения
        for (Bullet bullet : bulletList) {
            //если пуля принадлежит главному кораблю, значит это не пуля корабля противника
            if (bullet.getOwner() == mainShip) {
                //пропускаем остальной код на этой итерации - идем к следующей
                continue;
            }
            //если снаряд корабля противника попал в главный корабль
            if (mainShip.isBulletCollision(bullet)) {
                //вызываем метод расчета повреждения главного корабля
                mainShip.damage(bullet.getDamage());
                //вызываем метод уничтожения снаряда
                bullet.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        //вызываем метод переноса удаленных объектов взрывов из коллекции активных в свободные
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        //отрисовываем объекты в пуле взрывов
        explosionPool.drawActiveSprites(batch);

        //если игра окончена
        if(gameFinished) {
            //отрисовываем объект анимации конца игры
            gameOver.draw(batch);
            //если активирован флаг ожидания запуска новой игры
            if(newGameButton.isShowing()){
                //отрисовываем объект кнопки NewGame
                newGameButton.draw(batch);
            }
        //если игра еще не окончена
        } else {
            //отрисовываем главный корабль
            mainShip.draw(batch);
        }
        batch.end();
    }

    /**
     * Метод обновления анимации окончания игры
     * @param delta - время между кадрами
     */
    private void updateGameOverAnimation(float delta) {
        //***дорисовываем сцену битвы***
        //вызываем метод "последнего вздоха" взрывов
        explosionPool.setExplosionEndFrame(GameOver.LAST_FRAME);
        //останавливаем фоновую музыку
        music.pause();
        //запускаем анимацию с начальными установками
        gameOver.start();
        //вызываем метод обновления спрайта конец игры
        gameOver.update(delta);

        //если анимация конца игры закончена
        if(gameOver.isDestroyed()){
            //вызываем метод первоначальной установки параметров кнопки на экран
            newGameButton.resize(worldBounds);
            //взводим меркер отображения кнопки newGame на экране
            newGameButton.setShowing(true);
            //вызываем метод обновления кнопки NewGame
            newGameButton.update(delta);
        }
    }
}
