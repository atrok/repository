package PageProcessor;

import java.util.LinkedList;

import firewall.Rule;
import firewall.Title;

public class PacketFilterPageAdd extends PageProcessor {

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

	// it's more convenient way to handle PageProcessor with 1 rule than 1 PageProcessor with
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