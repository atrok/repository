package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import PageProcessor.PacketFilterPageAdd;
import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.WaitPageProcessor;
import firewall.Firewall;
import firewall.Rule;
import firewall.Title;


public class TestFirewall {

	@Test
	public void test() {
		LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl,"192.168.1.70"));

		PacketFilterActionsQueue.add(new WaitPageProcessor(3600000));

		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction("drop")
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP("192.168.1.70").setProt("udp"))); // add
																		// rules
																		// specified
																		// in
																		// LinkedList<Rule>
																		// rules
		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction("drop")
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP("192.168.1.70").setProt("tcp")));

		try {
			new Firewall(PacketFilterActionsQueue);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
