package tests;

import java.util.LinkedList;

import org.junit.Test;

import firewall.Firewall;
import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PageProcessor;

public class TestDefault {

	
	@Test
	public void testDefault() {
		LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());
		try{
			new Firewall(PacketFilterActionsQueue);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
