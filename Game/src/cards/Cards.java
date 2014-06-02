package cards;

import gamemanager.Game;
import gamemanager.GameStrategy;

/*	Игра "Cards". Специфически только для монополии реализует методы класса Game.
*
*      Файл Cards.java
*      
*      it's a Context object used in Strategy pattern we will use to start different card games
* */

public class Cards extends Game{

	private GameStrategy game;
	
	
	protected void getSpecificGame(GameStrategy gm) {
		// cards specific initialization actions
		game=gm;
	}

	@Override
	protected void playGame() {
		// cards specific play actions
		game.start();
	}

	@Override
	protected void endGame() {
		// cards specific actions to end a game
	}

	@Override
	protected void printWinner() {
		// monopoly specific actions to print winner
	}

	@Override
	protected void initializeGame() {
		// TODO Auto-generated method stub
		getSpecificGame(new HighLow());
	}

}


