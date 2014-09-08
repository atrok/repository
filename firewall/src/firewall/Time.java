package firewall;

public class Time {
	
	private int value;
	public Time(String value){
		try{
		this.value=Integer.parseInt(value);
		}catch(NumberFormatException e){
			throw e;
		}
	}
	public int getTime(){return value;}
}
