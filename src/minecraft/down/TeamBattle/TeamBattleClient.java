package down.TeamBattle;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.Display;

import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Managers.managers.AdminManager;
import down.TeamBattle.Managers.managers.AltManager;
import down.TeamBattle.Managers.managers.CommandManager;
import down.TeamBattle.Managers.managers.EventManager;
import down.TeamBattle.Managers.managers.FileManager;
import down.TeamBattle.Managers.managers.FriendManager;
import down.TeamBattle.Managers.managers.ModManager;
import down.TeamBattle.Managers.managers.ValueManager;
import down.TeamBattle.MinecraftLoginUtils.UpdateCheckThread;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;


public final class TeamBattleClient {

	private static final AdminManager adminManager = new AdminManager();
	private static final AltManager altManager = new AltManager();
	private static final int build = 1;
	private static final CommandManager commandManager = new CommandManager();
	private static final File directory = new File(
			Minecraft.getMinecraft().mcDataDir, "TeamBattle");
	private static final EventManager eventManager = new EventManager();
	private static final FileManager fileManager = new FileManager();
	private static final FriendManager friendManager = new FriendManager();
	private static final ModManager modManager = new ModManager();
	private static boolean newerVersionAvailable;
	private static final ValueManager valueManager = new ValueManager();

	

	public static AdminManager getAdminManager() {
		return adminManager;
	}

	public static AltManager getAltManager() {
		return altManager;
	}

	public static int getBuild() {
		return build;
	}

	public static CommandManager getCommandManager() {
		return commandManager;
	}

	public static File getDirectory() {
		return directory;
	}

	public static EventManager getEventManager() {
		return eventManager;
	}

	public static FileManager getFileManager() {
		return fileManager;
	}

	public static FriendManager getFriendManager() {
		return friendManager;
	}

	public static ModManager getModManager() {
		return modManager;
	}

	public static ValueManager getValueManager() {
		return valueManager;
	}

	public static boolean isNewerVersionAvailable() {
		return newerVersionAvailable;
	}

	public static void onStartup() {
		Display.setTitle("TeamBattle 1.7.10");
		Logger.logConsole("Started loading TeamBattle");
		Logger.logConsole("/277Build #" + build);
		new UpdateCheckThread().start();
		if (!directory.isDirectory()) {
			directory.mkdirs();
		}
		eventManager.setup();
		valueManager.setup();
		friendManager.setup();
		adminManager.setup();
		commandManager.setup();
		modManager.setup();
		altManager.setup();
		fileManager.setup();
		
		Collections.sort(commandManager.getContents(),
				new Comparator<Command>() {
					@Override
					public int compare(Command cmd1, Command cmd2) {
						return cmd1.getCommand().compareTo(cmd2.getCommand());
					}
				});

		// then sort mods so they are alphabetical
		Collections.sort(modManager.getContents(), new Comparator<ModuleBase>() {
			@Override
			public int compare(ModuleBase mod1, ModuleBase mod2) {
				return mod1.getName().compareTo(mod2.getName());
			}
		});
		Logger.logConsole("Successfully loaded TeamBattle");
		
	}

	

	public static void setNewerVersionAvailable(boolean newerVersionAvailable) {
		TeamBattleClient.newerVersionAvailable = newerVersionAvailable;
	}
}

/*
 * Nice memes
 * 
 * if ((event instanceof EventPostUpdate)) { if (isInLiquid()) {
 * Wrapper.getPlayer().motionY = 0.12D; return; } if
 * (isOnLiquid(Wrapper.getPlayer())) { Wrapper.getPlayer().noClip = true;
 * 
 * 
 * long currentMS = System.nanoTime() / 1000000L;
 * Minecraft.getMinecraft().thePlayer.onGround = false; if ((currentMS -
 * this.lastMS >= 250L) || (this.lastMS == -1L)) {
 * Wrapper.getPlayer().boundingBox.expand(0.0D, 2.0D, 0.0D); this.lastMS =
 * currentMS; } } else { Wrapper.getPlayer().noClip = false; } }
 */