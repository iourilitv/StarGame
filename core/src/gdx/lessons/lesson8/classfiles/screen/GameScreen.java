package gdx.lessons.lesson8.classfiles.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import gdx.lessons.lesson8.classfiles.base.BaseScreen;
import gdx.lessons.lesson8.classfiles.base.Font;
import gdx.lessons.lesson8.classfiles.math.Rect;
import gdx.lessons.lesson8.classfiles.pool.BulletPool;
import gdx.lessons.lesson8.classfiles.pool.EnemyPool;
import gdx.lessons.lesson8.classfiles.pool.ExplosionPool;
import gdx.lessons.lesson8.classfiles.sprite.Background;
import gdx.lessons.lesson8.classfiles.sprite.Bullet;
import gdx.lessons.lesson8.classfiles.sprite.Enemy;
import gdx.lessons.lesson8.classfiles.sprite.GameOver;
import gdx.lessons.lesson8.classfiles.sprite.MainShip;
import gdx.lessons.lesson8.classfiles.sprite.Star;
import gdx.lessons.lesson8.classfiles.sprite.StartNewGame;
import gdx.lessons.lesson8.classfiles.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    //инициируем константы шаблонов текста для вывода на экран
    private static final String FRAGS = "Frags:";//количество сбитых врагов
    private static final String HP = "HP:";//значение здоровья главного корабля
    private static final String LEVEL = "Level:";//текущий уровень игры
    //инициируем перечисление для режимов игры: игра, пауза, конец игры.
    private enum State {PLAYING, PAUSE, GAME_OVER}
    //объявляем переменные для хранения текущего и предыдущего режимов игры
    private State state;
    private State prevState;

    private Texture bg;
    private TextureAtlas atlas;

    private Music music;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;
    //объявляем переменную для хранения режима конец игры
    private GameOver gameOver;
    //объявляем переменную для хранения режима начать новую игру
    private StartNewGame startNewGame;

    //объявляем переменную шрифта текста
    private Font font;
    //объявляем переменные готового текста для вывода на экран
    private StringBuilder sbFrags;//количество сбитых врагов
    private StringBuilder sbHp;//значение здоровья главного корабля
    private StringBuilder sbLevel;//текущий уровень игры
    //объявляем переменную для хранения номера предыдущего уровня игры(НЕ нужно?)
    private int prevLevel = 1;
    //объявляем переменную для хранения количества сбитых врагов
    private int frags;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, atlas, worldBounds);
        //инициируем переменную для хранения режима конец игры
        gameOver = new GameOver(atlas);
        //инициируем переменную для хранения режима начать новую игру
        startNewGame = new StartNewGame(atlas, this);
        music.setLooping(true);
        music.play();
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
        checkCollisions();
        freeAllDestroyed();
        draw();
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
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        //передаем мировую координатную сетку в классы окончания игры и начала новой игры
        // и устанавливаем размеры их объектов
        gameOver.resize(worldBounds);
        startNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
        mainShip.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        enemyEmitter.dispose();
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
            startNewGame.touchDown(touch, pointer);
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
            startNewGame.touchUp(touch, pointer);
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
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);

        //если текущий режим игры в положении "играть" обновляем остальные объекты игры
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }
        //если переходим на следующий уровень игры
        if (prevLevel < enemyEmitter.getLevel()) {
            //сохраняем текущий уровень игры
            prevLevel = enemyEmitter.getLevel();
            //добавляем бонусны к значению жизни главного корабля игрока
            mainShip.setHp(mainShip.getHp() + 10);
        }
    }

    /**
     * Метод обработки столкновений кораблей с другими объектами.
     */
    private void checkCollisions() {
        //если текущий режим игры не а положении "играть" ни чего не делаем
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                mainShip.damage(enemy.getDamage());
                enemy.destroy();
                //когда корабль противника уничтожен, инкрементируем количество сбитых кораблей
                frags++;
                //если при столкновении с кораблем противника уничтожен главный корабль
                if (mainShip.isDestroyed()) {
                    //устанавливаем текущий режим игры в положение "конец игры"
                    state = State.GAME_OVER;
                }
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    //если корабль противника уничтожен
                    if (enemy.isDestroyed()) {
                        //инкрементируем количество сбитых кораблей
                        frags++;
                    }
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
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
        explosionPool.freeAllDestroyedActiveSprites();
    }

    /**
     * Метод отрисовки объектов игрового поля.
     */
    private void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);

        //если текущий режим игры в положении "играть" отрисовываем остальные объекты игры
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        //если текущий режим игры в положении "конец игры"
        } else if (state == State.GAME_OVER) {
            //отрисовываем сообщение "конец игры" и кнопку "новая игра"
            gameOver.draw(batch);
            startNewGame.draw(batch);
        }
        //вызываем метод вывода информации об игре на экран
        printInfo();
        batch.end();
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
                sbLevel.append(LEVEL).append(enemyEmitter.getLevel()),
                //координаты позиции сообщения
                levelPosX, levelPosY,
                //выравниваем сообщение по правому краю позиции
                Align.right);
    }

}
