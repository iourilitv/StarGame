package gdx.lessons.lesson6.hw.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson6.hw.base.Ship;
import gdx.lessons.lesson6.hw.math.Rect;
import gdx.lessons.lesson6.hw.pool.BulletPool;

public class Enemy extends Ship {
    //***инициируем константы режима работы корабля***
    //режим когда корабль уже инициирован, но не начал действовать(не стреляет и за полем)
    private static final int BEGINNING_MODE = 0;
    //режим когда корабль уже начал действовать
    private static final int ACTIVE_MODE = 1;
    //режим когда корабль уже начал действовать
    private static final int DAMAGED_MODE = 2;//FIXME. Это на будущее. К нему добавить степень повреждения.

    //объявляем переменную режима работы корабля
    private int operationMode;

    public Enemy(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        //корабль начинает движение сразу после вызова
        pos.mulAdd(v, delta);
        //если включен начальный режим
        if(operationMode == BEGINNING_MODE){
            //если корабль уже наполовину показался из-за экрана
            if(pos.y < worldBounds.getTop()){
                //устанавливаем его скорость по заданной начальной скорости
                v.set(v0);
                //переводим корабль в активный режим
                operationMode = ACTIVE_MODE;
            }
        //если корабль уже не в начальном режиме
        } else {
            //начинаем стрельбу сразу после активации
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
            reloadTimer += delta;
        }

        //если корабль полностью вышел за нижний край поля
        if (getTop() < worldBounds.getBottom()) {
            //удаляем корабль
            destroy();
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
    }

    /**
     * Метод настройки параметров звука корабля
     * @param soundVolume - уровень громкости звука
     * @param soundPitch - уровень тона звука
     */
    public void setSound(
            float soundVolume,
            float soundPitch
    ) {
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
    }

    /**
     * Метод установки параметров для начального режима
     * @param vYBeg - скорость выплывания корабля из-за экрана в начале
     */
    public void setBeginningMode(float vYBeg){
        //переводим корабль в начальный режим
        operationMode = BEGINNING_MODE;
        //ускоряем корабль пока он выплывает из-за экрана в начале
        v.y = vYBeg;
        //устанавливаем таймер выстрелов на начальную позицию
        // нужно чтобы стрельба началась сразу после окончания начального режима корабля
        reloadTimer = reloadInterval;
    }

}
