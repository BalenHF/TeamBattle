/*     */ package net.minecraft.client.entity;
/*     */ /*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiCommandBlock;
/*     */ import net.minecraft.client.gui.GuiEnchantment;
/*     */ import net.minecraft.client.gui.GuiHopper;
/*     */ import net.minecraft.client.gui.GuiRepair;
/*     */ import net.minecraft.client.gui.GuiScreenBook;
/*     */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*     */ import net.minecraft.client.gui.inventory.GuiChest;
/*     */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*     */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*     */ import net.minecraft.network.play.client.C0CPacketInput;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ import net.minecraft.world.World;
import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.EventSystem.events.EventChatSent;
import down.TeamBattle.EventSystem.events.EventPlayerMovement;
import down.TeamBattle.EventSystem.events.EventPostSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPreSendMotionUpdates;
import down.TeamBattle.EventSystem.events.EventPushOutOfBlocks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityPlayerSP extends AbstractClientPlayer
/*     */ {
/*     */   public final NetHandlerPlayClient sendQueue;
/*     */   private final StatFileWriter field_146108_bO;
/*     */   private double field_175172_bI;
/*     */   private double field_175166_bJ;
/*     */   private double field_175167_bK;
/*     */   private float field_175164_bL;
/*     */   private float field_175165_bM;
/*     */   private boolean field_175170_bN;
/*     */   private boolean field_175171_bO;
/*     */   private int field_175168_bP;
/*     */   private boolean field_175169_bQ;
/*     */   private String clientBrand;
/*     */   public MovementInput movementInput;
/*     */   protected Minecraft mc;
/*     */   protected int sprintToggleTimer;
/*     */   public int sprintingTicksLeft;
/*     */   public float renderArmYaw;
/*     */   public float renderArmPitch;
/*     */   public float prevRenderArmYaw;
/*     */   public float prevRenderArmPitch;
/*     */   private int horseJumpPowerCounter;
/*     */   private float horseJumpPower;
/*     */   public float timeInPortal;
/*     */   public float prevTimeInPortal;
/*     */   private static final String __OBFID = "CL_00000938";
/*     */   
/*     */   public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_)
/*     */   {
/* 106 */     super(worldIn, p_i46278_3_.func_175105_e());
/* 107 */     this.sendQueue = p_i46278_3_;
/* 108 */     this.field_146108_bO = p_i46278_4_;
/* 109 */     this.mc = mcIn;
/* 110 */     this.dimension = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float amount)
/*     */   {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void heal(float p_70691_1_) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mountEntity(Entity entityIn)
/*     */   {
/* 131 */     super.mountEntity(entityIn);
/*     */     
/* 133 */     if ((entityIn instanceof EntityMinecart))
/*     */     {
/* 135 */       this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 144 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ)))
/*     */     {
/* 146 */       super.onUpdate();
/*     */       
/* 148 */       if (isRiding())
/*     */       {
	                    	//TODO 
/* 150 */         EventPreSendMotionUpdates pre = new EventPreSendMotionUpdates(
/* 151 */           this.rotationYaw, this.rotationPitch);
/* 152 */         TeamBattleClient.getEventManager().call(pre);
/* 153 */         if (pre.isCancelled())
/* 154 */           return;
/* 155 */         this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/* 156 */         this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
/* 157 */         				//TODO
                    TeamBattleClient.getEventManager()
/* 158 */           .call(new EventPostSendMotionUpdates());
/*     */       }
/*     */       else
/*     */       {
	//TODO
/* 162 */         EventPreSendMotionUpdates pre = new EventPreSendMotionUpdates(
/* 163 */           this.rotationYaw, this.rotationPitch);
/* 164 */         TeamBattleClient.getEventManager().call(pre);
/* 165 */         if (pre.isCancelled())
/* 166 */           return;
/* 167 */         func_175161_p();
//TODO
/* 168 */         TeamBattleClient.getEventManager()
/* 169 */           .call(new EventPostSendMotionUpdates());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_175161_p()
/*     */   {
/* 176 */     boolean var1 = isSprinting();
/*     */     
/* 178 */     if (var1 != this.field_175171_bO)
/*     */     {
/* 180 */       if (var1)
/*     */       {
/* 182 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
/*     */       }
/*     */       else
/*     */       {
/* 186 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
/*     */       }
/*     */       
/* 189 */       this.field_175171_bO = var1;
/*     */     }
/*     */     
/* 192 */     boolean var2 = isSneaking();
/*     */     
/* 194 */     if (var2 != this.field_175170_bN)
/*     */     {
/* 196 */       if (var2)
/*     */       {
/* 198 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
/*     */       }
/*     */       else
/*     */       {
/* 202 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*     */       }
/*     */       
/* 205 */       this.field_175170_bN = var2;
/*     */     }
/*     */     
/* 208 */     if (func_175160_A())
/*     */     {
/* 210 */       double var3 = this.posX - this.field_175172_bI;
/* 211 */       double var5 = getEntityBoundingBox().minY - this.field_175166_bJ;
/* 212 */       double var7 = this.posZ - this.field_175167_bK;
/* 213 */       double var9 = this.rotationYaw - this.field_175164_bL;
/* 214 */       double var11 = this.rotationPitch - this.field_175165_bM;
/* 215 */       boolean var13 = (var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D) || (this.field_175168_bP >= 20);
/* 216 */       boolean var14 = (var9 != 0.0D) || (var11 != 0.0D);
/*     */       
/* 218 */       if (this.ridingEntity == null)
/*     */       {
/* 220 */         if ((var13) && (var14))
/*     */         {
/* 222 */           this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
/*     */         }
/* 224 */         else if (var13)
/*     */         {
/* 226 */           this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, getEntityBoundingBox().minY, this.posZ, this.onGround));
/*     */         }
/* 228 */         else if (var14)
/*     */         {
/* 230 */           this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/*     */         }
/*     */         else
/*     */         {
/* 234 */           this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 239 */         this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 240 */         var13 = false;
/*     */       }
/*     */       
/* 243 */       this.field_175168_bP += 1;
/*     */       
/* 245 */       if (var13)
/*     */       {
/* 247 */         this.field_175172_bI = this.posX;
/* 248 */         this.field_175166_bJ = getEntityBoundingBox().minY;
/* 249 */         this.field_175167_bK = this.posZ;
/* 250 */         this.field_175168_bP = 0;
/*     */       }
/*     */       
/* 253 */       if (var14)
/*     */       {
/* 255 */         this.field_175164_bL = this.rotationYaw;
/* 256 */         this.field_175165_bM = this.rotationPitch;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityItem dropOneItem(boolean p_71040_1_)
/*     */   {
/* 266 */     C07PacketPlayerDigging.Action var2 = p_71040_1_ ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
/* 267 */     this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 268 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void joinEntityItemWithWorld(EntityItem p_71012_1_) {}
/*     */   
/*     */ 
/*     */ 
/*     */ //TODO
/*     */   public void sendChatMessage(String p_71165_1_)
/*     */   {
/* 281 */     EventChatSent event = new EventChatSent(p_71165_1_);
/* 282 */     TeamBattleClient.getEventManager().call(event);
/* 283 */     event.checkForCommands();
/* 284 */     if (event.isCancelled())
/* 285 */       return;
/* 286 */     this.sendQueue.addToSendQueue(new C01PacketChatMessage(p_71165_1_));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void swingItem()
/*     */   {
/* 294 */     super.swingItem();
/* 295 */     this.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C0APacketAnimation());
/*     */   }
/*     */   
/*     */   public void respawnPlayer()
/*     */   {
/* 300 */     this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_)
/*     */   {
/* 309 */     if (!func_180431_b(p_70665_1_))
/*     */     {
/* 311 */       setHealth(getHealth() - p_70665_2_);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeScreen()
/*     */   {
/* 320 */     this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
/* 321 */     func_175159_q();
/*     */   }
/*     */   
/*     */   public void func_175159_q()
/*     */   {
/* 326 */     this.inventory.setItemStack(null);
/* 327 */     super.closeScreen();
/* 328 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlayerSPHealth(float p_71150_1_)
/*     */   {
/* 336 */     if (this.field_175169_bQ)
/*     */     {
/* 338 */       float var2 = getHealth() - p_71150_1_;
/*     */       
/* 340 */       if (var2 <= 0.0F)
/*     */       {
/* 342 */         setHealth(p_71150_1_);
/*     */         
/* 344 */         if (var2 < 0.0F)
/*     */         {
/* 346 */           this.hurtResistantTime = (this.maxHurtResistantTime / 2);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 351 */         this.lastDamage = var2;
/* 352 */         setHealth(getHealth());
/* 353 */         this.hurtResistantTime = this.maxHurtResistantTime;
/* 354 */         damageEntity(DamageSource.generic, var2);
/* 355 */         this.hurtTime = (this.maxHurtTime = 10);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 360 */       setHealth(p_71150_1_);
/* 361 */       this.field_175169_bQ = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addStat(StatBase p_71064_1_, int p_71064_2_)
/*     */   {
/* 370 */     if (p_71064_1_ != null)
/*     */     {
/* 372 */       if (p_71064_1_.isIndependent)
/*     */       {
/* 374 */         super.addStat(p_71064_1_, p_71064_2_);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendPlayerAbilities()
/*     */   {
/* 384 */     this.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C13PacketPlayerAbilities(this.capabilities));
/*     */   }
/*     */   
/*     */   public boolean func_175144_cb()
/*     */   {
/* 389 */     return true;
/*     */   }
/*     */   
/*     */   protected void sendHorseJump()
/*     */   {
/* 394 */     this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
/*     */   }
/*     */   
/*     */   public void func_175163_u()
/*     */   {
/* 399 */     this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/*     */   }
/*     */   
/*     */   public void func_175158_f(String p_175158_1_)
/*     */   {
/* 404 */     this.clientBrand = p_175158_1_;
/*     */   }
/*     */   
/*     */   public String getClientBrand()
/*     */   {
/* 409 */     return this.clientBrand;
/*     */   }
/*     */   
/*     */   public StatFileWriter getStatFileWriter()
/*     */   {
/* 414 */     return this.field_146108_bO;
/*     */   }
/*     */   
/*     */   public void addChatComponentMessage(IChatComponent p_146105_1_)
/*     */   {
/* 419 */     this.mc.ingameGUI.getChatGUI().printChatMessage(p_146105_1_);
/*     */   }
/*     */   
/*     */   protected boolean pushOutOfBlocks(double x, double y, double z)
/*     */   {//TODO
/* 424 */    
/* 427 */     EventPushOutOfBlocks push = new EventPushOutOfBlocks();
/* 428 */     TeamBattleClient.getEventManager().call(push);
/*     */     
/* 430 */     if (this.noClip)
/*     */     {
/* 432 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 436 */     BlockPos var7 = new BlockPos(x, y, z);
/* 437 */     double var8 = x - var7.getX();
/* 438 */     double var10 = z - var7.getZ();
/*     */     
/* 440 */     if (!func_175162_d(var7))
/*     */     {
/* 442 */       byte var12 = -1;
/* 443 */       double var13 = 9999.0D;
/*     */       
/* 445 */       if ((func_175162_d(var7.offsetWest())) && (var8 < var13))
/*     */       {
/* 447 */         var13 = var8;
/* 448 */         var12 = 0;
/*     */       }
/*     */       
/* 451 */       if ((func_175162_d(var7.offsetEast())) && (1.0D - var8 < var13))
/*     */       {
/* 453 */         var13 = 1.0D - var8;
/* 454 */         var12 = 1;
/*     */       }
/*     */       
/* 457 */       if ((func_175162_d(var7.offsetNorth())) && (var10 < var13))
/*     */       {
/* 459 */         var13 = var10;
/* 460 */         var12 = 4;
/*     */       }
/*     */       
/* 463 */       if ((func_175162_d(var7.offsetSouth())) && (1.0D - var10 < var13))
/*     */       {
/* 465 */         var13 = 1.0D - var10;
/* 466 */         var12 = 5;
/*     */       }
/*     */       
/* 469 */       float var15 = 0.1F;
/*     */       
/* 471 */       if (var12 == 0)
/*     */       {
/* 473 */         this.motionX = (-var15);
/*     */       }
/*     */       
/* 476 */       if (var12 == 1)
/*     */       {
/* 478 */         this.motionX = var15;
/*     */       }
/*     */       
/* 481 */       if (var12 == 4)
/*     */       {
/* 483 */         this.motionZ = (-var15);
/*     */       }
/*     */       
/* 486 */       if (var12 == 5)
/*     */       {
/* 488 */         this.motionZ = var15;
/*     */       }
/*     */     }
/*     */     
/* 492 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean func_175162_d(BlockPos p_175162_1_)
/*     */   {
/* 498 */     return (!this.worldObj.getBlockState(p_175162_1_).getBlock().isNormalCube()) && (!this.worldObj.getBlockState(p_175162_1_.offsetUp()).getBlock().isNormalCube());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSprinting(boolean sprinting)
/*     */   {
/* 506 */     super.setSprinting(sprinting);
/* 507 */     this.sprintingTicksLeft = (sprinting ? 600 : 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXPStats(float p_71152_1_, int p_71152_2_, int p_71152_3_)
/*     */   {
/* 515 */     this.experience = p_71152_1_;
/* 516 */     this.experienceTotal = p_71152_2_;
/* 517 */     this.experienceLevel = p_71152_3_;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addChatMessage(IChatComponent message)
/*     */   {
/* 528 */     this.mc.ingameGUI.getChatGUI().printChatMessage(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCommandSenderUseCommand(int permissionLevel, String command)
/*     */   {
/* 536 */     return permissionLevel <= 0;
/*     */   }
/*     */   
/*     */   public BlockPos getPosition()
/*     */   {
/* 541 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*     */   }
/*     */   
/*     */   public void playSound(String name, float volume, float pitch)
/*     */   {
/* 546 */     this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isServerWorld()
/*     */   {
/* 554 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isRidingHorse()
/*     */   {
/* 559 */     return (this.ridingEntity != null) && ((this.ridingEntity instanceof EntityHorse)) && (((EntityHorse)this.ridingEntity).isHorseSaddled());
/*     */   }
/*     */   
/*     */   public float getHorseJumpPower()
/*     */   {
/* 564 */     return this.horseJumpPower;
/*     */   }
/*     */   
/*     */   public void func_175141_a(TileEntitySign p_175141_1_)
/*     */   {
/* 569 */     this.mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiEditSign(p_175141_1_));
/*     */   }
/*     */   
/*     */   public void func_146095_a(CommandBlockLogic p_146095_1_)
/*     */   {
/* 574 */     this.mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void displayGUIBook(ItemStack bookStack)
/*     */   {
/* 582 */     Item var2 = bookStack.getItem();
/*     */     
/* 584 */     if (var2 == Items.writable_book)
/*     */     {
/* 586 */       this.mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void displayGUIChest(IInventory chestInventory)
/*     */   {
/* 595 */     String var2 = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
/*     */     
/* 597 */     if ("minecraft:chest".equals(var2))
/*     */     {
/* 599 */       this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
/*     */     }
/* 601 */     else if ("minecraft:hopper".equals(var2))
/*     */     {
/* 603 */       this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
/*     */     }
/* 605 */     else if ("minecraft:furnace".equals(var2))
/*     */     {
/* 607 */       this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
/*     */     }
/* 609 */     else if ("minecraft:brewing_stand".equals(var2))
/*     */     {
/* 611 */       this.mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiBrewingStand(this.inventory, chestInventory));
/*     */     }
/* 613 */     else if ("minecraft:beacon".equals(var2))
/*     */     {
/* 615 */       this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
/*     */     }
/* 617 */     else if ((!"minecraft:dispenser".equals(var2)) && (!"minecraft:dropper".equals(var2)))
/*     */     {
/* 619 */       this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
/*     */     }
/*     */     else
/*     */     {
/* 623 */       this.mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiDispenser(this.inventory, chestInventory));
/*     */     }
/*     */   }
/*     */   
/*     */   public void displayGUIHorse(EntityHorse p_110298_1_, IInventory p_110298_2_)
/*     */   {
/* 629 */     this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, p_110298_2_, p_110298_1_));
/*     */   }
/*     */   
/*     */   public void displayGui(IInteractionObject guiOwner)
/*     */   {
/* 634 */     String var2 = guiOwner.getGuiID();
/*     */     
/* 636 */     if ("minecraft:crafting_table".equals(var2))
/*     */     {
/* 638 */       this.mc.displayGuiScreen(new net.minecraft.client.gui.inventory.GuiCrafting(this.inventory, this.worldObj));
/*     */     }
/* 640 */     else if ("minecraft:enchanting_table".equals(var2))
/*     */     {
/* 642 */       this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, guiOwner));
/*     */     }
/* 644 */     else if ("minecraft:anvil".equals(var2))
/*     */     {
/* 646 */       this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
/*     */     }
/*     */   }
/*     */   
/*     */   public void displayVillagerTradeGui(IMerchant villager)
/*     */   {
/* 652 */     this.mc.displayGuiScreen(new net.minecraft.client.gui.GuiMerchant(this.inventory, villager, this.worldObj));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCriticalHit(Entity p_71009_1_)
/*     */   {
/* 660 */     this.mc.effectRenderer.func_178926_a(p_71009_1_, EnumParticleTypes.CRIT);
/*     */   }
/*     */   
/*     */   public void onEnchantmentCritical(Entity p_71047_1_)
/*     */   {
/* 665 */     this.mc.effectRenderer.func_178926_a(p_71047_1_, EnumParticleTypes.CRIT_MAGIC);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSneaking()
/*     */   {
/* 673 */     boolean var1 = this.movementInput != null ? this.movementInput.sneak : false;
/* 674 */     return (var1) && (!this.sleeping);
/*     */   }
/*     */   //TODO
/*     */   public void moveEntity(double motionX, double motionY, double motionZ)
/*     */   {
/* 679 */     EventPlayerMovement event = new EventPlayerMovement(motionX, 
/* 680 */       motionY, motionZ);
/* 681 */     TeamBattleClient.getEventManager().call(event);
/* 682 */     motionX = event.getMotionX();
/* 683 */     motionY = event.getMotionY();
/* 684 */     motionZ = event.getMotionZ();
/* 685 */     super.moveEntity(motionX, motionY, motionZ);
/*     */   }
/*     */   
/*     */   public void updateEntityActionState()
/*     */   {
/* 690 */     super.updateEntityActionState();
/*     */     
/* 692 */     if (func_175160_A())
/*     */     {
/* 694 */       this.moveStrafing = this.movementInput.moveStrafe;
/* 695 */       this.moveForward = this.movementInput.moveForward;
/* 696 */       this.isJumping = this.movementInput.jump;
/* 697 */       this.prevRenderArmYaw = this.renderArmYaw;
/* 698 */       this.prevRenderArmPitch = this.renderArmPitch;
/* 699 */       this.renderArmPitch = ((float)(this.renderArmPitch + (this.rotationPitch - this.renderArmPitch) * 0.5D));
/* 700 */       this.renderArmYaw = ((float)(this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5D));
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean func_175160_A()
/*     */   {
/* 706 */     return this.mc.func_175606_aa() == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 715 */     if (this.sprintingTicksLeft > 0)
/*     */     {
/* 717 */       this.sprintingTicksLeft -= 1;
/*     */       
/* 719 */       if (this.sprintingTicksLeft == 0)
/*     */       {
/* 721 */         setSprinting(false);
/*     */       }
/*     */     }
/*     */     
/* 725 */     if (this.sprintToggleTimer > 0)
/*     */     {
/* 727 */       this.sprintToggleTimer -= 1;
/*     */     }
/*     */     
/* 730 */     this.prevTimeInPortal = this.timeInPortal;
/*     */     
/* 732 */     if (this.inPortal)
/*     */     {
/* 734 */       if ((this.mc.currentScreen != null) && (!this.mc.currentScreen.doesGuiPauseGame()))
/*     */       {
/* 736 */         this.mc.displayGuiScreen(null);
/*     */       }
/*     */       
/* 739 */       if (this.timeInPortal == 0.0F)
/*     */       {
/* 741 */         this.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new net.minecraft.util.ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       
/* 744 */       this.timeInPortal += 0.0125F;
/*     */       
/* 746 */       if (this.timeInPortal >= 1.0F)
/*     */       {
/* 748 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */       
/* 751 */       this.inPortal = false;
/*     */     }
/* 753 */     else if ((isPotionActive(Potion.confusion)) && (getActivePotionEffect(Potion.confusion).getDuration() > 60))
/*     */     {
/* 755 */       this.timeInPortal += 0.006666667F;
/*     */       
/* 757 */       if (this.timeInPortal > 1.0F)
/*     */       {
/* 759 */         this.timeInPortal = 1.0F;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 764 */       if (this.timeInPortal > 0.0F)
/*     */       {
/* 766 */         this.timeInPortal -= 0.05F;
/*     */       }
/*     */       
/* 769 */       if (this.timeInPortal < 0.0F)
/*     */       {
/* 771 */         this.timeInPortal = 0.0F;
/*     */       }
/*     */     }
/*     */     
/* 775 */     if (this.timeUntilPortal > 0)
/*     */     {
/* 777 */       this.timeUntilPortal -= 1;
/*     */     }
/*     */     
/* 780 */     boolean var1 = this.movementInput.jump;
/* 781 */     boolean var2 = this.movementInput.sneak;
/* 782 */     float var3 = 0.8F;
/* 783 */     boolean var4 = this.movementInput.moveForward >= var3;
/* 784 */     this.movementInput.updatePlayerMoveState();
/*     */     
/*     */ 
/* 787 */     pushOutOfBlocks(this.posX - this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ + this.width * 0.35D);
/* 788 */     pushOutOfBlocks(this.posX - this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ - this.width * 0.35D);
/* 789 */     pushOutOfBlocks(this.posX + this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ - this.width * 0.35D);
/* 790 */     pushOutOfBlocks(this.posX + this.width * 0.35D, getEntityBoundingBox().minY + 0.5D, this.posZ + this.width * 0.35D);
/* 791 */     boolean var5 = (getFoodStats().getFoodLevel() > 6.0F) || (this.capabilities.allowFlying);
/*     */     
/* 793 */     if ((this.onGround) && (!var2) && (!var4) && (this.movementInput.moveForward >= var3) && (!isSprinting()) && (var5) && (!isUsingItem()) && (!isPotionActive(Potion.blindness)))
/*     */     {
/* 795 */       if ((this.sprintToggleTimer <= 0) && (!this.mc.gameSettings.keyBindSprint.getIsKeyPressed()))
/*     */       {
/* 797 */         this.sprintToggleTimer = 7;
/*     */       }
/*     */       else
/*     */       {
/* 801 */         setSprinting(true);
/*     */       }
/*     */     }
/*     */     
/* 805 */     if ((!isSprinting()) && (this.movementInput.moveForward >= var3) && (var5) && (!isUsingItem()) && (!isPotionActive(Potion.blindness)) && (this.mc.gameSettings.keyBindSprint.getIsKeyPressed()))
/*     */     {
/* 807 */       setSprinting(true);
/*     */     }
/*     */     
/* 810 */     if ((isSprinting()) && ((this.movementInput.moveForward < var3) || (this.isCollidedHorizontally) || (!var5)))
/*     */     {
/* 812 */       setSprinting(false);
/*     */     }
/*     */     
/* 815 */     if (this.capabilities.allowFlying)
/*     */     {
/* 817 */       if (this.mc.playerController.isSpectatorMode())
/*     */       {
/* 819 */         if (!this.capabilities.isFlying)
/*     */         {
/* 821 */           this.capabilities.isFlying = true;
/* 822 */           sendPlayerAbilities();
/*     */         }
/*     */       }
/* 825 */       else if ((!var1) && (this.movementInput.jump))
/*     */       {
/* 827 */         if (this.flyToggleTimer == 0)
/*     */         {
/* 829 */           this.flyToggleTimer = 7;
/*     */         }
/*     */         else
/*     */         {
/* 833 */           this.capabilities.isFlying = (!this.capabilities.isFlying);
/* 834 */           sendPlayerAbilities();
/* 835 */           this.flyToggleTimer = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 840 */     if ((this.capabilities.isFlying) && (func_175160_A()))
/*     */     {
/* 842 */       if (this.movementInput.sneak)
/*     */       {
/* 844 */         this.motionY -= this.capabilities.getFlySpeed() * 3.0F;
/*     */       }
/*     */       
/* 847 */       if (this.movementInput.jump)
/*     */       {
/* 849 */         this.motionY += this.capabilities.getFlySpeed() * 3.0F;
/*     */       }
/*     */     }
/*     */     
/* 853 */     if (isRidingHorse())
/*     */     {
/* 855 */       if (this.horseJumpPowerCounter < 0)
/*     */       {
/* 857 */         this.horseJumpPowerCounter += 1;
/*     */         
/* 859 */         if (this.horseJumpPowerCounter == 0)
/*     */         {
/* 861 */           this.horseJumpPower = 0.0F;
/*     */         }
/*     */       }
/*     */       
/* 865 */       if ((var1) && (!this.movementInput.jump))
/*     */       {
/* 867 */         this.horseJumpPowerCounter = -10;
/* 868 */         sendHorseJump();
/*     */       }
/* 870 */       else if ((!var1) && (this.movementInput.jump))
/*     */       {
/* 872 */         this.horseJumpPowerCounter = 0;
/* 873 */         this.horseJumpPower = 0.0F;
/*     */       }
/* 875 */       else if (var1)
/*     */       {
/* 877 */         this.horseJumpPowerCounter += 1;
/*     */         
/* 879 */         if (this.horseJumpPowerCounter < 10)
/*     */         {
/* 881 */           this.horseJumpPower = (this.horseJumpPowerCounter * 0.1F);
/*     */         }
/*     */         else
/*     */         {
/* 885 */           this.horseJumpPower = (0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 891 */       this.horseJumpPower = 0.0F;
/*     */     }
/*     */     
/* 894 */     super.onLivingUpdate();
/*     */     
/* 896 */     if ((this.onGround) && (this.capabilities.isFlying) && (!this.mc.playerController.isSpectatorMode()))
/*     */     {
/* 898 */       this.capabilities.isFlying = false;
/* 899 */       sendPlayerAbilities();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Balen\Desktop\Java\TeamBattleClient.jar!\net\minecraft\client\entity\EntityPlayerSP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */