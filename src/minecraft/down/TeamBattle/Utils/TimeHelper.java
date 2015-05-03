package down.TeamBattle.Utils;

public final class TimeHelper {
	public long lastMS;

	public long getCurrentMS() {
		return System.nanoTime() / 1000000;
	}

	public long getLastMS() {
		return lastMS;
	}

	public boolean hasReached(float f) {
		return getCurrentMS() - lastMS >= f;
	}

	public void reset() {
		lastMS = getCurrentMS();
	}

	public void setLastMS(long currentMS) {
		lastMS = currentMS;
	}
}
