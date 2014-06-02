package gamemanager;


/* 	Абстрактный класс, реализация абстрактных методов которого будет специфичной для каждого вида игры. 
*
*      Файл Game.java
* */

public abstract class Game {

	private int playersAmount;

	protected abstract void initializeGame();

	protected abstract void playGame();

	protected abstract void endGame();

	protected abstract void printWinner();

	public void playOneGame(int playersAmount){
		setPlayersAmount(playersAmount);

		initializeGame();
		playGame();
		endGame();

		printWinner();
	}

       public void setPlayersAmount(int playersAmount){
		this.playersAmount = playersAmount;
	}

}
