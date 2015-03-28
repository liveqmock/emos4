/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SplitDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private ConditionDescriptor conDesc;
/*  9 */   private List<SplitToDescriptor> toList = new ArrayList();
/*    */ 
/*    */   public SplitDescriptor(AbstractDescriptor parent) {
/* 12 */     setParent(parent);
/*    */   }
/*    */ 
/*    */   public SplitToDescriptor toDesc(String stepCode) {
/* 16 */     SplitToDescriptor rtn = null;
/* 17 */     if ((this.toList != null) && (this.toList.size() > 0) && (stepCode != null) && (!stepCode.trim().equals(""))) {
/* 18 */       for (int i = 0; i < this.toList.size(); i++) {
/* 19 */         SplitToDescriptor spToDesc = (SplitToDescriptor)this.toList.get(i);
/* 20 */         String stepId = spToDesc.getStepId();
/* 21 */         String joinId = spToDesc.getJoinId();
/* 22 */         String splitId = spToDesc.getSplitId();
/* 23 */         String endId = spToDesc.getEndId();
/* 24 */         if ((stepCode.equals(stepId)) || (stepCode.equals(joinId)) || (stepCode.equals(splitId)) || (stepCode.equals(endId))) {
/* 25 */           rtn = spToDesc;
/* 26 */           break;
/*    */         }
/*    */       }
/*    */     }
/* 30 */     return rtn;
/*    */   }
/*    */ 
/*    */   public ConditionDescriptor getConDesc() {
/* 34 */     return this.conDesc;
/*    */   }
/*    */ 
/*    */   public void setConDesc(ConditionDescriptor conDesc) {
/* 38 */     this.conDesc = conDesc;
/*    */   }
/*    */ 
/*    */   public List<SplitToDescriptor> getToList() {
/* 42 */     return this.toList;
/*    */   }
/*    */ 
/*    */   public void setToList(List<SplitToDescriptor> toList) {
/* 46 */     this.toList = toList;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.SplitDescriptor
 * JD-Core Version:    0.6.0
 */