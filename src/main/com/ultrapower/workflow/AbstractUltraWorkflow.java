/*     */ package com.ultrapower.workflow;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.workflow.bizform.DealProcessLogHandler;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.bizservice.BizCheck;
/*     */ import com.ultrapower.workflow.engine.core.WorkflowEngine;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.EngineModel;
/*     */ import com.ultrapower.workflow.engine.core.model.FieldDataType;
/*     */ import com.ultrapower.workflow.engine.core.model.WfAction;
/*     */ import com.ultrapower.workflow.engine.core.model.WfStep;
/*     */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*     */ import com.ultrapower.workflow.utils.ThreadUtils;
/*     */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public abstract class AbstractUltraWorkflow
/*     */   implements UltraWorkflow
/*     */ {
/*  37 */   private Logger log = LoggerFactory.getLogger(AbstractUltraWorkflow.class);
/*     */ 
/*  39 */   protected WorkflowEngine engine = null;
/*     */   protected IDao<WfEntry> entryDao;
/*     */   private BizCheck bizCheck;
/*     */ 
/*     */   public void test()
/*     */     throws WorkflowException
/*     */   {
/*     */   }
/*     */ 
/*     */   public List<WfAction> getAvailableActions(String baseSchema, String taskId, String defName, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/*  49 */     String BEGIN = "BEGIN";
/*  50 */     String AUDITING = "AUDITING";
/*  51 */     String DEAL = "DEAL";
/*  52 */     String COPY = "COPY";
/*  53 */     String ASSIGN_CN = "主办";
/*  54 */     String ASSIST_CN = "协办";
/*  55 */     String COPY_CN = "抄送";
/*  56 */     String AUDIT_CN = "审批";
/*  57 */     String CHECK_CN = "质检";
/*  58 */     String ORGANIZEAUDIT_CN = "会审";
/*  59 */     List actionList = new ArrayList();
/*  60 */     boolean entryActive = true;
/*     */ 
/*  62 */     if (StringUtils.isNotBlank(taskId)) {
/*  63 */       String baseId = null;
/*  64 */       String entryId = null;
/*  65 */       String stepCode = null;
/*  66 */       String baseStatus = null;
/*  67 */       long flagActive = 0L;
/*  68 */       ProcessTask ptask = null;
/*  69 */       BaseTask baseTask = getTaskManager().getTaskById(taskId);
/*  70 */       if (baseTask != null) {
/*  71 */         ptask = (ProcessTask)baseTask;
/*  72 */         baseId = baseTask.getBaseId();
/*  73 */         flagActive = ptask.getFlagActive();
/*  74 */         baseSchema = baseTask.getBaseSchema();
/*  75 */         baseStatus = baseTask.getEntryState();
/*  76 */         String topEntryId = baseTask.getTopEntryId();
/*  77 */         entryId = baseTask.getEntryId();
/*  78 */         stepCode = baseTask.getStepCode();
/*  79 */         WfEntry wfEntry = (WfEntry)this.entryDao.get(topEntryId);
/*  80 */         if (!"active".equals(wfEntry.getState())) {
/*  81 */           entryActive = false;
/*     */         }
/*     */       }
/*  84 */       this.log.info("获取当前任务的所有动作！baseSchema=" + baseSchema + ",baseId=" + baseId + ",taskId=" + taskId);
/*     */ 
/*  91 */       if ((entryActive) && (flagActive == 1L)) {
/*  92 */         actionList = this.engine.getAvailableActions(baseSchema, defName, entryId, stepCode, inputs);
/*     */       }
/*  94 */       Long flagPredefined = Long.valueOf(ptask.getFlagPreDefined());
/*  95 */       String phaseNo = ptask.getStepCode();
/*  96 */       Long flagAssign = Long.valueOf(ptask.getFlagAssign());
/*  97 */       Long flagAssist = Long.valueOf(ptask.getFlagAssist());
/*  98 */       Long flagCancel = Long.valueOf(ptask.getFlagCancel());
/*  99 */       Long flagClose = Long.valueOf(ptask.getFlagClose());
/* 100 */       Long flagCopy = Long.valueOf(ptask.getFlagCopy());
/* 101 */       Long flagToAssistAuditing = Long.valueOf(ptask.getFlagToAssistAuditing());
/* 102 */       Long flagToAuditing = Long.valueOf(ptask.getFlagToAuditing());
/* 103 */       Long flagTransfer = Long.valueOf(ptask.getFlagTransfer());
/* 104 */       Long flagTurnDown = Long.valueOf(ptask.getFlagTurnDown());
/* 105 */       Long flagTurnUp = Long.valueOf(ptask.getFlagTurnUp());
/* 106 */       Long flagRecall = Long.valueOf(ptask.getFlagRecall());
/* 107 */       String flagAssignType = ptask.getFlagAssignType();
/* 108 */       String processType = ptask.getProcessType();
/* 109 */       String prePhaseNo = ptask.getPrePhaseNo();
/* 110 */       Long bgDate = Long.valueOf(ptask.getAcceptTime());
/* 111 */       String parentFlowId = ptask.getParentFlowId();
/* 112 */       if (1L == flagActive) {
/* 113 */         if (1L == flagPredefined.longValue()) {
/* 114 */           String customActions = ptask.getCustomActions();
/* 115 */           if (StringUtils.isNotBlank(customActions)) {
/* 116 */             String[] actions = customActions.split(";");
/* 117 */             if (!ArrayUtils.isEmpty(actions)) {
/* 118 */               for (int i = 0; i < actions.length; i++) {
/* 119 */                 String actionStr = actions[i];
/* 120 */                 if (StringUtils.isNotBlank(actionStr)) {
/* 121 */                   String[] actionAry = actionStr.split("=");
/* 122 */                   String page = null;
/* 123 */                   if (actionAry.length > 1) {
/* 124 */                     page = actionAry[1];
/*     */                   }
/* 126 */                   actionList.add(new WfAction("NEXT", actionAry[0], page));
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 133 */         if (("BEGIN".equals(prePhaseNo)) && ("草稿".equals(baseStatus))) {
/* 134 */           actionList.add(0, new WfAction("SAVE", WfEngineUtils.getActionName("SAVE"), false));
/*     */         }
/* 136 */         if ((1L == flagAssign.longValue()) && (!AUDIT_CN.equals(flagAssignType))) {
/* 137 */           actionList.add(new WfAction("ASSIGN", WfEngineUtils.getActionName("ASSIGN"), true));
/*     */         }
/* 139 */         if ((1L == flagTransfer.longValue()) && (0L == flagPredefined.longValue())) {
/* 140 */           actionList.add(new WfAction("REASSIGN", WfEngineUtils.getActionName("REASSIGN"), true));
/*     */         }
/* 142 */         if ((1L == flagCopy.longValue()) && (!"已关闭".equals(baseStatus))) {
/* 143 */           actionList.add(new WfAction("MAKECOPY", WfEngineUtils.getActionName("MAKECOPY"), true));
/*     */         }
/* 145 */         if (1L == flagAssist.longValue()) flagPredefined.longValue();
/*     */ 
/* 147 */         if ((1L == flagToAuditing.longValue()) && (!AUDIT_CN.equals(flagAssignType))) {
/* 148 */           actionList.add(new WfAction("AUDIT", WfEngineUtils.getActionName("AUDIT"), true));
/*     */         }
/* 150 */         if ((1L == flagToAssistAuditing.longValue()) && (0L == flagPredefined.longValue())) {
/* 151 */           actionList.add(new WfAction("ORGANIZEAUDIT", WfEngineUtils.getActionName("ORGANIZEAUDIT"), true));
/*     */         }
/* 153 */         if (((ASSIGN_CN.equals(flagAssignType)) || (ASSIST_CN.equals(flagAssignType))) && (DEAL.equals(processType)) && (!BEGIN.equals(prePhaseNo)) && (0L == bgDate.longValue())) {
/* 154 */           actionList.add(0, new WfAction("ACCEPT", WfEngineUtils.getActionName("ACCEPT"), false));
/*     */         }
/* 156 */         if (((ASSIGN_CN.equals(flagAssignType)) || (ASSIST_CN.equals(flagAssignType))) && (DEAL.equals(processType)) && (!BEGIN.equals(prePhaseNo))) {
/* 157 */           actionList.add(1, new WfAction("NOTICE", WfEngineUtils.getActionName("NOTICE"), false));
/*     */         }
/* 159 */         if (((ASSIGN_CN.equals(flagAssignType)) || (ASSIST_CN.equals(flagAssignType))) && (DEAL.equals(processType)) && (!BEGIN.equals(prePhaseNo)) && (0L == flagPredefined.longValue())) {
/* 160 */           actionList.add(new WfAction("FINISH", WfEngineUtils.getActionName("FINISH"), false));
/*     */         }
/* 162 */         if ((AUDIT_CN.equals(flagAssignType)) && (AUDITING.equals(processType)) && (0L == flagPredefined.longValue())) {
/* 163 */           actionList.add(new WfAction("REAUDIT", WfEngineUtils.getActionName("REAUDIT"), true));
/* 164 */           actionList.add(new WfAction("AUDITINGPASS", WfEngineUtils.getActionName("AUDITINGPASS"), false));
/* 165 */           actionList.add(new WfAction("AUDITINGNOPASS", WfEngineUtils.getActionName("AUDITINGNOPASS"), false));
/*     */         }
/* 167 */         if ((ORGANIZEAUDIT_CN.equals(flagAssignType)) && (AUDITING.equals(processType)) && (0L == flagPredefined.longValue())) {
/* 168 */           actionList.add(new WfAction("ORGANIZEAUDITINGPASS", WfEngineUtils.getActionName("ORGANIZEAUDITINGPASS"), false));
/* 169 */           actionList.add(new WfAction("ORGANIZEAUDITINGNOPASS", WfEngineUtils.getActionName("ORGANIZEAUDITINGNOPASS"), false));
/*     */         }
/* 171 */         if ((COPY_CN.equals(flagAssignType)) && (DEAL.equals(processType))) {
/* 172 */           actionList.add(new WfAction("CONFIRM", WfEngineUtils.getActionName("CONFIRM"), false));
/*     */         }
/* 174 */         if (0L == flagPredefined.longValue()) {
/* 175 */           if ((1L == flagClose.longValue()) && (DEAL.equals(processType)) && ((WfEngineUtils.entryState("FINISH").equals(baseStatus)) || (WfEngineUtils.entryState("AUDITINGPASS").equals(baseStatus)) || (WfEngineUtils.entryState("NEW").equals(baseStatus)))) {
/* 176 */             actionList.add(new WfAction("CLOSE", WfEngineUtils.getActionName("CLOSE"), false));
/*     */           }
/*     */         }
/* 179 */         else if (1L == flagClose.longValue()) {
/* 180 */           actionList.add(new WfAction("CLOSE", WfEngineUtils.getActionName("CLOSE"), false));
/*     */         }
/*     */ 
/* 183 */         if (1L == flagCancel.longValue()) {
/* 184 */           actionList.add(new WfAction("CANCEL", WfEngineUtils.getActionName("CANCEL"), false));
/*     */         }
/* 186 */         if ((!BEGIN.equals(prePhaseNo)) && ((ASSIGN_CN.equals(flagAssignType)) || (ASSIST_CN.equals(flagAssignType)) || (COPY_CN.equals(flagAssignType))) && (DEAL.equals(processType)) && (1L == flagTurnUp.longValue())) {
/* 187 */           actionList.add(new WfAction("REJECT", WfEngineUtils.getActionName("REJECT"), false));
/*     */         }
/* 189 */         if ((AUDIT_CN.equals(flagAssignType)) && (AUDITING.equals(processType)) && (1L == flagTurnUp.longValue())) {
/* 190 */           actionList.add(new WfAction("REJECT", WfEngineUtils.getActionName("REJECT"), false));
/*     */         }
/* 192 */         if ((ORGANIZEAUDIT_CN.equals(flagAssignType)) && (AUDITING.equals(processType)) && (1L == flagTurnUp.longValue())) {
/* 193 */           actionList.add(new WfAction("REJECT", WfEngineUtils.getActionName("REJECT"), false));
/*     */         }
/* 195 */         if ((0L == flagPredefined.longValue()) && (1L == flagTurnDown.longValue()) && (DEAL.equals(processType)) && ((ASSIGN_CN.equals(flagAssignType)) || (ASSIST_CN.equals(flagAssignType))) && ((1L == flagActive) || (2L == flagActive))) {
/* 196 */           actionList.add(new WfAction("SENDBACK", WfEngineUtils.getActionName("SENDBACK"), false));
/*     */         }
/*     */       }
/* 199 */       else if (entryActive) {
/* 200 */         if (1L == flagPredefined.longValue()) {
/* 201 */           this.bizCheck.chasePriviCheck(ptask);
/* 202 */           String dealTypeStr = ptask.getDealTypeStr();
/* 203 */           if ((0L == flagActive) && (1L == flagRecall.longValue()) && (DEAL.equals(processType)) && ("21".equals(dealTypeStr))) {
/* 204 */             actionList.add(new WfAction("CHASE", WfEngineUtils.getActionName("CHASE"), false));
/*     */           }
/*     */         }
/* 207 */         else if (((ASSIGN_CN.equals(flagAssignType)) || (ASSIST_CN.equals(flagAssignType))) && (2L == flagActive) && (1L == flagRecall.longValue()) && (DEAL.equals(processType)) && (
/* 208 */           (!StringUtils.isNotBlank(parentFlowId)) || (!"BEGIN".equals(prePhaseNo))))
/*     */         {
/* 211 */           actionList.add(new WfAction("CHASE", WfEngineUtils.getActionName("CHASE"), false));
/*     */         }
/*     */ 
/* 221 */         if (1L != flagActive) {
/* 222 */           actionList.add(new WfAction("SUGGEST", WfEngineUtils.getActionName("SUGGEST"), false));
/* 223 */           actionList.add(new WfAction("HASTEN", WfEngineUtils.getActionName("HASTEN"), false));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 232 */       actionList = this.engine.getAvailableActions(baseSchema, defName, null, null, inputs);
/*     */     }
/* 234 */     return actionList;
/*     */   }
/*     */ 
/*     */   public EngineModel doAction(String baseSchema, String defName, String entryType, String userId, String userType, String entryId, String actionId, String taskId, String actionType, boolean isCurrent, List<ActionInfo> acInfos, Map<String, DataField> inputs, Map<String, String> params) throws WorkflowException {
/* 238 */     if (inputs == null) {
/* 239 */       inputs = new HashMap();
/*     */     }
/*     */ 
/* 242 */     ThreadUtils.reset();
/* 243 */     DealProcessLogHandler dpLogHandler = (DealProcessLogHandler)ApplicationContextUtils.getBean("dealProcessLogHandler");
/* 244 */     inputs.put(WfEngineConstants.MAILBUFFER, new DataField(WfEngineConstants.MAILBUFFER, FieldDataType.String, ""));
/* 245 */     EngineModel wfRtn = this.engine.doAction(baseSchema, defName, entryType, userId, "USER", entryId, actionId, taskId, actionType, isCurrent, acInfos, inputs, params);
/* 246 */     String entryStateFlag = wfRtn.getEntryStateFlag();
/* 247 */     String createTaskId = wfRtn.getCreateTaskId();
/* 248 */     String entryState = wfRtn.getEntryState();
/* 249 */     String serialNum = wfRtn.getBaseSn();
/* 250 */     String topEntryId = wfRtn.getTopEntryId();
/* 251 */     String createStepCode = wfRtn.getCreateStepCode();
/* 252 */     DealProcessModel dp = null;
/* 253 */     if (WfEngineUtils.isTrans(actionType))
/*     */     {
/* 255 */       if (StringUtils.isBlank(taskId))
/*     */       {
/* 257 */         taskId = wfRtn.getCreateTaskId();
/*     */       }
/* 259 */       dp = dpLogHandler.writeTransLog(entryType, taskId, userId, actionType, inputs, false);
/* 260 */       if (("1".equals(entryStateFlag)) || ("2".equals(entryStateFlag)))
/*     */       {
/* 262 */         if ((StringUtils.isNotBlank(createTaskId)) && 
/* 263 */           (dp != null)) {
/* 264 */           String processId = dp.getProcessId();
/* 265 */           wfRtn.setCreateProcessId(processId);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 271 */       dp = dpLogHandler.writeLog(entryType, taskId, userId, actionType, acInfos, inputs);
/*     */     }
/* 273 */     if (dp != null) {
/* 274 */       long baseCreateTime = dp.getBaseCreateTime();
/* 275 */       wfRtn.setBaseCreateTime(baseCreateTime);
/*     */     }
/* 277 */     String createProcessId = wfRtn.getCreateProcessId();
/* 278 */     String mailInfo = ((DataField)inputs.get(WfEngineConstants.MAILBUFFER)).getValue();
/* 279 */     mailInfo = StringUtils.isNotBlank(mailInfo) ? mailInfo : WfEngineUtils.getMessage("defaultInfo");
/* 280 */     if (mailInfo.length() > 1500) {
/* 281 */       mailInfo = mailInfo.substring(0, 1500) + "......";
/*     */     }
/* 283 */     wfRtn.setMailContent(mailInfo);
/* 284 */     this.log.info("流程引擎返回值！serialNum=" + serialNum + ",entryState=" + entryState + ",entryStateFlag=" + entryStateFlag + ",topEntryId=" + topEntryId + ",createTaskId=" + createTaskId + ",createProcessId=" + createProcessId + ",createStepCode=" + createStepCode + ",mailInfo=\n\r" + mailInfo);
/* 285 */     return wfRtn;
/*     */   }
/*     */ 
/*     */   public WorkflowDescriptor getWorkflowDescriptor(String baseSchema, String defName)
/*     */   {
/* 298 */     return this.engine.getWorkflowDescriptor(baseSchema, defName);
/*     */   }
/*     */ 
/*     */   public List<WfStep> getNextWfSteps(String entryId, String taskId, String baseSchema, String defName, String stepCode, Map<String, DataField> inputs) throws WorkflowException {
/* 302 */     return this.engine.getNextWfSteps(entryId, taskId, baseSchema, defName, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public List<WfStep> getCurrentWfSteps(String entryId, Map<String, DataField> inputs) throws WorkflowException {
/* 306 */     return this.engine.getCurrentWfSteps(entryId, inputs);
/*     */   }
/*     */ 
/*     */   public WfStep getCurrentWfStep(String baseSchema, String entryId, String stepCode, Map<String, DataField> inputs) throws WorkflowException {
/* 310 */     return this.engine.getCurrentWfStep(baseSchema, entryId, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public ITaskManager getTaskManager() throws WorkflowException {
/* 314 */     return this.engine.getTaskManager();
/*     */   }
/*     */ 
/*     */   public WorkflowEngine getEngine() {
/* 318 */     return this.engine;
/*     */   }
/*     */ 
/*     */   public void setEngine(WorkflowEngine engine) {
/* 322 */     this.engine = engine;
/*     */   }
/*     */ 
/*     */   public IDao<WfEntry> getEntryDao() {
/* 326 */     return this.entryDao;
/*     */   }
/*     */ 
/*     */   public void setEntryDao(IDao<WfEntry> entryDao) {
/* 330 */     this.entryDao = entryDao;
/*     */   }
/*     */ 
/*     */   public BizCheck getBizCheck() {
/* 334 */     return this.bizCheck;
/*     */   }
/*     */ 
/*     */   public void setBizCheck(BizCheck bizCheck) {
/* 338 */     this.bizCheck = bizCheck;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.AbstractUltraWorkflow
 * JD-Core Version:    0.6.0
 */