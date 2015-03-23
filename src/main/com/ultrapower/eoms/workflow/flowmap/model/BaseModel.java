package com.ultrapower.eoms.workflow.flowmap.model;

import java.util.Hashtable;
import java.util.Map;

import com.ultrapower.eoms.workflow.flowmap.util.Constants;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class BaseModel
{
	private Map<String,BaseFieldBean> m_BaseFieldHashtable;

	public void setM_BaseFieldHashtable(Map<String,BaseFieldBean> m_BaseFieldHashtable) {
		this.m_BaseFieldHashtable = m_BaseFieldHashtable;
	}

	public Map<String,BaseFieldBean> getM_BaseFieldHashtable() {
		return m_BaseFieldHashtable;
	}

	public BaseFieldBean getOneBaseFieldBean(String p_BaseFieldName)
	{
		return m_BaseFieldHashtable.get(p_BaseFieldName.toUpperCase());
	}
	private String baseID;
	private String baseSchema;
	private String baseName;
	private String baseSN;
	private String baseTplID;
	private String BaseSummary;
	private String BaseItems;
	private String BasePriority;
	private String BaseIsAllowLogGroup;
	private long BaseAcceptOutTime;
	private long BaseDealOutTime;
	private String BaseDescrption;
	private int BaseIsArchive;
	private String BaseAuditingLinkName;
	private int BaseIsTrueClose;
	private int BaseWorkFlowFlag;
	private String BaseCategoryClassName;
	private int BaseCategoryClassCode;
	private long BaseFlagCreated;
	private long BaseFlagSended;
	private long BaseFlagFinished;
	private long BaseFlagCloseed;
	private String BaseStatusCode;
	private long BaseAcceptOutFlag;
	private long BaseDealOutFlag;
	private String BaseDealVerdictName;
	private String BaseDealAssistantProcessName;
	private String BaseFieldLogName;
	private int BaseFlagIsMotherCreated;
	private String BaseFlowDrawDesc;
	private int BaseFlagIsCreateChild;
	private int BaseOpenDateTime;
	private String BaseCreatorConnectWay;
	private String BaseCreatorCorp;
	private String BaseCreatorCorpID;
	private String BaseCreatorDep;
	private String BaseCreatorDepID;
	private String BaseCreatorDN;
	private String BaseCreatorDNID;	

	//	属性设置区域--Begin--
	private String BaseModifyUrl="";
	
	private String BaseID="";

	private String BaseTplID="";

	private String BaseSchema="";

	private String BaseName="";

	private String BaseSN="";

	private String BaseCreatorFullName="";

	private String BaseCreatorLoginName="";

	private long BaseCreateDate=0;

	private long BaseSendDate=0;

	private long BaseFinishDate=0;

	private long BaseCloseDate=0;

	private String BaseStatus="";
 
	private String BaseResult="";

	private String BaseCloseSatisfy="";
	
	private long BaseAcceptDate;
	
	//是否已存入历史记录表
	private int IsArchive=0;
	
	private String BaseAuditinglLinkName="";
	private String BaseAuditingProcessName="";
	private String BaseAuditingProcessLogName="";
	private String BaseDealLinkName="";
	private String BaseDealProcessName="";
	private String BaseDealProcessLogName="";
	
	private String tmp_Begin_PhaseNo;
	private String tmp_Begin_ProcessID;
	private String BaseAttachGUID;
	private String BaseEntryID;
	
	private Hashtable hsOwnFiled=new Hashtable();

	//工单特有字段取值，因为每类工单都有自己特有而其它工单没有的字段，为了方便和同一取值采用了该方法

	public void SetOwnerFiled(String p_FiledName,String p_FiledValue)
	{
		if(p_FiledValue!=null)
			hsOwnFiled.put(p_FiledName,p_FiledValue);
		else
			hsOwnFiled.put(p_FiledName,"");
	}
	
	public String GetOwnerFiledValue(String p_FiledName)
	{
		Object obj;
		if(hsOwnFiled!=null)
		{
			obj=hsOwnFiled.get(p_FiledName);
			if(obj!=null)
				return obj.toString();
			else
			{
				p_FiledName=p_FiledName.toUpperCase();
				obj=hsOwnFiled.get(p_FiledName);
				if(obj!=null)
					return obj.toString();
			}
		}
		return "";
	}
	
	//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getBaseID() {
		return BaseID;
	}

	public void setBaseID(String p_BaseID) {
		BaseID = p_BaseID;
	}

	//	用户选择的固定流程工单模板ID
	public String getBaseTplID() {
		return BaseTplID;
	}

	public void setBaseTplID(String p_BaseTplID) {
		BaseTplID = p_BaseTplID;
	}

	//	指向主工单记录的工单类别的标示（新建模式下默认是工单的Form名）
	public String getBaseSchema() {
		return BaseSchema;
	}

	public void setBaseSchema(String p_BaseSchema) {
		BaseSchema = p_BaseSchema;
	}

	//	工单名称（新建模式下通过工单的Form名到工单类别表中查询）


	public String getBaseName() {
		return BaseName;
	}

	public void setBaseName(String p_BaseName) {
		BaseName = p_BaseName;
	}

	//	工单业务流水号，由（’ID’+’工单类别ID’+’时间’+’天的流水号'）组成（在Submit的Fiter中创建）。


	public String getBaseSN() {
		return BaseSN;
	}

	public void setBaseSN(String p_BaseSN) {
		BaseSN = p_BaseSN;
	}

	//	建单人名（新建模式下打开工单时默认填写）
	public String getBaseCreatorFullName() {
		return BaseCreatorFullName;
	}

	public void setBaseCreatorFullName(String p_BaseCreatorFullName) {
		BaseCreatorFullName = p_BaseCreatorFullName;
	}

	//	建单人登陆名（新建模式下打开工单时默认填写）
	public String getBaseCreatorLoginName() {
		return BaseCreatorLoginName;
	}

	public void setBaseCreatorLoginName(String p_BaseCreatorLoginName) {
		BaseCreatorLoginName = p_BaseCreatorLoginName;
	}

	//	建单时间（新建模式下打开工单时默认填写）
	public long getBaseCreateDate() {
		return BaseCreateDate;
	}

	public void setBaseCreateDate(long p_BaseCreateDate) {
		BaseCreateDate = p_BaseCreateDate;
	}

	//	派单时间
	public long getBaseSendDate() {
		return BaseSendDate;
	}

	public void setBaseSendDate(long p_BaseSendDate) {
		BaseSendDate = p_BaseSendDate;
	}

	//	完成时间
	public long getBaseFinishDate() {
		return BaseFinishDate;
	}

	public void setBaseFinishDate(long p_BaseFinishDate) {
		BaseFinishDate = p_BaseFinishDate;
	}

	//	关闭时间
	public long getBaseCloseDate() {
		return BaseCloseDate;
	}

	public void setBaseCloseDate(long p_BaseCloseDate) {
		BaseCloseDate = p_BaseCloseDate;
	}

	//	状态名（新建模式下打开工单时默认填写为“草稿”）
	public String getBaseStatus() {
		return BaseStatus;
	}

	public void setBaseStatus(String p_BaseStatus) {
		BaseStatus = p_BaseStatus;
	}

	//	主题
	/*public String getBaseSummary() {
		return BaseSummary;
	}

	public void setBaseSummary(String p_BaseSummary) {
		BaseSummary = p_BaseSummary;
	}*/

