package gdx.stargame;

import com.badlogic.gdx.Game;

import gdx.stargame.screen.MenuScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Итоговая курсовая работа - проект игры шутера StarGame.
 * @author Yuriy Litvinenko
 * Описание проекта.
 * После запуска приложения повляется экран меню с двумя кнопками Play и Exit на статичном фоне.
 * Нажатие на кнопку Exit закрывает приложение.
 * Нажатие на Play - закрывает экран меню и открывает игровой экран.
 * >>> На игровом экране.
 * Движение фона вверх/низ и подготовлен черновик для движения влево/право
 * (пока движение фона по X считается от движения по Y - оборот по Y сдвигает X).
 * Управление кораблём игрока(главный корабль) - с помощью тача и клавиатуры.
 * В приложении ротация всех спрайтов(корабли, снаряды, взрывы) организавана через пулы, что
 * не забивает память компьютера удаленными объектами.
 * Главный корабль сразу начинает стрелять снарядами в режиме автострельбы.
 * Сверху экрана рандомно генерируются корабли противника трех типов, которые выплывают
 * из-за экрана и также сразу начинают стрелять только вперед.
 * Попадание снарядов в корабль(с обеих сторон) меняет картинку корабля на доли секунды и
 * наносит урон жизни корабля.
 * Когда значение жизни корабля становится не больше нуля, запускается анимация взрыва корабля
 * и корабль убирается с экрана и возвращается в пул.
 * ДОБАВИТЬ:
 * 1. DONE Убрать. Взрыв кораблей противника внизу экрана.
 * 1а. Накладывать штраф за пропущенный(не сбитый) корабль противника в размере значения жизни
 *  корабля противника.
 * 2. Кнопку вызов меню под главным кораблем. По нажатию - пауза игры.
 * 3. Кораблям противника движение по x в заданных рандомно границах.
 * 4. Объемный звук стрельбы и взрывов.
 * 5. Подсчет очков.
 * 6. Вывод результата на экран.
 * 7. Вывод остатка жизни главного корабля на сам корабль.
 * 8. Ввод настроек. или скорректировать в ручную.
 * >>> Окончание игры.
 * После уничтожения главного корабля все объекты и фон замораживаются, главный корабль
 *  исчезает, оставшиеся недорисованними взрывы замораживаются в статичной картинке,
 *  запускается музыка(зациклена) и появляется сообщение GameOver, которое через паузу
 *  начинает крутиться и уменьшаться. После исчезновения сообщения GameOver на его месте
 *  появляется кнопка NewGame, при нажатии на которую начинается новая игра.
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
