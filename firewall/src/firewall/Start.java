package firewall;

import cmdlineargs.CmdLineParameters;
import cmdlineargs.commands.Command;
import cmdlineargs.commands.CommandAdd;
import cmdlineargs.commands.CommandChange;
import cmdlineargs.commands.CommandDelete;
import cmdlineargs.commands.CommandLineParser;
import cmdlineargs.commands.CommandStatus;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class Start {


	
	private static CmdLineParameters params = CmdLineParameters.getInstance();
	
	public static void main(String[] args) {
		
		//String args2[]={"-action","drop","-destip","192.168.1.70","-time"," 10%s"};
		
		
	    //JCommander cmd = new JCommander(params);
		
		CommandLineParser cmdParser = new CommandLineParser();
		JCommander commander=cmdParser.getCommander();
	    try {
	    	

	    	commander.parse(args);
	    	Command cmd = cmdParser.getCommandObj(commander.getParsedCommand());
	    	
	    	
	        RequestQueueFabric r=new RequestQueueFabric(cmd);
			new Firewall(r.getQueue());
			
	    } catch (ParameterException ex) {
	        System.out.println(ex.getMessage());
	        commander.usage();
	        System.exit(1);
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
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
				
	}
}

