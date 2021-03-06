package down.TeamBattle.Managers.managers;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Managers.ListManager;
import down.TeamBattle.Utils.Logger;

public final class CommandManager extends ListManager<Command> {
	public Command getCommandByName(String name) {
		if (contents == null)
			return null;
		for (final Command command : contents) {
			if (command.getCommand().equalsIgnoreCase(name))
				return command;
			for (final String alias : command.getAliases()) {
				if (alias.equalsIgnoreCase(name))
					return command;
			}
		}
		return null;
	}

	@Override
	public void setup() {
		Logger.logConsole("Preparing to load commands...");
		contents = new ArrayList<Command>();
		final Reflections reflect = new Reflections(Command.class);
		final Set<Class<? extends Command>> mods = reflect
				.getSubTypesOf(Command.class);
		for (final Class clazz : mods) {
			try {
				final Command command = (Command) clazz.newInstance();
				getContents().add(command);
				Logger.logConsole("Loaded command \"" + command.getCommand()
						+ "\"");
			} catch (final InstantiationException e) {
				e.printStackTrace();
				Logger.logConsole("Failed to load command \""
						+ clazz.getSimpleName() + "\" (InstantiationException)");
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
				Logger.logConsole("Failed to load command \""
						+ clazz.getSimpleName() + "\" (IllegalAccessException)");
			}
		}
		Logger.logConsole("Successfully loaded " + getContents().size()
				+ " commands.");
	}
}
