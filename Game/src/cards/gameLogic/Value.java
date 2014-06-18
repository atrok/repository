package cards.gameLogic;

public enum Value {
	ACE(11),KING(4),QUEEN(3),KNIGHT(2),TEN(10),NINE(9),EIGHT(8),SEVEN(7),SIX(6),FIVE(5),FOUR(4),THREE(3),TWO(2),JOKER(1);
	private int value;
	private Value(int x){
		this.value=x;
	}
	public int value(){return value;}
	public String toString(){
		switch(this){
			case ACE: return "Ace";
			case KING: return "King";
			case QUEEN: return "Queen";
			case KNIGHT: return "Knight";
			case TEN: return "Ten";
			case NINE: return "Nine";
			case EIGHT: return "Eight";
			case SEVEN: return "Seven";
			case SIX: return "Six";
			case FIVE: return "Five";
			case FOUR: return "Four";
			case THREE: return "Three";
			case TWO: return "Two";
			case JOKER: return "Joker";
			default: return "";
			}
	}
}
