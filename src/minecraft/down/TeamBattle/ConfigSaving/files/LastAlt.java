package down.TeamBattle.ConfigSaving.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.ConfigSaving.CustomFile;
import down.TeamBattle.Utils.Alt;

public final class LastAlt extends CustomFile {
	public LastAlt() {
		super("lastalt");
	}

	@Override
	public void loadFile() {
		try {
			final BufferedReader bufferedReader = new BufferedReader(
					new FileReader(getFile()));
			String s;
			while ((s = bufferedReader.readLine()) != null) {
				if (s.contains("\t")) {
					s = s.replace("\t", "    ");
				}
				if (s.contains("    ")) {
					final String[] parts = s.split("    ");
					final String[] account = parts[1].split(":");
					if (account.length == 2) {
						TeamBattleClient.getAltManager().setLastAlt(
								new Alt(account[0], account[1], parts[0]));
					} else {
						String pw = account[1];
						for (int i = 2; i < account.length; i++) {
							pw += ":" + account[i];
						}
						TeamBattleClient.getAltManager().setLastAlt(
								new Alt(account[0], pw, parts[0]));
					}
				} else {
					final String[] account = s.split(":");
					if (account.length == 1) {
						TeamBattleClient.getAltManager().setLastAlt(
								new Alt(account[0], ""));
					} else if (account.length == 2) {
						TeamBattleClient.getAltManager().setLastAlt(
								new Alt(account[0], account[1]));
					} else {
						String pw = account[1];
						for (int i = 2; i < account.length; i++) {
							pw += ":" + account[i];
						}
						TeamBattleClient.getAltManager().setLastAlt(
								new Alt(account[0], pw));
					}
				}
			}
			bufferedReader.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveFile() {
		try {
			final PrintWriter printWriter = new PrintWriter(getFile());
			final Alt alt = TeamBattleClient.getAltManager().getLastAlt();
			if (alt != null) {
				if (alt.getMask().equals("")) {
					printWriter.println(alt.getUsername() + ":"
							+ alt.getPassword());
				} else {
					printWriter.println(alt.getMask() + "    "
							+ alt.getUsername() + ":" + alt.getPassword());
				}
			}
			printWriter.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
