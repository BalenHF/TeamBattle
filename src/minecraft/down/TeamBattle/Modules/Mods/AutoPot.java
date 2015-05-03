/*     */ package down.TeamBattle.Modules.Mods;
/*     */ /*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.ModuleValues.Value;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;
import down.TeamBattle.Utils.TimeHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AutoPot extends ModuleBase
/*     */ {
/*  22 */   private final Value<Long> delay = new Value("autopot_delay", Long.valueOf(500L));
/*  23 */   private final Value<Float> health = new Value("autopot_health", 
/*  24 */     Float.valueOf(18.0F));
/*     */   private boolean potting;
/*  26 */   private final TimeHelper time = new TimeHelper();
/*     */   
/*     */   public AutoPot() {
/*  29 */     super("AutoPot", Potion.heal.getLiquidColor());
/*  30 */     TeamBattleClient.getCommandManager()
/*  31 */       .getContents()
/*  32 */       .add(new Command("autopotdelay", "<milliseconds>", new String[] { "potdelay", 
/*  33 */       "apd" })
/*     */       {
/*     */         public void run(String message)
/*     */         {
/*  36 */           if (message.split(" ")[1].equalsIgnoreCase("-d")) {
/*  37 */             AutoPot.this.delay.setValue((Long)AutoPot.this.delay.getDefaultValue());
/*     */           } else {
/*  39 */             AutoPot.this.delay.setValue(Long.valueOf(Long.parseLong(message.split(" ")[1])));
/*     */           }
/*     */           
/*  42 */           if (((Long)AutoPot.this.delay.getValue()).longValue() > 1000L) {
/*  43 */             AutoPot.this.delay.setValue(Long.valueOf(1000L));
/*  44 */           } else if (((Long)AutoPot.this.delay.getValue()).longValue() < 1L) {
/*  45 */             AutoPot.this.delay.setValue(Long.valueOf(1L));
/*     */           }
/*  47 */           Logger.logChat(
/*  48 */             "AutoPot Delay set to: " + AutoPot.this.delay.getValue());
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*  53 */       });TeamBattleClient.getCommandManager().getContents()
/*  54 */       .add(new Command("autopothealth", "<health>", new String[] { "pothealth", 
/*  55 */       "aph" })
/*     */       {
/*     */         public void run(String message)
/*     */         {
/*  58 */           if (message.split(" ")[1].equalsIgnoreCase("-d")) {
/*  59 */             AutoPot.this.health.setValue((Float)AutoPot.this.health.getDefaultValue());
/*     */           } else {
/*  61 */             AutoPot.this.health.setValue(Float.valueOf(Float.parseFloat(message.split(" ")[1])));
/*     */           }
/*     */           
/*  64 */           if (((Float)AutoPot.this.health.getValue()).floatValue() < 1.0D) {
/*  65 */             AutoPot.this.health.setValue(Float.valueOf(1.0F));
/*     */           }
/*  67 */          Logger.logChat(
/*  68 */             "AutoPot Health set to: " + AutoPot.this.health.getValue());
/*     */         }
/*     */         
/*     */ 
/*  72 */       });TeamBattleClient.getCommandManager().getContents()
/*  73 */       .add(new Command("autopot", "none", new String[] { "pot" })
/*     */       {
/*     */         public void run(String message) {
/*  76 */           if (AutoPot.this.doesHotbarHavePots()) {
/*  77 */             AutoPot.this.splashPot();
/*     */           } else {
/*  79 */             AutoPot.this.getPotsFromInventory();
/*  80 */             AutoPot.this.splashPot();
/*     */           }
/*     */         }
/*     */       });
/*     */   }
/*     */   
/*     */   private boolean doesHotbarHavePots() {
/*  87 */     for (int index = 36; index < 45; index++) {
/*  88 */       ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
/*  89 */         index).getStack();
/*  90 */       if (stack != null)
/*     */       {
/*     */ 
/*  93 */         if (isStackSplashHealthPot(stack))
/*  94 */           return true; }
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   private void getPotsFromInventory() {
/* 100 */     if ((mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiChest))
/* 101 */       return;
/* 102 */     for (int index = 9; index < 36; index++) {
/* 103 */       ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
/* 104 */         index).getStack();
/* 105 */       if (stack != null)
/*     */       {
/*     */ 
/* 108 */         if (isStackSplashHealthPot(stack)) {
/* 109 */           mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
/* 110 */           break;
/*     */         } }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isPotting() {
/* 116 */     return this.potting;
/*     */   }
/*     */   
/*     */   private boolean isStackSplashHealthPot(ItemStack stack) {
/* 120 */     if (stack == null)
/* 121 */       return false;
/* 122 */     if ((stack.getItem() instanceof ItemPotion)) {
/* 123 */       ItemPotion potion = (ItemPotion)stack.getItem();
/* 124 */       if (ItemPotion.isSplash(stack.getItemDamage())) {
/* 125 */         for (Object o : potion.getEffects(stack)) {
/* 126 */           PotionEffect effect = (PotionEffect)o;
/* 127 */           if (effect.getPotionID() == Potion.heal.id)
/* 128 */             return true;
/*     */         }
/*     */       }
/*     */     }
/* 132 */     return false;
/*     */   }
/*     */   
/*     */   public void onEvent(Event event)
/*     */   {
/* 137 */     if ((event instanceof EventPreSendMotionUpdates)) {
/* 138 */       if (updateCounter() == 0)
/* 139 */         return;
/* 140 */       EventPreSendMotionUpdates pre = (EventPreSendMotionUpdates)event;
/* 141 */       if ((mc.thePlayer.getHealth() <= ((Float)this.health.getValue()).floatValue()) && 
/* 142 */         (this.time.hasReached((float)((Long)this.delay.getValue()).longValue())) && 
/* 143 */         (doesHotbarHavePots())) {
/* 144 */         this.potting = true;
/* 145 */         pre.setPitch(90.0F);
/*     */       }
/*     */     }
/* 148 */     else if ((event instanceof EventPostSendMotionUpdates)) {
/* 149 */       if (updateCounter() == 0)
/* 150 */         return;
/* 151 */       if ((mc.thePlayer.getHealth() <= ((Float)this.health.getValue()).floatValue()) && 
/* 152 */         (this.time.hasReached((float)((Long)this.delay.getValue()).longValue()))) {
/* 153 */         if (doesHotbarHavePots()) {
/* 154 */           splashPot();
/* 155 */           this.potting = false;
/*     */         } else {
/* 157 */           getPotsFromInventory();
/*     */         }
/* 159 */         this.time.reset();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void splashPot() {
/* 165 */     for (int index = 36; index < 45; index++) {
/* 166 */       ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
/* 167 */         index).getStack();
/* 168 */       if (stack != null)
/*     */       {
/*     */ 
/*     */ 
/* 172 */         if (isStackSplashHealthPot(stack)) {
/* 173 */           int oldslot = mc.thePlayer.inventory.currentItem;
/* 174 */           this.potting = true;
/* 175 */           mc.getNetHandler().addToSendQueue(
/* 176 */             new net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook(
/* 177 */             mc.thePlayer.rotationYaw, 95.0F, 
/* 178 */             mc.thePlayer.onGround));
/* 179 */           mc.getNetHandler().addToSendQueue(
/* 180 */             new net.minecraft.network.play.client.C09PacketHeldItemChange(index - 36));
/* 181 */           mc.playerController.updateController();
/* 182 */           mc.getNetHandler().addToSendQueue(
/* 183 */             new net.minecraft.network.play.client.C08PacketPlayerBlockPlacement(stack));
/* 184 */           mc.getNetHandler().addToSendQueue(
/* 185 */             new net.minecraft.network.play.client.C09PacketHeldItemChange(oldslot));
/* 186 */           this.potting = false;
/* 187 */           mc.getNetHandler().addToSendQueue(
/* 188 */             new net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook(
/* 189 */             mc.thePlayer.rotationYaw, 
/* 190 */             mc.thePlayer.rotationPitch, 
/* 191 */             mc.thePlayer.onGround));
/* 192 */           break;
/*     */         } }
/*     */     }
/*     */   }
/*     */   
/*     */   private int updateCounter() {
/* 198 */     int counter = 0;
/* 199 */     for (int index = 9; index < 45; index++) {
/* 200 */       ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(
/* 201 */         index).getStack();
/* 202 */       if (stack != null)
/*     */       {
/*     */ 
/* 205 */         if (isStackSplashHealthPot(stack)) {
/* 206 */           counter += stack.stackSize;
/*     */         }
/*     */       }
/*     */     }
/* 210 */     setTag(getName() + "§f: §7" + counter);
/* 211 */     return counter;
/*     */   }
/*     */ }

