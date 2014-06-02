package gamemanager;

import cards.Cards;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author atrok
 */


 

 
 

 
 
 
/*	Класс, показывающий работу шаблона проектирования "Шаблонный метод".
 *
 *      Файл GamesManager.java
 * */
 
public class GameManager {
 
	public static void main (String [] args){
		final GameCode gameCode = GameCode.CHESS;
 
		Game game;
 
		switch (gameCode){
			case CHESS : 
				game = new Chess();  
				break;
			case MONOPOLY : 
				game = new Monopoly();  
				break;
			case CARDS : 
				game = new Cards();  
				break;
			default :
				throw new IllegalStateException();
		}
 
		game.playOneGame(2);
	}
 
}
