package gdx.stargame.lessons.lesson5.hw.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;

/**
 * Класс организует сохранение состояние игры пользователя
 */
public class Saver {

    //инициализируем объект класса предпочтений
    // устанавливаем путь к файлу для хранения предпочтений пользователя и сохранения игр
    //работает подобно хэш-таблице Map
//    private Preferences prefs = Gdx.app.getPreferences("game_pause_condition");
    private Preferences prefs;


    public Saver(String fileName) {
        this.prefs = Gdx.app.getPreferences(fileName);
    }

    /**
     * Метод сохранения переменной типа Integer
     * @param key - имя ключа переменной
     * @param integer - переменная типа Integer
     */
    public void saveInteger(String key, Integer integer){
        prefs.putInteger(key, integer);
        prefs.flush();
    }

    /**
     * Метод восстановления переменной типа Integer с затиранием предыдущего значения
     * @param key - имя ключа переменной
     * @return - переменную типа Integer
     */
    public int restoreInteger(String key){
        int integer = 0;
        if(prefs.contains(key)){
            integer = prefs.getInteger(key);

            System.out.println("integer= " + integer);
        }
        return integer;
    }

    /**
     * Метод восстановления одной переменной типа Float с затиранием предыдущего значения
     * @param key - имя ключа переменной
     * @return - переменную типа Integer
     */
    public float restoreSingleFloat(String key){
        float fl = 0f;
        if(prefs.contains(key)){
            fl = prefs.getFloat(key);

            System.out.println("fl= " + fl);
        }
        return fl;
    }

    /**
     * Метод сохранения вектора с затиранием предыдущего значения
     * @param key - имя ключа вектора
     * @param vector2 - объект вектора
     */
    public void saveFloat(String key, Vector2 vector2){
        prefs.putFloat(key + ".x", vector2.x);
        prefs.putFloat(key + ".y", vector2.y);
        prefs.flush();
    }

    /**
     * Метод восстановления вектора
     * @param key - имя вектора
     * @return - объект вектора
     */
    public Vector2 restoreFloat(String key){
        float x, y;
        if(prefs.contains(key + ".x")){
            x = prefs.getFloat(key + ".x");
        } else {
            x = 0;
        }
        if (prefs.contains(key + ".x")) {
            y = prefs.getFloat(key + ".y");
        } else {
            y = 0;
        }
        return new Vector2(x, y);
    }

    public Preferences getPrefs() {
        return prefs;
    }
}
