package gdx.stargame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import gdx.stargame.managers.ScoreCounter;
import gdx.stargame.managers.PrintManager;
import gdx.stargame.sprites.AboutMessage;
import gdx.stargame.sprites.BackgroundGalaxy;
import gdx.stargame.StarGame;
import gdx.stargame.base.BaseScreen;
import gdx.stargame.math.Rect;
import gdx.stargame.pools.BulletPool;
import gdx.stargame.pools.EnemyPool;
import gdx.stargame.pools.ExplosionPool;
import gdx.stargame.sprites.Bullet;
import gdx.stargame.sprites.ButtonMenu;
import gdx.stargame.sprites.ButtonNewGame;
import gdx.stargame.sprites.Enemy;
import gdx.stargame.sprites.GameOver;
import gdx.stargame.sprites.MainShip;
import gdx.stargame.managers.EnemyEmitter;

public class GameScreen extends BaseScreen {
    //инициируем перечисление для режимов игры: игра, пауза, конец игры.
    public enum State {PLAYING, PAUSE, GAME_OVER}

    //объявляем тектуру картинки фона с галактикой
    private Texture bgGalaxy;
    private TextureAtlas atlas;
    private Music music;
    //объявляем объект звука попадания снаряда в корабль
    private Sound shellHitSound;
    //объявляем константы параметров звука
    private final float shellHitSoundVolume = 0.1f;//уровень громкости звука
    private final float shellHitSoundPitch = 2f;//уровень тона звука

    //объявляем объект спрайта фона
    private BackgroundGalaxy background;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    //объявляем объект пула взрывов
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;

    //объявляем переменные для хранения текущего и предыдущего режимов игры
    private State state;
    private State prevState;

    //объявляем спрайт для сообщения "конец игры"
    private GameOver gameOver;
    //объявляем регион для картинки "новая игра"
    private ButtonNewGame newGameButton;
    //объявляем спрайт для кнопки меню
    private ButtonMenu buttonMenu;
    //объявляем спрайт сообщения "О программе"
    private AboutMessage aboutMessage;

    //инициируем переменную для объекта счетчика очков
    private ScoreCounter scoreCounter = ScoreCounter.getInstance();
    //объявляем переменную объекта менеджера печати
    private PrintManager printManager;

    public GameScreen(StarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        //инициируем объект звука попадания снаряда в корабль
        shellHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shell-hit.mp3"));
        //инициируем текстуру картинки фона галактики
        bgGalaxy = new Texture("textures/galaxy03-2610x3960.jpg");
        //инициируем объект региона всей картинки фона
        // с дефолтным константным вектором скорости сдвига
        background = new BackgroundGalaxy(new TextureRegion(bgGalaxy));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        //инициируем объект пула взрывов
        explosionPool = new ExplosionPool(atlas);
        //инициируем объект пула кораблей противника с дополнительным параметром - пулом взрывов
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        //инициируем объект гравного корабля с дополнительным параметром - пулом взрывов
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        //инициируем объект спрайта для анимации "конец игры"
        gameOver = new GameOver(atlas);
        //инициируем объект спрайта кнопки и организации режима "новая игра"
        newGameButton = new ButtonNewGame(atlas, this);
        //инициируем объект спрайта сообщения "О программе"
        aboutMessage = new AboutMessage(this);
        //инициируем объект спрайта для кнопки меню
        buttonMenu = new ButtonMenu(this);
        //инициируем переменную объекта менеджера печати
        printManager = new PrintManager(this);
        //устанавливаем текущиему и предыдущему режиму игры режим "играть"
        state = State.PLAYING;
        prevState = State.PLAYING;
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
        mainShip.resize(worldBounds);
        //передаем мировую координатную сетку в классы
        // окончания игры, начала новой игры и кнопки меню
        // и устанавливаем размеры их объектов
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
        buttonMenu.resize(worldBounds);
        aboutMessage.resize(worldBounds);
        printManager.resize(worldBounds);
    }

    /**
     * Метод обработки паузы игры(по событию сворачивание окна, в т.ч.).
     */
    @Override
    public void pause() {
        //запоминаем текущий уровень игры
        prevState = state;
        //устанавливаем текущему уровню игры режим "пауза"
        state = State.PAUSE;
        //преостанавливаем воспроизведение музыки
        music.pause();
        //передаем событие паузу в объект конца игры
        gameOver.pause();
    }

