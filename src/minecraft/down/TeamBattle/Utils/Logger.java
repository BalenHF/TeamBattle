package down.TeamBattle.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class Logger {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static void logChat(String message) {
		if (mc.thePlayer == null) {
			logConsole(message);
		} else {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2474[TeamBattle]\247f "
					+ message));
		}
	}

	public static void logConsole(String message) {
		System.out.println("[TeamBattle] " + message);
	}
}
