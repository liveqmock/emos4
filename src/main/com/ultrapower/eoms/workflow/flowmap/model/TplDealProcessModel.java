package com.ultrapower.eoms.workflow.flowmap.model;

import java.util.*;
/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class TplDealProcessModel implements Cloneable
{
	private String 	ProcessID	 = "";
	private String 	ProcessGoLine	 = "";
	private String 	ProcessBaseID	 = "";
	private String 	BaseStateCode	 = "";
	private String 	ProcessBaseSchema	 = "";
	private String 	BaseStateName	 = "";
	private String 	PhaseNo	 = "";
	private String 	RoleOnlyID	 = "";
	private String 	RoleName	 = "";
	private String 	ProcessStatus	 = "";
	private String 	ProcessType	 = "";
	private String 	Desc	 = "";
	private String 	ActionName	 = "";
	private String 	ActionPageName	 = "";
	private String 	ActionPageID	 = "";
	private int	FlagActive	;
	private int	FlagPredefined	;
	private int	FlagDuplicated	;
	private int	PosX	;
	private int	PosY	;
	private long	AssignOverTimeDate_tmp	;
	private long	AcceptOverTimeDate_tmp	;
	private long	DealOverTimeDate_tmp	;
	private int	Flag35IsCanCreateBase	;
	private int	Flag36IsCreateBase	;
	private int	FlagType	;
	private int	Flag00IsAvail	;
	private int	Flag01Assign	;
	private int	Flag02Copy	;
	private int	Flag03Assist	;
	private int	Flag04Transfer	;
	private int	Flag05TurnDown	;
	private int	Flag06TurnUp	;
	private int	Flag07Recall	;
	private int	Flag08Cancel	;
	private int	Flag09Close	;
	private int	Flag15ToAuditing	;
	private int	Flag16ToAssistAuditing	;
	private int	Flag20SideBySide	;
	private int	Flag22IsSelect	;
	private int	Flag30AuditingResult	;
	private int	Flag31IsTransfer	;
	private int	Flag32IsToTransfer	;
	private int	Flag33IsEndPhase	;
	private int	Flag34IsEndDuplicated	;
	private int	Flag37IsNeedStartInsideFlow	;
	private String	StartInsideFlowID	;
	private String	StartInsideFlowName	;
	private long stDate;
	private long bgDate;
	private long edDate;
	private int stProcessAction;
	private int edProcessAction;
	private String group = "";
	private String dealer = "";
	private String assignee = "";
	private String customActions = "";

	private String PhaseNoTakeMeActive = "";
	
	private String logXml = "";

	private List logList = new ArrayList();
	private List duplicatedList = new ArrayList();
	
	private long AcceptOverTimeDate;
	private long AcceptOverTimeDate_Relative;
	private String AssgineeID;
	private long AssignOverTimeDate;
	private long AssignOverTimeDate_Relative;
	private String AssigneeCorp;
	private String AssigneeCorpID;
	private String AssigneeDN;
	private String AssigneeDNID;
	private String AssigneeDep;
	private String AssigneeDepID;
	private long BaseOpenDateTime;
	private String CloseBaseSamenessGroup;
	private String CloseBaseSamenessGroupID;
	private String Commissioner;
	private String CommissionerID;
	private String CreateByUserID;
	private long DealOverTimeDate;
	private long DealOverTimeDate_Relative;
	private String DealerCorp;
	private String DealerCorpID;
	private String DealerDN;
	private String DealerDNID;
	private String DealerDep;
	private String DealerDepID;
	private String DealerID;
	private int FlagAssignGroupOrUser;
	private long FlagBegin;
	private long FlagEnd;
	private int FlagGroupSnatch;
	private long FlagStart;
	private String GroupID;
	private int IsQualityCheckUp;
	private String PrevPhaseNo;
	private String TransferPhaseNo;
	
	private String  StartInsideFlows;
	
	public int getFlag37IsNeedStartInsideFlow()
	{
		return Flag37IsNeedStartInsideFlow;
	}
	public void setFlag37IsNeedStartInsideFlow(int flag37IsNeedStartInsideFlow)
	{
		Flag37IsNeedStartInsideFlow = flag37IsNeedStartInsideFlow;
	}
	public String getStartInsideFlowID()
	{
		return StartInsideFlowID;
	}
	public void setStartInsideFlowID(String startInsideFlowID)
	{
		StartInsideFlowID = startInsideFlowID;
	}
	public String getStartInsideFlowName()
	{
		return StartInsideFlowName;
	}
	public void setStartInsideFlowName(String startInsideFlowName)
	{
		StartInsideFlowName = startInsideFlowName;
	}
	public String getStartInsideFlows()
	{
		return StartInsideFlows;
	}
	public void setStartInsideFlows(String startInsideFlows)
	{
		StartInsideFlows = startInsideFlows;
	}
	public long getAcceptOverTimeDate_Relative() {
		return AcceptOverTimeDate_Relative;
	}
	public void setAcceptOverTimeDate_Relative(int acceptOverTimeDate_Relative) {
		AcceptOverTimeDate_Relative = acceptOverTimeDate_Relative;
	}
	public String getAssgineeID() {
		return AssgineeID;
	}
	public void setAssgineeID(String assgineeID) {
		AssgineeID = assgineeID;
	}
	public long getAssignOverTimeDate() {
		return AssignOverTimeDate;
	}
	public void setAssignOverTimeDate(int assignOverTimeDate) {
		AssignOverTimeDate = assignOverTimeDate;
	}
	public long getAssignOverTimeDate_Relative() {
		return AssignOverTimeDate_Relative;
	}
	public void setAssignOverTimeDate_Relative(int assignOverTimeDate_Relative) {
		AssignOverTimeDate_Relative = assignOverTimeDate_Relative;
	}
	public String getAssigneeCorp() {
		return AssigneeCorp;
	}
	public void setAssigneeCorp(String assigneeCorp) {
		AssigneeCorp = assigneeCorp;
	}
	public String getAssigneeCorpID() {
		return AssigneeCorpID;
	}
	public void setAssigneeCorpID(String assigneeCorpID) {
		AssigneeCorpID = assigneeCorpID;
	}
	public String getAssigneeDN() {
		return AssigneeDN;
	}
	public void setAssigneeDN(String assigneeDN) {
		AssigneeDN = assigneeDN;
	}
	public String getAssigneeDNID() {
		return AssigneeDNID;
	}
	public void setAssigneeDNID(String assigneeDNID) {
		AssigneeDNID = assigneeDNID;
	}
	public String getAssigneeDep() {
		return AssigneeDep;
	}
	public void setAssigneeDep(String assigneeDep) {
		AssigneeDep = assigneeDep;
	}
	public String getAssigneeDepID() {
		return AssigneeDepID;
	}
	public void setAssigneeDepID(String assigneeDepID) {
		AssigneeDepID = assigneeDepID;
	}
	public long getBaseOpenDateTime() {
		return BaseOpenDateTime;
	}
	public void setBaseOpenDateTime(int baseOpenDateTime) {
		BaseOpenDateTime = baseOpenDateTime;
	}
	public String getCloseBaseSamenessGroup() {
		return CloseBaseSamenessGroup;
	}
	public void setCloseBaseSamenessGroup(String closeBaseSamenessGroup) {
		CloseBaseSamenessGroup = closeBaseSamenessGroup;
	}
	public String getCloseBaseSamenessGroupID() {
		return CloseBaseSamenessGroupID;
	}
	public void setCloseBaseSamenessGroupID(String closeBaseSamenessGroupID) {
		CloseBaseSamenessGroupID = closeBaseSamenessGroupID;
	}
	public String getCommissioner() {
		return Commissioner;
	}
	public void setCommissioner(String commissioner) {
		Commissioner = commissioner;
	}
	public String getCommissionerID() {
		return CommissionerID;
	}
	public void setCommissionerID(String commissionerID) {
		CommissionerID = commissionerID;
	}
	public String getCreateByUserID() {
		return CreateByUserID;
	}
	public void setCreateByUserID(String createByUserID) {
		CreateByUserID = createByUserID;
	}
	public long getDealOverTimeDate() {
		return DealOverTimeDate;
	}
	public void setDealOverTimeDate(int dealOverTimeDate) {
		DealOverTimeDate = dealOverTimeDate;
	}
	public long getDealOverTimeDate_Relative() {
		return DealOverTimeDate_Relative;
	}
	public void setDealOverTimeDate_Relative(int dealOverTimeDate_Relative) {
		DealOverTimeDate_Relative = dealOverTimeDate_Relative;
	}
	public String getDealerCorp() {
		return DealerCorp;
	}
	public void setDealerCorp(String dealerCorp) {
		DealerCorp = dealerCorp;
	}
	public String getDealerCorpID() {
		return DealerCorpID;
	}
	public void setDealerCorpID(String dealerCorpID) {
		DealerCorpID = dealerCorpID;
	}
	public String getDealerDN() {
		return DealerDN;
	}
	public void setDealerDN(String dealerDN) {
		DealerDN = dealerDN;
	}
	public String getDealerDNID() {
		return DealerDNID;
	}
	public void setDealerDNID(String dealerDNID) {
		DealerDNID = dealerDNID;
	}
	public String getDealerDep() {
		return DealerDep;
	}
	public void setDealerDep(String dealerDep) {
		DealerDep = dealerDep;
	}
	public String getDealerDepID() {
		return DealerDepID;
	}
	public void setDealerDepID(String dealerDepID) {
		DealerDepID = dealerDepID;
	}
	public String getDealerID() {
		return DealerID;
	}
	public void setDealerID(String dealerID) {
		DealerID = dealerID;
	}
	public int getFlagAssignGroupOrUser() {
		return FlagAssignGroupOrUser;
	}
	public void setFlagAssignGroupOrUser(int flagAssignGroupOrUser) {
		FlagAssignGroupOrUser = flagAssignGroupOrUser;
	}
	public long getFlagBegin() {
		return FlagBegin;
	}
	public void setFlagBegin(int flagBegin) {
		FlagBegin = flagBegin;
	}
	public long getFlagEnd() {
		return FlagEnd;
	}
	public void setFlagEnd(int flagEnd) {
		FlagEnd = flagEnd;
	}
	public int getFlagGroupSnatch() {
		return FlagGroupSnatch;
	}
	public void setFlagGroupSnatch(int flagGroupSnatch) {
		FlagGroupSnatch = flagGroupSnatch;
	}
	public long getFlagStart() {
		return FlagStart;
	}
	public void setFlagStart(int flagStart) {
		FlagStart = flagStart;
	}
	public String getGroupID() {
		return GroupID;
	}
	public void setGroupID(String groupID) {
		GroupID = groupID;
	}
	public int getIsQualityCheckUp() {
		return IsQualityCheckUp;
	}
	public void setIsQualityCheckUp(int isQualityCheckUp) {
		IsQualityCheckUp = isQualityCheckUp;
	}
	public String getPrevPhaseNo() {
		return PrevPhaseNo;
	}
	public void setPrevPhaseNo(String prevPhaseNo) {
		PrevPhaseNo = prevPhaseNo;
	}
	public String getTransferPhaseNo() {
		return TransferPhaseNo;
	}
	public void setTransferPhaseNo(String transferPhaseNo) {
		TransferPhaseNo = transferPhaseNo;
	}
	public String getProcessID() {
		return ProcessID;
	}
	public void setProcessID(String processID) {
		ProcessID = processID;
	}
	public String getProcessGoLine() {
		return ProcessGoLine;
	}
	public void setProcessGoLine(String processGoLine) {
		ProcessGoLine = processGoLine;
	}
	public String getProcessBaseID() {
		return ProcessBaseID;
	}
	public void setProcessBaseID(String processBaseID) {
		ProcessBaseID = processBaseID;
	}
	public String getBaseStateCode() {
		return BaseStateCode;
	}
	public void setBaseStateCode(String baseStateCode) {
		BaseStateCode = baseStateCode;
	}
	public String getProcessBaseSchema() {
		return ProcessBaseSchema;
	}
	public void setProcessBaseSchema(String processBaseSchema) {
		ProcessBaseSchema = processBaseSchema;
	}
	public String getBaseStateName() {
		return BaseStateName;
	}
	public void setBaseStateName(String baseStateName) {
		BaseStateName = baseStateName;
	}
	public String getPhaseNo() {
		return PhaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		PhaseNo = phaseNo;
	}
	public String getRoleOnlyID() {
		return RoleOnlyID;
	}
	public void setRoleOnlyID(String roleOnlyID) {
		RoleOnlyID = roleOnlyID;
	}
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public String getProcessStatus() {
		return ProcessStatus;
	}
	public void setProcessStatus(String processStatus) {
		ProcessStatus = processStatus;
	}
	public String getProcessType() {
		return ProcessType;
	}
	public void setProcessType(String processType) {
		ProcessType = processType;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public String getActionName() {
		return ActionName;
	}
	public void setActionName(String actionName) {
		ActionName = actionName;
	}
	public String getActionPageName() {
		return ActionPageName;
	}
	public void setActionPageName(String actionPageName) {
		ActionPageName = actionPageName;
	}
	public String getActionPageID() {
		return ActionPageID;
	}
	public void setActionPageID(String actionPageID) {
		ActionPageID = actionPageID;
	}
	public int getFlagActive() {
		return FlagActive;
	}
	public void setFlagActive(int flagActive) {
		FlagActive = flagActive;
	}
	public int getFlagPredefined() {
		return FlagPredefined;
	}
	public void setFlagPredefined(int flagPredefined) {
		FlagPredefined = flagPredefined;
	}
	public int getFlagDuplicated() {
		return FlagDuplicated;
	}
	public void setFlagDuplicated(int flagDuplicated) {
		FlagDuplicated = flagDuplicated;
	}
	public int getPosX() {
		return PosX;
	}
	public void setPosX(int posX) {
		PosX = posX;
	}
	public int getPosY() {
		return PosY;
	}
	public void setPosY(int posY) {
		PosY = posY;
	}
	public long getAssignOverTimeDate_tmp() {
		return AssignOverTimeDate_tmp;
	}
	public void setAssignOverTimeDate_tmp(long assignOverTimeDate_tmp) {
		AssignOverTimeDate_tmp = assignOverTimeDate_tmp;
	}
	public long getAcceptOverTimeDate_tmp() {
		return AcceptOverTimeDate_tmp;
	}
	public void setAcceptOverTimeDate_tmp(long acceptOverTimeDate_tmp) {
		AcceptOverTimeDate_tmp = acceptOverTimeDate_tmp;
	}
	public long getDealOverTimeDate_tmp() {
		return DealOverTimeDate_tmp;
	}
	public void setDealOverTimeDate_tmp(long dealOverTimeDate_tmp) {
		DealOverTimeDate_tmp = dealOverTimeDate_tmp;
	}
	public int getFlag35IsCanCreateBase() {
		return Flag35IsCanCreateBase;
	}
	public void setFlag35IsCanCreateBase(int flag35IsCanCreateBase) {
		Flag35IsCanCreateBase = flag35IsCanCreateBase;
	}
	public int getFlag36IsCreateBase() {
		return Flag36IsCreateBase;
	}
	public void setFlag36IsCreateBase(int flag36IsCreateBase) {
		Flag36IsCreateBase = flag36IsCreateBase;
	}
	public int getFlagType() {
		return FlagType;
	}
	public void setFlagType(int flagType) {
		FlagType = flagType;
	}
	public int getFlag00IsAvail() {
		return Flag00IsAvail;
	}
	public void setFlag00IsAvail(int flag00IsAvail) {
		Flag00IsAvail = flag00IsAvail;
	}
	public int getFlag01Assign() {
		return Flag01Assign;
	}
	public void setFlag01Assign(int flag01Assign) {
		Flag01Assign = flag01Assign;
	}
	public int getFlag02Copy() {
		return Flag02Copy;
	}
	public void setFlag02Copy(int flag02Copy) {
		Flag02Copy = flag02Copy;
	}
	public int getFlag03Assist() {
		return Flag03Assist;
	}
	public void setFlag03Assist(int flag03Assist) {
		Flag03Assist = flag03Assist;
	}
	public int getFlag04Transfer() {
		return Flag04Transfer;
	}
	public void setFlag04Transfer(int flag04Transfer) {
		Flag04Transfer = flag04Transfer;
	}
	public int getFlag05TurnDown() {
		return Flag05TurnDown;
	}
	public void setFlag05TurnDown(int flag05TurnDown) {
		Flag05TurnDown = flag05TurnDown;
	}
	public int getFlag06TurnUp() {
		return Flag06TurnUp;
	}
	public void setFlag06TurnUp(int flag06TurnUp) {
		Flag06TurnUp = flag06TurnUp;
	}
	public int getFlag07Recall() {
		return Flag07Recall;
	}
	public void setFlag07Recall(int flag07Recall) {
		Flag07Recall = flag07Recall;
	}
	public int getFlag08Cancel() {
		return Flag08Cancel;
	}
	public void setFlag08Cancel(int flag08Cancel) {
		Flag08Cancel = flag08Cancel;
	}
	public int getFlag09Close() {
		return Flag09Close;
	}
	public void setFlag09Close(int flag09Close) {
		Flag09Close = flag09Close;
	}
	public int getFlag15ToAuditing() {
		return Flag15ToAuditing;
	}
	public void setFlag15ToAuditing(int flag15ToAuditing) {
		Flag15ToAuditing = flag15ToAuditing;
	}
	public int getFlag16ToAssistAuditing() {
		return Flag16ToAssistAuditing;
	}
	public void setFlag16ToAssistAuditing(int flag16ToAssistAuditing) {
		Flag16ToAssistAuditing = flag16ToAssistAuditing;
	}
	public int getFlag20SideBySide() {
		return Flag20SideBySide;
	}
	public void setFlag20SideBySide(int flag20SideBySide) {
		Flag20SideBySide = flag20SideBySide;
	}
	public int getFlag22IsSelect() {
		return Flag22IsSelect;
	}
	public void setFlag22IsSelect(int flag22IsSelect) {
		Flag22IsSelect = flag22IsSelect;
	}
	public int getFlag30AuditingResult() {
		return Flag30AuditingResult;
	}
	public void setFlag30AuditingResult(int flag30AuditingResult) {
		Flag30AuditingResult = flag30AuditingResult;
	}
	public int getFlag31IsTransfer() {
		return Flag31IsTransfer;
	}
	public void setFlag31IsTransfer(int flag31IsTransfer) {
		Flag31IsTransfer = flag31IsTransfer;
	}
	public int getFlag32IsToTransfer() {
		return Flag32IsToTransfer;
	}
	public void setFlag32IsToTransfer(int flag32IsToTransfer) {
		Flag32IsToTransfer = flag32IsToTransfer;
	}
	public int getFlag33IsEndPhase() {
		return Flag33IsEndPhase;
	}
	public void setFlag33IsEndPhase(int flag33IsEndPhase) {
		Flag33IsEndPhase = flag33IsEndPhase;
	}
	public int getFlag34IsEndDuplicated() {
		return Flag34IsEndDuplicated;
	}
	public void setFlag34IsEndDuplicated(int flag34IsEndDuplicated) {
		Flag34IsEndDuplicated = flag34IsEndDuplicated;
	}
	public String getPhaseNoTakeMeActive() {
		return PhaseNoTakeMeActive;
	}
	public void setPhaseNoTakeMeActive(String phaseNoTakeMeActive) {
		PhaseNoTakeMeActive = phaseNoTakeMeActive;
	}
	public String getLogXml() {
		return logXml;
	}
	public void setLogXml(String logXml) {
		this.logXml = logXml;
	}
	public List getLogList() {
		return logList;
	}
	public void setLogList(List logList) {
		this.logList = logList;
	}
	public List getDuplicatedList() {
		return duplicatedList;
	}
	public void setDuplicatedList(List duplicatedList) {
		this.duplicatedList = duplicatedList;
	}
	
	public void addDuplicatedProcessModel(TplDealProcessModel dpModel)
	{
		if(dpModel.getFlag34IsEndDuplicated() == 1)
		{
			ProcessStatus = dpModel.getProcessStatus();
			edDate = dpModel.getEdDate();
			Flag00IsAvail = dpModel.Flag00IsAvail;
		}
		duplicatedList.add(dpModel);
	}
	
	public Object clone()
	{
		TplDealProcessModel o = null;
		try
		{
			o = (TplDealProcessModel)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return o;
	}
	public long getStDate()
	{
		return stDate;
	}
	public void setStDate(long stDate)
	{
		this.stDate = stDate;
	}
	public long getBgDate()
	{
		return bgDate;
	}
	public void setBgDate(long bgDate)
	{
		this.bgDate = bgDate;
	}
	public long getEdDate()
	{
		return edDate;
	}
	public void setEdDate(long edDate)
	{
		this.edDate = edDate;
	}
	public int getStProcessAction()
	{
		return stProcessAction;
	}
	public void setStProcessAction(int stProcessAction)
	{
		this.stProcessAction = stProcessAction;
	}
	public int getEdProcessAction()
	{
		return edProcessAction;
	}
	public String getAssignee()
	{
		return assignee;
	}
	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}
	public void setEdProcessAction(int edProcessAction)
	{
		this.edProcessAction = edProcessAction;
	}
	public String getGroup()
	{
		return group;
	}
	public void setGroup(String group)
	{
		this.group = group;
	}
	public String getDealer()
	{
		return dealer;
	}
	public void setDealer(String dealer)
	{
		this.dealer = dealer;
	}
	public String getCustomActions()
	{
		return customActions;
	}
	public void setCustomActions(String customActions)
	{
		this.customActions = customActions;
	}
	public void setAcceptOverTimeDate(int acceptOverTimeDate) {
		AcceptOverTimeDate = acceptOverTimeDate;
	}
	public long getAcceptOverTimeDate() {
		return AcceptOverTimeDate;
	}
	
}
