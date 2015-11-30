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
public class CommandChange implements Command{
	

	@Parameter(names = "-destip", description = "IP address to be added as firewall rule", validateWith = IPvalidator.class, required = true)
	private String destip = null;

	@Parameter(names = "-action", description = "Action to be requested to the packets (drop/pass/status). Firewall rule will affect TCP and UDP packets", validateWith = ActionValidator.class, required = true)
	private String action = null;

	@Parameter(names = "-proto", description = "Protocol of packets an action applies to (tcp/udp/both). If option is missed then both protocols are affected", validateWith = ProtoValidator.class)
	private String proto = null;

	private String[] protoArr = null;

	@Parameter(names = "-time", description = "Duration of action, %s(econds)/%m(inutes)/%h(ours)", converter = TimeConverter.class, validateWith = TimeArgValidator.class, required = false)
	private int timeout;

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
