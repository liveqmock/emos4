/*    */ package com.ultrapower.workflow.engine.store.model;
/*    */ 
/*    */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
/*    */ import org.apache.commons.beanutils.BeanUtils;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="BS_T_WF_HISTORYSTEPS")
/*    */ public class WfHistoryStep extends Step<WfHistoryStep>
/*    */ {
/*    */   public WfCurrentStep toCurrent()
/*    */   {
/* 22 */     WfCurrentStep curr = new WfCurrentStep();
/*    */     try {
/* 24 */       BeanUtils.copyProperties(curr, this);
/* 25 */       curr.setId(UUIDGenerator.getId());
/* 26 */       curr.setSubCount(0);
/* 27 */       curr.setFinishCount(0);
/* 28 */       curr.setFlowId(null);
/*    */     } catch (IllegalAccessException e) {
/* 30 */       e.printStackTrace();
/*    */     } catch (InvocationTargetException e) {
/* 32 */       e.printStackTrace();
/*    */     }
/* 34 */     return curr;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.store.model.WfHistoryStep
 * JD-Core Version:    0.6.0
 */