package gdx.stargame.lessons.lesson2.hw;

/**
 * Класс констант кодов клавиш направлений движения
 *///READY
public enum Direction {
    FORWARD(19),
    BACK(20),
    LEFT(21),
    RIGHT(22);

    private int keyCode;

    Direction(int keyCode) {
        this.keyCode = keyCode;
    }

    public int keyCode(){
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
