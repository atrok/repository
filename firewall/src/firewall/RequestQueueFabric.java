package firewall;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cmdlineargs.CmdLineParameters;
import PageProcessor.PacketFilterPageDefault;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.PageProcessorFabric;
import PageProcessor.WaitPageProcessor;

public class RequestQueueFabric {
	
	LinkedList<PageProcessor> PacketFilterActionsQueue = new LinkedList<PageProcessor>();
	CmdLineParameters params=CmdLineParameters.getInstance();

	/*
	 * every new set of requests to firewall should start off getting page body back
	 * we need it to obtain value of nonce variable used in every request to add/change/delete/login
	 */

	public RequestQueueFabric(){
		PacketFilterActionsQueue.add(new PacketFilterPageDefault());
		if (null!=params.getRules())
			parse();
	}
	
	public void parse(){
		
		PacketFilterActionsQueue.add(new PacketFilterPageDelete( 
				Title.PacketFilterUrl,params.getDestip()));
		
		//PageProcessor[] p=PageProcessorFabric.getInstance().createFromCommandLineParameters();
		Collections.addAll(PacketFilterActionsQueue, PageProcessorFabric.getInstance().createFromCommandLineParameters());
		
		if (params.getTimeout()!=0)
		{
			
			PacketFilterActionsQueue.add(new WaitPageProcessor(params.getTimeout()));
			PacketFilterActionsQueue.add(new PacketFilterPageDelete( 
					Title.PacketFilterUrl,params.getDestip()));		
			
			switch(params.getAction()){
			
			case "drop":
				params.setAction("pass");
				break;
			case "pass":
				params.setAction("drop");
				break;
			default:
				break;
			}
			

			
			Collections.addAll(PacketFilterActionsQueue, PageProcessorFabric.getInstance().createFromCommandLineParameters());
			
			
		}
	}
	
	public List<PageProcessor> getQueue(){return PacketFilterActionsQueue;}
}
