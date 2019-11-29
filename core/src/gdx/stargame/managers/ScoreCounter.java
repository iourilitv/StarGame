package gdx.stargame.managers;

/**
 * Класс логики подсчета очков.
 */
public class ScoreCounter {
    //создаем объект класса
    private static final ScoreCounter ourInstance = new ScoreCounter();

    public static ScoreCounter getInstance () {
        return ourInstance;
    }

    //инициируем константу суммы набранных очков требуемых для перехода на следующий уровень
    private static final int NEXT_LEVEL_SCORE_SUM = 10;//100;
    //инициируем переменную для текущей суммы набранных очков с начала игры
    private int scoreTotal = 0;
    //инициируем переменную для хранения максимального значения очков набранных за игру
    //(значение очков может уменьшаться из-за штрафов)
    private int maxScoreTotal = 0;
    //инициируем перенную текущего уровня игры(?должен быть в GameScreen?)
    private int level = 1;
    //инициируем переменную для хранения максимального значения уровня игры достигнутого за игру
    //(уровень может уменьшаться из-за штрафов)
    private int maxLevel = 1;
    //объявляем фраг перехода на следующий уровень
    private boolean isNextLevel;

    public void updateScoresAndCheckNextLevel(int score){
        //прибавляем переданные очки к текущей сумме набранных очков
        scoreTotal += score;
        //если текущая сумма очков стала меньше нуля, устанавливаем ноль
        scoreTotal = scoreTotal > 0 ? scoreTotal : 0;
        //если новая текущая сумма набранных очков достаточна для перехода на новый уровень
        if(scoreTotal >= level * NEXT_LEVEL_SCORE_SUM){
            //устанавливаем фраг перехода на следующий уровень
            isNextLevel = true;
            //инкрементируем текущий уровень
            level++;
        //если значение уровня игры больше минимального уровня и
        //если новая текущая сумма набранных очков уменьшилась достаточно для
        // возврата на предыдущий уровень
        } else if(level > 1 && scoreTotal < (level - 1) * NEXT_LEVEL_SCORE_SUM) {
            //декрементируем значение текущего уровня
            level--;
        }
        //проверяем и сохраняем новые значения максимумов за игру набранных очков и уровня
        checkAndSaveMaxScoreAndLevel();
    }

    /**
     * Метод проверяет и сохраняет новые значения максимумов за игру набранных очков и уровня.
     */
    private void checkAndSaveMaxScoreAndLevel() {
        //если текущее значение набранных очков больше сохраненного максимального значения
        if(scoreTotal > maxScoreTotal){
            //сохраняем новое значение
            maxScoreTotal = scoreTotal;
        }
        //если текущее значение уровня игры больше сохраненного максимального значения
        if(level > maxLevel){
            //сохраняем новое значение
            maxLevel = level;
        }
    }

    /**
     * Метод организует режим начала новой игры.
     */
    public void startNewGame() {
        //устанавливаем переменные текущего и максимального уровней игры в начальное состояние
        level = 1;
        maxLevel = 1;
        //сбрасываем переменные текущего и максимального за игру значений набранных очков
        scoreTotal = 0;
        maxScoreTotal = 0;
        //сбрасываем флаг перехода на новый уровень
        resetNextLevel();
    }

    int getScoreTotal() {
        return scoreTotal;
    }

    int getMaxScoreTotal() {
        return maxScoreTotal;
    }

    public int getLevel() {
        return level;
    }

    int getMaxLevel() {
        return maxLevel;
    }

    public boolean isNextLevel() {
        return isNextLevel;
    }

    public void resetNextLevel() {
        isNextLevel = false;
    }
}
