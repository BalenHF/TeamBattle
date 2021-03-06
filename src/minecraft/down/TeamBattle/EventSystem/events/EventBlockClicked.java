package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;

public final class EventBlockClicked extends Event {
	private final int x, y, z, side;

	public EventBlockClicked(int x, int y, int z, int side) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;
	}

	public int getSide() {
		return side;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}
