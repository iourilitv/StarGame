package gdx.stargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

//import gdx.stargame.puzzle.Puzzle1;
//import gdx.stargame.puzzle.Puzzle2;
//import gdx.stargame.stargame.StarGame;
//import gdx.stargame.lessons.lesson1.StarGame;
//import gdx.stargame.lessons.lesson2.classfiles.StarGame1;
//import gdx.stargame.lessons.lesson2.classfiles.StarGame;
import gdx.stargame.lessons.lesson2.hw.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		//устанавливаем размеры окна приложения
//		config.width = 1024;
//		config.height = 512;
//		//запрещаем изменение размеров окна приложения
//		config.resizable = false;

		//config.fullscreen = true;

		new LwjglApplication(new StarGame(), config);

		//слишком большой размер картинки - не вмещается в экран
//		new LwjglApplication(new StarGame(), config);

//		new LwjglApplication(new Puzzle1(), config);
//		new LwjglApplication(new Puzzle2(), config);
	}
}
