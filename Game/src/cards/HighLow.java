package cards;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JApplet;
import javax.swing.JFrame;

import cards.gameLogic.Card;
import cards.gameLogic.Deck;
import gamemanager.GameStrategy;

/**
 * This program lets the user play HighLow, a simple card game 
 * that is described in the output statements at the beginning of 
 * the main() routine.  After the user plays several games, 
 * the user's average score is reported.
 */

public class HighLow extends JFrame implements GameStrategy {

	
	public HighLow() {
      
      System.out.println("This program lets you play the simple card game,");
      System.out.println("HighLow.  A card is dealt from a deck of cards.");
      System.out.println("You have to predict whether the next card will be");
      System.out.println("higher or lower.  Your score in the game is the");
      System.out.println("number of correct predictions you make before");
      System.out.println("you guess wrong.");
      System.out.println();
   }

	public void start(){
		
	
      int gamesPlayed = 0;     // Number of games user has played.
      int sumOfScores = 0;     // The sum of all the scores from 
                               //      all the games played.
      double averageScore;     // Average score, computed by dividing
                               //      sumOfScores by gamesPlayed.
      boolean playAgain;       // Record user's response when user is 
                               //   asked whether he wants to play 
                               //   another game.
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));///needed to read from console
     
      do {
         int scoreThisGame;        // Score for one game.
         scoreThisGame = play();   // Play the game and get the score.
         sumOfScores += scoreThisGame;
         gamesPlayed++;
         System.out.print("Play again? (Y/N) ");
         playAgain = br.read();
      } while (playAgain);
      
      averageScore = ((double)sumOfScores) / gamesPlayed;
      
      System.out.println();
      System.out.println("You played " + gamesPlayed + " games.");
      System.out.printf("Your average score was %1.3f.\n", averageScore);
   
   }  // end main()
   

   /**
    * Lets the user play one game of HighLow, and returns the
    * user's score on that game.  The score is the number of
    * correct guesses that the user makes.
    */
   private static int play() {
   
      Deck deck = new Deck();  // Get a new deck of cards, and 
                               //   store a reference to it in 
                               //   the variable, deck.
      
      Card currentCard;  // The current card, which the user sees.

      Card nextCard;   // The next card in the deck.  The user tries
                       //    to predict whether this is higher or lower
                       //    than the current card.

      int correctGuesses ;  // The number of correct predictions the
                            //   user has made.  At the end of the game,
                            //   this will be the user's score.

      char guess;   // The user's guess.  'H' if the user predicts that
                    //   the next card will be higher, 'L' if the user
                    //   predicts that it will be lower.
      
      deck.shuffle();  // Shuffle the deck into a random order before
                       //    starting the game.

      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));///needed to read from console

      correctGuesses = 0;
      currentCard = deck.dealCard();
      System.out.print("The first card is the " + currentCard);
      
      while (true) {  // Loop ends when user's prediction is wrong.
         
         /* Get the user's prediction, 'H' or 'L' (or 'h' or 'l'). */
         
         System.out.print("Will the next card be higher (H) or lower (L)?  ");
         do {
             guess = br.readLine().charAt(0);
             guess = Character.toUpperCase(guess);
             if (guess != 'H' && guess != 'L') 
            	 System.out.print("Please respond with H or L:  ");
         } while (guess != 'H' && guess != 'L');
         
         /* Get the next card and show it to the user. */
         
         nextCard = deck.dealCard();
         System.out.print("The next card is " + nextCard);
         
         /* Check the user's prediction. */
         
         if (nextCard.getValue() == currentCard.getValue()) {
        	 System.out.print("The value is the same as the previous card.");
        	 System.out.print("You lose on ties.  Sorry!");
            break;  // End the game.
         }
         else if (nextCard.getValue() > currentCard.getValue()) {
            if (guess == 'H') {
            	System.out.print("Your prediction was correct.");
                correctGuesses++;
            }
            else {
            	System.out.print("Your prediction was incorrect.");
                break;  // End the game.
            }
         }
         else {  // nextCard is lower
            if (guess == 'L') {
            	System.out.print("Your prediction was correct.");
                correctGuesses++;
            }
            else {
            	System.out.print("Your prediction was incorrect.");
                break;  // End the game.
            }
         }
         
         /* To set up for the next iteration of the loop, the nextCard
            becomes the currentCard, since the currentCard has to be
            the card that the user sees, and the nextCard will be
            set to the next card in the deck after the user makes
            his prediction.  */
         
         currentCard = nextCard;
         
         System.out.print("The card is " + currentCard);
         
      } // end of while loop
      
      System.out.print("----");
      System.out.print("The game is over.");
      System.out.print("You made " + correctGuesses 
                                           + " correct predictions.");
      System.out.print("----");
      
      return correctGuesses;
      
   }  // end play()

   

} // end class
