/*     */ package down.TeamBattle.Modules.Mods;
/*     */ import java.util.List;

/*     */ import org.lwjgl.input.Keyboard;

import down.TeamBattle.TeamBattleClient;
import down.TeamBattle.CommandSystem.Command;
import down.TeamBattle.EventSystem.Event;
import down.TeamBattle.EventSystem.events.EventChatSent;
import down.TeamBattle.EventSystem.events.EventKeyPressed;
import down.TeamBattle.Modfc.Macro;
import down.TeamBattle.Modules.ModuleBase;
import down.TeamBattle.Utils.Logger;
/*     */ 
/*     */ public final class Macros extends ModuleBase
/*     */ {
/*  18 */   private final List<Macro> macros = new java.util.concurrent.CopyOnWriteArrayList();
/*     */   
/*     */   public Macros() {
/*  21 */     super("Macros");
/*  22 */     setEnabled(true);
/*     */     
/*  24 */     TeamBattleClient.getCommandManager().getContents()
/*  25 */       .add(new Command("macroadd", "<key> <command>", new String[] { "madd", "ma" })
/*     */       {
/*     */         public void run(String message) {
/*  28 */           String[] arguments = message.split(" ");
/*  29 */           int key = Keyboard.getKeyIndex(arguments[1]
/*  30 */             .toUpperCase());
/*  31 */           if (key == 0) {
/*  32 */             Logger.logChat("You can't macro the key \"NONE\".");
/*  33 */             return;
/*     */           }
/*  35 */           String command = message.substring(
/*  36 */             (arguments[0] + " " + arguments[1] + " ").length());
/*  37 */           if (command.startsWith(".")) {
/*  38 */             command = command.substring(1);
/*     */           }
/*     */           
/*  41 */           for (Macro macro : Macros.this.macros) {
/*  42 */             if (macro.getKey() == key) {
/*  43 */               Macros.this.macros.remove(macro);
/*     */             }
/*     */           }
/*  46 */           Macros.this.macros.add(new Macro(command, key));
/*  47 */           TeamBattleClient.getFileManager().getFileByName("macros")
/*  48 */             .saveFile();
/*  49 */           Logger.logChat("Macro \"" + Keyboard.getKeyName(key) + 
/*  50 */             "\" added with command \"" + command + "\".");
/*     */         }
/*     */         
/*     */ 
/*  54 */       });TeamBattleClient.getCommandManager().getContents()
/*  55 */       .add(new Command("macrodel", "<key>", new String[] { "mdel", "md" })
/*     */       {
/*     */         public void run(String message) {
/*  58 */           int key = Keyboard.getKeyIndex(message.split(" ")[1]
/*  59 */             .toUpperCase());
/*  60 */           if (key == 0) {
/*  61 */             Logger.logChat("You can't macro the key \"NONE\".");
/*  62 */             return;
/*     */           }
/*  64 */           boolean found = false;
/*  65 */           for (Macro macro : Macros.this.macros) {
/*  66 */             if (key == macro.getKey()) {
/*  67 */               Macros.this.macros.remove(macro);
/*  68 */               Logger.logChat("Macro \"" + 
/*  69 */                 Keyboard.getKeyName(key) + 
/*  70 */                 "\" removed.");
/*  71 */               TeamBattleClient.getFileManager()
/*  72 */                 .getFileByName("macros").saveFile();
/*  73 */               found = true;
/*  74 */               break;
/*     */             }
/*     */           }
/*     */           
/*  78 */           if (!found) {
/*  79 */             Logger.logChat(
/*     */             
/*  81 */               "Macro \"" + Keyboard.getKeyName(key) + "\" not found.");
/*     */           }
/*     */         }
/*     */       });
/*     */   }
/*     */   
/*     */   public final List<Macro> getMacros() {
/*  88 */     return this.macros;
/*     */   }
/*     */   
/*     */   public void onEvent(Event event)
/*     */   {
/*  93 */     if ((event instanceof EventKeyPressed)) {
/*  94 */       EventKeyPressed pressed = (EventKeyPressed)event;
/*  95 */       for (Macro macro : this.macros) {
/*  96 */         if (pressed.getKey() == macro.getKey()) {
/*  97 */           EventChatSent sent = new EventChatSent("." + 
/*  98 */             macro.getCommand());
/*  99 */           TeamBattleClient.getEventManager().call(sent);
/* 100 */           sent.checkForCommands();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


