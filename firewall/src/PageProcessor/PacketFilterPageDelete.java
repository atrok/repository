package PageProcessor;

import firewall.Rule;
import firewall.Title;

public class PacketFilterPageDelete extends PageProcessor {

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

				if (null != destip) {// delete those rules which destip matches ip of rules presented on the PageProcessor  
									
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