//	//	当前处理人
//	public String getBaseAssigneeS() {
//		return BaseAssigneeS;
//	}
//
//	public void setBaseAssigneeS(String p_BaseAssigneeS) {
//		BaseAssigneeS = p_BaseAssigneeS;
//	}
//
//	//	当前审批人
//
//
//	public String getBaseAuditingerS() {
//		return BaseAuditingerS;
//	}
//
//	public void setBaseAuditingerS(String p_BaseAuditingerS) {
//		BaseAuditingerS = p_BaseAuditingerS;
//	}

	//	专业，类别
/*

	public String getBaseItems() {
		return BaseItems;
	}

	public void setBaseItems(String p_BaseItems) {
		BaseItems = p_BaseItems;
	}

	//	紧急程度


	public String getBasePriority() {
		return BasePriority;
	}

	public void setBasePriority(String p_BasePriority) {
		BasePriority = p_BasePriority;
	}

	//	是否允许同组关单：、0否、1是，默认为0
	public int getBaseIsAllowLogGroup() {
		return BaseIsAllowLogGroup;
	}

	public void setBaseIsAllowLogGroup(int p_BaseIsAllowLogGroup) {
		BaseIsAllowLogGroup = p_BaseIsAllowLogGroup;
	}

	//	工单受理时限
	public long getBaseAcceptOutTime() {
		return BaseAcceptOutTime;
	}

	public void setBaseAcceptOutTime(long p_BaseAcceptOutTime) {
		BaseAcceptOutTime = p_BaseAcceptOutTime;
	}

	//	工单处理时限
	public long getBaseDealOutTime() {
		return BaseDealOutTime;
	}

	public void setBaseDealOutTime(long p_BaseDealOutTime) {
		BaseDealOutTime = p_BaseDealOutTime;
	}

	//	工单描述
	public String getBaseDescrption() {
		return BaseDescrption;
	}

	public void setBaseDescrption(String p_BaseDescrption) {
		BaseDescrption = p_BaseDescrption;
	}
	*/

	//	工单处理记录
	public String getBaseResult() {
		return BaseResult;
	}

	public void setBaseResult(String p_BaseResult) {
		BaseResult = p_BaseResult;
	}

	//	工单关闭的满意度
	public String getBaseCloseSatisfy() {
		return BaseCloseSatisfy;
	}

	public void setBaseCloseSatisfy(String p_BaseCloseSatisfy) {
		BaseCloseSatisfy = p_BaseCloseSatisfy;
	}	
	//	属性设置区域--End--

	
	public String getBaseModifyUrl() {
		String strModifyUrl = Constants.REMEDY_QUERY_URL;
		strModifyUrl = Constants.REMEDY_QUERY_URL.replaceAll("<REMEDY_FROM>",this.getBaseSchema());
		strModifyUrl=strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.getBaseID());
		setBaseModifyUrl(strModifyUrl);
		return BaseModifyUrl;
	}

	public void setBaseModifyUrl(String baseModifyUrl) {
		BaseModifyUrl = baseModifyUrl;
	}
	/** 
	 * 工单处理超时类型
	 * @return
	 */
	public int getBaseOverTimeType(long longServerDateTime)
	{
		int m_Type=0;
		//取服务器当前时间
		
		if(longServerDateTime<=0)
			return m_Type;
		if(GetOwnerFiledValue("BaseDealOutTime")==null)
			return m_Type;
		if(GetOwnerFiledValue("BaseDealOutTime").trim().equals(""))
			return m_Type;
		
		long longDealOverDate=Long.parseLong(GetOwnerFiledValue("BaseDealOutTime"));
		if(longDealOverDate==0)
			return m_Type;
		//如果未完成

		if(this.getBaseFinishDate()<=0)
		{
			//超时工单
			if(longServerDateTime>longDealOverDate)
			{
				//未完成已超时
				m_Type=Constants.OverTimeTypeNoFinishOvered;
			}
			else if(longServerDateTime<longDealOverDate)
			{
				//未完成未超时
				m_Type=Constants.OverTimeTypeNoFinishNoOver;
			}
			
		}
		else
		{
			//超时工单
			if(getBaseFinishDate()>longDealOverDate)
			{
				//已完成已超时
				m_Type=Constants.OverTimeTypeFinishedOvered;
			}
			else if(getBaseFinishDate()<longDealOverDate)
			{
				//已完成未超时
				m_Type=Constants.OverTimeTypeFinishedNoOver;
			}			
			
		}
		return m_Type;
	}

	//是否已存入历史记录表,如果是则在查询相应的Prcess log等要从相应的历史表中取信息

	public int getIsArchive() {
		return IsArchive;
	}
	
	public void setIsArchive(int isArchive) {
		IsArchive = isArchive;
	}
	//工单审批流程流转From名

	public String getBaseAuditinglLinkName() {
		return BaseAuditinglLinkName;
	}

	public void setBaseAuditinglLinkName(String baseAuditinglLinkName) {
		BaseAuditinglLinkName = baseAuditinglLinkName;
	}
	//工单审批流程From名

	public String getBaseAuditingProcessLogName() {
		return BaseAuditingProcessLogName;
	}

	public void setBaseAuditingProcessLogName(String baseAuditingProcessLogName) {
		BaseAuditingProcessLogName = baseAuditingProcessLogName;
	}

	public String getBaseAuditingProcessName() {
		return BaseAuditingProcessName;
	}

	public void setBaseAuditingProcessName(String baseAuditingProcessName) {
		BaseAuditingProcessName = baseAuditingProcessName;
	}

	public String getBaseDealLinkName() {
		return BaseDealLinkName;
	}

	public void setBaseDealLinkName(String baseDealLinkName) {
		BaseDealLinkName = baseDealLinkName;
	}

	public String getBaseDealProcessLogName() {
		return BaseDealProcessLogName;
	}

	public void setBaseDealProcessLogName(String baseDealProcessLogName) {
		BaseDealProcessLogName = baseDealProcessLogName;
	}

	public String getBaseDealProcessName() {
		return BaseDealProcessName;
	}

	public void setBaseDealProcessName(String baseDealProcessName) {
		BaseDealProcessName = baseDealProcessName;
	}

	public String getBaseSummary() {
		return BaseSummary;
	}

	public void setBaseSummary(String baseSummary) {
		BaseSummary = baseSummary;
	}

	public String getBaseItems() {
		return BaseItems;
	}

	public void setBaseItems(String baseItems) {
		BaseItems = baseItems;
	}

	public String getBasePriority() {
		return BasePriority;
	}

	public void setBasePriority(String basePriority) {
		BasePriority = basePriority;
	}

	public String getBaseIsAllowLogGroup() {
		return BaseIsAllowLogGroup;
	}

	public void setBaseIsAllowLogGroup(String baseIsAllowLogGroup) {
		BaseIsAllowLogGroup = baseIsAllowLogGroup;
	}

	public long getBaseAcceptOutTime() {
		return BaseAcceptOutTime;
	}

	public void setBaseAcceptOutTime(long baseAcceptOutTime) {
		BaseAcceptOutTime = baseAcceptOutTime;
	}

	public long getBaseDealOutTime() {
		return BaseDealOutTime;
	}

	public void setBaseDealOutTime(long baseDealOutTime) {
		BaseDealOutTime = baseDealOutTime;
	}

	public String getBaseDescrption() {
		return BaseDescrption;
	}

	public void setBaseDescrption(String baseDescrption) {
		BaseDescrption = baseDescrption;
	}

	public int getBaseIsArchive() {
		return BaseIsArchive;
	}

	public void setBaseIsArchive(int baseIsArchive) {
		BaseIsArchive = baseIsArchive;
	}

	public String getBaseAuditingLinkName() {
		return BaseAuditingLinkName;
	}

	public void setBaseAuditingLinkName(String baseAuditingLinkName) {
		BaseAuditingLinkName = baseAuditingLinkName;
	}

	public int getBaseIsTrueClose() {
		return BaseIsTrueClose;
	}

	public void setBaseIsTrueClose(int baseIsTrueClose) {
		BaseIsTrueClose = baseIsTrueClose;
	}

	public int getBaseWorkFlowFlag() {
		return BaseWorkFlowFlag;
	}

	public void setBaseWorkFlowFlag(int baseWorkFlowFlag) {
		BaseWorkFlowFlag = baseWorkFlowFlag;
	}

	public String getBaseCategoryClassName() {
		return BaseCategoryClassName;
	}

	public void setBaseCategoryClassName(String baseCategoryClassName) {
		BaseCategoryClassName = baseCategoryClassName;
	}

	public int getBaseCategoryClassCode() {
		return BaseCategoryClassCode;
	}

	public void setBaseCategoryClassCode(int baseCategoryClassCode) {
		BaseCategoryClassCode = baseCategoryClassCode;
	}

	public long getBaseFlagCreated() {
		return BaseFlagCreated;
	}

	public void setBaseFlagCreated(long baseFlagCreated) {
		BaseFlagCreated = baseFlagCreated;
	}

	public long getBaseFlagSended() {
		return BaseFlagSended;
	}

	public void setBaseFlagSended(long baseFlagSended) {
		BaseFlagSended = baseFlagSended;
	}

	public long getBaseFlagFinished() {
		return BaseFlagFinished;
	}

	public void setBaseFlagFinished(long baseFlagFinished) {
		BaseFlagFinished = baseFlagFinished;
	}

	public long getBaseFlagCloseed() {
		return BaseFlagCloseed;
	}

	public void setBaseFlagCloseed(long baseFlagCloseed) {
		BaseFlagCloseed = baseFlagCloseed;
	}

	public String getBaseStatusCode() {
		return BaseStatusCode;
	}

	public void setBaseStatusCode(String baseStatusCode) {
		BaseStatusCode = baseStatusCode;
	}

	public long getBaseAcceptOutFlag() {
		return BaseAcceptOutFlag;
	}

	public void setBaseAcceptOutFlag(long baseAcceptOutFlag) {
		BaseAcceptOutFlag = baseAcceptOutFlag;
	}

	public long getBaseDealOutFlag() {
		return BaseDealOutFlag;
	}

	public void setBaseDealOutFlag(long baseDealOutFlag) {
		BaseDealOutFlag = baseDealOutFlag;
	}

	public String getBaseDealVerdictName() {
		return BaseDealVerdictName;
	}

	public void setBaseDealVerdictName(String baseDealVerdictName) {
		BaseDealVerdictName = baseDealVerdictName;
	}

	public String getBaseDealAssistantProcessName() {
		return BaseDealAssistantProcessName;
	}

	public void setBaseDealAssistantProcessName(String baseDealAssistantProcessName) {
		BaseDealAssistantProcessName = baseDealAssistantProcessName;
	}

	public String getBaseFieldLogName() {
		return BaseFieldLogName;
	}

	public void setBaseFieldLogName(String baseFieldLogName) {
		BaseFieldLogName = baseFieldLogName;
	}

	public int getBaseFlagIsMotherCreated() {
		return BaseFlagIsMotherCreated;
	}

	public void setBaseFlagIsMotherCreated(int baseFlagIsMotherCreated) {
		BaseFlagIsMotherCreated = baseFlagIsMotherCreated;
	}

	public String getBaseFlowDrawDesc() {
		return BaseFlowDrawDesc;
	}

	public void setBaseFlowDrawDesc(String baseFlowDrawDesc) {
		BaseFlowDrawDesc = baseFlowDrawDesc;
	}

	public int getBaseFlagIsCreateChild() {
		return BaseFlagIsCreateChild;
	}

	public void setBaseFlagIsCreateChild(int baseFlagIsCreateChild) {
		BaseFlagIsCreateChild = baseFlagIsCreateChild;
	}

	public int getBaseOpenDateTime() {
		return BaseOpenDateTime;
	}

	public void setBaseOpenDateTime(int baseOpenDateTime) {
		BaseOpenDateTime = baseOpenDateTime;
	}

	public String getBaseCreatorConnectWay() {
		return BaseCreatorConnectWay;
	}

	public void setBaseCreatorConnectWay(String baseCreatorConnectWay) {
		BaseCreatorConnectWay = baseCreatorConnectWay;
	}

	public String getBaseCreatorCorp() {
		return BaseCreatorCorp;
	}

	public void setBaseCreatorCorp(String baseCreatorCorp) {
		BaseCreatorCorp = baseCreatorCorp;
	}

	public String getBaseCreatorCorpID() {
		return BaseCreatorCorpID;
	}

	public void setBaseCreatorCorpID(String baseCreatorCorpID) {
		BaseCreatorCorpID = baseCreatorCorpID;
	}

	public String getBaseCreatorDep() {
		return BaseCreatorDep;
	}

	public void setBaseCreatorDep(String baseCreatorDep) {
		BaseCreatorDep = baseCreatorDep;
	}

	public String getBaseCreatorDepID() {
		return BaseCreatorDepID;
	}

	public void setBaseCreatorDepID(String baseCreatorDepID) {
		BaseCreatorDepID = baseCreatorDepID;
	}

	public String getBaseCreatorDN() {
		return BaseCreatorDN;
	}

	public void setBaseCreatorDN(String baseCreatorDN) {
		BaseCreatorDN = baseCreatorDN;
	}

	public String getBaseCreatorDNID() {
		return BaseCreatorDNID;
	}

	public void setBaseCreatorDNID(String baseCreatorDNID) {
		BaseCreatorDNID = baseCreatorDNID;
	}

	public Hashtable getHsOwnFiled() {
		return hsOwnFiled;
	}

	public void setHsOwnFiled(Hashtable hsOwnFiled) {
		this.hsOwnFiled = hsOwnFiled;
	}

	public long getBaseAcceptDate() {
		return BaseAcceptDate;
	}

	public void setBaseAcceptDate(long baseAcceptDate) {
		BaseAcceptDate = baseAcceptDate;
	}

	public String getTmp_Begin_PhaseNo() {
		return tmp_Begin_PhaseNo;
	}

	public void setTmp_Begin_PhaseNo(String tmp_Begin_PhaseNo) {
		this.tmp_Begin_PhaseNo = tmp_Begin_PhaseNo;
	}

	public String getTmp_Begin_ProcessID() {
		return tmp_Begin_ProcessID;
	}

	public void setTmp_Begin_ProcessID(String tmp_Begin_ProcessID) {
		this.tmp_Begin_ProcessID = tmp_Begin_ProcessID;
	}

	public String getBaseAttachGUID() {
		return BaseAttachGUID;
	}

	public void setBaseAttachGUID(String baseAttachGUID) {
		BaseAttachGUID = baseAttachGUID;
	}

	public String getBaseEntryID() {
		return BaseEntryID;
	}

	public void setBaseEntryID(String baseEntryID) {
		BaseEntryID = baseEntryID;
	}

}
