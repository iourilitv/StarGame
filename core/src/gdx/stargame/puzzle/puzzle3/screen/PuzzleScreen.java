package gdx.stargame.puzzle.puzzle3.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gdx.stargame.puzzle.puzzle3.Puzzle3;
import gdx.stargame.puzzle.puzzle3.base.Act;
import gdx.stargame.puzzle.puzzle3.base.BaseScreen;
import gdx.stargame.puzzle.puzzle3.constants.Fragment;
import gdx.stargame.puzzle.puzzle3.constants.ScreenSettings;

/** NOT RELEASED!
 * Puzzle3.
 * Screen filling with pieces of the picture in random order.
 */
public class PuzzleScreen extends BaseScreen {
	private Texture picture;
	//private TextureRegion region;

	//ширина и высота куска
	private final int REGION_WIDTH = ScreenSettings.FRAGMENT_DIMENSIONS.getFragmentWidth();//128;
	private final int REGION_HEIGHT = ScreenSettings.FRAGMENT_DIMENSIONS.getFragmentHeight();//128;
	//ширина и высота текстуры(картинки)
	//private final int TEXTURE_WIDTH = game.getPicture().width();//2048 - REGION_WIDTH * 2;
	//private final int TEXTURE_HEIGHT = game.getPicture().height();//1024;
    private final int TEXTURE_WIDTH = ScreenSettings.CURRENT_PICTURE.getPicture().width();
    private final int TEXTURE_HEIGHT = ScreenSettings.CURRENT_PICTURE.getPicture().height();
    //константа периоди обновления экрана
    //private final float RENDER_PERIOD = 1f;

	//переменная сдвига картинки по горизонтали и по вертикали
	//private float deltaX;
	//private float deltaY;
	//задаем переменную скорости от частоты очищения экрана
	private float f;
	//массив кусков картинки
	//private TextureRegion[][] regions;
    private Fragment[][] fragments;
	//коллекцию индексов оставшихся кусков картинки
	private List<Fragment> currentFragments;

	//рассчитываем размер текстуры в кусках
	private int regWidth = TEXTURE_WIDTH / REGION_WIDTH;
	private int regHeight = TEXTURE_HEIGHT / REGION_HEIGHT;

	/*public PuzzleScreen(Puzzle3 game) {
		super(game);
        fragments = new Fragment[regHeight][regWidth];
        //Инициируем таблицу индексов фрагментов картинки
        currentFragments = new ArrayList<>();

//		System.out.println("PuzzleScreen.");
//		System.out.println("Gdx.graphics.getWidth()=" + Gdx.graphics.getWidth());
//		System.out.println("Gdx.graphics.getHeight()=" + Gdx.graphics.getHeight());
//
//		System.out.println("game.getPicture().width()=" + game.getPicture().width());
//		System.out.println("game.getPicture().height()=" + game.getPicture().height());


//		if(game.getLauncher().equals("Desktop")){
//			System.out.println("game.getClass().getClassLoader()= " +
//					game.getClass().getClassLoader().getClass().getSimpleName());
//		}
		//this.pause();
		//this.hide();
		//game.setScreen(new MenuScreen(game));
	}*/
    public PuzzleScreen(Act act) {
        super(act);
        fragments = new Fragment[regHeight][regWidth];
        //Инициируем таблицу индексов фрагментов картинки
        currentFragments = new ArrayList<>();
    }

	@Override
	public void show () {
		super.show();
		//инициализируем партию(группу) картинок
		//batch = new SpriteBatch();
		//инициируем объекты текстур на основе картинок в папке android/assets/
		//picture = new Texture(game.getPicture().sourceName());
        picture = new Texture(ScreenSettings.CURRENT_PICTURE.getPicture().sourceName());
		//инициируем массив кусков картинки
		splitUpPicture();
		//инициируем сообщество индексов оставшихся кусков картинки
        shuffleListOfFragments();

//		regions = new TextureRegion[regHeight][regWidth];
//		//наполняем массив кусков картинки
//		for (int y = 0; y < regHeight; y++) {
//			for (int x = 0; x < regWidth; x++) {
//				regions[y][x] = new TextureRegion(picture, x * REGION_HEIGHT, y * REGION_WIDTH,
//						REGION_WIDTH, REGION_HEIGHT);
//			}
//		}
	}

	//Метод наполняет массив и коллекцию оставшихся фрагментов кусками порезанной картинки
	/*private void splitUpPicture() {
		regions = new TextureRegion[regHeight][regWidth];
		//наполняем массив кусков картинки
		for (int y = 0; y < regHeight; y++) {
			for (int x = 0; x < regWidth; x++) {
				regions[y][x] = new TextureRegion(picture, x * REGION_HEIGHT, y * REGION_WIDTH,
						REGION_WIDTH, REGION_HEIGHT);
			}
		}
	}*/
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

        //TODO temporarily
        //System.out.println("Shuffled currentFragments= " + currentFragments);
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
        /*for (Fragment fragment: currentFragments) {
            batch.draw(fragment.getRegion(), fragment.getX(), fragment.getY());

        }*/
        for (int i = 0; i < (int)f; i++) {
            batch.draw(currentFragments.get(i).getRegion(),
                    currentFragments.get(i).getX(),
                    currentFragments.get(i).getY());
        }

