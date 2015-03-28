/*     */ package com.ultrapower.workflow.engine.task.model;
/*     */ 
/*     */ import javax.persistence.MappedSuperclass;
/*     */ import javax.persistence.Transient;
/*     */ 
/*     */ @MappedSuperclass
/*     */ public class ProcessTask<T> extends BaseTask<T>
/*     */ {
/*     */   private long acceptDate;
/*     */   private long sendDate;
/*     */   private long dueDate;
/*     */   private String workSheetDesc;
/*     */   private String defName;
/*     */   private String roleId;
/*     */   private String roleName;
/*     */   private long flagActive;
/*     */   private String flagAssignType;
/*     */   private String processType;
/*     */   private String prePhaseNo;
/*     */   private long flagDuplicated;
/*     */   private long flagEndDuplicated;
/*     */   private long flagEndPhase;
/*     */   private long flagPreDefined;
/*     */   private long baseStartDate;
/*     */   private long flagAssign;
/*     */   private long flagAssist;
/*     */   private long flagCopy;
/*     */   private long flagTransfer;
/*     */   private long flagTurnUp;
/*     */   private long flagRecall;
/*     */   private long flagCancel;
/*     */   private long flagClose;
/*     */   private long flagTurnDown;
/*     */   private long flagToAssistAuditing;
/*     */   private long flagToAuditing;
/*     */   private long flagAuditingResult;
/*     */   private long insideFlowsCount;
/*     */   private long finishInsideFlowsCount;
/*     */   private long hastenCount;
/*     */   private String stProcessAction;
/*     */   private String edProcessAction;
/*     */   private String flowId;
/*     */   private String parentFlowId;
/*     */   private String baseFlowId;
/*     */   private String customActions;
/*     */   private String preAssignString;
/*     */   private String origPhaseNo;
/*     */   private String dealTypeStr;
/*     */ 
/*     */   public long getAcceptDate()
/*     */   {
/*  55 */     return this.acceptDate;
/*     */   }
/*     */ 
/*     */   public void setAcceptDate(long acceptDate) {
/*  59 */     this.acceptDate = acceptDate;
/*     */   }
/*     */ 
/*     */   public long getSendDate() {
/*  63 */     return this.sendDate;
/*     */   }
/*     */ 
/*     */   public void setSendDate(long sendDate) {
/*  67 */     this.sendDate = sendDate;
/*     */   }
/*     */ 
/*     */   public long getDueDate() {
/*  71 */     return this.dueDate;
/*     */   }
/*     */ 
/*     */   public void setDueDate(long dueDate) {
/*  75 */     this.dueDate = dueDate;
/*     */   }
/*     */ 
/*     */   public String getWorkSheetDesc() {
/*  79 */     return this.workSheetDesc;
/*     */   }
/*     */ 
/*     */   public void setWorkSheetDesc(String workSheetDesc) {
/*  83 */     this.workSheetDesc = workSheetDesc;
/*     */   }
/*     */ 
/*     */   public BaseTask toCurrent()
/*     */   {
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */   public BaseTask toHistory()
/*     */   {
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDefName() {
/*  99 */     return this.defName;
/*     */   }
/*     */ 
/*     */   public void setDefName(String defName) {
/* 103 */     this.defName = defName;
/*     */   }
/*     */ 
/*     */   public long getFlagActive() {
/* 107 */     return this.flagActive;
/*     */   }
/*     */ 
/*     */   public void setFlagActive(long flagActive) {
/* 111 */     this.flagActive = flagActive;
/*     */   }
/*     */ 
/*     */   public String getFlagAssignType() {
/* 115 */     return this.flagAssignType;
/*     */   }
/*     */ 
/*     */   public void setFlagAssignType(String flagAssignType) {
/* 119 */     this.flagAssignType = flagAssignType;
/*     */   }
/*     */ 
/*     */   public String getProcessType() {
/* 123 */     return this.processType;
/*     */   }
/*     */ 
/*     */   public void setProcessType(String processType) {
/* 127 */     this.processType = processType;
/*     */   }
/*     */ 
/*     */   public String getRoleId() {
/* 131 */     return this.roleId;
/*     */   }
/*     */ 
/*     */   public void setRoleId(String roleId) {
/* 135 */     this.roleId = roleId;
/*     */   }
/*     */ 
/*     */   public String getRoleName() {
/* 139 */     return this.roleName;
/*     */   }
/*     */ 
/*     */   public void setRoleName(String roleName) {
/* 143 */     this.roleName = roleName;
/*     */   }
/*     */ 
/*     */   public String getPrePhaseNo() {
/* 147 */     return this.prePhaseNo;
/*     */   }
/*     */ 
/*     */   public void setPrePhaseNo(String prePhaseNo) {
/* 151 */     this.prePhaseNo = prePhaseNo;
/*     */   }
/*     */ 
/*     */   public String getStProcessAction() {
/* 155 */     return this.stProcessAction;
/*     */   }
/*     */ 
/*     */   public void setStProcessAction(String stProcessAction) {
/* 159 */     this.stProcessAction = stProcessAction;
/*     */   }
/*     */ 
/*     */   public String getFlowId() {
/* 163 */     return this.flowId;
/*     */   }
/*     */ 
/*     */   public void setFlowId(String flowId) {
/* 167 */     this.flowId = flowId;
/*     */   }
/*     */ 
/*     */   public String getParentFlowId() {
/* 171 */     return this.parentFlowId;
/*     */   }
/*     */ 
/*     */   public void setParentFlowId(String parentFlowId) {
/* 175 */     this.parentFlowId = parentFlowId;
/*     */   }
/*     */ 
/*     */   public String getBaseFlowId() {
/* 179 */     return this.baseFlowId;
/*     */   }
/*     */ 
/*     */   public void setBaseFlowId(String baseFlowId) {
/* 183 */     this.baseFlowId = baseFlowId;
/*     */   }
/*     */ 
/*     */   public String getCustomActions() {
/* 187 */     return this.customActions;
/*     */   }
/*     */ 
/*     */   public void setCustomActions(String customActions) {
/* 191 */     this.customActions = customActions;
/*     */   }
/*     */ 
/*     */   public String getPreAssignString() {
/* 195 */     return this.preAssignString;
/*     */   }
/*     */ 
/*     */   public void setPreAssignString(String preAssignString) {
/* 199 */     this.preAssignString = preAssignString;
/*     */   }
/*     */ 
/*     */   public String getOrigPhaseNo() {
/* 203 */     return this.origPhaseNo;
/*     */   }
/*     */ 
/*     */   public void setOrigPhaseNo(String origPhaseNo) {
/* 207 */     this.origPhaseNo = origPhaseNo;
/*     */   }
/*     */ 
/*     */   public long getFlagDuplicated() {
/* 211 */     return this.flagDuplicated;
/*     */   }
/*     */ 
/*     */   public void setFlagDuplicated(long flagDuplicated) {
/* 215 */     this.flagDuplicated = flagDuplicated;
/*     */   }
/*     */ 
/*     */   public long getFlagEndDuplicated() {
/* 219 */     return this.flagEndDuplicated;
/*     */   }
/*     */ 
/*     */   public void setFlagEndDuplicated(long flagEndDuplicated) {
/* 223 */     this.flagEndDuplicated = flagEndDuplicated;
/*     */   }
/*     */ 
/*     */   public long getFlagEndPhase() {
/* 227 */     return this.flagEndPhase;
/*     */   }
/*     */ 
/*     */   public void setFlagEndPhase(long flagEndPhase) {
/* 231 */     this.flagEndPhase = flagEndPhase;
/*     */   }
/*     */ 
/*     */   public long getFlagPreDefined() {
/* 235 */     return this.flagPreDefined;
/*     */   }
/*     */ 
/*     */   public void setFlagPreDefined(long flagPreDefined) {
/* 239 */     this.flagPreDefined = flagPreDefined;
/*     */   }
/*     */ 
/*     */   public long getBaseStartDate() {
/* 243 */     return this.baseStartDate;
/*     */   }
/*     */ 
/*     */   public void setBaseStartDate(long baseStartDate) {
/* 247 */     this.baseStartDate = baseStartDate;
/*     */   }
/*     */ 
/*     */   public long getFlagAssign() {
/* 251 */     return this.flagAssign;
/*     */   }
/*     */ 
/*     */   public void setFlagAssign(long flagAssign) {
/* 255 */     this.flagAssign = flagAssign;
/*     */   }
/*     */ 
/*     */   public long getFlagCopy() {
/* 259 */     return this.flagCopy;
/*     */   }
/*     */ 
/*     */   public void setFlagCopy(long flagCopy) {
/* 263 */     this.flagCopy = flagCopy;
/*     */   }
/*     */ 
/*     */   public long getFlagTransfer() {
/* 267 */     return this.flagTransfer;
/*     */   }
/*     */ 
/*     */   public void setFlagTransfer(long flagTransfer) {
/* 271 */     this.flagTransfer = flagTransfer;
/*     */   }
/*     */ 
/*     */   public long getFlagTurnUp() {
/* 275 */     return this.flagTurnUp;
/*     */   }
/*     */ 
/*     */   public void setFlagTurnUp(long flagTurnUp) {
/* 279 */     this.flagTurnUp = flagTurnUp;
/*     */   }
/*     */ 
/*     */   public long getFlagRecall() {
/* 283 */     return this.flagRecall;
/*     */   }
/*     */ 
/*     */   public void setFlagRecall(long flagRecall) {
/* 287 */     this.flagRecall = flagRecall;
/*     */   }
/*     */ 
/*     */   public long getFlagCancel() {
/* 291 */     return this.flagCancel;
/*     */   }
/*     */ 
/*     */   public void setFlagCancel(long flagCancel) {
/* 295 */     this.flagCancel = flagCancel;
/*     */   }
/*     */ 
/*     */   public long getFlagClose() {
/* 299 */     return this.flagClose;
/*     */   }
/*     */ 
/*     */   public void setFlagClose(long flagClose) {
/* 303 */     this.flagClose = flagClose;
/*     */   }
/*     */ 
/*     */   public long getFlagTurnDown() {
/* 307 */     return this.flagTurnDown;
/*     */   }
/*     */ 
/*     */   public void setFlagTurnDown(long flagTurnDown) {
/* 311 */     this.flagTurnDown = flagTurnDown;
/*     */   }
/*     */ 
/*     */   public long getFlagToAssistAuditing() {
/* 315 */     return this.flagToAssistAuditing;
/*     */   }
/*     */ 
/*     */   public void setFlagToAssistAuditing(long flagToAssistAuditing) {
/* 319 */     this.flagToAssistAuditing = flagToAssistAuditing;
/*     */   }
/*     */ 
/*     */   public long getFlagToAuditing() {
/* 323 */     return this.flagToAuditing;
/*     */   }
/*     */ 
/*     */   public void setFlagToAuditing(long flagToAuditing) {
/* 327 */     this.flagToAuditing = flagToAuditing;
/*     */   }
/*     */ 
/*     */   public long getFlagAuditingResult() {
/* 331 */     return this.flagAuditingResult;
/*     */   }
/*     */ 
/*     */   public void setFlagAuditingResult(long flagAuditingResult) {
/* 335 */     this.flagAuditingResult = flagAuditingResult;
/*     */   }
/*     */ 
/*     */   public long getInsideFlowsCount() {
/* 339 */     return this.insideFlowsCount;
/*     */   }
/*     */ 
/*     */   public void setInsideFlowsCount(long insideFlowsCount) {
/* 343 */     this.insideFlowsCount = insideFlowsCount;
/*     */   }
/*     */ 
/*     */   public long getFinishInsideFlowsCount() {
/* 347 */     return this.finishInsideFlowsCount;
/*     */   }
/*     */ 
/*     */   public void setFinishInsideFlowsCount(long finishInsideFlowsCount) {
/* 351 */     this.finishInsideFlowsCount = finishInsideFlowsCount;
/*     */   }
/*     */ 
/*     */   public long getHastenCount() {
/* 355 */     return this.hastenCount;
/*     */   }
/*     */ 
/*     */   public void setHastenCount(long hastenCount) {
/* 359 */     this.hastenCount = hastenCount;
/*     */   }
/*     */ 
/*     */   public long getFlagAssist() {
/* 363 */     return this.flagAssist;
/*     */   }
/*     */ 
/*     */   public void setFlagAssist(long flagAssist) {
/* 367 */     this.flagAssist = flagAssist;
/*     */   }
/*     */ 
/*     */   public String getEdProcessAction() {
/* 371 */     return this.edProcessAction;
/*     */   }
/*     */ 
/*     */   public void setEdProcessAction(String edProcessAction) {
/* 375 */     this.edProcessAction = edProcessAction;
/*     */   }
/*     */   @Transient
/*     */   public String getDealTypeStr() {
/* 380 */     return this.dealTypeStr;
/*     */   }
/*     */ 
/*     */   public void setDealTypeStr(String dealTypeStr) {
/* 384 */     this.dealTypeStr = dealTypeStr;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.task.model.ProcessTask
 * JD-Core Version:    0.6.0
 */