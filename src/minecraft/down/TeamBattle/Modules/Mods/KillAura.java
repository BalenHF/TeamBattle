package down.TeamBattle.Modules.Mods;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

import org.lwjgl.input.Keyboard;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPacketSent;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.ModuleValues.Value;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.EntityHelper;
import down.TeamBattle.Utils.Logger;
import down.TeamBattle.Utils.TimeHelper;

public final class KillAura extends ModuleBase{
	private final Value<Boolean> animals = new Value<Boolean>(
			"killaura_animals", false);
	private final Value<Boolean> autohit = new Value<Boolean>(
			"killaura_autohit", true);
	private final Value<Boolean> autosword = new Value<Boolean>(
			"killaura_autosword", true);
	private final Value<Long> delay = new Value<Long>("killaura_delay", 83L);
	private final Value<Boolean> direction = new Value<Boolean>(
			"killaura_direction", true);
	private final Value<Boolean> gcheat = new Value<Boolean>("killaura_gcheat",
			false);
	private final Value<String> ignore = new Value<String>("killaura_ignore",
			"");
	private final Value<Boolean> invisibles = new Value<Boolean>(
			"killaura_invisibles", true);
	private final Value<Integer> maxTargets = new Value<Integer>(
			"killaura_max_targets", 4);
	private final Value<Boolean> mobs = new Value<Boolean>("killaura_mobs",
			false);
	private final Value<Boolean> players = new Value<Boolean>(
			"killaura_players", true);
	private final Value<Double> reach = new Value<Double>("killaura_reach",
			3.8D);
	private final Random rnd = new Random();
	private final Value<Boolean> silent = new Value<Boolean>("killaura_silent",
			true);
	private final List<Entity> targets = new CopyOnWriteArrayList<Entity>();
	private final Value<Integer> ticksToWait = new Value<Integer>(
			"killaura_ticks_to_wait", 20);
	private final TimeHelper time = new TimeHelper();

	public KillAura() {
		super("KillAura", Keyboard.KEY_R, 0xFF8A0000);
		setTag("Kill Aura");

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauraplayers", "none", "auraplayers",
						"kap") {
					@Override
					public void run(String message) {
						players.setValue(!players.getValue());
						Logger.logChat("Kill Aura will "
								+ (players.getValue() ? "now" : "no longer")
								+ " aim at players.");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killaurainvisibles", "none",
						"aurainvisibles", "kai") {
					@Override
					public void run(String message) {
						invisibles.setValue(!invisibles.getValue());
						Logger.logChat("Kill Aura will "
								+ (invisibles.getValue() ? "now" : "no longer")
								+ " aim at invisibles.");
					}
				});

