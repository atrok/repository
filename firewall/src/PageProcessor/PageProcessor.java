package PageProcessor;

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

import firewall.Rule;
import static firewall.Title.*;

public abstract class PageProcessor{
	/*
	 * every http request is represented by PageProcessor object which consists of 
	 * url
	 * options of corresponding POST request
	 * options are kept in Rule object and are collected under rulesMap List
	 * the design is to have 1 Rule per PageProcessor object. It makes sense since Rule represents options to be passed with POST request
	 * that means every new http request should have its own options ie Rule
	 * 
	 * So next PageProcessor classes represent different http requests to service which url we pass along with PageProcessor object
	 * we need to have different Classes for every http request as each bears specific purpose and has its own set of specific options 
	 * Names of classes speak for themselves.
	 * 
	 * PageProcessor object is abstract class with template method run() common for all inherited classes but WaitPage
	 * WaitPageProcessor is needed to set a delay between execution of Pages in PageQueue
	 * 
	 */
	protected HashMap<String, String> options = new HashMap<String, String>();
	protected String url;

	protected static Document cachedPage = null;
	protected static LinkedList<Rule> rulesMap = new LinkedList<Rule>();
	protected static LinkedList<Rule> existingRulesMap = new LinkedList<Rule>();

	protected String errormessage;
	protected String title;

	public PageProcessor(String url) {
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

	public PageProcessor run() throws IOException {
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
		return PageProcessorFabric.getInstance().create(action);

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