package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class DG extends Command {

	public DG() {
		super("dg","< "," ");
		
	}
final Minecraft mc = Minecraft.getMinecraft();
	@Override
	public void run(String message) {
		Logger.logChat("Start Gliding");
		mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,  mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
		mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
		mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 5.0D, mc.thePlayer.posZ, false));
		mc.thePlayer.setPosition(mc.thePlayer.posX,mc.thePlayer.posY - 5.0,mc.thePlayer.posZ);
		
	}

}
