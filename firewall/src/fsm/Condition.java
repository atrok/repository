package fsm;

import PageProcessor.PageProcessor;

public enum Condition {

	ifEmpty{
		boolean condition(Input i){ return i!=null;}
	},
	ifTimer{
		boolean condition(Input i){ return i!=null;}
	},
	ifEmptyRuleList{
		boolean condition(Input i){ 
			
			
			return i!=null;}
	},
	ifLoggedIn{
		boolean condition(Input i){ return i!=null;}
	},
	ifPrevTransition{
		boolean condition(Input i){ return i!=null;}
	},
	
	ifTimerExpired{
		boolean condition(Input i){ return i!=null;}
	}
	;
	
	abstract boolean condition(Input i);
}


