package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Cancellable;
import down.TeamBattle.EventSystem.Event;

public final class EventPreSendMotionUpdates extends Event implements
		Cancellable {
	private boolean cancel;
	private float yaw, pitch;

	public EventPreSendMotionUpdates(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
}
