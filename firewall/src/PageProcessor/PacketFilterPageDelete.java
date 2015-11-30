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
		// In case of delete request we first need to obtain list of existing server rules
		// then we check what rule match to IP address of the rule we want to delete
		// and then take last rule  that matches out of existing server rules and assign it to ruleToRun
		//
		if (!serverSideRulesMap.isEmpty()) {
			for (Rule r : serverSideRulesMap) {

				// HashMap h = r.getMap();
				r.addNewFieldToRule("enfilter", "on");
				r.addNewFieldToRule("pass", "pass");//why we need this?
				r.dropEdit();
				logger.info(this.getClass().getName() + " destip:" + destip
						+ " r.getDestIP()" + r.getDestIP());

				if (null != destip) {// delete those rules which destip matches
										// ip of rules presented on the
										// PageProcessor

					if (destip.equals(r.getDestIP()))
					//	PageProcessor.rulesMap.add(r); //we populated rulesMap to be executed but it makes sense to attach Rule per PageRequest
						ruleToRun=r;
				} else { // in opposite case delete all rules on the page
					//PageProcessor.rulesMap.add(r);
					ruleToRun=r;
				}

			}
		}
		/*
		 * 26.10.2014 17:20 else {//TODO this is may not be required because of
		 * code refactoring in Page.run() PageProcessor.rulesMap.add(new
		 * Rule()); }
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see firewall.Page#isEmptyExistingRulesMap() we need to override it to
	 * add ability to delete specific rules only for that we need to check that
	 * among existing rules there is no rules having destip we aim to delete
	 */
	@Override
	public boolean isEmptyExistingRulesMap() {
		if (null != destip) {// if we need to delete specific rules only
			for (Rule r : serverSideRulesMap)
				if (r.getDestIP().equals(destip))
					return false;
			return true;
		}
		// in case we delete all rules we check if there is any content in the
		// list or not
		return serverSideRulesMap.isEmpty();
	}

	public String toString() {
		return this.getClass().toString() + "\n" + super.toString();

	}
}