    /**
     * Метод обработки продолжения игры после паузы(по событию разворачивание окна, в т.ч.).
     */
    @Override
    public void resume() {
        //устанавливаем текущий режим игру на сохраненный предыдущий
        state = prevState;
        //воспроизводим музыку с того места, где она была преостановлена
        music.play();
        if (state == State.GAME_OVER) {
            //передаем событие разворачивания окна после паузы в объект конца игры
            gameOver.resume();
        }
    }

    /**
     * Метод освобождения памяти от объектов скрина.
     */
    @Override
    public void dispose() {
        bgGalaxy.dispose();
        atlas.dispose();
        music.dispose();
        shellHitSound.dispose();
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

    /**
     * Метод обработки нажатия клавиши
     * @param keycode - код клавиши клавиатуры
     * @return - не используется
     */
    @Override
    public boolean keyDown(int keycode) {
        //если нажата клавиша пробел
        if(keycode == Input.Keys.SPACE){
            //если установлено состояние пауза игры
            if(state == State.PAUSE){
                //восстанавливаем игру после паузы
                resume();
            //если игра не в режиме паузы
            } else {
                //ставим игру на паузу
                pause();
            }
        }
        mainShip.keyDown(keycode);
        return false;
    }

    /**
     * Метод обработки отпускания нажатой клавиши
     * @param keycode - код клавиши клавиатуры
     * @return - не используется
     */
    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    /**
     * Метод отрабатывает касание экрана или клика мыши.
     * @param touch - вектор координаты точки касания
     * @param pointer - номер пальца или клика
     * @return - не используется
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //если касание не попало на кнопку меню
        if(!buttonMenu.isMe(touch)){
            //передаем касание только в класс главного корабля
            mainShip.touchDown(touch, pointer);
            //если текущий режим игры установлен режим "конец игры"
            if (state == State.GAME_OVER) {
                //вызываем метод отработки нажатия кнопки NewGame
                newGameButton.touchDown(touch, pointer);
            }
        //если касание на иконке кнопки
        } else {
            //вызываем метод отработки нажатия кнопки меню
            buttonMenu.touchDown(touch, pointer);
        }
        return false;
    }

    /**
     * Метод отрабатывает отпускание касания экрана или клика мыши.
     * @param touch - вектор координаты точки касания
     * @param pointer - номер пальца или клика
     * @return - не используется
     */
    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        //если касание не попало на кнопку меню
        if(!buttonMenu.isMe(touch)){
            //передаем касание только в класс главного корабля
            mainShip.touchUp(touch, pointer);
            //если текущий режим игры установлен режим "конец игры"
            if (state == State.GAME_OVER) {
                //вызываем метод отработки нажатия кнопки NewGame
                newGameButton.touchUp(touch, pointer);
            }
            //если касание на иконке кнопки
        } else {
            //вызываем метод отработки нажатия кнопки меню
            buttonMenu.touchUp(touch, pointer);
        }
        return false;
    }

    /**
     * Метод организует режим начала новой игры.
     */
    public void startNewGame() {
        //устанавливаем текущий режим игры в положение "играть"
        state = State.PLAYING;
        //устанавливаем начальные настройки объекта конца игры
        gameOver.startNewGame();
        //запускаем воспроизведение фоновой музыки
        music.play();
        //задаем начальные параметры объекту счетчика очков
        scoreCounter.startNewGame();
        //задаем начальные параметры главному кораблю
        mainShip.startNewGame(worldBounds);
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }

