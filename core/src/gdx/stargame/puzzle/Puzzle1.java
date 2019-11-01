package gdx.stargame.puzzle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Puzzle1.
 * Screen filling with a single piece of the picture in normal order.
 */
public class Puzzle1 extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture imgBG;
	private TextureRegion region;

	//TODO temporarily.Added
	//ширина и высота куска
	private final int REGION_WIDTH = 128;
	private final int REGION_HEIGHT = 128;
	//ширина и высота текстуры(картинки)
	private final int TEXTURE_WIDTH = 2048 - REGION_WIDTH * 2;
	private final int TEXTURE_HEIGHT = 1024;

	//переменная сдвига картинки по горизонтали и по вертикали
	private float deltaX;
	private float deltaY;
	//задаем переменную скорости от частоты очищения экрана
	private float f;

	//рассчитываем размер текстуры в кусках
	private int regWidth = TEXTURE_WIDTH / REGION_WIDTH;
	private int regHeight = TEXTURE_HEIGHT / REGION_HEIGHT;

	@Override
	public void create () {
		//инициализируем партию(группу) картинок
		batch = new SpriteBatch();
		//инициируем объекты текстур на основе картинок в папке android/assets/
		imgBG = new Texture("background-h-2048-1024.png");
		//инициируем кусок полной линии текстуры
		region = new TextureRegion(imgBG, REGION_WIDTH * 7, REGION_HEIGHT * 4, REGION_WIDTH, REGION_HEIGHT);

	}

	@Override
	public void render () {
		//устанавливаем цвет и прозрачность фона при очищении экрана
		// (0,0,0 - черный, 1,1,1 - белый, 0f - полностью прозрачный, - 1f - полностью не прозрачный
		Gdx.gl.glClearColor(0, 0, 0, 1);

		//если заполнили весь экран кусками картинки
		if(f > regWidth * regHeight){
			//обнуляем счетчик
			f = 0f;
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//команда на обновление экрана
		}

		//привязываем переменную сдвига картинки по горизонтали ко времени обновления экрана
		deltaX = calculateDeltaX(f);

		//привязываем переменную сдвига картинки по вертикали к уходу картинки за экран по горизонтали
		deltaY = calculateDeltaY(f);

		//задаем переменную скорости от частоты очищения экрана
		f += Gdx.graphics.getDeltaTime();

		//TODO temporarily
		//System.out.println("f: " + f + ". deltaX: " + deltaX + ". deltaY: " + deltaY);

		//в блоке begin...end располагаются все прорисовки
		batch.begin();

		//добавляем движение картинки по горизонтали и вертикали
		batch.draw(region, deltaX, deltaY);

		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();
		imgBG.dispose();
	}

	//TODO temporarily.Added
	//метод привязывает переменную сдвига картинки по горизонтали к ширине картинки
	private float calculateDeltaX(float f){
		//вычисляем размер сдвига куска картинки в зависимости от округленного значения скорости
		float x = REGION_WIDTH * ((int)f % regWidth);

		//TODO temporarily
		//System.out.println("f: " + f + ". x: " + x);

		//если картинка ушла за экран по горизонтали, переходим в начало
		return x < TEXTURE_WIDTH/* - REGION_WIDTH*/? x : 0;
	}

	//Метод рассчитывает сдвиг картинки по вертикали
	// в зависимости от ухода картинки за экран по горизонтали
	private float calculateDeltaY(float f) {
		//рассчитываем коэффициент текущей строки
		int k = (int)f / regWidth;
		//если картинка ушла за экран по горизонтали, переходим на строку выше
		float y = REGION_HEIGHT * k;

		//TODO temporarily
		//System.out.println("f: " + f + ". k: " + k + ". y: " + y);

		//если картинка ушла за экран по вертикали, переходим в начало
		return y < TEXTURE_HEIGHT ? y : 0;//768 для desktop
	}
}

//int k = Math.round(f - (y + 1) * TEXTURE_WIDTH / REGION_WIDTH);
//f = 0, y = 0 * 16 //k = 0 - (0 +1) * 16 = -16 >> y=0
//f = 1.15, y = 0 * 16 //k = 1.15 - (0 +1) * 16 = -15 >> y=0
//f = 15.535(16.468), y = 0 * 16 //k = 15.535 - (0 +1) * 16 = -0.465 >> y=0
//f = 16.501, y = 0 * 16 //k = 16.501 - (0 +1) * 16 = 0.501 >> y=1
//f = 31.535(32.468), y = 1 * 16 //k = 31.535 - (1 +1) * 16 = -0.465 >> y=0
//f = 16.501, y = 0 * 16 //k = 16.501 - (0 +1) * 16 = 0.501 >> y=1

//		batch.draw(region, 0, 0); //лево-низ
//		batch.draw(region, TEXTURE_WIDTH - REGION_WIDTH, 0);//право-низ
//		batch.draw(region, 0, TEXTURE_HEIGHT - REGION_HEIGHT);//лево-верх
//		batch.draw(region, TEXTURE_WIDTH - REGION_WIDTH, TEXTURE_HEIGHT - REGION_HEIGHT);//право-верх