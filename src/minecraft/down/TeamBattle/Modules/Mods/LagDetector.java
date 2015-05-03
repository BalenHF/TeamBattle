
package down.TeamBattle.Modules.Mods;

import net.minecraft.network.play.server.S02PacketChat;
import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventHUDDraw;
import down.TeamBattle.EventSystem.events.EventPacketReceive;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.TimeHelper;

public final class LagDetector extends ModuleBase
{

    public LagDetector()
    {
        super("LagDetector");
        setEnabled(true);
    }

    public TimeHelper getTime()
    {
        return time;
    }

    public void onEvent(Event event)
    {
        if(event instanceof EventHUDDraw)
        {
            if(time.hasReached(1000L))
                mc.fontRendererObj.drawString((new StringBuilder("TBH: \247a")).append(time.getCurrentMS() - time.getLastMS()).append(" \247cms").toString(), 2, !((Boolean)TeamBattleClient.getValueManager().getValueByName("hud_watermark").getValue()).booleanValue() && !((Boolean)TeamBattleClient.getValueManager().getValueByName("hud_time").getValue()).booleanValue() ? 2 : 15, 200);
        } else
        if(event instanceof EventPacketReceive)
        {
            EventPacketReceive receive = (EventPacketReceive)event;
            if(!(receive.getPacket() instanceof S02PacketChat))
                time.reset();
        }
    }

    private final TimeHelper time = new TimeHelper();
}