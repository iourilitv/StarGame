package gdx.lessons.lesson8.classfiles.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson8.classfiles.math.Rect;
import gdx.lessons.lesson8.classfiles.math.Rnd;
import gdx.lessons.lesson8.classfiles.pool.EnemyPool;
import gdx.lessons.lesson8.classfiles.sprite.Enemy;

public class EnemyEmitter {

    private static final float SMALL_BULLET_HEIGHT = 0.01f;
    private static final float SMALL_BULLET_VY = -0.3f;
    private static final int SMALL_BULLET_DAMAGE = 1;
    private static final float SMALL_RELOAD_INTERVAL = 3f;
    private static final float SMALL_HEIGHT = 0.1f;
    private static final int SMALL_HP = 1;

    private static final float MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float MIDDLE_BULLET_VY = -0.25f;
    private static final int MIDDLE_BULLET_DAMAGE = 5;
    private static final float MIDDLE_RELOAD_INTERVAL = 4f;
    private static final float MIDDLE_HEIGHT = 0.15f;
    private static final int MIDDLE_HP = 5;

    private static final float BIG_BULLET_HEIGHT = 0.03f;
    private static final float BIG_BULLET_VY = -0.2f;
    private static final int BIG_BULLET_DAMAGE = 10;
    private static final float BIG_RELOAD_INTERVAL = 2f;
    private static final float BIG_HEIGHT = 0.2f;
    private static final int BIG_HP = 10;

    private float generateInterval = 4f;
    private float generateTimer;

    private EnemyPool enemyPool;
    private Rect worldBounds;

    private TextureRegion[] enemySmallRegions;
    private TextureRegion[] enemyMiddleRegions;
    private TextureRegion[] enemyBigRegions;
    private TextureRegion bulletRegion;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMiddleV = new Vector2(0, -0.03f);
    private Vector2 enemyBigV = new Vector2(0, -0.005f);
    //инициируем перенную текущего уровня игры(?должен быть в GameScreen?)
    private int level = 1;

    private Sound sound;

    public EnemyEmitter(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMiddleRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    /**
     * Метод вызова кораблей противника.
     * @param delta - период времени между кадрами
     * @param frags - количество сбитых врагов(?Заменить на level?)
     */
    public void generate(float delta, int frags) {
        //подсчитываем переход на новый уровень игры(от количества сбитых кораблей противника)
        //(?перенести в отдельный класс для подсчета очков?)
        level = frags / 10 + 1;
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        SMALL_BULLET_HEIGHT,
                        SMALL_BULLET_VY,
                        //увеличиваем наносимый урон в зависимости от уровня игры
                        SMALL_BULLET_DAMAGE * level,
                        SMALL_RELOAD_INTERVAL,
                        sound,
                        SMALL_HEIGHT,
                        SMALL_HP
                );
            } else if (type < 0.8f) {
                enemy.set(
                        enemyMiddleRegions,
                        enemyMiddleV,
                        bulletRegion,
                        MIDDLE_BULLET_HEIGHT,
                        MIDDLE_BULLET_VY,
                        //увеличиваем наносимый урон в зависимости от уровня игры
                        MIDDLE_BULLET_DAMAGE * level,
                        MIDDLE_RELOAD_INTERVAL,
                        sound,
                        MIDDLE_HEIGHT,
                        MIDDLE_HP
                );
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        BIG_BULLET_HEIGHT,
                        BIG_BULLET_VY,
                        //увеличиваем наносимый урон в зависимости от уровня игры
                        BIG_BULLET_DAMAGE * level,
                        BIG_RELOAD_INTERVAL,
                        sound,
                        BIG_HEIGHT,
                        BIG_HP
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public void dispose() {
        sound.dispose();
    }

    /**
     * Геттер для уровня игры.
     * @return - номер уровня игры
     */
    public int getLevel() {
        return level;
    }

    /**
     * Сеттер для уровня игры.
     * @param level - новый номер уровня игры
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
