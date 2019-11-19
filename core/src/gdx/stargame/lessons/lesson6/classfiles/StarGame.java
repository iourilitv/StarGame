package gdx.stargame.lessons.lesson6.classfiles;//ru.geekbrains

import com.badlogic.gdx.Game;

import gdx.stargame.lessons.lesson6.classfiles.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
