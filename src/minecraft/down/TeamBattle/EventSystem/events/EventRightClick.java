package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;

public final class EventRightClick extends Event {
	private int delay;

	public EventRightClick(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
}
