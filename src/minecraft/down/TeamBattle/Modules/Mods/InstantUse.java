     package down.TeamBattle.Modules.Mods;
          import net.minecraft.item.ItemFood;
     import net.minecraft.item.ItemPotion;
     import net.minecraft.item.ItemStack;
     import net.minecraft.network.play.client.C03PacketPlayer;
     import net.minecraft.network.play.client.C07PacketPlayerDigging;
     import net.minecraft.util.EnumFacing;
import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.ModuleValues.Value;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;
     
     
     
     public class InstantUse extends ModuleBase
     {
    private float eatSpeed = 2.0F;
     private Value<Boolean> sonic = new Value("sonicuse_sonic", 
       Boolean.valueOf(false));
       
       public InstantUse() {
       super("SonicUse", -16776961);
       setTag("Sonic Use");
         
       TeamBattleClient.getCommandManager()
         .getContents()
        .add(new Command("sonicusesonic", "none", new String[] { "sonicsonic", "sus" })
           {
             public void run(String message) {
             InstantUse.this.sonic.setValue(Boolean.valueOf(!((Boolean)InstantUse.this.sonic.getValue()).booleanValue()));
             Logger.logChat("Sonic will " + (
               ((Boolean)InstantUse.this.sonic.getValue()).booleanValue() ? "now" : "no longer") + 
               " sonicly use items.");
             }
           });
       }
       
       private boolean isUsable(ItemStack stack)
       {
       if (stack == null)
         return false;
       if (mc.thePlayer.isUsingItem()) {
         if ((stack.getItem() instanceof net.minecraft.item.ItemBow))
           return true;
         if ((stack.getItem() instanceof ItemFood))
           return true;
         if ((stack.getItem() instanceof ItemPotion))
           return true;
         if ((stack.getItem() instanceof net.minecraft.item.ItemBucketMilk)) {
           return true;
           }
         }
       return false;
       }
       
       public void onEvent(Event event)
       {
       if (((event instanceof EventPostSendMotionUpdates)) && 
         (isUsable(mc.thePlayer.getCurrentEquippedItem())) && (mc.thePlayer.getItemInUseDuration() == 15)) {
         for (int x = 0; x < 15; x++) {
           mc.getNetHandler().addToSendQueue(
             new C03PacketPlayer(mc.thePlayer.onGround));
           mc.getNetHandler().addToSendQueue(
             new C03PacketPlayer(mc.thePlayer.onGround));
           mc.getNetHandler().addToSendQueue(
             new C03PacketPlayer(mc.thePlayer.onGround));
           mc.getNetHandler().addToSendQueue(
             new C03PacketPlayer(mc.thePlayer.onGround));
           mc.getNetHandler().addToSendQueue(
             new C03PacketPlayer(mc.thePlayer.onGround));
           }
         mc.getNetHandler().addToSendQueue(
           new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new net.minecraft.util.BlockPos(-1, -1, -1), EnumFacing.DOWN));
       mc.thePlayer.stopUsingItem();
         }
       }
     }



 