package fsm;

import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PageProcessor;

public enum Input {

RequestPageDefault {
	PageProcessor p=new PacketFilterPageDefault();
	
	public PageProcessor getPage(){
		
		return p.;
	}
	
	
},
RequestPageLogin {
	PageProcessor p=new PacketFilterPageDefault();
	
	public PageProcessor getPage(){
		
		return p;
	}
	
},
RequestPageRuleAdd {
	public String action(){
		return "RequestPageRuleAdd";
	}
	
},

RequestPageRuleDelete {
	public String action(){
		return "RequestPageRuleDelete";
	}
	
},

RequestPageRuleChange {
	public String action(){
		return "RequestPageRuleChange";
	}
	
},

RequesPageWait {
	public String action(){
		return "RequestPageWait";
	}
	
};

	abstract public PageProcessor getPage();
}


