package down.TeamBattle.CommandSystem;

public abstract class Command {
	protected final String[] aliases;
	protected final String command, arguments;

	public Command(String command, String arguments, String... aliases) {
		this.command = command;
		this.arguments = arguments;
		this.aliases = aliases;
	}

	public String[] getAliases() {
		return aliases;
	}

	public String getArguments() {
		return arguments;
	}

	public String getCommand() {
		return command;
	}

	public abstract void run(String message);
}
