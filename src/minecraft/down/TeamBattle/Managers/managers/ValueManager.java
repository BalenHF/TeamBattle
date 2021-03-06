package down.TeamBattle.Managers.managers;

import java.util.ArrayList;

import down.TeamBattle.Managers.ListManager;
import down.TeamBattle.ModuleValues.Value;

public final class ValueManager extends ListManager<Value> {
	public final Value getValueByName(String name) {
		for (final Value value : contents) {
			if (value.getName().equalsIgnoreCase(name))
				return value;
		}
		return null;
	}

	@Override
	public void setup() {
		contents = new ArrayList<Value>();
	}
}
