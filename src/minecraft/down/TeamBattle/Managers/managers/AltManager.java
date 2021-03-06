package down.TeamBattle.Managers.managers;

import java.util.ArrayList;

import down.TeamBattle.Managers.ListManager;
import down.TeamBattle.Utils.Alt;

public final class AltManager extends ListManager<Alt> {
	private Alt lastAlt;

	public Alt getLastAlt() {
		return lastAlt;
	}

	public void setLastAlt(Alt alt) {
		lastAlt = alt;
	}

	@Override
	public void setup() {
		contents = new ArrayList<Alt>();
	}
}
