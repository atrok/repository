package PageProcessor;

import java.util.List;

import firewall.Rule;

public final class PageResult {
	
	private PageProcessor p;
	
	public PageResult(PageProcessor p){
		this.p=p;
	}

	public List<Rule> getExistingRules() {
		return PageProcessor.serverSideRulesMap;
	}

	public String getErrormessage() {
		return PageProcessor.errormessage;
	}
	
	public String getUrl(){
		return p.getUrl();
	}
	
	public String toString(){
		return p.toString();
	}
	
}
