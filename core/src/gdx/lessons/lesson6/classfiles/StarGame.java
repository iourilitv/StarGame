package gdx.lessons.lesson6.classfiles;//ru.geekbrains

import com.badlogic.gdx.Game;

import gdx.lessons.lesson6.classfiles.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 6. Управление «экранами»
 * Разделение игры на экраны. Менеджер экранов. Пользовательский интерфейс.
 * Переходы между экранами, освобождение ресурсов.
 * На видео: Генерация трех типов кораблей противника. Движение и стрельба кораблей противника.
 * @author Alexey Kutepov
 *
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
