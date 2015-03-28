/*    */ package com.ultrapower.workflow.engine.store.model;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*    */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
/*    */ import org.apache.commons.beanutils.BeanUtils;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="BS_T_WF_CURRENTSTEPS")
/*    */ public class WfCurrentStep extends Step<WfCurrentStep>
/*    */ {
/*    */   public WfCurrentStep()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WfCurrentStep(String entryId, String forwardId, String forwardCode, String backwardId, String backwardCode, String origId, String origCode, String type)
/*    */   {
/* 24 */     super(null, entryId, UUIDGenerator.getId(), forwardId, forwardCode, backwardId, backwardCode, null, origId, origCode, null, null, TimeUtils.getCurrentTime(), 0, 0, type);
/*    */   }
/*    */ 
/*    */   public WfCurrentStep(String entryId, String stepCode, String forwardId, String forwardCode, String backwardId, String backwardCode, String flowId, String track, String type)
/*    */   {
/* 30 */     super(null, entryId, stepCode, forwardId, forwardCode, backwardId, backwardCode, flowId, null, null, null, track, TimeUtils.getCurrentTime(), 0, 0, type);
/*    */   }
/*    */ 
/*    */   public WfHistoryStep toHistory() {
/* 34 */     WfHistoryStep his = new WfHistoryStep();
/*    */     try {
/* 36 */       BeanUtils.copyProperties(his, this);
/*    */     } catch (IllegalAccessException e) {
/* 38 */       e.printStackTrace();
/*    */     } catch (InvocationTargetException e) {
/* 40 */       e.printStackTrace();
/*    */     }
/* 42 */     return his;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.store.model.WfCurrentStep
 * JD-Core Version:    0.6.0
 */