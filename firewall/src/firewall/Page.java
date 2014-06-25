package firewall;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class Page {
		protected HashMap<String,String> options=new HashMap<String,String>();
		protected String url;	
		
		protected static Document cachedPage;
		protected static LinkedList<Rule> rulesMap;
		
		public Page(String url){
			setUrl(url);
			rulesMap=new LinkedList<Rule>();
			rulesMap.add(new Rule()); /// we need empty Rule for cases when no rules exists
		}
		
		public void setUrl(String str){
			url=str;
		}
		
		public void setOptions(HashMap<String,String> opt){
			options=opt;
		}
		
		public String getUrl(){return url;}
		public HashMap getOptions(){return options;}
		
		public void run() throws IOException{

			for (Rule r: rulesMap){
				updateRules();

				options.putAll(r.getMap());
				updateOptions();
				
			if (null != cachedPage) {

				String nonce = cachedPage.select("input[name*=nonce]")
						.attr("value");
				options.put("nonce", nonce);
			}

			System.out.println(url);
			System.out.println(options.toString());

			Document doc= Jsoup.connect(url).data(options).post();

			String action = doc.select("form").attr("action");
			System.out.print(action);
			
			rulesMap=getRules();
			
			cachedPage= doc;
			}
		}

		private LinkedList<Rule> getRules(){
			Elements rules = cachedPage.select("table.grid tr");

			LinkedList<Rule> rulesMap = new LinkedList<Rule>();
			
			for (Element el : rules) {
				Rule list = new Rule();
				String str = "";
				Elements td = el.select("td");
				if(!td.isEmpty()){
				for (Element e : td) {
					if (e.select("input").hasAttr("type")) {
						list.add(e.select("input").attr("name").toString());
					} else {
						list.add(e.html().toString());
					}
				}
				}
				rulesMap.add(list);
				System.out.println(list.toString());
			}
			return rulesMap;
			// System.out.print(doc.html().toString());
		}
		
		abstract void updateOptions();
		abstract void updateRules();
		
}

class LoginPage extends Page{
	
	public LoginPage(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unused")
	void updateOptions(){
		options.put("password", Firewall.deviceCode);
	};
	
	void updateRules(){}

}

class PacketFilterPageAdd extends Page{
	
	public PacketFilterPageAdd(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public PacketFilterPageAdd(String url, LinkedList<Rule> rules) {
		super(url);
		// TODO Auto-generated constructor stub
		this.rulesMap=rules;
	}
	
	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		options.put("Add", "Add");
	}
	
	void updateRules(){	
		for (Rule r: rulesMap){
			HashMap h=r.getMap();
			r.dropRemove();
			r.dropEdit();
		}
	}
	
}

class PacketFilterPageDelete extends Page{
	
	public PacketFilterPageDelete(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	

	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		options.put(rulesMap.getFirst().getRemove(), "Delete");
		options.put("enfilter","on");
		options.put("pass","pass");
	}
	
	void updateRules(){
		for (Rule r: rulesMap){
			HashMap h=r.getMap();
			r.dropEdit();
		}
	}
}

class PacketFilterPageChange extends Page{
	
	public PacketFilterPageChange(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		options.put("Change", "Change");
	}
	
	void updateRules(){for (Rule r: rulesMap){
			HashMap h=r.getMap();
			r.dropRemove();
		}
	}
}

class PacketFilterPageDefault extends Page{
	
	public PacketFilterPageDefault(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		options.put("empty", "empty");
	}
	
	void updateRules(){}
}