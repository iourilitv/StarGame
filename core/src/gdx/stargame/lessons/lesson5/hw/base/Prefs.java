package gdx.stargame.lessons.lesson5.hw.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import gdx.stargame.lessons.lesson5.hw.screen.GameScreen;
import gdx.stargame.lessons.lesson5.hw.settings.ScreenSettings;

public class Prefs {
//    File file = new File("gdx/stargame/lessons/lesson5/hw/settings/ScreenSettings.java");
//    File file = new File("gdx.stargame.lessons.lesson5.hw.settings.ScreenSettings");
//    File file = new File(ScreenSettings.class.getCanonicalName());
//    File file = new File(ScreenSettings.class.getSimpleName());
//    File file = new File(ScreenSettings.class.getPackage().toString());
//    File file = new File(ScreenSettings.class.getPackage().toString());
    //инициализируем объект класса предпочтений
    // устанавливаем путь к файлу для хранения предпочтений пользователя и сохранения игр
    //работает подобно хэш-таблице Map
    private final Preferences prefs = Gdx.app.getPreferences("game_pause_condition");

    /**
     * Метод сохраняет текущее состояние скрина
     */
    public void saveScreenCondition(GameScreen screen){
//        Map<String, ?> map = screen.saveCondition();

        prefs.putFloat("mainShip.pos", -0.375f);
        //сохраняем данные игровой сессии пользователя
//        prefs.put(map);
        // вызов процесса сохранения изменений файла предпочтений
        prefs.flush();

        System.out.println("After prefs.get()=" + prefs.get());
        //After prefs.get()={mainShip.pos.x=0.0, mainShip.v.y=0.0, mainShip.pos.y=-0.375, mainShip.v0.y=0.0, mainShip.v0.x=0.5, mainShip.v.x=0.0}

//        System.out.println("map= " + map.toString());

//        System.out.println("ScreenSettings.values()= " +
//                Arrays.deepToString(ScreenSettings.values()));
    }

//    public void setStartPrefs() {
//
////        file.isDirectory();
////        System.out.println("file.listFiles()=" + Arrays.deepToString(file.listFiles()));
////        //file.listFiles()=null
//
////        Class clazz = Class.forName("ScreenSettings.java");
//
//
//
////        System.out.println("clazz.getDeclaredFields()= " +
////                Arrays.deepToString(clazz.getDeclaredFields()));
//    }

//    public void storeStartPrefs(){
//
////        System.out.println("file " + file + " .exists()= " + file.exists());
//        System.out.println("file " + file + " .mkdirs()= " + file.mkdirs());
//        //file package gdx.stargame.lessons.lesson5.hw.settings .mkdirs()= false
//    }


    public Preferences getPrefs() {
        return prefs;
    }
}
