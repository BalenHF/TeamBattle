package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Cancellable;
import down.TeamBattle.EventSystem.Event;
import net.minecraft.network.Packet;

public final class EventPacketReceive extends Event implements Cancellable {
	private boolean cancel;
	private Packet packet;

	public EventPacketReceive(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}