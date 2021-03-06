package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;

public final class Visible extends Command {
	public Visible() {
		super("visible", "<mod>", "vis");
	}

	@Override
	public void run(String message) {
		final String[] arguments = message.split(" ");
		final ModuleBase mod = TeamBattleClient.getModManager().getModByName(arguments[1]);
		if (mod == null) {
			Logger.logChat("Mod \"" + arguments[1] + "\" was not found!");
		} else {
			mod.setVisible(!mod.isVisible());
			Logger.logChat("Mod \"" + mod.getName() + "\" is now "
					+ (mod.isVisible() ? "\2472visible" : "\2474invisible")
					+ "\247f.");
		}
	}
}
