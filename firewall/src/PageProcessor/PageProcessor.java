package PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	protected static final Logger logger=Logger.getLogger(PageProcessor.class.getName());

	protected static Document cachedPage = null;
	protected static LinkedList<Rule> rulesMap = new LinkedList<Rule>();///??? why we need this?
	protected static LinkedList<Rule> serverSideRulesMap = new LinkedList<Rule>();
	protected Rule ruleToRun; //

	protected static String errormessage;
	
	public String getErrormessage() {
		return errormessage;
	}

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
		logger.info(this.getClass().getName()+ " entering Page.run()\n There is next Rule to:");
		/*
		 * we don't need it, there is one Rule object per Request, ruleToRun
		 * 
		if (!rulesMap.isEmpty()) {
			logger.info(rulesMap.toString());

			Rule r = rulesMap.remove();
			if (!r.isMapEmpty())
				options.putAll(r.getMap());
		}
*/
		//we create cached page when run PageDefault request, as result we obtain noonce value. That's why PageDefault should be first request in the queue
		if (null != cachedPage) {

			String nonce = cachedPage.select("input[name*=nonce]")
					.attr("value");
			options.put("nonce", nonce);
			ruleToRun.add("nonce", nonce);
		}

		logger.info("Request to be sent:" + url);
		logger.info("options to be sent:"+ruleToRun.toString());

		cachedPage = Jsoup.connect(url)
				.data(ruleToRun.getMap())
				.post();

		String action = cachedPage.select("form").attr("action");
		logger.info("Response gained (form value):" + action);

		errormessage = cachedPage.select("div#error-message-text").text();

		action = cachedPage.select("title").text();

		logger.info("Title of page: " + action + "\nError message:"
				+ errormessage);

		serverSideRulesMap = getRules();

		// }
		return PageProcessorFabric.getInstance().create(action);
		//
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

				Elements th = el.select("th");

				if (!th.isEmpty())// get headers for rule fields
					for (Element e : th) 
						ar.add(e.html().toString());
					
				

				Elements td = el.select("td"); // get values
				if (!td.isEmpty()) {
					int i = 0;
					for (Element e : td) {

						if (e.select("input").hasAttr("type")) {
							list.add(ar.get(i), //key
									e.select("input").attr("name").toString());//value
						} else {
							list.add(ar.get(i), e.html().toString());
						}
						i++;
					}
				}
				
				if (!list.isEmpty()) {
					rulesMap.add(list);
					logger.info("List of rules in effect on server side:\n"+list.toString());
				}

			}
			return rulesMap;
		}
		return PageProcessor.rulesMap;
		// System.out.print(doc.html().toString());
	}

	public String getTitle() {
		return title;
	}

	public boolean isEmptyExistingRulesMap() {
		return serverSideRulesMap.isEmpty();
	}

	abstract void updateOptions();


	@Override
	public String toString(){
		
		return String.format(" ---- Page Result ----\nExisting rules:\n%s\nlist of options:\n%s\n", serverSideRulesMap.toString(), getOptions().toString());
		
		
	}

}