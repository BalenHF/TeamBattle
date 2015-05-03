/*     */ package down.TeamBattle.Modules.Mods;
/*     */ /*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Speed extends ModuleBase
/*     */ {
/*  19 */   private final Value<Boolean> fastice = new Value("speed_fastice", 
/*  20 */     Boolean.valueOf(true));
/*  21 */   private final Value<Boolean> fastladder = new Value(
/*  22 */     "speed_fastladder", Boolean.valueOf(true));
/*  23 */   private final Value<Double> ncSpeed = new Value(
/*  24 */     "speed_nocheat_speed", Double.valueOf(2.6D));
/*     */   private boolean nextTick;
/*  26 */   private final Value<Boolean> nocheat = new Value("speed_nocheat", 
/*  27 */     Boolean.valueOf(false));
/*  28 */   private final Value<Double> speed = new Value("speed_speed", Double.valueOf(1.0D));
/*  29 */   private final Value<Boolean> sprint = new Value("speed_sprint", 
/*  30 */     Boolean.valueOf(true));
/*  31 */   int s = 0;
/*     */   private int packetsDelay;
/*     */   private int Ticks;
/*     */   
/*     */   public Speed() {
/*  36 */     super("Speed", -1551059);
/*  37 */     TeamBattleClient.getCommandManager().getContents()
/*  38 */       .add(new Command("speed", "<double>", new String[] { "sp" })
/*     */       {
/*     */         public void run(String message) {
/*  41 */           if (message.split(" ")[1].equalsIgnoreCase("-d")) {
/*  42 */             Speed.this.speed.setValue((Double)Speed.this.speed.getDefaultValue());
/*     */           } else {
/*  44 */             Speed.this.speed.setValue(Double.valueOf(Double.parseDouble(message
/*  45 */               .split(" ")[1])));
/*     */           }
/*  47 */           if (((Double)Speed.this.speed.getValue()).doubleValue() > 10.0D) {
/*  48 */             Speed.this.speed.setValue(Double.valueOf(10.0D));
/*  49 */           } else if (((Double)Speed.this.speed.getValue()).doubleValue() < 1.0D) {
/*  50 */             Speed.this.speed.setValue(Double.valueOf(1.0D));
/*     */           }
/*  52 */           Logger.logChat("Speed set to: " + Speed.this.speed.getValue());
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*  57 */       });TeamBattleClient.getCommandManager().getContents()
/*  58 */       .add(new Command("speednocheatspeed", "<double>", new String[] { "sncspeed", 
/*  59 */       "sncs" })
/*     */       {
/*     */         public void run(String message)
/*     */         {
/*  62 */           if (message.split(" ")[1].equalsIgnoreCase("-d")) {
/*  63 */             Speed.this.ncSpeed.setValue((Double)Speed.this.ncSpeed.getDefaultValue());
/*     */           } else {
/*  65 */             Speed.this.ncSpeed.setValue(Double.valueOf(Double.parseDouble(message
/*  66 */               .split(" ")[1])));
/*     */           }
/*     */           
/*  69 */           if (((Double)Speed.this.ncSpeed.getValue()).doubleValue() > 2.6D) {
/*  70 */             Speed.this.ncSpeed.setValue(Double.valueOf(2.6D));
/*  71 */           } else if (((Double)Speed.this.ncSpeed.getValue()).doubleValue() < 1.0D) {
/*  72 */             Speed.this.ncSpeed.setValue(Double.valueOf(1.0D));
/*     */           }
/*  74 */           Logger.logChat(
/*  75 */             "Speed NoCheat Speed set to: " + Speed.this.ncSpeed.getValue());
/*     */         }
/*     */         
/*     */ 
/*  79 */       });TeamBattleClient.getCommandManager().getContents()
/*  80 */       .add(new Command("speedsprint", "<double>", new String[] { "ss" })
/*     */       {
/*     */         public void run(String message) {
/*  83 */           Speed.this.sprint.setValue(Boolean.valueOf(!((Boolean)Speed.this.sprint.getValue()).booleanValue()));
/*  84 */           Logger.logChat("Speed will " + (
/*  85 */             ((Boolean)Speed.this.sprint.getValue()).booleanValue() ? "now" : "no longer") + 
/*  86 */             " sprint for you.");
/*     */         }
/*     */         
/*  89 */       });TeamBattleClient.getCommandManager().getContents()
/*  90 */       .add(new Command("speedfastice", "<double>", new String[] { "sfi" })
/*     */       {
/*     */         public void run(String message) {
/*  93 */           Speed.this.fastice.setValue(Boolean.valueOf(!((Boolean)Speed.this.fastice.getValue()).booleanValue()));
/*  94 */           Logger.logChat("Speed will " + (
/*  95 */             ((Boolean)Speed.this.fastice.getValue()).booleanValue() ? "now" : "no longer") + 
/*  96 */             " go fast on ice.");
/*     */         }
/*     */         
/*  99 */       });TeamBattleClient.getCommandManager().getContents()
/* 100 */       .add(new Command("speedfastladder", "<double>", new String[] { "sfl" })
/*     */       {
/*     */         public void run(String message) {
/* 103 */           Speed.this.fastladder.setValue(Boolean.valueOf(!((Boolean)Speed.this.fastladder.getValue()).booleanValue()));
/* 104 */           Logger.logChat("Speed will " + (
/* 105 */             ((Boolean)Speed.this.fastladder.getValue()).booleanValue() ? "now" : "no longer") + 
/* 106 */             " go fast on ladders.");
/*     */         }
/*     */         
/* 109 */       });TeamBattleClient.getCommandManager().getContents()
/* 110 */       .add(new Command("speednocheat", "<double>", new String[] { "snc", "sn" })
/*     */       {
/*     */         public void run(String message) {
/* 113 */           Speed.this.nocheat.setValue(Boolean.valueOf(!((Boolean)Speed.this.nocheat.getValue()).booleanValue()));
/* 114 */           Speed.this.setColor(((Boolean)Speed.this.nocheat.getValue()).booleanValue() ? -1551059 : -10953845);
/* 115 */           Logger.logChat("Speed will " + (
/* 116 */             ((Boolean)Speed.this.nocheat.getValue()).booleanValue() ? "now" : "no longer") + 
/* 117 */             " go extra fast on NoCheat.");
/*     */         }
/*     */       });
/*     */   }
/*     */   
/*     */   public void onDisabled()
/*     */   {
/* 124 */     super.onDisabled();
/* 125 */     if (mc.timer != null) {
/* 126 */       net.minecraft.util.Timer.timerSpeed = 1.0F;
/*     */     }
/*     */     
/* 129 */     if (mc.theWorld != null) {
/* 130 */       Blocks.ice.slipperiness = 1.0F;
/* 131 */       Blocks.packed_ice.slipperiness = 0.98F;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onEnabled()
/*     */   {
/* 137 */     super.onEnabled();
/* 138 */     setColor(((Boolean)this.nocheat.getValue()).booleanValue() ? -1551059 : -10953845);
/*     */   }
/*     */   
/*     */   public void onEvent(Event event)
/*     */   {
/* 143 */     if ((event instanceof EventPlayerMovement)) {
/* 144 */       EventPlayerMovement movement = (EventPlayerMovement)event;
/* 145 */       if (((Boolean)this.sprint.getValue()).booleanValue()) {
/* 146 */         if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0.0F && mc.thePlayer.getFoodStats().getFoodLevel() > 6)
{
	mc.thePlayer.setSprinting(true);
}
/*     */       }
/*     */       
/* 151 */       if ((((Boolean)this.fastladder.getValue()).booleanValue()) && (BlockHelper.isOnLadder()) && 
/* 152 */         (mc.thePlayer.isCollidedHorizontally)) {
/* 153 */         movement.setMotionY(movement.getMotionY() * 2.4D);
/* 154 */         mc.thePlayer.motionY += 0.15D;
/*     */       }
/*     */       
/* 157 */       if ((((Boolean)this.fastice.getValue()).booleanValue()) && (BlockHelper.isOnIce())) {
/* 158 */         Blocks.ice.slipperiness = 0.6F;
/* 159 */         Blocks.packed_ice.slipperiness = 0.6F;
/* 160 */         movement.setMotionX(movement.getMotionX() * 2.5D);
/* 161 */         movement.setMotionZ(movement.getMotionZ() * 2.5D);
/*     */       } else {
/* 163 */         Blocks.ice.slipperiness = 0.98F;
/* 164 */         Blocks.packed_ice.slipperiness = 0.98F;
/*     */       }
/*     */       
/* 167 */       ((Boolean)this.nocheat.getValue()).booleanValue();
/*     */     }
/* 169 */     else if ((event instanceof EventPreSendMotionUpdates)) {
/* 170 */       KillAura aura = (KillAura)
/* 171 */         TeamBattleClient.getModManager().getModByName("killaura");
/* 172 */       if (((Boolean)this.nocheat.getValue()).booleanValue()) {
/* 173 */         double speed = (BlockHelper.isOnLiquid()) 
/* 174 */            ? 1.5D : 
/* 175 */           ((Double)this.ncSpeed.getValue()).doubleValue();
/* 176 */         if (shouldSpeedUp()) {
/* 177 */           this.nextTick = (!this.nextTick);
/* 178 */           if (this.nextTick) {
/* 179 */             if ((this.s > 2) && (mc.thePlayer.onGround) && (mc.thePlayer.movementInput.moveForward != 0.0F) && (!mc.gameSettings.keyBindLeft.pressed) && (!mc.gameSettings.keyBindRight.pressed)) {
/* 180 */               if (!BlockHelper.isOnLiquid()) {
/* 181 */                 mc.thePlayer.setPosition(mc.thePlayer.posX + mc.thePlayer.motionX * 2.5D, mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ * 2.5D);
/*     */               }
/* 183 */               net.minecraft.util.Timer.timerSpeed = 1.8F;
/* 184 */               this.s = 0;
/*     */             }
/* 186 */             this.s += 1;
/*     */           } else {
/* 188 */             net.minecraft.util.Timer.timerSpeed = 1.0F;
/*     */           }
/* 190 */         } else if (this.nextTick) {
/* 191 */           net.minecraft.util.Timer.timerSpeed = 1.0F;
/* 192 */           this.nextTick = false;
/*     */         }
/* 194 */       } else if (net.minecraft.util.Timer.timerSpeed != 1.0F) {
/* 195 */         net.minecraft.util.Timer.timerSpeed = 1.0F;
/*     */       }
/* 197 */     } else if (((event instanceof EventPacketSent)) && 
/* 198 */       (((Boolean)this.nocheat.getValue()).booleanValue())) {
/* 199 */       EventPacketSent sent = (EventPacketSent)event;
/* 200 */       if ((shouldSpeedUp()) && 
/* 201 */         ((sent.getPacket() instanceof C03PacketPlayer))) {
/* 202 */         C03PacketPlayer player = (C03PacketPlayer)sent
/* 203 */           .getPacket();
/* 204 */         this.packetsDelay += 1;
/* 205 */         if ((player instanceof C03PacketPlayer)) {
/* 206 */           if (this.packetsDelay < 5) {
/* 207 */             return;
/*     */           }
/*     */           
/* 210 */           this.packetsDelay = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean shouldSpeedUp()
/*     */   {
/* 219 */     boolean moving = (mc.thePlayer.movementInput.moveForward != 0.0F) || 
/* 220 */       (mc.thePlayer.movementInput.moveStrafe != 0.0F);
/* 221 */     boolean walkable = true;
/*     */     
/* 223 */     return (!mc.thePlayer.isInWater()) && (!BlockHelper.isInLiquid()) && 
/* 224 */       (!BlockHelper.isOnIce()) && (!BlockHelper.isOnLadder()) && 
/* 225 */       (!mc.thePlayer.isSneaking()) && (mc.thePlayer.onGround) && 
/* 226 */       (moving) && (walkable);
/*     */   }
/*     */ }


