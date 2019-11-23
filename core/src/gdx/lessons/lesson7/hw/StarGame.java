package gdx.lessons.lesson7.hw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import gdx.lessons.lesson7.hw.base.BaseScreen;
import gdx.lessons.lesson7.hw.screen.MenuScreen;
import gdx.lessons.lesson7.hw.screen.GameScreen;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 7. Работа с камерой и звуками
 * Решение проблемы разных разрешений экранов. Добавление звуков и музыки.
 * На видео: анимация взрывов, ExplosionPool, обработка колизий(столкновений),
 * подсчет повреждений и разрущение кораблей.
 * @author Yuriy Litvinenko
 * Homework.
 * DONE 1. После уничтожения игрового корабля перестать обновлять пулы и убрать лишние объекты
 *    с экрана и реализовать надпись GameOver.
 *    Реализовано в измененной логике - после уничтожения главного корабля
 *    все объекты и фон замораживаются, главный корабль исчезает, оставшиеся недорисованними
 *    взрывы за мораживаются с статичной картинке, запускается музыка(зациклена) и появляется
 *    сообщение GameOver, которое через паузу начинает крутиться и уменьшаться. После исчезновения
 *    сообщения GameOver на его месте появляется кнопка NewGame. *
 * 2*. Реализовать кнопку NEW_GAME при нажатии на которую будет начинаться новая игра.
 *    Кнопку выводить вместе с надписью Game Over
 */
public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}

//	public void startNextGame(BaseScreen screen){
//
//        System.out.println("StarGame.startNextGame screen.getClass().getSimpleName()= " +
//                screen.getClass().getSimpleName());
//
////	    screen.dispose();
////        screen.hide();
//        setScreen(new GameScreen(this));
//    }
}
