package gdx.stargame.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import gdx.stargame.utils.Font;
import gdx.stargame.math.Rect;
import gdx.stargame.screens.GameScreen;
import gdx.stargame.sprites.MainShip;

/**
 * Класс управления выводом текстовой информации на экран.
 */
public class PrintManager {
    //инициируем константу размера шрифта линии информации
    private final float INFOLINE_FONT_SIZE = 0.02f;
    //инициируем константы шаблонов текста для вывода на экран
    private static final String SCORE = "Score:";//количество сбитых врагов
    private static final String HP = "HP:";//значение здоровья главного корабля
    private static final String LEVEL = "Level:";//текущий уровень игры

    //принимаем объект скрина(экрана)
    private GameScreen screen;
    //принимаем прямойгольник игрового мира
    private Rect worldBounds;
    //принимаем объект спрайта главного корабля
    private MainShip mainShip;
    //инициируем переменную для объекта счетчика очков
    private ScoreCounter scoreCounter = ScoreCounter.getInstance();

    //объявляем переменную шрифта текста
    private Font fontInfoLine;
    //объявляем переменные готового текста для вывода на экран
    private StringBuilder sbScore;//количество сбитых врагов
    private StringBuilder sbHp;//значение здоровья главного корабля
    private StringBuilder sbLevel;//текущий уровень игры

    public PrintManager(GameScreen screen) {
        this.screen = screen;
        //инициируем переменную шрифта текста. В параметрах fontFile, imageFile
        fontInfoLine = new Font("font/font.fnt", "font/font.png");
        //инициируем переменные готового текста для вывода на экран
        sbScore = new StringBuilder();//количество сбитых врагов
        sbHp = new StringBuilder();//значение здоровья главного корабля
        sbLevel = new StringBuilder();//текущий уровень игры
        //принимаем объект спрайта главного корабля
        this.mainShip = screen.getMainShip();
    }

    /**
     * Метод установки размера и позиции кнопки
     * @param worldBounds - прямоугольник игровго мира в мировых координатах
     */
    public void resize(Rect worldBounds) {
        //принимаем прямойгольник игрового мира
        this.worldBounds = worldBounds;
        //устанавливаем размер шрифта в мировых координатах
        fontInfoLine.setSize(INFOLINE_FONT_SIZE);//FIXME сделать зависимой от пропорций экрана

    }

    /**
     * Метод отрисовки объектов игрового поля.
     */
    public void draw(SpriteBatch batch) {
        //выводим линию информации об игре на экран
         printInfoLine(batch);
    }

    /**
     * Метод вывода информации об игре на экран
     * @param batch - батчер
     */
    private void printInfoLine(SpriteBatch batch) {
        //собираем и выводим строку блока информации об очках
        printScoreBlock(batch);
        //собираем и выводим строку блока информации о значении жизни главного корабля
        printMainShipHpBlock(batch);
        //собираем и выводим строку блока информации об уровне игры
        printLevelBlock(batch);

//        //сбрасываем итоговую строку сообщения о набранных очках//FIXME
//        sbScore.setLength(0);
//        //устанавливаем координаты позиции итоговой строки сообщения
//        // о подбитых кораблях противника
//        float fragsPosX = worldBounds.getLeft() + 0.01f;//с отступом от левого края игрового поля
//        float fragsPosY = worldBounds.getTop() - 0.01f;//с отступом от верха игрового поля
//        //***собираем строку информации об очках***
//        //текущее значение набранных очков
//        sbScore.append(SCORE).append(scoreCounter.getScoreTotal());
//        // и максимальное за игру значение набранных очков
//        sbScore.append("/").append(scoreCounter.getScoreTotal());//FIXME поменять на максимальное
//        //вызываем метод отрисовки шрифта текста сообщения о сбитых кораблях противника
//        fontInfoLine.draw(batch,
//                //добавляем в строку шаблон сообщения и количество сбитых кораблей противника
////                sbScore.append(SCORE).append(scoreCounter.getScoreTotal()).append("/"),//FIXME
//                sbScore,
//                //координаты позиции сообщения(по умолчанию выравнивание - по левому краю)
//                fragsPosX, fragsPosY);

        //сбрасываем итоговую строку сообщения о размере жизни главного корабля
//        sbHp.setLength(0);
//        //устанавливаем координаты позиции итоговой строки сообщения о жизни главного корабля
//        float hpPosX = worldBounds.pos.x;//по середине ширины игрового экрана
//        float hpPosY = worldBounds.getTop() - 0.01f;//с отступом от верха игрового поля
//        //вызываем метод отрисовки шрифта текста о размере жизни главного корабля
//        fontInfoLine.draw(batch,
//                //добавляем в строку шаблон сообщения и размер жизни главного корабля
//                sbHp.append(HP).append(mainShip.getHp()),
//                //координаты позиции сообщения
//                hpPosX, hpPosY,
//                //выравниваем сообщение по центру позиции
//                Align.center);

//        //сбрасываем итоговую строку сообщения о текущем уровне игры
//        sbLevel.setLength(0);
//        //устанавливаем координаты позиции итоговой строки сообщения о текущем уровне игры
//        float levelPosX = worldBounds.getRight() - 0.01f;
//        float levelPosY = worldBounds.getTop() - 0.01f;
//        //вызываем метод отрисовки шрифта текста о текущем уровне игры
//        fontInfoLine.draw(batch,
//                //добавляем в строку шаблон сообщения и номер текущего уровня игры
//                sbLevel.append(LEVEL).append(scoreCounter.getLevel()),
//                //координаты позиции сообщения
//                levelPosX, levelPosY,
//                //выравниваем сообщение по правому краю позиции
//                Align.right);
    }

