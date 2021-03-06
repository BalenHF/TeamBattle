package down.TeamBattle.CommandSystem.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public final class CopyCoords extends Command {
	public CopyCoords() {
		super("copycoords", "none", "cc");
	}

	@Override
	public void run(String message) {
		final int x = MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.posX);
		final int y = MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.posY);
		final int z = MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.posZ);
		final StringSelection ss = new StringSelection(x + " " + y + " " + z);
		final Clipboard clipboard = Toolkit.getDefaultToolkit()
				.getSystemClipboard();
		clipboard.setContents(ss, null);
		Logger.logChat("Current coordinates copied to clipboard.");
	}

}
