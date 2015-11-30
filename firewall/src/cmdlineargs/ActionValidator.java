package cmdlineargs;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ActionValidator implements IParameterValidator {
	
	
	public void validate(String name, String value) throws ParameterException {
		List<String> options=Arrays.asList("drop","pass","status");
		// TODO Auto-generated method stub
		if (value instanceof String){
			if (!options.contains(value))
				throw new ParameterException(" Entered value of action is incorrect: "+value+", expected values are (drop|pass|status)");
			return;
		}
		throw new ParameterException(" Entered type of Action is incorrect: "+value+", expected values are (drop|pass|status)");
	}

}
