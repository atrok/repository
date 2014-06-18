package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

import cards.gameLogic.Card;
import cards.gameLogic.Value;
import cards.gameLogic.Suit;
import static cards.gameLogic.Suit.*;
import static cards.gameLogic.Value.*;
import static util.Print.*;


public class testCard {

	@Test
	public void test() {

		List cards=new ArrayList();
		for (Suit s: Suit.values()){
			
			for (Value f: Value.values()){
				
				if (s==Suit.JOKER){
					cards.add(new Card(Value.JOKER,Suit.JOKER));
					}else{
						cards.add(new Card(f,s));
				}
			
				
			}
		}
		
		Iterator<Card> it=cards.iterator();
		
		while(it.hasNext())
			println(it.next().toString());
			
		
	}

}
