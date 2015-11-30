package cmdlineargs.commands;

import PageProcessor.PageProcessor;

public interface Command {
	public void initPageProcessor();
	public int getRulesSize();
	public PageProcessor getNextPageProcessor();

}
