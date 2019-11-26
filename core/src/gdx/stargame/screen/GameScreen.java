package gdx.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import gdx.stargame.base.Font;
import gdx.stargame.sprite.BackgroundGalaxy;
import gdx.stargame.StarGame;
import gdx.stargame.base.BaseScreen;
import gdx.stargame.math.Rect;
import gdx.stargame.pool.BulletPool;
import gdx.stargame.pool.EnemyPool;
import gdx.stargame.pool.ExplosionPool;
import gdx.stargame.sprite.Bullet;
import gdx.stargame.sprite.ButtonNewGame;
import gdx.stargame.sprite.Enemy;
import gdx.stargame.sprite.GameOver;
import gdx.stargame.sprite.MainShip;
import gdx.stargame.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    //инициируем перечисление для режимов игры: игра, пауза, конец игры.
    private enum State {PLAYING, PAUSE, GAME_OVER}
    //инициируем константы шаблонов текста для вывода на экран
    private static final String FRAGS = "Frags:";//количество сбитых врагов
    private static final String HP = "HP:";//значение здоровья главного корабля
    private static final String LEVEL = "Level:";//текущий уровень игры

    //объявляем тектуру картинки фона с галактикой
    private Texture bgGalaxy;
    private TextureAtlas atlas;
    private Music music;
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
//    //объявляем переменную для хранения режима конец игры
//    private GameOver gameOver;//FIXME
    //объявляем спрайт для сообщения "конец игры"
    private GameOver gameOver;//FIXME mine
//    //объявляем переменную для хранения режима начать новую игру
//    private StartNewGame startNewGame;//FIXME
    //объявляем регион для картинки "новая игра"
    private ButtonNewGame newGameButton;//FIXME mine

