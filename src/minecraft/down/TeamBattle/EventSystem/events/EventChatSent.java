package down.TeamBattle.EventSystem.events;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Cancellable;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.Utils.Logger;

public final class EventChatSent extends Event implements Cancellable  {
	private boolean cancel;
	private String message;

	public EventChatSent(String message) {
		this.message = message;
	}

	public void checkForCommands() {
		if (message.startsWith(".")) {
			for (final Command command : TeamBattleClient.getCommandManager()
					.getContents()) {
				if (message.split(" ")[0].equalsIgnoreCase("."
						+ command.getCommand())) {
					try {
						command.run(message);
					} catch (final Exception e) {
						Logger.logChat("Invalid arguments! "
								+ command.getCommand() + " "
								+ command.getArguments());
					}
					cancel = true;
				} else {
					for (final String alias : command.getAliases()) {
						if (message.split(" ")[0].equalsIgnoreCase("." + alias)) {
							try {
								command.run(message);
							} catch (final Exception e) {
								Logger.logChat("Invalid arguments! " + alias
										+ " " + command.getArguments());
							}
							cancel = true;
						}
					}
				}
			}

			if (!cancel) {
				Logger.logChat("Command \"" + message + "\" was not found!");
				cancel = true;
			}
		}
	}

	public String getMessage() {
		return message;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
