/*    */ package down.TeamBattle.Modules.Mods;
/*    */ /*    */ import net.minecraft.network.play.client.C0BPacketEntityAction;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.Modules.ModuleBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Sneak extends ModuleBase
/*    */ {
/*    */   public Sneak()
/*    */   {
/* 15 */     super("Sneak", 44, -16475287);
/*    */   }
/*    */   
/*    */   public void onDisabled()
/*    */   {
/* 20 */     super.onDisabled();
/* 21 */     if ((mc.thePlayer != null) && (!mc.thePlayer.isSneaking())) {
/* 22 */       mc.getNetHandler().addToSendQueue(
/* 23 */         new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*    */     }
/*    */   }
/*    */   
/*    */   public void onEvent(Event event)
/*    */   {
/* 29 */     if ((event instanceof EventPreSendMotionUpdates)) {
/* 30 */       if ((mc.thePlayer.motionX != 0.0D) && (mc.thePlayer.motionY != 0.0D) && 
/* 31 */         (mc.thePlayer.motionZ != 0.0D) && (!mc.thePlayer.isSneaking())) {
/* 32 */         mc.getNetHandler().addToSendQueue(
/* 33 */           new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
/* 34 */         mc.getNetHandler().addToSendQueue(
/* 35 */           new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*    */       }
/* 37 */     } else if (((event instanceof EventPostSendMotionUpdates)) && 
/* 38 */       (!mc.thePlayer.isSneaking())) {
/* 39 */       mc.getNetHandler().addToSendQueue(
/* 40 */         new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
/* 41 */       mc.getNetHandler().addToSendQueue(
/* 42 */         new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
/*    */     }
/*    */   }
/*    */ }


