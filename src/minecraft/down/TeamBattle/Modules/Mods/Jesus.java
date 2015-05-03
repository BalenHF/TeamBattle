/*    */ package down.TeamBattle.Modules.Mods;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventBlockBoundingBox;
import down.TeamBattle.EventSystem.events.EventPacketSent;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.BlockHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Jesus extends ModuleBase
/*    */ {
/*    */   private boolean nextTick;
/*    */   private double wasJesusin;
/*    */   
/*    */   public Jesus()
/*    */   {
/* 23 */     super("Jesus", 36, -7750957);
/*    */   }
/*    */   
/*    */   public void onEvent(Event event)
/*    */   {
/* 28 */     if ((event instanceof EventBlockBoundingBox)) {
/* 29 */       if (mc.thePlayer == null)
/* 30 */         return;
/* 31 */       EventBlockBoundingBox block = (EventBlockBoundingBox)event;
/* 32 */       if (((block.getBlock() instanceof BlockLiquid)) && 
/* 33 */         (!BlockHelper.isInLiquid()) && 
/* 34 */         (mc.thePlayer.fallDistance < 3.0F) && 
/* 35 */         (!mc.thePlayer.isSneaking())) {
/* 36 */         block.setBoundingBox(new AxisAlignedBB(block.getX(), 
/* 37 */           block.getY(), block.getZ(), block.getX() + 1, 
/* 38 */           block.getY() + 1, block.getZ() + 1));
/*    */       }
/* 40 */     } else if ((event instanceof EventPreSendMotionUpdates)) {
/* 41 */       if ((BlockHelper.isInLiquid()) && 
/* 42 */         (mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.air)) && 
/* 43 */         (!mc.thePlayer.isSneaking())) {
/* 44 */         this.wasJesusin = mc.thePlayer.motionY;
/* 45 */         mc.thePlayer.motionY = 0.08D;
/*    */       }
/* 47 */     } else if ((!(event instanceof EventPostSendMotionUpdates)) && 
/* 48 */       ((event instanceof EventPacketSent))) {
/* 49 */       EventPacketSent sent = (EventPacketSent)event;
/* 50 */       if ((sent.getPacket() instanceof C03PacketPlayer)) {
/* 51 */         C03PacketPlayer player = (C03PacketPlayer)sent
/* 52 */           .getPacket();
/* 53 */         if (BlockHelper.isOnLiquid()) {
/* 54 */           this.nextTick = (!this.nextTick);
/* 55 */           if (this.nextTick) {
/* 56 */             player.y -= 0.01D;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


