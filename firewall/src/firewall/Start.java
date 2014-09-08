package firewall;

import java.util.LinkedList;
import PageProcessor.PacketFilterPageAdd;
import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.WaitPageProcessor;
import cmdlineargs.CmdLineParameters;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class Start {


	
	private static CmdLineParameters params = CmdLineParameters.getInstance();
	
	public static void main(String[] args) {
		
		//String args2[]={"-action","drop","-destip","192.168.1.70","-time"," 10%s"};
		
		
	    JCommander cmd = new JCommander(params);
	    try {
	        cmd.parse(args);


	    } catch (ParameterException ex) {
	        System.out.println(ex.getMessage());
	        cmd.usage();
	        System.exit(1);
	    }

		//Assert.assertEquals(jct.verbose.intValue(), 2);
		
		

/*
		LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl));

		PacketFilterActionsQueue.add(new WaitPageProcessor(params.getTimeout()));

		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction(params.getAction())
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP(params.getDestip()).setProt("udp"))); // add
																		// rules
																		// specified
																		// in
																		// LinkedList<Rule>
																		// rules
		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction(params.getAction())
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP(params.getDestip()).setProt("tcp")));
*/
		
		try {
			new Firewall(PacketFilterActionsQueue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
	}
}

