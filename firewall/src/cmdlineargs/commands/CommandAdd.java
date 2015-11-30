package cmdlineargs.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PageProcessor.PacketFilterPageAdd;
import PageProcessor.PacketFilterPageChangeProcessor;
import PageProcessor.PacketFilterPageDelete;
import PageProcessor.PageProcessor;
import PageProcessor.WaitPageProcessor;
import cmdlineargs.ActionValidator;
import cmdlineargs.CmdLineParameters;
import cmdlineargs.IPvalidator;
import cmdlineargs.ProtoValidator;
import cmdlineargs.TimeArgValidator;
import cmdlineargs.TimeConverter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import firewall.Rule;
import firewall.Title;

@Parameters(separators = "=", commandDescription = " command to add firewall rule")
public class CommandAdd implements Command {

	List<PageProcessor> pageProcessor = new ArrayList<PageProcessor>();
	List<Rule> rules = new ArrayList<Rule>();

	CmdLineParameters cmd;

	@Parameter(names = "-destip", description = "IP address to be added as firewall rule", validateWith = IPvalidator.class, required = true)
	private String destip = null;

	@Parameter(names = "-action", description = "Action to be requested to the packets (drop/pass/status). Firewall rule will affect TCP and UDP packets", validateWith = ActionValidator.class, required = true)
	private String action = null;

	@Parameter(names = "-proto", description = "Protocol of packets an action applies to (tcp/udp/both). If option is missed then both protocols are affected", validateWith = ProtoValidator.class)
	private String proto = null;

	private String[] protoArr = null;

	@Parameter(names = "-time", description = "Duration of action, %s(econds)/%m(inutes)/%h(ours)", converter = TimeConverter.class, validateWith = TimeArgValidator.class, required = false)
	private int timeout;

	public void initPageProcessor() {
		if (null == cmd) {
			cmd = CmdLineParameters.getInstance();
			cmd.setDestip(destip);
			cmd.setAction(action);
			cmd.setProto();
			rules = Arrays.asList(cmd.getRules());

			// create page request per Rule. Multiple rules could occur if proto
			// was null (ie both);
			for (Rule r : rules) {
				pageProcessor.add(new PacketFilterPageAdd(
						Title.PacketFilterUrl, r));

			}
			if (timeout != 0) { // somebody added timeout so we need to 1)
								// enable Rule and then 2) disable it after
								// timeout expiration

				// 1. start timeout;

				pageProcessor.add(new WaitPageProcessor(timeout));

				// 2. Disable Rule. FOr this we change action value to opposite
				// value
				String tempAction = action;

				switch (action) {

				case "drop":
					tempAction = "pass";
					break;
				case "pass":
					tempAction = "drop";
					break;
				default:
					break;
				}

				for (Rule r : rules) {
					r.setAction(tempAction);
					pageProcessor.add(new PacketFilterPageChangeProcessor(
							Title.PacketFilterUrl, r));
				}

			}

		}

	}

	public int getRulesSize() {
		return rules.size();
	}

	public PageProcessor getNextPageProcessor() {
		return new PacketFilterPageAdd(Title.PacketFilterUrl, rules.remove(0));
	}
}
