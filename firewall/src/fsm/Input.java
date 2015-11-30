package fsm;

import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PageProcessor;

public enum Input {

RequestPageDefault {
	PageProcessor p=new PacketFilterPageDefault();
	
	public PageProcessor getPage(){
		
		return p;
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

	@Override
	public PageProcessor getPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
},

RequestPageRuleDelete {
	public String action(){
		return "RequestPageRuleDelete";
	}

	@Override
	public PageProcessor getPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
},

RequestPageRuleChange {
	public String action(){
		return "RequestPageRuleChange";
	}

	@Override
	public PageProcessor getPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
},

RequesPageWait {
	public String action(){
		return "RequestPageWait";
	}

	@Override
	public PageProcessor getPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
};

	abstract public PageProcessor getPage();
}


