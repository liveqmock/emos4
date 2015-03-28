/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ public class SplitToDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private String stepId;
/*    */   private String splitId;
/*    */   private String joinId;
/*    */   private String endId;
/*    */   private int remap;
/*    */   private String assignTree;
/*    */ 
/*    */   public SplitToDescriptor(AbstractDescriptor parent)
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
/*    */   public String getSplitId() {
/* 24 */     return this.splitId;
/*    */   }
/*    */ 
/*    */   public void setSplitId(String splitId) {
/* 28 */     this.splitId = splitId;
/*    */   }
/*    */ 
/*    */   public String getJoinId() {
/* 32 */     return this.joinId;
/*    */   }
/*    */ 
/*    */   public void setJoinId(String joinId) {
/* 36 */     this.joinId = joinId;
/*    */   }
/*    */ 
/*    */   public String getEndId() {
/* 40 */     return this.endId;
/*    */   }
/*    */ 
/*    */   public void setEndId(String endId) {
/* 44 */     this.endId = endId;
/*    */   }
/*    */ 
/*    */   public String getAssignTree() {
/* 48 */     return this.assignTree;
/*    */   }
/*    */ 
/*    */   public void setAssignTree(String assignTree) {
/* 52 */     this.assignTree = assignTree;
/*    */   }
/*    */ 
/*    */   public int getRemap() {
/* 56 */     return this.remap;
/*    */   }
/*    */ 
/*    */   public void setRemap(int remap) {
/* 60 */     this.remap = remap;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.SplitToDescriptor
 * JD-Core Version:    0.6.0
 */