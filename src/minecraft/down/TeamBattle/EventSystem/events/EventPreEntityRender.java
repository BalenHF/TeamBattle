package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;
import net.minecraft.entity.Entity;

public final class EventPreEntityRender extends Event {
	private final Entity entity;

	public EventPreEntityRender(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
