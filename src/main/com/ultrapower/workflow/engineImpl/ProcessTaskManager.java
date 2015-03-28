/*     */ package com.ultrapower.workflow.engineImpl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*     */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*     */ import com.ultrapower.workflow.engine.core.IFreeFunction;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*     */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
/*     */ import com.ultrapower.workflow.engine.task.AbstractTaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.CurrentProcessTask;
/*     */ import com.ultrapower.workflow.engine.task.model.HistoryProcessTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.exception.TaskException;
/*     */ import com.ultrapower.workflow.role.model.ChildRole;
/*     */ import com.ultrapower.workflow.utils.CommonUtils;
/*     */ import com.ultrapower.workflow.utils.ThreadUtils;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import com.ultrapower.workflow.utils.WfEngineConstants;
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
/*     */ public class ProcessTaskManager extends AbstractTaskManager<ProcessTask, CurrentProcessTask, HistoryProcessTask>
/*     */ {
/*  40 */   private Logger log = LoggerFactory.getLogger(ProcessTaskManager.class);
/*     */ 
/*     */   protected ProcessTask createTask(ProcessTask prevTask, Step step, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */   {
/*  47 */     this.log.info("生成任务！");
/*  48 */     Long now = Long.valueOf(TimeUtils.getCurrentTime());
/*  49 */     ProcessTask clone = null;
/*  50 */     if (prevTask != null) {
/*  51 */       clone = (ProcessTask)prevTask.clone();
/*  52 */       clone.setId(UUIDGenerator.getId());
/*     */     }
/*     */ 
/*  55 */     CurrentProcessTask task = null;
/*  56 */     if (clone != null) {
/*  57 */       this.log.info("从已有任务生成新任务！");
/*  58 */       if ((clone instanceof CurrentProcessTask))
/*  59 */         task = (CurrentProcessTask)clone;
/*  60 */       else if ((clone instanceof HistoryProcessTask)) {
/*  61 */         task = (CurrentProcessTask)clone.toCurrent();
/*     */       }
/*  63 */       task.setEntryId(step.getEntryId());
/*  64 */       task.setStepId(step.getId());
/*  65 */       task.setStepCode(step.getStepCode());
/*  66 */       task.setFlagAssign(0L);
/*  67 */       task.setFlagAssist(0L);
/*  68 */       task.setFlagAuditingResult(0L);
/*  69 */       task.setFlagCancel(0L);
/*  70 */       task.setFlagClose(0L);
/*  71 */       task.setFlagCopy(0L);
/*  72 */       task.setFlagRecall(0L);
/*  73 */       task.setFlagToAssistAuditing(0L);
/*  74 */       task.setFlagToAuditing(0L);
/*  75 */       task.setFlagTransfer(0L);
/*  76 */       task.setFlagTurnDown(0L);
/*  77 */       task.setFlagTurnUp(0L);
/*     */     } else {
/*  79 */       this.log.info("新建任务！");
/*  80 */       DataField baseId = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEID);
/*  81 */       DataField baseSchema = (DataField)inputs.get(WfEngineConstants.INPUTS_BASESCHEMA);
/*  82 */       DataField baseName = (DataField)inputs.get(WfEngineConstants.INPUTS_BASENAME);
/*  83 */       task = new CurrentProcessTask();
/*  84 */       task.setEntryId(step.getEntryId());
/*  85 */       task.setStepId(step.getId());
/*  86 */       task.setStepCode(step.getStepCode());
/*  87 */       task.setBaseId(baseId != null ? baseId.getValue() : null);
/*  88 */       task.setBaseSchema(baseSchema != null ? baseSchema.getValue() : null);
/*  89 */       task.setBaseName(baseName != null ? baseName.getValue() : null);
/*  90 */       task.setBaseCreateTime(now.longValue());
/*  91 */       String actorId = acInfo.getActorId();
/*  92 */       String actorType = acInfo.getActorType();
/*  93 */       task.setCreator(actorId);
/*  94 */       task.setCreatorType(actorType);
/*  95 */       if ("USER".equals(actorType)) {
/*  96 */         UserInfo user = ThreadObj.getUsersInfo(actorId);
/*  97 */         if (user != null)
/*  98 */           task.setCreatorName(user.getFullname());
/*     */       }
/* 100 */       else if ("GROUP".equals(actorType)) {
/* 101 */         DepInfo dep = ThreadObj.getDepInfo(actorId);
/* 102 */         if (dep != null)
/* 103 */           task.setCreatorName(dep.getDepname());
/*     */       }
/* 105 */       else if ("ROLE".equals(actorType)) {
/* 106 */         ChildRole childRole = ThreadObj.getChildRole(actorId);
/* 107 */         if (childRole != null) {
/* 108 */           task.setCreatorName(childRole.getChildRoleName());
/*     */         }
/*     */       }
/* 111 */       String topEntryId = CommonUtils.getVal(inputs, "top_entryId");
/* 112 */       String parEntryId = CommonUtils.getVal(inputs, "parent_entryId");
/* 113 */       String topTaskId = CommonUtils.getVal(inputs, "top_taskId");
/* 114 */       String serialNum = CommonUtils.getVal(inputs, "serialNum");
/* 115 */       String parentStepNo = CommonUtils.getVal(inputs, "parent_stepNo");
/* 116 */       String parentStepGroup = CommonUtils.getVal(inputs, "parent_stepGroup");
/* 117 */       if ((parEntryId != null) && (topEntryId != null)) {
/* 118 */         task.setTopEntryId(topEntryId);
/* 119 */         task.setParentEntryId(parEntryId);
/*     */       } else {
/* 121 */         task.setTopEntryId(task.getEntryId());
/*     */       }
/* 123 */       if (StringUtils.isNotBlank(serialNum)) {
/* 124 */         task.setSerialNum(serialNum);
/*     */       }
/* 126 */       if (StringUtils.isNotBlank(topTaskId)) {
/* 127 */         task.setTopTaskId(topTaskId);
/*     */       }
/* 129 */       WfEntry entry = (WfEntry)this.entryDao.get(task.getEntryId());
/* 130 */       if (entry != null) {
/* 131 */         task.setDefName(entry.getDefName());
/*     */       }
/* 133 */       if (StringUtils.isNotBlank(parentStepNo)) {
/* 134 */         task.setStepNo(parentStepNo);
/*     */       }
/* 136 */       if (StringUtils.isNotBlank(parentStepGroup)) {
/* 137 */         task.setStepGroup(parentStepGroup);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 142 */     DataField baseSummary = (DataField)inputs.get(WfEngineConstants.INPUTS_BASESUMMARY);
/* 143 */     DataField baseItems = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEITEMS);
/* 144 */     DataField basePriority = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEPRIORITY);
/* 145 */     DataField baseAcceptOutTime = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEACCEPTOUTTIME);
/* 146 */     DataField baseDealOutTime = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEDEALOUTTIME);
/* 147 */     task.setWorkSheetTitle(baseSummary != null ? baseSummary.getValue() : null);
/* 148 */     task.setWorkSheetProType(baseItems != null ? baseItems.getValue() : null);
/* 149 */     task.setWorkSheetUrgentLevel(basePriority != null ? basePriority.getValue() : null);
/*     */ 
/* 151 */     long assignOver = 0L;
/* 152 */     long acceptOver = 0L;
/* 153 */     long dealOver = 0L;
/* 154 */     if (stDesc != null) {
/* 155 */       if (baseAcceptOutTime != null) {
/* 156 */         acceptOver = WfEngineUtils.parseLong(baseAcceptOutTime.getValue());
/*     */       }
/* 158 */       if (baseDealOutTime != null) {
/* 159 */         dealOver = WfEngineUtils.parseLong(baseDealOutTime.getValue());
/*     */       }
/*     */ 
/* 163 */       String assignOvers = stDesc.getAssignOver();
/* 164 */       String acceptOvers = stDesc.getAcceptOver();
/* 165 */       String dealOvers = stDesc.getDealOver();
/* 166 */       long assignOverTemp = WfEngineUtils.parseLong(assignOvers);
/* 167 */       if (assignOverTemp > 0L) {
/* 168 */         assignOver = assignOverTemp + now.longValue();
/*     */       }
/* 170 */       long acceptOverTemp = WfEngineUtils.parseLong(acceptOvers);
/* 171 */       if (acceptOverTemp > 0L) {
/* 172 */         acceptOver = acceptOverTemp + now.longValue();
/*     */       }
/* 174 */       long dealOverTemp = WfEngineUtils.parseLong(dealOvers);
/* 175 */       if (dealOverTemp > 0L) {
/* 176 */         dealOver = dealOverTemp + now.longValue();
/*     */       }
/*     */ 
/* 179 */       if (acInfo.getAcceptOverTimeDate() > 0L) {
/* 180 */         acceptOver = acInfo.getAcceptOverTimeDate();
/*     */       }
/* 182 */       if (acInfo.getDealOverTimeDate() > 0L) {
/* 183 */         dealOver = acInfo.getDealOverTimeDate();
/*     */       }
/* 185 */       if (acInfo.getAssignOverTimeDate() > 0L)
/* 186 */         assignOver = acInfo.getAssignOverTimeDate();
/*     */     }
/*     */     else
/*     */     {
/* 190 */       StepDescriptor sd = ThreadUtils.getTopDefStep();
/* 191 */       if (sd != null)
/*     */       {
/* 194 */         String assignOvers = sd.getAssignOver();
/* 195 */         String acceptOvers = sd.getAcceptOver();
/* 196 */         String dealOvers = sd.getDealOver();
/* 197 */         long assignOverTemp = WfEngineUtils.parseLong(assignOvers);
/* 198 */         if (assignOverTemp > 0L) {
/* 199 */           assignOver = assignOverTemp + now.longValue();
/*     */         }
/* 201 */         long acceptOverTemp = WfEngineUtils.parseLong(acceptOvers);
/* 202 */         if (acceptOverTemp > 0L) {
/* 203 */           acceptOver = acceptOverTemp + now.longValue();
/*     */         }
/* 205 */         long dealOverTemp = WfEngineUtils.parseLong(dealOvers);
/* 206 */         if (dealOverTemp > 0L) {
/* 207 */           dealOver = dealOverTemp + now.longValue();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 212 */       if (acInfo.getAcceptOverTimeDate() > 0L) {
/* 213 */         acceptOver = acInfo.getAcceptOverTimeDate();
/*     */       }
/* 215 */       if (acInfo.getDealOverTimeDate() > 0L) {
/* 216 */         dealOver = acInfo.getDealOverTimeDate();
/*     */       }
/* 218 */       if (acInfo.getAssignOverTimeDate() > 0L) {
/* 219 */         assignOver = acInfo.getAssignOverTimeDate();
/*     */       }
/*     */     }
/*     */ 
/* 223 */     task.setAcceptDate(acceptOver);
/* 224 */     task.setSendDate(assignOver);
/* 225 */     task.setDueDate(dealOver);
/* 226 */     saveCurrentTask(task);
/* 227 */     return task;
/*     */   }
/*     */ 
/*     */   protected BaseTask accept(String userId, BaseTask bTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */   {
/* 235 */     String actionType = acInfo.getActionType();
/* 236 */     Long now = Long.valueOf(TimeUtils.getCurrentTime());
/* 237 */     UserInfo user = ThreadObj.getUsersInfo(userId);
/* 238 */     ProcessTask task = null;
/* 239 */     if (user != null) {
/* 240 */       task = (ProcessTask)bTask;
/* 241 */       String dealerId = task.getDealerId();
/* 242 */       String dealer = task.getDealer();
/* 243 */       String dealType = task.getDealType();
/* 244 */       if (StringUtils.isNotBlank(dealType)) dealType.equals("SHARE");
/*     */ 
/* 249 */       if ((StringUtils.isNotBlank(dealType)) && (!dealType.equals("SHARE")) && (StringUtils.isNotBlank(dealerId)) && (!dealerId.equals(userId))) {
/* 250 */         throw new TaskException("该任务已经被[" + dealer + "]受理！");
/*     */       }
/* 252 */       long acceptTime = task.getAcceptTime();
/* 253 */       if (acceptTime == 0L) {
/* 254 */         task.setAcceptTime(now.longValue());
/*     */       }
/* 256 */       task.setActionCode(actionType);
/* 257 */       task.setActionName(WfEngineUtils.getActionName(actionType));
/*     */ 
/* 259 */       String topEntryId = task.getTopEntryId();
/* 260 */       String parentEntryId = task.getParentEntryId();
/*     */ 
/* 264 */       WfEngineUtils.getDefaultFreePreFunction(acInfo).execute(userId, task, task, step, acInfo, inputs);
/*     */     }
/* 266 */     return task;
/*     */   }
/*     */ 
/*     */   public void cancelAuditTask(WfCurrentStep curStep, BaseTask curTask)
/*     */   {
/* 271 */     this.log.info("审批不通过，注销其他的未审批任务");
/* 272 */     String entryId = curTask.getEntryId();
/* 273 */     String taskId = curTask.getId();
/* 274 */     String findHql = "from CurrentProcessTask where entryId='" + entryId + "' and processState='" + "待审批" + "' and id != '" + taskId + "'";
/*     */ 
/* 276 */     List tasks = this.currTaskDao.find(findHql, null);
/* 277 */     int cancelSize = tasks.size();
/* 278 */     this.log.info("已注销的任务数 size = " + cancelSize);
/* 279 */     if (CollectionUtils.isNotEmpty(tasks)) {
/* 280 */       for (int i = 0; i < cancelSize; i++) {
/* 281 */         BaseTask baseTask = (BaseTask)tasks.get(i);
/* 282 */         String stepId = baseTask.getStepId();
/*     */ 
/* 284 */         WfEngineUtils.getDefaultFreePreFunction(new ActionInfo("cancelauditdp")).execute(null, null, baseTask, curStep, null, null);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 289 */     String historyStepId = StringUtils.isNotBlank(curStep.getOrigId()) ? curStep.getOrigId() : curStep.getForwardId();
/* 290 */     WfHistoryStep hisStep = (WfHistoryStep)this.historyDao.get(historyStepId);
/* 291 */     hisStep.setSubCount(hisStep.getSubCount() - cancelSize);
/*     */   }
/*     */ 
/*     */   public void updateBizProperties(CurrentProcessTask task, Map<String, DataField> inputs)
/*     */   {
/* 296 */     if ((task != null) && (inputs != null)) {
/* 297 */       DataField baseSummary = (DataField)inputs.get(WfEngineConstants.INPUTS_BASESUMMARY);
/* 298 */       DataField baseItems = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEITEMS);
/* 299 */       DataField basePriority = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEPRIORITY);
/* 300 */       String BaseSummary = baseSummary != null ? baseSummary.getValue() : "";
/* 301 */       String BaseItems = baseItems != null ? baseItems.getValue() : "";
/* 302 */       String BasePriority = basePriority != null ? basePriority.getValue() : "";
/* 303 */       String topEntryId = task.getTopEntryId();
/* 304 */       if (!BaseSummary.equals(task.getWorkSheetTitle())) {
/* 305 */         String table = "CurrentProcessTask";
/* 306 */         String hql = "update from " + table + " set workSheetTitle='" + BaseSummary + "' where topEntryId = '" + topEntryId + "'";
/* 307 */         this.currTaskDao.executeUpdate(hql, null);
/*     */ 
/* 309 */         table = "HistoryProcessTask";
/* 310 */         hql = "update from " + table + " set workSheetTitle='" + BaseSummary + "' where topEntryId = '" + topEntryId + "'";
/* 311 */         this.hisTaskDao.executeUpdate(hql, null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ProcessTask customTask(String userId, ProcessTask prevTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws TaskException
/*     */   {
/* 320 */     CurrentProcessTask current = null;
/* 321 */     if ((acInfo != null) && (prevTask != null)) {
/* 322 */       String topEntryId = prevTask.getTopEntryId();
/* 323 */       String actionType = acInfo.getActionType();
/* 324 */       if ("CHASEALL".equals(actionType))
/*     */       {
/* 326 */         String hql = "delete from CurrentProcessTask where topEntryId='" + topEntryId + "'";
/* 327 */         this.currTaskDao.executeUpdate(hql, null);
/*     */ 
/* 329 */         hql = "from HistoryProcessTask where topEntryId='" + topEntryId + "' and (actionCode='" + "NEW" + "' or actionCode='" + "CHASEALL" + "') and parentEntryId is null order by createTime desc";
/* 330 */         List find = this.hisTaskDao.find(hql, null);
/* 331 */         if (CollectionUtils.isNotEmpty(find)) {
/* 332 */           HistoryProcessTask hisTask = (HistoryProcessTask)find.get(0);
/*     */ 
/* 335 */           current = hisTask.toCurrent();
/* 336 */           this.currTaskDao.save(current);
/*     */         }
/*     */       }
/*     */     }
/* 340 */     return current;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engineImpl.ProcessTaskManager
 * JD-Core Version:    0.6.0
 */