package gdx.stargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

//import gdx.stargame.puzzle.Puzzle1;
import gdx.stargame.puzzle.Puzzle2;
//import gdx.stargame.stargame.StarGame;
//import gdx.stargame.lessons.lesson1.StarGame;
//import gdx.stargame.lessons.lesson2.classfiles.StarGame1;
//import gdx.stargame.lessons.lesson2.classfiles.StarGame;
import gdx.stargame.lessons.lesson2.hw.StarGame;
import gdx.stargame.puzzle.puzzle3.Puzzle3;
import gdx.stargame.puzzle.puzzle3.constants.ScreenSettings;
import gdx.stargame.puzzle.puzzle3.constants.Source;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//создаем объект картинки
		//Source picture = Source.PICTURE_1024X512;
        Source picture = ScreenSettings.CURRENT_PICTURE.getPicture();
        //устанавливаем размеры окна приложения
		config.width = picture.width();//1024
		config.height = picture.height();//512;

//		System.out.println("picture.name()= " + picture.name());
//		System.out.println("config.width= " + config.width);
//		System.out.println("config.height= " + config.height);


//		//запрещаем изменение размеров окна приложения
        config.resizable = false;

//		config.fullscreen = true;

//		new LwjglApplication(new StarGame(), config);
        //слишком большой размер картинки - не вмещается в экран
//		new LwjglApplication(new Puzzle2(), config);

        new LwjglApplication(new Puzzle3(DesktopLauncher.class.getSimpleName(), picture), config);
    }

	/*public static DesktopLauncher getInstance() {
		return desktopLauncher;
	}*/

	/*public static LwjglApplicationConfiguration getConfig() {
		return DesktopLauncher.getInstance().config;
	}*/

	/*public LwjglApplicationConfiguration getConfig() {
		return DesktopLauncher.getInstance().config;
	}*/
}

//package gdx.stargame.desktop;
//
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//
//import gdx.stargame.puzzle.Puzzle1;
//import gdx.stargame.puzzle.Puzzle2;
//import gdx.stargame.stargame.StarGame;
//
//public class DesktopLauncher {
//	public static void main (String[] arg) {
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
////		//устанавливаем размеры окна приложения
////		config.width = 1024;
////		config.height = 512;
////		//запрещаем изменение размеров окна приложения
////		config.resizable = false;
//
//		//config.fullscreen = true;
//
//		//слишком большой размер картинки - не вмещается в экран
//		new LwjglApplication(new StarGame(), config);
//
////		new LwjglApplication(new Puzzle1(), config);
////		new LwjglApplication(new Puzzle2(), config);
//	}
//}


//private static final DesktopLauncher desktopLauncher = new DesktopLauncher();
//	private LwjglApplicationConfiguration config;

	/*public DesktopLauncher() {
		this.config = new LwjglApplicationConfiguration();
	}*/

//
//		//передадим в объект приложения тип загрузчика
//		//DesktopLauncher.getInstance().config.title = "Desktop";
//		//new LwjglApplication(new Puzzle3(DesktopLauncher.getInstance().config.title));
//
//		//new LwjglApplication(new Puzzle3(DesktopLauncher.getInstance().config));
////		new LwjglApplication(new Puzzle3(DesktopLauncher.getInstance().config.title,
////				DesktopLauncher.getInstance()));