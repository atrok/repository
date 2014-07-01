package firewall;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class testDelete {

	@Test
	public void testDelete(){
		
		LinkedList<Page> PacketFilterActionsQueue = new LinkedList<Page>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());
		
		PacketFilterActionsQueue.add(new WaitPage(1000));

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl));
		
		PacketFilterActionsQueue.add(new WaitPage(1000));
		
		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction("drop")
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP("192.168.1.70").setProt("udp"))); // add
																		// rules
																		// specified
																		// in
																		// LinkedList<Rule>
																		// rules
		PacketFilterActionsQueue.add(new WaitPage(1000));
		
		PacketFilterActionsQueue.add(new PacketFilterPageAdd(
				Title.PacketFilterUrl, new Rule().setAction("drop")
						.setSourceIP("0.0.0.0-255.255.255.255")
						.setDestIP("192.168.1.69").setProt("tcp")));
		
		PacketFilterActionsQueue.add(new WaitPage(1000));
		
		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl,"192.168.1.69"));

		try {
			new Firewall(PacketFilterActionsQueue);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


}
