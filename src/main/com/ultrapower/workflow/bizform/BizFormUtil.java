/*     */ package com.ultrapower.workflow.bizform;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.configuration.version.service.IWfVersionService;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.FieldDataType;
/*     */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*     */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.ActorDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.role.model.ChildRole;
/*     */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class BizFormUtil
/*     */ {
/*  41 */   private static Logger log = LoggerFactory.getLogger(BizFormUtil.class);
/*     */ 
/*     */   public static List<ActionInfo> formatActionInfo(String assingStr)
/*     */   {
/*  46 */     List acInfoList = new ArrayList();
/*  47 */     if (StringUtils.isNotBlank(assingStr)) {
/*  48 */       String[] assAry = assingStr.split("#;");
/*  49 */       if (!ArrayUtils.isEmpty(assAry)) {
/*  50 */         for (String assignee : assAry) {
/*  51 */           parseAssignStr(acInfoList, assignee);
/*     */         }
/*     */       }
/*     */     }
/*  55 */     return acInfoList;
/*     */   }
/*     */ 
/*     */   public static void parseAssignStr(List<ActionInfo> acInfoList, String assignee) {
/*  59 */     String[] assigneeArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(assignee, "#:");
/*  60 */     if (!ArrayUtils.isEmpty(assigneeArr)) {
/*  61 */       String acType = assigneeArr[0];
/*  62 */       String actorType = "USER";
/*  63 */       if (acType.equals("G"))
/*  64 */         actorType = "GROUP";
/*  65 */       else if (acType.equals("R")) {
/*  66 */         actorType = "ROLE";
/*     */       }
/*  68 */       ActionInfo acInfo = new ActionInfo();
/*  69 */       acInfo.setActorId(assigneeArr[1]);
/*  70 */       acInfo.setActorType(actorType);
/*  71 */       String dealType = assigneeArr[3];
/*  72 */       acInfo.setDealType(WfEngineUtils.getDealType(dealType));
/*     */ 
/*  74 */       acInfo.setActionType(assigneeArr[2]);
/*  75 */       acInfo.setAcceptOverTimeDate(WfEngineUtils.parseLong(assigneeArr[4]));
/*  76 */       acInfo.setAssignOverTimeDate(WfEngineUtils.parseLong(assigneeArr[5]));
/*  77 */       acInfo.setDealOverTimeDate(WfEngineUtils.parseLong(assigneeArr[6]));
/*  78 */       acInfo.setStepCode(assigneeArr[8]);
/*  79 */       acInfo.setActionDesc(assigneeArr[10]);
/*  80 */       if (assigneeArr.length == 11) {
/*  81 */         acInfo.setSubName(assigneeArr[9]);
/*     */       }
/*     */ 
/*  84 */       if (StringUtils.isNotBlank(assigneeArr[7])) {
/*  85 */         String[] sps = assigneeArr[7].split(";");
/*  86 */         if (sps != null)
/*  87 */           for (int i = 0; i < sps.length; i++) {
/*  88 */             String str = sps[i];
/*  89 */             if (StringUtils.isNotBlank(str)) {
/*  90 */               String[] codeAndId = str.split(":");
/*  91 */               if (codeAndId != null) {
/*  92 */                 ActionInfo ac = acInfo.clone();
/*  93 */                 ac.setStepCode(codeAndId[0]);
/*  94 */                 ac.setStepId(codeAndId[1]);
/*  95 */                 acInfoList.add(ac);
/*     */               }
/*     */             }
/*     */           }
/*     */       }
/*     */       else {
/* 101 */         acInfoList.add(acInfo);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Map<String, DataField> formatInputs(String inputStr)
/*     */   {
/* 110 */     Map formatedInput = new HashMap();
/* 111 */     if (StringUtils.isNotBlank(inputStr)) {
/* 112 */       String[] inputArr = inputStr.split("@;");
/* 113 */       if (!ArrayUtils.isEmpty(inputArr)) {
/* 114 */         for (String val : inputArr)
/*     */         {
/* 116 */           if (StringUtils.isNotBlank(val)) {
/* 117 */             String[] valArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(val, "@:");
/* 118 */             FieldDataType en = null;
/* 119 */             int type = Integer.valueOf(valArr[1]).intValue();
/* 120 */             switch (type)
/*     */             {
/*     */             case 2:
/* 123 */               en = FieldDataType.Integer;
/* 124 */               break;
/*     */             case 4:
/* 126 */               en = FieldDataType.String;
/* 127 */               break;
/*     */             case 7:
/* 129 */               en = FieldDataType.DateTime;
/* 130 */               break;
/*     */             case 3:
/*     */             case 5:
/*     */             case 6:
/*     */             default:
/* 132 */               en = FieldDataType.String;
/*     */             }
/*     */ 
/* 135 */             DataField field = new DataField(valArr[0], en, valArr[2]);
/* 136 */             formatedInput.put(valArr[0], field);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 141 */     return formatedInput;
/*     */   }
/*     */ 
/*     */   public static DealProcessModel inDp(String userId, BaseTask baseTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs, WfEntry entry, List dpList, IDao<DealProcessModel> bizDao, AbstractDescriptor desc)
/*     */   {
/* 161 */     ProcessTask task = (ProcessTask)baseTask;
/* 162 */     String entryId = entry.getId();
/* 163 */     String entryType = entry.getType();
/* 164 */     DealProcessModel dpModel = null;
/* 165 */     String actionType = acInfo.getActionType();
/* 166 */     String actorId = acInfo.getActorId();
/* 167 */     String actorType = acInfo.getActorType();
/* 168 */     String dealType = acInfo.getDealType();
/* 169 */     Long now = Long.valueOf(TimeUtils.getCurrentTime());
/* 170 */     if (CollectionUtils.isNotEmpty(dpList)) {
/* 171 */       log.info("从已有DP生成新DP,userId=" + userId + ",stepId=" + step.getId() + ",stepCode=" + step.getStepCode());
/* 172 */       DealProcessModel dealProcess = (DealProcessModel)dpList.get(0);
/* 173 */       dealProcess.setFlagEndDuplicated(Long.valueOf(0L));
/* 174 */       dpModel = dealProcess.clone();
/* 175 */       dpModel.setProcessId(UUIDGenerator.getId());
/* 176 */       dpModel.setFlagDuplicated(Long.valueOf(1L));
/*     */ 
/* 178 */       ITaskManager taskManager = (ITaskManager)ApplicationContextUtils.getBean("taskManager");
/* 179 */       String oldTaskId = dealProcess.getTaskId();
/* 180 */       BaseTask oldTask = taskManager.getTaskById(oldTaskId);
/* 181 */       if (oldTask != null) {
/* 182 */         ProcessTask ot = (ProcessTask)oldTask;
/* 183 */         ot.setFlagEndDuplicated(0L);
/*     */       }
/*     */ 
/* 190 */       if ((task.getSendDate() > 0L) || (task.getAcceptDate() > 0L) || (task.getDueDate() > 0L)) {
/* 191 */         dpModel.setAssignOverTimeDate(Long.valueOf(task.getSendDate()));
/* 192 */         dpModel.setAcceptOverTimeDate(Long.valueOf(task.getAcceptDate()));
/* 193 */         dpModel.setDealOverTimeDate(Long.valueOf(task.getDueDate()));
/*     */       } else {
/* 195 */         task.setSendDate(dpModel.getAssignOverTimeDate().longValue());
/* 196 */         task.setAcceptDate(dpModel.getAcceptOverTimeDate().longValue());
/* 197 */         task.setDueDate(dpModel.getDealOverTimeDate().longValue());
/*     */       }
/*     */     }
/*     */     else {
/* 201 */       log.info("新建DP,userId=" + userId + ",stepId=" + step.getId() + ",stepCode=" + step.getStepCode());
/* 202 */       dpModel = new DealProcessModel();
/* 203 */       String parEntryId = entry.getParentEntryId();
/* 204 */       String topEntryId = entry.getTopEntryId();
/* 205 */       String parFlowId = entry.getParentFlowId();
/* 206 */       dpModel.setEntryId(entryId);
/* 207 */       dpModel.setParentEntryId(parEntryId);
/* 208 */       dpModel.setTopEntryId(topEntryId);
/* 209 */       dpModel.setParentFlowId(parFlowId);
/* 210 */       dpModel.setPhaseNo(step.getStepCode());
/* 211 */       dpModel.setPrePhaseNo(step.getForwardCode());
/* 212 */       dpModel.setOrigPhaseNo(StringUtils.isBlank(step.getOrigCode()) ? "" : step.getOrigCode());
/* 213 */       dpModel.setFlagDuplicated(Long.valueOf(0L));
/*     */ 
/* 215 */       dpModel.setFlagPredefined(Long.valueOf(0L));
/* 216 */       dpModel.setBaseStartDate(now);
/* 217 */       dpModel.setBaseCreateTime(task.getBaseCreateTime());
/* 218 */       dpModel.setBaseId(task.getBaseId());
/* 219 */       dpModel.setBaseSchema(task.getBaseSchema());
/* 220 */       dpModel.setAssignOverTimeDate(Long.valueOf(task.getSendDate()));
/* 221 */       dpModel.setAcceptOverTimeDate(Long.valueOf(task.getAcceptDate()));
/* 222 */       dpModel.setDealOverTimeDate(Long.valueOf(task.getDueDate()));
/* 223 */       task.setParentFlowId(parFlowId);
/* 224 */       task.setOrigPhaseNo(dpModel.getOrigPhaseNo());
/*     */ 
/* 226 */       if ("DEFINE".equals(entryType)) {
/* 227 */         if ((desc != null) && ((desc instanceof StepDescriptor))) {
/* 228 */           StepDescriptor stDesc = (StepDescriptor)desc;
/* 229 */           String type = stDesc.getType();
/* 230 */           dpModel.setProcessType(type);
/* 231 */           type = dpModel.getProcessType();
/* 232 */           if ("DEAL".equals(type))
/* 233 */             dpModel.setFlagAssignType("主办");
/* 234 */           else if ("AUDITING".equals(type))
/* 235 */             dpModel.setFlagAssignType("审批");
/* 236 */           else if ("COPY".equals(type))
/* 237 */             dpModel.setFlagAssignType("抄送");
/* 238 */           else if ("ASSIST".equals(type))
/* 239 */             dpModel.setFlagAssignType("协办");
/*     */         }
/*     */       }
/*     */       else {
/* 243 */         if (("AUDIT".equals(actionType)) || 
/* 244 */           ("REAUDIT".equals(actionType)) || 
/* 245 */           ("ORGANIZEAUDIT".equals(actionType)))
/* 246 */           dpModel.setProcessType("AUDITING");
/*     */         else {
/* 248 */           dpModel.setProcessType("DEAL");
/*     */         }
/*     */ 
/* 251 */         if ((actionType.equals("AUDIT")) || (actionType.equals("REAUDIT")))
/* 252 */           dpModel.setFlagAssignType("审批");
/* 253 */         else if (actionType.equals("ORGANIZEAUDIT"))
/* 254 */           dpModel.setFlagAssignType("会审");
/* 255 */         else if (actionType.equals("ASSIST"))
/* 256 */           dpModel.setFlagAssignType("协办");
/* 257 */         else if (actionType.equals("MAKECOPY"))
/* 258 */           dpModel.setFlagAssignType("抄送");
/* 259 */         else if (actionType.equals("CHECK"))
/* 260 */           dpModel.setFlagAssignType("质检");
/*     */         else {
/* 262 */           dpModel.setFlagAssignType("主办");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 267 */     task.setProcessType(dpModel.getProcessType());
/* 268 */     task.setFlagAssignType(dpModel.getFlagAssignType());
/* 269 */     task.setPrePhaseNo(dpModel.getPrePhaseNo());
/* 270 */     task.setFlagDuplicated(1L);
/* 271 */     task.setFlagEndDuplicated(1L);
/* 272 */     task.setFlagPreDefined(0L);
/* 273 */     task.setBaseStartDate(now.longValue());
/*     */ 
/* 276 */     if ((StringUtils.isNotBlank(actorType)) && (StringUtils.isNotBlank(actorId))) {
/* 277 */       dpModel.setActorType(actorType);
/* 278 */       task.setActorType(actorType);
/* 279 */       dpModel.setDealType(dealType);
/* 280 */       task.setDealType(dealType);
/* 281 */       WfEngineUtils.resetDpAssignee(dpModel);
/* 282 */       WfEngineUtils.resetTaskAssignee(task);
/* 283 */       if ("USER".equals(actorType)) {
/* 284 */         WfEngineUtils.setDpAssignee(dpModel, actorId);
/* 285 */         WfEngineUtils.setTaskAssignee(task, actorId);
/*     */       } else {
/* 287 */         dpModel.setAssignGroupId(actorId);
/* 288 */         task.setAssignGroupId(actorId);
/* 289 */         String groupName = null;
/* 290 */         if ("GROUP".equals(actorType)) {
/* 291 */           DepInfo dep = ThreadObj.getDepInfo(actorId);
/* 292 */           if (dep != null)
/* 293 */             groupName = dep.getDepname();
/*     */         }
/* 295 */         else if ("ROLE".equals(actorType)) {
/* 296 */           ChildRole childRole = ThreadObj.getChildRole(actorId);
/* 297 */           if (childRole != null) {
/* 298 */             groupName = childRole.getChildRoleName();
/*     */           }
/*     */         }
/* 301 */         dpModel.setAssignGroup(groupName);
/* 302 */         task.setAssignGroup(groupName);
/*     */       }
/*     */     }
/*     */ 
/* 306 */     dpModel.setFlagEndDuplicated(Long.valueOf(1L));
/* 307 */     dpModel.setStepId(step.getId());
/* 308 */     dpModel.setForwardStepId(step.getForwardId());
/* 309 */     dpModel.setFlowId(step.getFlowId());
/* 310 */     String actionName = WfEngineUtils.getActionName(actionType, inputs);
/* 311 */     task.setActionName(actionName);
/* 312 */     dpModel.setActionName(actionName);
/* 313 */     task.setActionCode(actionType);
/* 314 */     task.setFlowId(dpModel.getFlowId());
/*     */ 
/* 317 */     WfEngineUtils.setDpAssigner(dpModel, userId);
/* 318 */     WfEngineUtils.setTaskAssigner(task, userId);
/*     */ 
/* 321 */     WfEngineUtils.resetDpDealer(dpModel);
/* 322 */     WfEngineUtils.resetTaskDealer(task);
/*     */ 
/* 324 */     dpModel.setTaskId(task.getId());
/* 325 */     dpModel.setProcessStatus(task.getProcessState());
/* 326 */     dpModel.setFlagActive(Long.valueOf(1L));
/* 327 */     task.setFlagActive(1L);
/*     */ 
/* 329 */     dpModel.setStProcessAction(WfEngineUtils.getActionName(actionType));
/* 330 */     dpModel.setEdProcessAction(null);
/* 331 */     dpModel.setStDate(now);
/* 332 */     dpModel.setBgDate(Long.valueOf(0L));
/* 333 */     dpModel.setEdDate(Long.valueOf(0L));
/* 334 */     task.setStProcessAction(dpModel.getStProcessAction());
/* 335 */     task.setEdProcessAction(null);
/* 336 */     task.setCreateTime(now.longValue());
/* 337 */     task.setAcceptTime(0L);
/* 338 */     task.setFinishTime(0L);
/* 339 */     dpModel.setFinishInsideFlowsCount(Long.valueOf(step.getFinishCount()));
/* 340 */     task.setFinishInsideFlowsCount(dpModel.getFinishInsideFlowsCount().longValue());
/* 341 */     dpModel.setFlagTurnUp(Long.valueOf(1L));
/*     */ 
/* 344 */     setRoleInfo(desc, task, dpModel);
/*     */ 
/* 347 */     setAuditResult(actionType, dpModel);
/*     */ 
/* 349 */     Long flagAuditingResult = dpModel.getFlagAuditingResult();
/* 350 */     if (flagAuditingResult != null) {
/* 351 */       task.setFlagAuditingResult(flagAuditingResult.longValue());
/*     */     }
/*     */ 
/* 354 */     String parEntryId = entry.getParentEntryId();
/* 355 */     if (StringUtils.isNotBlank(parEntryId)) {
/* 356 */       String hql2 = "from DealProcessModel where phaseNo='" + dpModel.getPrePhaseNo() + "' and entryId = '" + entryId + "'";
/* 357 */       List dpList2 = bizDao.find(hql2, new Object[0]);
/* 358 */       if (CollectionUtils.isNotEmpty(dpList2)) {
/* 359 */         DealProcessModel preDp = (DealProcessModel)dpList2.get(0);
/* 360 */         if (preDp.getPrePhaseNo().equals("BEGIN")) {
/* 361 */           dpModel.setFlagTurnUp(Long.valueOf(0L));
/*     */         }
/*     */       }
/*     */     }
/* 365 */     task.setFlagTurnUp(dpModel.getFlagTurnUp().longValue());
/* 366 */     setSerialNum(task);
/* 367 */     return dpModel;
/*     */   }
/*     */ 
/*     */   private static void setRoleInfo(AbstractDescriptor desc, ProcessTask task, DealProcessModel dpModel) {
/* 371 */     if ((desc != null) && ((desc instanceof StepDescriptor))) {
/* 372 */       StepDescriptor stDesc = (StepDescriptor)desc;
/* 373 */       ActorDescriptor actorDesc = stDesc.getActorDesc();
/* 374 */       String dealType = stDesc.getTaskPolicy();
/* 375 */       dealType = StringUtils.isNotBlank(dealType) ? dealType : "";
/* 376 */       String existDealType = task.getDealType();
/* 377 */       if (StringUtils.isBlank(existDealType)) {
/* 378 */         dpModel.setDealType(dealType);
/* 379 */         task.setDealType(dealType);
/*     */       }
/* 381 */       if (actorDesc != null) {
/* 382 */         String roleId = actorDesc.getRoleId();
/* 383 */         String roleName = actorDesc.getRoleName();
/* 384 */         if ((StringUtils.isNotBlank(roleId)) && (StringUtils.isNotBlank(roleName))) {
/* 385 */           dpModel.setRoleId(roleId);
/* 386 */           dpModel.setRoleName(roleName);
/* 387 */           task.setRoleId(roleId);
/* 388 */           task.setRoleName(roleName);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setSerialNum(ProcessTask task)
/*     */   {
/* 399 */     if (StringUtils.isBlank(task.getSerialNum()))
/* 400 */       synchronized (BizFormUtil.class) {
/* 401 */         IWfVersionService verService = (IWfVersionService)ApplicationContextUtils.getBean("verServiceImpl");
/* 402 */         String serialNum = verService.getSerialNum(task.getBaseSchema(), task.getDefName());
/* 403 */         task.setSerialNum(serialNum);
/*     */       }
/*     */   }
/*     */ 
/*     */   public static DealProcessModel outDp(String userId, BaseTask baseTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs, List dpList)
/*     */   {
/* 420 */     log.info("关闭DP,userId=" + userId + ",stepId=" + step.getId() + ",stepCode=" + step.getStepCode());
/* 421 */     ProcessTask task = (ProcessTask)baseTask;
/* 422 */     DealProcessModel dpModel = (DealProcessModel)dpList.get(0);
/* 423 */     String actionType = acInfo.getActionType();
/* 424 */     Long now = Long.valueOf(TimeUtils.getCurrentTime());
/*     */ 
/* 426 */     WfEngineUtils.setDpDealer(dpModel, userId);
/* 427 */     WfEngineUtils.setTaskDealer(baseTask, userId);
/* 428 */     dpModel.setEdDate(now);
/* 429 */     task.setFinishTime(now.longValue());
/* 430 */     if ((dpModel.getBgDate() == null) || (dpModel.getBgDate().longValue() == 0L)) {
/* 431 */       dpModel.setBgDate(now);
/* 432 */       task.setAcceptTime(now.longValue());
/*     */     }
/* 434 */     dpModel.setBgDate(Long.valueOf(task.getAcceptTime()));
/* 435 */     dpModel.setProcessStatus(task.getProcessState());
/*     */ 
/* 437 */     dpModel.setFlagRecall(Long.valueOf(1L));
/* 438 */     task.setFlagRecall(1L);
/*     */ 
/* 440 */     String actionName = WfEngineUtils.getActionName(actionType, inputs);
/* 441 */     dpModel.setEdProcessAction(WfEngineUtils.getActionName(actionType));
/* 442 */     dpModel.setInsideFlowsCount(Long.valueOf(WfEngineUtils.parseLong(step.getSubCount())));
/* 443 */     task.setEdProcessAction(dpModel.getEdProcessAction());
/* 444 */     task.setInsideFlowsCount(dpModel.getInsideFlowsCount().longValue());
/* 445 */     dpModel.setActionName(actionName);
/* 446 */     Long selfDealLength = Long.valueOf(dpModel.getSelfDealLength().longValue() + (now.longValue() - dpModel.getStDate().longValue()));
/* 447 */     dpModel.setSelfDealLength(selfDealLength);
/*     */ 
/* 457 */     WfEngineUtils.setFlagActive(dpModel, task, actionType);
/*     */ 
/* 465 */     if ((!actionType.equals("FINISH")) && (!actionType.equals("CLOSE"))) {
/* 466 */       dpModel.setFlagEndPhase(Long.valueOf(0L));
/* 467 */       task.setFlagEndPhase(0L);
/*     */     }
/*     */ 
/* 471 */     setAuditResult(actionType, dpModel);
/*     */ 
/* 473 */     Long flagAuditingResult = dpModel.getFlagAuditingResult();
/* 474 */     if (flagAuditingResult != null) {
/* 475 */       task.setFlagAuditingResult(flagAuditingResult.longValue());
/*     */     }
/*     */ 
/* 478 */     if (inputs.containsKey("PreAssign")) {
/* 479 */       dpModel.setPreAssignString(((DataField)inputs.get("PreAssign")).getValue());
/* 480 */       task.setPreAssignString(dpModel.getPreAssignString());
/*     */     }
/* 482 */     return dpModel;
/*     */   }
/*     */ 
/*     */   private static void setAuditResult(String actionType, DealProcessModel dpModel) {
/* 486 */     if (("AUDITINGPASS".equals(actionType)) || 
/* 487 */       ("REAUDIT".equals(actionType)) || 
/* 488 */       ("ORGANIZEAUDITINGPASS".equals(actionType)))
/* 489 */       dpModel.setFlagAuditingResult(Long.valueOf(1L));
/* 490 */     else if (("AUDITINGNOPASS".equals(actionType)) || 
/* 491 */       ("ORGANIZEAUDITINGNOPASS".equals(actionType)))
/* 492 */       dpModel.setFlagAuditingResult(Long.valueOf(0L));
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.BizFormUtil
 * JD-Core Version:    0.6.0
 */