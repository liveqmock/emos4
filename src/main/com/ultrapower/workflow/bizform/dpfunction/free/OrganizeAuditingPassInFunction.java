/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import java.util.Map;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class OrganizeAuditingPassInFunction extends AbstractFreeInFunction
/*    */ {
/* 24 */   private Logger log = LoggerFactory.getLogger(OrganizeAuditingPassInFunction.class);
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 30 */     ProcessTask ptask = (ProcessTask)task;
/* 31 */     dpModel.setFlagAssign(Long.valueOf(1L));
/* 32 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 33 */     dpModel.setFlagClose(Long.valueOf(1L));
/* 34 */     dpModel.setFlagCancel(Long.valueOf(1L));
/* 35 */     ptask.setFlagAssign(1L);
/* 36 */     ptask.setFlagToAuditing(1L);
/* 37 */     ptask.setFlagClose(1L);
/* 38 */     ptask.setFlagCancel(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.OrganizeAuditingPassInFunction
 * JD-Core Version:    0.6.0
 */