package PageProcessor;

import firewall.Rule;
import firewall.Title;

public class PacketFilterPageDefault extends PageProcessor {

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
		//this.rulesMap.add(r);
		ruleToRun=r;
	}

	public String toString(){
		return this.getClass().toString()+"\n"+super.toString();
		
	}

}