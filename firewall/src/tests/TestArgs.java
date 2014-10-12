package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import PageProcessor.PacketFilterPageAdd;
import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.WaitPageProcessor;
import cmdlineargs.CmdLineParameters;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import firewall.Firewall;
import firewall.RequestQueueFabric;
import firewall.Rule;
import firewall.Title;

public class TestArgs {

	@Test
	public void test() {
		CmdLineParameters params = CmdLineParameters.getInstance();
	    JCommander cmd = new JCommander(params);
	    String args[]={"-action","drop","-destip","192.168.1.70","-time"," 10s"};
	    try {
	        cmd.parse(args);

	    } catch (ParameterException ex) {
	        System.out.println(ex.getMessage());
	        cmd.usage();
	        System.exit(1);
	    }

		//Assert.assertEquals(jct.verbose.intValue(), 2);
		
		

	    RequestQueueFabric rq=new RequestQueueFabric();
		LinkedList<PageProcessor> PacketFilterActionsQueue = (LinkedList<PageProcessor>)rq.getQueue();

		//PacketFilterActionsQueue.add(new PacketFilterPageDefault());
/*
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
