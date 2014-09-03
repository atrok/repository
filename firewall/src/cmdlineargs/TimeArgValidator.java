package cmdlineargs;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class TimeArgValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		// TODO Auto-generated method stub
		List<String> timeValues=Arrays.asList("s","m","h");
		String[] n = value.split("%");
		try{
    	Integer.parseInt(n[0]);
    	if (!timeValues.contains(n[1]))
			throw new ParameterException("-time option value is incorrect:"+value+", expected %s|%m|%h");
		}catch (NumberFormatException e){
			throw new ParameterException("-time option value is incorrect:"+e);
		}
    	
		
			

	}
}
