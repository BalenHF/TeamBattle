/*     */ package down.TeamBattle.Modules.Mods;
/*     */ import java.util.List;

/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.util.AxisAlignedBB;

/*     */ import org.lwjgl.opengl.GL11;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventRender3D;
import down.TeamBattle.Modfc.Point;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;
import down.TeamBattle.Utils.Render3DUtil;
import down.TeamBattle.Utils.RenderHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Waypoints extends ModuleBase
/*     */ {
/*     */   private float[] pointColor;
/*  26 */   public List<Point> points = new java.util.concurrent.CopyOnWriteArrayList();
/*     */   
/*     */   public Waypoints() {
/*  29 */     super("Waypoints");
/*  30 */     setEnabled(true);
/*     */     
/*  32 */     TeamBattleClient.getCommandManager()
/*  33 */       .getContents()
/*  34 */       .add(new Command("waypointadd", "<x> <y> <z> <name>", new String[] {
/*  35 */       "waypadd", "wpa" })
/*     */       {
/*     */         private boolean isInteger(String text) {
/*     */           try {
/*  38 */             Integer.parseInt(text);
/*     */           } catch (Exception e) {
/*  40 */             return false;
/*     */           }
/*  42 */           return true;
/*     */         }
/*     */         
/*     */         public void run(String message)
/*     */         {
/*  47 */           String[] arguments = message.split(" ");
/*  48 */           if (isInteger(arguments[1])) {
/*  49 */             int x = Integer.parseInt(arguments[1]);
/*  50 */             int y = Integer.parseInt(arguments[2]);
/*  51 */             int z = Integer.parseInt(arguments[3]);
/*  52 */             String name = message.substring(
/*     */             
/*  54 */               (arguments[0] + " " + arguments[1] + " " + arguments[2] + " " + arguments[3] + " ").length());
/*  56 */             String server; if (Waypoints.mc.getCurrentServerData() == null) {
/*  57 */               server = "singleplayer";
/*     */             } else {
/*  59 */               server = Waypoints.mc.getCurrentServerData().serverIP;
/*     */             }
/*  61 */             Waypoints.this.points.add(new Point(name, server, x, y, z));
/*  62 */             TeamBattleClient.getFileManager().getFileByName("waypoints")
/*  63 */               .saveFile();
/*  64 */             Logger.logChat("Waypoint \"" + name + 
/*  65 */               "\" added at " + x + ", " + y + ", " + z);
/*     */           }
/*     */           
/*     */         }
/*     */         
/*  70 */       });TeamBattleClient.getCommandManager().getContents()
/*  71 */       .add(new Command("waypointdel", "<name>", new String[] { "waypdel", "wpd" })
/*     */       {
/*     */         public void run(String message) {
/*  74 */           String[] arguments = message.split(" ");
/*  75 */           String name = message
/*  76 */             .substring((arguments[0] + " ").length());
/*  77 */           boolean found = false;
/*  78 */           for (Point point : Waypoints.this.points)
/*     */           {
/*  80 */             if (point.getName().toLowerCase().startsWith(name.toLowerCase())) {
/*  81 */               Waypoints.this.points.remove(point);
/*  82 */               TeamBattleClient.getFileManager()
/*  83 */                 .getFileByName("waypoints").saveFile();
/*  84 */               Logger.logChat("Waypoint \"" + point.getName() + 
/*  85 */                 "\" deleted.");
/*  86 */               found = true;
/*     */             }
/*     */           }
/*     */           
/*  90 */           if (!found) {
/*  91 */             Logger.logChat(
/*  92 */               "Waypoint \"" + name + "\" not found.");
/*     */           }
/*     */           
/*     */         }
/*  96 */       });
/*  97 */     this.pointColor = new float[] { 1.28F, 1.28F, 1.92F };
/*     */   }
/*     */   
/*     */   public final List<Point> getPoints() {
/* 101 */     return this.points;
/*     */   }
/*     */   
/*     */   public void onEvent(Event event)
/*     */   {
/* 106 */     if ((event instanceof EventRender3D)) {
/* 107 */       GL11.glPushMatrix();
/* 108 */       GL11.glDisable(3553);
/* 109 */       GL11.glDisable(2896);
/* 110 */       GL11.glEnable(3042);
/* 111 */       GL11.glBlendFunc(770, 771);
/* 112 */       GL11.glDisable(2929);
/* 113 */       GL11.glEnable(2848);
/* 114 */       GL11.glDepthMask(false);
/* 115 */       GL11.glLineWidth(1.0F);
/* 116 */       for (Point point : this.points) {
/*     */         String server;
/* 118 */         if (mc.getCurrentServerData() == null) {
/* 119 */           server = "singleplayer";
/*     */         } else {
/* 121 */           server = mc.getCurrentServerData().serverIP;
/*     */         }
/*     */         
/* 124 */         if (point.getServer().equalsIgnoreCase(server)) {
/* 125 */           renderPoint(point);
/*     */         }
/*     */       }
/* 128 */       GL11.glDepthMask(true);
/* 129 */       GL11.glDisable(2848);
/* 130 */       GL11.glEnable(2929);
/* 131 */       GL11.glEnable(2896);
/* 132 */       GL11.glDisable(3042);
/* 133 */       GL11.glEnable(3553);
/* 134 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderPoint(Point point) {
/* 139 */     double x = point.getX() - RenderManager.renderPosX;
/* 140 */     double y = point.getY() - RenderManager.renderPosY;
/* 141 */     double z = point.getZ() - RenderManager.renderPosZ;
/*     */     
/*     */ 
/* 144 */     AxisAlignedBB box = AxisAlignedBB.fromBounds(x, y, z, 
/* 145 */       x + 1.0D, y + 1.0D, z + 1.0D);
/* 146 */     GL11.glColor4f(this.pointColor[0], this.pointColor[1], this.pointColor[2], 0.4F);
/* 147 */    Render3DUtil.drawBoundingBox(box);
/* 148 */     GL11.glColor4f(this.pointColor[0], this.pointColor[1], this.pointColor[2], 0.6F);
/* 149 */     RenderHelper.drawLines(box);
/* 150 */     GL11.glColor4f(this.pointColor[0], this.pointColor[1], this.pointColor[2], 0.8F);
/* 151 */     RenderHelper.drawOutlinedBoundingBox(box);
/*     */     
/*     */ 
/* 154 */     GL11.glColor4f(this.pointColor[0], this.pointColor[1], this.pointColor[2], 1.0F);
/* 155 */     GL11.glBegin(2);
/* 156 */     GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
/* 157 */     GL11.glVertex3d(x + 0.5D, y, z + 0.5D);
/* 158 */     GL11.glEnd();
/*     */     
/*     */ 
/* 161 */     FontRenderer var12 = mc.fontRendererObj;
/* 162 */     double dist = mc.thePlayer.getDistance(point.getX(), 
/* 163 */       point.getY(), point.getZ()) / 3.0D;
/* 164 */     float var13 = (float)(dist <= 3.0D ? 3.0D : dist);
/* 165 */     float var14 = 0.016666668F * var13;
/* 166 */     GL11.glPushMatrix();
/* 167 */     GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, 
/* 168 */       (float)z + 0.5F);
/* 169 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 170 */     GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 171 */     GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 172 */     GL11.glScalef(-var14, -var14, var14);
/* 173 */     net.minecraft.client.renderer.OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 174 */     Tessellator var15 = Tessellator.instance;
/* 175 */     byte var16 = 0;
/* 176 */     WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
/* 177 */     worldRenderer.startDrawingQuads();
/* 178 */     String text = point.getName() + 
/* 179 */       " " + 
/* 180 */       (int)Math.round(mc.thePlayer.getDistance(point.getX(), 
/* 181 */       point.getY(), point.getZ())) + "m";
/* 182 */     int var17 = var12.getStringWidth(text) / 2;
/* 183 */     GL11.glColor4f(1.28F, 1.28F, 1.92F, 0.25F);
/* 184 */     worldRenderer.addVertex(-var17 - 2, -2.0D, 0.0D);
/* 185 */     worldRenderer.addVertex(-var17 - 2, 9.0D, 0.0D);
/* 186 */     worldRenderer.addVertex(var17 + 2, 9.0D, 0.0D);
/* 187 */     worldRenderer.addVertex(var17 + 2, -2.0D, 0.0D);
/* 188 */     var15.draw();
/* 189 */     GL11.glEnable(3553);
/* 190 */     var12.func_175063_a(text, -var17, 0.0F, -1);
/* 191 */     GL11.glDisable(3553);
/* 192 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


