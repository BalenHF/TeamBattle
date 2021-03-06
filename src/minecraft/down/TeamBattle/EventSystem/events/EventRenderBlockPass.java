package down.TeamBattle.EventSystem.events;

import down.TeamBattle.EventSystem.Event;
import net.minecraft.block.Block;

public class EventRenderBlockPass extends Event {
	private final Block block;
	private int renderBlockPass;

	public EventRenderBlockPass(Block block, int renderBlockPass) {
		this.block = block;
		this.renderBlockPass = renderBlockPass;
	}

	public Block getBlock() {
		return block;
	}

	public int getRenderBlockPass() {
		return renderBlockPass;
	}

	public void setRenderBlockPass(int renderBlockPass) {
		this.renderBlockPass = renderBlockPass;
	}
}
