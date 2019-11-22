package gdx.lessons.lesson5.classfiles.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс пулов для организации повторного использования созданных спрайтов.
 * @param <T>
 */
public abstract class SpritesPool<T extends Sprite> {
    //инициализируем пул(коллекцию) активных спрайтов
    protected final List<T> activeObjects = new ArrayList<>();
    //инициализируем пул(коллекцию) свободных(неактивных) спрайтов
    protected final List<T> freeObjects = new ArrayList<>();
    //Абстрактный метод создания нового объекта игры
    protected abstract T newObject();

    /**
     * Метод вызова объекта игры из пула, если есть свободный объект, или создания нового,
     * если нет свободных.
     * @return - объект игры
     */
    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        System.out.println("active/free : " + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    /**
     * Метод обновляет коллекцию активных объектов игры
     * @param delta - время между кадрами
     */
    public void updateActiveSprites(float delta) {
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    /**
     * Метод отрисовки коллекции активных объектов игры
     * @param batch - батчер
     */
    public void drawActiveSprites(SpriteBatch batch) {
        //проходим циклом коллекцию активных объектов игры
        for (Sprite sprite : activeObjects) {
            //если объект не помечен "уничтожен", отрисовываем
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    /**
     * Метод переноса помеченных на удаление объектов игры из пула активных объектов в пул свободных.
     */
    public void freeAllDestroyedActiveSprites() {
        //проходим циклом коллекцию активных объектов игры
        for (int i = 0; i < activeObjects.size(); i++) {
            //запоминаем текущий объект игры(спрайт)
            T sprite = activeObjects.get(i);
            //если объект помечен "уничтожен"
            if (sprite.isDestroyed()) {
                //удаляем объект из коллекции активных объектов
                activeObjects.remove(sprite);
                //и добавляем его в пул свободных объектов
                freeObjects.add(sprite);
                //декрементируем счетчик, чтобы не сбивать последовательность опроса
                i--;
                //устанавливаем состояние объекта - действующий
                sprite.flushDestroy();
                System.out.println("active/free : " + activeObjects.size() + "/" + freeObjects.size());
            }
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    /**
     * Метод удаляет из памяти коллекции объектов
     */
    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }
}
