/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ public class StartDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private String to;
/*    */ 
/*    */   public StartDescriptor(AbstractDescriptor parent)
/*    */   {
/*  7 */     setParent(parent);
/*    */   }
/*    */ 
/*    */   public String getTo() {
/* 11 */     return this.to;
/*    */   }
/*    */ 
/*    */   public void setTo(String to) {
/* 15 */     this.to = to;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.StartDescriptor
 * JD-Core Version:    0.6.0
 */