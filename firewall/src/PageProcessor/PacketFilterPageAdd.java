package PageProcessor;

import firewall.Rule;
import firewall.Title;

public class PacketFilterPageAdd extends PageProcessor {


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
		ruleToRun=rule; 
		title = Title.PACKETFILTER;
	}

	@Override
	void updateOptions() {

			ruleToRun.addNewFieldToRule("Add", "Add");
			ruleToRun.addNewFieldToRule("enfilter", "on");
			ruleToRun.dropRemove();
			ruleToRun.dropEdit();
	}


	public String toString(){
		return this.getClass().toString()+"\n"+super.toString();
		
	}

}