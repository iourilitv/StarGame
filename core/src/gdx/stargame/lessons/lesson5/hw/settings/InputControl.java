package gdx.stargame.lessons.lesson5.hw.settings;

import com.badlogic.gdx.Input;

/**
 * Класс констант кодов клавиш направлений движения
 */
public enum InputControl {
    //***контроль режимов игры***
    //сделать паузу в игре
    MUSIC_GAME_PAUSE_KEY(Input.Keys.P),
    MUSIC_GAME_RESUME_KEY(Input.Keys.R),

    FORWARD(Input.Keys.UP),//19
    BACK(Input.Keys.DOWN),//20
    LEFT(Input.Keys.LEFT),//21
    RIGHT(Input.Keys.RIGHT),//22

    //выключить музыку
    MUSIC_PLAY_PAUSE_KEY(Input.Keys.M),
    ;

    private int keyCode;

    InputControl(int keyCode) {
        this.keyCode = keyCode;
    }

    public int keyCode(){
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
