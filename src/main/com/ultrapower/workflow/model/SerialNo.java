/*    */ package com.ultrapower.workflow.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SerialNo
/*    */   implements Serializable
/*    */ {
/*    */   private String baseSchema;
/*  7 */   private String text = "ID-{KEY}-{DATE:yyyyMMdd}-{ID:5}";
/*  8 */   private int len = 5;
/*  9 */   private String dateReg = "yyyyMMdd";
/*    */ 
/*    */   public String getBaseSchema() {
/* 12 */     return this.baseSchema;
/*    */   }
/*    */ 
/*    */   public void setBaseSchema(String baseSchema) {
/* 16 */     this.baseSchema = baseSchema;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 20 */     return this.text;
/*    */   }
/*    */ 
/*    */   public void setText(String text) {
/* 24 */     this.text = text;
/*    */   }
/*    */ 
/*    */   public int getLen() {
/* 28 */     return this.len;
/*    */   }
/*    */ 
/*    */   public void setLen(int len) {
/* 32 */     this.len = len;
/*    */   }
/*    */ 
/*    */   public String getDateReg() {
/* 36 */     return this.dateReg;
/*    */   }
/*    */ 
/*    */   public void setDateReg(String dateReg) {
/* 40 */     this.dateReg = dateReg;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.model.SerialNo
 * JD-Core Version:    0.6.0
 */