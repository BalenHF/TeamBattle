package down.TeamBattle.ConfigSaving.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.ConfigSaving.CustomFile;
import down.TeamBattle.ModuleValues.Value;

public final class HUDConfig extends CustomFile {
	private Value[] hudvalues;

	public HUDConfig() {
		super("hudconfig");
	}

	@Override
	public void loadFile() {
		// when the file DOES exist, hudvalues is never set
		// this will stop the client from crashing
		if (hudvalues == null) {
			hudvalues = new Value[] {
					TeamBattleClient.getValueManager().getValueByName("hud_watermark"),
					TeamBattleClient.getValueManager().getValueByName("hud_arraylist"),
					TeamBattleClient.getValueManager().getValueByName("hud_coords"),
					TeamBattleClient.getValueManager().getValueByName("hud_fps"),
					TeamBattleClient.getValueManager()
							.getValueByName("hud_in-game_name"),
					TeamBattleClient.getValueManager().getValueByName(
							"hud_potion_effects"),
					TeamBattleClient.getValueManager().getValueByName("hud_time"),
					TeamBattleClient.getValueManager().getValueByName("hud_item_status"),
					TeamBattleClient.getValueManager()
							.getValueByName("hud_armor_status") };
		}
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(
					getFile()));
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] arguments = line.split(":");
				if (arguments.length == 2) {
					for (final Value value : hudvalues) {
						if (value == null) {
							continue;
						}
						if (arguments[0].equalsIgnoreCase(value.getName())) {
							// all of these values are booleans so no checking
							// is needed
							value.setValue(Boolean.parseBoolean(arguments[1]));
						}
					}
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
		// when the file doesn't exist, hudvalues is null
		// this will stop the client from crashing while still
		// creating the file for later use.
		if (hudvalues == null) {
			hudvalues = new Value[] {
					TeamBattleClient.getValueManager().getValueByName("hud_watermark"),
					TeamBattleClient.getValueManager().getValueByName("hud_arraylist"),
					TeamBattleClient.getValueManager().getValueByName("hud_coords"),
					TeamBattleClient.getValueManager().getValueByName("hud_fps"),
					TeamBattleClient.getValueManager()
							.getValueByName("hud_in-game_name"),
					TeamBattleClient.getValueManager().getValueByName(
							"hud_potion_effects"),
					TeamBattleClient.getValueManager().getValueByName("hud_time"),
					TeamBattleClient.getValueManager().getValueByName("hud_item_status"),
					TeamBattleClient.getValueManager()
							.getValueByName("hud_armor_status") };
		}
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(
					getFile()));
			for (final Value value : hudvalues) {
				if (value == null) {
					continue;
				}
				writer.write(value.getName() + ":" + value.getValue());
				writer.newLine();
			}
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
