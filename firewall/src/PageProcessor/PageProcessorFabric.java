package PageProcessor;

import firewall.Title;

public class PageProcessorFabric {

	/**
	 * @param args
	 */
	private static PageProcessorFabric pf=new PageProcessorFabric();
	
	private PageProcessorFabric(){}
	
	public static PageProcessorFabric getInstance(){
		return pf;
	}
	

	public PageProcessor getPageProcessor(String... str){
		
		switch (str[0]){
		case Title.LOGIN: return new LoginPageProcessor(Title.LoginUrl);
		case Title.PACKETFILTER: return new PacketFilterPageDefault(Title.PacketFilterUrl);
		case "delete": 
			String destip=str[1];
			return new PacketFilterPageDelete(Title.PacketFilterUrl,destip);
		default: 
			return null;
		}
	}

}
