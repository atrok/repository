package firewall;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static firewall.Title.*;

public abstract class Page {
	protected HashMap<String, String> options = new HashMap<String, String>();
	protected String url;

	protected static Document cachedPage = null;
	protected static LinkedList<Rule> rulesMap = new LinkedList<Rule>();
	protected static LinkedList<Rule> existingRulesMap = new LinkedList<Rule>();
	
	protected String errormessage;
	protected String title;

	public Page(String url) {
		setUrl(url);
		// rulesMap=new LinkedList<Rule>();
		// if (rulesMap.size()==0)
		// rulesMap.add(new Rule()); /// we need empty Rule to exist for run()
		// to execute properly
	}

	public void setUrl(String str) {
		url = str;
	}

	public void setOptions(HashMap<String, String> opt) {
		options = opt;
	}

	public String getUrl() {
		return url;
	}

	public HashMap getOptions() {
		return options;
	}

	public Page run() throws IOException {
		updateOptions();

		//while (!rulesMap.isEmpty()) {
			Rule r = rulesMap.remove();

			if (null != r.getMap())
				options.putAll(r.getMap());

			if (null != cachedPage) {

				String nonce = cachedPage.select("input[name*=nonce]").attr(
						"value");
				options.put("nonce", nonce);
			}

			System.out.println("Request to be sent:" + url);
			System.out.println("options to be sent:" + options.toString());

			cachedPage = Jsoup.connect(url).data(options).post();

			String action = cachedPage.select("form").attr("action");
			System.out.println("Value of action form in returned page:"
					+ action);

			errormessage = cachedPage.select("div#error-message-text").text();
			
			action=cachedPage.select("title").text();
			
			System.out.println("Title: "+action+"\nError message:" + errormessage);

			existingRulesMap = getRules();
			
		//}
		return this;

	}

	private LinkedList<Rule> getRules() {
		if (null != cachedPage) {
			Elements rules = cachedPage.select("table.grid tr");

			LinkedList<Rule> rulesMap = new LinkedList<Rule>();
			// rulesMap.add(new Rule());// we need to have Empty rule added for
			// run() to execute properly
			ArrayList<String> ar=new ArrayList<String>();
			for (Element el : rules) {
				Rule list = new Rule();
				String str = "";
				
				
				Elements th = el.select("th");
				
				if (!th.isEmpty()) {// headers for rule fields
					//int i=0;
					
					for (Element e : th) {
						ar.add(e.html().toString());
						}
					}
				
				
				Elements td = el.select("td");
				if (!td.isEmpty()) {
					int i=0;
					for (Element e : td) {
						
						if (e.select("input").hasAttr("type")) {
							list.add(ar.get(i),e.select("input").attr("name").toString());
						} else {
							list.add(ar.get(i),e.html().toString());
						}
						i++;
					}
				}
				if (!list.isEmpty()){
					rulesMap.add(list);
				System.out.println("List of already existing rules:\n"
						+ list.toString() + "\n");
				}
				
			}
			return rulesMap;
		}
		return this.rulesMap;
		// System.out.print(doc.html().toString());
	}
	public String getTitle(){
		return title;
	}
	
	public boolean isEmptyExistingRulesMap(){
		return existingRulesMap.isEmpty();
	}
	abstract void updateOptions();

	abstract void updateRules();

}

class LoginPage extends Page {

	public LoginPage(String url) {
		super(url);
		// TODO Auto-generated constructor stub
		title=Title.LOGIN;
	}

	@SuppressWarnings("unused")
	void updateOptions() {
		Rule r = new Rule();
		r.addNewFieldToRule("password", Firewall.deviceCode);
		this.rulesMap.add(r);

	};

	void updateRules() {
	}

}

class PacketFilterPageAdd extends Page {

	private LinkedList<Rule> rules=new LinkedList<Rule>();

	public PacketFilterPageAdd(String url) {
		super(url);
		// TODO Auto-generated constructor stub
		title=Title.PACKETFILTER;
	}

	public PacketFilterPageAdd(String url, LinkedList<Rule> rules) {
		super(url);
		this.rules = rules;
		// TODO Auto-generated constructor stub

	}
	
	// it's more convenient way to handle page with 1 rule than 1 page with multiple rules
	public PacketFilterPageAdd(String url, Rule rule) {
		super(url);
		this.rules.add(rule);
		// TODO Auto-generated constructor stub
		title=Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		// options.put("Add", "Add");
		for (Rule r : rules) {
			r.addNewFieldToRule("Add", "Add");
			r.addNewFieldToRule("enfilter", "on");
			// HashMap h=r.getMap();
			r.dropRemove();
			r.dropEdit();
		}
		this.rulesMap = rules;
	}

	void updateRules() {

	}

}

class PacketFilterPageDelete extends Page {

	public PacketFilterPageDelete(String url) {
		super(url);
		// TODO Auto-generated constructor stub
		title=Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub

		for (Rule r : existingRulesMap) {
//			HashMap h = r.getMap();
		r.addNewFieldToRule("enfilter", "on");
		r.addNewFieldToRule("pass", "pass");
		r.dropEdit();
		this.rulesMap.add(r);
		}


	}

	void updateRules() {

	}
}

class PacketFilterPageChange extends Page {

	public PacketFilterPageChange(String url) {
		super(url);
		// TODO Auto-generated constructor stub
		title=Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		Rule r = new Rule();
		r.addNewFieldToRule("enfilter", "on");
		r.addNewFieldToRule("Change", "Change");
		for (Rule r2 : rulesMap) {
			HashMap h = r.getMap();
			r2.dropRemove();
		}
		this.rulesMap.add(r);
	}

	void updateRules() {

	}
}

class PacketFilterPageDefault extends Page {

	public PacketFilterPageDefault(String url) {
		super(url);
		// TODO Auto-generated constructor stub
		title=Title.PACKETFILTER;
	}
	
	public PacketFilterPageDefault() {
		
		super(Title.PacketFilterUrl);
		// TODO Auto-generated constructor stub
		title=Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		// TODO Auto-generated method stub
		Rule r = new Rule();
		r.addNewFieldToRule("empty", "empty");
		this.rulesMap.add(r);
	}

	void updateRules() {
	}
}