package gdx.lessons.lesson2.classfiles;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson2.classfiles.screen.MenuScreen;

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
		//устанавливаем начальный экран приложения
		setScreen(new MenuScreen());
	}
}
