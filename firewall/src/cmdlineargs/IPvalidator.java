package cmdlineargs;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IPvalidator implements IParameterValidator {

	 public void validate(String name, String value)
		      throws ParameterException {
		    String[] n = value.split("\\.");

		    for(int i=0; i<n.length;i++){
		    	
		    	Integer num=Integer.parseInt(n[i]);
		    	
		    	
		    	System.out.println("validation of "+name+" option "+value+" :"+n[i]);
		    

		    
		    if (num < 0 || num> 255||n.length!=4 || null==value) {
		      throw new ParameterException("Parameter " + name + " should be ip address as xxx.xxx.xxx.xxx (found " + value +")");
		    }
		    	
		  }

	 }
}