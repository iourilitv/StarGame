package gdx.lessons.lesson7.hw;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson7.hw.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 7. Работа с камерой и звуками
 * Решение проблемы разных разрешений экранов. Добавление звуков и музыки.
 * На видео: анимация взрывов, ExplosionPool, обработка колизий(столкновений),
 * подсчет повреждений и разрущение кораблей.
 * @author Yuriy Litvinenko
 * Homework.
 * 1. После уничтожения игрового корабля перестать обновлять пулы и убрать лишние объекты
 *    с экрана и реализовать надпись GameOver
 * 2*. Реализовать кнопку NEW_GAME при нажатии на которую будет начинаться новая игра.
 *    Кнопку выводить вместе с надписью Game Over
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
