/*    */ package com.ultrapower.workflow.utils;
/*    */ 
/*    */ import com.ultrapower.randomutil.Random15;
/*    */ import com.ultrapower.randomutil.RandomN;
/*    */ 
/*    */ public class UUIDGenerator
/*    */ {
/*    */   public static String getId()
/*    */   {
/*  9 */     RandomN random = new Random15();
/* 10 */     String id = random.getRandom(System.currentTimeMillis());
/* 11 */     return id;
/*    */   }
/*    */ }
