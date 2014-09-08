package cmdlineargs;

import com.beust.jcommander.Parameter;


public class CmdLineParameters  {
	

	  @Parameter(names = "-destip", description = "IP address to be added as firewall rule", validateWith = IPvalidator.class, required = true)
	  private String destip;
	 
	  @Parameter(names = "-action", description = "Action to be applied to the packets (drop/pass). Firewall rule will affect TCP and UDP packets", validateWith = ActionValidator.class, required = true)
	  private String action;
	 
	  @Parameter(names = "-time", description = "Duration of action, %s(econds)/%m(inutes)/%h(ours)", converter = TimeConverter.class, validateWith = TimeArgValidator.class,required = false)
	  private int timeout;


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

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	  
	  

}
