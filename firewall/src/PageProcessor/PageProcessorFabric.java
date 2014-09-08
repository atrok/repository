package PageProcessor;

import firewall.Rule;
import firewall.Title;

public class PageProcessorFabric {

	/**
	 * @param args
	 */
	//private static PageProcessorFabric pf=new PageProcessorFabric();
	
	private PageProcessorFabric(){}
	
/*	public static PageProcessorFabric getInstance(){
		return pf;
	}*/
	

	public static PageProcessor create(Object[] str){
		
		
		switch ((String)str[0]){
		case Title.LOGIN: return new LoginPageProcessor(Title.LoginUrl);
		case Title.PACKETFILTER:
		case "default":
			return new PacketFilterPageDefault(Title.PacketFilterUrl);
		case "delete": 
			String destip=(String)str[1];
			return new PacketFilterPageDelete(Title.PacketFilterUrl,destip);
		case "drop":
		case "pass":
			return new PacketFilterPageAdd(Title.PacketFilterUrl, ((Rule)str[1]).setSourceIP("0.0.0.0-255.255.255.255"));
		default: 
			return null;
		}
	}

}
