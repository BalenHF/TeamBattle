package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Cancellable;
import down.TeamBattle.EventSystem.Event;

public class EventRenderBurning extends Event implements Cancellable {
	private boolean cancel;

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
