package gdx.puzzle.puzzle3.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gdx.puzzle.puzzle3.base.Act;
import gdx.puzzle.puzzle3.base.BaseScreen;
import gdx.puzzle.puzzle3.constants.Fragment;
import gdx.puzzle.puzzle3.constants.ScreenSettings;

/**
 * Puzzle3.
 * Screen filling with pieces of the picture in random order.
 */
public class PuzzleScreen extends BaseScreen {
	private Texture picture;

	//ширина и высота куска
	private final int REGION_WIDTH = ScreenSettings.FRAGMENT_DIMENSIONS.getFragmentWidth();//128;
	private final int REGION_HEIGHT = ScreenSettings.FRAGMENT_DIMENSIONS.getFragmentHeight();//128;
	//ширина и высота текстуры(картинки)
    private final int TEXTURE_WIDTH = ScreenSettings.CURRENT_PICTURE.getPicture().width();
    private final int TEXTURE_HEIGHT = ScreenSettings.CURRENT_PICTURE.getPicture().height();

	//задаем переменную скорости от частоты очищения экрана
	private float f;
	//массив кусков картинки
    private Fragment[][] fragments;
	//коллекцию индексов оставшихся кусков картинки
	private List<Fragment> currentFragments;

	//рассчитываем размер текстуры в кусках
	private int regWidth = TEXTURE_WIDTH / REGION_WIDTH;
	private int regHeight = TEXTURE_HEIGHT / REGION_HEIGHT;

    public PuzzleScreen(Act act) {
        super(act);
        fragments = new Fragment[regHeight][regWidth];
        //Инициируем таблицу индексов фрагментов картинки
        currentFragments = new ArrayList<>();
    }

	@Override
	public void show () {
		super.show();
		//инициируем объекты текстур на основе картинок в папке android/assets/
        picture = new Texture(ScreenSettings.CURRENT_PICTURE.getPicture().sourceName());
		//инициируем массив кусков картинки
		splitUpPicture();
		//инициируем сообщество индексов оставшихся кусков картинки
        shuffleListOfFragments();
	}

	//Метод наполняет массив и коллекцию оставшихся фрагментов кусками порезанной картинки
    private void splitUpPicture() {
        //наполняем массив кусков картинки
        for (int y = 0; y < regHeight; y++) {
            for (int x = 0; x < regWidth; x++) {
                //рассчитываем индекс фрагмента
                int index = y * regWidth + x;
                fragments[y][x] = new Fragment(index,
                        new TextureRegion(picture, x * REGION_HEIGHT, y * REGION_WIDTH,
                        REGION_WIDTH, REGION_HEIGHT));
                //добавляем фрагмент в коллекцию оставщихся фрагментов
                currentFragments.add(fragments[y][x]);
            }
        }
    }

	//Метод инициирует сообщество индексов оставшихся кусков картинки
	private void shuffleListOfFragments() {
		Random random1 = new Random();
        Random random2 = new Random();
		Fragment temp;
		int size = currentFragments.size();//regWidth * regHeight;
        //перемешиваем элементы в таблице оставшихся фрагментов случайным образом
		for (int i = 0; i < size; i++) {
            //генерируем временные индексы элементов коллекции случайным образом
		    int i1 = random1.nextInt(size);
            int i2 = random2.nextInt(size);
            //запоминаем первый фрагмент
		    temp = currentFragments.get(i1);
		    //записываем в место первого фрагмента ссылку на второй фрагмент
            currentFragments.set(i1, currentFragments.get(i2));
            //записываем в место второго фрагмента ссылку на первый фрагмент
            currentFragments.set(i2, temp);
		}
	}

	@Override
	public void render (float delta) {
		super.render(delta);

		//устанавливаем цвет и прозрачность фона при очищении экрана
		// (0,0,0 - черный, 1,1,1 - белый, 0f - полностью прозрачный, - 1f - полностью не прозрачный
		Gdx.gl.glClearColor(0, 0, 0, 1);
        //команда на обновление экрана
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //выводим на экран кусок картинки
        for (int i = 0; i < (int)f; i++) {
            batch.draw(currentFragments.get(i).getRegion(),
                    currentFragments.get(i).getX(),
                    currentFragments.get(i).getY());
        }
        batch.end();

        //если заполнили весь экран кусками картинки
        if(f >= regWidth * regHeight){
            //продолжаем выводить всю коллекцию фрагментов
            f = regWidth * regHeight;
        } else{
            //задаем переменную скорости от частоты очищения экрана
            f += delta;
        }

	}

	@Override
	public void dispose () {
		super.dispose();

		batch.dispose();
		picture.dispose();
	}

}