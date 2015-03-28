/*      */ package com.ultrapower.workflow.engine.core;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.ultrapower.eoms.common.core.dao.IDao;
/*      */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*      */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*      */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*      */ import com.ultrapower.workflow.bizform.dpfunction.free.CanActiveStep;
/*      */ import com.ultrapower.workflow.bizform.dpfunction.free.SendMoreClassicInFunction;
/*      */ import com.ultrapower.workflow.configuration.sort.model.WfType;
/*      */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*      */ import com.ultrapower.workflow.engine.core.model.DataField;
/*      */ import com.ultrapower.workflow.engine.core.model.EngineModel;
/*      */ import com.ultrapower.workflow.engine.core.model.FieldDataType;
/*      */ import com.ultrapower.workflow.engine.core.model.WfAction;
/*      */ import com.ultrapower.workflow.engine.core.model.WfStep;
/*      */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*      */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.ActorDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.ConditionDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.EndDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.JoinDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.MetaDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.SplitDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.SplitToDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.StartDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.SubflowsDescriptor;
/*      */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*      */ import com.ultrapower.workflow.engine.store.model.Step;
/*      */ import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
/*      */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*      */ import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
/*      */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*      */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*      */ import com.ultrapower.workflow.engine.task.model.CurrentProcessTask;
/*      */ import com.ultrapower.workflow.exception.TaskException;
/*      */ import com.ultrapower.workflow.exception.WorkflowDefineException;
/*      */ import com.ultrapower.workflow.exception.WorkflowEngineException;
/*      */ import com.ultrapower.workflow.exception.WorkflowException;
/*      */ import com.ultrapower.workflow.role.model.ChildRole;
/*      */ import com.ultrapower.workflow.role.service.IRoleService;
/*      */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*      */ import com.ultrapower.workflow.utils.CommonUtils;
/*      */ import com.ultrapower.workflow.utils.RuleUtils;
/*      */ import com.ultrapower.workflow.utils.ThreadUtils;
/*      */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*      */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*      */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.collections.CollectionUtils;
/*      */ import org.apache.commons.lang.ArrayUtils;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.transaction.annotation.Transactional;
/*      */ 
/*      */ @Transactional
/*      */ public abstract class AbstractWorkflowEngine
/*      */   implements WorkflowEngine
/*      */ {
/*   75 */   private static Logger log = LoggerFactory.getLogger(AbstractWorkflowEngine.class);
/*      */   protected WorkflowFactory factory;
/*      */   protected IDao<WfEntry> entryDao;
/*      */   protected IDao<WfCurrentStep> currentDao;
/*      */   protected IDao<WfHistoryStep> historyDao;
/*      */   protected ITaskManager taskManager;
/*      */   protected IRoleService roleService;
/*      */   protected IDao<CurrentProcessTask> currTaskDao;
/*      */ 
/*      */   public WorkflowDescriptor getWorkflowDescriptor(String baseSchema, String defName)
/*      */   {
/*   90 */     if (org.apache.commons.lang.StringUtils.isBlank(defName)) {
/*   91 */       WfType wfType = WfEngineUtils.getWfTypeBySchema(baseSchema);
/*   92 */       defName = wfType.getWfDefaultVersion();
/*      */     }
/*   94 */     if (org.apache.commons.lang.StringUtils.isBlank(defName)) {
/*   95 */       throw new WorkflowDefineException("流程标识为 " + baseSchema + " 的流程类别还没有设置启用版本！");
/*      */     }
/*   97 */     return this.factory.getWorkflowDescriptor(defName);
/*      */   }
/*      */ 
/*      */   public List<WfAction> getAvailableActions(String baseSchema, String defName, String entryId, String stepCode, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*  107 */     List actions = new ArrayList();
/*  108 */     if (org.apache.commons.lang.StringUtils.isNotBlank(entryId))
/*      */     {
/*  110 */       WfEntry wfEntry = (WfEntry)this.entryDao.get(entryId);
/*  111 */       if (wfEntry != null) {
/*  112 */         String type = wfEntry.getType();
/*  113 */         if (!"FREE".equals(type))
/*      */         {
/*  116 */           StepDescriptor stDesc = getStDescById(entryId, stepCode);
/*  117 */           List actionList = stDesc.getActionList();
/*  118 */           if (CollectionUtils.isNotEmpty(actionList))
/*  119 */             for (int i = 0; i < actionList.size(); i++) {
/*  120 */               ActionDescriptor acDesc = (ActionDescriptor)actionList.get(i);
/*  121 */               actions.add(WfEngineUtils.convert(acDesc));
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*  126 */     else if (org.apache.commons.lang.StringUtils.isNotBlank(baseSchema))
/*      */     {
/*  128 */       WfType wfType = WfEngineUtils.getWfTypeBySchema(baseSchema);
/*  129 */       long wfType1 = 0L;
/*  130 */       if (wfType != null) {
/*  131 */         wfType1 = wfType.getWfType();
/*      */       }
/*  133 */       if (wfType1 == 1L) {
/*  134 */         StepDescriptor stDesc = getStartStepDescriptor(baseSchema, defName, null);
/*  135 */         List actionList = stDesc.getActionList();
/*  136 */         if (CollectionUtils.isNotEmpty(actionList)) {
/*  137 */           for (int i = 0; i < actionList.size(); i++) {
/*  138 */             ActionDescriptor acDesc = (ActionDescriptor)actionList.get(i);
/*  139 */             actions.add(WfEngineUtils.convert(acDesc));
/*      */           }
/*      */         }
/*  142 */         paseActions(actions, stDesc);
/*      */       }
/*      */       else {
/*  145 */         actions.add(new WfAction("SAVE", WfEngineUtils.getActionName("SAVE"), false));
/*  146 */         actions.add(new WfAction("ASSIGN", WfEngineUtils.getActionName("ASSIGN"), true));
/*      */ 
/*  148 */         actions.add(new WfAction("AUDIT", WfEngineUtils.getActionName("AUDIT"), true));
/*  149 */         actions.add(new WfAction("CANCEL", WfEngineUtils.getActionName("CANCEL"), false));
/*  150 */         actions.add(new WfAction("CLOSE", WfEngineUtils.getActionName("CLOSE"), false));
/*      */       }
/*      */     }
/*  153 */     return actions;
/*      */   }
/*      */ 
/*      */   private void paseActions(List<WfAction> actions, StepDescriptor stDesc)
/*      */   {
/*  162 */     if (stDesc != null) {
/*  163 */       List metaList = stDesc.getMetaList();
/*  164 */       if (CollectionUtils.isNotEmpty(metaList))
/*  165 */         for (int i = 0; i < metaList.size(); i++) {
/*  166 */           MetaDescriptor mtDesc = (MetaDescriptor)metaList.get(i);
/*  167 */           if (mtDesc != null) {
/*  168 */             String name = mtDesc.getName();
/*  169 */             String value = CommonUtils.getString(mtDesc.getValue());
/*  170 */             if (WfEngineConstants.CUSTOM_ACTIONS.equals(name)) {
/*  171 */               if (org.apache.commons.lang.StringUtils.isNotBlank(value)) {
/*  172 */                 String[] split = value.split(";");
/*  173 */                 if (!ArrayUtils.isEmpty(split)) {
/*  174 */                   for (int j = 0; j < split.length; j++) {
/*  175 */                     if (org.apache.commons.lang.StringUtils.isNotBlank(split[j])) {
/*  176 */                       String[] split2 = split[j].split("=");
/*  177 */                       String actionName = split2[0];
/*  178 */                       String page = "";
/*  179 */                       if (split2.length > 1) {
/*  180 */                         page = split2[1];
/*      */                       }
/*  182 */                       if (org.apache.commons.lang.StringUtils.isNotBlank(actionName))
/*  183 */                         actions.add(new WfAction("NEXT", actionName, page));
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*  189 */             else if ("1".equals(value))
/*  190 */               if (WfEngineConstants.flagSave.equals(name))
/*  191 */                 actions.add(new WfAction("SAVE", WfEngineUtils.getActionName("SAVE"), false));
/*  192 */               else if (WfEngineConstants.flagAssign.equals(name))
/*  193 */                 actions.add(new WfAction("ASSIGN", WfEngineUtils.getActionName("ASSIGN"), true));
/*  194 */               else if (!WfEngineConstants.flagAssist.equals(name))
/*  195 */                 if (WfEngineConstants.flagCopy.equals(name)) {
/*  196 */                   actions.add(new WfAction("MAKECOPY", WfEngineUtils.getActionName("MAKECOPY"), true)); } else {
/*  197 */                   if ((WfEngineConstants.flagTurnUp.equals(name)) || 
/*  198 */                     (WfEngineConstants.flagRecall.equals(name))) continue;
/*  199 */                   if (WfEngineConstants.flagClose.equals(name))
/*  200 */                     actions.add(new WfAction("CLOSE", WfEngineUtils.getActionName("CLOSE"), false));
/*  201 */                   else if (WfEngineConstants.flagCancel.equals(name))
/*  202 */                     actions.add(new WfAction("CANCEL", WfEngineUtils.getActionName("CANCEL"), false));
/*  203 */                   else if (!WfEngineConstants.flagTurnDown.equals(name))
/*  204 */                     if (WfEngineConstants.flagToAuditing.equals(name))
/*  205 */                       actions.add(new WfAction("AUDIT", WfEngineUtils.getActionName("AUDIT"), true));
/*  206 */                     else WfEngineConstants.flagStartInsideFlow.equals(name);
/*      */                 }
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<WfStep> getCurrentWfSteps(String entryId, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*  219 */     List wfSteps = new ArrayList();
/*  220 */     if (isFixFlow(entryId)) {
/*  221 */       List currentSteps = getCurrentSteps(entryId);
/*  222 */       if (CollectionUtils.isNotEmpty(currentSteps)) {
/*  223 */         for (int i = 0; i < currentSteps.size(); i++) {
/*  224 */           WfCurrentStep wfCurrentStep = (WfCurrentStep)currentSteps.get(i);
/*  225 */           String stepCode = wfCurrentStep.getStepCode();
/*  226 */           StepDescriptor stDesc = getStDescById(entryId, stepCode);
/*  227 */           wfSteps.add(WfEngineUtils.convert(stDesc, null, null));
/*      */         }
/*      */       }
/*      */     }
/*  231 */     return wfSteps;
/*      */   }
/*      */ 
/*      */   public WfStep getCurrentWfStep(String baseSchema, String entryId, String stepCode, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*  238 */     WfStep wfStep = null;
/*  239 */     if (org.apache.commons.lang.StringUtils.isBlank(entryId)) {
/*  240 */       wfStep = WfEngineUtils.convert(getStartStepDescriptor(baseSchema, null, null), null, inputs);
/*  241 */     } else if (isFixFlow(entryId)) {
/*  242 */       List currentSteps = getCurrentSteps(entryId);
/*  243 */       if (CollectionUtils.isNotEmpty(currentSteps)) {
/*  244 */         for (int i = 0; i < currentSteps.size(); i++) {
/*  245 */           WfCurrentStep wfCurrentStep = (WfCurrentStep)currentSteps.get(i);
/*  246 */           String stCode = wfCurrentStep.getStepCode();
/*  247 */           if (stCode.equals(stepCode)) {
/*  248 */             StepDescriptor stDesc = getStDescById(entryId, stepCode);
/*  249 */             wfStep = WfEngineUtils.convert(stDesc, null, inputs);
/*  250 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  255 */     return wfStep;
/*      */   }
/*      */ 
/*      */   protected StepDescriptor getStartStepDescriptor(String baseSchema, String defName, String entryId)
/*      */   {
/*  266 */     StepDescriptor stDesc = null;
/*  267 */     if (org.apache.commons.lang.StringUtils.isNotBlank(defName)) {
/*  268 */       StartDescriptor startDesc = getStartDesc(defName);
/*  269 */       if (startDesc != null) {
/*  270 */         String to = startDesc.getTo();
/*  271 */         stDesc = getStDesc(defName, to);
/*      */       }
/*  273 */     } else if (org.apache.commons.lang.StringUtils.isNotBlank(entryId)) {
/*  274 */       WorkflowDescriptor wfDesc = getWfDescById(entryId);
/*  275 */       defName = wfDesc.getName();
/*  276 */       StartDescriptor startDesc = getStartDesc(defName);
/*  277 */       if (startDesc != null) {
/*  278 */         String to = startDesc.getTo();
/*  279 */         stDesc = getStDesc(defName, to);
/*      */       }
/*  281 */     } else if (org.apache.commons.lang.StringUtils.isNotBlank(baseSchema)) {
/*  282 */       String defVersionBySchema = WfEngineUtils.getDefVersionBySchema(baseSchema);
/*  283 */       if (org.apache.commons.lang.StringUtils.isNotBlank(defVersionBySchema)) {
/*  284 */         defName = defVersionBySchema;
/*  285 */         StartDescriptor startDesc = getStartDesc(defName);
/*  286 */         if (startDesc != null) {
/*  287 */           String to = startDesc.getTo();
/*  288 */           stDesc = getStDesc(defName, to);
/*      */         }
/*      */       }
/*      */     }
/*  292 */     return stDesc;
/*      */   }
/*      */ 
/*      */   public List<WfStep> getNextWfSteps(String entryId, String taskId, String baseSchema, String defName, String stepCode, Map<String, DataField> inputs)
/*      */   {
/*  299 */     StepDescriptor stDesc = null;
/*  300 */     List nextWfSteps = new ArrayList();
/*  301 */     WorkflowDescriptor wfDesc = null;
/*  302 */     if (org.apache.commons.lang.StringUtils.isNotBlank(entryId)) {
/*  303 */       wfDesc = getWfDescById(entryId);
/*  304 */     } else if (org.apache.commons.lang.StringUtils.isNotBlank(defName)) {
/*  305 */       wfDesc = getWfDesc(defName);
/*      */     } else {
/*  307 */       defName = WfEngineUtils.getDefVersionBySchema(baseSchema);
/*  308 */       wfDesc = getWfDesc(defName);
/*      */     }
/*  310 */     if (org.apache.commons.lang.StringUtils.isBlank(stepCode))
/*  311 */       stDesc = getStartStepDescriptor(baseSchema, defName, null);
/*      */     else {
/*  313 */       stDesc = wfDesc.getStepDescriptor(stepCode);
/*      */     }
/*  315 */     if ((org.apache.commons.lang.StringUtils.isBlank(defName)) && (wfDesc != null)) {
/*  316 */       defName = wfDesc.getName();
/*      */     }
/*  318 */     ActionDescriptor acDesc = getAcDesc(stDesc, null);
/*  319 */     if (acDesc != null) {
/*  320 */       String tarStepCode = acDesc.getToStepId();
/*  321 */       String tarSplitCode = acDesc.getToSplitId();
/*  322 */       String tarJoinId = acDesc.getToJoinId();
/*  323 */       String tarEndCode = acDesc.getToEndId();
/*  324 */       if (org.apache.commons.lang.StringUtils.isNotBlank(tarSplitCode)) {
/*  325 */         SplitDescriptor spDesc = getSpDesc(defName, tarSplitCode);
/*  326 */         if (spDesc != null) {
/*  327 */           String conText = spDesc.getConDesc().getText();
/*  328 */           Map result = new LinkedHashMap();
/*  329 */           Map toResult = new LinkedHashMap();
/*  330 */           List tmpList = new ArrayList();
/*  331 */           parseCondition(entryId, defName, inputs, spDesc, conText, result, toResult, tmpList);
/*  332 */           if (result != null) {
/*  333 */             Set keySet = result.keySet();
/*  334 */             Iterator it = keySet.iterator();
/*  335 */             while (it.hasNext()) {
/*  336 */               String phaseNo = (String)it.next();
/*  337 */               AbstractDescriptor absDesc = (AbstractDescriptor)result.get(phaseNo);
/*  338 */               if ((absDesc instanceof StepDescriptor)) {
/*  339 */                 nextWfSteps.add(WfEngineUtils.convert((StepDescriptor)absDesc, taskId, inputs));
/*  340 */               } else if ((absDesc instanceof JoinDescriptor)) {
/*  341 */                 JoinDescriptor jnDesc = (JoinDescriptor)absDesc;
/*  342 */                 getJoinNextSteps(entryId, defName, taskId, jnDesc, nextWfSteps, inputs);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  347 */       } else if (org.apache.commons.lang.StringUtils.isNotBlank(tarStepCode)) {
/*  348 */         StepDescriptor nextStDesc = getStDesc(defName, tarStepCode);
/*  349 */         if (nextStDesc != null)
/*  350 */           nextWfSteps.add(WfEngineUtils.convert(nextStDesc, taskId, inputs));
/*      */       }
/*  352 */       else if (!org.apache.commons.lang.StringUtils.isNotBlank(tarEndCode))
/*      */       {
/*  355 */         if (org.apache.commons.lang.StringUtils.isNotBlank(tarJoinId)) {
/*  356 */           JoinDescriptor jnDesc = getJnDesc(defName, tarJoinId);
/*  357 */           getJoinNextSteps(entryId, defName, taskId, jnDesc, nextWfSteps, inputs);
/*      */         }
/*      */       }
/*      */     }
/*  361 */     return nextWfSteps;
/*      */   }
/*      */ 
/*      */   protected void getJoinNextSteps(String entryId, String defName, String taskId, JoinDescriptor jnDesc, List<WfStep> nextWfSteps, Map<String, DataField> inputs)
/*      */   {
/*  374 */     if (jnDesc != null) {
/*  375 */       String stepId = jnDesc.getStepId();
/*  376 */       String splitId = jnDesc.getSplitId();
/*  377 */       String endId = jnDesc.getEndId();
/*  378 */       if (org.apache.commons.lang.StringUtils.isNotBlank(stepId)) {
/*  379 */         StepDescriptor nextStDesc = getStDesc(defName, stepId);
/*  380 */         if (nextStDesc != null)
/*  381 */           nextWfSteps.add(WfEngineUtils.convert(nextStDesc, taskId, inputs));
/*      */       }
/*  383 */       else if (org.apache.commons.lang.StringUtils.isNotBlank(splitId)) {
/*  384 */         SplitDescriptor spDesc = getSpDesc(defName, splitId);
/*  385 */         if (spDesc != null) {
/*  386 */           String conText = spDesc.getConDesc().getText();
/*  387 */           Map result = new LinkedHashMap();
/*  388 */           Map toResult = new LinkedHashMap();
/*  389 */           List tmpList = new ArrayList();
/*  390 */           parseCondition(entryId, defName, inputs, spDesc, conText, result, toResult, tmpList);
/*  391 */           if (result != null) {
/*  392 */             Set keySet = result.keySet();
/*  393 */             Iterator it = keySet.iterator();
/*  394 */             while (it.hasNext()) {
/*  395 */               String phaseNo = (String)it.next();
/*  396 */               AbstractDescriptor absDesc = (AbstractDescriptor)result.get(phaseNo);
/*  397 */               if ((absDesc instanceof StepDescriptor)) {
/*  398 */                 nextWfSteps.add(WfEngineUtils.convert((StepDescriptor)absDesc, taskId, inputs));
/*  399 */               } else if ((absDesc instanceof JoinDescriptor)) {
/*  400 */                 JoinDescriptor jnD = (JoinDescriptor)absDesc;
/*  401 */                 getJoinNextSteps(entryId, defName, taskId, jnDesc, nextWfSteps, inputs);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public EngineModel subDoAction(String baseSchema, String defName, String entryType, String actorId, String actorType, String entryId, String actionId, String taskId, String actionType, boolean isCurrent, List<ActionInfo> acInfos, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*  429 */     BaseTask initTask = null;
/*  430 */     String entryStateFlag = "0";
/*  431 */     if ((org.apache.commons.lang.StringUtils.isNotBlank(entryId)) && ("TERMINATE".equals(actionType))) {
/*  432 */       log.info("流程实例终止! entryId=" + entryId);
/*  433 */       terminate(actorId, actionType, entryId, inputs);
/*  434 */       if (org.apache.commons.lang.StringUtils.isBlank(taskId)) {
/*  435 */         List historyTasks = this.taskManager.queryHistoryTask("from HistoryProcessTask where topEntryId='" + entryId + "'");
/*  436 */         if (CollectionUtils.isNotEmpty(historyTasks)) {
/*  437 */           BaseTask baseTask = (BaseTask)historyTasks.get(0);
/*  438 */           taskId = baseTask.getId();
/*      */         }
/*      */       }
/*      */     }
/*  442 */     else if (WfEngineUtils.isTrans(actionType)) {
/*  443 */       if (org.apache.commons.lang.StringUtils.isBlank(taskId)) {
/*  444 */         if (org.apache.commons.lang.StringUtils.isNotBlank(baseSchema)) {
/*  445 */           WfType wfType = WfEngineUtils.getWfTypeBySchema(baseSchema);
/*  446 */           if (wfType != null) {
/*  447 */             long type = wfType.getWfType();
/*  448 */             entryType = type == 1L ? "DEFINE" : "FREE";
/*  449 */             defName = wfType.getWfDefaultVersion();
/*  450 */             String baseName = wfType.getName();
/*  451 */             inputs.put(WfEngineConstants.INPUTS_BASESCHEMA, new DataField(WfEngineConstants.INPUTS_BASESCHEMA, FieldDataType.String, baseSchema));
/*  452 */             inputs.put(WfEngineConstants.INPUTS_BASENAME, new DataField(WfEngineConstants.INPUTS_BASENAME, FieldDataType.String, baseName));
/*  453 */             log.info("流程标识(baseSchema=" + baseSchema + ")不为空！通过流程标识获取baseName=" + baseName + ",defName=" + defName);
/*      */           }
/*      */         }
/*      */ 
/*  457 */         initTask = init(defName, entryType, actorId, actorType, inputs);
/*  458 */         taskId = initTask.getId();
/*  459 */         entryStateFlag = "1";
/*      */       }
/*      */ 
/*  462 */       transWorkflow(actorId, actionId, taskId, actionType, isCurrent, acInfos, inputs);
/*      */     }
/*      */ 
/*  466 */     BaseTask curTask = null;
/*  467 */     if (curTask == null) {
/*  468 */       curTask = this.taskManager.getTaskById(taskId);
/*      */     }
/*  470 */     String topEntryId = curTask.getTopEntryId();
/*  471 */     String entryState = curTask.getEntryState();
/*  472 */     String serialNum = curTask.getSerialNum();
/*  473 */     String stepCode = curTask.getStepCode();
/*  474 */     WfEntry topEntry = (WfEntry)this.entryDao.get(topEntryId);
/*  475 */     long closeTime = topEntry.getCloseTime();
/*  476 */     EngineModel rtnModel = new EngineModel();
/*  477 */     rtnModel.setEntryState(entryState);
/*      */ 
/*  479 */     rtnModel.setTopEntryId(topEntryId);
/*  480 */     rtnModel.setBaseSn(serialNum);
/*  481 */     if (initTask != null) {
/*  482 */       rtnModel.setCreateTaskId(taskId);
/*  483 */       rtnModel.setCreateStepCode(stepCode);
/*      */     }
/*  485 */     if (closeTime > 0L) {
/*  486 */       entryStateFlag = "2";
/*  487 */       rtnModel.setBaseCloseTime(closeTime);
/*      */     }
/*  489 */     rtnModel.setEntryStateFlag(entryStateFlag);
/*  490 */     rtnModel.setNewTasks(ThreadUtils.getNewTasks());
/*  491 */     rtnModel.setOldTasks(ThreadUtils.getOldTasks());
/*  492 */     return rtnModel;
/*      */   }
/*      */ 
/*      */   public EngineModel doAction(String baseSchema, String defName, String entryType, String actorId, String actorType, String entryId, String actionId, String taskId, String actionType, boolean isCurrent, List<ActionInfo> acInfos, Map<String, DataField> inputs, Map<String, String> params)
/*      */     throws WorkflowException
/*      */   {
/*  520 */     String oldEntryState = null;
/*  521 */     String oldStepCode = null;
/*  522 */     String oldStepState = null;
/*  523 */     if (org.apache.commons.lang.StringUtils.isNotBlank(taskId)) {
/*  524 */       BaseTask task = this.taskManager.getTaskById(taskId);
/*  525 */       if (task != null) {
/*  526 */         oldEntryState = task.getEntryState();
/*  527 */         oldStepCode = task.getStepCode();
/*  528 */         oldStepState = task.getProcessState();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  533 */     doExtendAction(actorId, entryId, taskId, params);
/*      */ 
/*  535 */     boolean joinFlag = true;
/*  536 */     BaseTask initTask = null;
/*  537 */     String entryStateFlag = "0";
/*  538 */     if ((org.apache.commons.lang.StringUtils.isNotBlank(entryId)) && ("TERMINATE".equals(actionType))) {
/*  539 */       log.info("流程实例终止! entryId=" + entryId);
/*  540 */       terminate(actorId, actionType, entryId, inputs);
/*  541 */       if (org.apache.commons.lang.StringUtils.isBlank(taskId)) {
/*  542 */         List historyTasks = this.taskManager.queryHistoryTask("from HistoryProcessTask where topEntryId='" + entryId + "'");
/*  543 */         if (CollectionUtils.isNotEmpty(historyTasks)) {
/*  544 */           BaseTask baseTask = (BaseTask)historyTasks.get(0);
/*  545 */           taskId = baseTask.getId();
/*      */         }
/*      */       }
/*      */     }
/*  549 */     else if ("RENEXT".equals(actionType)) {
/*  550 */       log.info("固定转派! entryId=" + entryId);
/*  551 */       BaseTask baseTask = this.taskManager.getTaskById(taskId);
/*  552 */       if (baseTask != null) {
/*  553 */         Step curStep = (Step)this.currentDao.get(baseTask.getStepId());
/*  554 */         reNext(curStep, baseTask, actorId, acInfos, inputs);
/*      */       }
/*      */     }
/*  557 */     else if (WfEngineUtils.isTrans(actionType)) {
/*  558 */       if (org.apache.commons.lang.StringUtils.isBlank(taskId)) {
/*  559 */         if (org.apache.commons.lang.StringUtils.isNotBlank(baseSchema)) {
/*  560 */           WfType wfType = WfEngineUtils.getWfTypeBySchema(baseSchema);
/*  561 */           if (wfType != null) {
/*  562 */             long type = wfType.getWfType();
/*  563 */             entryType = type == 1L ? "DEFINE" : "FREE";
/*  564 */             defName = wfType.getWfDefaultVersion();
/*  565 */             String baseName = wfType.getName();
/*  566 */             inputs.put(WfEngineConstants.INPUTS_BASESCHEMA, new DataField(WfEngineConstants.INPUTS_BASESCHEMA, FieldDataType.String, baseSchema));
/*  567 */             inputs.put(WfEngineConstants.INPUTS_BASENAME, new DataField(WfEngineConstants.INPUTS_BASENAME, FieldDataType.String, baseName));
/*  568 */             log.info("流程标识(baseSchema=" + baseSchema + ")不为空！通过流程标识获取baseName=" + baseName + ",defName=" + defName);
/*      */           } else {
/*  570 */             throw new WorkflowDefineException("baseSchema=" + baseSchema + "的流程类别不存在！");
/*      */           }
/*      */         }
/*      */ 
/*  574 */         initTask = init(defName, entryType, actorId, actorType, inputs);
/*  575 */         taskId = initTask.getId();
/*  576 */         entryStateFlag = "1";
/*      */       }
/*      */ 
/*  579 */       joinFlag = transWorkflow(actorId, actionId, taskId, actionType, isCurrent, acInfos, inputs);
/*      */     }
/*      */ 
/*  583 */     BaseTask curTask = null;
/*      */ 
/*  585 */     if (WfEngineUtils.isTrans(actionType)) {
/*  586 */       BaseTask templateTask = this.taskManager.getTaskById(taskId);
/*  587 */       if (templateTask == null) {
/*  588 */         throw new WorkflowException("任务不存在，无法更新工单状态！taskId=" + taskId);
/*      */       }
/*  590 */       String stepId = templateTask.getStepId();
/*  591 */       Step wfStep = (Step)this.historyDao.get(stepId);
/*  592 */       if (wfStep == null) {
/*  593 */         wfStep = (Step)this.currentDao.get(stepId);
/*      */       }
/*  595 */       if ((wfStep != null) && (WfEngineUtils.isTransStep(wfStep))) {
/*  596 */         curTask = updateEntryState(templateTask, actionType, inputs, joinFlag);
/*      */       }
/*      */     }
/*      */ 
/*  600 */     if (curTask == null) {
/*  601 */       curTask = this.taskManager.getTaskById(taskId);
/*      */     }
/*  603 */     String topEntryId = curTask.getTopEntryId();
/*  604 */     String entryState = curTask.getEntryState();
/*  605 */     String serialNum = curTask.getSerialNum();
/*  606 */     String stepCode = curTask.getStepCode();
/*  607 */     String newStepState = curTask.getProcessState();
/*  608 */     String baseId = curTask.getBaseId();
/*  609 */     WfEntry topEntry = (WfEntry)this.entryDao.get(topEntryId);
/*  610 */     long closeTime = topEntry.getCloseTime();
/*      */ 
/*  612 */     EngineModel rtnModel = new EngineModel();
/*  613 */     rtnModel.setEntryState(entryState);
/*  614 */     rtnModel.setTopEntryId(topEntryId);
/*  615 */     rtnModel.setBaseSn(serialNum);
/*  616 */     if (initTask != null) {
/*  617 */       rtnModel.setCreateTaskId(taskId);
/*  618 */       rtnModel.setCreateStepCode(stepCode);
/*      */     }
/*  620 */     if (closeTime > 0L) {
/*  621 */       entryStateFlag = "2";
/*  622 */       rtnModel.setBaseCloseTime(closeTime);
/*      */     }
/*  624 */     List newTasks = ThreadUtils.getNewTasks();
/*  625 */     List oldTasks = ThreadUtils.getOldTasks();
/*  626 */     rtnModel.setEntryStateFlag(entryStateFlag);
/*  627 */     rtnModel.setNewTasks(WfEngineUtils.updateCacheEntryState(newTasks, entryState));
/*  628 */     rtnModel.setOldTasks(WfEngineUtils.updateCacheEntryState(oldTasks, entryState));
/*      */ 
/*  639 */     return rtnModel;
/*      */   }
/*      */ 
/*      */   public void doExtendAction(String userId, String entryId, String taskId, Map<String, String> params)
/*      */   {
/*      */   }
/*      */ 
/*      */   protected BaseTask init(String defName, String entryType, String actorId, String actorType, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*  664 */     String parEntryId = CommonUtils.getVal(inputs, "parent_entryId");
/*  665 */     String topEntryId = CommonUtils.getVal(inputs, "top_entryId");
/*  666 */     String parFlowId = CommonUtils.getVal(inputs, "parent_flowId");
/*  667 */     String topFlowId = CommonUtils.getVal(inputs, "top_flowId");
/*  668 */     WfEntry entry = new WfEntry(defName, entryType, parEntryId, topEntryId, parFlowId);
/*  669 */     this.entryDao.save(entry);
/*  670 */     String entryId = entry.getId();
/*  671 */     if (org.apache.commons.lang.StringUtils.isBlank(topEntryId))
/*  672 */       entry.setTopEntryId(entryId);
/*      */     else {
/*  674 */       entry.setTopEntryId(topEntryId);
/*      */     }
/*  676 */     if (org.apache.commons.lang.StringUtils.isBlank(topFlowId))
/*  677 */       entry.setTopFlowId(parFlowId);
/*      */     else {
/*  679 */       entry.setTopFlowId(topFlowId);
/*      */     }
/*      */ 
/*  682 */     BaseTask task = null;
/*  683 */     String acCode = "NEW";
/*  684 */     String acCnName = WfEngineUtils.getActionName(acCode);
/*  685 */     ActionInfo ac = new ActionInfo(actorId, actorType, null, acCode, acCnName);
/*  686 */     if (entryType.equals("DEFINE")) {
/*  687 */       log.info("固定流程实例初始化！defName:" + defName);
/*  688 */       StartDescriptor startDesc = getStartDesc(defName);
/*  689 */       String to = startDesc.getTo();
/*  690 */       StepDescriptor stDesc = getWfDesc(defName).getStepDescriptor(to);
/*  691 */       if (stDesc != null) {
/*  692 */         String thisStepId = stDesc.getId();
/*  693 */         String type = stDesc.getType();
/*  694 */         WfCurrentStep step = new WfCurrentStep(entryId, thisStepId, "BEGIN", "BEGIN", "BEGIN", "BEGIN", null, null, type);
/*  695 */         step.setName(stDesc.getDesc());
/*  696 */         step.setStepNo(stDesc.getStepNo());
/*  697 */         step.setStepGroup(stDesc.getStepGroup());
/*  698 */         this.currentDao.save(step);
/*  699 */         task = this.taskManager.assignTask(null, null, step, stDesc, ac, inputs);
/*  700 */         log.info(actorId + " " + acCnName);
/*  701 */         setDefProcessState(stDesc, ac, task);
/*  702 */         WfEngineUtils.executeFunction(stDesc.getPreFuncList(), actorId, null, task, step, stDesc, ac, inputs);
/*      */       }
/*      */       else {
/*  705 */         SplitDescriptor spDesc = getWfDescById(entryId).getSplitDescriptor(to);
/*  706 */         if (spDesc != null) {
/*  707 */           String thisStepId = spDesc.getId();
/*  708 */           String type = spDesc.getType();
/*  709 */           WfCurrentStep step = new WfCurrentStep(entryId, thisStepId, "BEGIN", "BEGIN", "BEGIN", "BEGIN", null, thisStepId, type);
/*      */ 
/*  711 */           String parentStepGroup = CommonUtils.getVal(inputs, "parent_stepGroup");
/*  712 */           if (org.apache.commons.lang.StringUtils.isNotBlank(parentStepGroup)) {
/*  713 */             step.setStepGroup(parentStepGroup);
/*      */           }
/*  715 */           this.currentDao.save(step);
/*  716 */           task = this.taskManager.assignTask(null, null, step, null, ac, inputs);
/*  717 */           IDefFunction func = (IDefFunction)ApplicationContextUtils.getBean("defaultDefInFunction");
/*  718 */           func.execute(actorId, null, task, step, null, spDesc, ac, inputs);
/*      */         }
/*      */       }
/*  721 */     } else if (entryType.equals("FREE")) {
/*  722 */       log.info("自由流程实例初始化！defName:" + defName);
/*  723 */       WfCurrentStep step = new WfCurrentStep(entryId, "BEGIN", "BEGIN", "BEGIN", "BEGIN", null, null, "DEAL");
/*      */ 
/*  725 */       String parentStepGroup = CommonUtils.getVal(inputs, "parent_stepGroup");
/*  726 */       if (org.apache.commons.lang.StringUtils.isNotBlank(parentStepGroup)) {
/*  727 */         step.setStepGroup(parentStepGroup);
/*      */       }
/*  729 */       this.currentDao.save(step);
/*  730 */       task = this.taskManager.assignTask(null, null, step, null, ac, inputs);
/*  731 */       log.info(actorId + " " + acCnName);
/*  732 */       task.setEntryState(WfEngineUtils.entryState(acCode));
/*  733 */       task.setProcessState(WfEngineUtils.inProcessState(acCode));
/*  734 */       WfEngineUtils.getDefaultFreePreFunction(ac).execute(actorId, null, task, step, ac, inputs);
/*      */     }
/*  736 */     return task;
/*      */   }
/*      */ 
/*      */   protected boolean transWorkflow(String userId, String actionId, String taskId, String actionType, boolean isCurrent, List<ActionInfo> acInfos, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*  754 */     String entryType = null;
/*  755 */     BaseTask curTask = null;
/*  756 */     Step curStep = null;
/*  757 */     if (isCurrent)
/*  758 */       curTask = this.taskManager.getCurrentTaskById(taskId);
/*      */     else {
/*  760 */       curTask = this.taskManager.getHistoryTaskById(taskId);
/*      */     }
/*  762 */     if (curTask == null) {
/*  763 */       throw new WorkflowException("任务不存在！taskId:" + taskId + ",isCurrent:" + isCurrent);
/*      */     }
/*  765 */     String stepId = curTask.getStepId();
/*  766 */     String entryId = curTask.getEntryId();
/*  767 */     if (isCurrent)
/*  768 */       curStep = (Step)this.currentDao.get(stepId);
/*      */     else {
/*  770 */       curStep = (Step)this.historyDao.get(stepId);
/*      */     }
/*  772 */     if (curStep == null) {
/*  773 */       throw new WorkflowException("step不存在！stepId:" + stepId + ",isCurrent:" + isCurrent);
/*      */     }
/*      */ 
/*  777 */     if (WfEngineUtils.isTransStep(curStep)) {
/*  778 */       checkEntry(entryId, actionId, actionType);
/*      */     }
/*  780 */     boolean joinFlag = true;
/*  781 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/*  782 */     entryType = entry.getType();
/*      */     try {
/*  784 */       if (entryType.equals("DEFINE"))
/*      */       {
/*  786 */         joinFlag = fixTrans(actionType, actionId, userId, acInfos, inputs, curTask, curStep);
/*  787 */       } else if (entryType.equals("FREE"))
/*      */       {
/*  789 */         freeTrans(actionType, userId, acInfos, inputs, curTask, curStep);
/*  790 */         DataField joinFlagDF = (DataField)inputs.get("sendToJoin_JoinFlag");
/*  791 */         if (joinFlagDF != null)
/*      */         {
/*  793 */           joinFlag = Boolean.valueOf(joinFlagDF.getValue()).booleanValue();
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/*  797 */       log.error(e.getMessage(), e);
/*  798 */       throw new WorkflowException(e.getMessage(), e);
/*      */     }
/*  800 */     return joinFlag;
/*      */   }
/*      */ 
/*      */   protected boolean fixTrans(String actionType, String actionId, String userId, List<ActionInfo> acAllList, Map<String, DataField> inputs, BaseTask curTask, Step curStep)
/*      */     throws WorkflowException
/*      */   {
/*  815 */     String taskId = curTask.getId();
/*  816 */     String entryId = curTask.getEntryId();
/*  817 */     log.info("固定流程流转 actionType=" + actionType + ",actionName=" + WfEngineUtils.getActionName(actionType));
/*  818 */     if (CollectionUtils.isEmpty(acAllList)) {
/*  819 */       acAllList = new ArrayList();
/*      */     }
/*  821 */     boolean joinFlag = true;
/*      */ 
/*  823 */     if (("ACCEPT".equals(actionType)) || 
/*  824 */       ("SAVE".equals(actionType)) || 
/*  825 */       ("NEW".equals(actionType)) || 
/*  826 */       ("NOTICE".equals(actionType)) || 
/*  827 */       ("CANCEL".equals(actionType)) || 
/*  828 */       ("CLOSE".equals(actionType)) || 
/*  829 */       ("SUSPEND".equals(actionType)) || 
/*  830 */       ("ACTIVE".equals(actionType)) || 
/*  831 */       ("REJECT".equals(actionType)) || 
/*  832 */       ("CHASE".equals(actionType))) {
/*  833 */       freeTrans(actionType, userId, acAllList, inputs, curTask, curStep);
/*      */     } else {
/*  835 */       if (("NEXT".equals(actionType)) && (WfEngineUtils.isNextAndCopy(acAllList)))
/*      */       {
/*  837 */         List copyAcList = WfEngineUtils.getActionInfoList(acAllList, "MAKECOPY", false, null, null);
/*  838 */         affectTrans("MAKECOPY", actionId, userId, inputs, curTask, curStep, copyAcList);
/*      */       }
/*      */ 
/*  841 */       joinFlag = affectTrans(actionType, actionId, userId, inputs, curTask, curStep, acAllList);
/*      */     }
/*  843 */     return joinFlag;
/*      */   }
/*      */ 
/*      */   protected boolean affectTrans(String actionType, String actionId, String userId, Map<String, DataField> inputs, BaseTask curTask, Step curStep, List<ActionInfo> acTransList)
/*      */     throws TaskException, WorkflowDefineException
/*      */   {
/*  860 */     String currStepCode = curStep.getStepCode();
/*  861 */     String entryId = curStep.getEntryId();
/*  862 */     StepDescriptor stDesc = getWfDescById(entryId).getStepDescriptor(currStepCode);
/*  863 */     ActionDescriptor acDesc = getAcDesc(stDesc, actionId);
/*  864 */     SplitDescriptor spDesc = getWfDescById(entryId).getSplitDescriptor(currStepCode);
/*  865 */     if (!"MAKECOPY".equals(actionType))
/*      */     {
/*  867 */       finishOld(actionType, userId, inputs, curTask, curStep, stDesc);
/*      */     }
/*  869 */     boolean joinFlag = true;
/*  870 */     List trace = new ArrayList();
/*  871 */     if (stDesc != null) {
/*  872 */       String tarStepCode = null;
/*  873 */       String tarSplitCode = null;
/*  874 */       String tarJoinCode = null;
/*  875 */       String tarEndCode = null;
/*  876 */       int remap = 0;
/*  877 */       if (isInsideAction(entryId, currStepCode, actionType))
/*      */       {
/*  879 */         tarStepCode = currStepCode;
/*      */       }
/*  881 */       else if (acDesc != null) {
/*  882 */         tarStepCode = acDesc.getToStepId();
/*  883 */         tarSplitCode = acDesc.getToSplitId();
/*  884 */         tarJoinCode = acDesc.getToJoinId();
/*  885 */         tarEndCode = acDesc.getToEndId();
/*  886 */         remap = acDesc.getRemap();
/*      */       }
/*      */ 
/*  890 */       if (org.apache.commons.lang.StringUtils.isNotBlank(tarStepCode)) {
/*  891 */         log.info("动作的目标是step");
/*      */ 
/*  893 */         sendToStep(actionType, userId, curStep, curTask, acDesc, remap, tarStepCode, acTransList, inputs, trace);
/*  894 */       } else if (org.apache.commons.lang.StringUtils.isNotBlank(tarSplitCode))
/*      */       {
/*  896 */         sendToSplit(actionType, userId, inputs, curTask, acDesc, curStep, acTransList, tarSplitCode, trace);
/*  897 */       } else if (org.apache.commons.lang.StringUtils.isNotBlank(tarJoinCode))
/*      */       {
/*  899 */         joinFlag = sendToJoin(userId, inputs, curTask, acDesc, curStep, tarJoinCode, acTransList, trace);
/*  900 */       } else if (org.apache.commons.lang.StringUtils.isNotBlank(tarEndCode))
/*      */       {
/*  902 */         sendToEnd(userId, inputs, entryId, acDesc, tarEndCode, acTransList, trace);
/*      */       }
/*  904 */     } else if (spDesc != null) {
/*  905 */       sendToSplit(actionType, userId, inputs, curTask, acDesc, curStep, acTransList, currStepCode, trace);
/*      */     }
/*  907 */     return joinFlag;
/*      */   }
/*      */ 
/*      */   public String getActionTypeByDealType(String entryId, String tarStepCode, String actionType)
/*      */   {
/*  918 */     String rtnActionType = actionType;
/*  919 */     if (org.apache.commons.lang.StringUtils.isNotBlank(tarStepCode)) {
/*  920 */       StepDescriptor tarStDesc = getStDescById(entryId, tarStepCode);
/*  921 */       boolean tarIsAuto = tarStDesc.isAuto();
/*  922 */       boolean isDefSub = false;
/*  923 */       String dealType = tarStDesc.getType();
/*  924 */       SubflowsDescriptor subflows = tarStDesc.getSubflows();
/*  925 */       if (subflows != null) {
/*  926 */         Map subflowMap = subflows.getSubflowMap();
/*  927 */         if ((subflowMap != null) && (CollectionUtils.isNotEmpty((Collection)subflowMap.keySet()))) {
/*  928 */           isDefSub = true;
/*      */         }
/*      */       }
/*  931 */       if ((tarIsAuto) && (!isDefSub)) {
/*  932 */         if ("AUDITING".equals(dealType))
/*  933 */           rtnActionType = "AUDIT";
/*  934 */         else if ("DEAL".equals(dealType))
/*  935 */           rtnActionType = "ASSIGN";
/*  936 */         else if ("ASSIST".equals(dealType)) {
/*  937 */           rtnActionType = "ASSIST";
/*      */         }
/*      */       }
/*  940 */       log.info("通过流程环节类型(dealType)，取得对应动作! actionType=" + rtnActionType + ",stepCode=" + tarStepCode);
/*      */     }
/*  942 */     return rtnActionType;
/*      */   }
/*      */ 
/*      */   public String getTargetStepCode(String entryId, String tarStepCode, String currStepCode, String actionType)
/*      */   {
/*  954 */     String tarCode = tarStepCode;
/*  955 */     if ((org.apache.commons.lang.StringUtils.isNotBlank(currStepCode)) && 
/*  956 */       (isInsideAction(entryId, currStepCode, actionType))) {
/*  957 */       tarCode = currStepCode;
/*      */     }
/*      */ 
/*  960 */     return tarCode;
/*      */   }
/*      */ 
/*      */   public boolean isInsideAction(String entryId, String currStepCode, String actionType)
/*      */   {
/*  971 */     boolean isInsideAction = false;
/*  972 */     if (org.apache.commons.lang.StringUtils.isNotBlank(currStepCode)) {
/*  973 */       StepDescriptor curStDesc = getWfDescById(entryId).getStepDescriptor(currStepCode);
/*  974 */       if (curStDesc != null) {
/*  975 */         boolean curIsAuto = curStDesc.isAuto();
/*  976 */         boolean isNextAction = actionType.equals("NEXT");
/*  977 */         if ((!isNextAction) && (!curIsAuto)) {
/*  978 */           isInsideAction = true;
/*      */         }
/*      */       }
/*      */     }
/*  982 */     return isInsideAction;
/*      */   }
/*      */ 
/*      */   private void sendToEnd(String userId, Map<String, DataField> inputs, String entryId, ActionDescriptor acDesc, String tarEndCode, List<ActionInfo> acTransList, List<String> trace)
/*      */   {
/*  996 */     log.info("动作的目标是end");
/*  997 */     WfEntry wfEntry = (WfEntry)this.entryDao.get(entryId);
/*  998 */     wfEntry.setEndCode(tarEndCode);
/*  999 */     activeParent("NEXT", userId, entryId, inputs, acTransList);
/*      */   }
/*      */ 
/*      */   protected BaseTask updateEntryState(BaseTask templateTask, String actionType, Map<String, DataField> inputs, boolean joinFlag)
/*      */   {
/* 1008 */     if (("SUSPEND".equals(actionType)) || ("ACTIVE".equals(actionType))) {
/* 1009 */       log.info("挂起、激活工单状态不变");
/* 1010 */       return null;
/*      */     }
/* 1012 */     String entryId = templateTask.getEntryId();
/* 1013 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 1014 */     String parentFlowId = entry.getParentFlowId();
/* 1015 */     String topFlowId = entry.getTopFlowId();
/* 1016 */     String topEntryId = entry.getTopEntryId();
/* 1017 */     WfEntry topEntry = (WfEntry)this.entryDao.get(topEntryId);
/* 1018 */     long closeTime = topEntry.getCloseTime();
/* 1019 */     String state = topEntry.getState();
/* 1020 */     String endCode = topEntry.getEndCode();
/* 1021 */     String statusName = null;
/* 1022 */     String serialNum = templateTask.getSerialNum();
/* 1023 */     String oldEntryState = templateTask.getEntryState();
/*      */ 
/* 1025 */     if (closeTime > 0L) {
/* 1026 */       if ("finish".equals(state)) {
/* 1027 */         if (org.apache.commons.lang.StringUtils.isNotBlank(endCode)) {
/* 1028 */           statusName = getEndDescById(topEntryId, endCode).getStatusName();
/*      */         }
/* 1030 */         else if ("CLOSE".equals(actionType)) {
/* 1031 */           statusName = WfEngineUtils.entryState(actionType);
/*      */         }
/*      */       }
/* 1034 */       else if ("cancel".equals(state))
/* 1035 */         statusName = WfEngineUtils.entryState("CANCEL");
/* 1036 */       else if ("terminate".equals(state)) {
/* 1037 */         statusName = WfEngineUtils.entryState("TERMINATE");
/*      */       }
/* 1039 */       log.info("流程已经结束，更新工单状态！serialNum=" + serialNum + ",statusName=" + statusName);
/*      */     } else {
/* 1041 */       Step templateStep = null;
/* 1042 */       List currentSteps = getTransCurrentSteps(entryId);
/* 1043 */       if (CollectionUtils.isNotEmpty(currentSteps)) {
/* 1044 */         templateStep = (Step)currentSteps.get(0);
/*      */       }
/*      */ 
/* 1047 */       if ((templateStep != null) && ("JOIN".equals(templateStep.getType())))
/*      */       {
/* 1049 */         String templateStepEntryID = templateStep.getEntryId();
/* 1050 */         List templateCTList = this.currTaskDao.find("from CurrentProcessTask where entryid=?", new String[] { templateStepEntryID });
/* 1051 */         if ((templateCTList == null) || (templateCTList.size() == 0))
/*      */         {
/* 1053 */           joinFlag = false;
/*      */         }
/*      */       }
/* 1056 */       String tempStepId = null;
/* 1057 */       String forwardCode = null;
/* 1058 */       if (templateStep != null) {
/* 1059 */         tempStepId = templateStep.getId();
/* 1060 */         forwardCode = templateStep.getForwardCode();
/*      */       }
/*      */ 
/* 1063 */       if ((isFreeFlow(topEntryId)) && (templateStep != null)) {
/* 1064 */         if (forwardCode.equals("BEGIN"))
/*      */         {
/* 1066 */           statusName = WfEngineUtils.entryState(actionType);
/* 1067 */           if ("CHASE".equals(actionType))
/* 1068 */             statusName = WfEngineUtils.entryState("NEW");
/*      */         }
/*      */         else {
/* 1071 */           BaseTask currentTask = this.taskManager.getCurrentTaskByStepId(tempStepId);
/* 1072 */           if ((currentTask != null) && (!"CHASE".equals(actionType))) {
/* 1073 */             String entryState = currentTask.getEntryState();
/* 1074 */             DataField flag = (DataField)inputs.get("entry_state_update_flag_no");
/* 1075 */             if (flag != null)
/* 1076 */               statusName = "待处理";
/* 1077 */             else if (entryState.equals("待处理"))
/* 1078 */               statusName = "处理中";
/* 1079 */             else if (entryState.equals("待审批"))
/* 1080 */               statusName = "审批中";
/* 1081 */             else if ((entryState.equals("处理中")) || (entryState.equals("审批中")))
/*      */             {
/* 1083 */               statusName = "";
/* 1084 */             } else if ((actionType.equals("ASSIGN")) || (actionType.equals("SENDBACK")))
/* 1085 */               statusName = "待处理";
/* 1086 */             else if (actionType.equals("AUDIT")) {
/* 1087 */               statusName = "待审批";
/*      */             }
/*      */           }
/*      */         }
/* 1091 */         log.info("自由流程更新工单状态！serialNum=" + serialNum + ",statusName=" + statusName + ",actionType=" + actionType);
/*      */       }
/* 1093 */       else if ((topEntryId.equals(entryId)) && (templateStep != null)) {
/* 1094 */         String stepCode = templateStep.getStepCode();
/* 1095 */         if (org.apache.commons.lang.StringUtils.isNotBlank(stepCode)) {
/* 1096 */           StepDescriptor stDesc = getWfDescById(topEntryId).getStepDescriptor(stepCode);
/*      */ 
/* 1098 */           if (stDesc != null)
/*      */           {
/* 1100 */             String tmpStatusName = stDesc.getStatusName();
/* 1101 */             if ((org.apache.commons.lang.StringUtils.isNotBlank(tmpStatusName)) && (tmpStatusName.equals(oldEntryState)))
/* 1102 */               statusName = "";
/* 1103 */             else if (!joinFlag)
/*      */             {
/* 1107 */               statusName = "";
/*      */             }
/* 1109 */             else statusName = tmpStatusName;
/*      */           }
/*      */         }
/*      */ 
/* 1113 */         log.info("没有子流程的普通固定流程更新工单状态！serialNum=" + serialNum + ",statusName=" + statusName + ",entryId=" + entryId);
/*      */       }
/*      */       else {
/* 1116 */         if ("CANCEL".equals(actionType)) {
/* 1117 */           statusName = WfEngineUtils.entryState("CANCEL");
/* 1118 */         } else if ("TERMINATE".equals(state)) {
/* 1119 */           statusName = WfEngineUtils.entryState("TERMINATE");
/*      */         }
/* 1122 */         else if (CollectionUtils.isEmpty(currentSteps)) {
/* 1123 */           currentSteps = getTransCurrentSteps(topEntryId);
/*      */ 
/* 1126 */           if (CollectionUtils.isNotEmpty(currentSteps)) {
/* 1127 */             templateStep = (Step)currentSteps.get(0);
/* 1128 */             if (!templateStep.getType().equals("JOIN")) {
/* 1129 */               String tempEntryId = templateStep.getEntryId();
/* 1130 */               String tempStepCode = templateStep.getStepCode();
/* 1131 */               statusName = getStDescById(tempEntryId, tempStepCode).getStatusName();
/* 1132 */               log.info("模板工单状态 tempEntryId=" + tempEntryId + ",tempStepCode=" + tempStepCode + ",statusName=" + statusName);
/*      */             }
/*      */           } else {
/* 1135 */             List transHistorySteps = getTransHistorySteps(topEntryId);
/* 1136 */             if (CollectionUtils.isNotEmpty(transHistorySteps)) {
/* 1137 */               templateStep = (Step)transHistorySteps.get(0);
/* 1138 */               String tempEntryId = templateStep.getEntryId();
/* 1139 */               String tempStepCode = templateStep.getStepCode();
/*      */ 
/* 1141 */               String tmpStatusName = getStDescById(tempEntryId, tempStepCode).getStatusName();
/* 1142 */               if ((org.apache.commons.lang.StringUtils.isNotBlank(tmpStatusName)) && (tmpStatusName.equals(oldEntryState)))
/* 1143 */                 statusName = "";
/*      */               else {
/* 1145 */                 statusName = tmpStatusName;
/*      */               }
/* 1147 */               log.info("模板工单状态 tempEntryId=" + tempEntryId + ",tempStepCode=" + tempStepCode + ",statusName=" + statusName);
/*      */             }
/*      */           }
/* 1150 */         } else if (org.apache.commons.lang.StringUtils.isNotBlank(topFlowId)) {
/* 1151 */           String hql = " where flowId=?";
/* 1152 */           List steps = this.historyDao.find("from WfHistoryStep " + hql, new Object[] { topFlowId });
/* 1153 */           if (CollectionUtils.isEmpty(steps)) {
/* 1154 */             steps = this.currentDao.find("from WfCurrentStep " + hql, new Object[] { topFlowId });
/*      */           }
/* 1156 */           if (CollectionUtils.isNotEmpty(steps)) {
/* 1157 */             Step s = (Step)steps.get(0);
/*      */ 
/* 1159 */             String tmpStatusName = getStDescById(s.getEntryId(), s.getStepCode()).getStatusName();
/* 1160 */             if ((org.apache.commons.lang.StringUtils.isNotBlank(tmpStatusName)) && (tmpStatusName.equals(oldEntryState)))
/* 1161 */               statusName = "";
/*      */             else {
/* 1163 */               statusName = tmpStatusName;
/*      */             }
/*      */           }
/* 1166 */           log.info("topFlowId=" + topFlowId + ",hql=" + hql);
/*      */         }
/*      */ 
/* 1169 */         log.info("有子流程的固定流程更新工单状态！serialNum=" + serialNum + ",statusName=" + statusName + ",topEntryId=" + topEntryId);
/*      */       }
/*      */     }
/*      */ 
/* 1173 */     if ((org.apache.commons.lang.StringUtils.isNotBlank(statusName)) && 
/* 1174 */       (ThreadUtils.isUpdate() == null)) {
/* 1175 */       log.info("工单状态更新！topEntryId=" + topEntryId + ",statusName=" + statusName + ",serialNum=" + serialNum);
/* 1176 */       this.taskManager.updateAllTaskEntryState(topEntryId, statusName);
/* 1177 */       templateTask.setEntryState(statusName);
/*      */     }
/*      */ 
/* 1180 */     return templateTask;
/*      */   }
/*      */ 
/*      */   protected String getEntryState(BaseTask curTask)
/*      */   {
/* 1185 */     throw new WorkflowException("由子类继承实现");
/*      */   }
/*      */ 
/*      */   private void finishOld(String actionType, String userId, Map<String, DataField> inputs, BaseTask curTask, Step curStep, StepDescriptor stDesc)
/*      */   {
/* 1198 */     log.info("固定流转，结束旧任务!userId=" + userId + ",stepCode=" + curStep.getStepCode());
/* 1199 */     moveToHistory((WfCurrentStep)curStep);
/* 1200 */     curTask.setProcessState("已" + WfEngineUtils.getDefActionName(inputs));
/* 1201 */     if (stDesc != null) {
/* 1202 */       WfEngineUtils.executeFunction(stDesc.getPostFuncList(), userId, null, curTask, curStep, null, new ActionInfo(actionType), inputs);
/*      */     }
/* 1204 */     this.taskManager.moveToHistory(curTask);
/*      */   }
/*      */ 
/*      */   private void sendToSplit(String actionType, String userId, Map<String, DataField> inputs, BaseTask curTask, ActionDescriptor acDesc, Step curStep, List<ActionInfo> acTransList, String tarSplitCode, List<String> trace)
/*      */   {
/* 1212 */     log.info("动作的目标是split");
/* 1213 */     trace.add(tarSplitCode);
/* 1214 */     String entryId = curTask.getEntryId();
/* 1215 */     SplitDescriptor spDesc = getSpDescById(entryId, tarSplitCode);
/* 1216 */     Map result = new LinkedHashMap();
/* 1217 */     Map toResult = new LinkedHashMap();
/* 1218 */     String conType = spDesc.getConDesc().getType();
/* 1219 */     String conText = spDesc.getConDesc().getText();
/* 1220 */     log.info("分支的类型:" + conType + ",分支的条件:" + conText);
/*      */ 
/* 1222 */     parseCondition(entryId, null, inputs, spDesc, conText, result, toResult, trace);
/* 1223 */     int concurrentSteps = result.keySet().size();
/*      */ 
/* 1225 */     for (Iterator iterator = result.keySet().iterator(); iterator.hasNext(); ) {
/* 1226 */       String stepCode = (String)iterator.next();
/* 1227 */       AbstractDescriptor nodeDesc = (AbstractDescriptor)result.get(stepCode);
/* 1228 */       SplitToDescriptor spToDesc = (SplitToDescriptor)toResult.get(stepCode);
/* 1229 */       String assignTree = spToDesc.getAssignTree();
/* 1230 */       int remap = spToDesc.getRemap();
/* 1231 */       if (nodeDesc != null)
/* 1232 */         if ((nodeDesc instanceof StepDescriptor))
/*      */         {
/* 1234 */           if (concurrentSteps == 1) {
/* 1235 */             sendToStep(actionType, userId, curStep, curTask, acDesc, remap, stepCode, acTransList, inputs, trace);
/* 1236 */           } else if (concurrentSteps > 1) {
/* 1237 */             List thisActionInfoList = getTargetStepActionInfoList(curStep, stepCode, acTransList, actionType, assignTree);
/* 1238 */             sendToStep(actionType, userId, curStep, curTask, acDesc, remap, stepCode, thisActionInfoList, inputs, trace);
/*      */           }
/* 1240 */         } else if ((nodeDesc instanceof EndDescriptor))
/* 1241 */           sendToEnd(userId, inputs, entryId, acDesc, stepCode, acTransList, trace);
/* 1242 */         else if ((nodeDesc instanceof JoinDescriptor))
/* 1243 */           sendToJoin(userId, inputs, curTask, acDesc, curStep, stepCode, acTransList, trace);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setStepCode(List<ActionInfo> acTransList, String stepCode)
/*      */   {
/* 1250 */     if (CollectionUtils.isNotEmpty(acTransList))
/* 1251 */       for (int i = 0; i < acTransList.size(); i++) {
/* 1252 */         ActionInfo actionInfo = (ActionInfo)acTransList.get(i);
/* 1253 */         String stCode = actionInfo.getStepCode();
/*      */ 
/* 1257 */         if ((org.apache.commons.lang.StringUtils.isNotBlank(stCode)) && (stCode.startsWith("dp_"))) {
/*      */           break;
/*      */         }
/* 1260 */         actionInfo.setStepCode(stepCode);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void parseCondition(String entryId, String defName, Map<String, DataField> inputs, SplitDescriptor spDesc, String conText, Map<String, AbstractDescriptor> result, Map<String, SplitToDescriptor> toResult, List<String> trace)
/*      */   {
/* 1272 */     WorkflowDescriptor wkDesc = null;
/* 1273 */     if (org.apache.commons.lang.StringUtils.isNotBlank(entryId))
/* 1274 */       wkDesc = getWfDescById(entryId);
/*      */     else {
/* 1276 */       wkDesc = getWfDesc(defName);
/*      */     }
/* 1278 */     String stepElse = null;
/* 1279 */     if (org.apache.commons.lang.StringUtils.isNotBlank(conText)) {
/* 1280 */       String[] splits = conText.split("#");
/* 1281 */       if (!ArrayUtils.isEmpty(splits)) {
/* 1282 */         for (int i = 0; i < splits.length; i++) {
/* 1283 */           String[] split = splits[i].split(":");
/* 1284 */           if (!ArrayUtils.isEmpty(split)) {
/* 1285 */             String stepCode = split[0];
/* 1286 */             if (split.length == 2) {
/* 1287 */               String stepCon = split[1];
/*      */ 
/* 1289 */               if (stepCon.equals(WfEngineConstants.ELSE)) {
/* 1290 */                 stepElse = stepCode;
/*      */               } else {
/* 1292 */                 boolean ruleRes = RuleUtils.paseRule(stepCon, inputs);
/* 1293 */                 log.info("环节号:" + stepCode + ",规则文本:" + stepCon + ",规则结果:" + ruleRes);
/* 1294 */                 if (ruleRes) {
/* 1295 */                   AbstractDescriptor nodeDesc = getNodeType(entryId, defName, stepCode);
/* 1296 */                   if ((nodeDesc != null) && ((nodeDesc instanceof SplitDescriptor))) {
/* 1297 */                     SplitDescriptor tmpSpDesc = (SplitDescriptor)nodeDesc;
/* 1298 */                     String condText = tmpSpDesc.getConDesc().getText();
/* 1299 */                     String id = tmpSpDesc.getId();
/* 1300 */                     trace.add(id);
/* 1301 */                     parseCondition(entryId, defName, inputs, tmpSpDesc, condText, result, toResult, trace);
/*      */                   } else {
/* 1303 */                     result.put(stepCode, nodeDesc);
/* 1304 */                     if (spDesc != null)
/* 1305 */                       toResult.put(stepCode, spDesc.toDesc(stepCode));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             else {
/* 1311 */               log.info("环节号:" + stepCode + ",规则为空，将视为通过规则");
/* 1312 */               AbstractDescriptor nodeDesc = getNodeType(entryId, defName, stepCode);
/* 1313 */               if ((nodeDesc != null) && ((nodeDesc instanceof SplitDescriptor))) {
/* 1314 */                 SplitDescriptor tmpSpDesc = (SplitDescriptor)nodeDesc;
/* 1315 */                 String condText = tmpSpDesc.getConDesc().getText();
/* 1316 */                 String id = tmpSpDesc.getId();
/* 1317 */                 trace.add(id);
/* 1318 */                 parseCondition(entryId, defName, inputs, tmpSpDesc, condText, result, toResult, trace);
/*      */               } else {
/* 1320 */                 result.put(stepCode, getNodeType(entryId, defName, stepCode));
/* 1321 */                 if (spDesc != null) {
/* 1322 */                   toResult.put(stepCode, spDesc.toDesc(stepCode));
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1330 */     if ((CollectionUtils.isEmpty((Collection)result.keySet())) && (org.apache.commons.lang.StringUtils.isNotBlank(stepElse)))
/*      */     {
/* 1332 */       StepDescriptor stDesc = wkDesc.getStepDescriptor(stepElse);
/* 1333 */       SplitDescriptor tmpSpDesc = wkDesc.getSplitDescriptor(stepElse);
/* 1334 */       EndDescriptor edDesc = wkDesc.getEndDescriptor(stepElse);
/* 1335 */       JoinDescriptor jnDesc = wkDesc.getJoinDescriptor(stepElse);
/* 1336 */       if ((stDesc != null) || (edDesc != null) || (jnDesc != null)) {
/* 1337 */         result.put(stepElse, getNodeType(entryId, defName, stepElse));
/* 1338 */         if (spDesc != null)
/* 1339 */           toResult.put(stepElse, spDesc.toDesc(stepElse));
/*      */       }
/* 1341 */       else if (tmpSpDesc != null) {
/* 1342 */         String condText = tmpSpDesc.getConDesc().getText();
/* 1343 */         String id = tmpSpDesc.getId();
/* 1344 */         trace.add(id);
/* 1345 */         parseCondition(entryId, defName, inputs, tmpSpDesc, condText, result, toResult, trace);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private AbstractDescriptor getNodeType(String entryId, String defName, String stepCode) {
/* 1351 */     WorkflowDescriptor wkDesc = null;
/* 1352 */     if (org.apache.commons.lang.StringUtils.isNotBlank(entryId))
/* 1353 */       wkDesc = getWfDescById(entryId);
/*      */     else {
/* 1355 */       wkDesc = getWfDesc(defName);
/*      */     }
/* 1357 */     if (wkDesc != null) {
/* 1358 */       StepDescriptor stepDescriptor = wkDesc.getStepDescriptor(stepCode);
/* 1359 */       SplitDescriptor splitDescriptor = wkDesc.getSplitDescriptor(stepCode);
/* 1360 */       EndDescriptor endDescriptor = wkDesc.getEndDescriptor(stepCode);
/* 1361 */       JoinDescriptor joinDescriptor = wkDesc.getJoinDescriptor(stepCode);
/* 1362 */       if (stepDescriptor != null)
/* 1363 */         return stepDescriptor;
/* 1364 */       if (splitDescriptor != null)
/* 1365 */         return splitDescriptor;
/* 1366 */       if (endDescriptor != null)
/* 1367 */         return endDescriptor;
/* 1368 */       if (joinDescriptor != null) {
/* 1369 */         return joinDescriptor;
/*      */       }
/*      */     }
/* 1372 */     return null;
/*      */   }
/*      */ 
/*      */   private boolean sendToJoin(String userId, Map<String, DataField> inputs, BaseTask curTask, ActionDescriptor acDesc, Step curStep, String tarJoinCode, List<ActionInfo> acTransList, List<String> trace)
/*      */   {
/* 1387 */     trace.add(tarJoinCode);
/* 1388 */     String entryId = curStep.getEntryId();
/* 1389 */     JoinDescriptor jnDesc = getJnDescById(entryId, tarJoinCode);
/* 1390 */     String curStepId = curStep.getId();
/* 1391 */     String curStepCode = curStep.getStepCode();
/* 1392 */     String stepGroup = curStep.getStepGroup();
/* 1393 */     String stepCode = jnDesc.getStepId();
/* 1394 */     String splitId = jnDesc.getSplitId();
/* 1395 */     String endId = jnDesc.getEndId();
/* 1396 */     String condType = jnDesc.getConDesc().getType();
/* 1397 */     String cond = jnDesc.getConDesc().getValue();
/* 1398 */     String count = jnDesc.getConDesc().getText();
/*      */ 
/* 1400 */     String curStepIDTemp = "";
/* 1401 */     DataField dataFieldTemp = (DataField)inputs.get("FreeSubStepGroup_CurStepID");
/* 1402 */     if (dataFieldTemp != null) {
/* 1403 */       curStepIDTemp = dataFieldTemp.getValue();
/*      */     }
/* 1405 */     if (curStepIDTemp.length() == 0) {
/* 1406 */       curStepIDTemp = curStepId;
/*      */     }
/*      */ 
/* 1409 */     boolean canActive = false;
/* 1410 */     if (("GROUP".equals(count)) && (org.apache.commons.lang.StringUtils.isNotBlank(stepGroup))) {
/* 1411 */       canActive = true;
/* 1412 */       List transCurrentSteps = getTransCurrentSteps(curTask);
/* 1413 */       if (CollectionUtils.isNotEmpty(transCurrentSteps)) {
/* 1414 */         for (int i = 0; i < transCurrentSteps.size(); i++) {
/* 1415 */           WfCurrentStep wfCurrentStep = (WfCurrentStep)transCurrentSteps.get(i);
/* 1416 */           String stepGroup2 = wfCurrentStep.getStepGroup();
/* 1417 */           String thisId = wfCurrentStep.getId();
/* 1418 */           String thisType = wfCurrentStep.getType();
/* 1419 */           if ((stepGroup.equals(stepGroup2)) && (!thisId.equals(curStepIDTemp)) && (!"JOIN".equals(thisType))) {
/* 1420 */             canActive = false;
/* 1421 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1427 */     String tarId = null;
/* 1428 */     if (org.apache.commons.lang.StringUtils.isNotBlank(stepCode))
/* 1429 */       tarId = stepCode;
/* 1430 */     else if (org.apache.commons.lang.StringUtils.isNotBlank(splitId))
/* 1431 */       tarId = splitId;
/* 1432 */     else if (org.apache.commons.lang.StringUtils.isNotBlank(endId)) {
/* 1433 */       tarId = endId;
/*      */     }
/* 1435 */     boolean joinFlag = true;
/* 1436 */     log.info("动作的目标是join,conditionType=" + condType + ",conditionValue=" + cond + ",count=" + count);
/*      */ 
/* 1438 */     WfCurrentStep joinStep = null;
/* 1439 */     String hql = "from WfCurrentStep where entryId = '" + entryId + "' and stepCode='" + tarId + "'";
/* 1440 */     List list = this.currentDao.find(hql, null);
/* 1441 */     if (CollectionUtils.isEmpty(list)) {
/* 1442 */       joinStep = new WfCurrentStep(entryId, tarId, null, null, null, null, null, null, null);
/* 1443 */       joinStep.setType("JOIN");
/* 1444 */       joinStep.setStepGroup(stepGroup);
/* 1445 */       this.currentDao.save(joinStep);
/*      */     } else {
/* 1447 */       joinStep = (WfCurrentStep)list.get(0);
/*      */     }
/* 1449 */     joinStep.setFinishCount(joinStep.getFinishCount() + 1);
/* 1450 */     joinStep.setForwardId(curStepId);
/* 1451 */     int targetSize = "GROUP".equals(count) ? 0 : Integer.parseInt(count);
/* 1452 */     int finishCount = joinStep.getFinishCount();
/* 1453 */     log.info("当前完成数 " + finishCount + "，目标完成数" + targetSize);
/* 1454 */     if ((finishCount == targetSize) || (canActive)) {
/* 1455 */       this.currentDao.remove(joinStep);
/* 1456 */       log.info("分支全部完成，激活合并");
/* 1457 */       joinStep.setId(curStepId);
/*      */ 
/* 1466 */       if (org.apache.commons.lang.StringUtils.isNotBlank(stepCode))
/* 1467 */         sendToStep("NEXT", userId, joinStep, curTask, acDesc, 0, stepCode, acTransList, inputs, trace);
/* 1468 */       else if (org.apache.commons.lang.StringUtils.isNotBlank(splitId))
/* 1469 */         sendToSplit("NEXT", userId, inputs, curTask, acDesc, joinStep, acTransList, splitId, trace);
/* 1470 */       else if (org.apache.commons.lang.StringUtils.isNotBlank(endId))
/* 1471 */         sendToEnd(userId, inputs, entryId, acDesc, endId, acTransList, trace);
/*      */     }
/* 1473 */     else if (("GROUP".equals(count)) && (org.apache.commons.lang.StringUtils.isNotBlank(stepGroup)) && (!canActive)) {
/* 1474 */       log.info("并行分支尚未全部完成，继续等待");
/* 1475 */       joinFlag = false;
/* 1476 */     } else if (finishCount < targetSize) {
/* 1477 */       log.info("分支尚未全部完成，继续等待");
/* 1478 */       joinFlag = false;
/* 1479 */       ThreadUtils.setUpdate();
/* 1480 */     } else if (finishCount > targetSize) {
/* 1481 */       log.info("分支已经合并到主流程！");
/*      */     }
/*      */ 
/* 1494 */     DataField joinFlagDF = new DataField();
/* 1495 */     joinFlagDF.setValue(joinFlag);
/* 1496 */     inputs.put("sendToJoin_JoinFlag", joinFlagDF);
/* 1497 */     return joinFlag;
/*      */   }
/*      */ 
/*      */   protected void activeParent(String actionType, String userId, String entryId, Map<String, DataField> inputs, List<ActionInfo> acTransList)
/*      */   {
/* 1509 */     throw new WorkflowException("由子类继承实现");
/*      */   }
/*      */ 
/*      */   public void sendToStep(String actionType, String userId, Step curStep, BaseTask curTask, ActionDescriptor acDesc, int remap, String tarStepCode, List<ActionInfo> acList, Map<String, DataField> inputs, List<String> trace)
/*      */     throws WorkflowEngineException, TaskException
/*      */   {
/* 1528 */     String currStepCode = curStep.getStepCode();
/* 1529 */     String entryId = curStep.getEntryId();
/* 1530 */     tarStepCode = getTargetStepCode(entryId, tarStepCode, currStepCode, actionType);
/* 1531 */     StepDescriptor tarStDesc = getStDescById(entryId, tarStepCode);
/* 1532 */     actionType = getActionTypeByDealType(entryId, tarStepCode, actionType);
/*      */ 
/* 1534 */     if (CollectionUtils.isEmpty(acList)) {
/* 1535 */       log.info("前台传入的处理人信息为空，将解析环节上的处理人规则，获取处理人");
/* 1536 */       acList = parseActorRule(curTask, tarStDesc, inputs);
/*      */ 
/* 1538 */       log.info("重新匹配 remap = " + remap);
/* 1539 */       if (remap == 0)
/*      */       {
/* 1541 */         log.info(" remap=0，优先级：db,角色细分 ");
/* 1542 */         if (CollectionUtils.isEmpty(acList)) {
/* 1543 */           acList = getActorFromDb(curStep, tarStepCode, acList);
/*      */         }
/* 1545 */         if (CollectionUtils.isEmpty(acList)) {
/* 1546 */           String defName = tarStDesc.getParent().getName();
/* 1547 */           List chiRoles = this.roleService.matchChildRole(null, defName, tarStepCode, inputs);
/* 1548 */           if (CollectionUtils.isNotEmpty(chiRoles))
/* 1549 */             for (int i = 0; i < chiRoles.size(); i++) {
/* 1550 */               ChildRole chiRole = (ChildRole)chiRoles.get(i);
/* 1551 */               String childRoleId = chiRole.getChildRoleId();
/* 1552 */               List depList = chiRole.getDepList();
/* 1553 */               String code = null;
/*      */ 
/* 1555 */               if (CollectionUtils.isNotEmpty(depList)) {
/* 1556 */                 DepInfo dep = (DepInfo)depList.get(0);
/* 1557 */                 String depId = dep.getPid();
/* 1558 */                 code = "G#:" + depId + "#:NEXT#:2#:#:#:#:#:" + tarStepCode + "#:#:#;";
/*      */               } else {
/* 1560 */                 code = "R#:" + childRoleId + "#:NEXT#:2#:#:#:#:#:" + tarStepCode + "#:#:#;";
/*      */               }
/* 1562 */               BizFormUtil.parseAssignStr(acList, code);
/* 1563 */               log.info("成功匹配到角色细分,chiRoleCode=" + code);
/*      */             }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1569 */         log.info(" remap=1，优先级：角色细分，db ");
/* 1570 */         if (CollectionUtils.isEmpty(acList)) {
/* 1571 */           String defName = tarStDesc.getParent().getName();
/* 1572 */           List chiRoles = this.roleService.matchChildRole(null, defName, tarStepCode, inputs);
/* 1573 */           if (CollectionUtils.isNotEmpty(chiRoles)) {
/* 1574 */             for (int i = 0; i < chiRoles.size(); i++) {
/* 1575 */               ChildRole chiRole = (ChildRole)chiRoles.get(i);
/* 1576 */               String childRoleId = chiRole.getChildRoleId();
/* 1577 */               List depList = chiRole.getDepList();
/* 1578 */               String code = null;
/*      */ 
/* 1580 */               if (CollectionUtils.isNotEmpty(depList)) {
/* 1581 */                 DepInfo dep = (DepInfo)depList.get(0);
/* 1582 */                 String depId = dep.getPid();
/* 1583 */                 code = "G#:" + depId + "#:NEXT#:2#:#:#:#:#:" + tarStepCode + "#:#:#;";
/*      */               } else {
/* 1585 */                 code = "R#:" + childRoleId + "#:NEXT#:2#:#:#:#:#:" + tarStepCode + "#:#:#;";
/*      */               }
/* 1587 */               BizFormUtil.parseAssignStr(acList, code);
/* 1588 */               log.info("成功匹配到角色细分,chiRoleCode=" + code);
/*      */             }
/*      */           }
/*      */         }
/* 1592 */         if (CollectionUtils.isEmpty(acList)) {
/* 1593 */           acList = getActorFromDb(curStep, tarStepCode, acList);
/*      */         }
/*      */       }
/*      */     }
/* 1597 */     if (CollectionUtils.isEmpty(acList)) {
/* 1598 */       throw new WorkflowEngineException("没有指定[" + tarStepCode + "]环节的处理人，流程定义中也没有配置固定的处理人！");
/*      */     }
/*      */ 
/* 1601 */     send(actionType, userId, curStep, curTask, acDesc, tarStDesc, inputs, acList, trace);
/*      */   }
/*      */ 
/*      */   private List<ActionInfo> getTargetStepActionInfoList(Step curStep, String tarStepCode, List<ActionInfo> totalList, String actionType, String assignTree)
/*      */   {
/* 1608 */     List acList = new ArrayList();
/* 1609 */     if ((curStep != null) && (org.apache.commons.lang.StringUtils.isNotBlank(tarStepCode)) && (org.apache.commons.lang.StringUtils.isNotBlank(actionType))) {
/* 1610 */       String currStepCode = curStep.getStepCode();
/* 1611 */       String entryId = curStep.getEntryId();
/* 1612 */       tarStepCode = getTargetStepCode(entryId, tarStepCode, currStepCode, actionType);
/* 1613 */       actionType = getActionTypeByDealType(entryId, tarStepCode, actionType);
/* 1614 */       StepDescriptor tarStDesc = getStDescById(entryId, tarStepCode);
/* 1615 */       acList = WfEngineUtils.getActionInfoList(totalList, actionType, true, tarStepCode, assignTree);
/*      */     }
/* 1617 */     return acList;
/*      */   }
/*      */ 
/*      */   public void send(String actionType, String userId, Step curStep, BaseTask curTask, ActionDescriptor acDesc, StepDescriptor tarStDesc, Map<String, DataField> inputs, List<ActionInfo> acList, List<String> tracks) {
/* 1621 */     if (CollectionUtils.isNotEmpty(acList)) {
/* 1622 */       String tarStepCode = tarStDesc.getId();
/*      */ 
/* 1624 */       if ((isClassicSubflow(tarStDesc)) || (isInsideFreeSubflow(actionType, tarStDesc, curStep)) || (isClassicFreeSubflow(actionType, tarStDesc))) {
/* 1625 */         sendMore(actionType, userId, curStep, curTask, acDesc, tarStDesc, acList, inputs, tracks);
/*      */       } else {
/* 1627 */         if (acList.size() != 1) {
/* 1628 */           throw new WorkflowEngineException("指定[" + tarStepCode + "]环节的处理人不唯一（目标环节没有配置子流程属性，处理人必须唯一）！size=" + acList.size());
/*      */         }
/* 1630 */         sendOne(actionType, userId, curStep, curTask, acDesc, tarStDesc, acList, inputs, tracks);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected List<ActionInfo> getActorFromDb(Step curStep, String tarStepCode, List<ActionInfo> acList)
/*      */   {
/* 1643 */     throw new WorkflowException("由子类继承实现");
/*      */   }
/*      */ 
/*      */   protected List<ActionInfo> parseActorRule(BaseTask curTask, StepDescriptor tarStDesc, Map<String, DataField> inputs)
/*      */   {
/* 1654 */     List acList = new ArrayList();
/* 1655 */     String tarStepCode = tarStDesc.getId();
/* 1656 */     ActorDescriptor actorDesc = tarStDesc.getActorDesc();
/* 1657 */     if (actorDesc != null)
/*      */     {
/* 1659 */       String actorType = actorDesc.getActorType();
/* 1660 */       String rule = actorDesc.getRule();
/* 1661 */       if ((org.apache.commons.lang.StringUtils.isNotBlank(actorType)) && (org.apache.commons.lang.StringUtils.isNotBlank(rule))) {
/* 1662 */         if (actorType.equals("0"))
/*      */         {
/* 1664 */           acList.addAll(WfEngineUtils.rule0(tarStepCode, rule));
/* 1665 */         } else if (actorType.equals("1"))
/*      */         {
/* 1668 */           acList.addAll(WfEngineUtils.rule1(curTask, tarStepCode, rule));
/* 1669 */         } else if (actorType.equals("2"))
/*      */         {
/* 1671 */           acList.addAll(WfEngineUtils.rule2(tarStepCode, rule, inputs));
/* 1672 */         } else if (!actorType.equals("3"))
/*      */         {
/* 1674 */           actorType.equals("4");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1679 */     return acList;
/*      */   }
/*      */ 
/*      */   private void sendOne(String actionType, String userId, Step curStep, BaseTask curTask, ActionDescriptor acDesc, StepDescriptor tarStDesc, List<ActionInfo> acList, Map<String, DataField> inputs, List<String> tracks)
/*      */     throws TaskException, WorkflowEngineException
/*      */   {
/* 1698 */     ActionInfo actionInfo = (ActionInfo)acList.get(0);
/* 1699 */     String tarStepCode = tarStDesc.getId();
/* 1700 */     log.info("一派一流转:" + userId + " 派给 " + actionInfo.getActorId() + ", stepCode=" + tarStepCode);
/* 1701 */     String entryId = curStep.getEntryId();
/* 1702 */     String forwardId = curStep.getId();
/* 1703 */     String forwardCode = curStep.getStepCode();
/* 1704 */     String track = "";
/* 1705 */     if (CollectionUtils.isNotEmpty(tracks)) {
/* 1706 */       track = WfEngineUtils.joinList(tracks, ",");
/*      */     }
/* 1708 */     String type = tarStDesc.getType();
/* 1709 */     WfCurrentStep step = new WfCurrentStep(entryId, tarStepCode, forwardId, forwardCode, null, null, null, track, type);
/* 1710 */     step.setName(tarStDesc.getDesc());
/* 1711 */     step.setStepNo(tarStDesc.getStepNo());
/* 1712 */     step.setStepGroup(tarStDesc.getStepGroup());
/* 1713 */     this.currentDao.save(step);
/* 1714 */     BaseTask task = this.taskManager.assignTask(userId, curTask, step, tarStDesc, actionInfo, inputs);
/* 1715 */     setDefProcessState(tarStDesc, new ActionInfo(actionType), task);
/* 1716 */     WfEngineUtils.executeFunction(tarStDesc.getPreFuncList(), userId, curTask, task, step, acDesc, tarStDesc, actionInfo, inputs);
/*      */   }
/*      */ 
/*      */   private void sendMore(String actionType, String userId, Step curStep, BaseTask curTask, ActionDescriptor acDesc, StepDescriptor tarStDesc, List<ActionInfo> acList, Map<String, DataField> inputs, List<String> tracks)
/*      */     throws WorkflowException
/*      */   {
/* 1734 */     if (!"MAKECOPY".equals(actionType)) {
/* 1735 */       curStep = (Step)this.historyDao.get(curStep.getId());
/*      */     }
/* 1737 */     String stepCode = tarStDesc.getId();
/* 1738 */     String stepNo = com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(tarStDesc.getStepNo());
/* 1739 */     String stepGroup = com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(tarStDesc.getStepGroup());
/* 1740 */     String flowId = UUIDGenerator.getId();
/* 1741 */     int size = acList.size();
/* 1742 */     log.info("一派多[" + size + "]流转！ stepCode=" + stepCode);
/*      */ 
/* 1744 */     String parEntryId = curStep.getEntryId();
/* 1745 */     WfEntry entry = (WfEntry)this.entryDao.get(parEntryId);
/* 1746 */     String topEntryId = entry.getTopEntryId();
/* 1747 */     String topFlowId = entry.getTopFlowId();
/* 1748 */     String parTaskId = curTask.getId();
/* 1749 */     String topTaskId = org.apache.commons.lang.StringUtils.isBlank(curTask.getTopTaskId()) ? curTask.getId() : curTask.getTopTaskId();
/* 1750 */     String serialNum = curTask.getSerialNum();
/* 1751 */     inputs.put("parent_entryId", new DataField("parent_entryId", FieldDataType.String, parEntryId));
/* 1752 */     inputs.put("top_entryId", new DataField("top_entryId", FieldDataType.String, topEntryId));
/* 1753 */     inputs.put("parent_taskId", new DataField("parent_taskId", FieldDataType.String, parTaskId));
/* 1754 */     inputs.put("top_taskId", new DataField("top_taskId", FieldDataType.String, topTaskId));
/* 1755 */     inputs.put("parent_flowId", new DataField("parent_flowId", FieldDataType.String, flowId));
/* 1756 */     inputs.put("top_flowId", new DataField("top_flowId", FieldDataType.String, topFlowId));
/* 1757 */     inputs.put("serialNum", new DataField("serialNum", FieldDataType.String, serialNum));
/* 1758 */     inputs.put("parent_stepNo", new DataField("parent_stepNo", FieldDataType.String, stepNo));
/* 1759 */     inputs.put("parent_stepGroup", new DataField("parent_stepGroup", FieldDataType.String, stepGroup));
/* 1760 */     ThreadUtils.setTopDefStep(tarStDesc);
/*      */ 
/* 1762 */     if (isClassicFreeSubflow(actionType, tarStDesc))
/*      */     {
/* 1764 */       log.info("传统自由自流程");
/* 1765 */       if (CollectionUtils.isNotEmpty(acList)) {
/* 1766 */         String desc = WfEngineUtils.subflowDesc(userId, acList, inputs);
/* 1767 */         subflowDeal(userId, actionType, curStep, curTask, stepCode, flowId, 1, inputs, desc, tarStDesc, tracks);
/* 1768 */         log.info("开始初始化子流程!defName=FREESUBFLOW,entryType=FREE,actionType=" + actionType + ",userId=" + acList);
/* 1769 */         subDoAction(null, "FREESUBFLOW", "FREE", userId, "USER", null, null, null, actionType, true, acList, inputs);
/*      */       }
/* 1771 */     } else if (isClassicSubflow(tarStDesc)) {
/* 1772 */       log.info("子流程是固定流程，共有[" + size + "]个子流程");
/* 1773 */       SubflowsDescriptor subflows = tarStDesc.getSubflows();
/* 1774 */       Map subflowMap = subflows.getSubflowMap();
/* 1775 */       ConditionDescriptor conDesc = subflows.getConDesc();
/* 1776 */       String conText = conDesc.getText();
/* 1777 */       log.info("子流程运行条件 ： " + conText);
/* 1778 */       Map result = new LinkedHashMap();
/* 1779 */       Map toResult = new LinkedHashMap();
/* 1780 */       List tmpList = new ArrayList();
/* 1781 */       parseCondition(parEntryId, null, inputs, null, conText, result, toResult, tmpList);
/* 1782 */       if (CollectionUtils.isEmpty((Collection)result.keySet())) {
/* 1783 */         throw new WorkflowException("没有符合条件的子流程，流程不能继续流转，请检查子流程的流转条件！");
/*      */       }
/*      */ 
/* 1786 */       boolean ruleClassicSubflow = false;
/* 1787 */       Map subflowActionInfoListMap = new HashMap();
/* 1788 */       if ((acList != null) && (acList.size() > 1))
/*      */       {
/* 1790 */         for (Iterator iterator = result.keySet().iterator(); iterator.hasNext(); ) {
/* 1791 */           String subName = (String)iterator.next();
/* 1792 */           List stepActionInfoList = (List)subflowActionInfoListMap.get(subName);
/* 1793 */           if (stepActionInfoList == null)
/*      */           {
/* 1795 */             stepActionInfoList = new ArrayList();
/*      */           }
/* 1797 */           String splitStepCode = "";
/* 1798 */           for (ActionInfo acInfo : acList)
/*      */           {
/* 1800 */             if (!subName.equals(acInfo.getSubName()))
/*      */               continue;
/* 1802 */             if ((splitStepCode.equals("")) || (splitStepCode.equals(acInfo.getStepCode())))
/*      */             {
/* 1804 */               if (splitStepCode.equals("")) splitStepCode = acInfo.getStepCode();
/*      */ 
/* 1806 */               List itemist = new ArrayList();
/* 1807 */               stepActionInfoList.add(itemist);
/*      */             }
/* 1809 */             ((List)stepActionInfoList.get(stepActionInfoList.size() - 1)).add(acInfo);
/* 1810 */             ruleClassicSubflow = true;
/*      */           }
/*      */ 
/* 1813 */           if (stepActionInfoList.size() <= 0)
/*      */             continue;
/* 1815 */           subflowActionInfoListMap.put(subName, stepActionInfoList);
/*      */         }
/*      */       }
/*      */       StepDescriptor stDesc;
/* 1819 */       if ((subflowMap != null) && (!ruleClassicSubflow)) {
/* 1820 */         String desc = WfEngineUtils.subflowDesc(userId, acList, inputs);
/* 1821 */         subflowDeal(userId, actionType, curStep, curTask, stepCode, flowId, size, inputs, desc, tarStDesc, tracks);
/* 1822 */         int count = 0;
/* 1823 */         for (Iterator iterator = result.keySet().iterator(); iterator.hasNext(); ) {
/* 1824 */           String subName = (String)iterator.next();
/* 1825 */           String subEntryType = "DEFINE";
/* 1826 */           if (CollectionUtils.isNotEmpty(acList)) {
/* 1827 */             for (int j = 0; j < acList.size(); j++) {
/* 1828 */               ActionInfo acInfo = (ActionInfo)acList.get(j);
/* 1829 */               acInfo = acInfo.clone();
/* 1830 */               String acSubName = acInfo.getSubName();
/* 1831 */               if ((org.apache.commons.lang.StringUtils.isNotBlank(subName)) && ((org.apache.commons.lang.StringUtils.isBlank(acSubName)) || (subName.equals(acSubName)))) {
/* 1832 */                 count++;
/* 1833 */                 StartDescriptor startDesc = getWfDesc(subName).getStartDesc();
/* 1834 */                 stDesc = getWfDesc(subName).getStepDescriptor(startDesc.getTo());
/* 1835 */                 EngineModel engineModel = null;
/* 1836 */                 if (stDesc != null)
/*      */                 {
/* 1838 */                   acInfo.setStepCode(stDesc.getId());
/* 1839 */                   log.info("第[" + count + "]个子流程开始初始化,defName=" + subName + ",entryType=" + subEntryType);
/* 1840 */                   log.info(userId + " 派给 " + acInfo.getActorId());
/* 1841 */                   engineModel = subDoAction(null, subName, subEntryType, acInfo.getActorId(), acInfo.getActorType(), null, null, null, "SAVE", true, null, inputs);
/* 1842 */                   if (engineModel != null) {
/* 1843 */                     String tmpTaskId = engineModel.getCreateTaskId();
/* 1844 */                     if (org.apache.commons.lang.StringUtils.isNotBlank(tmpTaskId)) {
/* 1845 */                       BaseTask tmp = (BaseTask)curTask.clone();
/* 1846 */                       tmp.setId(tmpTaskId);
/* 1847 */                       WfEngineUtils.sendMsg(userId, acInfo, acDesc, null, tmp, inputs);
/*      */                     }
/*      */                   }
/*      */                 } else {
/* 1851 */                   log.info("流程开始节点直连分支，第[" + count + "]个子流程开始初始化,defName=" + subName + ",entryType=" + subEntryType);
/* 1852 */                   log.info(userId + " 派给 " + acInfo.getActorId());
/* 1853 */                   acInfo.setStepCode(null);
/* 1854 */                   acInfo.setActionType("NEXT");
/* 1855 */                   List acInfos = (List)Lists.newArrayList(new ActionInfo[] { acInfo });
/* 1856 */                   subDoAction(null, subName, subEntryType, acInfo.getActorId(), acInfo.getActorType(), null, null, null, "NEXT", true, acInfos, inputs);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1863 */         if (count != size) {
/* 1864 */           throw new WorkflowException("子流程标的个数[" + size + "]与实际执行的个数[" + count + "]不相等，请检查子流程的处理人subName属性是否正确！");
/*      */         }
/*      */       }
/* 1867 */       else if ((subflowMap != null) && (ruleClassicSubflow))
/*      */       {
/* 1869 */         String desc = WfEngineUtils.subflowDesc(userId, acList, inputs);
/* 1870 */         subflowDeal(userId, actionType, curStep, curTask, stepCode, flowId, size, inputs, desc, tarStDesc, tracks);
/* 1871 */         int count = 0;
/* 1872 */         for (Iterator iterator = result.keySet().iterator(); iterator.hasNext(); ) {
/* 1873 */           String subName = (String)iterator.next();
/* 1874 */           String subEntryType = "DEFINE";
/* 1875 */           if (subflowActionInfoListMap.size() > 0) {
/* 1876 */             List stepActionInfoList = (List)subflowActionInfoListMap.get(subName);
/* 1877 */             for (int j = 0; j < stepActionInfoList.size(); j++) {
/* 1878 */               count++;
/* 1879 */               List acInfoList = (List)stepActionInfoList.get(j);
/* 1880 */               for (ActionInfo acInfo : acInfoList)
/*      */               {
/* 1882 */                 acInfo = acInfo.clone();
/*      */               }
/*      */ 
/* 1885 */               if (org.apache.commons.lang.StringUtils.isNotBlank(subName)) {
/* 1886 */                 StartDescriptor startDesc = getWfDesc(subName).getStartDesc();
/* 1887 */                 StepDescriptor stDesc = getWfDesc(subName).getStepDescriptor(startDesc.getTo());
/* 1888 */                 EngineModel engineModel = null;
/*      */                 String tmpTaskId;
/* 1889 */                 if (stDesc != null)
/*      */                 {
/* 1892 */                   log.info("第[" + count + "]个子流程开始初始化,defName=" + subName + ",entryType=" + subEntryType);
/*      */ 
/* 1894 */                   ActionInfo acInfo = (ActionInfo)acInfoList.get(0);
/* 1895 */                   engineModel = subDoAction(null, subName, subEntryType, acInfo.getActorId(), acInfo.getActorType(), null, null, null, "SAVE", true, acInfoList, inputs);
/* 1896 */                   if (engineModel != null) {
/* 1897 */                     tmpTaskId = engineModel.getCreateTaskId();
/* 1898 */                     if (org.apache.commons.lang.StringUtils.isNotBlank(tmpTaskId)) {
/* 1899 */                       BaseTask tmp = (BaseTask)curTask.clone();
/* 1900 */                       tmp.setId(tmpTaskId);
/* 1901 */                       WfEngineUtils.sendMsg(userId, acInfo, acDesc, null, tmp, inputs);
/*      */                     }
/*      */                   }
/*      */                 } else {
/* 1905 */                   log.info("流程开始节点直连分支，第[" + count + "]个子流程开始初始化,defName=" + subName + ",entryType=" + subEntryType);
/*      */ 
/* 1908 */                   for (ActionInfo acInfo : acInfoList)
/*      */                   {
/* 1910 */                     acInfo.setActionType("NEXT");
/*      */                   }
/* 1912 */                   ActionInfo acInfo = (ActionInfo)acInfoList.get(0);
/* 1913 */                   subDoAction(null, subName, subEntryType, acInfo.getActorId(), acInfo.getActorType(), null, null, null, "NEXT", true, acInfoList, inputs);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1921 */     else if (isInsideFreeSubflow(actionType, tarStDesc, curStep))
/*      */     {
/* 1923 */       log.info("子流程是自由环节");
/* 1924 */       if (CollectionUtils.isNotEmpty(acList)) {
/* 1925 */         curStep.setSubCount(1);
/* 1926 */         String existFlowId = curStep.getFlowId();
/* 1927 */         if (org.apache.commons.lang.StringUtils.isBlank(existFlowId)) {
/* 1928 */           curStep.setFlowId(flowId);
/*      */         }
/*      */         else {
/* 1931 */           inputs.put("parent_flowId", new DataField("parent_flowId", FieldDataType.String, existFlowId));
/*      */         }
/* 1933 */         WfEngineUtils.getDefaultFreePreFunction(new ActionInfo("sendmoreinside")).execute(userId, null, curTask, curStep, null, inputs);
/* 1934 */         log.info("开始初始化子流程!defName=FREESUBFLOW,entryType=FREE,userId=" + acList);
/* 1935 */         subDoAction(null, "FREESUBFLOW", "FREE", userId, "USER", null, null, null, actionType, true, acList, inputs);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void subflowDeal(String userId, String actionType, Step curStep, BaseTask curTask, String stepCode, String flowId, int size, Map<String, DataField> inputs, String desc, StepDescriptor stDesc, List<String> tracks)
/*      */   {
/* 1952 */     WfHistoryStep hisBack = (WfHistoryStep)((WfHistoryStep)curStep).clone();
/* 1953 */     hisBack.setId(UUIDGenerator.getId());
/* 1954 */     hisBack.setCreateTime(TimeUtils.getCurrentTime());
/* 1955 */     hisBack.setSubCount(size);
/* 1956 */     hisBack.setFinishCount(0);
/* 1957 */     hisBack.setFlowId(flowId);
/* 1958 */     hisBack.setStepCode(stepCode);
/* 1959 */     hisBack.setForwardId(curStep.getId());
/* 1960 */     hisBack.setForwardCode(curStep.getStepCode());
/* 1961 */     StepDescriptor stepDes = getStDescById(curStep.getEntryId(), stepCode);
/* 1962 */     hisBack.setName(stepDes.getDesc());
/*      */ 
/* 1964 */     hisBack.setStepGroup(stepDes.getStepGroup());
/* 1965 */     String track = "";
/* 1966 */     if (CollectionUtils.isNotEmpty(tracks)) {
/* 1967 */       track = WfEngineUtils.joinList(tracks, ",");
/*      */     }
/* 1969 */     hisBack.setTrack(track);
/* 1970 */     this.historyDao.save(hisBack);
/* 1971 */     SendMoreClassicInFunction sendMore = (SendMoreClassicInFunction)ApplicationContextUtils.getBean("sendmoreclassicInFunction");
/* 1972 */     sendMore.subDeal(userId, curTask, hisBack, new ActionInfo(actionType), inputs, desc, stDesc);
/*      */   }
/*      */ 
/*      */   protected void freeTrans(String mActionType, String userId, List<ActionInfo> acInfos, Map<String, DataField> inputs, BaseTask curTask, Step curStep)
/*      */     throws WorkflowException
/*      */   {
/* 1986 */     ActionInfo tarAcInfo = new ActionInfo(mActionType);
/* 1987 */     if ((CollectionUtils.isEmpty(acInfos)) || ("SAVE".equals(mActionType)))
/*      */     {
/* 1989 */       acInfos = (List)Lists.newArrayList(new ActionInfo[] { tarAcInfo });
/*      */     }
/* 1991 */     String taskId = curTask.getId();
/* 1992 */     String forwardCode = curStep.getForwardCode();
/* 1993 */     String entryState = curTask.getEntryState();
/* 1994 */     for (int i = 0; i < acInfos.size(); i++) {
/* 1995 */       ActionInfo acInfo = (ActionInfo)acInfos.get(i);
/* 1996 */       String actorId = org.apache.commons.lang.StringUtils.isNotBlank(acInfo.getActorId()) ? acInfo.getActorId() : "";
/* 1997 */       String actionType = acInfo.getActionType();
/* 1998 */       log.info(userId + " " + WfEngineUtils.getActionName(actionType) + " " + actorId);
/* 1999 */       if (org.apache.commons.lang.StringUtils.isNotBlank(actionType)) {
/* 2000 */         if ((actionType.equals("SAVE")) || (actionType.equals("NEW"))) {
/* 2001 */           this.taskManager.updateBizProperties(curTask, inputs);
/* 2002 */         } else if ((actionType.equals("ACCEPT")) || (actionType.equals("NOTICE"))) {
/* 2003 */           accept(curStep, curTask, userId, acInfo, inputs);
/* 2004 */         } else if ((actionType.equals("ASSIGN")) || 
/* 2005 */           (actionType.equals("AUDIT")) || 
/* 2006 */           (actionType.equals("ORGANIZEAUDIT")))
/*      */         {
/* 2008 */           assign(curStep, curTask, userId, acInfo, inputs);
/* 2009 */         } else if ((actionType.equals("REAUDIT")) || 
/* 2010 */           (actionType.equals("AUDITINGPASS")) || 
/* 2011 */           (actionType.equals("AUDITINGNOPASS")))
/*      */         {
/* 2013 */           auditing(curStep, curTask, userId, acInfo, inputs);
/* 2014 */         } else if (actionType.equals("REASSIGN"))
/*      */         {
/* 2016 */           reAssign(curStep, curTask, userId, acInfo, inputs);
/* 2017 */         } else if ((actionType.equals("FINISH")) || 
/* 2018 */           (actionType.equals("CONFIRM")) || 
/* 2019 */           (actionType.equals("ORGANIZEAUDITINGPASS")) || 
/* 2020 */           (actionType.equals("ORGANIZEAUDITINGNOPASS")))
/*      */         {
/* 2022 */           finish(curStep, curTask, userId, acInfo, inputs);
/* 2023 */         } else if (actionType.equals("REJECT"))
/*      */         {
/* 2025 */           reject(curStep, curTask, userId, acInfo, inputs);
/* 2026 */         } else if (actionType.equals("CLOSE"))
/*      */         {
/* 2028 */           close(curStep, curTask, userId, acInfo, inputs);
/* 2029 */         } else if (actionType.equals("ASSIST"))
/*      */         {
/* 2031 */           assist(curStep, curTask, userId, acInfo, inputs);
/* 2032 */         } else if (actionType.equals("MAKECOPY"))
/*      */         {
/* 2034 */           copy(curStep, curTask, userId, acInfo, inputs);
/* 2035 */         } else if (actionType.equals("CHASE"))
/*      */         {
/* 2037 */           chase(curStep, curTask, userId, acInfo, inputs);
/* 2038 */         } else if (actionType.equals("CHASEALL")) {
/* 2039 */           chaseAll(curStep, curTask, userId, acInfo, inputs);
/* 2040 */         } else if (actionType.equals("SUSPEND"))
/*      */         {
/* 2042 */           suspend(curStep, curTask, userId, acInfo, inputs);
/* 2043 */           WfEngineUtils.getDefaultFreePostFunction(tarAcInfo).execute(userId, null, curTask, curStep, tarAcInfo, inputs);
/* 2044 */         } else if (actionType.equals("ACTIVE"))
/*      */         {
/* 2046 */           active(curStep, curTask, userId, acInfo, inputs);
/* 2047 */         } else if (actionType.equals("CANCEL"))
/*      */         {
/* 2049 */           cancel(curStep, curTask, userId, acInfo, inputs);
/* 2050 */         } else if (actionType.equals("SENDBACK"))
/*      */         {
/* 2052 */           sendBack(curStep, curTask, userId, acInfo, inputs);
/* 2053 */         } else if (actionType.equals("APPENDASSIGN"))
/*      */         {
/* 2055 */           appendAssign(curStep, curTask, userId, acInfo, inputs);
/*      */         } else {
/* 2057 */           customAction(curStep, curTask, userId, acInfo, inputs);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2063 */     String[] noTransActions = { 
/* 2064 */       "SAVE", "NEW", "ACCEPT", 
/* 2065 */       "NOTICE", "CHASE", "MAKECOPY", 
/* 2066 */       "ACTIVE", "SUSPEND", "APPENDASSIGN" };
/*      */ 
/* 2068 */     if ((ArrayUtils.indexOf(noTransActions, mActionType) < 0) || (
/* 2069 */       (isFreeSubFlow(curStep.getEntryId())) && 
/* 2070 */       ("BEGIN".equals(forwardCode)) && 
/* 2071 */       (mActionType.equals("MAKECOPY")))) {
/* 2072 */       curTask.setProcessState(WfEngineUtils.outProcessState(mActionType));
/* 2073 */       WfEngineUtils.getDefaultFreePostFunction(tarAcInfo).execute(userId, null, curTask, curStep, tarAcInfo, inputs);
/* 2074 */       if ((curStep instanceof WfCurrentStep)) {
/* 2075 */         moveToHistory((WfCurrentStep)curStep);
/* 2076 */         this.taskManager.moveToHistory(curTask);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void assign(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2091 */     curStep.setSubCount(curStep.getSubCount() + 1);
/* 2092 */     String entryId = curStep.getEntryId();
/* 2093 */     String forwardId = curStep.getId();
/* 2094 */     String forwarCode = curStep.getStepCode();
/* 2095 */     WfCurrentStep step = new WfCurrentStep(entryId, forwardId, forwarCode, null, null, null, null, "DEAL");
/*      */ 
/* 2097 */     String parentStepGroup = CommonUtils.getVal(inputs, "parent_stepGroup");
/* 2098 */     if (org.apache.commons.lang.StringUtils.isNotBlank(parentStepGroup)) {
/* 2099 */       step.setStepGroup(parentStepGroup);
/*      */     }
/* 2101 */     this.currentDao.save(step);
/* 2102 */     BaseTask task = this.taskManager.assignTask(userId, curTask, step, null, acInfo, inputs);
/* 2103 */     setFreeProcessState(acInfo, task);
/* 2104 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, curTask, task, step, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void appendAssign(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2117 */     String forwardId = curStep.getId();
/* 2118 */     String hql = "from WfCurrentStep where forwardId = '" + forwardId + "' ";
/*      */ 
/* 2126 */     curStep.setSubCount(curStep.getSubCount() + 1);
/* 2127 */     String entryId = curStep.getEntryId();
/* 2128 */     String forwarCode = curStep.getStepCode();
/* 2129 */     WfCurrentStep step = new WfCurrentStep(entryId, forwardId, forwarCode, null, null, null, null, "DEAL");
/* 2130 */     this.currentDao.save(step);
/* 2131 */     BaseTask task = this.taskManager.assignTask(userId, curTask, step, null, acInfo, inputs);
/* 2132 */     setFreeProcessState(acInfo, task);
/* 2133 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, curTask, task, step, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void assist(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2146 */     String entryId = curStep.getEntryId();
/* 2147 */     String forwardId = curStep.getId();
/* 2148 */     String forwardCode = curStep.getStepCode();
/* 2149 */     WfCurrentStep step = new WfCurrentStep(entryId, forwardId, forwardCode, null, null, null, null, "ASSIST");
/*      */ 
/* 2151 */     String parentStepGroup = CommonUtils.getVal(inputs, "parent_stepGroup");
/* 2152 */     if (org.apache.commons.lang.StringUtils.isNotBlank(parentStepGroup)) {
/* 2153 */       step.setStepGroup(parentStepGroup);
/*      */     }
/* 2155 */     this.currentDao.save(step);
/* 2156 */     BaseTask task = this.taskManager.assignTask(userId, curTask, step, null, acInfo, inputs);
/* 2157 */     setFreeProcessState(acInfo, task);
/* 2158 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, curTask, task, step, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void copy(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2171 */     String entryId = curStep.getEntryId();
/* 2172 */     String forwardId = curStep.getId();
/* 2173 */     String forwardCode = curStep.getStepCode();
/* 2174 */     WfCurrentStep step = new WfCurrentStep(entryId, forwardId, forwardCode, null, null, null, null, "COPY");
/* 2175 */     this.currentDao.save(step);
/* 2176 */     BaseTask task = this.taskManager.assignTask(userId, curTask, step, null, acInfo, inputs);
/* 2177 */     setFreeProcessState(acInfo, task);
/* 2178 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, curTask, task, step, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void reAssign(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2191 */     String entryId = curStep.getEntryId();
/* 2192 */     String origId = org.apache.commons.lang.StringUtils.isNotBlank(curStep.getOrigId()) ? curStep.getOrigId() : curStep.getForwardId();
/* 2193 */     String origCode = org.apache.commons.lang.StringUtils.isNotBlank(curStep.getOrigCode()) ? curStep.getOrigCode() : curStep.getForwardCode();
/* 2194 */     WfCurrentStep step = new WfCurrentStep(entryId, curStep.getId(), curStep.getStepCode(), null, null, origId, origCode, "DEAL");
/* 2195 */     this.currentDao.save(step);
/* 2196 */     BaseTask task = this.taskManager.assignTask(userId, curTask, step, null, acInfo, inputs);
/* 2197 */     setFreeProcessState(acInfo, task);
/* 2198 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, curTask, task, step, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void auditing(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2205 */     String actionType = acInfo.getActionType();
/* 2206 */     log.info("审批类型 actionType:" + actionType);
/* 2207 */     if (actionType.equals("AUDITINGNOPASS")) {
/* 2208 */       this.taskManager.cancelAuditTask((WfCurrentStep)curStep, curTask);
/*      */ 
/* 2210 */       inputs.put(WfEngineConstants.wfFreeAuditResult, new DataField(WfEngineConstants.wfFreeAuditResult, FieldDataType.String, WfEngineConstants.wfFreeAuditResult_0)); } else {
/* 2211 */       if (actionType.equals("REAUDIT")) {
/* 2212 */         reAssign(curStep, curTask, userId, acInfo, inputs);
/* 2213 */         return;
/*      */       }
/* 2215 */       inputs.put(WfEngineConstants.wfFreeAuditResult, new DataField(WfEngineConstants.wfFreeAuditResult, FieldDataType.String, WfEngineConstants.wfFreeAuditResult_1));
/*      */     }
/* 2217 */     String historyStepId = org.apache.commons.lang.StringUtils.isNotBlank(curStep.getOrigId()) ? curStep.getOrigId() : curStep.getForwardId();
/* 2218 */     backDeal(historyStepId, curStep, curTask, userId, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void sendBack(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2231 */     curStep.setSubCount(curStep.getSubCount() + 1);
/* 2232 */     String rejStepId = acInfo.getStepId();
/* 2233 */     WfHistoryStep hisStep = (WfHistoryStep)this.historyDao.get(rejStepId);
/* 2234 */     String forwardId = curStep.getId();
/* 2235 */     String forwardCode = curStep.getStepCode();
/* 2236 */     String backwardId = hisStep.getBackwardId();
/* 2237 */     String backwardCode = hisStep.getBackwardCode();
/* 2238 */     String origId = hisStep.getOrigId();
/* 2239 */     String origCode = hisStep.getOrigCode();
/* 2240 */     String stepCode = hisStep.getStepCode();
/* 2241 */     String entryId = curStep.getEntryId();
/* 2242 */     WfCurrentStep step = new WfCurrentStep(entryId, forwardId, forwardCode, backwardId, backwardCode, origId, origCode, "DEAL");
/* 2243 */     step.setStepCode(stepCode);
/* 2244 */     this.currentDao.save(step);
/* 2245 */     BaseTask hisTask = this.taskManager.getHistoryTaskByStepId(hisStep.getId());
/* 2246 */     BaseTask task = hisTask.toCurrent();
/* 2247 */     String actorType = task.getActorType();
/* 2248 */     acInfo.setActorType(actorType);
/* 2249 */     if ("USER".equals(actorType)) {
/* 2250 */       acInfo.setActorId(task.getAssigneeId());
/*      */     } else {
/* 2252 */       acInfo.setActorId(task.getAssignGroupId());
/* 2253 */       acInfo.setDealType(task.getDealType());
/*      */     }
/* 2255 */     task.setStepId(step.getId());
/* 2256 */     this.taskManager.saveCurrentTask(task);
/* 2257 */     setFreeProcessState(acInfo, task);
/* 2258 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, curTask, task, step, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void finish(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2271 */     String historyStepId = org.apache.commons.lang.StringUtils.isNotBlank(curStep.getOrigId()) ? curStep.getOrigId() : curStep.getForwardId();
/* 2272 */     String stepGroup = curStep.getStepGroup();
/* 2273 */     if ((stepGroup != null) && (stepGroup.length() > 0)) {
/* 2274 */       DataField dataField = new DataField();
/* 2275 */       dataField.setName("curStepID");
/* 2276 */       dataField.setValue(curStep.getId());
/* 2277 */       inputs.put("FreeSubStepGroup_CurStepID", dataField);
/*      */     }
/* 2279 */     backDeal(historyStepId, curStep, curTask, userId, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void reject(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2292 */     String stepId = acInfo.getStepId();
/* 2293 */     backDeal(stepId, curStep, curTask, userId, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void backDeal(String historyStepId, Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2307 */     String actionType = acInfo.getActionType();
/* 2308 */     String entryId = curStep.getEntryId();
/* 2309 */     String type = curStep.getType();
/* 2310 */     String forwardCode = curStep.getForwardCode();
/* 2311 */     String stepGroup = curStep.getStepGroup();
/* 2312 */     Boolean isTrans = Boolean.valueOf(WfEngineUtils.isTransStep(curStep));
/* 2313 */     if ((!"BEGIN".equals(forwardCode)) && ((isTrans.booleanValue()) || (("ASSIST".equals(type)) && (isSubFlow(entryId))))) {
/* 2314 */       WfHistoryStep hisStep = (WfHistoryStep)this.historyDao.get(historyStepId);
/* 2315 */       if (hisStep.getSubCount() > 0) {
/* 2316 */         hisStep.setFinishCount(hisStep.getFinishCount() + 1);
/*      */       }
/* 2318 */       String forwardId = hisStep.getForwardId();
/* 2319 */       String forwarCode = hisStep.getForwardCode();
/* 2320 */       String stepCode = hisStep.getStepCode();
/* 2321 */       String origId = hisStep.getOrigId();
/* 2322 */       String origCode = hisStep.getOrigCode();
/*      */ 
/* 2324 */       if ((stepGroup == null) || (stepGroup.length() == 0)) {
/* 2325 */         stepGroup = hisStep.getStepGroup();
/*      */       }
/*      */ 
/* 2328 */       log.info("分派个数（subCount）：" + hisStep.getSubCount() + "，完成个数（finishCount）：" + hisStep.getFinishCount());
/* 2329 */       boolean res = canActiveStep(curStep, curTask, inputs, stepCode);
/* 2330 */       if (res)
/* 2331 */         if ((isFreeSubFlow(entryId)) && ("BEGIN".equalsIgnoreCase(forwarCode)))
/*      */         {
/* 2333 */           if ((isTrans.booleanValue()) || ("ASSIST".equals(type)))
/* 2334 */             activeParent(actionType, userId, entryId, inputs, null);
/*      */         }
/*      */         else {
/* 2337 */           BaseTask hisTask = this.taskManager.getHistoryTaskByStepId(historyStepId);
/* 2338 */           WfCurrentStep step = new WfCurrentStep(entryId, forwardId, forwarCode, null, null, origId, origCode, "DEAL");
/* 2339 */           step.setStepCode(stepCode);
/* 2340 */           step.setStepGroup(stepGroup);
/* 2341 */           this.currentDao.save(step);
/* 2342 */           BaseTask task = hisTask.toCurrent();
/* 2343 */           task.setStepId(step.getId());
/* 2344 */           DataField baseSummary = (DataField)inputs.get(WfEngineConstants.INPUTS_BASESUMMARY);
/* 2345 */           DataField baseItems = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEITEMS);
/* 2346 */           DataField basePriority = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEPRIORITY);
/* 2347 */           task.setWorkSheetTitle(baseSummary != null ? baseSummary.getValue() : null);
/* 2348 */           task.setWorkSheetProType(baseItems != null ? baseItems.getValue() : null);
/* 2349 */           task.setWorkSheetUrgentLevel(basePriority != null ? basePriority.getValue() : null);
/* 2350 */           this.taskManager.saveCurrentTask(task);
/* 2351 */           String actorType = task.getActorType();
/* 2352 */           acInfo.setActorType(actorType);
/* 2353 */           if ("USER".equals(actorType)) {
/* 2354 */             acInfo.setActorId(task.getAssigneeId());
/*      */           } else {
/* 2356 */             acInfo.setActorId(task.getAssignGroupId());
/* 2357 */             acInfo.setDealType(task.getDealType());
/*      */           }
/* 2359 */           if (isFreeFlow(entryId)) {
/* 2360 */             ThreadUtils.setIsAllFinish();
/* 2361 */             setFreeProcessState(acInfo, task);
/* 2362 */             WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, null, task, step, acInfo, inputs);
/* 2363 */             if (forwarCode.equals("BEGIN"))
/* 2364 */               curTask.setEntryState(WfEngineUtils.entryState(actionType));
/*      */           }
/*      */           else {
/* 2367 */             String defStepCode = acInfo.getStepCode();
/* 2368 */             StepDescriptor stDesc = getStDescById(entryId, defStepCode);
/* 2369 */             setDefProcessState(stDesc, acInfo, task);
/* 2370 */             WfEngineUtils.executeFunction(stDesc.getPreFuncList(), userId, null, task, step, stDesc, acInfo, inputs);
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void reNext(Step curStep, BaseTask curTask, String userId, List<ActionInfo> acInfos, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2387 */     if ((curTask != null) && (CollectionUtils.isNotEmpty(acInfos))) {
/* 2388 */       ActionInfo acInfo = (ActionInfo)acInfos.get(0);
/* 2389 */       BaseTask task = this.taskManager.assignTask(userId, curTask, curStep, null, acInfo, inputs);
/* 2390 */       StepDescriptor tarStDesc = getStDescById(curStep.getEntryId(), curStep.getStepCode());
/* 2391 */       curTask.setProcessState("已" + WfEngineUtils.getDefActionName(inputs));
/*      */ 
/* 2393 */       WfEngineUtils.setTaskDealer(curTask, userId);
/* 2394 */       CurrentProcessTask cTask = (CurrentProcessTask)curTask;
/* 2395 */       cTask.setFlagActive(0L);
/* 2396 */       cTask.setEdProcessAction(WfEngineUtils.getDefActionName(inputs));
/*      */ 
/* 2398 */       this.taskManager.moveToHistory(curTask);
/* 2399 */       WfEngineUtils.executeFunction(tarStDesc.getPostFuncList(), userId, null, curTask, curStep, null, new ActionInfo("RENEXT"), inputs);
/* 2400 */       WfEngineUtils.executeFunction(tarStDesc.getPreFuncList(), userId, curTask, task, curStep, tarStDesc, acInfo, inputs);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean canActiveStep(Step curStep, BaseTask curTask, Map<String, DataField> inputs, String tarStepCode)
/*      */   {
/* 2440 */     String baseId = curTask.getBaseId();
/* 2441 */     String baseSchema = curTask.getBaseSchema();
/* 2442 */     String entryId = curTask.getEntryId();
/* 2443 */     String thisStepId = curStep.getId();
/* 2444 */     CanActiveStep canActive = (CanActiveStep)ApplicationContextUtils.getBean("canActiveStep");
/* 2445 */     return canActive.judge(baseId, baseSchema, entryId, thisStepId, tarStepCode);
/*      */   }
/*      */ 
/*      */   protected void close(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void accept(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2473 */     this.taskManager.assignTask(userId, curTask, null, null, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void chase(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2486 */     String stepId = acInfo.getStepId();
/* 2487 */     String actionType = acInfo.getActionType();
/* 2488 */     String entryId = curStep.getEntryId();
/* 2489 */     log.info("追回环节的stepId[" + stepId + "]");
/* 2490 */     if (org.apache.commons.lang.StringUtils.isNotBlank(stepId)) {
/* 2491 */       WfCurrentStep currStep = (WfCurrentStep)this.currentDao.get(stepId);
/* 2492 */       BaseTask currTask = this.taskManager.getCurrentTaskByStepId(stepId);
/* 2493 */       if (currTask == null) {
/* 2494 */         throw new WorkflowEngineException("目标stepId[" + stepId + "]的任务不存在，可能已经结束！");
/*      */       }
/* 2496 */       String currTaskId = currTask.getId();
/*      */ 
/* 2504 */       String forwardId = currStep.getForwardId();
/* 2505 */       String forwardCode = currStep.getForwardCode();
/* 2506 */       String origId = currStep.getOrigId();
/* 2507 */       String origCode = currStep.getOrigCode();
/* 2508 */       String chaseId = null;
/* 2509 */       String chaseCode = null;
/*      */ 
/* 2511 */       if ((org.apache.commons.lang.StringUtils.isNotBlank(forwardId)) && (!"BEGIN".equals(forwardCode))) {
/* 2512 */         chaseId = forwardId;
/* 2513 */         chaseCode = forwardCode;
/* 2514 */       } else if ((org.apache.commons.lang.StringUtils.isNotBlank(origId)) && (!"BEGIN".equals(origCode))) {
/* 2515 */         chaseId = origId;
/* 2516 */         chaseCode = origCode;
/*      */       }
/* 2518 */       if ((org.apache.commons.lang.StringUtils.isNotBlank(chaseId)) && (org.apache.commons.lang.StringUtils.isNotBlank(chaseCode))) {
/* 2519 */         WfHistoryStep historyStep = (WfHistoryStep)this.historyDao.get(chaseId);
/* 2520 */         String forwarCode = historyStep.getForwardCode();
/*      */ 
/* 2522 */         this.currentDao.remove(currStep);
/* 2523 */         this.taskManager.deleteCurrentTaskById(currTaskId);
/*      */ 
/* 2525 */         WfEngineUtils.getDefaultFreePostFunction(acInfo).execute(userId, null, currTask, curStep, acInfo, inputs);
/* 2526 */         if (!WfEngineUtils.isTransStep(currStep)) {
/* 2527 */           log.info("当前追回的是非流转环节，不影响前置环节的激活！");
/* 2528 */           return;
/*      */         }
/*      */ 
/* 2531 */         int subCount = historyStep.getSubCount();
/* 2532 */         if (subCount > 0) {
/* 2533 */           historyStep.setSubCount(subCount - 1);
/*      */         }
/* 2535 */         log.info("forwardId:" + chaseId + ",总共派发:" + historyStep.getSubCount() + ",已经完成:" + historyStep.getFinishCount());
/* 2536 */         boolean res = canActiveStep(curStep, curTask, inputs, chaseCode);
/*      */ 
/* 2538 */         if (res)
/* 2539 */           if ((isFreeSubFlow(entryId)) && ("BEGIN".equalsIgnoreCase(forwarCode)) && (WfEngineUtils.isTransStep(curStep))) {
/* 2540 */             activeParent(actionType, userId, entryId, inputs, null);
/*      */           } else {
/* 2542 */             log.info("已经追回了全部任务，激活父任务！");
/* 2543 */             WfCurrentStep parentStep = historyStep.toCurrent();
/* 2544 */             this.currentDao.save(parentStep);
/* 2545 */             BaseTask historyTask = this.taskManager.getHistoryTaskByStepId(chaseId);
/* 2546 */             BaseTask rtnTask = historyTask.toCurrent();
/* 2547 */             rtnTask.setStepId(parentStep.getId());
/* 2548 */             this.taskManager.saveCurrentTask(rtnTask);
/* 2549 */             String actorType = rtnTask.getActorType();
/* 2550 */             acInfo.setActorType(actorType);
/* 2551 */             if ("USER".equals(actorType)) {
/* 2552 */               String assigneeId = rtnTask.getAssigneeId();
/*      */ 
/* 2555 */               if ((assigneeId != null) && (assigneeId.length() > 0))
/* 2556 */                 acInfo.setActorId(rtnTask.getAssigneeId());
/*      */               else
/* 2558 */                 acInfo.setActorId(rtnTask.getDealerId());
/*      */             }
/*      */             else {
/* 2561 */               acInfo.setActorId(rtnTask.getAssignGroupId());
/* 2562 */               acInfo.setDealType(rtnTask.getDealType());
/*      */             }
/* 2564 */             if (isFreeFlow(entryId)) {
/* 2565 */               setFreeProcessState(acInfo, rtnTask);
/* 2566 */               WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, null, rtnTask, parentStep, acInfo, inputs);
/* 2567 */               if (("BEGIN".equalsIgnoreCase(forwarCode)) && (WfEngineUtils.isTransStep(curStep)))
/* 2568 */                 rtnTask.setEntryState(WfEngineUtils.entryState("NEW"));
/*      */             }
/*      */             else {
/* 2571 */               StepDescriptor stDesc = getStDescById(parentStep.getEntryId(), parentStep.getStepCode());
/* 2572 */               rtnTask.setEntryState(stDesc.getStatusName());
/* 2573 */               setFreeProcessState(acInfo, rtnTask);
/* 2574 */               WfEngineUtils.executeFunction(stDesc.getPreFuncList(), userId, null, rtnTask, parentStep, stDesc, acInfo, inputs);
/*      */             }
/*      */           }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void chaseAll(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2592 */     BaseTask newTask = this.taskManager.assignTask(userId, curTask, curStep, null, acInfo, inputs);
/* 2593 */     String entryId = newTask.getTopEntryId();
/* 2594 */     String stepId = newTask.getStepId();
/* 2595 */     String hql = "delete from WfCurrentStep where entryId='" + entryId + "'";
/* 2596 */     this.currentDao.executeUpdate(hql, null);
/* 2597 */     WfHistoryStep hisStep = (WfHistoryStep)this.historyDao.get(stepId);
/* 2598 */     this.historyDao.remove(hisStep);
/* 2599 */     WfCurrentStep current = hisStep.toCurrent();
/* 2600 */     this.currentDao.save(current);
/* 2601 */     newTask.setStepId(current.getId());
/* 2602 */     newTask.setStepCode(current.getStepCode());
/* 2603 */     setFreeProcessState(acInfo, newTask);
/* 2604 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, null, newTask, current, acInfo, inputs);
/*      */   }
/*      */ 
/*      */   protected void customAction(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/* 2611 */     throw new WorkflowEngineException("流程引擎没有匹配的执行动作！actionType=" + acInfo.getActionType() + ";actionName=" + acInfo.getActionName());
/*      */   }
/*      */ 
/*      */   protected void suspend(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void active(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void cancel(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*      */     throws WorkflowException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void setFreeProcessState(ActionInfo acInfo, BaseTask task)
/*      */   {
/* 2661 */     String actionType = acInfo.getActionType();
/* 2662 */     String inProcessState = WfEngineUtils.inProcessState(actionType);
/* 2663 */     if (org.apache.commons.lang.StringUtils.isNotBlank(inProcessState))
/* 2664 */       task.setProcessState(inProcessState);
/*      */   }
/*      */ 
/*      */   private void setDefProcessState(StepDescriptor stDesc, ActionInfo acInfo, BaseTask task)
/*      */   {
/* 2679 */     task.setProcessState("待处理");
/*      */   }
/*      */ 
/*      */   protected abstract void terminate(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap);
/*      */ 
/*      */   protected void checkEntry(String entryId, String actionId, String actionType)
/*      */     throws WorkflowEngineException
/*      */   {
/* 2699 */     if (org.apache.commons.lang.StringUtils.isNotBlank(entryId)) {
/* 2700 */       WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 2701 */       if (entry != null) {
/* 2702 */         log.info("流程实例状态,entryId=" + entryId + ", state=" + entry.getState());
/* 2703 */         String state = entry.getState();
/* 2704 */         if (state.equals("finish"))
/* 2705 */           throw new WorkflowEngineException("流程实例已结束！entryId=" + entryId);
/* 2706 */         if (state.equals("cancel"))
/* 2707 */           throw new WorkflowEngineException("流程实例已作废！entryId=" + entryId);
/* 2708 */         if ((state.equals("suspend")) && (
/* 2709 */           (!org.apache.commons.lang.StringUtils.isNotBlank(actionType)) || (!actionType.equals("ACTIVE"))))
/* 2710 */           throw new WorkflowEngineException("流程实例已挂起！entryId=" + entryId);
/*      */       }
/*      */       else
/*      */       {
/* 2714 */         throw new WorkflowEngineException("流程实例不存在！entryId=" + entryId);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected WorkflowDescriptor getWfDesc(String defName) throws WorkflowDefineException {
/* 2720 */     WorkflowDescriptor wfDesc = this.factory.getWorkflowDescriptor(defName);
/* 2721 */     if (wfDesc == null) {
/* 2722 */       throw new WorkflowDefineException("不存在名称为" + defName + "的WorkflowDescriptor！");
/*      */     }
/* 2724 */     return wfDesc;
/*      */   }
/*      */ 
/*      */   protected WorkflowDescriptor getWfDescById(String entryId) throws WorkflowDefineException {
/* 2728 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 2729 */     return getWfDesc(entry.getDefName());
/*      */   }
/*      */ 
/*      */   protected StepDescriptor getStDesc(String defName, String stepCode) throws WorkflowDefineException {
/* 2733 */     StepDescriptor stDesc = getWfDesc(defName).getStepDescriptor(stepCode);
/* 2734 */     if (stDesc == null) {
/* 2735 */       throw new WorkflowDefineException(defName + "文件中不存在stepCode为" + stepCode + "的StepDescriptor！");
/*      */     }
/* 2737 */     return stDesc;
/*      */   }
/*      */ 
/*      */   protected StepDescriptor getStDescById(String entryId, String stepCode) throws WorkflowDefineException {
/* 2741 */     StepDescriptor stDesc = getWfDescById(entryId).getStepDescriptor(stepCode);
/* 2742 */     if (stDesc == null) {
/* 2743 */       throw new WorkflowDefineException(entryId + "文件中不存在stepCode为" + stepCode + "的StepDescriptor！");
/*      */     }
/* 2745 */     return stDesc;
/*      */   }
/*      */ 
/*      */   protected SplitDescriptor getSpDescById(String entryId, String splitCode) throws WorkflowDefineException {
/* 2749 */     SplitDescriptor spDesc = getWfDescById(entryId).getSplitDescriptor(splitCode);
/* 2750 */     if (spDesc == null) {
/* 2751 */       throw new WorkflowDefineException(entryId + "文件中不存在splitId为" + splitCode + "的SplitDescriptor！");
/*      */     }
/* 2753 */     return spDesc;
/*      */   }
/*      */ 
/*      */   protected SplitDescriptor getSpDesc(String defName, String splitCode) throws WorkflowDefineException {
/* 2757 */     SplitDescriptor spDesc = getWfDesc(defName).getSplitDescriptor(splitCode);
/* 2758 */     if (spDesc == null) {
/* 2759 */       throw new WorkflowDefineException(defName + "文件中不存在splitId为" + splitCode + "的SplitDescriptor！");
/*      */     }
/* 2761 */     return spDesc;
/*      */   }
/*      */ 
/*      */   protected JoinDescriptor getJnDesc(String defName, String jnCode) throws WorkflowDefineException {
/* 2765 */     JoinDescriptor jnDesc = getWfDesc(defName).getJoinDescriptor(jnCode);
/* 2766 */     if (jnDesc == null) {
/* 2767 */       throw new WorkflowDefineException(defName + "文件中不存在joinId为" + jnCode + "的JoinDescriptor！");
/*      */     }
/* 2769 */     return jnDesc;
/*      */   }
/*      */ 
/*      */   protected JoinDescriptor getJnDescById(String entyrId, String jnCode) throws WorkflowDefineException {
/* 2773 */     JoinDescriptor jnDesc = getWfDescById(entyrId).getJoinDescriptor(jnCode);
/* 2774 */     if (jnDesc == null) {
/* 2775 */       throw new WorkflowDefineException(entyrId + "文件中不存在joinId为" + jnCode + "的JoinDescriptor！");
/*      */     }
/* 2777 */     return jnDesc;
/*      */   }
/*      */ 
/*      */   protected ActionDescriptor getAcDesc(StepDescriptor stDesc, String acId)
/*      */     throws WorkflowDefineException
/*      */   {
/* 2788 */     ActionDescriptor acDesc = null;
/* 2789 */     if (stDesc != null) {
/* 2790 */       if (org.apache.commons.lang.StringUtils.isBlank(acId)) {
/* 2791 */         List actionList = stDesc.getActionList();
/* 2792 */         if (CollectionUtils.isNotEmpty(actionList))
/* 2793 */           acDesc = (ActionDescriptor)actionList.get(0);
/*      */       }
/*      */       else {
/* 2796 */         List actionList = stDesc.getActionList();
/* 2797 */         if (CollectionUtils.isNotEmpty(actionList)) {
/* 2798 */           for (int i = 0; i < actionList.size(); i++) {
/* 2799 */             ActionDescriptor ac = (ActionDescriptor)actionList.get(i);
/* 2800 */             if (ac != null) {
/* 2801 */               String actionNo = ac.getActionNo();
/* 2802 */               if (acId.equals(actionNo)) {
/* 2803 */                 acDesc = ac;
/* 2804 */                 break;
/*      */               }
/*      */             }
/*      */           }
/* 2808 */           if (acDesc == null) {
/* 2809 */             acDesc = stDesc.getActionById(acId);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2817 */     return acDesc;
/*      */   }
/*      */ 
/*      */   protected StartDescriptor getStartDesc(String defName)
/*      */     throws WorkflowDefineException
/*      */   {
/* 2827 */     StartDescriptor startDesc = getWfDesc(defName).getStartDesc();
/* 2828 */     if (startDesc == null) {
/* 2829 */       throw new WorkflowDefineException(defName + "文件中不存在StartDescriptor！");
/*      */     }
/* 2831 */     return startDesc;
/*      */   }
/*      */ 
/*      */   protected EndDescriptor getEndDescById(String entryId, String endId) throws WorkflowDefineException {
/* 2835 */     EndDescriptor endDesc = getWfDescById(entryId).getEndDescriptor(endId);
/* 2836 */     if (endDesc == null) {
/* 2837 */       throw new WorkflowDefineException(entryId + "文件中不存在id为 " + endId + " 的EndDescriptor！");
/*      */     }
/* 2839 */     return endDesc;
/*      */   }
/*      */ 
/*      */   protected EndDescriptor getEndDesc(String defName, String endId) throws WorkflowDefineException {
/* 2843 */     EndDescriptor endDesc = getWfDesc(defName).getEndDescriptor(endId);
/* 2844 */     if (endDesc == null) {
/* 2845 */       throw new WorkflowDefineException(defName + "文件中不存在id为 " + endId + " 的EndDescriptor！");
/*      */     }
/* 2847 */     return endDesc;
/*      */   }
/*      */ 
/*      */   public boolean isSubFlow(String entryId)
/*      */   {
/* 2856 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 2857 */     return org.apache.commons.lang.StringUtils.isNotBlank(entry.getParentEntryId());
/*      */   }
/*      */ 
/*      */   public boolean isFreeSubFlow(String entryId)
/*      */   {
/* 2866 */     return (isSubFlow(entryId)) && (isFreeFlow(entryId));
/*      */   }
/*      */ 
/*      */   public boolean isFreeFlow(String entryId)
/*      */   {
/* 2875 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 2876 */     String type = entry.getType();
/* 2877 */     return type.equals("FREE");
/*      */   }
/*      */ 
/*      */   public boolean isFixFlow(String entryId)
/*      */   {
/* 2886 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 2887 */     String type = entry.getType();
/* 2888 */     return type.equals("DEFINE");
/*      */   }
/*      */ 
/*      */   private boolean isClassicSubflow(StepDescriptor tarStDesc)
/*      */   {
/* 2897 */     boolean flag = false;
/* 2898 */     if (tarStDesc != null) {
/* 2899 */       SubflowsDescriptor subflows = tarStDesc.getSubflows();
/* 2900 */       flag = (subflows != null) && (!subflows.getSubflowMap().isEmpty()) && (tarStDesc.isAuto());
/*      */     }
/* 2902 */     return flag;
/*      */   }
/*      */ 
/*      */   private boolean isInsideFreeSubflow(String actionType, StepDescriptor tarStDesc, Step curStep)
/*      */   {
/* 2913 */     boolean flag = false;
/* 2914 */     if ((tarStDesc != null) && (curStep != null)) {
/* 2915 */       String tarStepCode = tarStDesc.getId();
/* 2916 */       String srcCode = curStep.getStepCode();
/* 2917 */       boolean autoStep = tarStDesc.isAuto();
/*      */ 
/* 2919 */       boolean freeAction = !actionType.equals("NEXT");
/* 2920 */       flag = (freeAction) && (!autoStep);
/*      */     }
/* 2922 */     log.info("是否是固定流程的[内部]自由子流程[" + flag + "]");
/* 2923 */     return flag;
/*      */   }
/*      */ 
/*      */   private boolean isClassicFreeSubflow(String actionType, StepDescriptor tarStDesc)
/*      */   {
/* 2934 */     boolean flag = false;
/* 2935 */     if (tarStDesc != null) {
/* 2936 */       boolean autoStep = tarStDesc.isAuto();
/* 2937 */       boolean freeAction = !actionType.equals("NEXT");
/* 2938 */       flag = (freeAction) && (autoStep);
/*      */     }
/* 2940 */     log.info("是否是固定流程的传统自由子流程[" + flag + "]");
/* 2941 */     return flag;
/*      */   }
/*      */ 
/*      */   private List<WfCurrentStep> getCurrentSteps(String entryId)
/*      */   {
/* 2950 */     String hql = "from WfCurrentStep where entryId = '" + entryId + "' order by createTime desc, id desc";
/* 2951 */     List list = this.currentDao.find(hql, null);
/* 2952 */     return list;
/*      */   }
/*      */ 
/*      */   private List<WfHistoryStep> getHistorySteps(String entryId)
/*      */   {
/* 2961 */     String hql = "from WfHistoryStep where entryId = '" + entryId + "' order by createTime desc, id desc";
/* 2962 */     List list = this.historyDao.find(hql, null);
/* 2963 */     return list;
/*      */   }
/*      */ 
/*      */   protected List<WfCurrentStep> getTransCurrentSteps(String entryId)
/*      */   {
/* 2972 */     List rtn = new ArrayList();
/* 2973 */     List currentSteps = getCurrentSteps(entryId);
/* 2974 */     if (CollectionUtils.isNotEmpty(currentSteps)) {
/* 2975 */       for (int i = 0; i < currentSteps.size(); i++) {
/* 2976 */         WfCurrentStep wfCurrentStep = (WfCurrentStep)currentSteps.get(i);
/* 2977 */         if (WfEngineUtils.isTransStep(wfCurrentStep)) {
/* 2978 */           rtn.add(wfCurrentStep);
/*      */         }
/*      */       }
/*      */     }
/* 2982 */     return rtn;
/*      */   }
/*      */ 
/*      */   protected List<WfCurrentStep> getTransCurrentSteps(BaseTask curTask)
/*      */   {
/* 2989 */     List rtn = new ArrayList();
/*      */ 
/* 2991 */     List currTaskList = this.currTaskDao.find("from CurrentProcessTask where baseId=?", new Object[] { curTask.getBaseId() });
/* 2992 */     StringBuffer stepsql = new StringBuffer("from  WfCurrentStep where ");
/* 2993 */     StringBuffer condition = new StringBuffer();
/* 2994 */     Map entryIdAndStepGroup = new HashMap();
/*      */     String stepgroup;
/* 2995 */     for (CurrentProcessTask cTask : currTaskList)
/*      */     {
/* 2997 */       stepgroup = cTask.getStepGroup();
/* 2998 */       String stepid = cTask.getStepId();
/* 2999 */       String entryid = cTask.getEntryId();
/* 3000 */       if ((stepgroup != null) && (stepgroup.length() > 0)) {
/* 3001 */         entryIdAndStepGroup.put(stepid, stepgroup);
/* 3002 */         if (condition.length() == 0) {
/* 3003 */           condition.append(" entryId='" + entryid + "' ");
/*      */         }
/*      */         else {
/* 3006 */           condition.append(" or entryId='" + entryid + "' ");
/*      */         }
/*      */       }
/*      */     }
/* 3010 */     if (condition.length() > 0)
/*      */     {
/* 3012 */       stepsql.append(condition);
/* 3013 */       List currentSteps = this.currentDao.find(stepsql.toString(), new Object[0]);
/* 3014 */       for (WfCurrentStep cStep : currentSteps) {
/* 3015 */         String id = cStep.getId();
/* 3016 */         String type = cStep.getType();
/* 3017 */         String stepgroup = cStep.getStepGroup();
/* 3018 */         if (WfEngineUtils.isTransStep(cStep)) {
/* 3019 */           if ((stepgroup == null) || (stepgroup.length() == 0)) {
/* 3020 */             cStep.setStepGroup((String)entryIdAndStepGroup.get(id));
/*      */           }
/* 3022 */           rtn.add(cStep);
/*      */         }
/*      */       }
/*      */     }
/* 3026 */     return rtn;
/*      */   }
/*      */ 
/*      */   protected List<WfHistoryStep> getTransHistorySteps(String entryId)
/*      */   {
/* 3035 */     List rtn = new ArrayList();
/* 3036 */     List historySteps = getHistorySteps(entryId);
/* 3037 */     if (CollectionUtils.isNotEmpty(historySteps)) {
/* 3038 */       for (int i = 0; i < historySteps.size(); i++) {
/* 3039 */         WfHistoryStep wfHistoryStep = (WfHistoryStep)historySteps.get(i);
/* 3040 */         if (WfEngineUtils.isTransStep(wfHistoryStep)) {
/* 3041 */           rtn.add(wfHistoryStep);
/*      */         }
/*      */       }
/*      */     }
/* 3045 */     return rtn;
/*      */   }
/*      */ 
/*      */   protected List<WfHistoryStep> getHistoryStepsByFlowId(String entryId, String flowId) {
/* 3049 */     String hql = "from WfHistoryStep where entryId = '" + entryId + "' and flowId = '" + flowId + "'";
/* 3050 */     List list = this.historyDao.find(hql, null);
/* 3051 */     return list;
/*      */   }
/*      */ 
/*      */   protected void changeEntry(String entryId, String state) {
/* 3055 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 3056 */     if ("active".equals(state))
/* 3057 */       entry.setCloseTime(0L);
/*      */     else {
/* 3059 */       entry.setCloseTime(TimeUtils.getCurrentTime());
/*      */     }
/* 3061 */     entry.setState(state);
/* 3062 */     this.entryDao.save(entry);
/*      */   }
/*      */ 
/*      */   private void moveToHistory(WfCurrentStep curStep) {
/* 3066 */     this.currentDao.remove(curStep);
/* 3067 */     this.historyDao.save(curStep.toHistory());
/*      */   }
/*      */ 
/*      */   private WfCurrentStep moveToCurrent(WfHistoryStep hisStep) {
/* 3071 */     this.historyDao.remove(hisStep);
/* 3072 */     WfCurrentStep current = hisStep.toCurrent();
/* 3073 */     this.currentDao.save(current);
/* 3074 */     return current;
/*      */   }
/*      */ 
/*      */   public ITaskManager getTaskManager() {
/* 3078 */     return this.taskManager;
/*      */   }
/*      */ 
/*      */   public void setTaskManager(ITaskManager taskManager) {
/* 3082 */     this.taskManager = taskManager;
/*      */   }
/*      */ 
/*      */   public IDao<WfEntry> getEntryDao() {
/* 3086 */     return this.entryDao;
/*      */   }
/*      */ 
/*      */   public void setEntryDao(IDao<WfEntry> entryDao) {
/* 3090 */     this.entryDao = entryDao;
/*      */   }
/*      */ 
/*      */   public IDao<WfCurrentStep> getCurrentDao() {
/* 3094 */     return this.currentDao;
/*      */   }
/*      */ 
/*      */   public void setCurrentDao(IDao<WfCurrentStep> currentDao) {
/* 3098 */     this.currentDao = currentDao;
/*      */   }
/*      */ 
/*      */   public IDao<WfHistoryStep> getHistoryDao() {
/* 3102 */     return this.historyDao;
/*      */   }
/*      */ 
/*      */   public void setHistoryDao(IDao<WfHistoryStep> historyDao) {
/* 3106 */     this.historyDao = historyDao;
/*      */   }
/*      */ 
/*      */   public WorkflowFactory getFactory() {
/* 3110 */     return this.factory;
/*      */   }
/*      */ 
/*      */   public void setFactory(WorkflowFactory factory) {
/* 3114 */     this.factory = factory;
/*      */   }
/*      */ 
/*      */   public IRoleService getRoleService() {
/* 3118 */     return this.roleService;
/*      */   }
/*      */ 
/*      */   public void setRoleService(IRoleService roleService) {
/* 3122 */     this.roleService = roleService;
/*      */   }
/*      */ 
/*      */   public IDao<CurrentProcessTask> getCurrTaskDao()
/*      */   {
/* 3127 */     return this.currTaskDao;
/*      */   }
/*      */ 
/*      */   public void setCurrTaskDao(IDao<CurrentProcessTask> currTaskDao)
/*      */   {
/* 3132 */     this.currTaskDao = currTaskDao;
/*      */   }
/*      */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.AbstractWorkflowEngine
 * JD-Core Version:    0.6.0
 */