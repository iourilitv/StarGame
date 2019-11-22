package gdx.lessons.lesson3.hw;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson3.hw.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 3. Разработка «каркаса» игры
 * Создание каркаса проекта: игрок, астероиды, пули и их взаимодействие.
 * На видео: матрица координатной сетки, приведение к единой координатной сетке,
 * создание шаблона игрока Sprite.
 * @author Yuriy Litvinenko
 * Homework.
 * DONE 1. Разобраться с темой урока.
 * DONE 2. Адаптировать ДЗ урока2 к новой архитектуре проекта. Желательно всю логику,
 * 		которая касается обработки логотипа по максимуму разместить в классе Logo.
 * Additionally.
 * DONE 1. Реализовать движение спрайта через интерфейс Mobility.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
