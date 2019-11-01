package gdx.stargame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import gdx.stargame.puzzle.Puzzle1;
import gdx.stargame.puzzle.Puzzle2;
import gdx.stargame.stargame.StarGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarGame(), config);
//        initialize(new Puzzle1(), config);
//		initialize(new Puzzle2(), config);
	}
}
