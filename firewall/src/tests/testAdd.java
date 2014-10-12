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

public class testAdd {

		@Test
		public void test(){
			
			LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();

			PacketFilterActionsQueue.add(new PacketFilterPageDefault());
			
			PacketFilterActionsQueue.add(new PacketFilterPageAdd(
					Title.PacketFilterUrl, new Rule().setAction("drop")
							.setSourceIP("0.0.0.0-255.255.255.255")
							.setDestIP("192.168.1.124").setProt("udp"))); // add
																			// rules
																			// specified
																			// in
																			// LinkedList<Rule>
																			// rules
			PacketFilterActionsQueue.add(new PacketFilterPageAdd(
					Title.PacketFilterUrl, new Rule().setAction("drop")
							.setSourceIP("0.0.0.0-255.255.255.255")
							.setDestIP("192.168.1.124").setProt("tcp"))); // add
																			// rules
																			// specified
																			// in
																			// LinkedList<Rule>
																			// rules
			try {
				new Firewall(PacketFilterActionsQueue);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}


	}

