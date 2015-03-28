/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WorkflowDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private StartDescriptor startDesc;
/*  8 */   private Map<String, StepDescriptor> stepDescMap = new HashMap();
/*  9 */   private Map<String, SplitDescriptor> splitDescMap = new HashMap();
/* 10 */   private Map<String, JoinDescriptor> joinDescMap = new HashMap();
/* 11 */   private Map<String, StatusDescriptor> statusDescMap = new HashMap();
/* 12 */   private Map<String, EndDescriptor> endDescMap = new HashMap();
/*    */ 
/*    */   public StartDescriptor getStartDesc() {
/* 15 */     return this.startDesc;
/*    */   }
/*    */ 
/*    */   public void setStartDesc(StartDescriptor startDesc) {
/* 19 */     this.startDesc = startDesc;
/*    */   }
/*    */ 
/*    */   public Map<String, StepDescriptor> getStepDescMap() {
/* 23 */     return this.stepDescMap;
/*    */   }
/*    */ 
/*    */   public void setStepDescMap(Map<String, StepDescriptor> stepDescMap) {
/* 27 */     this.stepDescMap = stepDescMap;
/*    */   }
/*    */ 
/*    */   public Map<String, SplitDescriptor> getSplitDescMap() {
/* 31 */     return this.splitDescMap;
/*    */   }
/*    */ 
/*    */   public void setSplitDescMap(Map<String, SplitDescriptor> splitDescMap) {
/* 35 */     this.splitDescMap = splitDescMap;
/*    */   }
/*    */ 
/*    */   public Map<String, JoinDescriptor> getJoinDescMap() {
/* 39 */     return this.joinDescMap;
/*    */   }
/*    */ 
/*    */   public void setJoinDescMap(Map<String, JoinDescriptor> joinDescMap) {
/* 43 */     this.joinDescMap = joinDescMap;
/*    */   }
/*    */ 
/*    */   public Map<String, StatusDescriptor> getStatusDescMap() {
/* 47 */     return this.statusDescMap;
/*    */   }
/*    */ 
/*    */   public void setStatusDescMap(Map<String, StatusDescriptor> statusDescMap) {
/* 51 */     this.statusDescMap = statusDescMap;
/*    */   }
/*    */ 
/*    */   public Map<String, EndDescriptor> getEndDescMap() {
/* 55 */     return this.endDescMap;
/*    */   }
/*    */ 
/*    */   public void setEndDescMap(Map<String, EndDescriptor> endDescMap) {
/* 59 */     this.endDescMap = endDescMap;
/*    */   }
/*    */ 
/*    */   public void putStepDescriptor(String stepId, StepDescriptor stDesc) {
/* 63 */     this.stepDescMap.put(stepId, stDesc);
/*    */   }
/*    */ 
/*    */   public StepDescriptor getStepDescriptor(String stepId) {
/* 67 */     return (StepDescriptor)this.stepDescMap.get(stepId);
/*    */   }
/*    */ 
/*    */   public void putSplitDescriptor(String splitId, SplitDescriptor spDesc) {
/* 71 */     this.splitDescMap.put(splitId, spDesc);
/*    */   }
/*    */ 
/*    */   public SplitDescriptor getSplitDescriptor(String splitId) {
/* 75 */     return (SplitDescriptor)this.splitDescMap.get(splitId);
/*    */   }
/*    */ 
/*    */   public void putJoinDescriptor(String joinId, JoinDescriptor jnDesc) {
/* 79 */     this.joinDescMap.put(joinId, jnDesc);
/*    */   }
/*    */ 
/*    */   public JoinDescriptor getJoinDescriptor(String joinId) {
/* 83 */     return (JoinDescriptor)this.joinDescMap.get(joinId);
/*    */   }
/*    */ 
/*    */   public void putEndDescriptor(String endId, EndDescriptor enDesc) {
/* 87 */     this.endDescMap.put(endId, enDesc);
/*    */   }
/*    */ 
/*    */   public EndDescriptor getEndDescriptor(String endId) {
/* 91 */     return (EndDescriptor)this.endDescMap.get(endId);
/*    */   }
/*    */ 
/*    */   public void putStatusDescriptor(String statusId, StatusDescriptor staDesc) {
/* 95 */     this.statusDescMap.put(statusId, staDesc);
/*    */   }
/*    */ 
/*    */   public StatusDescriptor getStatusDescriptor(String statusId) {
/* 99 */     return (StatusDescriptor)this.statusDescMap.get(statusId);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.WorkflowDescriptor
 * JD-Core Version:    0.6.0
 */