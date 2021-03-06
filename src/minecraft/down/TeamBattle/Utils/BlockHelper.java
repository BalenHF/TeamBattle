package down.TeamBattle.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class BlockHelper {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static int getBestTool(int x, int y, int z) {
		final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
		int slot = 0;
		float dmg = 0.1F;
		for (int index = 36; index < 45; index++) {
			final ItemStack itemStack = mc.thePlayer.inventoryContainer
					.getSlot(index).getStack();
			if (itemStack != null
					&& block != null
					&& itemStack.getItem().getStrVsBlock(itemStack, block) > dmg) {
				slot = index - 36;
				dmg = itemStack.getItem().getStrVsBlock(itemStack, block);
			}
		}
		if (dmg > 0.1F)
			return slot;
		return mc.thePlayer.inventory.currentItem;
	}

	public static float[] getBlockRotations(int x, int y, int z) {
		final double var4 = x - mc.thePlayer.posX + 0.5D;
		final double var6 = z - mc.thePlayer.posZ + 0.5D;
		final double var8 = y
				- (mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - 1.0D);
		final double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
		final float var12 = (float) (Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
		return new float[] { var12,
				(float) -(Math.atan2(var8, var14) * 180.0D / Math.PI) };
	}

	// this is darkmagician's. credits to him.
	public static boolean isInLiquid() {
		boolean inLiquid = false;
		final int y = (int) mc.thePlayer.boundingBox.copy().minY;
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid))
						return false;
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

	public static boolean isOnIce() {
		boolean onIce = false;
		final int y = (int) mc.thePlayer.boundingBox.copy().offset(0.0D, -0.1D,
				0.0D).minY;
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (block instanceof BlockPackedIce
							|| block instanceof BlockIce) {
						onIce = true;
					}
				}
			}
		}
		return onIce;
	}

	public static boolean isOnLadder() {
		boolean onLadder = false;
		final int y = (int) mc.thePlayer.boundingBox.copy().offset(0.0D, 1.0D,
				0.0D).minY;
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLadder))
						return false;
					onLadder = true;
				}
			}
		}
		return onLadder || mc.thePlayer.isOnLadder();
	}

	// this method is N3xuz_DK's I believe. credits to him.
	public static boolean isOnLiquid() {
		boolean onLiquid = false;
		final int y = (int) mc.thePlayer.boundingBox.copy().offset(0.0D,
				-0.01D, 0.0D).minY;
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid))
						return false;
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}
}
