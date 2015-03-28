/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class ChaseOutFunction extends AbstractFreeOutFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 28 */     setOwnFlag(userId, fromTask, task, step, acInfo, inputs, null);
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 36 */     String actionType = acInfo.getActionType();
/* 37 */     String currTaskId = task.getId();
/* 38 */     String hql = "from DealProcessModel where taskId='" + currTaskId + "'";
/* 39 */     List find = this.bizDao.find(hql, null);
/* 40 */     if (CollectionUtils.isNotEmpty(find)) {
/* 41 */       DealProcessModel dp = (DealProcessModel)find.get(0);
/* 42 */       long now = System.currentTimeMillis() / 1000L;
/* 43 */       WfEngineUtils.setDpDealer(dp, userId);
/* 44 */       dp.setFlagEndPhase(Long.valueOf(0L));
/* 45 */       dp.setEdDate(Long.valueOf(now));
/* 46 */       dp.setBgDate(Long.valueOf(now));
/* 47 */       dp.setProcessStatus(WfEngineUtils.outProcessState(actionType));
/* 48 */       long flagPredefined = dp.getFlagPredefined().longValue();
/* 49 */       String actionName = WfEngineUtils.getActionName(actionType, inputs);
/* 50 */       dp.setEdProcessAction(actionName);
/* 51 */       dp.setFlagActive(Long.valueOf(0L));
/* 52 */       ProcessTask ptask = (ProcessTask)task;
/* 53 */       ptask.setEdProcessAction(actionName);
/* 54 */       ptask.setFlagActive(0L);
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.ChaseOutFunction
 * JD-Core Version:    0.6.0
 */