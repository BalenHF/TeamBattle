package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.CommandSystem.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class Damage extends Command {
	public Damage() {
		super("damage", "<hearts>", "dmg", "hurt");
	}

	@Override
	public void run(String message) {
		final Minecraft mc = Minecraft.getMinecraft();
		double dmg = Double.parseDouble(message.split(" ")[1]);

		mc.getNetHandler().addToSendQueue(
				new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						
						mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ,
						mc.thePlayer.onGround));
		mc.getNetHandler().addToSendQueue(
				new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						
						mc.thePlayer.posY - 2.0D - dmg, mc.thePlayer.posZ,
						mc.thePlayer.onGround));
	}
}
