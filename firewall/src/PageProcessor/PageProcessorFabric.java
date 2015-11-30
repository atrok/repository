package PageProcessor;

import cmdlineargs.CmdLineParameters;
import firewall.Rule;
import firewall.Title;

public class PageProcessorFabric {

	/**
	 * @param args
	 */
	//private static PageProcessorFabric pf=new PageProcessorFabric();
	
	private Rule rule=null;
	
	private CmdLineParameters cmd=CmdLineParameters.getInstance();
	private static PageProcessorFabric pf=new PageProcessorFabric();
	
	private PageProcessorFabric(){}
	
	public static PageProcessorFabric getInstance(){
		return pf;
	}
	
//public static PageProcessorFabric getInstance(){return pf;}
	public PageProcessor create(String str){
		
		
		switch (str){
		case Title.LOGIN: return new LoginPageProcessor(Title.LoginUrl);
		case Title.PACKETFILTER:
		case "default":
			return new PacketFilterPageDefault(Title.PacketFilterUrl);
		case "drop":
			String destip=cmd.getDestip();
			return new PacketFilterPageDelete(Title.PacketFilterUrl,destip);

		case "pass":
			return new PacketFilterPageAdd(Title.PacketFilterUrl, rule.setSourceIP("0.0.0.0-255.255.255.255"));
		default: 
			return null;
		}
	}
	
	public PageProcessor[] createFromCommandLineParameters(){
		Rule[] r=cmd.getRules(); /// Rule is a list of fields and options that assembles POST request to remote server
		int l =r.length;
		PageProcessor[] p=new PageProcessor[l];
		
		if(null!=r){
		for (int i=0;i<l;i++){
			rule=r[i];
			p[i]=create(cmd.getAction());
		}
		}
		return p;
		
	}

}
