package gdx.stargame.base;

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
    //инициируем перенную текущего уровня игры(?должен быть в GameScreen?)
    private int level = 1;
    //инициируем переменную для хранения номера предыдущего уровня игры(НЕ нужно?)//FIXME
    private int prevLevel = 1;

    public void checkNextLevel(int score){
        //прибавляем переданные очки к текущей сумме набранных очков
        scoreTotal += score;
        //если текущая сумма очков стала меньше нуля, устанавливаем ноль
        scoreTotal = scoreTotal > 0 ? scoreTotal : 0;
        //если новая текущая сумма набранных очков достаточна для перехода на новый уровень
        if(scoreTotal >= level * NEXT_LEVEL_SCORE_SUM){
            //сохраняем значение текущего уровня
            prevLevel = level;
            //инкрементируем текущий уровень
            level++;
        //если значение уровня игры больше минимального уровня и
        //если новая текущая сумма набранных очков уменьшилась достаточно для
        // возврата на предыдущий уровень
        } else if(level > 1 && scoreTotal <= (level - 1) * NEXT_LEVEL_SCORE_SUM) {
            //сохраняем значение текущего уровня
            level = prevLevel;
            //декрементируем текущий уровень
            prevLevel--;

//            if(scoreTotal <= (level - 1) * NEXT_LEVEL_SCORE_SUM){
//                //сохраняем значение текущего уровня
//                level = prevLevel;
//                //декрементируем текущий уровень
//                prevLevel--;
//                }
//            //если значение уровня игры не больше минимального уровня
//            } /*else if (scoreTotal <= NEXT_LEVEL_SCORE_SUM){
//            //декрементируем текущий уровень
//            prevLevel = 1;*/
        }
    }

    /**
     * Метод организует режим начала новой игры.
     */
    public void startNewGame() {

        //устанавливаем переменные текущего и предыдущего уровней игры в начальное состояние
        setLevel(1);
        setPrevLevel(1);
        //сбрасываем счетчик сбитых врагов
        setScoreTotal(0);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrevLevel() {
        return prevLevel;
    }

    public void setPrevLevel(int prevLevel) {
        this.prevLevel = prevLevel;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
