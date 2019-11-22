package gdx.lessons.lesson1;

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
 * @author Alexey Kutepov
 */
public class StarGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		region = new TextureRegion(img, 20, 30, 50, 70);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.855f, 0.71f, 0.69f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(1f, 1f, 1f, 1f);
		batch.draw(img, 100, 50, 100, 100);
		batch.draw(region, 400, 400);
		batch.setColor(0.528f, 0.928f, 0.328f, 0.9f);
		batch.draw(img, 130, 80);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
