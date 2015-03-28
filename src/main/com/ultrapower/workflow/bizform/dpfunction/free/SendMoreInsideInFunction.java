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
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class SendMoreInsideInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 27 */     setOwnFlag(userId, fromTask, task, step, acInfo, inputs, null);
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 34 */     String hql = "from DealProcessModel where taskId='" + task.getId() + "'";
/* 35 */     List find = this.bizDao.find(hql, null);
/* 36 */     if (CollectionUtils.isNotEmpty(find)) {
/* 37 */       DealProcessModel dp = (DealProcessModel)find.get(0);
/* 38 */       dp.setFlowId(step.getFlowId());
/* 39 */       dp.setFlagRecall(Long.valueOf(0L));
/*    */     }
/* 41 */     ProcessTask ptask = (ProcessTask)task;
/* 42 */     ptask.setFlagRecall(0L);
/* 43 */     ptask.setFlowId(step.getFlowId());
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.SendMoreInsideInFunction
 * JD-Core Version:    0.6.0
 */