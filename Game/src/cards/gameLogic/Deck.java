package cards.gameLogic;

import java.util.ArrayList;
import java.util.List;

abstract public  class Deck {

    /**
     * Constructor.  Create an unshuffled deck of cards.
     */
	List cards=new ArrayList();
    
	public Deck(){

//		int i;
		for (Suit s: Suit.values()){
			
			for (Value f: Value.values()){
				
					cards.add(new Card(f,s));
				}
		}

    };

    /**
     * Put all the used cards back into the deck,
     * and shuffle it into a random order.
     */
    abstract public void shuffle();

    /**
     * As cards are dealt from the deck, the number of 
     * cards left decreases.  This function returns the 
     * number of cards that are still left in the deck.
     */
    abstract public int cardsLeft();

    /**
     * Deals one card from the deck and returns it.
     * @throws IllegalStateException if no more cards are left.
     */
    abstract public Card dealCard();
}