//    //объявляем переменную для хранения статуса пауза игры
//    private boolean gamePaused;//FIXME mine
//    //объявляем переменную для хранения статуса конца игры
//    private boolean gameFinished;//FIXME mine

    //объявляем переменную шрифта текста
    private Font font;
    //объявляем переменные готового текста для вывода на экран
    private StringBuilder sbFrags;//количество сбитых врагов
    private StringBuilder sbHp;//значение здоровья главного корабля
    private StringBuilder sbLevel;//текущий уровень игры

    //инициируем перенную текущего уровня игры(?должен быть в GameScreen?)
    private int level = 1;
    //инициируем переменную для хранения номера предыдущего уровня игры(НЕ нужно?)//FIXME
    private int prevLevel = 1;
    //объявляем переменную для хранения количества сбитых врагов
    private int frags;

    public GameScreen(StarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
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

//        //инициируем переменную для хранения режима конец игры
//        gameOver = new GameOver(atlas);//FIXME
        //инициируем спрайт для анимации "конец игры"
        gameOver = new GameOver(atlas.findRegion("message_game_over"));
//        //инициируем переменную для хранения режима начать новую игру
//        startNewGame = new StartNewGame(atlas, this);//FIXME
        //инициируем объект кнопки "новая игра"
        newGameButton = new ButtonNewGame(atlas, this);

        //инициируем переменную шрифта текста. В параметрах fontFile, imageFile
        font = new Font("font/font.fnt", "font/font.png");
        //устанавливаем размер шрифта в мировых координатах
        font.setSize(0.02f);
        //инициируем переменные готового текста для вывода на экран
        sbFrags = new StringBuilder();//количество сбитых врагов
        sbHp = new StringBuilder();//значение здоровья главного корабля
        sbLevel = new StringBuilder();//текущий уровень игры
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
        //передаем мировую координатную сетку в классы окончания игры и начала новой игры
        // и устанавливаем размеры их объектов
        gameOver.resize(worldBounds);
//        startNewGame.resize(worldBounds);//FIXME
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
    }

    @Override
    public void dispose() {
        bgGalaxy.dispose();
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

    /**
     * Метод отрабатывает касание экрана или клика мыши.
     * @param touch - вектор координаты точки касания
     * @param pointer - номер пальца или клика
     * @return - не используется
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        //если текущий режим игры установлен режим "играть"
        if (state == State.PLAYING) {
            //передаем касание только в класс главного корабля
            mainShip.touchDown(touch, pointer);
            //если текущий режим игры установлен режим "конец игры"
        } else if (state == State.GAME_OVER) {
            //передаем касание в класс началь новую игру
//            startNewGame.touchDown(touch, pointer);//FIXME
//        //вызываем метод отработки нажатия кнопки NewGame
        newGameButton.touchDown(touch, pointer);//FIXME mine
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
        //если текущий режим игры установлен режим "играть"
        if (state == State.PLAYING) {
            //передаем касание только в класс главного корабля
            mainShip.touchUp(touch, pointer);
            //если текущий режим игры установлен режим "конец игры"
        } else if (state == State.GAME_OVER) {
            //передаем касание в класс началь новую игру
//            startNewGame.touchUp(touch, pointer);//FIXME
//        //вызываем метод отработки отпускания нажатия кнопки NewGame
        newGameButton.touchUp(touch, pointer);//FIXME mine
        }
        return false;
    }

    /**
     * Метод организует режим начала новой игры.
     */
    public void startNewGame() {
        //устанавливаем текущий режим игры в положение "играть"
        state = State.PLAYING;
        //устанавливаем переменную предыдущего уровня игры в начальное состояние
        prevLevel = 1;
        //сбрасываем счетчик сбитых врагов
        frags = 0;
        //задаем начальные параметры главному кораблю
        mainShip.startNewGame(worldBounds);//FIXME

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }

    /**
     * Метод отработки изменений в объектах игры.
     * @param delta - период времени между кадрами(1/60 сек)
     */
//    private void update(float delta) {
//        //FIXME
////        //если текущий режим игры в положении "играть" обновляем все объекты игры
////        if (state == State.PLAYING) {
////            mainShip.update(delta);
////            bulletPool.updateActiveSprites(delta);
////            enemyPool.updateActiveSprites(delta);
////            enemyEmitter.generate(delta, frags);
////        }
////        //если переходим на следующий уровень игры
////        if (prevLevel < enemyEmitter.getLevel()) {
////            //сохраняем текущий уровень игры
////            prevLevel = enemyEmitter.getLevel();
////            //добавляем бонусны к значению жизни главного корабля игрока
////            mainShip.setHp(mainShip.getHp() + 10);
////        }
//
//        //если игра поставлена на паузе
//        if(gamePaused) {
//            //выходим, нивего не обновляя
//            return;
//        }
//        //если игра окончена
//        if (gameFinished){
//            //запускаем метод обновления анимации окончания игры  и выходим
//            updateGameOverAnimation(delta);
//            return;
//        }
//        //если игра не на паузе или главный корабль еще живой, обновляем все элементы
//        if(!mainShip.isDestroyed()) {
//            //вызываем методы обновления фона со звездами
//            background.update(delta);
//            mainShip.update(delta);
//            bulletPool.updateActiveSprites(delta);
//            enemyPool.updateActiveSprites(delta);
//            enemyEmitter.generate(delta);
//            //обновляем пул взрывов
//            explosionPool.updateActiveSprites(delta);
//        }
//        //если игра на паузе или окончена
//        else {
//            //устанавливаем состояние конец игры
//            gameFinished = true;
//        }
//    }
    private void update(float delta) {
        //FIXME just check
        //если текущий режим игры в положении "играть" обновляем все объекты игры
        if (state == State.PLAYING) {
            //вызываем методы обновления фона со звездами
            background.update(delta);
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);//FIXME add level
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

        //FIXME не надо?
        //если переходим на следующий уровень игры
        if (prevLevel < level) {
            //сохраняем текущий уровень игры
            prevLevel = level;
            //добавляем бонусны к значению жизни главного корабля игрока
            mainShip.setHp(mainShip.getHp() + 10);
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
                //вызываем метод уничтожения корабля противника(такая игровая логика)
                enemy.destroy();

                //когда корабль противника уничтожен, инкрементируем количество сбитых кораблей
                frags++;//FIXME
                //если при столкновении с кораблем противника уничтожен главный корабль
                if (mainShip.isDestroyed()) {
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
                    //вызываем метод расчета повреждения корабля противника
                    enemy.damage(bullet.getDamage());
                    //вызываем метод уничтожения снаряда
                    bullet.destroy();

                    //если корабль противника уничтожен
                    if (enemy.isDestroyed()) {
                        //инкрементируем количество сбитых кораблей
                        frags++;//FIXME
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
                //вызываем метод расчета повреждения главного корабля
                mainShip.damage(bullet.getDamage());
                //вызываем метод уничтожения снаряда
                bullet.destroy();

                //FIXME just check
                //если при попадании снаряда уничтожен главный корабль
                if (mainShip.isDestroyed()) {
                    //устанавливаем текущий режим игры в положение "конец игры"
                    state = State.GAME_OVER;
                }
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        //вызываем метод переноса удаленных объектов взрывов из коллекции активных в свободные
        explosionPool.freeAllDestroyedActiveSprites();
    }

    /**
     * Метод отрисовки объектов игрового поля.
     */
//    private void draw() {
//        Gdx.gl.glClearColor(1, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        //вызываем методы прорисовки у каждого спрайта
//        background.draw(batch);
//        bulletPool.drawActiveSprites(batch);
//        enemyPool.drawActiveSprites(batch);
//        //отрисовываем объекты в пуле взрывов
//        explosionPool.drawActiveSprites(batch);
//
//        //FIXME
////        //если текущий режим игры в положении "играть" отрисовываем остальные объекты игры
////        if (state == State.PLAYING) {
////            mainShip.draw(batch);
////            bulletPool.drawActiveSprites(batch);
////            enemyPool.drawActiveSprites(batch);
////            //если текущий режим игры в положении "конец игры"
////        } else if (state == State.GAME_OVER) {
////            //отрисовываем сообщение "конец игры" и кнопку "новая игра"
////            gameOver.draw(batch);
////            startNewGame.draw(batch);
////        }
//
//        //если игра окончена
//        if(gameFinished) {
//            //отрисовываем объект анимации конца игры
//            gameOver.draw(batch);
//            //если активирован флаг ожидания запуска новой игры
//            if(newGameButton.isShowing()){
//                //отрисовываем объект кнопки NewGame
//                newGameButton.draw(batch);
//            }
//        //если игра еще не окончена
//        } else {
//            //отрисовываем главный корабль
//            mainShip.draw(batch);
//        }
//
//        //вызываем метод вывода информации об игре на экран
//        printInfo();
//        batch.end();
//    }
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

        //FIXME just check
        //если текущий режим игры в положении "играть" отрисовываем остальные объекты игры
        if (state == State.PLAYING) {
            //отрисовываем главный корабль
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
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
        printInfo();
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

    /**
     * Метод вывода информации об игре на экран
     */
    private void printInfo() {
        //сбрасываем итоговую строку сообщения о подбитых кораблях противника
        sbFrags.setLength(0);
        //устанавливаем координаты позиции итоговой строки сообщения
        // о подбитых кораблях противника
        float fragsPosX = worldBounds.getLeft() + 0.01f;//с отступом от левого края игрового поля
        float fragsPosY = worldBounds.getTop() - 0.01f;//с отступом от верха игрового поля
        //вызываем метод отрисовки шрифта текста сообщения о сбитых кораблях противника
        font.draw(batch,
                //добавляем в строку шаблон сообщения и количество сбитых кораблей противника
                sbFrags.append(FRAGS).append(frags),
                //координаты позиции сообщения(по умолчанию выравнивание - по левому краю)
                fragsPosX, fragsPosY);
        //сбрасываем итоговую строку сообщения о размере жизни главного корабля
        sbHp.setLength(0);
        //устанавливаем координаты позиции итоговой строки сообщения о жизни главного корабля
        float hpPosX = worldBounds.pos.x;//по середине ширины игрового экрана
        float hpPosY = worldBounds.getTop() - 0.01f;//с отступом от верха игрового поля
        //вызываем метод отрисовки шрифта текста о размере жизни главного корабля
        font.draw(batch,
                //добавляем в строку шаблон сообщения и размер жизни главного корабля
                sbHp.append(HP).append(mainShip.getHp()),
                //координаты позиции сообщения
                hpPosX, hpPosY,
                //выравниваем сообщение по центру позиции
                Align.center);
        //сбрасываем итоговую строку сообщения о текущем уровне игры
        sbLevel.setLength(0);
        //устанавливаем координаты позиции итоговой строки сообщения о текущем уровне игры
        float levelPosX = worldBounds.getRight() - 0.01f;
        float levelPosY = worldBounds.getTop() - 0.01f;
        //вызываем метод отрисовки шрифта текста о текущем уровне игры
        font.draw(batch,
                //добавляем в строку шаблон сообщения и номер текущего уровня игры
                sbLevel.append(LEVEL).append(level),
                //координаты позиции сообщения
                levelPosX, levelPosY,
                //выравниваем сообщение по правому краю позиции
                Align.right);
    }
}