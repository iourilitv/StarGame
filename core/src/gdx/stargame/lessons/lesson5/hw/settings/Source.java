package gdx.stargame.lessons.lesson5.hw.settings;

/**
 * Класс перечислений ресурсов(файлы, картинки) в android/assets/
 */
public enum Source {
    //ссылки на файлы картинок фона
    PICTURE_1024X512("background-h-1024-512.png", 1024, 512),
    PICTURE_2048X1024("background-h-2048-1024.png", 2048, 1024),
    PICTURE_512X1024("background-v-512-1024.png", 512, 1024),
    PICTURE_1024X2048("background-v-1024-2048.png", 1024, 2048),
    GALAXY02_1800X2880("textures/galaxy02-1800x2880.jpg", 1800, 2880),
    GALAXY03_2610X3960("textures/galaxy03-2610x3960.jpg", 2610, 3960),

    //ссылка на файл фоновой музыки типа1
    SOUND_BACKGROUND_MUSIC1("sounds/music.mp3"),
    //ссылка на файл звука взрыва типа1
    SOUND_EXPLOSION1("sounds/explosion.wav"),
    //ссылка на файл звука выстрела корабля игрока снарядом типа 1
    SOUND_MAIN_SHIP_B1_SHOOT("sounds/bullet.wav"),
    //ссылка на файл звука выстрела корабля игрока лазером типа 1
    SOUND_MAIN_SHIP_L1_SHOOT("sounds/laser.wav"),
    //ссылка на файл звука выстрела корабля противника снарядом типа 1
    SOUND_ENEMY_B1_SHOOT("sounds/bullet.wav"),
    //ссылка на файл звука выстрела корабля противника лазером типа 1
    SOUND_ENEMY_L1_SHOOT("sounds/laser.wav"),
    ;

    private final String sourceName;//имя файла
    private int width;//ширина картинки
    private int height;//высота картинки

    Source(String sourceName) {
        this.sourceName = sourceName;
    }

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
