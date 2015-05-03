package down.TeamBattle.Modules.Mods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventHUDDraw;
import down.TeamBattle.ModuleValues.Value;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;

public final class HUD extends ModuleBase {
	private final Value<Boolean> armor = new Value<Boolean>("hud_armor_status",
			true);
	private final Value<Boolean> arraylist = new Value<Boolean>(
			"hud_arraylist", true);
	private final Value<Boolean> coords = new Value<Boolean>("hud_coords", true);
	private final Value<Boolean> fps = new Value<Boolean>("hud_fps", true);
	private final Value<Boolean> ign = new Value<Boolean>("hud_in-game_name",
			false);
	private final Value<Boolean> item = new Value<Boolean>("hud_item_status",
			false);
	private final RenderItem itemRender = new RenderItem(mc.renderEngine,mc.modelManager);
	private final Value<Boolean> potions = new Value<Boolean>(
			"hud_potion_effects", true);
	private final Value<Boolean> time = new Value<Boolean>("hud_time", false);
	private final Value<Boolean> watermark = new Value<Boolean>(
			"hud_watermark", true);

	public HUD() {
		super("HUD");
		setEnabled(true);

		TeamBattleClient.getCommandManager()
				.getContents()
				.add(new Command(
						"hud",
						"<watermark/arraylist/coords/fps/ign/time/potions/item/armor>",
						new String[] {}) {
					@Override
					public void run(String message) {
						if (message.split(" ")[1].equalsIgnoreCase("watermark")) {
							watermark.setValue(!watermark.getValue());
							Logger.logChat("HUD will "
									+ (watermark.getValue() ? "now"
											: "no longer")
									+ " show the watermark.");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("arraylist")) {
							arraylist.setValue(!arraylist.getValue());
							Logger.logChat("HUD will "
									+ (arraylist.getValue() ? "now"
											: "no longer")
									+ " show the arraylist.");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("coords")) {
							coords.setValue(!coords.getValue());
							Logger.logChat("HUD will "
									+ (coords.getValue() ? "now" : "no longer")
									+ " show your coordinates. (XYZ)");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("fps")) {
							fps.setValue(!fps.getValue());
							Logger.logChat("HUD will "
									+ (fps.getValue() ? "now" : "no longer")
									+ " show your FPS.");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("ign")) {
							ign.setValue(!ign.getValue());
							Logger.logChat("HUD will "
									+ (ign.getValue() ? "now" : "no longer")
									+ " show your in-game name. (IGN)");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("time")) {
							time.setValue(!time.getValue());
							Logger.logChat("HUD will "
									+ (time.getValue() ? "now" : "no longer")
									+ " show system time.");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("item")) {
							item.setValue(!item.getValue());
							Logger.logChat("HUD will "
									+ (item.getValue() ? "now" : "no longer")
									+ " show your item status.");
						} else if (message.split(" ")[1]
								.equalsIgnoreCase("armor")) {
							armor.setValue(!armor.getValue());
							Logger.logChat("HUD will "
									+ (armor.getValue() ? "now" : "no longer")
									+ " show your armor status.");
						} else {
							Logger.logChat("Invalid option! Valid options: watermark, arraylist, coords, fps, ign, time, potions, item, armor");
						}
						TeamBattleClient.getFileManager().getFileByName("hudconfig")
								.saveFile();
					}
				});
	}

