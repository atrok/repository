package tests;

import static org.junit.Assert.*;
import static util.Print.*;

import org.junit.Test;

import static cards.gameLogic.Suit.*;
import cards.gameLogic.*;
public class testSuit {

	@Test
	public void test() {
		/*
		Suit CLUBS=Suit.CLUBS;
		Suit HEARTS=Suit.HEARTS;
		Suit DIAMONDS=Suit.DIAMONDS;
		Suit SPADES=Suit.SPADES;
		*/
		// println(CLUBS.color());
		assertEquals("Clubs", CLUBS.toString());
		assertEquals("black", CLUBS.color());
		
		assertEquals("Hearts", HEARTS.toString());
		assertEquals("red", HEARTS.color());
		
		assertEquals("Diamonds",DIAMONDS.toString());
		assertEquals("red", DIAMONDS.color());
		
		assertEquals("Spades",SPADES.toString());
		assertEquals("black",SPADES.color());
		
		
	}

}

