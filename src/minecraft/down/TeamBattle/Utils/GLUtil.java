/*    */ package down.TeamBattle.Utils;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GLUtil
/*    */ {
/*  9 */   private static Map<Integer, Boolean> glCapMap = new java.util.HashMap();
/*    */   
/* 11 */   public static void setGLCap(int cap, boolean flag) { glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
/* 12 */     if (flag) {
/* 13 */       GL11.glEnable(cap);
/*    */     } else
/* 15 */       GL11.glDisable(cap);
/*    */   }
/*    */   
/*    */   public static void revertGLCap(int cap) {
/* 19 */     Boolean origCap = (Boolean)glCapMap.get(Integer.valueOf(cap));
/* 20 */     if (origCap != null) {
/* 21 */       if (origCap.booleanValue()) {
/* 22 */         GL11.glEnable(cap);
/*    */       } else
/* 24 */         GL11.glDisable(cap);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void glEnable(int cap) {
/* 29 */     setGLCap(cap, true);
/*    */   }
/*    */   
/* 32 */   public static void glDisable(int cap) { setGLCap(cap, false); }
/*    */   
/*    */   public static void revertAllCaps() {
/* 35 */     for (Iterator localIterator = glCapMap.keySet().iterator(); localIterator.hasNext();) { int cap = ((Integer)localIterator.next()).intValue();
/* 36 */       revertGLCap(cap);
/*    */     }
/*    */   }
/*    */ }


