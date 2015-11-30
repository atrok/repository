package cmdlineargs.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.beust.jcommander.JCommander;

public class CommandLineParser {
	
	JCommander jc;
	
	static Map<String,Command> commands;
	static{
		commands=new HashMap<String,Command>();
		commands.put("pass", new CommandAdd());
		commands.put("change", new CommandChange());
		commands.put("drop", new CommandDelete());
		commands.put("status", new CommandStatus());
	}

	public CommandLineParser(){
		jc=new JCommander();
		
		addCommands();
	}
	
	public JCommander getCommander(){
		return jc;
	}
	
	public void addCommands(){
		
		for (Entry<String, Command> entry : commands.entrySet())
		{
			  jc.addCommand(entry.getKey(),entry.getValue());
			}

	}
	
	public Command getCommandObj(String cmd){ // Here we return Command object determined on base of obtained from jc.getParsedCommand. 
		Command cmdobj=commands.get(cmd);
		return (null!=cmdobj) ? cmdobj : null;
	}
}
