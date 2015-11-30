package firewall;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;

import cmdlineargs.CmdLineParameters;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.PageProcessorFabric;
import PageProcessor.PageResult;

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

	public static final String RESPONSE_NOK="Another client has the SDB write lock";
	
	public static final Logger logger=Logger.getLogger(Firewall.class.getName());
	
	
	public static final String deviceCode = "3287024592";
	private LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();
	public static PageResult result=null;

	CmdLineParameters cmd=CmdLineParameters.getInstance();

	// TODO Auto-generated method stub


	public Firewall(List<PageProcessor> list) throws Exception {

		PacketFilterActionsQueue=(LinkedList<PageProcessor>)list;

		/*
		 * run through all tasks in TaskQueue
		 */

		ExecutorService exec=Executors.newCachedThreadPool();
		
		Future<PageResult> res=exec.submit(new Callable<PageResult>() {
			PageProcessorFabric pf = PageProcessorFabric.getInstance();
			public PageResult call() {
				PageProcessor result=null;
				while (!PacketFilterActionsQueue.isEmpty()) {
					
					logger.info(String.format("--------- PacketFilterActionsQueue content\n[ %s ] --------------------",PacketFilterActionsQueue.toString() ));
					
					PageProcessor p = PacketFilterActionsQueue.remove();

					try {
						result = p.run();

						if (!p.getTitle().equals(result.getTitle())
								&& result.getTitle().equals(Title.LOGIN)) {
							PacketFilterActionsQueue.addFirst(p);
							PacketFilterActionsQueue.addFirst(result);
							logger.info(String.format("--------- adding PageProcessor to the queue again because of Login\n %s --------------------",p.toString() ));
						}
						
						logger.info(String.format("--------- PageRequest %s returned with result %s",result.getClass().toString(),result.getErrormessage()));
						if (Firewall.RESPONSE_NOK.equals(result.getErrormessage())){
						logger.info(String.format("--------- adding PageProcessor to the queue again because of Response_NOK\n %s --------------------",p.toString() ));	
							PacketFilterActionsQueue.addFirst(p);
						}
						
						/*
						 * we remove it because the population of Requests queue is delegated to RequestQueueFabric
						 * originally we issued 1 pagedelete request, but if in resulting rules set there was a rule/rules to be deleted we added pagedelete request here
						 * now RequestQueueFabric should populate Deleterequests in accordance with options provided in command line
						 * 
						if ((p instanceof PacketFilterPageDelete)
								&& (!((PacketFilterPageDelete) p).isEmptyExistingRulesMap())) {
							//PacketFilterActionsQueue.addFirst(p);
							
							cmd.setDestip(((PacketFilterPageDelete) p).getDestip());
							PacketFilterActionsQueue.addFirst(pf.create("drop"));
							logger.info(String.format("--------- adding PageProcessor to the queue again because ((PacketFilterPageDelete) p).isEmptyExistingRulesMap() is not empty\n %s --------------------",p.toString() ));	
						}
						
						*/
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
				
				}
				return (null!=result)? new PageResult(result): null;
				
			}
		});
		
		result=res.get();
		
//		p.start();
		
	//	p.join();
	}
	

}
