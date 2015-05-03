
package down.TeamBattle.Modules.Mods;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Timer;
import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPacketSent;
import down.TeamBattle.EventSystem.events.EventPlayerMovement;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.ModuleValues.Value;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.BlockHelper;
import down.TeamBattle.Utils.Logger;


public final class Blast extends ModuleBase
{

    public Blast()
    {
        super("Blast", 50, 0xffe8552d);
        TeamBattleClient.getCommandManager().getContents().add(new Command("blast", "<double>", new String[] {
            "sp"
        }) {

            public void run(String message)
            {
                if(message.split(" ")[1].equalsIgnoreCase("-d"))
                    blast.setValue((Double)blast.getDefaultValue());
                else
                    blast.setValue(Double.valueOf(Double.parseDouble(message.split(" ")[1])));
                if(((Double)blast.getValue()).doubleValue() > 10D)
                    blast.setValue(Double.valueOf(10D));
                else
                if(((Double)blast.getValue()).doubleValue() < 1.0D)
                    blast.setValue(Double.valueOf(1.0D));
                Logger.logChat((new StringBuilder("Blast set to: ")).append(blast.getValue()).toString());
            }

            
        }
);
        TeamBattleClient.getCommandManager().getContents().add(new Command("blastnocheatblast", "<double>", new String[] {
            "sncblast", "sncs"
        }) {

            public void run(String message)
            {
                if(message.split(" ")[1].equalsIgnoreCase("-d"))
                    ncBlast.setValue((Double)ncBlast.getDefaultValue());
                else
                    ncBlast.setValue(Double.valueOf(Double.parseDouble(message.split(" ")[1])));
                if(((Double)ncBlast.getValue()).doubleValue() > 2.6000000000000001D)
                    ncBlast.setValue(Double.valueOf(2.6000000000000001D));
                else
                if(((Double)ncBlast.getValue()).doubleValue() < 1.0D)
                    ncBlast.setValue(Double.valueOf(1.0D));
                Logger.logChat((new StringBuilder("Blast NoCheat Blast set to: ")).append(ncBlast.getValue()).toString());
            }

            
        }
);
        TeamBattleClient.getCommandManager().getContents().add(new Command("blastsprint", "<double>", new String[] {
            "ss"
        }) {

            public void run(String message)
            {
                sprint.setValue(Boolean.valueOf(!((Boolean)sprint.getValue()).booleanValue()));
                Logger.logChat((new StringBuilder("Blast will ")).append(((Boolean)sprint.getValue()).booleanValue() ? "now" : "no longer").append(" sprint for you.").toString());
            }

           
        }
);
        TeamBattleClient.getCommandManager().getContents().add(new Command("blastfastice", "<double>", new String[] {
            "sfi"
        }) {

            public void run(String message)
            {
                fastice.setValue(Boolean.valueOf(!((Boolean)fastice.getValue()).booleanValue()));
                Logger.logChat((new StringBuilder("Blast will ")).append(((Boolean)fastice.getValue()).booleanValue() ? "now" : "no longer").append(" go fast on ice.").toString());
            }

            
        }
);
        TeamBattleClient.getCommandManager().getContents().add(new Command("blastfastladder", "<double>", new String[] {
            "sfl"
        }) {

            public void run(String message)
            {
                fastladder.setValue(Boolean.valueOf(!((Boolean)fastladder.getValue()).booleanValue()));
                Logger.logChat((new StringBuilder("Blast will ")).append(((Boolean)fastladder.getValue()).booleanValue() ? "now" : "no longer").append(" go fast on ladders.").toString());
            }

           

           
        }
);
        TeamBattleClient.getCommandManager().getContents().add(new Command("blastnocheat", "<double>", new String[] {
            "snc", "sn"
        }) {

            public void run(String message)
            {
                nocheat.setValue(Boolean.valueOf(!((Boolean)nocheat.getValue()).booleanValue()));
                setColor(((Boolean)nocheat.getValue()).booleanValue() ? 0xffe8552d : 0xff58db8b);
                Logger.logChat((new StringBuilder("Blast will ")).append(((Boolean)nocheat.getValue()).booleanValue() ? "now" : "no longer").append(" go extra fast on NoCheat.").toString());
                Logger.logChat("\247c[WARNING]\247f The blast is kinda glitchy on the latest version of NC+, use Blast instead.");
            }

           
        }
);
    }

    public void onDisabled()
    {
        super.onDisabled();
        if(mc.timer != null)
        {
            Timer _tmp = mc.timer;
            Timer.timerSpeed = 1.0F;
        }
        if(mc.theWorld != null)
        {
            Blocks.ice.slipperiness = 0.98F;
            Blocks.packed_ice.slipperiness = 0.98F;
        }
    }

