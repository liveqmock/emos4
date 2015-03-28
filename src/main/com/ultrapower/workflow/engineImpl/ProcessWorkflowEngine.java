/*     */ package com.ultrapower.workflow.engineImpl;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*     */ import com.ultrapower.workflow.engine.core.AbstractWorkflowEngine;
/*     */ import com.ultrapower.workflow.engine.core.IDefFunction;
/*     */ import com.ultrapower.workflow.engine.core.IFreeFunction;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.FieldDataType;
/*     */ import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class ProcessWorkflowEngine extends AbstractWorkflowEngine
/*     */ {
/*  40 */   private Logger log = LoggerFactory.getLogger(ProcessWorkflowEngine.class);
/*     */ 
/*     */   protected void activeParent(String actionType, String userId, String entryId, Map<String, DataField> inputs, List<ActionInfo> acTransList)
/*     */   {
/*  50 */     this.log.info("流程实例运行结束！");
/*  51 */     List transCurrentSteps = getTransCurrentSteps(entryId);
/*  52 */     if (CollectionUtils.isEmpty(transCurrentSteps)) {
/*  53 */       changeEntry(entryId, "finish");
/*     */     }
/*  55 */     WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/*  56 */     String entryType = entry.getType();
/*  57 */     String parentFlowId = entry.getParentFlowId();
/*  58 */     String parentEntryId = entry.getParentEntryId();
/*  59 */     if ((StringUtils.isNotBlank(parentEntryId)) && (!"CONFIRM".equals(actionType))) {
/*  60 */       this.log.info("子流程运行完毕，判断是否需要激活父流程！");
/*  61 */       List parHistorySteps = getHistoryStepsByFlowId(parentEntryId, parentFlowId);
/*  62 */       if (CollectionUtils.isNotEmpty(parHistorySteps)) {
/*  63 */         WfHistoryStep wfHistoryStep = (WfHistoryStep)parHistorySteps.get(0);
/*  64 */         wfHistoryStep.setFinishCount(wfHistoryStep.getFinishCount() + 1);
/*  65 */         this.log.info("子流程总数:" + wfHistoryStep.getSubCount() + ",已完成子流程个数:" + wfHistoryStep.getFinishCount());
/*  66 */         if (wfHistoryStep.getFinishCount() == wfHistoryStep.getSubCount()) {
/*  67 */           this.log.info("所有子流程都已完成，激活父流程！");
/*  68 */           if (entryType.equals("DEFINE"))
/*  69 */             defSubActiveParent(userId, wfHistoryStep, inputs, acTransList);
/*  70 */           else if (entryType.equals("FREE")) {
/*  71 */             freeSubActiveParent(actionType, userId, wfHistoryStep, inputs, acTransList);
/*     */           }
/*     */         }
/*  74 */         else if (("AUDITINGNOPASS".equals(actionType)) && 
/*  75 */           (entryType.equals("FREE"))) {
/*  76 */           freeSubActiveParent(actionType, userId, wfHistoryStep, inputs, acTransList);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void defSubActiveParent(String userId, WfHistoryStep hisStep, Map<String, DataField> inputs, List<ActionInfo> acTransList)
/*     */   {
/*  88 */     this.log.info("激活固定子流程的父流程！");
/*  89 */     String parentEntryId = hisStep.getEntryId();
/*  90 */     String parStepCode = hisStep.getStepCode();
/*  91 */     WfCurrentStep currStep = new WfCurrentStep();
/*     */     try {
/*  93 */       BeanUtils.copyProperties(currStep, hisStep);
/*  94 */       this.currentDao.save(currStep);
/*  95 */       this.historyDao.remove(hisStep);
/*     */     } catch (Exception e) {
/*  97 */       e.printStackTrace();
/*     */     }
/*  99 */     ActionDescriptor acDesc = getAcDesc(getStDescById(parentEntryId, parStepCode), null);
/* 100 */     String hql = "from HistoryProcessTask where entryId = '" + parentEntryId + "' and stepCode = '" + parStepCode + "' order by createTime desc";
/* 101 */     List tasks = this.taskManager.queryHistoryTask(hql);
/* 102 */     this.log.info("ProcessWorkflowEngine, size=" + tasks.size() + ",hql=" + hql);
/* 103 */     if (CollectionUtils.isNotEmpty(tasks)) {
/* 104 */       BaseTask tmpTask = (BaseTask)((BaseTask)tasks.get(0)).clone();
/* 105 */       String taskId = tmpTask.getId();
/* 106 */       this.taskManager.deleteHistoryTaskById(taskId);
/* 107 */       BaseTask currTask = tmpTask.toCurrent();
/* 108 */       currTask.setStepCode(parStepCode);
/* 109 */       currTask.setStepId(hisStep.getId());
/* 110 */       currTask.setId(taskId);
/*     */ 
/* 113 */       DataField df = (DataField)inputs.get("NextDefAssigne");
/* 114 */       if (df != null) {
/* 115 */         String assginess = df.getValue();
/* 116 */         this.log.info("下一环节的处理人串 : " + assginess);
/* 117 */         if (StringUtils.isNotBlank(assginess)) {
/* 118 */           String[] preAsses = assginess.split("#;");
/* 119 */           if (!ArrayUtils.isEmpty(preAsses)) {
/* 120 */             List acInfoList = new ArrayList();
/* 121 */             for (int i = 0; i < preAsses.length; i++) {
/* 122 */               String assignee = preAsses[i];
/* 123 */               if (StringUtils.isNotBlank(assignee)) {
/* 124 */                 BizFormUtil.parseAssignStr(acInfoList, assignee);
/*     */               }
/*     */             }
/* 127 */             if (CollectionUtils.isNotEmpty(acInfoList))
/* 128 */               fixTrans("NEXT", null, userId, acInfoList, inputs, currTask, currStep);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         boolean joinFlag;
/* 132 */         if (CollectionUtils.isNotEmpty(acTransList))
/* 133 */           joinFlag = fixTrans("NEXT", null, userId, acTransList, inputs, currTask, currStep);
/*     */         else
/* 135 */           joinFlag = fixTrans("NEXT", null, userId, null, inputs, currTask, currStep);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void freeSubActiveParent(String actionType, String userId, WfHistoryStep wfHistoryStep, Map<String, DataField> inputs, List<ActionInfo> acTransList)
/*     */   {
/* 144 */     String parentEntryId = wfHistoryStep.getEntryId();
/* 145 */     String parStepCode = wfHistoryStep.getStepCode();
/* 146 */     StepDescriptor stDesc = getStDescById(parentEntryId, parStepCode);
/* 147 */     if (stDesc.isAuto()) {
/* 148 */       this.log.info("激活传统自由子流程的父流程！");
/* 149 */       defSubActiveParent(userId, wfHistoryStep, inputs, acTransList);
/*     */     } else {
/* 151 */       this.log.info("激活内部自由子流程的父流程！重新激活DealProcess,WfCurrentStep,WfCurrentTask");
/* 152 */       WfCurrentStep currStep = wfHistoryStep.toCurrent();
/* 153 */       this.currentDao.save(currStep);
/* 154 */       String hql = "from HistoryProcessTask where entryId = '" + parentEntryId + "' and stepCode='" + parStepCode + "' order by createTime desc";
/* 155 */       List tasks = this.taskManager.queryHistoryTask(hql);
/* 156 */       if (CollectionUtils.isNotEmpty(tasks)) {
/* 157 */         BaseTask tmpTask = (BaseTask)((BaseTask)tasks.get(0)).clone();
/* 158 */         BaseTask current = tmpTask.toCurrent();
/* 159 */         ProcessTask pCurrent = (ProcessTask)current;
/* 160 */         pCurrent.setFlagActive(1L);
/* 161 */         pCurrent.setStepId(currStep.getId());
/* 162 */         pCurrent.setStepCode(parStepCode);
/* 163 */         pCurrent.setProcessState("待处理");
/* 164 */         pCurrent.setCreateTime(TimeUtils.getCurrentTime());
/* 165 */         this.taskManager.saveCurrentTask(pCurrent);
/* 166 */         WfEngineUtils.getDefaultFreePreFunction(new ActionInfo("activeparent")).execute(userId, null, pCurrent, currStep, new ActionInfo(actionType), inputs);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected List<ActionInfo> getActorFromDb(Step curStep, String tarStepCode, List<ActionInfo> acList)
/*     */   {
/* 176 */     this.log.info("没有传入处理人也没有指定处理人规则，查询该环节的历史处理人！");
/* 177 */     String hql = "from HistoryProcessTask where entryId = '" + curStep.getEntryId() + "' and stepCode = '" + tarStepCode + "' order by createTime desc";
/* 178 */     List tasks = this.taskManager.queryHistoryTask(hql);
/* 179 */     if (CollectionUtils.isNotEmpty(tasks)) {
/* 180 */       BaseTask hisTask = (BaseTask)tasks.get(0);
/* 181 */       String actorType = hisTask.getActorType();
/* 182 */       String actor = hisTask.getDealerId();
/* 183 */       String actorName = hisTask.getDealer();
/* 184 */       if (StringUtils.isBlank(actor)) {
/* 185 */         if ("USER".equals(actorType)) {
/* 186 */           actor = hisTask.getAssigneeId();
/* 187 */           actorName = hisTask.getAssignee();
/* 188 */         } else if (("GROUP".equals(actorType)) || ("ROLE".equals(actorType))) {
/* 189 */           actor = hisTask.getAssignGroupId();
/* 190 */           actorName = hisTask.getAssignGroup();
/*     */         }
/*     */       }
/* 193 */       else actorType = "USER";
/*     */ 
/* 195 */       String stepCode = hisTask.getStepCode();
/* 196 */       this.log.info("从历史表里获取处理人,actorId=" + actor + ",actorName=" + actorName + ",actorType=" + actorType);
/* 197 */       acList = (List)Lists.newArrayList(new ActionInfo[] { new ActionInfo(actor, actorType, actorName, "NEXT", null, stepCode) });
/*     */     }
/* 199 */     return acList;
/*     */   }
/*     */ 
/*     */   protected void active(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/* 207 */     changeEntry(curStep.getEntryId(), "active");
/* 208 */     WfEngineUtils.getDefaultFreePostFunction(acInfo).execute(userId, null, curTask, curStep, acInfo, inputs);
/*     */   }
/*     */ 
/*     */   public void close(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/* 216 */     changeEntry(curStep.getEntryId(), "finish");
/* 217 */     String baseId = curTask.getBaseId();
/* 218 */     String baseSchema = curTask.getBaseSchema();
/* 219 */     String hql = "from CurrentProcessTask where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and id !='" + curTask.getId() + "'";
/* 220 */     List tasks = this.taskManager.queryCurrentTask(hql);
/* 221 */     if (CollectionUtils.isNotEmpty(tasks)) {
/* 222 */       this.log.info("归档操作时，将该工单其他的激活任务全部关闭！");
/* 223 */       for (int i = 0; i < tasks.size(); i++) {
/* 224 */         this.taskManager.moveToHistory((BaseTask)tasks.get(i));
/*     */       }
/*     */     }
/* 227 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, null, curTask, curStep, acInfo, inputs);
/*     */   }
/*     */ 
/*     */   public void cancel(Step curStep, BaseTask curTask, String userId, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws WorkflowException
/*     */   {
/* 235 */     changeEntry(curStep.getEntryId(), "cancel");
/* 236 */     String baseId = curTask.getBaseId();
/* 237 */     String baseSchema = curTask.getBaseSchema();
/* 238 */     String hql = "from CurrentProcessTask where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and id !='" + curTask.getId() + "'";
/* 239 */     List tasks = this.taskManager.queryCurrentTask(hql);
/* 240 */     if (CollectionUtils.isNotEmpty(tasks)) {
/* 241 */       this.log.info("作废操作时，将该工单其他的激活任务全部关闭！");
/* 242 */       for (int i = 0; i < tasks.size(); i++) {
/* 243 */         this.taskManager.moveToHistory((BaseTask)tasks.get(i));
/*     */       }
/*     */     }
/* 246 */     WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, null, curTask, curStep, acInfo, inputs);
/*     */   }
/*     */ 
/*     */   protected void terminate(String userId, String actionType, String topEntryId, Map<String, DataField> inputs)
/*     */   {
/* 254 */     if (StringUtils.isNotBlank(topEntryId)) {
/* 255 */       changeEntry(topEntryId, "terminate");
/* 256 */       String hql = "from CurrentProcessTask where topEntryId = '" + topEntryId + "'";
/* 257 */       this.log.info("流程实例终止，终止任务 hql=" + hql);
/* 258 */       List tasks = this.taskManager.queryCurrentTask(hql);
/* 259 */       if (CollectionUtils.isNotEmpty(tasks)) {
/* 260 */         for (int i = 0; i < tasks.size(); i++) {
/* 261 */           BaseTask baseTask = (BaseTask)tasks.get(i);
/* 262 */           baseTask.setFinishTime(TimeUtils.getCurrentTime());
/* 263 */           this.taskManager.moveToHistory(baseTask);
/*     */         }
/*     */       }
/* 266 */       inputs.put("topEntryId", new DataField("topEntryId", FieldDataType.String, topEntryId));
/* 267 */       WfEngineUtils.getDefaultFreePreFunction(new ActionInfo(actionType)).execute(userId, null, null, null, null, inputs);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doExtendAction(String userId, String entryId, String taskId, Map<String, String> params)
/*     */   {
/* 277 */     if (params != null)
/* 278 */       preAssistAudit(userId, taskId, params);
/*     */   }
/*     */ 
/*     */   private void preAssistAudit(String userId, String taskId, Map<String, String> params)
/*     */   {
/* 290 */     String stepGroups = (String)params.get("wf_sys_stepGroups");
/* 291 */     if ((StringUtils.isNotBlank(taskId)) && (StringUtils.isNotBlank(stepGroups))) {
/* 292 */       BaseTask task = this.taskManager.getTaskById(taskId);
/* 293 */       String stepId = task.getStepId();
/* 294 */       String tmpEntryId = task.getEntryId();
/* 295 */       String topEntryId = task.getTopEntryId();
/* 296 */       String[] groups = stepGroups.split(",");
/* 297 */       if (!ArrayUtils.isEmpty(groups))
/* 298 */         for (int i = 0; i < groups.length; i++) {
/* 299 */           String groupId = groups[i];
/* 300 */           if ((!StringUtils.isNotBlank(groupId)) || 
/* 301 */             (!StringUtils.isNotBlank(taskId))) continue;
/* 302 */           String hql = "delete from CurrentProcessTask where topEntryId='" + topEntryId + "' and id != '" + taskId + "' and stepGroup='" + groupId + "'";
/* 303 */           this.entryDao.executeUpdate(hql, null);
/* 304 */           hql = "delete from WfCurrentStep where entryId = '" + tmpEntryId + "' and id !='" + stepId + "' and stepGroup='" + groupId + "'";
/* 305 */           this.entryDao.executeUpdate(hql, null);
/* 306 */           IDefFunction func = (IDefFunction)ApplicationContextUtils.getBean("wf_sys_stepGroups_Function");
/* 307 */           func.execute(userId, null, task, null, null, null, null, null);
/*     */         }
/*     */     }
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engineImpl.ProcessWorkflowEngine
 * JD-Core Version:    0.6.0
 */