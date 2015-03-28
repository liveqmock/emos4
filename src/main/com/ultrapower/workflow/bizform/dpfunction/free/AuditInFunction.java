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
/*    */ public class AuditInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 26 */     checkUser(acInfo);
/* 27 */     dpModel.setFlagToAssistAuditing(Long.valueOf(1L));
/* 28 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 29 */     ProcessTask ptask = (ProcessTask)task;
/* 30 */     ptask.setFlagToAssistAuditing(1L);
/* 31 */     ptask.setFlagToAuditing(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.AuditInFunction
 * JD-Core Version:    0.6.0
 */