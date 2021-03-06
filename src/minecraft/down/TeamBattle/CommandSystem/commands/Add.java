package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Utils.Logger;

public final class Add extends Command {
	public Add() {
		super("add", "<name> <alias>", "fadd");
	}

	@Override
	public void run(String message) {
		final String name = message.split(" ")[1];
		final String alias = message.substring((message.split(" ")[0] + " "
				+ name + " ").length());
		TeamBattleClient.getFriendManager().addFriend(name, alias);
		Logger.logChat("Friend \""
				+ TeamBattleClient.getFriendManager().replaceNames(name, true)
				+ "\" added.");
		if (TeamBattleClient.getFileManager().getFileByName("friends") != null) {
			TeamBattleClient.getFileManager().getFileByName("friends").saveFile();
		}
	}
}
