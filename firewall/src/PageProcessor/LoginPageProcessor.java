package PageProcessor;

import firewall.Firewall;
import firewall.Rule;
import firewall.Title;

public class LoginPageProcessor extends PageProcessor {

	public LoginPageProcessor(String url) {
		super(url);
		 
		title = Title.LOGIN;
	}

	void updateOptions() {
		Rule r = new Rule();
		r.addNewFieldToRule("password", Firewall.deviceCode);
		//PageProcessor.rulesMap.add(r);
		ruleToRun=r;
	}
	
	public String toString(){
		return this.getClass().toString()+"\n"+super.toString();
		
	}
}