        batch.end();
        //задаем переменную скорости от частоты очищения экрана
        if(f >= regWidth * regHeight){
            f = regWidth * regHeight;//RENDER_PERIOD * - 1;//0f;
        } else{
            f += delta;
        }

        //если заполнили весь экран кусками картинки
		/*if(f > regWidth * regHeight){
			//TODO temporarily
			System.out.println("f: " + f + ". deltaX: " + deltaX + ". deltaY: " + deltaY);
			//обнуляем счетчик
			f = 0f;
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//команда на обновление экрана
		}*/

		//***подготовка кусков картинки***
//		//привязываем переменную сдвига картинки по горизонтали ко времени обновления экрана
//		deltaX = calculateDeltaX(f);
//		//привязываем переменную сдвига картинки по вертикали к уходу картинки за экран по горизонтали
//		deltaY = calculateDeltaY(f);


        //присваиваем текущей переменной кусок картинки из коллекции оставшихся фрагментов

        //задаем переменную скорости от частоты очищения экрана
//        f += Gdx.graphics.getDeltaTime();
        //f += delta;
        //выводим с задержкой на экран все элементы коллекции оставшихся фрагментов
        /*if (f > RENDER_PERIOD && currentFragments.size() !=0){
            //удаляя элемент присваиваем текущей переменной кусок картинки из коллекции
            region = currentFragments.remove(0).getRegion();
            //устанавливаем переменную сдвига картинки относительно начала координат по горизонтали
            deltaX = region.getRegionX();
            //устанавливаем переменную сдвига картинки относительно начала координат по вертикали
            //TEXTURE_HEIGHT -  REGION_HEIGHT - приведение координаты фрагмента к текущей сетке
            deltaY = TEXTURE_HEIGHT - REGION_HEIGHT - region.getRegionY();
            //в блоке begin...end располагаются все прорисовки
            batch.begin();
            //выводим на экран кусок картинки
            batch.draw(region, deltaX, deltaY);
            batch.end();
            //обнуляем счетчик
            f = 0f;
        }*/

        //TODO temporarily
        //System.out.println("f: " + f + ". deltaX: " + deltaX + ". deltaY: " + deltaY);

//		//в блоке begin...end располагаются все прорисовки
//		batch.begin();
//		//присваиваем текущей переменной кусок картинки из массива
//		region = regions[regHeight - 1 - (int)deltaY / REGION_HEIGHT][(int)deltaX / REGION_WIDTH];
//        //выводим на экран кусок картинки
//        batch.draw(region, deltaX, deltaY);
//		batch.end();

	}

	@Override
	public void dispose () {
		super.dispose();

		batch.dispose();
		picture.dispose();
	}

	/*//метод привязывает переменную сдвига картинки по горизонтали к ширине картинки
	private float calculateDeltaX(float f){
		//вычисляем размер сдвига куска картинки в зависимости от округленного значения скорости
		float x = REGION_WIDTH * ((int)f % regWidth);

		//TODO temporarily
		//System.out.println("f: " + f + ". x: " + x);

		//если картинка ушла за экран по горизонтали, переходим в начало
		return x < TEXTURE_WIDTH/* - REGION_WIDTH/? x : 0;
	}*/

	/*//Метод рассчитывает сдвиг картинки по вертикали
	// в зависимости от ухода картинки за экран по горизонтали
	private float calculateDeltaY(float f) {
		//рассчитываем коэффициент текущей строки
		int k = (int)f / regWidth;
		//если картинка ушла за экран по горизонтали, переходим на строку выше
		//TODO version 1. Started from the down-left corner
		float y = REGION_HEIGHT * k;
		//TODO version 1. Started from the up-left corner
//		float y = TEXTURE_HEIGHT - REGION_HEIGHT * (k + 1);

		//TODO temporarily
		//System.out.println("f: " + f + ". k: " + k + ". y: " + y);

		//если картинка ушла за экран по вертикали, переходим в начало
		//TODO version 1. Started from the down-left corner
		return y < TEXTURE_HEIGHT ? y : 0;
		//TODO version 1. Started from the up-left corner
//		return y >= 0 ? y : TEXTURE_HEIGHT - REGION_HEIGHT;
	}*/
}

//int k = Math.round(f - (y + 1) * TEXTURE_WIDTH / REGION_WIDTH);
//f = 0, y = 0 * 16 //k = 0 - (0 +1) * 16 = -16 >> y=0
//f = 1.15, y = 0 * 16 //k = 1.15 - (0 +1) * 16 = -15 >> y=0
//f = 15.535(16.468), y = 0 * 16 //k = 15.535 - (0 +1) * 16 = -0.465 >> y=0
//f = 16.501, y = 0 * 16 //k = 16.501 - (0 +1) * 16 = 0.501 >> y=1
//f = 31.535(32.468), y = 1 * 16 //k = 31.535 - (1 +1) * 16 = -0.465 >> y=0
//f = 16.501, y = 0 * 16 //k = 16.501 - (0 +1) * 16 = 0.501 >> y=1

//		batch.draw(region, 0, 0);
//		batch.draw(region, TEXTURE_WIDTH - REGION_WIDTH, 0);
//		batch.draw(region, 0, TEXTURE_HEIGHT - REGION_HEIGHT);
//		batch.draw(region, TEXTURE_WIDTH - REGION_WIDTH, TEXTURE_HEIGHT - REGION_HEIGHT);