		TeamBattleClient.getCommandManager().getContents()
				.add(new Command("killauramobs", "none", "auramobs", "kam") {
					@Override
					public void run(String message) {
						mobs.setValue(!mobs.getValue());
						Logger.logChat("Kill Aura will "
								+ (mobs.getValue() ? "now" : "no longer")
								+ " aim at mobs.");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauraanimals", "none", "auraanimals",
						"kaa") {
					@Override
					public void run(String message) {
						animals.setValue(!animals.getValue());
						Logger.logChat("Kill Aura will "
								+ (animals.getValue() ? "now" : "no longer")
								+ " aim at animals.");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killaurasilent", "none", "aurasilent", "kas") {
					@Override
					public void run(String message) {
						silent.setValue(!silent.getValue());
						Logger.logChat("Kill Aura will "
								+ (silent.getValue() ? "now" : "no longer")
								+ " silently aim at targets.");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauradirection", "none", "auradirection",
						"kadi") {
					@Override
					public void run(String message) {
						direction.setValue(!direction.getValue());
						Logger.logChat("Kill Aura will "
								+ (direction.getValue() ? "now" : "no longer")
								+ " bypass nocheat's direction check.");
						if (direction.getValue()) {
							Logger.logChat("\247cWARNING\247f: The direction bypass is really obvious in the sense you look down at the ground when you hit people. Don't use it if you're trying to look legit.");
						}
					}
				});

		TeamBattleClient.getCommandManager().getContents()
				.add(new Command("killauraautohit", "none", "aurahit", "kah") {
					@Override
					public void run(String message) {
						autohit.setValue(!autohit.getValue());
						Logger.logChat("Kill Aura will "
								+ (autohit.getValue() ? "now" : "no longer")
								+ " automatically hit targets.");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauragcheat", "none", "auragcheat", "kagc") {
					@Override
					public void run(String message) {
						gcheat.setValue(!gcheat.getValue());
						Logger.logChat("Kill Aura will "
								+ (gcheat.getValue() ? "now" : "no longer")
								+ " bypass GCheat. (badlion anticheat)");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauraautosword", "none", "aurasword",
						"kaas") {
					@Override
					public void run(String message) {
						autosword.setValue(!autosword.getValue());
						Logger.logChat("Kill Aura will "
								+ (autosword.getValue() ? "now" : "no longer")
								+ " automatically switches sword.");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauraignore", "<color>", "auraignore",
						"kaig", "oc") {
					@Override
					public void run(String message) {
						final String[] arguments = message.split(" ");
						if (arguments.length == 1) {
							ignore.setValue("");
							Logger.logChat("Kill Aura ignore colors cleared.");
						}
						ignore.setValue(message.split(" ")[1].substring(0, 1));
						Logger.logChat("Kill Aura will ignore \247"
								+ ignore.getValue() + "this" + " \247fcolor");
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauradelay", "<milliseconds>",
						"auradelay", "kad") {
					@Override
					public void run(String message) {
						if (message.split(" ")[1].equalsIgnoreCase("-d")) {
							delay.setValue(delay.getDefaultValue());
						} else {
							delay.setValue(Long.parseLong(message.split(" ")[1]));
						}

						if (delay.getValue() > 1000L) {
							delay.setValue(1000L);
						} else if (delay.getValue() < 1L) {
							delay.setValue(1L);
						}
						Logger.logChat("Kill Aura Delay set to: "
								+ delay.getValue());
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killaurareach", "<blocks>", "aurareach",
						"kar") {
					@Override
					public void run(String message) {
						if (message.split(" ")[1].equalsIgnoreCase("-d")) {
							reach.setValue(reach.getDefaultValue());
						} else {
							reach.setValue(Double.parseDouble(message
									.split(" ")[1]));
						}
						if (reach.getValue() > 6.0D) {
							reach.setValue(6.0D);
						} else if (reach.getValue() < 1.0D) {
							reach.setValue(1.0D);
						}
						Logger.logChat("Kill Aura Reach set to: "
								+ KillAura.this.reach.getValue());
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauramaxtargets", "<targets>",
						"auramaxtargets", "kamt") {
					@Override
					public void run(String message) {
						if (message.split(" ")[1].equalsIgnoreCase("-d")) {
							maxTargets.setValue(maxTargets.getDefaultValue());
						} else {
							maxTargets.setValue(Integer.parseInt(message
									.split(" ")[1]));
						}
						if (maxTargets.getValue() > 10) {
							maxTargets.setValue(10);
						} else if (maxTargets.getValue() < 1) {
							maxTargets.setValue(1);
						}
						Logger.logChat("Kill Aura Max Targets set to: "
								+ maxTargets.getValue());
					}
				});

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command("killauraticks", "<ticks to wait>",
						"auraticks", "kat") {
					@Override
					public void run(String message) {
						if (message.split(" ")[1].equalsIgnoreCase("-d")) {
							ticksToWait.setValue(ticksToWait.getDefaultValue());
						} else {
							ticksToWait.setValue(Integer.parseInt(message
									.split(" ")[1]));
						}
						if (ticksToWait.getValue() > 10000) {
							ticksToWait.setValue(10000);
						} else if (ticksToWait.getValue() < 1) {
							ticksToWait.setValue(1);
						}
						Logger.logChat("Kill Aura will now wait: "
								+ ticksToWait.getValue() + " ticks.");
					}
				});
	}

	private void attack(Entity entity) {
		long delay = this.delay.getValue();
		if (gcheat.getValue()) {
			// gcheat bypass (used on badlion)
			// attack entities on a random delay between 200 (5 aps) and 166 (6
			// aps)
			delay = 200 - rnd.nextInt(34);
			// starts out at 5 aps then gets a random int to subtract from to
			// make a
			// random delay between 200 and 166
		}
		if (time.hasReached(delay)) {
			final boolean wasSprinting = mc.thePlayer.isSprinting();
			if (autosword.getValue()) {
				mc.thePlayer.inventory.currentItem = EntityHelper
						.getBestWeapon(entity);
				mc.playerController.updateController();
			}
			mc.thePlayer.setSprinting(false);
			if (autohit.getValue()) {
				mc.thePlayer.swingItem();
			} else {
				mc.getNetHandler().addToSendQueue(
						new C0APacketAnimation());
			}
			int oldDamage = 0;
			if (mc.thePlayer.getCurrentEquippedItem() != null) {
				oldDamage = mc.thePlayer.getCurrentEquippedItem()
						.getItemDamage();
			}
			mc.playerController.attackEntity(mc.thePlayer, entity);
			
			if (mc.thePlayer.getCurrentEquippedItem() != null) {
				mc.thePlayer.getCurrentEquippedItem().setItemDamage(oldDamage);
			}
			mc.thePlayer.setSprinting(wasSprinting);
			targets.remove(entity);
			if (targets.size() == 0) {
				time.reset();
			}
		}
	}

	public Value<String> getIgnore() {
		return ignore;
	}

	public List<Entity> getTargets() {
		return targets;
	}

	public boolean isValidTarget(Entity entity) {
		boolean valid = false;
		if (entity.isInvisible() && !invisibles.getValue())
			return false;
		if (entity instanceof EntityPlayer && players.getValue()) {
			if (!ignore.getValue().equals("")
					&& entity.getDisplayName().getFormattedText()
							.startsWith("\247" + ignore.getValue())
					|| entity.getName().equalsIgnoreCase(""))
				return false;
			valid = entity != null
					&& entity != mc.thePlayer
					&& entity.isEntityAlive()
					&& entity.ticksExisted > ticksToWait.getValue()
					&& mc.thePlayer.getDistanceToEntity(entity) <= reach
							.getValue()
					&& !TeamBattleClient.getFriendManager().isFriend(
							entity.getName());
		} else if (entity instanceof IMob && mobs.getValue()) {
			valid = entity != null
					&& entity.isEntityAlive()
					&& entity.ticksExisted > ticksToWait.getValue()
					&& mc.thePlayer.getDistanceToEntity(entity) <= reach
							.getValue();
		} else if (entity instanceof IAnimals && !(entity instanceof IMob)
				&& animals.getValue()) {
			valid = entity != null
					&& entity.isEntityAlive()
					&& entity.ticksExisted > ticksToWait.getValue()
					&& mc.thePlayer.getDistanceToEntity(entity) <= reach
							.getValue();
		}
		return valid;
	}

	@Override
	public void onDisabled() {
		super.onDisabled();
		targets.clear();
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventPreSendMotionUpdates) {
			final EventPreSendMotionUpdates pre = (EventPreSendMotionUpdates) event;
			if (targets.isEmpty()) {
				populateTargets();
			}

			for (final Entity entity : targets) {
				if (isValidTarget(entity)
						&& !((AutoPot) TeamBattleClient.getModManager().getModByName(
								"autopot")).isPotting()) {
					final float[] rotations = EntityHelper
							.getEntityRotations(entity);
					pre.setYaw(direction.getValue()
							&& entity instanceof EntityPlayer ? pre.getYaw()
							: rotations[0]);
					pre.setPitch(direction.getValue()
							&& entity instanceof EntityPlayer ? 95
							: rotations[1]);
					if (!silent.getValue()) {
						mc.thePlayer.rotationYaw = rotations[0];
						mc.thePlayer.rotationPitch = rotations[1];
					}
				} else {
					targets.remove(entity);
				}
			}
		} else if (event instanceof EventPostSendMotionUpdates) {
			for (final Entity entity : targets) {
				if (isValidTarget(entity)) {
					if (autohit.getValue()
							|| !autohit.getValue()
							&& mc.thePlayer.isSwingInProgress
							&& !(mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY && mc.objectMouseOver.entityHit == entity)) {
						attack(entity);
					}
				} else {
					targets.remove(entity);
				}
			}
		} else if (event instanceof EventPacketSent) {
			final EventPacketSent sent = (EventPacketSent) event;
			if (sent.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer player = (C03PacketPlayer) sent
						.getPacket();
				for (final Entity entity : targets) {
					if (isValidTarget(entity)
							&& !((AutoPot) TeamBattleClient.getModManager()
									.getModByName("autopot")).isPotting()) {
						((AutoPot)TeamBattleClient.getModManager().getModByName("autopot")).isPotting();
						
					}
				}
			}
		}
	}

	private void populateTargets() {
		for (final Object o : mc.theWorld.loadedEntityList) {
			final Entity entity = (Entity) o;
			if (!isValidTarget(entity)
					|| targets.size() >= (gcheat.getValue() ? 1 : maxTargets
							.getValue())) {
				continue;
			}
			targets.add(entity);
		}
	}
	
		
	
	}