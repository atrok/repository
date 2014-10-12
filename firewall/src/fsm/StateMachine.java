package fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract public class StateMachine {
	protected State state;
	private Map map = new HashMap();

	public StateMachine(State state) {
		this.state = state;
	}

	public void buildTable(Object[][][] table) {
		for (int i = 0; i < table.length; i++) {
			Object[][] row = table[i];
			Object currentState = row[0][0];
			List transitions = new ArrayList();
			for (int j = 1; j < row.length; j++)
				transitions.add(row[j]);

			map.put(currentState, transitions);
		}
	}

	public void nextState(Input input) {
		Iterator it = ((List) map.get(state)).iterator();
		while (it.hasNext()) {
			Object[] tran = (Object[]) it.next();
			if (input == tran[0] || input.getClass() == tran[0]) {
				if (tran[1] != null) {
					Condition c = (Condition) tran[1];
					if (!c.condition(input))
						continue; // Failed test
				}
				if (tran[2] != null)
					((Transition) tran[2]).transition(input);
				state = (State) tran[3];
				return;
			}
		}
		throw new RuntimeException("Input not supported for current state");
	}
}
