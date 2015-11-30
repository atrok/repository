package PageProcessor;

import firewall.Rule;
import firewall.Title;

public class PacketFilterPageChangeProcessor extends PageProcessor {

	private String destip;
	private String action;

	public PacketFilterPageChangeProcessor(String url) {
		super(url);

		title = Title.PACKETFILTER;
	}

	public PacketFilterPageChangeProcessor(String url, String destip,
			String action) {
		super(url);

		title = Title.PACKETFILTER;
		this.destip = destip;
		this.action = action;
	}

	public PacketFilterPageChangeProcessor(String url, Rule rule){
		super(url);
		
		destip=rule.getDestIP();
		action=rule.getAction();
		
	}	
	@Override
	void updateOptions() {
		//
		// Rule r = new Rule();

		if (!serverSideRulesMap.isEmpty()) {
			for (Rule r : serverSideRulesMap) {
				if (null != destip) {// change those rules where destip matches
					// ip of rules presented on the
					// PageProcessor

					if (destip.equals(r.getDestIP()))

						// HashMap h = r.getMap();
						r.addNewFieldToRule("enfilter", "on");
					r.addNewFieldToRule("Change", "Change");
					r.setAction(action);
					r.dropRemove();
					logger.info(this.getClass().getName() + " destip:" + destip
							+ " r.getDestIP()" + r.getDestIP());

					// PageProcessor.rulesMap.add(r); //we populated rulesMap to
					// be executed but it makes sense to attach Rule per
					// PageRequest
					ruleToRun = r;
				} else { // in opposite case delete all rules on the page
					// PageProcessor.rulesMap.add(r);
					ruleToRun = r;
				}

			}
		}

	}

	public String toString() {
		return this.getClass().getName() + ":\n" + super.toString();

	}

}