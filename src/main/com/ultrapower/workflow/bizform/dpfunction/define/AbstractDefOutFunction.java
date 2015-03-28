/*    */ package com.ultrapower.workflow.bizform.dpfunction.define;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*    */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.IDefFunction;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
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
/*    */ public abstract class AbstractDefOutFunction
/*    */   implements IDefFunction
/*    */ {
/*    */   protected IDao<DealProcessModel> bizDao;
/*    */   protected UserManagerService userManagerService;
/*    */ 
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionDescriptor acDesc, AbstractDescriptor desc, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 39 */     String hql = "from DealProcessModel where taskId='" + task.getId() + "'";
/* 40 */     List dpList = this.bizDao.find(hql, null);
/* 41 */     if (CollectionUtils.isNotEmpty(dpList)) {
/* 42 */       DealProcessModel dpModel = BizFormUtil.outDp(userId, task, step, acInfo, inputs, dpList);
/* 43 */       if ((desc instanceof StepDescriptor)) {
/* 44 */         StepDescriptor stDesc = (StepDescriptor)desc;
/* 45 */         String type = stDesc.getType();
/* 46 */         boolean auto = stDesc.isAuto();
/* 47 */         if (auto) {
/* 48 */           WfEngineUtils.resetDpAssignee(dpModel);
/* 49 */           WfEngineUtils.resetDpDealer(dpModel);
/* 50 */           WfEngineUtils.resetTaskAssignee(task);
/* 51 */           WfEngineUtils.resetTaskDealer(task);
/*    */ 
/* 53 */           if ("AUDITING".equals(type)) {
/* 54 */             dpModel.setProcessStatus("已审批");
/* 55 */             task.setProcessState("已审批");
/*    */           } else {
/* 57 */             dpModel.setProcessStatus("已处理");
/* 58 */             task.setProcessState("已处理");
/*    */           }
/*    */         }
/*    */       }
/* 62 */       setOwnFlag(userId, task, step, acInfo, inputs, dpModel, desc);
/* 63 */       this.bizDao.merge(dpModel);
/*    */     }
/*    */   }
/*    */ 
/*    */   public abstract void setOwnFlag(String paramString, BaseTask paramBaseTask, Step paramStep, ActionInfo paramActionInfo, Map<String, DataField> paramMap, DealProcessModel paramDealProcessModel, AbstractDescriptor paramAbstractDescriptor);
/*    */ 
/*    */   public IDao<DealProcessModel> getBizDao()
/*    */   {
/* 80 */     return this.bizDao;
/*    */   }
/*    */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 83 */     this.bizDao = bizDao;
/*    */   }
/*    */ 
/*    */   public UserManagerService getUserManagerService() {
/* 87 */     return this.userManagerService;
/*    */   }
/*    */ 
/*    */   public void setUserManagerService(UserManagerService userManagerService) {
/* 91 */     this.userManagerService = userManagerService;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.define.AbstractDefOutFunction
 * JD-Core Version:    0.6.0
 */