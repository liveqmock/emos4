/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import java.util.Map;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class FinishInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 26 */     ProcessTask ptask = (ProcessTask)task;
/* 27 */     dpModel.setFlagAssign(Long.valueOf(1L));
/* 28 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 29 */     dpModel.setFlagClose(Long.valueOf(1L));
/* 30 */     dpModel.setFlagCancel(Long.valueOf(1L));
/* 31 */     dpModel.setFlagTurnDown(Long.valueOf(1L));
/* 32 */     ptask.setFlagAssign(1L);
/* 33 */     ptask.setFlagToAuditing(1L);
/* 34 */     ptask.setFlagClose(1L);
/* 35 */     ptask.setFlagCancel(1L);
/* 36 */     ptask.setFlagTurnDown(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.FinishInFunction
 * JD-Core Version:    0.6.0
 */