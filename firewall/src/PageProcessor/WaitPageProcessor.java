package PageProcessor;

import firewall.Title;

public class WaitPageProcessor extends PageProcessor {

	private int timeout = 1000;

	public WaitPageProcessor(String url) {
		super(url);
		 
		title = Title.WAITPAGE;
	}

	/*
	 * WaitPageProcessor is needed to set a delay between execution of Pages in PageQueue 
	 * it's a simple implementation of scheduler
	 */
	
	public WaitPageProcessor() {

		super(Title.PacketFilterUrl);
		 
		title = Title.WAITPAGE;
	}

	public WaitPageProcessor(int timeout) {

		super(Title.PacketFilterUrl);
		 
		title = Title.WAITPAGE;
		this.timeout = timeout; //TODO need to implement timeout formatting, ie ability to pass timeout in human readable format
								// ie WaitPage('2h') or WaitPage('2min') WaitPage('2sec')
								// implement time ranges since.. through..
	}

	@Override
	void updateOptions() {
		//  
		// Rule r = new Rule();
		// r.addNewFieldToRule("empty", "empty");
		// this.rulesMap.add(r);
	}

/* 
 * (non-Javadoc)
 * @see firewall.Page#run()
 * we override run() method since we don't need to send any http requests but just wait specified amount of time before next request to be sent
 */
	public PageProcessor run() {
		int t = 1000;
		while (timeout >= 0) {
			try {
				System.out.print("Wait is in progres .." + timeout / 1000
						+ " sec to go\n");
				timeout = timeout - t;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this;

	}
}