/*      */ package com.ultrapower.workflow.bizform.model;
/*      */ 
/*      */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*      */ import java.io.Serializable;
/*      */ import javax.persistence.Entity;
/*      */ import javax.persistence.Id;
/*      */ import javax.persistence.Table;
/*      */ import javax.persistence.Transient;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ @Entity
/*      */ @Table(name="BS_T_WF_DEALPROCESS")
/*      */ public class DealProcessModel
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   private String processId;
/*      */   private String processType;
/*      */   private String taskId;
/*      */   private String phaseNo;
/*      */   private String stepId;
/*      */   private String forwardStepId;
/*      */   private String prePhaseNo;
/*      */   private String origPhaseNo;
/*      */   private String stepGroup;
/*      */   private String track;
/*      */   private String processStatus;
/*      */   private long flagActive;
/*      */   private long flagDuplicated;
/*      */   private long flagEndDuplicated;
/*   51 */   private long flagEndPhase = 1L;
/*      */   private long flagPredefined;
/*      */   private String stProcessAction;
/*      */   private String edProcessAction;
/*      */   private long selfDealLength;
/*      */   private long baseStartDate;
/*      */   private String baseId;
/*      */   private String baseSchema;
/*      */   private String entryId;
/*      */   private String parentEntryId;
/*      */   private String topEntryId;
/*      */   private String flowId;
/*      */   private String parentFlowId;
/*      */   private String baseFlowId;
/*      */   private String assigneeId;
/*      */   private String assignee;
/*      */   private String assigneeDepId;
/*      */   private String assigneeDep;
/*      */   private String assigneeCorpId;
/*      */   private String assigneeCorp;
/*      */   private String assigneeDnid;
/*      */   private String assigneeDn;
/*      */   private String assignGroupId;
/*      */   private String assignGroup;
/*      */   private String flagAssignType;
/*      */   private String dealerId;
/*      */   private String dealer;
/*      */   private String dealerDepId;
/*      */   private String dealerDep;
/*      */   private String dealerCorpId;
/*      */   private String dealerCorp;
/*      */   private String dealerDnid;
/*      */   private String dealerDn;
/*      */   private String assignerId;
/*      */   private String assigner;
/*      */   private String assignerDepId;
/*      */   private String assignerDep;
/*      */   private String assignerCorpId;
/*      */   private String assignerCorp;
/*      */   private String assignerDnid;
/*      */   private String assignerDn;
/*      */   private long stDate;
/*      */   private long bgDate;
/*      */   private long edDate;
/*      */   private long assignOverTimeDate;
/*      */   private long acceptOverTimeDate;
/*      */   private long dealOverTimeDate;
/*      */   private long flagAssign;
/*      */   private long flagAssist;
/*      */   private long flagCopy;
/*      */   private long flagTransfer;
/*      */   private long flagTurnUp;
/*      */   private long flagRecall;
/*      */   private long flagCancel;
/*      */   private long flagClose;
/*      */   private long flagTurnDown;
/*      */   private long flagToAssistAuditing;
/*      */   private long flagToAuditing;
/*      */   private long flagStartInsideFlow;
/*      */   private long flagAuditingResult;
/*      */   private long insideFlowsCount;
/*      */   private long finishInsideFlowsCount;
/*      */   private long hastenCount;
/*      */   private String customActions;
/*      */   private String preAssignString;
/*      */   private String dpDesc;
/*      */   private long baseCreateTime;
/*      */   private String actorType;
/*      */   private String dealType;
/*      */   private String roleId;
/*      */   private String roleName;
/*      */   private String actionName;
/*      */   private String stepNo;
/*      */   private String dealTypeStr;
/*      */ 
/*      */   public DealProcessModel clone()
/*      */   {
/*      */     try
/*      */     {
/*   27 */       return (DealProcessModel)super.clone();
/*      */     }
/*      */     catch (Exception e) {
/*      */     }
/*   31 */     return null;
/*      */   }
/*      */ 
/*      */   public DealProcessModel()
/*      */   {
/*  138 */     this.processId = UUIDGenerator.getId();
/*      */   }
/*      */ 
/*      */   public DealProcessModel(String processType, String taskId, String phaseNo, String prePhaseNo, String origPhaseNo, String processStatus, Long flagActive, Long flagDuplicated, Long flagEndDuplicated, Long flagEndPhase, Long flagPredefined, String stProcessAction, String edProcessAction, Long selfDealLength, Long baseStartDate, String baseId, String baseSchema, String flowId, String parentBaseId, String parentFlowId, String baseBaseId, String baseFlowId, String assigneeId, String assignee, String assigneeDepId, String assigneeDep, String assigneeCorpId, String assigneeCorp, String assigneeDnid, String assigneeDn, String assignGroupId, String assignGroup, String flagAssignType, String dealerId, String dealer, String dealerDepId, String dealerDep, String dealerCorpId, String dealerCorp, String dealerDnid, String dealerDn, String assignerId, String assigner, String assignerDepId, String assignerDep, String assignerCorpId, String assignerCorp, String assignerDnid, String assignerDn, Long stDate, Long bgDate, Long edDate, Long assignOverTimeDate, Long acceptOverTimeDate, Long dealOverTimeDate, Long flagAssign, Long flagAssist, Long flagCopy, Long flagTransfer, Long flagTurnUp, Long flagRecall, Long flagCancel, Long flagClose, Long flagTurnDown, Long flagToAssistAuditing, Long flagToAuditing, Long flagStartInsideFlow, Long flagAuditingResult, Long insideFlowsCount, Long finishInsideFlowsCount, String actionPageName, String actionPageId, String customActions, String preAssignString)
/*      */   {
/*  144 */     this.processId = UUIDGenerator.getId();
/*  145 */     this.processType = processType;
/*  146 */     this.taskId = taskId;
/*  147 */     this.phaseNo = phaseNo;
/*  148 */     this.prePhaseNo = prePhaseNo;
/*  149 */     this.origPhaseNo = origPhaseNo;
/*  150 */     this.processStatus = processStatus;
/*  151 */     this.flagActive = flagActive.longValue();
/*  152 */     this.flagDuplicated = flagDuplicated.longValue();
/*  153 */     this.flagEndDuplicated = flagEndDuplicated.longValue();
/*  154 */     this.flagEndPhase = flagEndPhase.longValue();
/*  155 */     this.flagPredefined = flagPredefined.longValue();
/*  156 */     this.stProcessAction = stProcessAction;
/*  157 */     this.edProcessAction = edProcessAction;
/*  158 */     this.selfDealLength = selfDealLength.longValue();
/*  159 */     this.baseStartDate = baseStartDate.longValue();
/*  160 */     this.baseId = baseId;
/*  161 */     this.baseSchema = baseSchema;
/*  162 */     this.flowId = flowId;
/*  163 */     this.parentFlowId = parentFlowId;
/*  164 */     this.baseFlowId = baseFlowId;
/*  165 */     this.assigneeId = assigneeId;
/*  166 */     this.assignee = assignee;
/*  167 */     this.assigneeDepId = assigneeDepId;
/*  168 */     this.assigneeDep = assigneeDep;
/*  169 */     this.assigneeCorpId = assigneeCorpId;
/*  170 */     this.assigneeCorp = assigneeCorp;
/*  171 */     this.assigneeDnid = assigneeDnid;
/*  172 */     this.assigneeDn = assigneeDn;
/*  173 */     this.assignGroupId = assignGroupId;
/*  174 */     this.assignGroup = assignGroup;
/*  175 */     this.flagAssignType = flagAssignType;
/*  176 */     this.dealerId = dealerId;
/*  177 */     this.dealer = dealer;
/*  178 */     this.dealerDepId = dealerDepId;
/*  179 */     this.dealerDep = dealerDep;
/*  180 */     this.dealerCorpId = dealerCorpId;
/*  181 */     this.dealerCorp = dealerCorp;
/*  182 */     this.dealerDnid = dealerDnid;
/*  183 */     this.dealerDn = dealerDn;
/*  184 */     this.assignerId = assignerId;
/*  185 */     this.assigner = assigner;
/*  186 */     this.assignerDepId = assignerDepId;
/*  187 */     this.assignerDep = assignerDep;
/*  188 */     this.assignerCorpId = assignerCorpId;
/*  189 */     this.assignerCorp = assignerCorp;
/*  190 */     this.assignerDnid = assignerDnid;
/*  191 */     this.assignerDn = assignerDn;
/*  192 */     this.stDate = stDate.longValue();
/*  193 */     this.bgDate = bgDate.longValue();
/*  194 */     this.edDate = edDate.longValue();
/*  195 */     this.assignOverTimeDate = assignOverTimeDate.longValue();
/*  196 */     this.acceptOverTimeDate = acceptOverTimeDate.longValue();
/*  197 */     this.dealOverTimeDate = dealOverTimeDate.longValue();
/*  198 */     this.flagAssign = flagAssign.longValue();
/*  199 */     this.flagAssist = flagAssist.longValue();
/*  200 */     this.flagCopy = flagCopy.longValue();
/*  201 */     this.flagTransfer = flagTransfer.longValue();
/*  202 */     this.flagTurnUp = flagTurnUp.longValue();
/*  203 */     this.flagRecall = flagRecall.longValue();
/*  204 */     this.flagCancel = flagCancel.longValue();
/*  205 */     this.flagClose = flagClose.longValue();
/*  206 */     this.flagTurnDown = flagTurnDown.longValue();
/*  207 */     this.flagToAssistAuditing = flagToAssistAuditing.longValue();
/*  208 */     this.flagToAuditing = flagToAuditing.longValue();
/*  209 */     this.flagStartInsideFlow = flagStartInsideFlow.longValue();
/*  210 */     this.flagAuditingResult = flagAuditingResult.longValue();
/*  211 */     this.insideFlowsCount = insideFlowsCount.longValue();
/*  212 */     this.finishInsideFlowsCount = finishInsideFlowsCount.longValue();
/*  213 */     this.customActions = customActions;
/*  214 */     this.preAssignString = preAssignString;
/*      */   }
/*      */ 
/*      */   @Id
/*      */   public String getProcessId()
/*      */   {
/*  222 */     return this.processId;
/*      */   }
/*      */ 
/*      */   public void setProcessId(String processId)
/*      */   {
/*  227 */     this.processId = processId;
/*      */   }
/*      */ 
/*      */   public String getTaskId()
/*      */   {
/*  232 */     return this.taskId;
/*      */   }
/*      */ 
/*      */   public void setTaskId(String taskId)
/*      */   {
/*  237 */     this.taskId = taskId;
/*      */   }
/*      */ 
/*      */   public String getPhaseNo()
/*      */   {
/*  242 */     return this.phaseNo;
/*      */   }
/*      */ 
/*      */   public void setPhaseNo(String phaseNo)
/*      */   {
/*  247 */     this.phaseNo = phaseNo;
/*      */   }
/*      */ 
/*      */   public String getPrePhaseNo()
/*      */   {
/*  252 */     return this.prePhaseNo;
/*      */   }
/*      */ 
/*      */   public void setPrePhaseNo(String prePhaseNo)
/*      */   {
/*  257 */     this.prePhaseNo = prePhaseNo;
/*      */   }
/*      */ 
/*      */   public String getOrigPhaseNo()
/*      */   {
/*  262 */     return this.origPhaseNo;
/*      */   }
/*      */ 
/*      */   public void setOrigPhaseNo(String origPhaseNo)
/*      */   {
/*  267 */     this.origPhaseNo = origPhaseNo;
/*      */   }
/*      */ 
/*      */   public String getProcessStatus()
/*      */   {
/*  272 */     return this.processStatus;
/*      */   }
/*      */ 
/*      */   public void setProcessStatus(String processStatus)
/*      */   {
/*  277 */     this.processStatus = processStatus;
/*      */   }
/*      */ 
/*      */   public Long getFlagActive()
/*      */   {
/*  282 */     return Long.valueOf(this.flagActive);
/*      */   }
/*      */ 
/*      */   public void setFlagActive(Long flagActive)
/*      */   {
/*  287 */     this.flagActive = flagActive.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagDuplicated()
/*      */   {
/*  292 */     return Long.valueOf(this.flagDuplicated);
/*      */   }
/*      */ 
/*      */   public void setFlagDuplicated(Long flagDuplicated)
/*      */   {
/*  297 */     this.flagDuplicated = flagDuplicated.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagEndDuplicated()
/*      */   {
/*  302 */     return Long.valueOf(this.flagEndDuplicated);
/*      */   }
/*      */ 
/*      */   public void setFlagEndDuplicated(Long flagEndDuplicated)
/*      */   {
/*  307 */     this.flagEndDuplicated = flagEndDuplicated.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagEndPhase()
/*      */   {
/*  312 */     return Long.valueOf(this.flagEndPhase);
/*      */   }
/*      */ 
/*      */   public void setFlagEndPhase(Long flagEndPhase)
/*      */   {
/*  317 */     this.flagEndPhase = flagEndPhase.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagPredefined()
/*      */   {
/*  322 */     return Long.valueOf(this.flagPredefined);
/*      */   }
/*      */ 
/*      */   public void setFlagPredefined(Long flagPredefined)
/*      */   {
/*  327 */     this.flagPredefined = flagPredefined.longValue();
/*      */   }
/*      */ 
/*      */   public String getStProcessAction()
/*      */   {
/*  332 */     return this.stProcessAction;
/*      */   }
/*      */ 
/*      */   public void setStProcessAction(String stProcessAction)
/*      */   {
/*  337 */     this.stProcessAction = stProcessAction;
/*      */   }
/*      */ 
/*      */   public String getEdProcessAction()
/*      */   {
/*  342 */     return this.edProcessAction;
/*      */   }
/*      */ 
/*      */   public void setEdProcessAction(String edProcessAction)
/*      */   {
/*  347 */     this.edProcessAction = edProcessAction;
/*      */   }
/*      */ 
/*      */   public Long getSelfDealLength()
/*      */   {
/*  352 */     return Long.valueOf(this.selfDealLength);
/*      */   }
/*      */ 
/*      */   public void setSelfDealLength(Long selfDealLength)
/*      */   {
/*  357 */     this.selfDealLength = selfDealLength.longValue();
/*      */   }
/*      */ 
/*      */   public Long getBaseStartDate()
/*      */   {
/*  362 */     return Long.valueOf(this.baseStartDate);
/*      */   }
/*      */ 
/*      */   public void setBaseStartDate(Long baseStartDate)
/*      */   {
/*  367 */     this.baseStartDate = baseStartDate.longValue();
/*      */   }
/*      */ 
/*      */   public String getBaseId()
/*      */   {
/*  372 */     return this.baseId;
/*      */   }
/*      */ 
/*      */   public void setBaseId(String baseId)
/*      */   {
/*  377 */     this.baseId = baseId;
/*      */   }
/*      */ 
/*      */   public String getBaseSchema()
/*      */   {
/*  382 */     return this.baseSchema;
/*      */   }
/*      */ 
/*      */   public void setBaseSchema(String baseSchema)
/*      */   {
/*  387 */     this.baseSchema = baseSchema;
/*      */   }
/*      */ 
/*      */   public String getFlowId()
/*      */   {
/*  392 */     return this.flowId;
/*      */   }
/*      */ 
/*      */   public void setFlowId(String flowId)
/*      */   {
/*  397 */     this.flowId = flowId;
/*      */   }
/*      */ 
/*      */   public String getParentFlowId()
/*      */   {
/*  402 */     return this.parentFlowId;
/*      */   }
/*      */ 
/*      */   public void setParentFlowId(String parentFlowId)
/*      */   {
/*  407 */     this.parentFlowId = parentFlowId;
/*      */   }
/*      */ 
/*      */   public String getBaseFlowId()
/*      */   {
/*  412 */     return this.baseFlowId;
/*      */   }
/*      */ 
/*      */   public void setBaseFlowId(String baseFlowId)
/*      */   {
/*  417 */     this.baseFlowId = baseFlowId;
/*      */   }
/*      */ 
/*      */   public String getAssigneeId()
/*      */   {
/*  422 */     return this.assigneeId;
/*      */   }
/*      */ 
/*      */   public void setAssigneeId(String assigneeId)
/*      */   {
/*  427 */     this.assigneeId = assigneeId;
/*      */   }
/*      */ 
/*      */   public String getAssignee()
/*      */   {
/*  432 */     return this.assignee;
/*      */   }
/*      */ 
/*      */   public void setAssignee(String assignee)
/*      */   {
/*  437 */     this.assignee = assignee;
/*      */   }
/*      */ 
/*      */   public String getAssigneeDepId()
/*      */   {
/*  442 */     return this.assigneeDepId;
/*      */   }
/*      */ 
/*      */   public void setAssigneeDepId(String assigneeDepId)
/*      */   {
/*  447 */     this.assigneeDepId = assigneeDepId;
/*      */   }
/*      */ 
/*      */   public String getAssigneeDep()
/*      */   {
/*  452 */     return this.assigneeDep;
/*      */   }
/*      */ 
/*      */   public void setAssigneeDep(String assigneeDep)
/*      */   {
/*  457 */     this.assigneeDep = assigneeDep;
/*      */   }
/*      */ 
/*      */   public String getAssigneeCorpId()
/*      */   {
/*  462 */     return this.assigneeCorpId;
/*      */   }
/*      */ 
/*      */   public void setAssigneeCorpId(String assigneeCorpId)
/*      */   {
/*  467 */     this.assigneeCorpId = assigneeCorpId;
/*      */   }
/*      */ 
/*      */   public String getAssigneeCorp()
/*      */   {
/*  472 */     return this.assigneeCorp;
/*      */   }
/*      */ 
/*      */   public void setAssigneeCorp(String assigneeCorp)
/*      */   {
/*  477 */     this.assigneeCorp = assigneeCorp;
/*      */   }
/*      */ 
/*      */   public String getAssigneeDnid()
/*      */   {
/*  482 */     return this.assigneeDnid;
/*      */   }
/*      */ 
/*      */   public void setAssigneeDnid(String assigneeDnid)
/*      */   {
/*  487 */     this.assigneeDnid = assigneeDnid;
/*      */   }
/*      */ 
/*      */   public String getAssigneeDn()
/*      */   {
/*  492 */     return this.assigneeDn;
/*      */   }
/*      */ 
/*      */   public void setAssigneeDn(String assigneeDn)
/*      */   {
/*  497 */     this.assigneeDn = assigneeDn;
/*      */   }
/*      */ 
/*      */   public String getAssignGroupId()
/*      */   {
/*  502 */     return this.assignGroupId;
/*      */   }
/*      */ 
/*      */   public void setAssignGroupId(String assignGroupId)
/*      */   {
/*  507 */     this.assignGroupId = assignGroupId;
/*      */   }
/*      */ 
/*      */   public String getAssignGroup()
/*      */   {
/*  512 */     return this.assignGroup;
/*      */   }
/*      */ 
/*      */   public void setAssignGroup(String assignGroup)
/*      */   {
/*  517 */     this.assignGroup = assignGroup;
/*      */   }
/*      */ 
/*      */   public String getFlagAssignType()
/*      */   {
/*  522 */     return this.flagAssignType;
/*      */   }
/*      */ 
/*      */   public void setFlagAssignType(String flagAssignType)
/*      */   {
/*  527 */     this.flagAssignType = flagAssignType;
/*      */   }
/*      */ 
/*      */   public String getDealerId()
/*      */   {
/*  532 */     return this.dealerId;
/*      */   }
/*      */ 
/*      */   public void setDealerId(String dealerId)
/*      */   {
/*  537 */     this.dealerId = dealerId;
/*      */   }
/*      */ 
/*      */   public String getDealer()
/*      */   {
/*  542 */     return this.dealer;
/*      */   }
/*      */ 
/*      */   public void setDealer(String dealer)
/*      */   {
/*  547 */     this.dealer = dealer;
/*      */   }
/*      */ 
/*      */   public String getDealerDepId()
/*      */   {
/*  552 */     return this.dealerDepId;
/*      */   }
/*      */ 
/*      */   public void setDealerDepId(String dealerDepId)
/*      */   {
/*  557 */     this.dealerDepId = dealerDepId;
/*      */   }
/*      */ 
/*      */   public String getDealerDep()
/*      */   {
/*  562 */     return this.dealerDep;
/*      */   }
/*      */ 
/*      */   public void setDealerDep(String dealerDep)
/*      */   {
/*  567 */     this.dealerDep = dealerDep;
/*      */   }
/*      */ 
/*      */   public String getDealerCorpId()
/*      */   {
/*  572 */     return this.dealerCorpId;
/*      */   }
/*      */ 
/*      */   public void setDealerCorpId(String dealerCorpId)
/*      */   {
/*  577 */     this.dealerCorpId = dealerCorpId;
/*      */   }
/*      */ 
/*      */   public String getDealerCorp()
/*      */   {
/*  582 */     return this.dealerCorp;
/*      */   }
/*      */ 
/*      */   public void setDealerCorp(String dealerCorp)
/*      */   {
/*  587 */     this.dealerCorp = dealerCorp;
/*      */   }
/*      */ 
/*      */   public String getDealerDnid()
/*      */   {
/*  592 */     return this.dealerDnid;
/*      */   }
/*      */ 
/*      */   public void setDealerDnid(String dealerDnid)
/*      */   {
/*  597 */     this.dealerDnid = dealerDnid;
/*      */   }
/*      */ 
/*      */   public String getDealerDn()
/*      */   {
/*  602 */     return this.dealerDn;
/*      */   }
/*      */ 
/*      */   public void setDealerDn(String dealerDn)
/*      */   {
/*  607 */     this.dealerDn = dealerDn;
/*      */   }
/*      */ 
/*      */   public String getAssignerId()
/*      */   {
/*  612 */     return this.assignerId;
/*      */   }
/*      */ 
/*      */   public void setAssignerId(String assignerId)
/*      */   {
/*  617 */     this.assignerId = assignerId;
/*      */   }
/*      */ 
/*      */   public String getAssigner()
/*      */   {
/*  622 */     return this.assigner;
/*      */   }
/*      */ 
/*      */   public void setAssigner(String assigner)
/*      */   {
/*  627 */     this.assigner = assigner;
/*      */   }
/*      */ 
/*      */   public String getAssignerDepId()
/*      */   {
/*  632 */     return this.assignerDepId;
/*      */   }
/*      */ 
/*      */   public void setAssignerDepId(String assignerDepId)
/*      */   {
/*  637 */     this.assignerDepId = assignerDepId;
/*      */   }
/*      */ 
/*      */   public String getAssignerDep()
/*      */   {
/*  642 */     return this.assignerDep;
/*      */   }
/*      */ 
/*      */   public void setAssignerDep(String assignerDep)
/*      */   {
/*  647 */     this.assignerDep = assignerDep;
/*      */   }
/*      */ 
/*      */   public String getAssignerCorpId()
/*      */   {
/*  652 */     return this.assignerCorpId;
/*      */   }
/*      */ 
/*      */   public void setAssignerCorpId(String assignerCorpId)
/*      */   {
/*  657 */     this.assignerCorpId = assignerCorpId;
/*      */   }
/*      */ 
/*      */   public String getAssignerCorp()
/*      */   {
/*  662 */     return this.assignerCorp;
/*      */   }
/*      */ 
/*      */   public void setAssignerCorp(String assignerCorp)
/*      */   {
/*  667 */     this.assignerCorp = assignerCorp;
/*      */   }
/*      */ 
/*      */   public String getAssignerDnid()
/*      */   {
/*  672 */     return this.assignerDnid;
/*      */   }
/*      */ 
/*      */   public void setAssignerDnid(String assignerDnid)
/*      */   {
/*  677 */     this.assignerDnid = assignerDnid;
/*      */   }
/*      */ 
/*      */   public String getAssignerDn()
/*      */   {
/*  682 */     return this.assignerDn;
/*      */   }
/*      */ 
/*      */   public void setAssignerDn(String assignerDn)
/*      */   {
/*  687 */     this.assignerDn = assignerDn;
/*      */   }
/*      */ 
/*      */   public Long getStDate()
/*      */   {
/*  692 */     return Long.valueOf(this.stDate);
/*      */   }
/*      */ 
/*      */   public void setStDate(Long stDate)
/*      */   {
/*  697 */     this.stDate = stDate.longValue();
/*      */   }
/*      */ 
/*      */   public Long getBgDate()
/*      */   {
/*  702 */     return Long.valueOf(this.bgDate);
/*      */   }
/*      */ 
/*      */   public void setBgDate(Long bgDate)
/*      */   {
/*  707 */     this.bgDate = bgDate.longValue();
/*      */   }
/*      */ 
/*      */   public Long getEdDate()
/*      */   {
/*  712 */     return Long.valueOf(this.edDate);
/*      */   }
/*      */ 
/*      */   public void setEdDate(Long edDate)
/*      */   {
/*  717 */     this.edDate = edDate.longValue();
/*      */   }
/*      */ 
/*      */   public Long getAssignOverTimeDate()
/*      */   {
/*  722 */     return Long.valueOf(this.assignOverTimeDate);
/*      */   }
/*      */ 
/*      */   public void setAssignOverTimeDate(Long assignOverTimeDate)
/*      */   {
/*  727 */     this.assignOverTimeDate = assignOverTimeDate.longValue();
/*      */   }
/*      */ 
/*      */   public Long getAcceptOverTimeDate()
/*      */   {
/*  732 */     return Long.valueOf(this.acceptOverTimeDate);
/*      */   }
/*      */ 
/*      */   public void setAcceptOverTimeDate(Long acceptOverTimeDate)
/*      */   {
/*  737 */     this.acceptOverTimeDate = acceptOverTimeDate.longValue();
/*      */   }
/*      */ 
/*      */   public Long getDealOverTimeDate()
/*      */   {
/*  742 */     return Long.valueOf(this.dealOverTimeDate);
/*      */   }
/*      */ 
/*      */   public void setDealOverTimeDate(Long dealOverTimeDate)
/*      */   {
/*  747 */     this.dealOverTimeDate = dealOverTimeDate.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagAssign()
/*      */   {
/*  752 */     return Long.valueOf(this.flagAssign);
/*      */   }
/*      */ 
/*      */   public void setFlagAssign(Long flagAssign)
/*      */   {
/*  757 */     this.flagAssign = flagAssign.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagAssist()
/*      */   {
/*  762 */     return Long.valueOf(this.flagAssist);
/*      */   }
/*      */ 
/*      */   public void setFlagAssist(Long flagAssist)
/*      */   {
/*  767 */     this.flagAssist = flagAssist.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagCopy()
/*      */   {
/*  772 */     return Long.valueOf(this.flagCopy);
/*      */   }
/*      */ 
/*      */   public void setFlagCopy(Long flagCopy)
/*      */   {
/*  777 */     this.flagCopy = flagCopy.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagTransfer()
/*      */   {
/*  782 */     return Long.valueOf(this.flagTransfer);
/*      */   }
/*      */ 
/*      */   public void setFlagTransfer(Long flagTransfer)
/*      */   {
/*  787 */     this.flagTransfer = flagTransfer.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagTurnUp()
/*      */   {
/*  792 */     return Long.valueOf(this.flagTurnUp);
/*      */   }
/*      */ 
/*      */   public void setFlagTurnUp(Long flagTurnUp)
/*      */   {
/*  797 */     this.flagTurnUp = flagTurnUp.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagRecall()
/*      */   {
/*  802 */     return Long.valueOf(this.flagRecall);
/*      */   }
/*      */ 
/*      */   public void setFlagRecall(Long flagRecall)
/*      */   {
/*  807 */     this.flagRecall = flagRecall.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagCancel()
/*      */   {
/*  812 */     return Long.valueOf(this.flagCancel);
/*      */   }
/*      */ 
/*      */   public void setFlagCancel(Long flagCancel)
/*      */   {
/*  817 */     this.flagCancel = flagCancel.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagClose()
/*      */   {
/*  822 */     return Long.valueOf(this.flagClose);
/*      */   }
/*      */ 
/*      */   public void setFlagClose(Long flagClose)
/*      */   {
/*  827 */     this.flagClose = flagClose.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagTurnDown()
/*      */   {
/*  832 */     return Long.valueOf(this.flagTurnDown);
/*      */   }
/*      */ 
/*      */   public void setFlagTurnDown(Long flagTurnDown)
/*      */   {
/*  837 */     this.flagTurnDown = flagTurnDown.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagToAssistAuditing()
/*      */   {
/*  842 */     return Long.valueOf(this.flagToAssistAuditing);
/*      */   }
/*      */ 
/*      */   public void setFlagToAssistAuditing(Long flagToAssistAuditing)
/*      */   {
/*  847 */     this.flagToAssistAuditing = flagToAssistAuditing.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagToAuditing()
/*      */   {
/*  852 */     return Long.valueOf(this.flagToAuditing);
/*      */   }
/*      */ 
/*      */   public void setFlagToAuditing(Long flagToAuditing)
/*      */   {
/*  857 */     this.flagToAuditing = flagToAuditing.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagStartInsideFlow()
/*      */   {
/*  862 */     return Long.valueOf(this.flagStartInsideFlow);
/*      */   }
/*      */ 
/*      */   public void setFlagStartInsideFlow(Long flagStartInsideFlow)
/*      */   {
/*  867 */     this.flagStartInsideFlow = flagStartInsideFlow.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFlagAuditingResult()
/*      */   {
/*  872 */     return Long.valueOf(this.flagAuditingResult);
/*      */   }
/*      */ 
/*      */   public void setFlagAuditingResult(Long flagAuditingResult)
/*      */   {
/*  877 */     this.flagAuditingResult = flagAuditingResult.longValue();
/*      */   }
/*      */ 
/*      */   public Long getInsideFlowsCount()
/*      */   {
/*  882 */     return Long.valueOf(this.insideFlowsCount);
/*      */   }
/*      */ 
/*      */   public void setInsideFlowsCount(Long insideFlowsCount)
/*      */   {
/*  887 */     this.insideFlowsCount = insideFlowsCount.longValue();
/*      */   }
/*      */ 
/*      */   public Long getFinishInsideFlowsCount()
/*      */   {
/*  892 */     return Long.valueOf(this.finishInsideFlowsCount);
/*      */   }
/*      */ 
/*      */   public void setFinishInsideFlowsCount(Long finishInsideFlowsCount)
/*      */   {
/*  897 */     this.finishInsideFlowsCount = finishInsideFlowsCount.longValue();
/*      */   }
/*      */ 
/*      */   public String getCustomActions()
/*      */   {
/*  902 */     return this.customActions;
/*      */   }
/*      */ 
/*      */   public void setCustomActions(String customActions)
/*      */   {
/*  907 */     this.customActions = customActions;
/*      */   }
/*      */ 
/*      */   public String getPreAssignString()
/*      */   {
/*  912 */     return this.preAssignString;
/*      */   }
/*      */ 
/*      */   public void setPreAssignString(String preAssignString)
/*      */   {
/*  917 */     this.preAssignString = preAssignString;
/*      */   }
/*      */ 
/*      */   public String getProcessType()
/*      */   {
/*  922 */     return this.processType;
/*      */   }
/*      */ 
/*      */   public void setProcessType(String processType)
/*      */   {
/*  927 */     if (StringUtils.isBlank(processType))
/*  928 */       this.processType = "DEAL";
/*      */     else
/*  930 */       this.processType = processType;
/*      */   }
/*      */ 
/*      */   public String getStepId()
/*      */   {
/*  935 */     return this.stepId;
/*      */   }
/*      */ 
/*      */   public void setStepId(String stepId) {
/*  939 */     this.stepId = stepId;
/*      */   }
/*      */ 
/*      */   public long getHastenCount() {
/*  943 */     return this.hastenCount;
/*      */   }
/*      */ 
/*      */   public void setHastenCount(long hastenCount) {
/*  947 */     this.hastenCount = hastenCount;
/*      */   }
/*      */ 
/*      */   public String getDpDesc() {
/*  951 */     return this.dpDesc;
/*      */   }
/*      */ 
/*      */   public void setDpDesc(String dpDesc) {
/*  955 */     this.dpDesc = dpDesc;
/*      */   }
/*      */ 
/*      */   public String getEntryId() {
/*  959 */     return this.entryId;
/*      */   }
/*      */ 
/*      */   public void setEntryId(String entryId) {
/*  963 */     this.entryId = entryId;
/*      */   }
/*      */ 
/*      */   public String getParentEntryId() {
/*  967 */     return this.parentEntryId;
/*      */   }
/*      */ 
/*      */   public void setParentEntryId(String parentEntryId) {
/*  971 */     this.parentEntryId = parentEntryId;
/*      */   }
/*      */ 
/*      */   public String getTopEntryId() {
/*  975 */     return this.topEntryId;
/*      */   }
/*      */ 
/*      */   public void setTopEntryId(String topEntryId) {
/*  979 */     this.topEntryId = topEntryId;
/*      */   }
/*      */ 
/*      */   public long getBaseCreateTime() {
/*  983 */     return this.baseCreateTime;
/*      */   }
/*      */ 
/*      */   public void setBaseCreateTime(long baseCreateTime) {
/*  987 */     this.baseCreateTime = baseCreateTime;
/*      */   }
/*      */ 
/*      */   public String getActorType() {
/*  991 */     return this.actorType;
/*      */   }
/*      */ 
/*      */   public void setActorType(String actorType) {
/*  995 */     this.actorType = actorType;
/*      */   }
/*      */ 
/*      */   public String getDealType() {
/*  999 */     return this.dealType;
/*      */   }
/*      */ 
/*      */   public void setDealType(String dealType) {
/* 1003 */     this.dealType = dealType;
/*      */   }
/*      */ 
/*      */   public String getForwardStepId() {
/* 1007 */     return this.forwardStepId;
/*      */   }
/*      */ 
/*      */   public void setForwardStepId(String forwardStepId) {
/* 1011 */     this.forwardStepId = forwardStepId;
/*      */   }
/*      */ 
/*      */   public String getRoleId() {
/* 1015 */     return this.roleId;
/*      */   }
/*      */ 
/*      */   public void setRoleId(String roleId) {
/* 1019 */     this.roleId = roleId;
/*      */   }
/*      */ 
/*      */   public String getRoleName() {
/* 1023 */     return this.roleName;
/*      */   }
/*      */ 
/*      */   public void setRoleName(String roleName) {
/* 1027 */     this.roleName = roleName;
/*      */   }
/*      */ 
/*      */   public String getActionName() {
/* 1031 */     return this.actionName;
/*      */   }
/*      */ 
/*      */   public void setActionName(String actionName) {
/* 1035 */     this.actionName = actionName;
/*      */   }
/*      */   @Transient
/*      */   public String getDealTypeStr() {
/* 1040 */     return this.dealTypeStr;
/*      */   }
/*      */ 
/*      */   public void setDealTypeStr(String dealTypeStr) {
/* 1044 */     this.dealTypeStr = dealTypeStr;
/*      */   }
/*      */ 
/*      */   public String getStepNo() {
/* 1048 */     return this.stepNo;
/*      */   }
/*      */ 
/*      */   public void setStepNo(String stepNo) {
/* 1052 */     this.stepNo = stepNo;
/*      */   }
/*      */ 
/*      */   public String getStepGroup() {
/* 1056 */     return this.stepGroup;
/*      */   }
/*      */ 
/*      */   public void setStepGroup(String stepGroup) {
/* 1060 */     this.stepGroup = stepGroup;
/*      */   }
/*      */ 
/*      */   public String getTrack() {
/* 1064 */     return this.track;
/*      */   }
/*      */ 
/*      */   public void setTrack(String track) {
/* 1068 */     this.track = track;
/*      */   }
/*      */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.model.DealProcessModel
 * JD-Core Version:    0.6.0
 */