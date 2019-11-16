package gdx.stargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

//import gdx.stargame.lessons.lesson4.hw.StarGame;
import gdx.stargame.lessons.lesson5.classfiles.StarGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 700;
        config.width = 500;
        config.resizable = false;
        new LwjglApplication(new StarGame(), config);
    }

}

//***for .lessons.lesson3.classfiles.***
/*public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.height = 600;
    config.width = 600;
    config.resizable = false;
    new LwjglApplication(new StarGame(), config);
}*/

//***for .puzzle.puzzle3.***
//import gdx.stargame.puzzle.puzzle3.Puzzle3;
//import gdx.stargame.puzzle.puzzle3.constants.ScreenSettings;
//import gdx.stargame.puzzle.puzzle3.constants.Source;
    /*public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//создаем объект картинки
        Source picture = ScreenSettings.CURRENT_PICTURE.getPicture();
        //устанавливаем размеры окна приложения
		config.width = picture.width();//1024
		config.height = picture.height();//512;

		//запрещаем изменение размеров окна приложения
        config.resizable = false;

        new LwjglApplication(new Puzzle3(DesktopLauncher.class.getSimpleName(), picture), config);

    }*/

//***for .lessons.lesson2.hw.***
//import gdx.stargame.lessons.lesson2.hw.StarGame;
/*public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.height = 600;
    config.width = 600;
    config.resizable = false;
    new LwjglApplication(new StarGame(), config);
}*/



//		config.fullscreen = true;

//		new LwjglApplication(new StarGame(), config);
//слишком большой размер картинки - не вмещается в экран
//		new LwjglApplication(new Puzzle2(), config);