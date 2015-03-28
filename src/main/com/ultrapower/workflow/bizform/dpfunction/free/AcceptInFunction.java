/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class AcceptInFunction extends AbstractFreeInFunction
/*    */ {
/* 26 */   protected DealProcessModel dpModel = null;
/*    */ 
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs) throws WorkflowException
/*    */   {
/* 30 */     setOwnFlag(userId, fromTask, task, step, acInfo, inputs, null);
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step currStep, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dp)
/*    */   {
/* 38 */     Long now = Long.valueOf(TimeUtils.getCurrentTime());
/* 39 */     String processState = WfEngineUtils.inProcessState(acInfo.getActionType());
/* 40 */     String hql = "from DealProcessModel where taskId = '" + task.getId() + "'";
/* 41 */     List dps = this.bizDao.find(hql, null);
/* 42 */     if (CollectionUtils.isNotEmpty(dps)) {
/* 43 */       this.dpModel = ((DealProcessModel)dps.get(0));
/* 44 */       WfEngineUtils.setDpDealer(this.dpModel, userId);
/* 45 */       this.dpModel.setBgDate(now);
/* 46 */       this.dpModel.setProcessStatus(processState);
/* 47 */       this.bizDao.saveOrUpdate(this.dpModel);
/* 48 */       WfEngineUtils.setTaskDealer(task, userId);
/* 49 */       task.setProcessState(processState);
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.AcceptInFunction
 * JD-Core Version:    0.6.0
 */