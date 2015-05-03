package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;
import net.minecraft.client.multiplayer.WorldClient;

public final class EventWorldLoad extends Event {
	private final WorldClient world;

	public EventWorldLoad(WorldClient world) {
		this.world = world;
	}

	public WorldClient getWorld() {
		return world;
	}
}
