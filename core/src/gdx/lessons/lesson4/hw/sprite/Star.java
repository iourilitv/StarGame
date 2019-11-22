package gdx.lessons.lesson4.hw.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gdx.lessons.lesson4.hw.base.Sprite;
import gdx.lessons.lesson4.hw.math.Rect;
import gdx.lessons.lesson4.hw.math.Rnd;

/**
 * Спрайт звезды.
 */

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.01f, 0.0065f));
        //генерируем случайные параметры вектора скорости звезды
        // (из-за этого выглядит как падающий снег)
        float vy = Rnd.nextFloat(-0.005f, -0.001f);
        float vx = Rnd.nextFloat(-0.0005f, 0.0005f);
        v.set(vx, vy);
    }

    /**
     * Конструктор для звезды с заданным общим константным вектором скорости.
     * Так выглядит действительно, как корабль летит на фоне звезд.
     * @param atlas - атлас звезды
     * @param starsVelocity - общий константный вектор скорости
     */
    Star(TextureAtlas atlas, Vector2 starsVelocity) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.01f, 0.0065f));
        //устанавливаем константные параметры вектора скорости звезды
        v.set(starsVelocity);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        //генерируем случайные координаты позиции звезды
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.add(v);
        //проверяем не вышли ли за границы мира и выбрасываем звезду с обратной стороны
        checkBounds();
    }

    private void checkBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

}
