/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SubflowsDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private ConditionDescriptor conDesc;
/*  8 */   private Map<String, SubflowDescriptor> subflowMap = new HashMap();
/*    */ 
/* 10 */   public ConditionDescriptor getConDesc() { return this.conDesc; }
/*    */ 
/*    */   public void setConDesc(ConditionDescriptor conDesc) {
/* 13 */     this.conDesc = conDesc;
/*    */   }
/*    */   public Map<String, SubflowDescriptor> getSubflowMap() {
/* 16 */     return this.subflowMap;
/*    */   }
/*    */   public void setSubflowMap(Map<String, SubflowDescriptor> subflowMap) {
/* 19 */     this.subflowMap = subflowMap;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.SubflowsDescriptor
 * JD-Core Version:    0.6.0
 */