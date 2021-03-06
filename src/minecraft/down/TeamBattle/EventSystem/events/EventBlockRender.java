package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;
import net.minecraft.block.Block;

public final class EventBlockRender extends Event {
	private final Block block;
	private boolean renderAllFaces;
	private int x;
	private int y;
	private int z;

	public EventBlockRender(Block block, int x, int y, int z) {
		this.block = block;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Block getBlock() {
		return block;
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

	public void setRenderAllFaces(boolean renderAllFaces) {
		this.renderAllFaces = renderAllFaces;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public boolean shouldRenderAllFaces() {
		return renderAllFaces;
	}
}
