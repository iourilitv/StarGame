package gdx.puzzle.puzzle3.constants;

import com.badlogic.gdx.Input;

public enum KeyControl {
    QUIT_GAME(Input.Keys.Q),
    GO_TO_MENU(Input.Keys.M),
    GO_BACK(Input.Keys.B),
    NEW_GAME(Input.Keys.N),
    CONTINUE_GAME(Input.Keys.C)
    ;

    private int keyCode;

    KeyControl(int keyCode) {
        this.keyCode = keyCode;
    }

    public int keyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
