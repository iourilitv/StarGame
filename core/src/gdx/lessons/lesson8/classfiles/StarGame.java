package gdx.lessons.lesson8.classfiles;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson8.classfiles.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 8. Финальная доработка проекта
 * Доработка всех компонентов. Сборка APK-файла. Тестирование на устройстве.
 * На видео: Вывод текста с помощью BitMap(сборщик runnable-hiero.jar). Режим паузы. Подсчет отчков. Уровни игры. Сборка APK-файла.
 * @author Alexey Kutepov
 *
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
