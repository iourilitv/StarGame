package gdx.stargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import gdx.stargame.Puzzle;
import gdx.stargame.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//устанавливаем размеры окна приложения
		config.width = 1024;
		config.height = 512;
		//запрещаем изменение размеров окна приложения
		config.resizable = false;
		new LwjglApplication(new StarGame(), config);
//		new LwjglApplication(new Puzzle(), config);
	}
}
