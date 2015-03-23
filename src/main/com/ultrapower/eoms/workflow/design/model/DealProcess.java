package com.ultrapower.eoms.workflow.design.model;

import java.util.List;


/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class DealProcess implements java.io.Serializable, Cloneable
{
	@Override
	public DealProcess clone()
	{
		try
		{
			return (DealProcess)super.clone();
		}
		catch (Exception e)
		{
			return null;
		}
	}

	// Fields

	private String processId;
	private String processType;
	private String taskId;
	private String phaseNo;
	private String stepId;
	private String forwardStepId;
	private String prePhaseNo;
	private String processStatus;
	private long flagActive;
	private long flagDuplicated;
	private long flagEndDuplicated;
	private long flagEndPhase;
	private long flagPredefined;
	private String stProcessAction;
	private String edProcessAction;
	private long selfDealLength;
	private long baseStartDate;
	private String baseId;
	private String baseSchema;
	private String flowId;
	private String parentBaseId;
	private String parentFlowId;
	private String baseBaseId;
	private String baseFlowId;
	private String assigneeId;
	private String assignee;
	private String assigneeDepId;
	private String assigneeDep;
	private String assigneeCorpId;
	private String assigneeCorp;
	private String assigneeDnid;
	private String assigneeDn;
	private String assignGroupId;
	private String assignGroup;
	private String flagAssignType;
	private String dealerId;
	private String dealer;
	private String dealerDepId;
	private String dealerDep;
	private String dealerCorpId;
	private String dealerCorp;
	private String dealerDnid;
	private String dealerDn;
	private String assignerId;
	private String assigner;
	private String assignerDepId;
	private String assignerDep;
	private String assignerCorpId;
	private String assignerCorp;
	private String assignerDnid;
	private String assignerDn;
	private String stDate;
	private String bgDate;
	private String edDate;
	private String assignOverTimeDate;
	private String acceptOverTimeDate;
	private String dealOverTimeDate;
	private long flagAssign;
	private long flagAssist;
	private long flagCopy;
	private long flagTransfer;
	private long flagTurnUp;
	private long flagRecall;
	private long flagCancel;
	private long flagClose;
	private long flagTurnDown;
	private long flagToAssistAuditing;
	private long flagToAuditing;
	private long flagStartInsideFlow;
	private long flagAuditingResult;
	private long insideFlowsCount;
	private long finishInsideFlowsCount;
	private String actionPageName;
	private String actionPageId;
	private String customActions;
	private String preAssignString;
	private String dpDesc;//环节说明
	private long baseCreateTime;//建单时间
	private String actorType;
	private String dealType;
	private List<DealProcessLog> dpLogs;
	
	private String roleName; 
	private String defName; 
	
	
	// Constructors

	public String getDpDesc() {
		return dpDesc;
	}

	public void setDpDesc(String dpDesc) {
		this.dpDesc = dpDesc;
	}

	public long getBaseCreateTime() {
		return baseCreateTime;
	}

	public void setBaseCreateTime(long baseCreateTime) {
		this.baseCreateTime = baseCreateTime;
	}

	public String getActorType() {
		return actorType;
	}

	public void setActorType(String actorType) {
		this.actorType = actorType;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	/** default constructor */
	public DealProcess()
	{
		//this.processId = UUIDGenerator.getId();
	}

	/** full constructor */
	public DealProcess(String processType, String taskId, String phaseNo, String prePhaseNo, String processStatus, Long flagActive, Long flagDuplicated, Long flagEndDuplicated, Long flagEndPhase, Long flagPredefined, String stProcessAction, String edProcessAction, Long selfDealLength, Long baseStartDate, String baseId, String baseSchema, String flowId, String parentBaseId, String parentFlowId, String baseBaseId, String baseFlowId, String assigneeId, String assignee, String assigneeDepId, String assigneeDep, String assigneeCorpId, String assigneeCorp, String assigneeDnid, String assigneeDn, String assignGroupId, String assignGroup, String flagAssignType, String dealerId, String dealer, String dealerDepId, String dealerDep, String dealerCorpId, String dealerCorp, String dealerDnid, String dealerDn, String assignerId, String assigner, String assignerDepId, String assignerDep, String assignerCorpId, String assignerCorp, String assignerDnid, String assignerDn, Long stDate, Long bgDate, Long edDate, Long assignOverTimeDate, Long acceptOverTimeDate, Long dealOverTimeDate, Long flagAssign, Long flagAssist, Long flagCopy, Long flagTransfer, Long flagTurnUp, Long flagRecall, Long flagCancel, Long flagClose, Long flagTurnDown, Long flagToAssistAuditing, Long flagToAuditing, Long flagStartInsideFlow, Long flagAuditingResult, Long insideFlowsCount, Long finishInsideFlowsCount, String actionPageName, String actionPageId, String customActions, String preAssignString)
	{
		this.processId = "";
		this.processType = processType;
		this.taskId = taskId;
		this.phaseNo = phaseNo;
		this.prePhaseNo = prePhaseNo;
		this.processStatus = processStatus;
		this.flagActive = flagActive;
		this.flagDuplicated = flagDuplicated;
		this.flagEndDuplicated = flagEndDuplicated;
		this.flagEndPhase = flagEndPhase;
		this.flagPredefined = flagPredefined;
		this.stProcessAction = stProcessAction;
		this.edProcessAction = edProcessAction;
		this.selfDealLength = selfDealLength;
		this.baseStartDate = baseStartDate;
		this.baseId = baseId;
		this.baseSchema = baseSchema;
		this.flowId = flowId;
		this.parentBaseId = parentBaseId;
		this.parentFlowId = parentFlowId;
		this.baseBaseId = baseBaseId;
		this.baseFlowId = baseFlowId;
		this.assigneeId = assigneeId;
		this.assignee = assignee;
		this.assigneeDepId = assigneeDepId;
		this.assigneeDep = assigneeDep;
		this.assigneeCorpId = assigneeCorpId;
		this.assigneeCorp = assigneeCorp;
		this.assigneeDnid = assigneeDnid;
		this.assigneeDn = assigneeDn;
		this.assignGroupId = assignGroupId;
		this.assignGroup = assignGroup;
		this.flagAssignType = flagAssignType;
		this.dealerId = dealerId;
		this.dealer = dealer;
		this.dealerDepId = dealerDepId;
		this.dealerDep = dealerDep;
		this.dealerCorpId = dealerCorpId;
		this.dealerCorp = dealerCorp;
		this.dealerDnid = dealerDnid;
		this.dealerDn = dealerDn;
		this.assignerId = assignerId;
		this.assigner = assigner;
		this.assignerDepId = assignerDepId;
		this.assignerDep = assignerDep;
		this.assignerCorpId = assignerCorpId;
		this.assignerCorp = assignerCorp;
		this.assignerDnid = assignerDnid;
		this.assignerDn = assignerDn;
		this.flagAssign = flagAssign;
		this.flagAssist = flagAssist;
		this.flagCopy = flagCopy;
		this.flagTransfer = flagTransfer;
		this.flagTurnUp = flagTurnUp;
		this.flagRecall = flagRecall;
		this.flagCancel = flagCancel;
		this.flagClose = flagClose;
		this.flagTurnDown = flagTurnDown;
		this.flagToAssistAuditing = flagToAssistAuditing;
		this.flagToAuditing = flagToAuditing;
		this.flagStartInsideFlow = flagStartInsideFlow;
		this.flagAuditingResult = flagAuditingResult;
		this.insideFlowsCount = insideFlowsCount;
		this.finishInsideFlowsCount = finishInsideFlowsCount;
		this.actionPageName = actionPageName;
		this.actionPageId = actionPageId;
		this.customActions = customActions;
		this.preAssignString = preAssignString;
	}

	// Property accessors

	public String getProcessId()
	{
		return this.processId;
	}

	public void setProcessId(String processId)
	{
		this.processId = processId;
	}

	public String getTaskId()
	{
		return this.taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getPhaseNo()
	{
		return this.phaseNo;
	}

	public void setPhaseNo(String phaseNo)
	{
		this.phaseNo = phaseNo;
	}

	public String getPrePhaseNo()
	{
		return this.prePhaseNo;
	}

	public void setPrePhaseNo(String prePhaseNo)
	{
		this.prePhaseNo = prePhaseNo;
	}

	public String getProcessStatus()
	{
		return this.processStatus;
	}

	public void setProcessStatus(String processStatus)
	{
		this.processStatus = processStatus;
	}

	public Long getFlagActive()
	{
		return this.flagActive;
	}

	public void setFlagActive(Long flagActive)
	{
		this.flagActive = flagActive;
	}

	public Long getFlagDuplicated()
	{
		return this.flagDuplicated;
	}

	public void setFlagDuplicated(Long flagDuplicated)
	{
		this.flagDuplicated = flagDuplicated;
	}

	public Long getFlagEndDuplicated()
	{
		return this.flagEndDuplicated;
	}

	public void setFlagEndDuplicated(Long flagEndDuplicated)
	{
		this.flagEndDuplicated = flagEndDuplicated;
	}

	public Long getFlagEndPhase()
	{
		return this.flagEndPhase;
	}

	public void setFlagEndPhase(Long flagEndPhase)
	{
		this.flagEndPhase = flagEndPhase;
	}

	public Long getFlagPredefined()
	{
		return this.flagPredefined;
	}

	public void setFlagPredefined(Long flagPredefined)
	{
		this.flagPredefined = flagPredefined;
	}

	public String getStProcessAction()
	{
		return this.stProcessAction;
	}

	public void setStProcessAction(String stProcessAction)
	{
		this.stProcessAction = stProcessAction;
	}

	public String getEdProcessAction()
	{
		return this.edProcessAction;
	}

	public void setEdProcessAction(String edProcessAction)
	{
		this.edProcessAction = edProcessAction;
	}

	public Long getSelfDealLength()
	{
		return this.selfDealLength;
	}

	public void setSelfDealLength(Long selfDealLength)
	{
		this.selfDealLength = selfDealLength;
	}

	public Long getBaseStartDate()
	{
		return baseStartDate;
	}

	public void setBaseStartDate(Long baseStartDate)
	{
		this.baseStartDate = baseStartDate;
	}

	public String getBaseId()
	{
		return this.baseId;
	}

	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	public String getBaseSchema()
	{
		return this.baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getFlowId()
	{
		return this.flowId;
	}

	public void setFlowId(String flowId)
	{
		this.flowId = flowId;
	}

	public String getParentBaseId()
	{
		return this.parentBaseId;
	}

	public void setParentBaseId(String parentBaseId)
	{
		this.parentBaseId = parentBaseId;
	}

	public String getParentFlowId()
	{
		return this.parentFlowId;
	}

	public void setParentFlowId(String parentFlowId)
	{
		this.parentFlowId = parentFlowId;
	}

	public String getBaseBaseId()
	{
		return this.baseBaseId;
	}

	public void setBaseBaseId(String baseBaseId)
	{
		this.baseBaseId = baseBaseId;
	}

	public String getBaseFlowId()
	{
		return this.baseFlowId;
	}

	public void setBaseFlowId(String baseFlowId)
	{
		this.baseFlowId = baseFlowId;
	}

	public String getAssigneeId()
	{
		return this.assigneeId;
	}

	public void setAssigneeId(String assigneeId)
	{
		this.assigneeId = assigneeId;
	}

	public String getAssignee()
	{
		return this.assignee;
	}

	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}

	public String getAssigneeDepId()
	{
		return this.assigneeDepId;
	}

	public void setAssigneeDepId(String assigneeDepId)
	{
		this.assigneeDepId = assigneeDepId;
	}

	public String getAssigneeDep()
	{
		return this.assigneeDep;
	}

	public void setAssigneeDep(String assigneeDep)
	{
		this.assigneeDep = assigneeDep;
	}

	public String getAssigneeCorpId()
	{
		return this.assigneeCorpId;
	}

	public void setAssigneeCorpId(String assigneeCorpId)
	{
		this.assigneeCorpId = assigneeCorpId;
	}

	public String getAssigneeCorp()
	{
		return this.assigneeCorp;
	}

	public void setAssigneeCorp(String assigneeCorp)
	{
		this.assigneeCorp = assigneeCorp;
	}

	public String getAssigneeDnid()
	{
		return this.assigneeDnid;
	}

	public void setAssigneeDnid(String assigneeDnid)
	{
		this.assigneeDnid = assigneeDnid;
	}

	public String getAssigneeDn()
	{
		return this.assigneeDn;
	}

	public void setAssigneeDn(String assigneeDn)
	{
		this.assigneeDn = assigneeDn;
	}

	public String getAssignGroupId()
	{
		return this.assignGroupId;
	}

	public void setAssignGroupId(String assignGroupId)
	{
		this.assignGroupId = assignGroupId;
	}

	public String getAssignGroup()
	{
		return this.assignGroup;
	}

	public void setAssignGroup(String assignGroup)
	{
		this.assignGroup = assignGroup;
	}

	public String getFlagAssignType()
	{
		return this.flagAssignType;
	}

	public void setFlagAssignType(String flagAssignType)
	{
		this.flagAssignType = flagAssignType;
	}

	public String getDealerId()
	{
		return this.dealerId;
	}

	public void setDealerId(String dealerId)
	{
		this.dealerId = dealerId;
	}

	public String getDealer()
	{
		return this.dealer;
	}

	public void setDealer(String dealer)
	{
		this.dealer = dealer;
	}

	public String getDealerDepId()
	{
		return this.dealerDepId;
	}

	public void setDealerDepId(String dealerDepId)
	{
		this.dealerDepId = dealerDepId;
	}

	public String getDealerDep()
	{
		return this.dealerDep;
	}

	public void setDealerDep(String dealerDep)
	{
		this.dealerDep = dealerDep;
	}

	public String getDealerCorpId()
	{
		return this.dealerCorpId;
	}

	public void setDealerCorpId(String dealerCorpId)
	{
		this.dealerCorpId = dealerCorpId;
	}

	public String getDealerCorp()
	{
		return this.dealerCorp;
	}

	public void setDealerCorp(String dealerCorp)
	{
		this.dealerCorp = dealerCorp;
	}

	public String getDealerDnid()
	{
		return this.dealerDnid;
	}

	public void setDealerDnid(String dealerDnid)
	{
		this.dealerDnid = dealerDnid;
	}

	public String getDealerDn()
	{
		return this.dealerDn;
	}

	public void setDealerDn(String dealerDn)
	{
		this.dealerDn = dealerDn;
	}

	public String getAssignerId()
	{
		return this.assignerId;
	}

	public void setAssignerId(String assignerId)
	{
		this.assignerId = assignerId;
	}

	public String getAssigner()
	{
		return this.assigner;
	}

	public void setAssigner(String assigner)
	{
		this.assigner = assigner;
	}

	public String getAssignerDepId()
	{
		return this.assignerDepId;
	}

	public void setAssignerDepId(String assignerDepId)
	{
		this.assignerDepId = assignerDepId;
	}

	public String getAssignerDep()
	{
		return this.assignerDep;
	}

	public void setAssignerDep(String assignerDep)
	{
		this.assignerDep = assignerDep;
	}

	public String getAssignerCorpId()
	{
		return this.assignerCorpId;
	}

	public void setAssignerCorpId(String assignerCorpId)
	{
		this.assignerCorpId = assignerCorpId;
	}

	public String getAssignerCorp()
	{
		return this.assignerCorp;
	}

	public void setAssignerCorp(String assignerCorp)
	{
		this.assignerCorp = assignerCorp;
	}

	public String getAssignerDnid()
	{
		return this.assignerDnid;
	}

	public void setAssignerDnid(String assignerDnid)
	{
		this.assignerDnid = assignerDnid;
	}

	public String getAssignerDn()
	{
		return this.assignerDn;
	}

	public void setAssignerDn(String assignerDn)
	{
		this.assignerDn = assignerDn;
	}

	public Long getFlagAssign()
	{
		return this.flagAssign;
	}

	public void setFlagAssign(Long flagAssign)
	{
		this.flagAssign = flagAssign;
	}

	public Long getFlagAssist()
	{
		return this.flagAssist;
	}

	public void setFlagAssist(Long flagAssist)
	{
		this.flagAssist = flagAssist;
	}

	public Long getFlagCopy()
	{
		return this.flagCopy;
	}

	public void setFlagCopy(Long flagCopy)
	{
		this.flagCopy = flagCopy;
	}

	public Long getFlagTransfer()
	{
		return this.flagTransfer;
	}

	public void setFlagTransfer(Long flagTransfer)
	{
		this.flagTransfer = flagTransfer;
	}

	public Long getFlagTurnUp()
	{
		return this.flagTurnUp;
	}

	public void setFlagTurnUp(Long flagTurnUp)
	{
		this.flagTurnUp = flagTurnUp;
	}

	public Long getFlagRecall()
	{
		return this.flagRecall;
	}

	public void setFlagRecall(Long flagRecall)
	{
		this.flagRecall = flagRecall;
	}

	public Long getFlagCancel()
	{
		return this.flagCancel;
	}

	public void setFlagCancel(Long flagCancel)
	{
		this.flagCancel = flagCancel;
	}

	public Long getFlagClose()
	{
		return this.flagClose;
	}

	public void setFlagClose(Long flagClose)
	{
		this.flagClose = flagClose;
	}

	public Long getFlagTurnDown()
	{
		return this.flagTurnDown;
	}

	public void setFlagTurnDown(Long flagTurnDown)
	{
		this.flagTurnDown = flagTurnDown;
	}

	public Long getFlagToAssistAuditing()
	{
		return this.flagToAssistAuditing;
	}

	public void setFlagToAssistAuditing(Long flagToAssistAuditing)
	{
		this.flagToAssistAuditing = flagToAssistAuditing;
	}

	public Long getFlagToAuditing()
	{
		return this.flagToAuditing;
	}

	public void setFlagToAuditing(Long flagToAuditing)
	{
		this.flagToAuditing = flagToAuditing;
	}

	public Long getFlagStartInsideFlow()
	{
		return this.flagStartInsideFlow;
	}

	public void setFlagStartInsideFlow(Long flagStartInsideFlow)
	{
		this.flagStartInsideFlow = flagStartInsideFlow;
	}

	public Long getFlagAuditingResult()
	{
		return this.flagAuditingResult;
	}

	public void setFlagAuditingResult(Long flagAuditingResult)
	{
		this.flagAuditingResult = flagAuditingResult;
	}

	public Long getInsideFlowsCount()
	{
		return this.insideFlowsCount;
	}

	public void setInsideFlowsCount(Long insideFlowsCount)
	{
		this.insideFlowsCount = insideFlowsCount;
	}

	public Long getFinishInsideFlowsCount()
	{
		return this.finishInsideFlowsCount;
	}

	public void setFinishInsideFlowsCount(Long finishInsideFlowsCount)
	{
		this.finishInsideFlowsCount = finishInsideFlowsCount;
	}

	public String getActionPageName()
	{
		return this.actionPageName;
	}

	public void setActionPageName(String actionPageName)
	{
		this.actionPageName = actionPageName;
	}

	public String getActionPageId()
	{
		return this.actionPageId;
	}

	public void setActionPageId(String actionPageId)
	{
		this.actionPageId = actionPageId;
	}

	public String getCustomActions()
	{
		return this.customActions;
	}

	public void setCustomActions(String customActions)
	{
		this.customActions = customActions;
	}

	public String getPreAssignString()
	{
		return this.preAssignString;
	}

	public void setPreAssignString(String preAssignString)
	{
		this.preAssignString = preAssignString;
	}

	public String getProcessType()
	{
		return processType;
	}

	public void setProcessType(String processType)
	{
		this.processType = processType;
	}

	public String getStDate() {
		return stDate;
	}

	public void setStDate(String stDate) {
		this.stDate = stDate;
	}

	public String getBgDate() {
		return bgDate;
	}

	public void setBgDate(String bgDate) {
		this.bgDate = bgDate;
	}

	public String getEdDate() {
		return edDate;
	}

	public void setEdDate(String edDate) {
		this.edDate = edDate;
	}

	public String getAssignOverTimeDate() {
		return assignOverTimeDate;
	}

	public void setAssignOverTimeDate(String assignOverTimeDate) {
		this.assignOverTimeDate = assignOverTimeDate;
	}

	public String getAcceptOverTimeDate() {
		return acceptOverTimeDate;
	}

	public void setAcceptOverTimeDate(String acceptOverTimeDate) {
		this.acceptOverTimeDate = acceptOverTimeDate;
	}

	public String getDealOverTimeDate() {
		return dealOverTimeDate;
	}

	public void setDealOverTimeDate(String dealOverTimeDate) {
		this.dealOverTimeDate = dealOverTimeDate;
	}

	public List<DealProcessLog> getDpLogs() {
		return dpLogs;
	}

	public void setDpLogs(List<DealProcessLog> dpLogs) {
		this.dpLogs = dpLogs;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getForwardStepId() {
		return forwardStepId;
	}

	public void setForwardStepId(String forwardStepId) {
		this.forwardStepId = forwardStepId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDefName() {
		return defName;
	}

	public void setDefName(String defName) {
		this.defName = defName;
	}
	
}