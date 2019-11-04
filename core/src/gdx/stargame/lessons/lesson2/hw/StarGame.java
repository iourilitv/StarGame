package gdx.stargame.lessons.lesson2.hw;

import com.badlogic.gdx.Game;

import gdx.stargame.lessons.lesson2.hw.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 2. Базовые возможности фреймворка LibGDX
 * Работа с графикой. Векторная математика. Обработка логики игры.
 * @author Yuriy Litvinenko
 * Homework.
 * DONE 2.Реализовать движение логотипа badlogic (можно свою картинку вставить)
 * при нажатии клавиши мыши (touchDown) в точку нажатия на экране и остановку в данной точке.
 * 3.Реализовать управление логотипом с помощью клавиш со стрелками на клавиатуре***
 * (дополнительное задание, не обязательное к выполнению).
 */
public class StarGame extends Game {

	@Override
	public void create() {
		//устанавливаем начальный экран приложения
		setScreen(new MenuScreen());
	}
}
