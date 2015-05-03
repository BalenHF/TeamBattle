/*     */ package down.TeamBattle.Utils;
/*     */ 
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ 
/*     */ public class Render3DUtil
/*     */ {
/*     */   public static void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
/*     */   {
/*  10 */     net.minecraft.client.renderer.Tessellator var1 = net.minecraft.client.renderer.Tessellator.getInstance();
/*  11 */     WorldRenderer var2 = var1.getWorldRenderer();
/*  12 */     var2.startDrawing(3);
/*  13 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/*  14 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/*  15 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/*  16 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/*  17 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/*  18 */     var1.draw();
/*  19 */     var2.startDrawing(3);
/*  20 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/*  21 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/*  22 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/*  23 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/*  24 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/*  25 */     var1.draw();
/*  26 */     var2.startDrawing(1);
/*  27 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/*  28 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/*  29 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/*  30 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/*  31 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/*  32 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/*  33 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/*  34 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/*  35 */     var1.draw();
/*     */   }
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
/*  39 */     net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
/*  40 */     WorldRenderer worldrender = net.minecraft.client.renderer.Tessellator.getInstance().getWorldRenderer();
/*     */     
/*  42 */     worldrender.startDrawingQuads();
/*  43 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
/*  44 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  45 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
/*  46 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  47 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  48 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  49 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  50 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  51 */     tessellator.draw();
/*  52 */     worldrender.startDrawingQuads();
/*  53 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  54 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
/*  55 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  56 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
/*  57 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  58 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  59 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  60 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  61 */     tessellator.draw();
/*  62 */     worldrender.startDrawingQuads();
/*  63 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  64 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  65 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  66 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  67 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  68 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  69 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  70 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  71 */     tessellator.draw();
/*  72 */     worldrender.startDrawingQuads();
/*  73 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
/*  74 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
/*  75 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  76 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  77 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
/*  78 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  79 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  80 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
/*  81 */     tessellator.draw();
/*  82 */     worldrender.startDrawingQuads();
/*  83 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
/*  84 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  85 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  86 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  87 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  88 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  89 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
/*  90 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  91 */     tessellator.draw();
/*  92 */     worldrender.startDrawingQuads();
/*  93 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
/*  94 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  95 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  96 */     worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
/*  97 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
/*  98 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
/*  99 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
/* 100 */     worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/* 101 */     tessellator.draw();
/*     */   }
/*     */ }


