package cmdlineargs;

import java.util.List;

import com.beust.jcommander.Parameter;

import firewall.Rule;


public class CmdLineParameters  {
	

	  @Parameter(names = "-destip", description = "IP address to be added as firewall rule", validateWith = IPvalidator.class, required = true)
	  private String destip;
	 
	  @Parameter(names = "-action", description = "Action to be applied to the packets (drop/pass). Firewall rule will affect TCP and UDP packets", validateWith = ActionValidator.class, required = true)
	  private String action;
	  
	  @Parameter(names = "-proto", description = "Protocol of packets an action applies to (tcp/udp/both). If option is missed then both protocols are affected", validateWith = ProtoValidator.class)
	  private String proto;
	  
	  private String[] protoArr;
	 
	  @Parameter(names = "-time", description = "Duration of action, %s(econds)/%m(inutes)/%h(ours)", converter = TimeConverter.class, validateWith = TimeArgValidator.class,required = false)
	  private int timeout;
	  
	  private static CmdLineParameters cmd = new CmdLineParameters();
	  
	  private CmdLineParameters(){}
	  
	  public static CmdLineParameters getInstance(){return cmd;}
	  
	  private Rule[] rule=null;

	  
	public String getDestip() {
		return destip;
	}

	public void setDestip(String destip) {
		this.destip = destip;
	}

	public String getAction() {
		return action;
	}

	
	public void setAction(String action) {
		this.action = action;
	}

	public String[] getProto() {
		return protoArr;
	}

	
	public void setProto(String proto) {
		if (proto=="both"){
			String[] p= {"tcp","udp"};
			protoArr=p;
		}else{
			protoArr[0]=proto;
		}
		
	}
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	  
	public Rule[] getRules(){
		
		
		for (int i=0;i>protoArr.length;i++ ){
			rule[i]=new Rule()
				.setAction(action)
				.setDestIP(destip)
				.setProt(protoArr[i]);
		}
		return rule;
	}
	
	public Object[] toArray(){
		
		return new Object[]{getAction(),getRules()};
	}
	  

}
