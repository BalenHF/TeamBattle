package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.Listener;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.Utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class Teleport extends Command {
	public Listener listener;
	private final Minecraft mc = Minecraft.getMinecraft();

	public Teleport() {
		super("teleport", "<x> <y> <z>", "tp");
		listener = new Listener() {
			@Override
			public void onEvent(Event event) {
				if (event instanceof EventPreSendMotionUpdates) {
					final EventPreSendMotionUpdates pre = (EventPreSendMotionUpdates) event;
					pre.setCancelled(true);

					if (mc.thePlayer.ridingEntity == null) {
						TeamBattleClient.getEventManager().removeListener(this);
					}
				}
			}
		};
	}

	@Override
	public void run(String message) {
		final String[] arguments = message.split(" ");
		final double x = Double.parseDouble(arguments[1]);
		final double y = Double.parseDouble(arguments[2]);
		final double z = Double.parseDouble(arguments[3]);
		if (mc.thePlayer.ridingEntity != null) {
			TeamBattleClient.getEventManager().addListener(listener);
		}
		mc.thePlayer.setPosition(x, y, z);
		mc.getNetHandler().addToSendQueue(
				new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1, z,
						mc.thePlayer.onGround));
		Logger.logChat("You have been teleported to " + x + ", " + y + ", " + z);
		if (mc.thePlayer.ridingEntity != null) {
			for (int index = 0; index < 20; index++) {
				mc.playerController.attackEntity(mc.thePlayer,
						mc.thePlayer.ridingEntity);
			}
		}
	}

}
