/*     */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*     */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*     */ import com.ultrapower.eoms.ultrasm.service.DepManagerService;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.UltraWorkflow;
/*     */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.engine.core.IFreeFunction;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.FieldDataType;
/*     */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.exception.WorkflowEngineException;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import com.ultrapower.workflow.role.model.ChildRole;
/*     */ import com.ultrapower.workflow.role.service.IRoleService;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public abstract class AbstractFreeInFunction
/*     */   implements IFreeFunction
/*     */ {
/*  48 */   private static Logger log = LoggerFactory.getLogger(AbstractFreeInFunction.class);
/*     */   protected IDao<DealProcessModel> bizDao;
/*     */   protected IDao<WfEntry> entryDao;
/*     */   protected IDao<WfCurrentStep> currentDao;
/*     */   protected IDao<WfHistoryStep> historyDao;
/*     */   protected UserManagerService userManagerService;
/*     */   protected DepManagerService depManagerService;
/*     */   protected IRoleService roleService;
/*     */   protected UltraWorkflow ultraWorkflow;
/*     */   protected ITaskManager taskManager;
/*     */   protected UserInfo user;
/*     */ 
/*     */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/*  63 */     if ((step != null) && (task != null) && (StringUtils.isNotBlank(userId))) {
/*  64 */       ProcessTask ptask = (ProcessTask)task;
/*  65 */       String actionType = acInfo.getActionType();
/*  66 */       this.user = ThreadObj.getUsersInfo(userId);
/*  67 */       String actorName = getActorName(acInfo);
/*  68 */       String entryId = step.getEntryId();
/*  69 */       String baseId = task.getBaseId();
/*  70 */       String baseSchema = task.getBaseSchema();
/*  71 */       WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/*  72 */       String hql = "from DealProcessModel where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and phaseNo='" + task.getStepCode() + "' and flagEndDuplicated=1";
/*  73 */       List dpList = this.bizDao.find(hql, null);
/*  74 */       DealProcessModel dpModel = BizFormUtil.inDp(userId, task, step, acInfo, inputs, entry, dpList, this.bizDao, null);
/*  75 */       setOwnFlag(userId, fromTask, task, step, acInfo, inputs, dpModel);
/*  76 */       String preAssignString = dpModel.getPreAssignString();
/*  77 */       dpModel.setPreAssignString(null);
/*  78 */       ptask.setPreAssignString(null);
/*  79 */       String msg = WfEngineUtils.getDpDesc(actionType, inputs, this.user != null ? this.user.getFullname() : "", actorName);
/*  80 */       dpModel.setDpDesc(msg + WfEngineUtils.getActionDesc(acInfo));
/*  81 */       task.setTaskName(dpModel.getDpDesc());
/*  82 */       dpModel.setStepNo(task.getStepNo());
/*  83 */       dpModel.setStepGroup(task.getStepGroup());
/*  84 */       if (isSubFlow(task)) {
/*  85 */         dpModel.setFlagClose(Long.valueOf(0L));
/*  86 */         dpModel.setFlagCancel(Long.valueOf(0L));
/*  87 */         ptask.setFlagCancel(0L);
/*  88 */         ptask.setFlagClose(0L);
/*     */       }
/*  90 */       this.bizDao.merge(dpModel);
/*     */ 
/*  92 */       boolean sendMsgFlag = true;
/*     */ 
/*  95 */       if (("AUDITINGPASS".equals(actionType)) && 
/*  96 */         (StringUtils.isNotBlank(preAssignString))) {
/*  97 */         sendMsgFlag = false;
/*  98 */         log.info("审批通过并派发！");
/*  99 */         String[] preAsses = preAssignString.split("#;");
/* 100 */         if (!ArrayUtils.isEmpty(preAsses)) {
/* 101 */           List acInfoList = new ArrayList();
/* 102 */           for (int i = 0; i < preAsses.length; i++) {
/* 103 */             String assignee = preAsses[i];
/* 104 */             if (StringUtils.isNotBlank(assignee)) {
/* 105 */               BizFormUtil.parseAssignStr(acInfoList, assignee);
/*     */             }
/*     */           }
/* 108 */           String actorType = dpModel.getActorType();
/* 109 */           String actorStr = "";
/* 110 */           if ("USER".equals(actorType))
/* 111 */             actorStr = dpModel.getAssigneeId();
/*     */           else {
/* 113 */             actorStr = dpModel.getDealerId();
/*     */           }
/* 115 */           this.ultraWorkflow.doAction(null, null, "FREE", actorStr, "USER", task.getEntryId(), null, task.getId(), "ASSIGN", true, acInfoList, inputs, null);
/* 116 */           inputs.put("entry_state_update_flag_no", new DataField("entry_state_update_flag_no", FieldDataType.String, "entry_state_update_flag_no"));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 121 */       if (sendMsgFlag)
/*     */       {
/* 123 */         sendMsg(userId, acInfo, fromTask, task, inputs);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendMsg(String userId, ActionInfo acInfo, BaseTask fromTask, BaseTask task, Map<String, DataField> inputs)
/*     */   {
/* 137 */     WfEngineUtils.sendMsg(userId, acInfo, fromTask, task, inputs);
/*     */   }
/*     */ 
/*     */   public String getActorName(ActionInfo acInfo)
/*     */   {
/* 146 */     String actorName = "";
/* 147 */     if (acInfo != null) {
/* 148 */       String actorType = acInfo.getActorType();
/* 149 */       String actorId = acInfo.getActorId();
/* 150 */       if ("USER".equals(actorType)) {
/* 151 */         UserInfo actor = ThreadObj.getUsersInfo(actorId);
/* 152 */         if (actor != null)
/* 153 */           actorName = actor.getFullname();
/*     */         else
/* 155 */           throw new WorkflowEngineException(" 用户不存在！userId=" + actorId);
/*     */       }
/* 157 */       else if ("GROUP".equals(actorType)) {
/* 158 */         DepInfo dep = ThreadObj.getDepInfo(actorId);
/* 159 */         if (dep != null)
/* 160 */           actorName = dep.getDepname();
/*     */         else
/* 162 */           throw new WorkflowEngineException(" 用户组不存在！depId=" + actorId);
/*     */       }
/* 164 */       else if ("ROLE".equals(actorType)) {
/* 165 */         ChildRole childRole = ThreadObj.getChildRole(actorId);
/* 166 */         if (childRole != null)
/* 167 */           actorName = childRole.getChildRoleName();
/*     */         else {
/* 169 */           throw new WorkflowEngineException(" 角色细分不存在！childRoleId=" + actorId);
/*     */         }
/*     */       }
/*     */     }
/* 173 */     return actorName;
/*     */   }
/*     */ 
/*     */   public boolean isSubFlow(BaseTask task)
/*     */   {
/* 182 */     boolean rtn = false;
/* 183 */     if (task != null) {
/* 184 */       rtn = !task.getEntryId().equals(task.getTopEntryId());
/*     */     }
/* 186 */     return rtn;
/*     */   }
/*     */ 
/*     */   protected void checkUser(ActionInfo acInfo) {
/* 190 */     if ((acInfo == null) || (StringUtils.isBlank(acInfo.getActorId())))
/* 191 */       throw new RuntimeException(acInfo.getActionType() + " 动作的处理人不能为空！");
/*     */   }
/*     */ 
/*     */   public abstract void setOwnFlag(String paramString, BaseTask paramBaseTask1, BaseTask paramBaseTask2, Step paramStep, ActionInfo paramActionInfo, Map<String, DataField> paramMap, DealProcessModel paramDealProcessModel);
/*     */ 
/*     */   public IDao<DealProcessModel> getBizDao()
/*     */   {
/* 208 */     return this.bizDao;
/*     */   }
/*     */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 211 */     this.bizDao = bizDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfEntry> getEntryDao() {
/* 215 */     return this.entryDao;
/*     */   }
/*     */ 
/*     */   public void setEntryDao(IDao<WfEntry> entryDao) {
/* 219 */     this.entryDao = entryDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 223 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 227 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public IDao<WfCurrentStep> getCurrentDao() {
/* 231 */     return this.currentDao;
/*     */   }
/*     */ 
/*     */   public void setCurrentDao(IDao<WfCurrentStep> currentDao) {
/* 235 */     this.currentDao = currentDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfHistoryStep> getHistoryDao() {
/* 239 */     return this.historyDao;
/*     */   }
/*     */ 
/*     */   public void setHistoryDao(IDao<WfHistoryStep> historyDao) {
/* 243 */     this.historyDao = historyDao;
/*     */   }
/*     */ 
/*     */   public DepManagerService getDepManagerService() {
/* 247 */     return this.depManagerService;
/*     */   }
/*     */ 
/*     */   public void setDepManagerService(DepManagerService depManagerService) {
/* 251 */     this.depManagerService = depManagerService;
/*     */   }
/*     */ 
/*     */   public IRoleService getRoleService() {
/* 255 */     return this.roleService;
/*     */   }
/*     */ 
/*     */   public void setRoleService(IRoleService roleService) {
/* 259 */     this.roleService = roleService;
/*     */   }
/*     */ 
/*     */   public ITaskManager getTaskManager() {
/* 263 */     return this.taskManager;
/*     */   }
/*     */ 
/*     */   public void setTaskManager(ITaskManager taskManager) {
/* 267 */     this.taskManager = taskManager;
/*     */   }
/*     */ 
/*     */   public UltraWorkflow getUltraWorkflow() {
/* 271 */     return this.ultraWorkflow;
/*     */   }
/*     */ 
/*     */   public void setUltraWorkflow(UltraWorkflow ultraWorkflow) {
/* 275 */     this.ultraWorkflow = ultraWorkflow;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.AbstractFreeInFunction
 * JD-Core Version:    0.6.0
 */