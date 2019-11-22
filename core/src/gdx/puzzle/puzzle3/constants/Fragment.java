package gdx.puzzle.puzzle3.constants;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fragment {
    //принимаем экземпляр картинки
    private int index;
    //принимаем кусок картинки
    private TextureRegion region;

    public Fragment(int index, TextureRegion region) {
        this.index = index;
        this.region = region;
    }

    public int getIndex() {
        return index;
    }

    public TextureRegion getRegion() {
        return region;
    }

    //ширина фрагмента
    int getWidth(){
        return region.getRegionWidth();
    }

    //высота фрагмента
    int getHeight(){
        return region.getRegionHeight();
    }

    //значение сдвига фрагмента картинки по горизонтали относительно ноля
    public int getX(){
        return region.getRegionX();
    }

    //значение сдвига фрагмента картинки по вертикали относительно ноля
    //FIXME добавить в конструктор параметр private Texture picture;
    public int getY(){
        return Source.PICTURE_1024X512.height() - region.getRegionHeight() - region.getRegionY();
    }

    @Override
    public String toString() {
        return "Fragment{" +
                "index=" + index +
                '}';
    }
}
