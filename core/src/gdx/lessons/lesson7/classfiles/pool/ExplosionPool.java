package gdx.lessons.lesson7.classfiles.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson7.classfiles.base.SpritesPool;
import gdx.lessons.lesson7.classfiles.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound sound;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
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