	private void drawArmorStatus(ScaledResolution scaledRes)
    {
        if(mc.playerController.isNotCreative())
        {
            int x = 15;
            GL11.glPushMatrix();
            for(int index = 3; index >= 0; index--)
            {
                ItemStack stack = mc.thePlayer.inventory.armorInventory[index];
                if(stack != null)
                {
                    itemRender.func_175042_a(stack, scaledRes.getScaledWidth() / 2 + x, scaledRes.getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(Material.water) ? 65 : 55));
                    Minecraft.getMinecraft().getRenderItem().func_175030_a(mc.fontRendererObj, stack, scaledRes.getScaledWidth() / 2 + x, scaledRes.getScaledHeight() - (mc.thePlayer.isInsideOfMaterial(Material.water) ? 65 : 55));
                    x += 18;
                }
            }}

	}

	private void drawArraylist(ScaledResolution scaledRes) {
		int y = 2;
		List<ModuleBase> mods = TeamBattleClient.getModManager().getContents();
		Collections.sort(mods, new Comparator<ModuleBase>() {
			@Override
			public int compare(ModuleBase mod1, ModuleBase mod2) {
				return mod1.getTag().compareTo(mod2.getTag());
			}
		});
		for (final ModuleBase mod : mods) {
			if (!mod.isVisible() || !mod.isEnabled()) {
				continue;
			}
			mc.fontRendererObj.drawString(
					mod.getTag(),
					scaledRes.getScaledWidth()
							- mc.fontRendererObj.getStringWidth(mod.getTag()) - 2,
					y, mod.getColor());
			y += mc.fontRendererObj.FONT_HEIGHT;
		}
	}

	private void drawInformation(ScaledResolution scaledRes) {
		final List<String> info = new ArrayList<String>();
		if (ign.getValue()) {
			info.add("IGN: \2477" + mc.session.getUsername());
		}

		if (fps.getValue()) {
			info.add("FPS: \2477"
					+ mc.debug.split(",")[0].replaceAll(" fps", ""));
		}

		if (coords.getValue()) {
			final int x = MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.posX);
			final int y = MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.posY);
			final int z = MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.posZ);
			info.add("XYZ: \2477" + x + ", " + y + ", " + z);
		}

		int y = scaledRes.getScaledHeight() - 10;
		for (final String text : info) {
			mc.fontRendererObj.drawString(text, 2, y, 0xFFFFFFFF);
			y -= mc.fontRendererObj.FONT_HEIGHT;
		}
	}

	private void drawItemStatus(ScaledResolution scaledRes)
    {
        ItemStack stack = mc.thePlayer.getCurrentEquippedItem();
        if(stack != null && (stack.isItemDamaged() || stack.hasTagCompound()))
        {
            int x = 20;
            int y = ((Boolean)watermark.getValue()).booleanValue() ? ((int) (((LagDetector)TeamBattleClient.getModManager().getModByName("lagdetector")).getTime().hasReached(1000F) ? 2 : 12)) : ((int) (((LagDetector)TeamBattleClient.getModManager().getModByName("lagdetector")).getTime().hasReached(1000F) ? 12 : 44));
            GL11.glPushMatrix();
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(3042);
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glDisable(32826);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            RenderHelper.disableStandardItemLighting();
            if(scaledRes.getScaleFactor() == 2)
            {
                double scale = 0.5D;
                GL11.glScaled(0.5D, 0.5D, 0.5D);
                x *= 2;
                y *= 2;
            }
            String name = stack.getDisplayName();
            if(stack.hasDisplayName())
                name = (new StringBuilder("\247o")).append(name).toString();
            mc.fontRendererObj.drawString(name, x, y, -1);
            y += mc.fontRendererObj.FONT_HEIGHT;
            if(stack.stackSize > 1)
            {
                mc.fontRendererObj.drawString((new StringBuilder("Amount: ")).append(stack.stackSize).toString(), x, y, -1);
                y += mc.fontRendererObj.FONT_HEIGHT;
            }
            if(stack.isItemDamaged() && stack.getMaxDamage() != 0)
            {
                int damagecolor = (int)Math.round(255D - ((double)stack.getItemDamage() * 255D) / (double)stack.getMaxDamage());
                int fixedcolor = 255 - damagecolor << 16 | damagecolor << 8;
                int maxDamage = stack.getMaxDamage();
                int itemDamage = stack.getItemDamage();
                int durability = (100 * (maxDamage - itemDamage)) / maxDamage;
                mc.fontRendererObj.drawString((new StringBuilder("\247fDurability: \247r")).append(durability).toString(), x, y, fixedcolor);
                y += mc.fontRendererObj.FONT_HEIGHT;
            }
            GL11.glPopMatrix();
        }
    }
		

	private void drawPotionEffects(ScaledResolution scaledRes) {
		int y = 10;
		for (final PotionEffect effect : (Collection<PotionEffect>) mc.thePlayer
				.getActivePotionEffects()) {
			final Potion potion = Potion.potionTypes[effect.getPotionID()];
			String name = I18n.format(potion.getName());

			if (effect.getAmplifier() == 1) {
				name = name + " II";
			} else if (effect.getAmplifier() == 2) {
				name = name + " III";
			} else if (effect.getAmplifier() == 3) {
				name = name + " IV";
			}

			name = name + "\247f: \2477" + Potion.getDurationString(effect);
			mc.fontRendererObj.drawString(
					name,
					scaledRes.getScaledWidth()
							- mc.fontRendererObj.getStringWidth(name) - 2,
					scaledRes.getScaledHeight() - y, potion.getLiquidColor());
			y += mc.fontRendererObj.FONT_HEIGHT;
		}
	}

	private void drawTime(ScaledResolution scaledRes) {
		final Date date = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		final String time = formatter.format(date);
		mc.fontRendererObj.drawString(
				time,
				watermark.getValue() ? 4 + mc.fontRendererObj
						.getStringWidth("TeamBattle") : 2, 2, 0xDD808080);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventHUDDraw) {
			final ScaledResolution scaledRes = new ScaledResolution(mc,
					mc.displayWidth, mc.displayHeight);
			if (watermark.getValue()) {
				GL11.glEnable(3042);
				mc.fontRendererObj.drawString("TeamBattle", 2, 2,
						0x8055FFFF);
				
				GL11.glDisable(3042);
			}

			if (arraylist.getValue()) {
				GL11.glEnable(3042);
				drawArraylist(scaledRes);
				GL11.glDisable(3042);
			}

			drawInformation(scaledRes);

			if (time.getValue()) {
				drawTime(scaledRes);
			}

			if (potions.getValue()) {
				drawPotionEffects(scaledRes);
			}

			if (armor.getValue()) {
				drawArmorStatus(scaledRes);
			}

			if (item.getValue()) {
				drawItemStatus(scaledRes);
			}
		}
		
		
	}

}
