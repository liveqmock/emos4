/*     */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.engine.core.IFreeFunction;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public abstract class AbstractFreeOutFunction
/*     */   implements IFreeFunction
/*     */ {
/*     */   protected IDao<DealProcessModel> bizDao;
/*     */   protected IDao<WfEntry> entryDao;
/*     */   protected IDao<WfCurrentStep> currentDao;
/*     */   protected IDao<WfHistoryStep> historyDao;
/*     */   protected UserManagerService userManagerService;
/*     */   protected ITaskManager taskManager;
/*     */ 
/*     */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/*  45 */     String hql = "from DealProcessModel where taskId='" + task.getId() + "'";
/*  46 */     List dpList = this.bizDao.find(hql, null);
/*  47 */     if (CollectionUtils.isNotEmpty(dpList)) {
/*  48 */       DealProcessModel dpModel = BizFormUtil.outDp(userId, task, step, acInfo, inputs, dpList);
/*  49 */       setOwnFlag(userId, fromTask, task, step, acInfo, inputs, dpModel);
/*  50 */       this.bizDao.merge(dpModel);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getSendFlag(Step step)
/*     */   {
/*  60 */     boolean sendFlag = true;
/*  61 */     if (step != null) {
/*  62 */       String historyStepId = StringUtils.isNotBlank(step.getOrigId()) ? step.getOrigId() : step.getForwardId();
/*  63 */       WfHistoryStep tarStep = (WfHistoryStep)this.historyDao.get(historyStepId);
/*  64 */       if (tarStep != null) {
/*  65 */         String forwardCode = tarStep.getForwardCode();
/*  66 */         String entryId = tarStep.getEntryId();
/*  67 */         WfEntry wfEntry = (WfEntry)this.entryDao.get(entryId);
/*  68 */         if (("BEGIN".equals(forwardCode)) && (StringUtils.isNotBlank(wfEntry.getParentEntryId()))) {
/*  69 */           sendFlag = false;
/*     */         }
/*     */       }
/*     */     }
/*  73 */     return sendFlag;
/*     */   }
/*     */ 
/*     */   public void sendMsg(String userId, ActionInfo acInfo, BaseTask fromTask, BaseTask task, Map<String, DataField> inputs)
/*     */   {
/*  85 */     WfEngineUtils.sendMsg(userId, acInfo, fromTask, task, inputs);
/*     */   }
/*     */ 
/*     */   public abstract void setOwnFlag(String paramString, BaseTask paramBaseTask1, BaseTask paramBaseTask2, Step paramStep, ActionInfo paramActionInfo, Map<String, DataField> paramMap, DealProcessModel paramDealProcessModel);
/*     */ 
/*     */   public IDao<DealProcessModel> getBizDao()
/*     */   {
/* 101 */     return this.bizDao;
/*     */   }
/*     */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 104 */     this.bizDao = bizDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfEntry> getEntryDao() {
/* 108 */     return this.entryDao;
/*     */   }
/*     */ 
/*     */   public void setEntryDao(IDao<WfEntry> entryDao) {
/* 112 */     this.entryDao = entryDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfCurrentStep> getCurrentDao() {
/* 116 */     return this.currentDao;
/*     */   }
/*     */ 
/*     */   public void setCurrentDao(IDao<WfCurrentStep> currentDao) {
/* 120 */     this.currentDao = currentDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfHistoryStep> getHistoryDao() {
/* 124 */     return this.historyDao;
/*     */   }
/*     */ 
/*     */   public void setHistoryDao(IDao<WfHistoryStep> historyDao) {
/* 128 */     this.historyDao = historyDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 132 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 136 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public ITaskManager getTaskManager() {
/* 140 */     return this.taskManager;
/*     */   }
/*     */ 
/*     */   public void setTaskManager(ITaskManager taskManager) {
/* 144 */     this.taskManager = taskManager;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.AbstractFreeOutFunction
 * JD-Core Version:    0.6.0
 */