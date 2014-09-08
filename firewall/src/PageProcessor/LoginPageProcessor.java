package PageProcessor;

import firewall.Firewall;
import firewall.Rule;
import firewall.Title;

public class LoginPageProcessor extends PageProcessor {

	public LoginPageProcessor(String url) {
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