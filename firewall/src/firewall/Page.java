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
	/*
	 * every http request is represented by Page object which consists of 
	 * url
	 * options of corresponding POST request
	 * options are kept in Rule object and are collected under rulesMap List
	 * the design is to have 1 Rule per Page object. It makes sense since Rule represents options to be passed with POST request
	 * that means every new http request should have its own options ie Rule
	 * 
	 * So next Page classes represent different http requests to service which url we pass along with Page object
	 * we need to have different Classes for every http request as each bears specific purpose and has its own set of specific options 
	 * Names of classes speak for themselves.
	 * 
	 * Page object is abstract class with template method run() common for all inherited classes but WaitPage
	 * WaitPage is needed to set a delay between execution of Pages in PageQueue
	 * 
	 */
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

		// while (!rulesMap.isEmpty()) {
		System.out.println(this.getClass().toString()
				+ " entering Page.run() rulesMap:");
		if (!rulesMap.isEmpty()) {
			System.out.println(rulesMap.toString());

			Rule r = rulesMap.remove();
			if (null != r.getMap())
				options.putAll(r.getMap());
		}

		if (null != cachedPage) {

			String nonce = cachedPage.select("input[name*=nonce]")
					.attr("value");
			options.put("nonce", nonce);
		}

		System.out.println("Request to be sent:" + url);
		System.out.println("options to be sent:" + options.toString());

		cachedPage = Jsoup.connect(url).data(options).post();

		String action = cachedPage.select("form").attr("action");
		System.out.println("Response gained (form value):" + action);

		errormessage = cachedPage.select("div#error-message-text").text();

		action = cachedPage.select("title").text();

		System.out.println("Title of page: " + action + "\nError message:"
				+ errormessage);

		existingRulesMap = getRules();

		// }
		return PageFabric.getInstance().getPage(action);

	}

	private LinkedList<Rule> getRules() {
		if (null != cachedPage) {
			Elements rules = cachedPage.select("table.grid tr");

			LinkedList<Rule> rulesMap = new LinkedList<Rule>();
			// rulesMap.add(new Rule());// we need to have Empty rule added for
			// run() to execute properly
			ArrayList<String> ar = new ArrayList<String>();
			for (Element el : rules) {
				Rule list = new Rule();
				String str = "";

				Elements th = el.select("th");

				if (!th.isEmpty()) {// headers for rule fields
					// int i=0;

					for (Element e : th) {
						ar.add(e.html().toString());
					}
				}

				Elements td = el.select("td");
				if (!td.isEmpty()) {
					int i = 0;
					for (Element e : td) {

						if (e.select("input").hasAttr("type")) {
							list.add(ar.get(i), e.select("input").attr("name")
									.toString());
						} else {
							list.add(ar.get(i), e.html().toString());
						}
						i++;
					}
				}
				if (!list.isEmpty()) {
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

	public String getTitle() {
		return title;
	}

	public boolean isEmptyExistingRulesMap() {
		return existingRulesMap.isEmpty();
	}

	abstract void updateOptions();



}

class LoginPage extends Page {

	public LoginPage(String url) {
		super(url);
		 
		title = Title.LOGIN;
	}

	@SuppressWarnings("unused")
	void updateOptions() {
		Rule r = new Rule();
		r.addNewFieldToRule("password", Firewall.deviceCode);
		this.rulesMap.add(r);

	};


}

class PacketFilterPageAdd extends Page {

	private LinkedList<Rule> rules = new LinkedList<Rule>();

	public PacketFilterPageAdd(String url) {
		super(url);
		 
		title = Title.PACKETFILTER;
	}

	/*
	 * public PacketFilterPageAdd(String url, LinkedList<Rule> rules) {
	 * super(url); this.rules = rules;  
	 * 
	 * }
	 */

	// it's more convenient way to handle page with 1 rule than 1 page with
	// multiple rules
	public PacketFilterPageAdd(String url, Rule rule) {
		super(url);
		this.rules.add(rule);
		 
		title = Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		//  
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

}

class PacketFilterPageDelete extends Page {

	private String destip = null;

	public String getDestip() {
		return destip;
	}

	public PacketFilterPageDelete(String url) {
		super(url);
		 
		title = Title.PACKETFILTER;
	}

	public PacketFilterPageDelete(String url, String destip) {
		super(url);
		 
		title = Title.PACKETFILTER;
		this.destip = destip;
	}

	@Override
	void updateOptions() {
		//  

		if (!existingRulesMap.isEmpty()) {
			for (Rule r : existingRulesMap) {

				// HashMap h = r.getMap();
				r.addNewFieldToRule("enfilter", "on");
				r.addNewFieldToRule("pass", "pass");
				r.dropEdit();
System.out.println(this.getClass().toString()+" destip:"+destip+" r.getDestIP()"+r.getDestIP());

				if (null != destip) {// delete those rules which destip matches ip of rules presented on the page  
									
					if (destip.equals(r.getDestIP()))
						this.rulesMap.add(r);
				} else { // in opposite case delete all rules on the page
					this.rulesMap.add(r);

				}
				
				
			}
		} else {//TODO this is may not be required because of code refactoring in Page.run()
			this.rulesMap.add(new Rule());
		}

	}
	/*
	 * (non-Javadoc)
	 * @see firewall.Page#isEmptyExistingRulesMap()
	 * we need to override it to add ability to delete specific rules only 
	 * for that we need to check that among existing rules there is no rules having destip we target to delete
	 */
	@Override
	public boolean isEmptyExistingRulesMap() {
		if (null!=destip){// if we need to delete specific rules only
			for (Rule r:existingRulesMap)
				if (r.getDestIP().equals(destip))
				return false;
			return true;
		}
		// in case we delete all rules we check if there is any content in the list or not
		return existingRulesMap.isEmpty();
	}

}

class PacketFilterPageChange extends Page {

	public PacketFilterPageChange(String url) {
		super(url);
		 
		title = Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		//  
		Rule r = new Rule();
		r.addNewFieldToRule("enfilter", "on");
		r.addNewFieldToRule("Change", "Change");
		for (Rule r2 : rulesMap) {
			r2.dropRemove();
		}
		this.rulesMap.add(r);
	}

}

class PacketFilterPageDefault extends Page {

	public PacketFilterPageDefault(String url) {
		super(url);
		 
		title = Title.PACKETFILTER;
	}

	public PacketFilterPageDefault() {

		super(Title.PacketFilterUrl);
		 
		title = Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {
		//  
		Rule r = new Rule();
		r.addNewFieldToRule("empty", "empty");
		this.rulesMap.add(r);
	}

}

class WaitPage extends Page {

	private int timeout = 1000;

	public WaitPage(String url) {
		super(url);
		 
		title = Title.WAITPAGE;
	}

	/*
	 * WaitPage is needed to set a delay between execution of Pages in PageQueue 
	 * it's a simple implementation of scheduler
	 */
	
	public WaitPage() {

		super(Title.PacketFilterUrl);
		 
		title = Title.WAITPAGE;
	}

	public WaitPage(int timeout) {

		super(Title.PacketFilterUrl);
		 
		title = Title.WAITPAGE;
		this.timeout = timeout; //TODO need to implement timeout formatting, ie ability to pass timeout in human readable format
								// ie WaitPage('2h') or WaitPage('2min') WaitPage('2sec')
								// implement time ranges since.. through..
	}

	@Override
	void updateOptions() {
		//  
		// Rule r = new Rule();
		// r.addNewFieldToRule("empty", "empty");
		// this.rulesMap.add(r);
	}

/* 
 * (non-Javadoc)
 * @see firewall.Page#run()
 * we override run() method since we don't need to send any http requests but just wait specified amount of time before next request to be sent
 */
	public Page run() {
		int t = 1000;
		while (timeout >= 0) {
			try {
				System.out.print("Wait is in progres .." + timeout / 1000
						+ " sec to go\n");
				timeout = timeout - t;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this;

	}
}