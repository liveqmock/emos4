/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ConditionsDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private List<ConditionDescriptor> conditionList;
/*    */   private List<ConditionsDescriptor> conditionsList;
/*    */ 
/*    */   public List<ConditionDescriptor> getConditionList()
/*    */   {
/* 10 */     return this.conditionList;
/*    */   }
/*    */   public void setConditionList(List<ConditionDescriptor> conditionList) {
/* 13 */     this.conditionList = conditionList;
/*    */   }
/*    */   public List<ConditionsDescriptor> getConditionsList() {
/* 16 */     return this.conditionsList;
/*    */   }
/*    */   public void setConditionsList(List<ConditionsDescriptor> conditionsList) {
/* 19 */     this.conditionsList = conditionsList;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.ConditionsDescriptor
 * JD-Core Version:    0.6.0
 */