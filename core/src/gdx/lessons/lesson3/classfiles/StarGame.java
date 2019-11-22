package gdx.lessons.lesson3.classfiles;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson3.classfiles.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 3. Разработка «каркаса» игры
 * Создание каркаса проекта: игрок, астероиды, пули и их взаимодействие.
 * @author Alexey Kutepov
 * Матрица координатной сетки, приведение к единой координатной сетке,
 * создание шаблона игрока Sprite.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
