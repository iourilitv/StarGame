package gdx.lessons.lesson8.classfiles.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gdx.lessons.lesson8.classfiles.base.Sprite;
import gdx.lessons.lesson8.classfiles.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setTop(0.08f);
    }
}
