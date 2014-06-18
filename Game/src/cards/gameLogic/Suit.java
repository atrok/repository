package cards.gameLogic;

public enum Suit {
	
	HEARTS(Color.RED),DIAMONDS(Color.RED),SPADES(Color.BLACK),CLUBS(Color.BLACK),JOKER(Color.NONE);

	private Suit(String string){this.value=string;}
	private String value;
	public String color(){return value;}
	public String toString(){
		switch(this){
			case HEARTS: return "Hearts";
			case DIAMONDS: return "Diamonds";
			case SPADES: return "Spades";
			case CLUBS: return "Clubs";
			
			default: return "";
			}
			
		
	}
}

class Color {
	public final static String RED="red";
	public final static String BLACK="black";
	public final static String NONE="none";
	
}