    public void onEnabled()
    {
        super.onEnabled();
        setColor(((Boolean)nocheat.getValue()).booleanValue() ? 0xffe8552d : 0xff58db8b);
    }

    public void onEvent(Event event)
    {
        if(event instanceof EventPlayerMovement)
        {
            EventPlayerMovement movement = (EventPlayerMovement)event;
            if(((Boolean)sprint.getValue()).booleanValue())
                mc.thePlayer.setSprinting(mc.thePlayer.moveForward > 0.0F && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 6);
            if(((Boolean)fastladder.getValue()).booleanValue() && BlockHelper.isOnLadder() && mc.thePlayer.isCollidedHorizontally)
            {
                movement.setMotionY(movement.getMotionY() * 2.25D);
                mc.thePlayer.motionY += 0.14999999999999999D;
            }
            if(((Boolean)fastice.getValue()).booleanValue() && BlockHelper.isOnIce())
            {
                Blocks.ice.slipperiness = 0.6F;
                Blocks.packed_ice.slipperiness = 0.6F;
                movement.setMotionX(movement.getMotionX() * 2.5D);
                movement.setMotionZ(movement.getMotionZ() * 2.5D);
            } else
            {
                Blocks.ice.slipperiness = 0.98F;
                Blocks.packed_ice.slipperiness = 0.98F;
            }
            if(!((Boolean)nocheat.getValue()).booleanValue())
            {
                movement.setMotionX(movement.getMotionX() * ((Double)this.blast.getValue()).doubleValue());
                movement.setMotionZ(movement.getMotionZ() * ((Double)this.blast.getValue()).doubleValue());
            }
        } else
        if(event instanceof EventPreSendMotionUpdates)
        {
            if(((Boolean)nocheat.getValue()).booleanValue())
            {
               // KillAura aura = (KillAura)TeamBattleClient.getModManager().getModByName("killaura");
                double blast = BlockHelper.isOnLiquid() ? 1.5D : ((Double)ncBlast.getValue()).doubleValue();
                boolean strafe = mc.thePlayer.moveStrafing != 0.0F;
                blast += mc.thePlayer.isSprinting() ? 0.02D : 0.40000000000000002D;
                if(!strafe)
                    blast += 0.039999999105930328D;
                if(shouldBlastUp())
                {
                    nextTick = !nextTick;
                    if(nextTick)
                    {
                        Timer _tmp = mc.timer;
                        Timer.timerSpeed = 3F;
                    } else
                    {
                        Timer _tmp1 = mc.timer;
                        Timer.timerSpeed = 1.8F;
                    }
                } else
                if(nextTick)
                {
                    Timer _tmp2 = mc.timer;
                    Timer.timerSpeed = 1.0F;
                    nextTick = false;
                }
            } else
            {
                Timer _tmp3 = mc.timer;
                if(Timer.timerSpeed != 1.0F)
                {
                    Timer _tmp4 = mc.timer;
                    Timer.timerSpeed = 1.0F;
                }
            }
        } else
        if((event instanceof EventPacketSent) && ((Boolean)nocheat.getValue()).booleanValue())
        {
            EventPacketSent sent = (EventPacketSent)event;
            if(shouldBlastUp() && nextTick && (sent.getPacket() instanceof C03PacketPlayer))
            {
                C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                packetsDelay++;
                if(player instanceof C03PacketPlayer)
                {
                    if(packetsDelay < 5)
                        return;
                    packetsDelay = 0;
                    ((EventPacketSent)event).setCancelled(true);
                }
            }
        }
    }

    private boolean shouldBlastUp()
    {
        boolean moving = mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F;
        boolean walkable = true;
        return !mc.thePlayer.isInWater() && !BlockHelper.isInLiquid() && !BlockHelper.isOnIce() && !BlockHelper.isOnLadder() && !mc.thePlayer.isSneaking() && mc.thePlayer.onGround && moving && walkable;
    }

    private final Value fastice = new Value("blast_fastice", Boolean.valueOf(true));
    private final Value fastladder = new Value("blast_fastladder", Boolean.valueOf(true));
    private final Value ncBlast = new Value("blast_nocheat_blast", Double.valueOf(2.5D));
    private boolean nextTick;
    private final Value nocheat = new Value("blast_nocheat", Boolean.valueOf(true));
    private final Value blast = new Value("blast_blast", Double.valueOf(1.0D));
    private final Value sprint = new Value("blast_sprint", Boolean.valueOf(true));
    private int timerDelay;
    private int packetsDelay;
    final Blast blasty = (Blast)TeamBattleClient.getModManager().getModByName("blast");






}