package down.TeamBattle.ConfigSaving.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.ConfigSaving.CustomFile;
import down.TeamBattle.Modfc.Point;
import down.TeamBattle.Modules.Mods.Waypoints;

public final class WaypointsFile extends CustomFile {
	public WaypointsFile() {
		super("waypoints");
	}

	@Override
	public void loadFile() {
		try {
			final Waypoints waypoints = (Waypoints) TeamBattleClient.getModManager()
					.getModByName("waypoints");
			if (waypoints == null)
				return;
			final BufferedReader reader = new BufferedReader(new FileReader(
					getFile()));
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] arguments = line.split(":");
				if (arguments.length == 5) {
					final String name = arguments[0];
					final String server = arguments[1];
					final int x = Integer.parseInt(arguments[2]);
					final int y = Integer.parseInt(arguments[3]);
					final int z = Integer.parseInt(arguments[4]);
					waypoints.getPoints().add(new Point(name, server, x, y, z));
				}
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
			final Waypoints waypoints = (Waypoints) TeamBattleClient.getModManager()
					.getModByName("waypoints");
			if (waypoints == null)
				return;
			final BufferedWriter writer = new BufferedWriter(new FileWriter(
					getFile()));
			for (final Point point : waypoints.getPoints()) {
				final String name = point.getName();
				final String server = point.getServer();
				final int x = point.getX();
				final int y = point.getY();
				final int z = point.getZ();
				writer.write(name + ":" + server + ":" + x + ":" + y + ":" + z);
				writer.newLine();
			}
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
