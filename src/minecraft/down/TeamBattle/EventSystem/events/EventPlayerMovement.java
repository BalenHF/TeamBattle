package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;

public final class EventPlayerMovement extends Event {
	private double motionX, motionY, motionZ;

	public EventPlayerMovement(double motionX, double motionY, double motionZ) {
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	public double getMotionX() {
		return motionX;
	}

	public double getMotionY() {
		return motionY;
	}

	public double getMotionZ() {
		return motionZ;
	}

	public void setMotionX(double motionX) {
		this.motionX = motionX;
	}

	public void setMotionY(double motionY) {
		this.motionY = motionY;
	}

	public void setMotionZ(double motionZ) {
		this.motionZ = motionZ;
	}
}
