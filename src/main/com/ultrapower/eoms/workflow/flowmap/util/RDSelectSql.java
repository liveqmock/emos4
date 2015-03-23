package com.ultrapower.eoms.workflow.flowmap.util;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public final class RDSelectSql {
	/**
	 * WF:Config_BaseOwnFieldInfo
	 */
	public final static StringBuffer Config_BaseOwnFieldInfo =  new StringBuffer().append(" select")
																			.append("  C1 as   id,")
																			.append("  C650000001 as   BaseName,") 
																			.append("  C650000002 as   BaseSchama,") 
																			.append("  C650000011 as   defEditStep,") 
																			.append("  C650000010 as   BaseFix_field_RequiredPhase,") 
																			.append("  C650000009 as   freeEditStep,") 
																			.append("  C650000008 as   BaseFree_field_ShowStep,") 
																			.append("  C650000100 as   BaseOwnFieldInfoDesc,") 
																			.append("  C650000004 as   fieldCode,") 
																			.append("  C650000003 as   fieldId,") 
																			.append("  C650000019 as   Base_field_IsRequired,") 
																			.append("  C650000018 as   Base_field_Purpose_FlowControl,") 
																			.append("  C650000017 as   isPrint,") 
																			.append("  C650000005 as   fieldName,") 
																			.append("  C650000006 as   fieldType,") 
																			.append("  C650000007 as   fieldTypeValue,") 
																			.append("  C650000014 as   EntryMode,") 
																			.append("  C650000016 as   LogIsWrite,") 
																			.append("  C650000012 as   PrintOneLine,") 
																			.append("  C650000013 as   PrintOrder,") 
																			.append("  C650000021 as   Base_field_Is_AllPrint,") 
																			.append("  C650000020 as   Base_field_WriteAction,") 
																			//.append("  C650000022 as   Base_belong_form,") 
																			.append("  C650000015 as   fieldIsToLong ");
	/**
	 * 基础工单查询
	 */
	public final static StringBuffer BaseOn_BaseIDSelectBase =  new StringBuffer().append(" select")
																			.append(" Base.C1 as BaseID,") 
																			.append(" Base.C700000001 as BaseSchema,") 
																			.append(" Base.C700000002 as BaseName,") 
																			.append(" Base.C700000003 as BaseSN,") 
																			.append(" Base.C700000004 as BaseCreatorFullName,") 
																			.append(" Base.C700000005 as BaseCreatorLoginName,") 
																			.append(" Base.C700000006 as BaseCreateDate,") 
																			.append(" Base.C700000007 as BaseSendDate,") 
																			.append(" Base.C700000008 as BaseFinishDate,") 
																			.append(" Base.C700000009 as BaseCloseDate,") 
																			.append(" Base.C700000010 as BaseStatus,") 
																			.append(" Base.C700000020 as BaseResult,") 
																			.append(" Base.C700000021 as BaseCloseSatisfy,") 
																			.append(" Base.C700000022 as BaseTplID,") 
																			.append("  Base.C700000023  as  BaseIsArchive,") 
																			.append("  Base.C700000030  as  BaseIsTrueClose,") 
																			.append("  Base.C700000041  as  BaseWorkFlowFlag,") 
																			.append("  Base.C700000042  as  BaseCategoryClassName,") 
																			.append("  Base.C700000043  as  BaseCategoryClassCode,") 
																			.append("  Base.C700000044  as  BaseFlagCreated,") 
																			.append("  Base.C700000045  as  BaseFlagSended,") 
																			.append("  Base.C700000046  as  BaseFlagFinished,") 
																			.append("  Base.C700000047  as  BaseFlagCloseed,") 
																			.append("  Base.C700000048  as  BaseStatusCode,") 
																			.append("  Base.C700000049  as  BaseAcceptOutFlag,") 
																			.append("  Base.C700000050  as  BaseDealOutFlag,") 
																			.append("  Base.C700000054  as  BaseFlagIsMotherCreated,") 
																			.append("  Base.C700000055  as  BaseFlowDrawDesc,") 
																			.append("  Base.C700000056  as  BaseFlagIsCreateChild,") 
																			.append("  Base.C700000057  as  BaseOpenDateTime,") 
																			.append("  Base.C700000100  as  BaseCreatorConnectWay,") 
																			.append("  Base.C700000101  as  BaseCreatorCorp,") 
																			.append("  Base.C700000102  as  BaseCreatorCorpID,") 
																			.append("  Base.C700000103  as  BaseCreatorDep,") 
																			.append("  Base.C700000104  as  BaseCreatorDepID,") 
																			.append("  Base.C700000105  as  BaseCreatorDN,") 
																			.append("  Base.C700000106  as  BaseCreatorDNID ");
	
	public final static StringBuffer BaseSubOn_BaseSubIDSelectBaseSub =  new StringBuffer().append(" select")
																				.append(" BaseSub.C700020001 as BaseID,") 
																				.append(" BaseSub.C700000011 as BaseSummary,") 
																				.append(" BaseSub.C700000014 as BaseItems,") 
																				.append(" BaseSub.C700038041 as Pro_ProcessID");
	
	
	public final static StringBuffer sheetModel =  new StringBuffer().append(" select")
	.append("  C1 as   id,")
	.append("  C650000000 as   modelId,") 
	.append("  C650000001 as   modelName,") 
	.append("  C650000002 as   modelVersion,") 
	.append("  C650000003 as   baseName,") 
	.append("  C650000004 as   baseSchema,") 
	.append("  C650000007 as   fieldId,") 
	.append("  C650000008 as   fieldName,")//英文 
	.append("  C650000011 as   fieldCnName,")//中文 
	.append("  C650000009 as   fieldType,") 
	.append("  C650000010 as   fieldTypeValue,") 
	.append("  C650000012 as   isPrint,") 
	.append("  C650000014 as   printLine,") //0否,1是
	.append("  C650000015 as   fieldValue") ;
	
	public final static StringBuffer sheetAttachModel =  new StringBuffer().append(" select")
	.append("  C650000019 as   attachId"); 
																				
																				
}
