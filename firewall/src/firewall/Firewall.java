package firewall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

public class Firewall {

	/*
	 * after first execution the connection should return either Login page or
	 * PacketFilter page in case of Login page we need to authenticate if
	 * successful it will return packetfilter page on packetfilter page we need
	 * to check if there are any rules if any then we need to delete those where
	 * destip matches to ip to be blocked/unblocked if there are no rules then
	 * we should add new rules to block TCP and UDP for destip
	 * 
	 * In Queue we put Tasks
	 * 
	 * we can determine set of tasks set of tasks serve one certain purpose, for
	 * instance:
	 * 
	 * Block traffic for certain device, for instance Task Login Task
	 * AddRule(tcp) Task AddRule(udp)
	 * 
	 * allow traffic for certain device, for instance Task Login Task CheckRules
	 * Task DeleteRule( check for rules and delete it )
	 * 
	 * 
	 * Each Task is Page(url,options) where options determine an action to
	 * perform
	 * 
	 * 
	 * rule ( Action(pass) + " " + SourceIP(sourceip) + " " + DestIP(destip) +
	 * " " + Prot(proto) + " " + SourcePort(sourceport) + " " +
	 * DestPort(destport) page (url, options)
	 * 
	 * 
	 * options=nonce+enfilter+action(Add,Change,Delete,Login)+rule
	 * 
	 * urls and its corresponding options are next: /cgi-bin/packetfilter.ha
	 * 
	 * 
	 * ADD: nonce:14962253002cd17a7d1852fb1ef85322666928188093171f enfilter:on
	 * pass:drop sourceip:0.0.0.0-255.255.255.255 destip:192.168.1.70 proto:udp
	 * sourceport: destport: Add:Add
	 * 
	 * CHANGE: (consists of 2 actions)
	 * 
	 * nonce:e891cd53002cda9be8077dc5f654eac1e5e7d25304e3b34c enfilter:on
	 * Edit_37:Edit pass:pass sourceip: destip: proto:tcp sourceport: destport:
	 * 
	 * nonce:59b91c8f002cdaee51ba93c96aead92ee4c9690984aa87c9 enfilter:on
	 * pass:drop sourceip:0.0.0.0-255.255.255.255 destip:192.168.1.70 proto:tcp
	 * sourceport: destport: Change:Change
	 * 
	 * DELETE
	 * 
	 * nonce:229a4bb1002cdb097275a943f0deafdf0e46daa00fb6678c enfilter:on
	 * Remove_37:Delete pass:pass proto:tcp
	 * 
	 * /cgi-bin/login.ha
	 * 
	 * LOGIN nonce:419e614c002cc5f753065c1e458eb134a5f2b58f78afc869,
	 * password:3287024592
	 */
	// global constants and variables

	private boolean ALLOW = true;
	public static final String deviceCode = "3287024592";

	public static final String Login = "Login";
	public static final String Packetfilter = "Packet Filter";

	HashMap<String, String> LoginInputsAdd = new HashMap<String, String>();
	LinkedList<Page> PacketFilterActionsQueue = new LinkedList<Page>();

	// private static final name="pass" value="drop" />
	String errormessage = "";
	String title = "";

	String destip = "192.168.1.70";

	private Document cachedPage;

	// TODO Auto-generated method stub


	public Firewall(LinkedList<Page> Queue) throws Exception {

		PacketFilterActionsQueue=Queue;

		/*
		 * run through all tasks in TaskQueue
		 */

		new Thread(new Runnable() {
			PageFabric pf = PageFabric.getInstance();
			String title = "";

			public void run() {
				while (!PacketFilterActionsQueue.isEmpty()) {
					Page p = PacketFilterActionsQueue.remove();

					try {
						Page result = p.run();

						if (!p.getTitle().equals(result.getTitle())
								&& result.getTitle().equals(Title.LOGIN)) {
							PacketFilterActionsQueue.addFirst(p);
							PacketFilterActionsQueue.addFirst(result);
						}
						if ((p instanceof PacketFilterPageDelete)
								&& (!result.isEmptyExistingRulesMap())) {
							PacketFilterActionsQueue.addFirst(p);
							PacketFilterActionsQueue.addFirst(pf
									.getPage("delete"));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}

			}
		}).start();
		
		
	}

}
