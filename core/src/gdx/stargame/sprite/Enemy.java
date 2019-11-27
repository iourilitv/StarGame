package gdx.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.stargame.base.ScoreCounter;
import gdx.stargame.base.Ship;
import gdx.stargame.math.Rect;
import gdx.stargame.pool.BulletPool;
import gdx.stargame.pool.ExplosionPool;

public class Enemy extends Ship {
    //инициируем перечисление состояний корабля противника
    private enum State {
        DESCENT, //выплывание из-за экрана
        FIGHT //бой(активный режим)
    }
    //объявляем объект состояния корабля противника
    private State state;
    //инициируем ветор скорости выплывание корабля противника из-за экрана
    private Vector2 descentV = new Vector2(0, -0.15f);

    //объявляем переменную для объекта счетчика очков
    private ScoreCounter scoreCounter = ScoreCounter.getInstance();

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        //принимаем объект пула вызрывов
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //проверяем на какой стадии сейчас корабль
        switch (state) {
            //если еще выплывает из-за экрана
            case DESCENT:
                //сбрасываем таймер перезагрузки снарядов, чтобы не начал стрелять пока не выплывет
                reloadTimer = 0f;
                //если корабль уже полностью выплыл на экран игрового поля
                if (getTop() <= worldBounds.getTop()) {
                    //устанавливаем ему начальную скорость в активном режиме
                    v.set(v0);
                    //переключаем состояние корабля противника в режим боя
                    state = State.FIGHT;
                    //устанавливаем таймер перезагрузки равным интервалу перезагрузки снарядов,
                    //чтобы выстрелить как только выплывет на экран
                    reloadTimer = reloadInterval;
                }
                break;
            //если уже в активном режиме
            case FIGHT:
                //если корабль противника полностью скрылся за нижней границей мира
                if (getTop() < worldBounds.getBottom()) {
                    //помечаем корабль противника как уничтоженный, но без взрыва
                    destroyed = true;

                    //вычитаем очки из набранных очков игрока в размере значения
                    // жизни  ускользнувшего корабля противника помноженого на текущий уровень игры
//                    updateScore();//FIXME Передать сюда класс подсчета очков
                    scoreCounter.checkNextLevel(- getConstHp() * scoreCounter.getLevel());

                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            Sound sound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.sound = sound;
        setHeightProportion(height);
        this.hp = hp;
        //запоминаем значение константы жизни корабля
        this.constHp = hp;
        //устанавливаем скорость выплывания корабля противника пока не выплыл из-за экрана
        this.v.set(descentV);
        //устанавливаем состояние выплывания для корабля противника
        state = State.DESCENT;
    }

    /**
     * Метод меняет параметры настройки корабля противника в зависимости от текущего уровня игры
     * @param level - текущий уровень игры
     * @param bulletVY - скорость снарядов корабля по вертикали
     * @param damage - пореждение наносимое главному кораблю при столкновении с кораблем противника
     * @param reloadInterval - период между выпуском снарядов корабля противника
     * @param hp - уровень жизни(здоровья) корабля
     */
    public void changeShipSettingsByLevel(
                        int level, float bulletVY, int damage,
                        float reloadInterval, int hp
                        ){
        //увеличиваем параметры корабля в зависимости от текущего уровня игры
        this.bulletV.set(0, bulletVY * level);
        this.damage = damage * level;
        this.reloadInterval = reloadInterval * level;
        this.hp = constHp * level;
        //запоминаем новое значение константы жизни корабля
        this.constHp = hp;
    }

    /** //FIXME
     * Метод меняет настройки корабля противника в зависимости от текущего уровня игры
     * @param level - текущий уровень игры
     */
    public void changeShipSettingsByLevel(int level){
        //увеличиваем параметры корабля в зависимости от текущего уровня игры
        this.bulletV.set(0, bulletV.y * level);
        this.damage = damage * level;
        this.reloadInterval = reloadInterval * level;
        //запоминаем новое значение константы жизни корабля
        this.constHp = constHp * level;
        this.hp = constHp;
    }

    /**
     * Метод обработки столкновений корабля противника со снарядом.
     * @param bullet - снаряд(здесь по логике - главного корабля)
     * @return - true - есть столкновение(здесь - попадание снаряда в корабль противника)
     */
    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        //чтобы снаряд визуально долетал до центра корабля противника
                        || bullet.getTop() < pos.y
        );
    }
}
