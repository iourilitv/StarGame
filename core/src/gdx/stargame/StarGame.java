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
 * Изменить:
 * DONE 1. Убрать. Взрыв кораблей противника внизу экрана.
 * DONE 1а. Добавить. Накладывать штраф за пропущенный(не сбитый) корабль противника в
 * размере значения жизни проскочившего корабля противника.
 * 1b. Все настройки типов кораблей вынести в отдельные классы наследники Enemy. Это упростит
 *  код и избавит от необходимости в наличии переменных для доступа к константам корабля.
 * 2. Добавить кнопку вызов меню под главным кораблем. По нажатию - пауза игры.
 * 2a. Добавить меню игрового поля, вызываемого кнопкой п.2 с элеменами:
 *   новая игра; настройки; сохранить игру; загрузить игру; о программе; выйти.
 * 2b. Добавить сохранение настроек игры.
 * DONE 3. Добавить кораблям противника движение по x в заданных рандомно границах в режиме челнока.
 *  Если корабль противника вышел за экран, отменить стрельбу и движение по Y.
 * DONE 4. Добавить различия в громкости и тональности звука выстрела в зависимости от типа
 *  * корабля противника и звуковой эффект - звук воспроизводится слева или справа в зависимости
 *  * от расположения корабля на поле.
 * DONE 4a. Добавить тоже, что и в п.4 для взрывов.
 * DONE 5. Подсчет очков в отдельном классе. Очки как прибавляются, так и вычитаются.
 * DONE 5a. Добавить автоматический переход на новый уровень игры:
 *   на уровень выше - при превышении суммы набранных очков порога перехода на следующий уровень;
 *   и ниже - при уменьшении суммы до порогового значения перехода вниз.
 * DONE 5b. Добавить. Изменять настройки кораблей при изменении уровня игры в обе стороны:
 *   Восстановление значения жизни главного корабля при переходе на другой уровень;
 *   Перенастроить настройки активных кораблей противника.
 * DONE 5a. Добавить бонусы как в п.5b, но для главного корабля.
 * DONE 6. Вывод результата на экран.
 * 7. Добавить. Вывод остатка жизни главного корабля на сам корабль,
 *    а лучше только на корабли противника.
 * 8. Добавить. Ввод настроек. или скорректировать в ручную.
 * >>> Окончание игры.
 * DONE После уничтожения главного корабля все объекты и фон замораживаются, главный корабль
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