    /**
     * Метод отработки изменений в объектах игры.
     * @param delta - период времени между кадрами(1/60 сек)
     */
    private void update(float delta) {
        //если текущий режим игры в положении "играть" обновляем все объекты игры
        if (state == State.PLAYING) {
            //вызываем методы обновления фона со звездами
            background.update(delta);
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, scoreCounter.getLevel());
            //обновляем пул взрывов
            explosionPool.updateActiveSprites(delta);
        //если игра поставлена на паузе
        } else if(state == State.PAUSE) {
            //выходим, ничего не обновляя
            return;
        //если игра окончена
        } else if (state == State.GAME_OVER){
            //запускаем метод обновления анимации окончания игры  и выходим
            updateGameOverAnimation(delta);
            return;
        }
        //если переходим на следующий уровень игры
        if (scoreCounter.isNextLevel()) {
            //сбрасываем флаг перехода на новый уровень
            scoreCounter.resetNextLevel();
            //восстанавливаем значение жизни главного корабля игрока при измении уровеня игры
            mainShip.changeShipSettingsByLevel(scoreCounter.getLevel());
            //меняем настройки кораблей противника при переходе на другой уровень
            enemyPool.changeShipsSettingsByLevel(scoreCounter.getLevel());
        }
    }

    /**
     * Метод проверки столкновений объектов(снарядов, кораблей и т.п.)
     */
    private void checkCollisions() {
        //если текущий режим игры не а положении "играть" ни чего не делаем
        if (state != State.PLAYING) {
            return;
        }
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
                //когда корабль противника уничтожен, увеличиваем значение суммы набранных очков
                // на размер жизни сбитого корабля противника установленного для текущего уровня
                scoreCounter.updateScoresAndCheckNextLevel(
                        enemy.getConstHp() * scoreCounter.getLevel());
                //вызываем метод уничтожения корабля противника
                enemy.destroy();
                //если при столкновении с кораблем противника уничтожен главный корабль
                if (mainShip.isDestroyed()) {
                    //сбрасываем значение жизни корабля
                    mainShip.setHp(0);
                    //устанавливаем текущий режим игры в положение "конец игры"
                    state = State.GAME_OVER;
                }
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
                    //воспроизводим звук попадания сняряда в корабль в зависимости от расположения корабля
                    enemy.playSurroundSound(shellHitSound, shellHitSoundVolume, shellHitSoundPitch);
                    //вызываем метод расчета повреждения корабля противника
                    enemy.damage(bullet.getDamage());
                    //вызываем метод уничтожения снаряда
                    bullet.destroy();
                    //если корабль противника уничтожен
                    if (enemy.isDestroyed()) {
                        //когда корабль противника уничтожен, увеличиваем значение суммы набранных очков
                        // на размер жизни сбитого корабля противника установленного для текущего уровня
                        scoreCounter.updateScoresAndCheckNextLevel(
                                enemy.getConstHp() * scoreCounter.getLevel());
                    }
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
                //воспроизводим звук попадания сняряда в корабль в зависимости от расположения корабля
                mainShip.playSurroundSound(shellHitSound, shellHitSoundVolume, shellHitSoundPitch);
                //вызываем метод расчета повреждения главного корабля
                mainShip.damage(bullet.getDamage());
                //вызываем метод уничтожения снаряда
                bullet.destroy();
                //если при попадании снаряда уничтожен главный корабль
                if (mainShip.isDestroyed()) {
                    //сбрасываем значение жизни корабля
                    mainShip.setHp(0);
                    //устанавливаем текущий режим игры в положение "конец игры"
                    state = State.GAME_OVER;
                }
            }
        }
    }

    /**
     * Метод во всех пулах переносит все помеченные на удаление спрайты в пуле
     * из коллекции активных спрайтов в коллекцию пассивных.
     */
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        //вызываем метод переноса удаленных объектов взрывов из коллекции активных в свободные
        explosionPool.freeAllDestroyedActiveSprites();
    }

    /**
     * Метод отрисовки объектов игрового поля.
     */
    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //вызываем методы прорисовки у каждого спрайта
        background.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        //отрисовываем объекты в пуле взрывов
        explosionPool.drawActiveSprites(batch);
        //если текущий режим игры в положении "играть" или паузы
        // отрисовываем остальные объекты игры
        if (state == State.PLAYING || state == State.PAUSE) {
            //если главный корабль не уничтожен
            if(!mainShip.isDestroyed()){
                //отрисовываем главный корабль
                mainShip.draw(batch);
            }
        //если текущий режим игры в положении "конец игры"
        } else if (state == State.GAME_OVER) {
            //отрисовываем сообщение "конец игры" и кнопку "новая игра"
            //отрисовываем объект анимации конца игры
            gameOver.draw(batch);
            //если активирован флаг ожидания запуска новой игры
            if(newGameButton.isShowing()){
                //отрисовываем объект кнопки NewGame
                newGameButton.draw(batch);
            }
        }
        //вызываем метод вывода информации об игре на экран
        printManager.draw(batch);
        //если установлен режим паузы
        if (state == State.PAUSE) {
            //отрисовываем сообщение "О программе"
            aboutMessage.draw(batch);
        }
        //отрисовываем объект кнопки меню
        buttonMenu.draw(batch);
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
        music.stop();
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

    /**
     * Геттер на состояние игры.
     * @return - состояние игры.
     */
    public State getState() {
        return state;
    }

    /**
     * Геттер на объект спрайта главного корабля.
     * @return - объект спрайта главного корабля
     */
    public MainShip getMainShip() {
        return mainShip;
    }

    /**
     * Геттер на пул кораблей противника.
     * @return - пул кораблей противника
     */
    public EnemyPool getEnemyPool() {
        return enemyPool;
    }
}
