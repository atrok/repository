package firewall;

public class PageFabric {

	/**
	 * @param args
	 */
	private static PageFabric pf=new PageFabric();
	
	private PageFabric(){}
	
	public static PageFabric getInstance(){
		return pf;
	}
	

	public Page getPage(String... str){
		
		switch (str[0]){
		case Title.LOGIN: return new LoginPage(Title.LoginUrl);
		case Title.PACKETFILTER: return new PacketFilterPageDefault(Title.PacketFilterUrl);
		case "delete": 
			String destip=str[1];
			return new PacketFilterPageDelete(Title.PacketFilterUrl,destip);
		default: 
			return null;
		}
	}

}
