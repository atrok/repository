package firewall;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Start {

	  @Parameter
	  private List<String> parameters = new ArrayList<String>();
	 
	  @Parameter(names = "-destip", description = "IP address to be added as firewall rule")
	  private String destip;
	 
	  @Parameter(names = "-action", description = "Action to be applied to the packets (drop/pass). Firewall rule will affect TCP and UDP packets")
	  private String action;
	 
	  @Parameter(names = "-time", description = "Duration of action, %s(econds)/%m(inutes)/%h(ours)")
	  private int timeout;

	  
	public static void main(String[] args) {
		
		Start jct = new Start();
		String[] argv = args;
		new JCommander(jct, argv);
		 
		//Assert.assertEquals(jct.verbose.intValue(), 2);
		
		


		LinkedList<Page> PacketFilterActionsQueue = new LinkedList<Page>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl));

		PacketFilterActionsQueue.add(new WaitPage(jct.timeout));

		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction(jct.action)
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP(jct.action).setProt("udp"))); // add
																		// rules
																		// specified
																		// in
																		// LinkedList<Rule>
																		// rules
		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction(jct.action)
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP(jct.destip).setProt("tcp")));
		
		try {
			new Firewall(new LinkedList<Page>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
//TODO write argument validators

