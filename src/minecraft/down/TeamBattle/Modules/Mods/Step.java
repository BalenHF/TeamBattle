/*    */ package down.TeamBattle.Modules.Mods;
/*    */ import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.ModuleValues.Value;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;
/*    */ 
/*    */ 
/*    */ public final class Step extends ModuleBase
/*    */ {
/* 12 */   private final Value<Boolean> reverse = new Value("step_reverse", Boolean.valueOf(false));
/*    */   private boolean editPacket;
/* 14 */   private final Value<Float> height = new Value("step_height", Float.valueOf(1.0F));
/*    */   
/*    */   public Step() {
/* 17 */     super("Step");
/* 18 */     setEnabled(false);
/* 19 */     setVisible(false);
/* 20 */     TeamBattleClient.getCommandManager().getContents()
/* 21 */       .add(new Command("stepheight", "<float>", new String[] { "sh" })
/*    */       {
/*    */         public void run(String message) {
/* 24 */           if (message.split(" ")[1].equalsIgnoreCase("-d")) {
/* 25 */             Step.this.height.setValue((Float)Step.this.height.getDefaultValue());
/*    */           } else {
/* 27 */             Step.this.height.setValue(Float.valueOf(Float.parseFloat(message.split(" ")[1])));
/*    */           }
/*    */           
/* 30 */           if (((Float)Step.this.height.getValue()).floatValue() < 1.0F) {
/* 31 */             Step.this.height.setValue(Float.valueOf(0.5F));
/*    */           }
/* 33 */           Logger.logChat(
/* 34 */             "Step Height set to: " + Step.this.height.getValue());
/*    */ 
/*    */         }
/*    */         
/*    */ 
/* 39 */       });TeamBattleClient.getCommandManager().getContents()
/* 40 */       .add(new Command("stepreverse", "none", new String[] { "sreverse", 
/* 41 */       "srev" })
/*    */       {
/*    */         public void run(String message)
/*    */         {
/* 44 */           Step.this.reverse.setValue(Boolean.valueOf(!((Boolean)Step.this.reverse.getValue()).booleanValue()));
/* 45 */           Logger.logChat("Step will " + (
/* 46 */             ((Boolean)Step.this.reverse.getValue()).booleanValue() ? "now" : "no longer") + 
/* 47 */             " go reverse mode.");
/*    */         }
/*    */       });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onEvent(Event event)
/*    */   {
/* 56 */     if ((event instanceof EventPreSendMotionUpdates)) {
/* 57 */       mc.thePlayer.stepHeight = ((Float)this.height.getValue()).floatValue();
/* 58 */       if ((((Boolean)this.reverse.getValue()).booleanValue()) && 
/* 59 */         (mc.thePlayer.fallDistance < 1.0F) && (mc.thePlayer.fallDistance <= ((Float)this.height.getValue()).floatValue()) && (!mc.thePlayer.capabilities.isFlying) && (!mc.thePlayer.isSneaking()) && (!mc.gameSettings.keyBindJump.pressed)) {
/* 60 */         mc.thePlayer.motionY = -0.8D;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onDisabled()
/*    */   {
/* 69 */     super.onDisabled();
/* 70 */     if (mc.theWorld != null) {
/* 71 */       mc.thePlayer.stepHeight = 0.5F;
/*    */     }
/*    */   }
/*    */   
/*    */   public void onEnabled()
/*    */   {
/* 77 */     super.onEnabled();
/* 78 */     if (mc.theWorld != null) {
/* 79 */       mc.thePlayer.stepHeight = 1.0F;
/*    */     }
/*    */   }
/*    */ }


