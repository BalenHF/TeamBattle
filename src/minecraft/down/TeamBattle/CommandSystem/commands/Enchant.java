package down.TeamBattle.CommandSystem.commands;

import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.Utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.StatCollector;

public class Enchant extends Command {
	public Enchant() {
		super("enchant", "<enchant> <level>", "enc");
	}

	@Override
	public void run(String message) {
		if (Minecraft.getMinecraft().playerController.isNotCreative()) {
			Logger.logChat("You must be in creative mode to edit an item's enchants!");
			return;
		}
		final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory
				.getCurrentItem();
		if (itemStack == null) {
			Logger.logChat("You must be holding an item to edit it!");
			return;
		}
		final String name = message.split(" ")[1];
		int level = Integer.parseInt(message.split(" ")[2]);
		if (level > 127) {
			level = 127;
		} else if (level < -127) {
			level = -127;
		}
		boolean found = false;
		for (final Enchantment enchantment : Enchantment.enchantmentsList) {
			if (enchantment == null) {
				continue;
			}
			final String encname = StatCollector.translateToLocal(enchantment
					.getName());
			if (name.equalsIgnoreCase(encname.replaceAll(" ", ""))
					|| name.equalsIgnoreCase("*")) {
				itemStack.addEnchantment(enchantment, level);
				Minecraft
						.getMinecraft()
						.getNetHandler()
						.addToSendQueue(
								new C10PacketCreativeInventoryAction(
										Minecraft.getMinecraft().thePlayer.inventory.currentItem,
										itemStack));
				Logger.logChat("Added enchantment \""
						+ enchantment.getTranslatedName(level)
						+ "\" to your current item.");
				found = true;
			}
		}
		if (!found) {
			Logger.logChat("Invalid enchant.");
		}
	}
}