/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ActionDescriptor extends AbstractDescriptor
/*    */   implements Comparable<ActionDescriptor>
/*    */ {
/*    */   private String toStepId;
/*    */   private String toSplitId;
/*    */   private String toJoinId;
/*    */   private String toEndId;
/*    */   private String actionNo;
/*    */   private int remap;
/*    */   private int linkpri;
/* 15 */   private Map<String, FunctionDescriptor> preFunMap = new HashMap();
/* 16 */   private Map<String, FunctionDescriptor> postFunMap = new HashMap();
/*    */ 
/* 18 */   public String getToStepId() { return this.toStepId; }
/*    */ 
/*    */   public void setToStepId(String toStepId) {
/* 21 */     this.toStepId = toStepId;
/*    */   }
/*    */   public Map<String, FunctionDescriptor> getPreFunMap() {
/* 24 */     return this.preFunMap;
/*    */   }
/*    */   public void setPreFunMap(Map<String, FunctionDescriptor> preFunMap) {
/* 27 */     this.preFunMap = preFunMap;
/*    */   }
/*    */   public Map<String, FunctionDescriptor> getPostFunMap() {
/* 30 */     return this.postFunMap;
/*    */   }
/*    */   public void setPostFunMap(Map<String, FunctionDescriptor> postFunMap) {
/* 33 */     this.postFunMap = postFunMap;
/*    */   }
/*    */   public void putPreFuncMap(String funcId, FunctionDescriptor func) {
/* 36 */     this.preFunMap.put(funcId, func);
/*    */   }
/*    */   public void putPostFuncMap(String funcId, FunctionDescriptor func) {
/* 39 */     this.postFunMap.put(funcId, func);
/*    */   }
/*    */   public FunctionDescriptor getPreFuncById(String preId) {
/* 42 */     return (FunctionDescriptor)this.preFunMap.get(preId);
/*    */   }
/*    */   public FunctionDescriptor getPostFuncById(String postId) {
/* 45 */     return (FunctionDescriptor)this.postFunMap.get(postId);
/*    */   }
/*    */   public String getToSplitId() {
/* 48 */     return this.toSplitId;
/*    */   }
/*    */   public void setToSplitId(String toSplitId) {
/* 51 */     this.toSplitId = toSplitId;
/*    */   }
/*    */   public String getToJoinId() {
/* 54 */     return this.toJoinId;
/*    */   }
/*    */   public void setToJoinId(String toJoinId) {
/* 57 */     this.toJoinId = toJoinId;
/*    */   }
/*    */   public String getToEndId() {
/* 60 */     return this.toEndId;
/*    */   }
/*    */   public void setToEndId(String toEndId) {
/* 63 */     this.toEndId = toEndId;
/*    */   }
/*    */   public String getActionNo() {
/* 66 */     return this.actionNo;
/*    */   }
/*    */   public void setActionNo(String actionNo) {
/* 69 */     this.actionNo = actionNo;
/*    */   }
/*    */   public int getLinkpri() {
/* 72 */     return this.linkpri;
/*    */   }
/*    */   public void setLinkpri(int linkpri) {
/* 75 */     this.linkpri = linkpri;
/*    */   }
/*    */   public int compareTo(ActionDescriptor o) {
/* 78 */     if (o != null) {
/* 79 */       return o.getLinkpri() >= this.linkpri ? -1 : 1;
/*    */     }
/* 81 */     return 0;
/*    */   }
/*    */   public int getRemap() {
/* 84 */     return this.remap;
/*    */   }
/*    */   public void setRemap(int remap) {
/* 87 */     this.remap = remap;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.ActionDescriptor
 * JD-Core Version:    0.6.0
 */