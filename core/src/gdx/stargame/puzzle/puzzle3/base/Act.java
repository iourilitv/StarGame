package gdx.stargame.puzzle.puzzle3.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.HashMap;
import java.util.Stack;

import gdx.stargame.puzzle.puzzle3.Puzzle3;
import gdx.stargame.puzzle.puzzle3.constants.Source;
import gdx.stargame.puzzle.puzzle3.screen.MenuScreen;
import gdx.stargame.puzzle.puzzle3.screen.PuzzleScreen;

public class Act {
    //принимаем экземпляр игры
    private Puzzle3 game;
    //принимаем картинку
    private Source picture;
    //объявляем таблицу активных скринов(key - тип скрина(имя класса), объект скрина)
    private HashMap<String, Screen> screens;
    //объявляем стэк активных скринов
    private Stack<Screen> screensStack;

    /*public Act(Source picture) {
        this.picture = picture;
        this.screens = new HashMap<>();
        this.screensStack = new Stack<>();
    }*/

    public Act(Puzzle3 game, Source picture) {
        this.game = game;
        this.picture = picture;
        this.screens = new HashMap<>();
        this.screensStack = new Stack<>();
        init();
    }

    private void init(){
        //устанавливаем начальный экран приложения
        Screen screen = new MenuScreen(this);
        screens.put("MenuScreen", screen);
        screensStack.push(screen);
        game.setScreen(screen);
    }


    void startNewGame(){
        Screen screen = new PuzzleScreen(this);
        screens.put("PuzzleScreen", screen);
        screensStack.push(screen);
        game.setScreen(screen);
    }

    void goToPreviousScreen(){
        if(!screensStack.empty()){
            screensStack.pop();
        }
        if(!screensStack.empty()){
            game.setScreen(screensStack.peek());
        }
    }

    void continueGame(){
        //запоминаем ссылку на скрин объекта игры
        Screen screen = screens.get("PuzzleScreen");
        //если стэк не пустой и сверху не лежит объект игры
        if(!screensStack.empty() && !screensStack.peek().equals(screen)){
            //добавляем в стэк скрин объекта игры
            screensStack.push(screen);
            //устанавливаем скрин объекта игры, как текущий
            game.setScreen(screen);
        }
    }

    public Source getPicture() {
        return picture;
    }

    public void setPicture(Source picture) {
        this.picture = picture;
    }


    /*@Override
    public void create() {
        //устанавливаем начальный экран приложения
//		Screen screen = new MenuScreen(this);
        Screen screen = new PuzzleScreen(this);
        screens.put("MenuScreen", screen);
		screensStack.push(screen);
        setScreen(screen);

    }*/

    public HashMap<String, Screen> getScreens() {
        return screens;
    }

    public Stack<Screen> getScreensStack() {
        return screensStack;
    }

    public Puzzle3 getGame() {
        return game;
    }

}
