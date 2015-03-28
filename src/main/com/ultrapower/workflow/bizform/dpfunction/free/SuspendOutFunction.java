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
/*    */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class SuspendOutFunction extends AbstractFreeOutFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 30 */     String hql = "from DealProcessModel where taskId='" + task.getId() + "'";
/* 31 */     List dpList = this.bizDao.find(hql, null);
/* 32 */     if (CollectionUtils.isNotEmpty(dpList)) {
/* 33 */       DealProcessModel dpModel = (DealProcessModel)dpList.get(0);
/* 34 */       dpModel.setFlagActive(Long.valueOf(WfEngineConstants.FLAGACTIVE_SUSPEND));
/* 35 */       ProcessTask pTask = (ProcessTask)task;
/* 36 */       pTask.setFlagActive(WfEngineConstants.FLAGACTIVE_SUSPEND);
/* 37 */       WfEngineUtils.setDpDealer(dpModel, userId);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.SuspendOutFunction
 * JD-Core Version:    0.6.0
 */