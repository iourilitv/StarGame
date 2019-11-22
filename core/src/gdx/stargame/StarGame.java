package gdx.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 1. Установка и настройка инструментов разработки
 * Установка и настройка инструментов разработки. Создание шаблонного
 * проекта и его запуск. Структура проекта. Обзор модулей LibGDX.
 * @author Yuriy Litvinenko
 * Домашнее задание:
 * Установить все необходимые инструменты для разработки (перечислены на вебинаре и в методичке)
 * и собрать проект.
 * Разместить проект на GitHub
 * Подобрать картинку для фона, сделать её размеры кратными степени 2 (например 2048x1024 или 2048x2048)
 * и добавить фон в игру
 *
 */
public class StarGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Texture imgBG;
	private TextureRegion region;

	//TODO temporarily.Added
	//ширина и высота текстуры(картинки)
	private final int TEXTURE_WIDTH = 1792;//1920;//2048;//512;
	private final int TEXTURE_HEIGHT = 1024;
	//ширина и высота куска
	private final int REGION_WIDTH = 128;
	private final int REGION_HEIGHT = 128;
	//переменная сдвига картинки по горизонтали и по вертикали
	private float deltaX;
	private float deltaY;

	@Override
	public void create () {
		//инициализируем партию(группу) картинок
		batch = new SpriteBatch();
		//инициируем объекты текстур на обснове картинок в папке android/assets/
		img = new Texture("badlogic.jpg");
		//imgBG = new Texture("background-v-512-1024.png");
		imgBG = new Texture("background-h-2048-1024.png");
		//инициируем объект вырезанного куска текстуры (в пикселях)
		// x=32, y=32 - координаты левого верхнего! угла куска на текстуре
		//width=64, height=64 - размеры куска
		region = new TextureRegion(img, 32, 32, REGION_WIDTH, REGION_HEIGHT);

	}

	@Override
	public void render () {
		//устанавливаем цвет и прозрачность фона при очищении экрана
		// (0,0,0 - черный, 1,1,1 - белый, 0f - полностью прозрачный, - 1f - полностью не прозрачный
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//команда на обновление экрана

		//TODO temporarily.Added
		//привязываем переменную сдвига картинки по горизонтали ко времени обновления экрана
		deltaX = calculateDeltaX(deltaX);
		//привязываем переменную сдвига картинки по вертикали к уходу картинки за экран по горизонтали
		deltaY = calculateDeltaY(deltaX, deltaY);

		//в блоке begin...end располагаются все прорисовки
		batch.begin();
		//устанавливаем цвет всех картинок после этой стороки и до следующего setColor
		batch.setColor(0f, 1, 0, 1f);
		//**выводим на экран картинки(последняя будет поверх предыдущих)**
		//выводим картинку с начальными координатами в левом нижнем углу экрана
		batch.draw(imgBG, 0, 0);
		//выводим картинку с начальными координатами в левом нижнем углу экрана и
		// с определенными размерами(можно сжимать и растягивать)
//		batch.draw(imgBG, 0, 0, 256, 256);//
		batch.setColor(1f, 1f, 1f, 1f);
		batch.draw(img, 200, 200);
		batch.setColor(1f, 0f, 0f, 1f);
		//batch.draw(region, 000, 000);

		//TODO temporarily.Added
		//добавляем движение картинки по горизонтали и вертикали
		batch.draw(region, deltaX, deltaY);

		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	//TODO temporarily.Added
	//метод привязывает переменную сдвига картинки по горизонтали ко времени обновления экрана
	private float calculateDeltaX(float x){
		x += Gdx.graphics.getDeltaTime() * 300;
		//если картинка ушла за экран по горизонтали, переходим в начало
		return x < TEXTURE_WIDTH - REGION_WIDTH ? x : 0;
	}

	//TODO temporarily.Added
	//Метод рассчитывает сдвиг картинки по вертикали
	// в зависимости от ухода картинки за экран по горизонтали
	private float calculateDeltaY(float x, float y) {
		if(x == 0){
			//если картинка ушла за экран по горизонтали, переходим на строку выше
			y += REGION_HEIGHT;
		}
		//если картинка ушла за экран по вертикали, переходим в начало
		return y < TEXTURE_HEIGHT ? y : 0;//768 для desktop
	}
}
