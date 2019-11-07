package gdx.stargame.puzzle.puzzle3.constants;

/**
 * Класс перечислений ресурсов(файлы, картинки) в android/assets/
 */
public enum Source {
    PICTURE_1024X512("background-h-1024-512.png", 1024, 512),
    PICTURE_2048X1024("background-h-2048-1024.png", 2048, 1024),
    PICTURE_512X1024("background-v-512-1024.png", 512, 1024),
    PICTURE_1024X2048("background-v-1024-2048.png", 1024, 2048);

    private final String sourceName;//имя файла
    private final int width;//ширина картинки
    private final int height;//высота картинки

    Source(String sourceName, int width, int height) {
        this.sourceName = sourceName;
        this.width = width;
        this.height = height;
    }

    public String sourceName() {
        return sourceName;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
