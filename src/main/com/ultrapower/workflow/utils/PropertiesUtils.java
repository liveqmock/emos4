/*    */ package com.ultrapower.workflow.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class PropertiesUtils
/*    */ {
/*  8 */   private static Properties prop = new Properties();
/*    */ 
/*    */   static {
/* 11 */     init();
/*    */   }
/*    */ 
/*    */   public static String getProp(String key)
/*    */   {
/* 16 */     return prop.getProperty(key);
/*    */   }
/*    */ 
/*    */   public static void init() {
/*    */     try {
/* 21 */       prop.load(PropertiesUtils.class.getResourceAsStream("/wfengine/configs/config.properties"));
/*    */     } catch (IOException e) {
/* 23 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.PropertiesUtils
 * JD-Core Version:    0.6.0
 */