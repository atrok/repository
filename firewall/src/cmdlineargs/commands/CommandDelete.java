package cmdlineargs.commands;

import PageProcessor.PageProcessor;
import cmdlineargs.ActionValidator;
import cmdlineargs.IPvalidator;
import cmdlineargs.ProtoValidator;
import cmdlineargs.TimeArgValidator;
import cmdlineargs.TimeConverter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters (separators="=", commandDescription=" command to add firewall rule")
public class CommandDelete implements Command{
	

	@Parameter(names = "-destip", description = "IP address to be delete out of firewall rules", validateWith = IPvalidator.class, required = true)
	private String destip = null;

	@Parameter(names = "-proto", description = "Protocol of packets an action applies to (tcp/udp/both). If option is missed then both protocols are affected", validateWith = ProtoValidator.class)
	private String proto = null;

	@Override
	public void initPageProcessor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRulesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageProcessor getNextPageProcessor() {
		// TODO Auto-generated method stub
		return null;
	}	

}
