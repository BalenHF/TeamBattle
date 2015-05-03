
package down.TeamBattle.Modules.Mods;

import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.Modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Block extends ModuleBase
{

    public Block()
    {
        super("Block");
        setTag("");
        setVisible(false);
        setEnabled(true);
    }

    public void onEvent(Event event)
    {
        if(event instanceof EventPreSendMotionUpdates)
        {
            if(mc.thePlayer.isBlocking())
                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
        } else
        if((event instanceof EventPostSendMotionUpdates) && mc.thePlayer.isBlocking())
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
    }
}