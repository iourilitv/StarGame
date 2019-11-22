package gdx.lessons.lesson4.classfiles;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson4.classfiles.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 4. Оптимизация проекта
 * Оптимизация работы с памятью. Управление объектами через Emitter-классы.
 * Использование паттерна Object Pool. Использование атласов текстур.
 * @author Alexey Kutepov
 * Использование атласов текстур. Создание звезд на фоне.
 * Создание кнопок на скрине Menu. Переключение между скринами.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
