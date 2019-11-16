package gdx.stargame.lessons.lesson5.classfiles;

import com.badlogic.gdx.Game;

import gdx.stargame.lessons.lesson5.classfiles.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