    /**
     * Метод собирает и выводит строку блока информации об очках.
     * Левое значение - текущее, правое - максимальное значение за всю игру.
     * @param batch - батчер
     */
    private void printScoreBlock(SpriteBatch batch) {
        //сбрасываем итоговую строку блока сообщения об очках
        sbScore.setLength(0);
        //устанавливаем координаты позиции блока
        float fragsPosX = worldBounds.getLeft() + 0.01f;//с отступом от левого края игрового поля
        float fragsPosY = worldBounds.getTop() - 0.01f;//с отступом от верха игрового поля
        //***собираем строку информации об очках***
        //добавляем шаблон сообщения и текущее значение набранных очков
        sbScore.append(SCORE).append(scoreCounter.getScoreTotal());
        // и максимальное за игру значение набранных очков
        sbScore.append("/").append(scoreCounter.getScoreTotal());//FIXME поменять на максимальное
        //вызываем метод отрисовки шрифта текста сообщения о сбитых кораблях противника
        fontInfoLine.draw(batch,
                //отрисовываем готовую строку об очках
                sbScore,
                //координаты позиции сообщения(по умолчанию выравнивание - по левому краю)
                fragsPosX, fragsPosY);
    }

    /**
     * Метод собирает и выводит строку блока информации о значении жизни главного корабля.
     * Левое значение - текущее, правое - максимальное возможное на текущем уровне игры.
     * @param batch - батчер
     */
    private void printMainShipHpBlock(SpriteBatch batch) {
        sbHp.setLength(0);
        //устанавливаем координаты позиции итоговой строки сообщения о жизни главного корабля
        float hpPosX = worldBounds.pos.x;//по середине ширины игрового экрана
        float hpPosY = worldBounds.getTop() - 0.01f;//с отступом от верха игрового поля
        //***собираем строку информации о значении жизни главного корабля***
        //добавляем в строку шаблон сообщения и размер жизни главного корабля
        sbHp.append(HP).append(mainShip.getHp());
        // и максимально возможное значение жизни на текущем уровне игры
        sbHp.append("/").append(mainShip.getConstHp() * scoreCounter.getLevel());
        //вызываем метод отрисовки шрифта текста о размере жизни главного корабля
        fontInfoLine.draw(batch,
                //отрисовываем готовую строку о значении жизни главного корабля
                sbHp,
                //координаты позиции сообщения
                hpPosX, hpPosY,
                //выравниваем сообщение по центру позиции
                Align.center);
    }

    /**
     * Метод собирает и выводит строку блока информации об уровне игры.
     * Левое значение - текущее, правое - максимальное значение за всю игру.
     * @param batch - батчер
     */
    private void printLevelBlock(SpriteBatch batch) {
        //сбрасываем итоговую строку сообщения о текущем уровне игры
        sbLevel.setLength(0);
        //устанавливаем координаты позиции итоговой строки сообщения о текущем уровне игры
        float levelPosX = worldBounds.getRight() - 0.01f;
        float levelPosY = worldBounds.getTop() - 0.01f;
        //***собираем строку информации об уровне игры***
        //добавляем в строку шаблон сообщения и номер текущего уровня игры
        sbLevel.append(LEVEL).append(scoreCounter.getLevel());
        // и максимальное за игру значение уровня игры
        sbLevel.append("/").append(scoreCounter.getLevel());//FIXME поменять на максимальное
        //вызываем метод отрисовки шрифта текста о текущем уровне игры
        fontInfoLine.draw(batch,
                //отрисовываем готовую строку об уровне игры
                sbLevel,
                //координаты позиции сообщения
                levelPosX, levelPosY,
                //выравниваем сообщение по правому краю позиции
                Align.right);
    }
}
