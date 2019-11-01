package gdx.stargame.lessons.lesson2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Вебинар.20191028_GB-Разработка_игры_на_LibGDX.Преподаватель: Алексей Кутепов
 * Урок 2. Базовые возможности фреймворка LibGDX
 * Работа с графикой. Векторная математика. Обработка логики игры.
 * @author Alexey Kutepov
 * Работа с векторами класса Vector2.
 */
public class StarGame1 extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		//создаем объекты векторов уже с заданными координатами вершины(концец со стрелкой)
		Vector2 v1 = new Vector2(3f, 5f);
		Vector2 v2 = new Vector2(6f, 8f);
		//к вектору v1 прибавляем прибавляем весктор v2
		v1.add(v2);
		System.out.println("v1.add(v2); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.add(v2); v1.x = 9.0 v1.y = 13.0

		//создаем объект нового вектора и присваиваем ему результат сложения векторов v1 и v2
		//фактически мы копируем v1 в новый вектор и прибавляем v2
		Vector2 v3 = v1.cpy().add(v2);
		System.out.println("v1.add(v2); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.add(v2); v1.x = 9.0 v1.y = 13.0
		System.out.println("v3.x = " + v3.x + " v3.y = " + v3.y);
		//v3.x = 15.0 v3.y = 21.0

		//устанавливаем новые значения координат векторов
		v1.set(5, 4);
		v2.set(2, 2);
		//из первого вектора вычитаем второй
		v1.sub(v2);
		System.out.println("v1.sub(v2); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.sub(v2); v1.x = 3.0 v1.y = 2.0

		//вычисляем длину вектора по теореме Пифагора
		System.out.println("v1.len(); = " + v1.len());
		//v1.len(); = 3.6055512

		v1.set(100, 10);
		//умножаем вектор на скаляр(все координаты умножаются на скаляр)
		v1.scl(0.9f);
		System.out.println("v1.scl(0.9f); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.scl(0.9f); v1.x = 90.0 v1.y = 9.0
		System.out.println("v1.len(); = " + v1.len());
		//v1.len(); = 90.44888

		v1.set(100, 10);
		//нормализуем вектор(приводим длину вектора к значению 1, сохраняя угол вектора)
		//фактически каждый компонент(координата) вектора делится на его длину
		System.out.println("***before***. v1.nor(); v1.x = " + v1.x + " v1.y = " + v1.y);
		//***before***. v1.nor(); v1.x = 100.0 v1.y = 10.0
		System.out.println("v1.len(); = " + v1.len());
		//v1.len(); = 100.49876
		v1.nor();
		System.out.println("v1.nor(); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.nor(); v1.x = 0.9950372 v1.y = 0.09950372
		System.out.println("v1.len(); = " + v1.len());
		//v1.len(); = 1.0

		//***Скалярное произведение векторов***
		//Расчет скалярного произведения двух векторов вычисляется по формуле:
		//v1(x1, y1) • v2(x2, y2) = x1 * x2 + y1 * y2.
		//устанавливаем новые координаты
		v1.set(1, 1);
		v2.set(-1, 1);
		//нормализуем вектора
		v1.nor();
		v2.nor();
		System.out.println("***before dot***. v1.nor(); v1.x = " + v1.x + " v1.y = " + v1.y + ". v2.x = " + v2.x + " v2.y = " + v2.y);
		//***before dot***. v1.nor(); v1.x = 0.70710677 v1.y = 0.70710677. v2.x = -0.70710677 v2.y = 0.70710677
		//вычисляем скалярное произведение векторов
		//Если векторы указывают в одном направлении, их скалярное произведение больше нуля,
		// векторы разнонаправлены - меньше нуля, перпендикулярны друг другу - равно нулю.
		// С помощью скалярного произведения векторов можно рассчитать, сколько их указывает в одном
		// направлении.
		// рассчитаем угол (в радианах) между двумя векторами через аркосинус.
		System.out.println("Math.acos(v1.dot(v2)) = " + Math.acos(v1.dot(v2)));
		//Math.acos(v1.dot(v2)) = 1.5707963267948966 - здесь это половина Пи, значит угол 90 град.

		//тоже самое, но через синус
//		System.out.println("Math.asin(v1.dot(v2)) = " + Math.asin(v1.dot(v2)));
		//Math.asin(v1.dot(v2)) = 0.0
//		System.out.println("v1.dot(v2); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.dot(v2); v1.x = 0.70710677 v1.y = 0.70710677
		System.out.println("v1.dot(v2) = " + v1.dot(v2));
		//v1.dot(v2) = 0.0
		System.out.println("v1.dot(v2); v1.x = " + v1.x + " v1.y = " + v1.y);
		//v1.dot(v2); v1.x = 0.70710677 v1.y = 0.70710677

		//v1.set(1, 1);
		//v2.set(-1, -1);
		//v1.dot(v2) = -0.99999994
		//v1.set(1, 1);
		//v2.set(1, 1);
		//v1.dot(v2) = 0.99999994
		//v1.set(100, 1);
		//v2.set(1, 1);
		//v1.dot(v2) = 0.71414214


		Vector2 v11 = new Vector2( 1 , 1 );
		Vector2 v22 = new Vector2( 2 , 1 );
		System.out.println( "dist: " + v22.cpy().sub(v11).len());
		//dist: 1.0 !!! Ошибка в методичке - 2.0 !!!
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
