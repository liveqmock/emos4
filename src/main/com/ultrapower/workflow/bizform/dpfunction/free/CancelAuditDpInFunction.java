/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class CancelAuditDpInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 31 */     setOwnFlag(userId, fromTask, task, step, acInfo, inputs, null);
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask baseTask, Step currStep, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 38 */     String forwardCode = currStep.getForwardCode();
/* 39 */     String origCode = currStep.getOrigCode();
/* 40 */     forwardCode = StringUtils.isNotBlank(origCode) ? origCode : forwardCode;
/* 41 */     String stepId = baseTask.getStepId();
/*    */ 
/* 43 */     String hql = "from DealProcessModel where stepId='" + stepId + "'";
/* 44 */     List find = this.bizDao.find(hql, null);
/* 45 */     if (CollectionUtils.isNotEmpty(find)) {
/* 46 */       DealProcessModel dp = (DealProcessModel)find.get(0);
/* 47 */       String dpPrePhaseNo = dp.getPrePhaseNo();
/* 48 */       String origPhaseNo = dp.getOrigPhaseNo();
/* 49 */       if (((StringUtils.isNotBlank(dpPrePhaseNo)) && (dpPrePhaseNo.equals(forwardCode))) || ((StringUtils.isNotBlank(origPhaseNo)) && (origPhaseNo.equals(forwardCode)))) {
/* 50 */         long now = System.currentTimeMillis() / 1000L;
/*    */ 
/* 52 */         dp.setFlagEndPhase(Long.valueOf(0L));
/* 53 */         dp.setEdDate(Long.valueOf(now));
/* 54 */         dp.setBgDate(Long.valueOf(now));
/* 55 */         dp.setProcessStatus(WfEngineUtils.outProcessState("AUDITINGNOPASS"));
/* 56 */         long flagPredefined = dp.getFlagPredefined().longValue();
/* 57 */         String actionName = WfEngineUtils.getActionName("AUDITINGNOPASS", inputs);
/* 58 */         dp.setEdProcessAction(actionName);
/* 59 */         dp.setFlagActive(Long.valueOf(0L));
/* 60 */         this.taskManager.deleteCurrentTaskById(baseTask.getId());
/* 61 */         this.currentDao.removeById(baseTask.getStepId());
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.CancelAuditDpInFunction
 * JD-Core Version:    0.6.0
 */