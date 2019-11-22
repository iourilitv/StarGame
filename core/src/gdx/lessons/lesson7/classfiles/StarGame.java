package gdx.lessons.lesson7.classfiles;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson7.classfiles.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 7. Работа с камерой и звуками
 * Решение проблемы разных разрешений экранов. Добавление звуков и музыки.
 * На видео: анимация взрывов, ExplosionPool, обработка колизий(столкновений), подсчет повреждений и разрущение кораблей.
 * @author Alexey Kutepov
 *
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
