package tests;

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

public class testDelete {

	@Test
	public void testDelete(){
		
		LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());
		
		PacketFilterActionsQueue.add(new WaitPageProcessor(1000));

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl)); // this deletes all rules on the page
		
		
		PacketFilterActionsQueue.add(new PacketFilterPageDelete( 
				Title.PacketFilterUrl,"192.168.1.69")); // this deletes only rules that matches to ipaddress

		try {
			new Firewall(PacketFilterActionsQueue);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


}
