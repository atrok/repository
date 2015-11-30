package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;

import PageProcessor.PageProcessor;
import PageProcessor.PageResult;
import cmdlineargs.CmdLineParameters;
import cmdlineargs.commands.Command;
import cmdlineargs.commands.CommandLineParser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import firewall.Firewall;
import firewall.RequestQueueFabric;
import firewall.Rule;

public class TestNewDefault {
	
	private static final Logger logger=Logger.getLogger(TestNewDefault.class.getName());
	
	private static CmdLineParameters params = CmdLineParameters.getInstance();
	//private List<Rule> rules=new ArrayList<Rule>();
	
	

	@Test
	public void testPassDelete() {
		
		logger.info("###############################                                 #####################");
		logger.info("############################### Testing of adding functionality #####################");
		logger.info("###############################                                 #####################");
		logger.info("############################### testing the addition of rule for IP 192.168.1.2, 2 rules are allowed #####################");			

		String args2[]=new String[]{"-action","pass","-destip","192.168.1.2"};
		assertEquals(
				getRulesFromResultFiltered(processCmdArgs(args2),args2[3])
				.size(),
				2);

		logger.info("############################### testing the addition of rule for IP 192.168.1.2 under condition of already existing rules for that IP\n expected result is 2 only rules in resulting list of rules for that IP #####################");
		
			args2=new String[]{"","pass","-destip","192.168.1.2"};
			assertEquals(
					getRulesFromResultFiltered(processCmdArgs(args2),args2[3])
					.size(),
					2);

		logger.info("############################### testing the addition of rule for IP 192.168.1.69 under condition of already existing rules for that IP\n expected result is 4 rules in resulting list of rules #####################");
			
			args2=new String[]{"-action","pass","-destip","192.168.1.3"};
			PageResult p=processCmdArgs(args2);
			assertEquals(p.getExistingRules().size(),
					4);
			// 2 rules per IP
			assertEquals(getRulesFromResultFiltered(p,args2[3]),2);

			
		logger.info("############################### testing the deletion of rule for IP 192.168.1.70 under condition of already existing rules for that IP\n expected result is no rules in resulting list of rules for that IP #####################");
			
			args2=new String[]{"-action","drop","-destip","192.168.1.3"};
			assertTrue(isRuleListEmpty(args2));
		
		logger.info("############################### testing the deletion of rule for IP 192.168.1.70 under condition of no rules for that IP\n expected result is no rules in resulting list of rules for that IP #####################");
			
		args2=new String[]{"-action","drop","-destip","192.168.1.70"};
		assertTrue(isRuleListEmpty(args2));

	}

	@Ignore
	public void testTimeout(){
		
		logger.info("###############################                                 #####################");
		logger.info("############################### Testing of timeout functionality #####################");
		
		String args2[]={"","delete","-destip","192.168.1.70"};
		logger.info("############################### asserted the removal of rule for IP "+args2[3]+" #####################");
			
			assertTrue(isRuleListEmpty(args2)); // isRuleListEmpty returns True if resulting array of rules is empty
			
			
			String args3[]={"","add","-destip","192.168.1.73"};
			assertTrue(isRuleListEmpty(args3));
			
			logger.info("############################### asserted the removal of rule for IP "+args2[3]+" #####################");

			
	}
	
	@Ignore
	public void testChange(){
		
		logger.info("###############################                                 #####################");
		logger.info("############################### Testing of changing functionality #####################");
		
		String args2[]={"-action","drop","-destip","192.168.1.70"};
		logger.info("############################### asserted the removal of rule for IP "+args2[3]+" #####################");
			
			assertTrue(isRuleListEmpty(args2));
			
			
			String args3[]={"-action","drop","-destip","192.168.1.69"};
			assertTrue(isRuleListEmpty(args3));
			
			logger.info("############################### asserted the removal of rule for IP "+args2[3]+" #####################");

			
	}

	private boolean isRuleListEmpty(String... args) {
				
		return 
				getRulesFromResultFiltered(processCmdArgs(args),args[3])
				.isEmpty();	
	}	
	
	
	private List<Rule> getRulesFromResultFiltered(PageResult p, String filter){

		List<Rule> rules=new ArrayList<Rule>();
		for (Rule r: p.getExistingRules()){
			if (!filter.equals("")){ /// return list of rules filtered by 1 criteria (filter)
				if (r.getDestIP().equals(filter))
					rules.add(r);
			}else{ // or return all rules
				rules.add(r);
				}
			}
		/*
		if (rules.size()>2 ||rules.size()==1)
			fail(String.format("For given ip %s only 2 rules are allowed, 1 rule per type of protocol (tcp/udp). In case of deletion no rules to be left",filter));
		*/
		return rules;
	}
	
	private PageResult processCmdArgs(String... args){
		
		CommandLineParser cmdParser = new CommandLineParser();
		JCommander commander=cmdParser.getCommander();
	    try {
	    	commander.parse(args);
	    	Command cmd = cmdParser.getCommandObj(commander.getParsedCommand());
	    	
	    	
	        RequestQueueFabric r=new RequestQueueFabric(cmd);
			new Firewall(r.getQueue());
			
			logger.info(String.format("##########################\n %s \n##########################",Firewall.result.toString()));
			
			return Firewall.result;

	    } catch (ParameterException e) {
	        logger.log(Level.SEVERE, "one of command line arguments is incorrect: ", e);
	        //cmd.usage();
	    }catch(Exception e){
	        logger.log(Level.SEVERE, "Exception occured while processing PageRequest: ", e);
		}
	    return null;
	}

}
