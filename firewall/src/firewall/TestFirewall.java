package firewall;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class TestFirewall {

	@Test
	public void test() {
		LinkedList<Page> PacketFilterActionsQueue = new LinkedList<Page>();

		PacketFilterActionsQueue.add(new PacketFilterPageDefault());

		PacketFilterActionsQueue.add(new PacketFilterPageDelete(
				Title.PacketFilterUrl));

		PacketFilterActionsQueue.add(new WaitPage(3000));

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
