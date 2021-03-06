package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public final class EventBlockBoundingBox extends Event {
	private final Block block;
	private AxisAlignedBB boundingBox;
	private final int x, y, z;

	public EventBlockBoundingBox(AxisAlignedBB boundingBox, Block block, int x,
			int y, int z) {
		this.boundingBox = boundingBox;
		this.block = block;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Block getBlock() {
		return block;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
}
