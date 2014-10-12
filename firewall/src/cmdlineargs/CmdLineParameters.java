package cmdlineargs;

import java.util.List;

import com.beust.jcommander.Parameter;

import firewall.Rule;

public class CmdLineParameters {

	@Parameter(names = "-destip", description = "IP address to be added as firewall rule", validateWith = IPvalidator.class, required = true)
	private String destip = null;

	@Parameter(names = "-action", description = "Action to be applied to the packets (drop/pass). Firewall rule will affect TCP and UDP packets", validateWith = ActionValidator.class, required = true)
	private String action = null;

	@Parameter(names = "-proto", description = "Protocol of packets an action applies to (tcp/udp/both). If option is missed then both protocols are affected", validateWith = ProtoValidator.class)
	private String proto = null;

	private String[] protoArr = null;

	@Parameter(names = "-time", description = "Duration of action, %s(econds)/%m(inutes)/%h(ours)", converter = TimeConverter.class, validateWith = TimeArgValidator.class, required = false)
	private int timeout;

	private Rule[] rule;

	// static //private Rule[] rule=null;
	static CmdLineParameters cmd = new CmdLineParameters();

	private CmdLineParameters() {

	};

	public static CmdLineParameters getInstance() {
		return cmd;
	}

	public String getDestip() {
		return destip;
	}

	public void setDestip(String destip) {
		this.destip = destip;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String[] getProto() {
		setProto();
		return protoArr;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Rule[] getRules() {
		if (null == protoArr)
			setProto();

		//if (null == rule) {

			Rule[] r = new Rule[protoArr.length];
			for (int i = 0; i < protoArr.length; i++) {

				r[i] = new Rule().setAction(action).setDestIP(destip)
						.setProt(protoArr[i]);
			//}
			rule=r;
		}
		return rule;
	}

	public void setProto() {
		// this.proto=proto;
		if (proto == "both" || null == proto) {
			String[] p = { "tcp", "udp" };
			protoArr = p;
		} else {
			protoArr[0] = proto;
		}

	}

}
