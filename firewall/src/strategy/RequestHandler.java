package strategy;

import org.jsoup.Jsoup;

public class RequestHandler implements Handler{
	public Page process(Page p){
		
		//return Jsoup.connect(p).data(options).post();
		return null;
	} 
}
