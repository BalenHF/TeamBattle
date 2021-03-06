package down.TeamBattle.ConfigSaving.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.ConfigSaving.CustomFile;
import down.TeamBattle.EventSystem.events.EventChatSent;

public final class AutoExec extends CustomFile {
	public AutoExec() {
		super("autoexec");
	}

	@Override
	public void loadFile() {
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(
					getFile()));
			String line;
			while ((line = reader.readLine()) != null) {
				final EventChatSent sent = new EventChatSent("." + line);
				TeamBattleClient.getEventManager().call(sent);
				sent.checkForCommands();
			}
			reader.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveFile() {
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(
					getFile()));
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
