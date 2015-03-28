/*     */ package com.ultrapower.workflow.engine.task.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.MappedSuperclass;
/*     */ 
/*     */ @MappedSuperclass
/*     */ public abstract class BaseTask<T>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private String id;
/*     */   private String entryId;
/*     */   private String parentEntryId;
/*     */   private String topEntryId;
/*     */   private String parentTaskId;
/*     */   private String topTaskId;
/*     */   private String actorType;
/*     */   private String stepId;
/*     */   private String stepCode;
/*     */   private String stepName;
/*     */   private String processState;
/*     */   private String entryState;
/*     */   private long createTime;
/*     */   private long acceptTime;
/*     */   private long finishTime;
/*     */   private long baseCreateTime;
/*     */   private String dealType;
/*     */   private String serialNum;
/*     */   private String taskName;
/*     */   private String actionName;
/*     */   private String actionCode;
/*     */   private String creator;
/*     */   private String creatorName;
/*     */   private String creatorType;
/*     */   private String assigneeId;
/*     */   private String assignee;
/*     */   private String assigneeDepId;
/*     */   private String assigneeDep;
/*     */   private String assigneeCorpId;
/*     */   private String assigneeCorp;
/*     */   private String assigneeDnid;
/*     */   private String assigneeDn;
/*     */   private String assignGroupId;
/*     */   private String assignGroup;
/*     */   private String dealerId;
/*     */   private String dealer;
/*     */   private String dealerDepId;
/*     */   private String dealerDep;
/*     */   private String dealerCorpId;
/*     */   private String dealerCorp;
/*     */   private String dealerDnid;
/*     */   private String dealerDn;
/*     */   private String assignerId;
/*     */   private String assigner;
/*     */   private String assignerDepId;
/*     */   private String assignerDep;
/*     */   private String assignerCorpId;
/*     */   private String assignerCorp;
/*     */   private String assignerDnid;
/*     */   private String assignerDn;
/*     */   private String baseSchema;
/*     */   private String baseName;
/*     */   private String baseId;
/*     */   private String workSheetTitle;
/*     */   private String workSheetProType;
/*     */   private String workSheetUrgentLevel;
/*     */   private String stepNo;
/*     */   private String stepGroup;
/*     */   private String track;
/*     */ 
/*     */   public String getAssigneeId()
/*     */   {
/*  82 */     return this.assigneeId;
/*     */   }
/*     */ 
/*     */   public void setAssigneeId(String assigneeId) {
/*  86 */     this.assigneeId = assigneeId;
/*     */   }
/*     */ 
/*     */   public String getAssignee() {
/*  90 */     return this.assignee;
/*     */   }
/*     */ 
/*     */   public void setAssignee(String assignee) {
/*  94 */     this.assignee = assignee;
/*     */   }
/*     */ 
/*     */   public String getAssigneeDepId() {
/*  98 */     return this.assigneeDepId;
/*     */   }
/*     */ 
/*     */   public void setAssigneeDepId(String assigneeDepId) {
/* 102 */     this.assigneeDepId = assigneeDepId;
/*     */   }
/*     */ 
/*     */   public String getAssigneeDep() {
/* 106 */     return this.assigneeDep;
/*     */   }
/*     */ 
/*     */   public void setAssigneeDep(String assigneeDep) {
/* 110 */     this.assigneeDep = assigneeDep;
/*     */   }
/*     */ 
/*     */   public String getAssigneeCorpId() {
/* 114 */     return this.assigneeCorpId;
/*     */   }
/*     */ 
/*     */   public void setAssigneeCorpId(String assigneeCorpId) {
/* 118 */     this.assigneeCorpId = assigneeCorpId;
/*     */   }
/*     */ 
/*     */   public String getAssigneeCorp() {
/* 122 */     return this.assigneeCorp;
/*     */   }
/*     */ 
/*     */   public void setAssigneeCorp(String assigneeCorp) {
/* 126 */     this.assigneeCorp = assigneeCorp;
/*     */   }
/*     */ 
/*     */   public String getAssigneeDnid() {
/* 130 */     return this.assigneeDnid;
/*     */   }
/*     */ 
/*     */   public void setAssigneeDnid(String assigneeDnid) {
/* 134 */     this.assigneeDnid = assigneeDnid;
/*     */   }
/*     */ 
/*     */   public String getAssigneeDn() {
/* 138 */     return this.assigneeDn;
/*     */   }
/*     */ 
/*     */   public void setAssigneeDn(String assigneeDn) {
/* 142 */     this.assigneeDn = assigneeDn;
/*     */   }
/*     */ 
/*     */   public String getAssignGroupId() {
/* 146 */     return this.assignGroupId;
/*     */   }
/*     */ 
/*     */   public void setAssignGroupId(String assignGroupId) {
/* 150 */     this.assignGroupId = assignGroupId;
/*     */   }
/*     */ 
/*     */   public String getAssignGroup() {
/* 154 */     return this.assignGroup;
/*     */   }
/*     */ 
/*     */   public void setAssignGroup(String assignGroup) {
/* 158 */     this.assignGroup = assignGroup;
/*     */   }
/*     */ 
/*     */   public String getDealerId() {
/* 162 */     return this.dealerId;
/*     */   }
/*     */ 
/*     */   public void setDealerId(String dealerId) {
/* 166 */     this.dealerId = dealerId;
/*     */   }
/*     */ 
/*     */   public String getDealer() {
/* 170 */     return this.dealer;
/*     */   }
/*     */ 
/*     */   public void setDealer(String dealer) {
/* 174 */     this.dealer = dealer;
/*     */   }
/*     */ 
/*     */   public String getDealerDepId() {
/* 178 */     return this.dealerDepId;
/*     */   }
/*     */ 
/*     */   public void setDealerDepId(String dealerDepId) {
/* 182 */     this.dealerDepId = dealerDepId;
/*     */   }
/*     */ 
/*     */   public String getDealerDep() {
/* 186 */     return this.dealerDep;
/*     */   }
/*     */ 
/*     */   public void setDealerDep(String dealerDep) {
/* 190 */     this.dealerDep = dealerDep;
/*     */   }
/*     */ 
/*     */   public String getDealerCorpId() {
/* 194 */     return this.dealerCorpId;
/*     */   }
/*     */ 
/*     */   public void setDealerCorpId(String dealerCorpId) {
/* 198 */     this.dealerCorpId = dealerCorpId;
/*     */   }
/*     */ 
/*     */   public String getDealerCorp() {
/* 202 */     return this.dealerCorp;
/*     */   }
/*     */ 
/*     */   public void setDealerCorp(String dealerCorp) {
/* 206 */     this.dealerCorp = dealerCorp;
/*     */   }
/*     */ 
/*     */   public String getDealerDnid() {
/* 210 */     return this.dealerDnid;
/*     */   }
/*     */ 
/*     */   public void setDealerDnid(String dealerDnid) {
/* 214 */     this.dealerDnid = dealerDnid;
/*     */   }
/*     */ 
/*     */   public String getDealerDn() {
/* 218 */     return this.dealerDn;
/*     */   }
/*     */ 
/*     */   public void setDealerDn(String dealerDn) {
/* 222 */     this.dealerDn = dealerDn;
/*     */   }
/*     */ 
/*     */   public BaseTask() {
/* 226 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */   @Id
/*     */   public String getId() {
/* 231 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/* 234 */     this.id = id;
/*     */   }
/*     */   public String getEntryId() {
/* 237 */     return this.entryId;
/*     */   }
/*     */   public void setEntryId(String entryId) {
/* 240 */     this.entryId = entryId;
/*     */   }
/*     */   public String getActorType() {
/* 243 */     return this.actorType;
/*     */   }
/*     */   public void setActorType(String actorType) {
/* 246 */     this.actorType = actorType;
/*     */   }
/*     */   public String getStepId() {
/* 249 */     return this.stepId;
/*     */   }
/*     */   public void setStepId(String stepId) {
/* 252 */     this.stepId = stepId;
/*     */   }
/*     */   public String getProcessState() {
/* 255 */     return this.processState;
/*     */   }
/*     */   public void setProcessState(String processState) {
/* 258 */     this.processState = processState;
/*     */   }
/*     */   public String getEntryState() {
/* 261 */     return this.entryState;
/*     */   }
/*     */   public void setEntryState(String entryState) {
/* 264 */     this.entryState = entryState;
/*     */   }
/*     */   public long getCreateTime() {
/* 267 */     return this.createTime;
/*     */   }
/*     */   public void setCreateTime(long createTime) {
/* 270 */     this.createTime = createTime;
/*     */   }
/*     */   public long getAcceptTime() {
/* 273 */     return this.acceptTime;
/*     */   }
/*     */   public void setAcceptTime(long acceptTime) {
/* 276 */     this.acceptTime = acceptTime;
/*     */   }
/*     */   public long getFinishTime() {
/* 279 */     return this.finishTime;
/*     */   }
/*     */   public void setFinishTime(long finishTime) {
/* 282 */     this.finishTime = finishTime;
/*     */   }
/*     */   public String getDealType() {
/* 285 */     return this.dealType;
/*     */   }
/*     */   public void setDealType(String dealType) {
/* 288 */     this.dealType = dealType;
/*     */   }
/*     */ 
/*     */   public String getStepCode() {
/* 292 */     return this.stepCode;
/*     */   }
/*     */   public void setStepCode(String stepCode) {
/* 295 */     this.stepCode = stepCode;
/*     */   }
/*     */ 
/*     */   public String getActionName() {
/* 299 */     return this.actionName;
/*     */   }
/*     */ 
/*     */   public void setActionName(String actionName) {
/* 303 */     this.actionName = actionName;
/*     */   }
/*     */ 
/*     */   public String getActionCode() {
/* 307 */     return this.actionCode;
/*     */   }
/*     */ 
/*     */   public void setActionCode(String actionCode) {
/* 311 */     this.actionCode = actionCode;
/*     */   }
/*     */ 
/*     */   public Object clone() {
/* 315 */     Object result = null;
/*     */     try {
/* 317 */       result = super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 319 */       e.printStackTrace();
/*     */     }
/* 321 */     return result;
/*     */   }
/*     */   public abstract BaseTask toCurrent();
/*     */ 
/*     */   public abstract BaseTask toHistory();
/*     */ 
/* 329 */   public String getParentEntryId() { return this.parentEntryId; }
/*     */ 
/*     */   public void setParentEntryId(String parentEntryId)
/*     */   {
/* 333 */     this.parentEntryId = parentEntryId;
/*     */   }
/*     */ 
/*     */   public String getTopEntryId() {
/* 337 */     return this.topEntryId;
/*     */   }
/*     */ 
/*     */   public void setTopEntryId(String topEntryId) {
/* 341 */     this.topEntryId = topEntryId;
/*     */   }
/*     */ 
/*     */   public String getParentTaskId() {
/* 345 */     return this.parentTaskId;
/*     */   }
/*     */ 
/*     */   public void setParentTaskId(String parentTaskId) {
/* 349 */     this.parentTaskId = parentTaskId;
/*     */   }
/*     */ 
/*     */   public String getTopTaskId() {
/* 353 */     return this.topTaskId;
/*     */   }
/*     */ 
/*     */   public void setTopTaskId(String topTaskId) {
/* 357 */     this.topTaskId = topTaskId;
/*     */   }
/*     */ 
/*     */   public String getCreatorName() {
/* 361 */     return this.creatorName;
/*     */   }
/*     */ 
/*     */   public void setCreatorName(String creatorName) {
/* 365 */     this.creatorName = creatorName;
/*     */   }
/*     */ 
/*     */   public String getCreatorType() {
/* 369 */     return this.creatorType;
/*     */   }
/*     */ 
/*     */   public void setCreatorType(String creatorType) {
/* 373 */     this.creatorType = creatorType;
/*     */   }
/*     */ 
/*     */   public String getCreator() {
/* 377 */     return this.creator;
/*     */   }
/*     */ 
/*     */   public void setCreator(String creator) {
/* 381 */     this.creator = creator;
/*     */   }
/*     */ 
/*     */   public String getAssignerId() {
/* 385 */     return this.assignerId;
/*     */   }
/*     */ 
/*     */   public void setAssignerId(String assignerId) {
/* 389 */     this.assignerId = assignerId;
/*     */   }
/*     */ 
/*     */   public String getAssigner() {
/* 393 */     return this.assigner;
/*     */   }
/*     */ 
/*     */   public void setAssigner(String assigner) {
/* 397 */     this.assigner = assigner;
/*     */   }
/*     */ 
/*     */   public String getAssignerDepId() {
/* 401 */     return this.assignerDepId;
/*     */   }
/*     */ 
/*     */   public void setAssignerDepId(String assignerDepId) {
/* 405 */     this.assignerDepId = assignerDepId;
/*     */   }
/*     */ 
/*     */   public String getAssignerDep() {
/* 409 */     return this.assignerDep;
/*     */   }
/*     */ 
/*     */   public void setAssignerDep(String assignerDep) {
/* 413 */     this.assignerDep = assignerDep;
/*     */   }
/*     */ 
/*     */   public String getAssignerCorpId() {
/* 417 */     return this.assignerCorpId;
/*     */   }
/*     */ 
/*     */   public void setAssignerCorpId(String assignerCorpId) {
/* 421 */     this.assignerCorpId = assignerCorpId;
/*     */   }
/*     */ 
/*     */   public String getAssignerCorp() {
/* 425 */     return this.assignerCorp;
/*     */   }
/*     */ 
/*     */   public void setAssignerCorp(String assignerCorp) {
/* 429 */     this.assignerCorp = assignerCorp;
/*     */   }
/*     */ 
/*     */   public String getAssignerDnid() {
/* 433 */     return this.assignerDnid;
/*     */   }
/*     */ 
/*     */   public void setAssignerDnid(String assignerDnid) {
/* 437 */     this.assignerDnid = assignerDnid;
/*     */   }
/*     */ 
/*     */   public String getAssignerDn() {
/* 441 */     return this.assignerDn;
/*     */   }
/*     */ 
/*     */   public void setAssignerDn(String assignerDn) {
/* 445 */     this.assignerDn = assignerDn;
/*     */   }
/*     */ 
/*     */   public String getSerialNum() {
/* 449 */     return this.serialNum;
/*     */   }
/*     */ 
/*     */   public void setSerialNum(String serialNum) {
/* 453 */     this.serialNum = serialNum;
/*     */   }
/*     */ 
/*     */   public String getBaseSchema() {
/* 457 */     return this.baseSchema;
/*     */   }
/*     */ 
/*     */   public void setBaseSchema(String baseSchema) {
/* 461 */     this.baseSchema = baseSchema;
/*     */   }
/*     */ 
/*     */   public String getBaseName() {
/* 465 */     return this.baseName;
/*     */   }
/*     */ 
/*     */   public void setBaseName(String baseName) {
/* 469 */     this.baseName = baseName;
/*     */   }
/*     */ 
/*     */   public String getBaseId() {
/* 473 */     return this.baseId;
/*     */   }
/*     */ 
/*     */   public void setBaseId(String baseId) {
/* 477 */     this.baseId = baseId;
/*     */   }
/*     */ 
/*     */   public long getBaseCreateTime() {
/* 481 */     return this.baseCreateTime;
/*     */   }
/*     */ 
/*     */   public void setBaseCreateTime(long baseCreateTime) {
/* 485 */     this.baseCreateTime = baseCreateTime;
/*     */   }
/*     */ 
/*     */   public String getStepName() {
/* 489 */     return this.stepName;
/*     */   }
/*     */ 
/*     */   public void setStepName(String stepName) {
/* 493 */     this.stepName = stepName;
/*     */   }
/*     */ 
/*     */   public String getWorkSheetTitle() {
/* 497 */     return this.workSheetTitle;
/*     */   }
/*     */ 
/*     */   public void setWorkSheetTitle(String workSheetTitle) {
/* 501 */     this.workSheetTitle = workSheetTitle;
/*     */   }
/*     */ 
/*     */   public String getWorkSheetProType() {
/* 505 */     return this.workSheetProType;
/*     */   }
/*     */ 
/*     */   public void setWorkSheetProType(String workSheetProType) {
/* 509 */     this.workSheetProType = workSheetProType;
/*     */   }
/*     */ 
/*     */   public String getWorkSheetUrgentLevel() {
/* 513 */     return this.workSheetUrgentLevel;
/*     */   }
/*     */ 
/*     */   public void setWorkSheetUrgentLevel(String workSheetUrgentLevel) {
/* 517 */     this.workSheetUrgentLevel = workSheetUrgentLevel;
/*     */   }
/*     */ 
/*     */   public String getTaskName() {
/* 521 */     return this.taskName;
/*     */   }
/*     */ 
/*     */   public void setTaskName(String taskName) {
/* 525 */     this.taskName = taskName;
/*     */   }
/*     */ 
/*     */   public String getStepNo() {
/* 529 */     return this.stepNo;
/*     */   }
/*     */ 
/*     */   public void setStepNo(String stepNo) {
/* 533 */     this.stepNo = stepNo;
/*     */   }
/*     */ 
/*     */   public String getStepGroup() {
/* 537 */     return this.stepGroup;
/*     */   }
/*     */ 
/*     */   public void setStepGroup(String stepGroup) {
/* 541 */     this.stepGroup = stepGroup;
/*     */   }
/*     */ 
/*     */   public String getTrack() {
/* 545 */     return this.track;
/*     */   }
/*     */ 
/*     */   public void setTrack(String track) {
/* 549 */     this.track = track;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.task.model.BaseTask
 * JD-Core Version:    0.6.0
 */