/*    */ package down.TeamBattle.Modfc;
/*    */ 
/*    */ public final class Point { protected String name;
/*    */   protected final String server;
/*    */   protected final int x;
/*    */   protected final int y;
/*    */   protected final int z;
/*    */   
/*  9 */   public Point(String name, String server, int x, int y, int z) { this.name = name;
/* 10 */     this.server = server;
/* 11 */     this.x = x;
/* 12 */     this.y = y;
/* 13 */     this.z = z;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 17 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getServer() {
/* 21 */     return this.server;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 25 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 29 */     return this.y;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 33 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 37 */     this.name = name;
/*    */   }
/*    */ }


