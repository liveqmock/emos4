/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ public class JoinDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private String stepId;
/*    */   private String splitId;
/*    */   private String endId;
/*    */   private ConditionDescriptor conDesc;
/*    */ 
/*    */   public JoinDescriptor(AbstractDescriptor parent)
/*    */   {
/* 12 */     setParent(parent);
/*    */   }
/*    */ 
/*    */   public String getStepId() {
/* 16 */     return this.stepId;
/*    */   }
/*    */ 
/*    */   public void setStepId(String stepId) {
/* 20 */     this.stepId = stepId;
/*    */   }
/*    */ 
/*    */   public ConditionDescriptor getConDesc() {
/* 24 */     return this.conDesc;
/*    */   }
/*    */ 
/*    */   public void setConDesc(ConditionDescriptor conDesc) {
/* 28 */     this.conDesc = conDesc;
/*    */   }
/*    */ 
/*    */   public String getSplitId() {
/* 32 */     return this.splitId;
/*    */   }
/*    */ 
/*    */   public void setSplitId(String splitId) {
/* 36 */     this.splitId = splitId;
/*    */   }
/*    */ 
/*    */   public String getEndId() {
/* 40 */     return this.endId;
/*    */   }
/*    */ 
/*    */   public void setEndId(String endId) {
/* 44 */     this.endId = endId;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.JoinDescriptor
 * JD-Core Version:    0.6.0
 */