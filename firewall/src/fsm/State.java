package fsm;

public interface State {
	public String getState();
	
}

class Begin implements State{
	public String getState(){
		return "Begin State";
	};
}
class LoggedIn implements State{
	public String getState(){
		return "LoggedIn State";
	};
}
class RuleAdded implements State{
	public String getState(){
		return "RuleAdded State";
	};
}
class RuleDeleted implements State{
	public String getState(){
		return "RuleDeleted State";
	};
}
class RuleChanged implements State{
	public String getState(){
		return "RuleChanged State";
	};
}
class TimerSet implements State{
	public String getState(){
		return "TimerSet State";
	};
}


