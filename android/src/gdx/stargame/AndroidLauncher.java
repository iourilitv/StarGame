package gdx.stargame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import gdx.stargame.lessons.lesson3.hw.StarGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarGame(), config);
	}
}

//***for .lessons.lesson3.classfiles.***

	/*protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarGame(), config);
	}*/

//***for .puzzle.puzzle3.***
//import gdx.stargame.puzzle.Puzzle1;
//import gdx.stargame.puzzle.Puzzle2;
		/*protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Puzzle2(), config);
//        initialize(new Puzzle1(), config);
	}*/

//***for .stargame.StarGame***
//import gdx.stargame.stargame.StarGame;
	/*protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarGame(), config);
	}*/