package gdx.lessons.lesson8.classfiles;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson8.classfiles.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
