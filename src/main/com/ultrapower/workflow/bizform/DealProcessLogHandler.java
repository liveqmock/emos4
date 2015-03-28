/*     */ package com.ultrapower.workflow.bizform;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessLogModel;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class DealProcessLogHandler
/*     */ {
/*  28 */   private Logger log = LoggerFactory.getLogger(DealProcessLogHandler.class);
/*     */   private UserManagerService userManagerService;
/*     */   private IDao<DealProcessLogModel> dpLogDao;
/*     */   private IDao<DealProcessModel> bizDao;
/*     */ 
/*     */   public DealProcessModel writeTransLog(String entryType, String taskID, String userID, String actionType, Map<String, DataField> inputs, boolean beHastened)
/*     */   {
/*  45 */     DealProcessModel dpModel = null;
/*  46 */     String hql = "from DealProcessModel where taskId='" + taskID + "' ";
/*  47 */     List dpList = this.bizDao.find(hql, null);
/*  48 */     if ((dpList != null) && (dpList.size() > 0)) {
/*  49 */       dpModel = (DealProcessModel)dpList.get(0);
/*     */ 
/*  51 */       if ((actionType.equals("HASTEN")) && (beHastened)) {
/*  52 */         dpModel.setHastenCount(dpModel.getHastenCount() + 1L);
/*     */       }
/*     */ 
/*  55 */       DealProcessLogModel logModel = new DealProcessLogModel();
/*  56 */       logModel.setProcessId(dpModel.getProcessId());
/*  57 */       logModel.setProcessType(dpModel.getProcessType());
/*  58 */       logModel.setPhaseNo(dpModel.getPhaseNo());
/*  59 */       logModel.setBaseId(dpModel.getBaseId());
/*  60 */       logModel.setBaseSchema(dpModel.getBaseSchema());
/*  61 */       logModel.setFlowId(dpModel.getFlowId());
/*  62 */       logModel.setParentFlowId(dpModel.getParentFlowId());
/*  63 */       logModel.setBaseFlowId(dpModel.getBaseFlowId());
/*  64 */       logModel.setBaseCreateTime(dpModel.getBaseCreateTime());
/*  65 */       WfEngineUtils.setDpLogUser(logModel, userID);
/*     */ 
/*  67 */       String freeActionName = !actionType.equals("NEXT") ? WfEngineUtils.getActionName(actionType) : null;
/*  68 */       String defActionName = WfEngineUtils.getDefActionName(inputs);
/*  69 */       String dealDesc = WfEngineUtils.getDealDesc(inputs);
/*  70 */       logModel.setActionName(StringUtils.isNotBlank(defActionName) ? defActionName : freeActionName);
/*  71 */       logModel.setLogTime(Long.valueOf(TimeUtils.getCurrentTime()));
/*  72 */       logModel.setLogDesc(dealDesc);
/*  73 */       this.dpLogDao.save(logModel);
/*  74 */       this.log.info("成功记录日志, actionType:" + actionType);
/*     */     }
/*  76 */     return dpModel;
/*     */   }
/*     */ 
/*     */   public DealProcessModel writeLog(String entryType, String taskID, String userID, String actionType, List<ActionInfo> acs, Map<String, DataField> inputs)
/*     */   {
/*  90 */     DealProcessModel dp = null;
/*  91 */     this.log.info("开始记录非流转类动作日志（如：催办、建议等）, actionType:" + actionType);
/*  92 */     if (CollectionUtils.isNotEmpty(acs)) {
/*  93 */       for (int i = 0; i < acs.size(); i++) {
/*  94 */         ActionInfo ac = (ActionInfo)acs.get(i);
/*  95 */         String stepId = ac.getStepId();
/*     */ 
/*  98 */         String hql = "from DealProcessModel where stepId='" + stepId + "' and flagEndDuplicated=1 ";
/*  99 */         List dpList = this.bizDao.find(hql, null);
/* 100 */         if ((dpList != null) && (dpList.size() > 0)) {
/* 101 */           DealProcessModel dpModel = (DealProcessModel)dpList.get(0);
/* 102 */           String tarTaskId = dpModel.getTaskId();
/* 103 */           String assigneeId = dpModel.getAssigneeId();
/* 104 */           String assignGroupId = dpModel.getAssignGroupId();
/* 105 */           String actorType = dpModel.getActorType();
/* 106 */           ActionInfo acInfo = new ActionInfo();
/* 107 */           if (StringUtils.isNotBlank(assignGroupId))
/* 108 */             acInfo.setActorId(assignGroupId);
/*     */           else {
/* 110 */             acInfo.setActorId(assigneeId);
/*     */           }
/* 112 */           acInfo.setActorType(actorType);
/* 113 */           acInfo.setActionType(actionType);
/* 114 */           ITaskManager taskManager = (ITaskManager)ApplicationContextUtils.getBean("taskManager");
/* 115 */           BaseTask tarTask = taskManager.getTaskById(tarTaskId);
/* 116 */           BaseTask fromTask = StringUtils.isNotBlank(taskID) ? taskManager.getTaskById(taskID) : null;
/* 117 */           WfEngineUtils.sendMsg(userID, acInfo, fromTask, tarTask, inputs);
/* 118 */           writeTransLog(entryType, tarTaskId, userID, actionType, inputs, true);
/*     */         }
/*     */       }
/*     */     }
/* 122 */     return dp;
/*     */   }
/*     */ 
/*     */   public IDao<DealProcessLogModel> getDpLogDao() {
/* 126 */     return this.dpLogDao;
/*     */   }
/*     */ 
/*     */   public void setDpLogDao(IDao<DealProcessLogModel> dpLogDao) {
/* 130 */     this.dpLogDao = dpLogDao;
/*     */   }
/*     */ 
/*     */   public IDao<DealProcessModel> getBizDao() {
/* 134 */     return this.bizDao;
/*     */   }
/*     */ 
/*     */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 138 */     this.bizDao = bizDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 142 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 146 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.DealProcessLogHandler
 * JD-Core Version:    0.6.0
 */