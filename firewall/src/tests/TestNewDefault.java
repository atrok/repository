package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import firewall.Firewall;
import firewall.RequestQueueFabric;

public class TestNewDefault {

	@Test
	public void test() {
		try{
			RequestQueueFabric r=new RequestQueueFabric();
			new Firewall(r.getQueue());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
