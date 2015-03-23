package com.ultrapower.eoms.workflow.flowmap.util;

import java.util.HashMap;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public final class Constants {

	/** 	描述：系统路径
 	 */	
	public static String  sysPath				= "";
	/** 	描述：是否集成基础组树的标示

	 */
	public static String  TREEFLAG				= "";

	/** 	描述：工单表级分离的运行方式 0：Servlet ；1：单独了应用进程
	 */
	public static String  BaseArchiveRunMode	= "";

	/** 	描述：工单表级分离的活动库中保存的数据量：单位天	
	 */
	public static String  BaseArchiveActivityDataCapacity	= "";
	
	/** 	描述：工单表级分离的运行线程的间隔

	 */
	public static String  BaseArchiveRuniInterval	= "";

	/**
	 *  	描述：工单表级分离的运行周期 0：天 ；1：周 ；2：月
	 */

	public static String  BaseArchiveCycMode	= "";

	/**
	 *  	工单表级分离的运行周期为月时的 当月的哪一天 31>n>0
	 */
	public static String  BaseArchiveCycMonthDay	= "";

	/**
	 *  	工单表级分离的运行周期为周时的 当周的哪一天 8>n>0
	 */
	public static String  BaseArchiveCycWeekDay	= "";

	/**
	 *  	工单表级分离的运行周期为天时的 当周的哪一天 n=1 
	 */
	public static String  BaseArchiveCycDayDay	= "";

	/**
	 *  	工单表级分离的运行当天的时间 24>n>-1
	 */
	public static String  BaseArchiveRunTimeHour	= "";
	 
	
	/**
	 *  	描述：统一的网页标题

	 */
	public static String  HTMLTITLE				= "";
	
	/**
	 *  	描述：Remedy的Server名

	 */	
	public static String  REMEDY_SERVERNAME		= "";
	
	/**
	 *  	描述：Remedy的Port
	 */	
	public static int  REMEDY_SERVERPORT		= 0;	
	
	/**
	 *  	描述：Remedy的Demo名

	 */	
	public static String  REMEDY_DEMONAME		= "";
	
	/**
	 *  	描述：Remedy的Demo的密码

	 */	
	public static String  REMEDY_DEMOPASSWORD	= "";
	
	/**
	 *  	描述：Session变量的名字

	 */	
	public static String  SESSIONNAME			= "";
	
	/**
	 *  	描述：Remedy查询URL（通过ProcessID 其他用不了）
	 */	
	public static String  REMEDY_PROCESS_QUERY_URL		= "";	
	
	/**
	 *  	描述：Remedy新建URL（通过BaseTplID）
	 */	
	public static String  REMEDY_BASETPL_CREATE_URL		= "";	
	
	/**
	 *  	描述：Remedy查询URL
	 */	
	public static String  REMEDY_QUERY_URL		= "";
	
	/**
	 *  	描述：Remedy新建URL
	 */	
	public static String  REMEDY_CREATE_URL		= "";
	
	/**
	 *  	描述：流程产品登陆的URL
	 */	
	public static String  ULTRAPROCESS_LOGIN_URL		= "";
	
	/**
	 *  	描述：流程产品退出的URL
	 */	
	public static String  ULTRAPROCESS_LOGOUT_URL		= "";
	
	/**
	 *    描述： remedy的ip地址和端口配置 如：192.168.20.23:8090
	 */
	
	public static String RemedyServerAddress="";// 只给报表用，因为报表和系统会分开工程部署，打开工单会用到
	
	/**
	 * 		描述：所有工单信息

	 */
	public static String  TblBaseInfor					="";
	
	/**
	 * 		描述：所有工单处理环节的修改的字段日志信息

	 */
	public static String  TblBaseFieldModifyLog			="";
	
	/**
	 * 		描述：所有工单处理环节的修改的字段日志信息备份

	 */
	public static String  TblBaseFieldModifyLog_H		="";
	
	/**		
	 * 		描述：处理环节Process表

	*/
	public static String  TblDealProcess			= "";
	public static String  TblDealLink				= "";
	public static String  TblDealProcessLog			= "";

	/**		
	 * 		描述：处理环节Process历史(归档)表

	*/
	public static String  TblDealProcess_H			= "";
	public static String  TblDealLink_H				= "";
	public static String  TblDealProcessLog_H		= "";
	
	/**
	 * 		描述：审批环节Process表

	*/
	public static String  TblAuditingProcess	= "";
	public static String  TblAuditingLink		= "";
	public static String  TblAuditingProcessLog	= "";

	/**
	 * 		描述：审批环节Process历史(归档)表
	*/
	public static String  TblAuditingProcess_H	= "";
	public static String  TblAuditingLink_H		= "";
	public static String  TblAuditingProcessLog_H	= "";
	
	/**
	 * 		描述：流程配置信息表
	 */
	public static String  TblBaseCategory		= "";
	public static String  TblBaseCategoryClass		= "";
	public static String  TblBaseState			= "";
	public static String  TblProcessState		= "";
	public static String  TblBaseOwnFieldInfo	= "";	
	
	public static String TblDimension = "";
	
	/**
	 * 		描述：附件表
	 */
	public static String  TblBaseAttachment		="";
	
	/**
	 * 		描述：工单信息表
	 */
	public static String  TblAppBaseInfor		="";
	/**
	 * 		描述：工单判断信息表
	 */
	public static String  TblAppDealVerdict		="";
	/**
	 * 		描述：工单辅助环节信息表
	 */
	public static String  TblAppDealAssistantProcess		="";
	
	/**
	 * 		描述：公告表
	 */
	public static String  TblNote	        	= "";
	public static String  TblNoteClass			= "";
	public static String  TblNoteAuditing		= "";
	public static String  TblNoteRead           = "";
	
	/**
	 * 		描述：系统表
	 */
	public static String TblUser				= "";
	public static String TblGroup				= "";
	public static String TblSysGroup			= "";
	public static String TblSysUser				= "";
	
	/**
	 * 		工单表

	 */	
	public static String TblBaseIncident		="";//故障工单
	public static String TblBaseImportIncident	="";//重大故障工单
	
	/**
	 * 		描述：配置表
	 */
	public static String TblUserCloseBaseGroup	="";//同组关单配置表

	public static String TblUserCommission		="";//代办设置表

	
	public static String TblGroupIsSnatch		="";//组是否抢单信息s

	
	public static String TblTplDealProcess		="";//	
	public static String TblTplDealLink		="";//	
	
	/**
	 * 		描述：待阅知(抄送)
	*/
	public static final int ProcessWaitConfirm			= 10;

	/**
	 * 		描述：待处理(主办、协办)
	*/
	public static final int ProcessWaitDeal				= 20;

	/**
	 * 		描述：处理中(主办、协办)
	*/
	public static final int ProcessDealing				= 30;
	
	/**
	 * 		描述：待审批
	*/
	public static final int ProcessWaitAuditing			= 40;
	
	/**
	 * 		描述：处理=待处理(未受理主办、协办)+处理中(已受理主办、协办)
	 */
	public static final int ProcessDeal					= 50;
	
	/**		描述：我建立的工单
	*/
	public static final int ProcessMyCreate				= 60;
	
	/**
	 * 		描述：我处理过的工单(我处理过，不管是否处理完成。包括自己建单(处理)的工单)
	 */
	public static final int ProcessMyDeallAll				=70;
	
	/**
	 * 		描述：我组处理过的工单(我组处理过，不管是否处理完成。包括自己建单(处理)的工单)
	 */
	public static final int ProcessMyGroupDeallAll				=71;
	
	/**
	 * 		描述：我所有待处理的所有工单(未受理)
	 */
	public static final int ProcessMyWaitDeal				=80;
	
	/**
	 * 		描述：我所有处理中的所有工单(已受理)
	 */	
	public static final int ProcessMyDealing				=90;
	
	/**
	 * 		描述：我所有的待处理的工单(ProcessMyWaitDeal+ProcessMyDealing的集合)
	 */
	public static final int ProcessMyWaitDeallAll				=100;
	
	/**
	 * 		描述：我处理过的工单(我处理过，不管是否处理完成。包括不包括自己建单(处理)的工单)
	 */	
	public static final int ProcessMyDeallAllNotIncludeNew	=110;
	
	/**
	 * 		描述：我审批过的工单(我审批过，并且已完成的。）
	 */
	public static final int ProcessMyAuditingAndIsFinished			=120;
	
	/**
	 * 		描述：我处理完成的工单(我处理并且已处理完成)
	 */
	public static final int ProcessMyDealAndIsFinished		=130;
	
	/**
	 * 		描述：我(已)派发的工单
	 */
	public static final int ProcessMyAssign				=140;
	
	/**
	 * 		描述：完成已超时
	*/
	public static final int OverTimeTypeNoFinishNoOver	= 10;
	
	/**
	 * 		描述：未完成已超时

	*/
	public static final int OverTimeTypeNoFinishOvered	= 20;
	
	/**
	 * 		描述：完成未超时工单
	*/
	public static final int OverTimeTypeFinishedNoOver	= 30;
	
	/**
	 * 		描述：未完成未超时

	*/
	public static final int OverTimeTypeFinishedOvered	= 40;
	
	/**
	 * 		描述：已超时
	*/
	public static final int OverTimeTypeOvered			= 90;
	
	/**
	 * 		描述：未超时
	*/
	public static final int OverTimeTypeNoOver			= 91;
	
	/**
	 * 待处理和待审批的待办事宜=待处理+待审批

	 */
	public static final int ProcessWaitDealAndAuditing =120;
	
	
	/**
	 * 		描述：要查询的pda工单
	 */
	public static String pdaBaseSchema					= "";
	
	/**
	 * 查历史库数据参数
	 */
	public static int IsArchive							= 1;
	
	/**
	 * 查活动库数据参数
	 */	
	public static int IsNotArchive						= 0;
	
	/**
	 * 待处理动作 默认为0主办、1协办(从配置文件中配置)
	 */
	public static String WaitDealAction					="";
	
	/**
	 * 待阅知阅知动作 默认为 2 抄送(从配置文件中配置)
	 */
	public static String WaitConfirmAction="";
	/**
	 * 待审批动作  默认为 3 审批(从配置文件中配置)
	 */
	public static String WaitAuditing="";
	
	/**
	 * 待审批公告

	 */
	public static final int NoteWaitAuditing=10;
	
	/**
	 * 待阅读公告

	 */
	public static final int NoteWaitRead=20;
	
	public static String  REMEDY_Portal_URL		= "";
	
	/**
	 * 页面信息
	 */
	//查询Table每页默认的显示的行数
	public static int PageRows=20;
	
	/**
	 * 
	 * arschema中name与achemaid对应关系
	 */
	//public static HashMap<String,String> arschemaName2Id = new HashMap<String,String>();
	//static {
	//	QueryAdapter queryAdapter = new QueryAdapter();
	//	String sql = "select * from arschema"; 
	//	DataTable dTable = queryAdapter.executeQuery(sql,null,0);
	//	for (int i = 0; i < dTable.length(); i++) {
	//		DataRow dRow = dTable.getDataRow(i);
			//Constants.arschemaName2Id.put(dRow.getString("name"), dRow.getString("schemaid"));
	//	}
	//}
	
	public static String[] appBaseSubForm = {"WF:App_Base_SubAssistAuditing",
											 "WF:App_Base_SubAssistAuditingTurnUp",
											 "WF:App_Base_SubAuditing",
											 "WF:App_Base_SubAuditingTurnUp",
											 "WF:App_Base_SubConfirm",
											 "WF:App_Base_SubDealHasten",
											 "WF:App_Base_SubDealRecall",
											 "WF:App_Base_SubDealTurnDown",
											 "WF:App_Base_SubDealTurnUp",
											 "WF:App_Base_SubFinish",
											 "WF:App_Base_SubPhaseNotice",
											 "WF:App_Base_SubPhaseSuggest"
											 ,"WF:App_Base_SubToAssist"
											 ,"WF:App_Base_SubToAssistAuditing"
											 ,"WF:App_Base_SubToAuditing"
											 ,"WF:App_Base_SubToCopy"
											 ,"WF:App_Base_SubToDeal"
											 ,"WF:App_Base_SubToDivide"};
}
