package cmdlineargs;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class TimeConverter implements IStringConverter<Integer> {
	@Override
	public Integer convert(String value){
		
		
		List<String> timeValues=Arrays.asList("s","m","h");
		String[] n = value.split("%");
		try{
    	int num=Integer.parseInt(n[0]);
    	if (!timeValues.contains(n[1]))
			throw new ParameterException("-time option value is incorrect:"+value+", expected %s|%m|%h");
    	
    	switch (n[1]){
	    	case "s": return num*1000;
	    	case "m": return num*1000*60;
	    	case "h": return num*1000*3600;
	    	default: return null;
    		}
    	
		}catch (NumberFormatException e){
			throw new ParameterException("-time option value is incorrect:"+e);
		}
	}

}
