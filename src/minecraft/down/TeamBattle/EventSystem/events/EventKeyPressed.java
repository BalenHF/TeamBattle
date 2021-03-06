package down.TeamBattle.EventSystem.events;

import org.lwjgl.input.Keyboard;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.Modules.ModuleBase;

public final class EventKeyPressed extends Event {
	private final int key;

	public EventKeyPressed(int key) {
		this.key = key;
	}

	public void checkKey() {
		if (key == Keyboard.KEY_NONE)
			return;
		for (final ModuleBase mod : TeamBattleClient.getModManager().getContents()) {
			if (mod.getKeybind() == Keyboard.KEY_NONE) {
				continue;
			}
			if (key == mod.getKeybind()) {
				mod.toggle();
			}
		}
	}

	public int getKey() {
		return key;
	}
}
