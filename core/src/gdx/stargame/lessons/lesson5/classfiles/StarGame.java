package gdx.stargame.lessons.lesson5.classfiles;

import com.badlogic.gdx.Game;

import gdx.stargame.lessons.lesson5.classfiles.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 5. Доработка игровой логики
 * Система уровней, добавление новых видов врагов, боссы, powerups, улучшение корабля.
 * @author Alexey Kutepov
 * Движение корабля по кнопкам и касанию, создание объектов снарядов,
 *  применение пулов объектов игры и стрельба своего корабля.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
