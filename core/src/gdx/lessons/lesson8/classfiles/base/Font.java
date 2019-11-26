package gdx.lessons.lesson8.classfiles.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Класс организующий вывод текста с помощью BitMap.
 */
public class Font extends BitmapFont {
    /**
     * Упрощенный конструктор из BitmapFont.
     * @param fontFile - файл карты текстуры текста
     * @param imageFile - текстура текста
     */
    public Font(String fontFile, String imageFile) {
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);
        //применяем линейные фильтры сглаживания краев текста(пиксели как квадратики, появляющиеся
        // при увеличении и уменьшении текста
        getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * Метод изменения размера шрифта.
     * @param size - новый размер шрифта
     */
    public void setSize(float size) {
        getData().setScale(size / getCapHeight());
    }

    /**
     * Упрощенный метод отрисовки текста из метода draw BitmapFont.
     * @param batch - батч
     * @param str - строка текста
     * @param x - координата позиции текста по горизонтали(?не понятно какой точки текста?)
     * @param y - координата позиции текста по вертикали(?не понятно какой точки текста?)
     * @param halign - targetWidth - ширина иторгового текста
     * @return - wrap - обертка
     */
    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int halign) {
        return super.draw(batch, str, x, y, 0f, halign, false);
    }
}
