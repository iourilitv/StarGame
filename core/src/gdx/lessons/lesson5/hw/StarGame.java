package gdx.lessons.lesson5.hw;

import com.badlogic.gdx.Game;

import gdx.lessons.lesson5.hw.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 5. Доработка игровой логики
 * Система уровней, добавление новых видов врагов, боссы, powerups, улучшение корабля.
 * Движение корабля по кнопкам и касанию, создание объектов снарядов,
 *  применение пулов объектов игры и стрельба своего корабля.
 * @author Yuriy Litvinenko
 * Homework.
 * DONE 1. Разобраться с классами Sound(http://www.libgdx.ru/2013/10/sound-effects.html) и
 * 		Music(http://www.libgdx.ru/2013/10/streaming-music.html) (можно мне вопросы задавать)
 * 		и реализовать фоновую музыку
 * DONE 2. ***Реализовать 1 вид вражеских кораблей, используя пулл спрайтов
 * 		(корабль пролетает по экрану и дистроится после вылета за границы экрана)
 * Additionally.
 * DONE 1. Замораживать картинку по нажатию клавиши "пауза"
 * DONE 2. Сохранять настройки игры по свертыванию(hide) окна
 * DONE 3. Добавить кораблям противника движение по x в заданных рандомно границах.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
