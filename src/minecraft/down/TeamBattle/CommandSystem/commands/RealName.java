/*package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.util.StringUtils;

public final class RealName extends Command {
	public RealName() {
		super("realname", "<alias>", "rn");
	}

	@Override
	public void run(String message) {
		boolean found = false;
		for (final Object o : Minecraft.getMinecraft().getNetHandler().playerInfoList) {
			final GuiPlayerInfo playerInfo = (GuiPlayerInfo) o;
			final String name = StringUtils.stripControlCodes(playerInfo.name);
			if (TeamBattleClient.getFriendManager().isFriend(name)) {
				final String protect = TeamBattleClient.getFriendManager().getContents()
						.get(name);
				if (message.substring((message.split(" ")[0] + " ").length())
						.equalsIgnoreCase(protect)) {
					Logger.logChat("Friend \"\2473" + protect
							+ "\247r\" real name is: " + name);
					found = true;
				}
			}
		}

		if (!found) {
			Logger.logChat("Friend \"" + message.split(" ")[1]
					+ "\" was not found!");
		}
	}
}*/
