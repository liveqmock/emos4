/*     */ package com.ultrapower.workflow.bizform.dpfunction.define;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.ultrasm.service.DepManagerService;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.engine.core.IDefFunction;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public abstract class AbstractDefInFunction
/*     */   implements IDefFunction
/*     */ {
/*  38 */   private Logger log = LoggerFactory.getLogger(AbstractDefInFunction.class);
/*     */   private IDao<DealProcessModel> bizDao;
/*     */   private IDao<WfEntry> entryDao;
/*     */   protected UserManagerService userManagerService;
/*     */   protected DepManagerService depManagerService;
/*     */ 
/*     */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionDescriptor acDesc, AbstractDescriptor desc, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/*  46 */     String entryId = step.getEntryId();
/*  47 */     String track = step.getTrack();
/*  48 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/*  49 */     String hql = "from DealProcessModel where entryId='" + entryId + "' and flagEndDuplicated=1 and phaseNo='" + task.getStepCode() + "'";
/*  50 */     List dpList = this.bizDao.find(hql, null);
/*  51 */     DealProcessModel dpModel = BizFormUtil.inDp(userId, task, step, acInfo, inputs, entry, dpList, this.bizDao, desc);
/*  52 */     dpModel.setFlagPredefined(Long.valueOf(1L));
/*  53 */     dpModel.setPrePhaseNo(StringUtils.isNotBlank(step.getOrigCode()) ? step.getOrigCode() : step.getForwardCode());
/*  54 */     dpModel.setFlagStartInsideFlow(Long.valueOf(1L));
/*  55 */     dpModel.setFlagAssign(Long.valueOf(1L));
/*  56 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/*  57 */     dpModel.setTrack(track);
/*  58 */     ProcessTask ptask = (ProcessTask)task;
/*  59 */     ptask.setPrePhaseNo(dpModel.getPrePhaseNo());
/*  60 */     ptask.setFlagPreDefined(1L);
/*  61 */     ptask.setFlagAssign(1L);
/*  62 */     ptask.setFlagToAuditing(1L);
/*  63 */     ptask.setTrack(track);
/*  64 */     if ((desc != null) && ((desc instanceof StepDescriptor))) {
/*  65 */       StepDescriptor stDesc = (StepDescriptor)desc;
/*  66 */       dpModel.setDpDesc(stDesc.getDesc() + WfEngineUtils.getActionDesc(acInfo));
/*  67 */       dpModel.setStepNo(stDesc.getStepNo());
/*  68 */       dpModel.setStepGroup(stDesc.getStepGroup());
/*  69 */       task.setTaskName(dpModel.getDpDesc());
/*  70 */       ptask.setStepNo(stDesc.getStepNo());
/*  71 */       ptask.setStepGroup(stDesc.getStepGroup());
/*     */     }
/*  73 */     String actionType = acInfo.getActionType();
/*  74 */     setOwnFlag(userId, task, step, acInfo, inputs, dpModel, desc);
/*  75 */     this.bizDao.merge(dpModel);
/*  76 */     if ((!"CHASE".equals(actionType)) && (!"NEW".equals(actionType)))
/*  77 */       sendMsg(userId, acInfo, acDesc, fromTask, task, inputs);
/*     */   }
/*     */ 
/*     */   public void sendMsg(String userId, ActionInfo acInfo, ActionDescriptor acDesc, BaseTask fromTask, BaseTask task, Map<String, DataField> inputs)
/*     */   {
/*  91 */     WfEngineUtils.sendMsg(userId, acInfo, acDesc, fromTask, task, inputs);
/*     */   }
/*     */ 
/*     */   public abstract void setOwnFlag(String paramString, BaseTask paramBaseTask, Step paramStep, ActionInfo paramActionInfo, Map<String, DataField> paramMap, DealProcessModel paramDealProcessModel, AbstractDescriptor paramAbstractDescriptor);
/*     */ 
/*     */   public IDao<DealProcessModel> getBizDao()
/*     */   {
/* 107 */     return this.bizDao;
/*     */   }
/*     */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 110 */     this.bizDao = bizDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfEntry> getEntryDao() {
/* 114 */     return this.entryDao;
/*     */   }
/*     */ 
/*     */   public void setEntryDao(IDao<WfEntry> entryDao) {
/* 118 */     this.entryDao = entryDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 122 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 126 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public DepManagerService getDepManagerService() {
/* 130 */     return this.depManagerService;
/*     */   }
/*     */ 
/*     */   public void setDepManagerService(DepManagerService depManagerService) {
/* 134 */     this.depManagerService = depManagerService;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.define.AbstractDefInFunction
 * JD-Core Version:    0.6.0
 */