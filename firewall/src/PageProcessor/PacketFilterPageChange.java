package PageProcessor;

import firewall.Rule;
import firewall.Title;

public class PacketFilterPageChange extends PageProcessor {

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