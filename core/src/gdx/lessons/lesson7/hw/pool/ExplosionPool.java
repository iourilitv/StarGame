package gdx.lessons.lesson7.hw.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson7.hw.base.SpritesPool;
import gdx.lessons.lesson7.hw.sprite.Explosion;

/**
 * Класс для организации пула объектов-взрывов.
 */
public class ExplosionPool extends SpritesPool<Explosion> {
    //принимаем объект атласа
    private TextureAtlas atlas;
    //объявляем объект звука взрыва
    private Sound sound;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
        //инициируем объект звука взрыва
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, sound);
    }

    @Override
    public void dispose() {
        sound.dispose();
        super.dispose();
    }
}
