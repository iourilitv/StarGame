package gdx.stargame.lessons.lesson4.hw;

import com.badlogic.gdx.Game;

import gdx.stargame.lessons.lesson4.hw.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 4. Оптимизация проекта
 * Оптимизация работы с памятью. Управление объектами через Emitter-классы.
 * Использование паттерна Object Pool. Использование атласов текстур.
 * Использование атласов текстур. Создание звезд на фоне.
 * Создание кнопок на скрине Menu. Переключение между скринами.
 * @author Yuriy Litvinenko
 * Homework.
 * DONE 1. Реализовать спрайт корабля
 * DONE 2. Разрезать текстуру корабля на 2 части
 * DONE 3*. Cделать управление кораблём с помощью тача и клавиатуры
 * Additionally.
 * DONE 1. Движение фона вверх/низ и подготовлен черновик для движения влево/право
 *    (пока движение фона по X считается от движения по Y - оборот по Y сдвигает X)
 * DONE 2. Вращение главного корабля по тачу в квадрат центра вращения на корабле.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
