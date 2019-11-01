package gdx.stargame.lessons.lesson2;

import com.badlogic.gdx.Game;

import gdx.stargame.lessons.lesson2.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 2. Базовые возможности фреймворка LibGDX
 * Работа с графикой. Векторная математика. Обработка логики игры.
 * @author Alexey Kutepov
 * Применение расширения от Game.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
