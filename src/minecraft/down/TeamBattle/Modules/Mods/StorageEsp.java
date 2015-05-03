/*    */ package down.TeamBattle.Modules.Mods;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.tileentity.TileEntityDispenser;
/*    */ import net.minecraft.tileentity.TileEntityLockable;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;

/*    */ import org.lwjgl.opengl.GL11;

import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventRender3D;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.GLUtil;
import down.TeamBattle.Utils.Render3DUtil;
import down.TeamBattle.Utils.RenderHelper;
/*    */ 
/*    */ public class StorageEsp extends ModuleBase
/*    */ {
/*    */   public StorageEsp()
/*    */   {
/* 21 */     super("StorageESP", 10079487);
/* 22 */     setTag("Storage ESP");
/*    */   }
/*    */   
/*    */   public void drawESPOnStorage(TileEntityLockable storage, double x, double y, double z)
/*    */   {
/* 27 */     double r = 1.0D;
/* 28 */     double g = 1.0D;
/* 29 */     double b = 1.0D;
/* 30 */     if ((storage instanceof net.minecraft.tileentity.TileEntityChest))
/*    */     {
/* 32 */       r = 0.6784313725490196D;
/* 33 */       g = 0.47058823529411764D;
/* 34 */       b = 0.054901960784313725D;
/*    */     }
/* 36 */     if ((storage instanceof net.minecraft.tileentity.TileEntityFurnace))
/*    */     {
/* 38 */       r = 0.6588235294117647D;
/* 39 */       g = 0.2627450980392157D;
/* 40 */       b = 0.2627450980392157D;
/*    */     }
/* 42 */     if ((storage instanceof TileEntityDispenser))
/*    */     {
/* 44 */       r = 0.19607843137254902D;
/* 45 */       g = 0.6549019607843137D;
/* 46 */       b = 0.6784313725490196D;
/*    */     }
/* 48 */     if ((storage instanceof net.minecraft.tileentity.TileEntityHopper))
/*    */     {
/* 50 */       r = 0.5803921568627451D;
/* 51 */       g = 0.5803921568627451D;
/* 52 */       b = 0.5803921568627451D;
/*    */     }
/* 54 */     mc.getRenderManager();mc.getRenderManager();mc.getRenderManager();drawESP(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ, r, g, b);
/*    */   }
/*    */   
/*    */   public void drawESP(double x, double y, double z, double r, double g, double b)
/*    */   {
/* 59 */     GL11.glPushMatrix();
/* 60 */     GLUtil.glEnable(3042);
/* 61 */     GL11.glBlendFunc(770, 771);
/* 62 */     GL11.glLineWidth(1.5F);
/* 63 */     GLUtil.glDisable(2896);
/* 64 */     GLUtil.glDisable(3553);
/* 65 */     GLUtil.glEnable(2848);
/* 66 */     GLUtil.glDisable(2929);
/* 67 */     GL11.glDepthMask(false);
/* 68 */     GL11.glColor4d(r, g, b, 0.15000000596046448D);
/* 69 */     GL11.glColor4d(r, g, b, 0.20000000298023224D);
/* 70 */     Render3DUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
/* 71 */     GL11.glColor4d(r, g, b, 0.4000000059604645D);
/* 72 */     RenderHelper.drawLines(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
/* 73 */     GL11.glColor4d(r, g, b, 0.6000000238418579D);
/* 74 */     Render3DUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
/* 75 */     GL11.glLineWidth(2.0F);
/* 76 */     GLUtil.revertAllCaps();
/* 77 */     GL11.glDepthMask(true);
/* 78 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public void onEvent(Event event)
/*    */   {
/* 83 */     if ((event instanceof EventRender3D)) {
/* 84 */       for (Object o : mc.theWorld.loadedTileEntityList) {
/* 85 */         if ((o instanceof TileEntityLockable)) {
/* 86 */           TileEntityLockable storage = (TileEntityLockable)o;
/* 87 */           drawESPOnStorage(storage, storage.getPos().getX(), storage.getPos().getY(), storage.getPos().getZ());
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


