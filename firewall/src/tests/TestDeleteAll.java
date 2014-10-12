package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import cmdlineargs.CmdLineParameters;
import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.PageProcessorFabric;
import PageProcessor.WaitPageProcessor;
import firewall.Firewall;
import firewall.Start;
import firewall.Title;

public class TestDeleteAll {

	@Test
	public void test() {
		LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());
		
		PacketFilterActionsQueue.add(new WaitPageProcessor(1000));

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl)); // this deletes all rules on the page
		
		
		try {
			new Firewall(PacketFilterActionsQueue);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

