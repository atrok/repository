package cmdlineargs;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ProtoValidator implements IParameterValidator {
	
	
	public void validate(String name, String value) throws ParameterException {
		List<String> options=Arrays.asList("tcp","udp","both");
		// TODO Auto-generated method stub
		String err=" Entered value of protocol is incorrect: "+value+", expected values are (tcp|udp|both)";
		if (value instanceof String){
			if (!options.contains(value))
				throw new ParameterException(err);
			return;
		}
		throw new ParameterException(err);
	}

}
