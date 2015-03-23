package com.ultrapower.eoms.common.constants;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： yhg
 * @date： 2010-5-12 下午05:37:49
 * 当前版本：
 * 摘要: 定义常量的类
 */
public class DutyConstants {
	/**
	 * 定义排班规则 共六种
	 * 引用排班、模板排班、轮休排班、人员顺序排班、班次顺序排班、删除排班
	 */
	public final static String YINYONG="引用排班";
	public final static String MOBAN="模板排班";
	public final static String LUNXIU="轮休排班";
	public final static String RENYUAN="人员顺序排班";
	public final static String BANCI="班次顺序排班";
	public final static String SHANCHU="删除排班";
	public final static String GIVEN="固定人员";
	/**
	 * 值班状态，用于做判断使用
	 */
	public final static Long BOTHNOTAPPLY = 0L;//双方均未提出交接班申请
	public final static Long OFFPERAPPLY = 1L;//交班方提出申请等待接班方确认
	public final static Long ONPERAPPLY = 2L;//接班方提出申请等待交班方确认
	public final static Long CONFIRM = 3L;//对方已经确认
	public final static Long OFFPERREFUS = 4L;//交班方拒绝
	public final static Long ONPERREFUS = 5L;//接班方拒绝
	public final static Long OFFPERNOTON = 6L;//已接班未提出交班申请
	public final static Long OFFPER = 7L;//已交班
	public final static Long OFFPERNO = 8L;//已交班，下一班未接班
	/**
	 * 值班状态名称，用于页面显示
	 */
	public final static String BOTHNOTAPPLY_NAME = "双方均未提出交接班申请";
	public final static String OFFPERAPPLY_NAME = "交班方提出申请等待接班方确认";
	public final static String ONPERAPPLY_NAME = "接班方提出申请等待交班方确认";
	public final static String CONFIRM_NAME = "对方已经确认";
	public final static String OFFPERREFUS_NAME = "交班方拒绝";
	public final static String ONPERREFUS_NAME = "接班方拒绝";
	public final static String OFFPERNOTON_NAME = "已接班未提出交班申请";
	public final static String OFFPER_NAME = "已交班";
	public final static String OFFPERNO_NAME = "已交班，下一班未接班";
	/**
	 * 年/月/非周期计划状态 1：草稿，2：待审核，3：审核通过，4：审核不通过，5：停用
	 */
	public final static String CAOGAO="草稿";
	public final static String DAISHEN="待审核";
	public final static String SHENGUO="审核通过";
	public final static String SHENNOTGUO="审核不通过";
	public final static String TINGYONG="停用";
	
	/**
	 * 周期（1：日，2：周，3：半月，4：月，5：季度，6：半年，7：年）
	 */
	
	public final static String DAY = "日";
	public final static String WEEK = "周";
	public final static String HALFMONTH = "半月";
	public final static String MONTH = "月";
	public final static String SEASON = "季度";
	public final static String HALFYEAR = "半年";
	public final static String YEAR = "年";
	/**
	 * 激活、停用、“1”启用
	 */
	public final static String ACTIVE_HUO = "激活";
	public final static String ACTIVE_TING = "停用";
	public final static String ON = "1";
	/**
	 * 作业计划完成有限期限
	 * （1：1月、2：1周、3：3天、4：当天、5：无有效期）
	 */
	public final static String ONEMONTH = "1月";
	public final static String ONEWEEK = "1周";
	public final static String THREEDAYS = "3天";
	public final static String CURRDAYS = "当天";
	public final static String NODATE = "无有效期";
	/**
	 * 是否周末执行，1：是，0：否
	 */
	public final static int ISWEEKEND_ONE = 1;
	public final static int ISWEEKEND_ZERO = 0;
	/**
	 * 是否节假日执行，1：是，0：否
	 */
	public final static int ISHOLIDAY_ONE = 1;
	public final static int ISHOLIDAY_ZERO = 0;
	/**
	 * 是否巡检，1：是，0：否
	 */
	public final static int ISCHECK_ONE = 1;
	public final static int ISCHECK_ZERO = 0;
	/**
	 * 执行方式（1：共同执行，2：分开执行）
	 */
	public final static int ISSHARE_ONE = 1;
	public final static int ISSHARE_ZERO = 2;
	
	/**
	 * 替班的七种状态  
	 * 0：已提交确认，1：确认通过，2：确认拒绝，
	 * 0：已送审，1：审批通过，2：审批驳回
	 */
	public final static int STATUS_CONFIRM_SUBMIT = 0;
	public final static int STATUS_CONFIRM_AGREE = 1;
	public final static int STATUS_CONFIRM_REFUSE = 2;
	public final static int STATUS_APPROVE = 0;
	public final static int STATUS_APPROVE_PASS = 1;
	public final static int STATUS_APPROVE_OVERRULE = 2;
	
	/**
	 * 替班的四种类型
	 * 1：替班，2：换班，3：休假，4：临时值班
	 */
	public final static int STATUS_DUTYRELAY = 1;
	public final static int STATUS_DUTYRELIEF = 2;
	public final static int STATUS_DUTYADJUSMENT = 3;
	public final static int STATUS_DUTYTEMP = 4;
	
	/**
	 * 交接班人员类型
	 * 1：只有主班人才能交接班；2：值班人都可以交接班；3：值班人必须交接班;4:自动交接
	 */
	public final static int LEADERCHANGE = 1;
	public final static int DUTYUSERCHANGE = 2;
	public final static int DUTYUSERMUSTCHANGE = 3;
	public final static int DUTYAUTO = 4;
	
	/**
	 * 主班人标志
	 * 1：主班人，0：普通值班人
	 */
	public final static Long LEADERDUTY = 1L;
	public final static Long COMMONDUTYUSER = 0L;
	
	/**
	 * 质检标志
	 */
	public final static Long ISCHECK = 1L;
	public final static Long NOTCHECK = 0L;
	
	/** 是 */
	public final static String YES = "是";
	
	/** 否 */
	public final static String NO = "否";
	
	/** 值班日志类型，1：普通日志 */
	public final static String GENERALLOG = "普通日志";
	/** 值班日志类型，2：附加日志 */
	public final static String ADDITIONALLOG = "附加日志";
	/** 值班日志类型，3：作业计划 */
	public final static String SCHEDULE = "作业计划";
	/** 值班日志类型，4：工单 */
	public final static String WORKORDER = "工单";

	/** 启用 */
	public final static String ENABLE = "启用";
	/** 停用 */
	public final static String DISABLE = "停用";
	
	/***********************************作业计划**************************************/
	/**
	 * 计划的5种状态
	 */
	public final static String PLAN_DRAFT_NAME = "草稿";
	public final static String PLAN_FORAPPROVE_NAME = "待审核";
	public final static String PLAN_OVERAPPORVE_NAME = "审核通过";
	public final static String PLAN_NOTAPPROVE_NAME = "审核不通过";
	public final static String PLAN_USELESS_NAME = "停用"; 
	
	/**
	 * 计划的5种状态
	 * 1：草稿，2：待审核，3：审核通过，4：审核不通过，5：停用
	 */
	public final static int PLAN_DRAFT = 1;
	public final static int PLAN_FORAPPROVE = 2;
	public final static int PLAN_OVERAPPORVE = 3;
	public final static int PLAN_NOTAPPROVE = 4;
	public final static int PLAN_USELESS = 5;
	
	/** 正常值班 */
	public final static String NORMALDUTY = "正常值班";
	/** 无盲点值班 */
	public final static String CONTINUOUSDUTY = "无盲点值班";
	
	/** 交接班人员类型1：只有主班人才能交接班 */
	public final static String CHIEFTYPE_ONLYPRINCIPAL = "只有主班人才能交接班";
	/** 交接班人员类型2：值班人都可以交接班 */
	public final static String CHIEFTYPE_ALLONDUTY = "值班人都可以交接班";
	/** 交接班人员类型3：值班人必须交接班 */
	public final static String CHIEFTYPE_MUSTONDUTY = "值班人必须交接班";
	/** 交接班人员类型4:自动交接 */
	public final static String CHIEFTYPE_AUTO = "自动交接";
	
	/** 交接班方式,1交接班无需确认 */
	public final static String CONFIRMTYPE_OFFNO = "交接班无需确认";
	/** 交接班方式,2交班方单向确认 */
	public final static String CONFIRMTYPE_OFFSIMPLE = "交班方单向确认";
	/** 交接班方式,3接班方单向确认 */
	public final static String CONFIRMTYPE_ONSIMPLE = "接班方单向确认";
	/** 交接班方式,4交接方双向确认 */
	public final static String CONFIRMTYPE_BOTH = "交接方双向确认";
	
	/** 值班室配置--是否隐藏标志位--停用 */
	public static final int DUTYORGANIZATION_STATE_STOP = 0;
	
	/** 值班室配置--是否隐藏标志位--启用 */
	public static final int DUTYORGANIZATION_STATE_START = 1;
	
	/**
	 * 质检对象类型，MONTH：月计划，NOTCYCLE：非周期计划
	 */
	public final static String CHECK_MONTH = "MONTH";
	public final static String CHECK_NOTCYCLE = "NOTCYCLE";
	public final static String CHECK_MONTH_CN = "月计划";
	public final static String CHECK_NOTCYCLE_CN = "非周期计划";
	
	/**
	 * 计划执行情况，1：未完成，未超时；2：未完成，已超时；
	 * 				3：已完成，未超时计划；4：已完成，已超时
	 */
	public static final Long NOTCOMPLETE_NOTTIMEOVER = 1L;
	public static final Long NOTCOMPLETE_TIMEOVER= 2L;
	public static final Long COMPLETE_NOTTIMEOVER= 3L;
	public static final Long COMPLETE_TIMEOVER= 4L;
	
	/**
	 * 是否超时，1：是，0：否
	 */
	public static final int TIMEOVER= 1;
	public static final int NOTTIMEOVER= 0;
	
	public static final int COMPLETE = 2;//已完成
	public static final int ISCOMPLETE = 3;//计划执行已完成
	
	/** 作业计划状态 1：未填写 */
	public final static String PLAN_EXEC_NOFILLED = "未填写";
	/** 作业计划状态 2：已完成 */
	public final static String PLAN_EXEC_COMPLETED = "已填写";
	/** 作业计划状态 3：已关闭 */
	public final static String PLAN_EXEC_CLOSED = "已完成";
	/** 作业计划状态 4：待审核 */
	public final static String PLAN_EXEC_PREPAREAUDIT = "待审核";
	/** 作业计划状态 5：审核通过 */
	public final static String PLAN_EXEC_APPROVED = "审核通过";
	/** 作业计划状态 6：审核不通过 */
	public final static String PLAN_EXEC_NOAPPROVED = "审核不通过";
	
	/**
	 * 计划类型，1：月计划；2：非周期计划
	 */
	public final static int PLANTYPE_MONTH = 1;
	public final static int PLANTYPE_NOTCYCLE = 2;
	/**
	 * 目录树url
	 */
	public final static String USER_ORG_TREE_URL = "ultraduty/portal/dutyPortal.action?organizationId=";
	
	public final static String FAULT_RECORD_URL = "ultraduty/portal/listFaultRecord.action?titleFlag=duty_title_dutyLog&dutyLogType=1&calendarId=";
	public final static String FAULT_RECORD_SEARCH_URL = "ultraduty/portal/listFaultRecordSearch.action?organizationId=";
	public final static String[] WAITING_DEAL_SHEET_URL = {"sheet/myWaitingDealSheetQueryPlan.action?titleFlag=","&username=","&organizationId="};
	public final static String PLAN_EXEC_URL = "ultraplan/planExecute/listPlanExecByOrg.action?titleFlag=plan_title_pendingPlanExec&organizationId=";
	public final static String[] DUTY_LOG_URL = {"ultraduty/portal/listDutyLog.action?titleFlag=duty_title_template&dutyLogType=2&calendarId=","&templateId="};
	public final static String[] DUTY_LOG_SEARCH_URL = {"ultraduty/portal/listDutyLogSearch.action?titleFlag=duty_title_template&dutyLogType=2&dutyLogModFlag=false&organizationId=","&templateId="};
	public final static String DUTY_WORK_URL = "dutyWorkCheck/listDutyWork.action?organizationId=";
	public final static String MONTH_PLAN_EXECUTE_SEARCH_URL = "ultraplan/planExecute/planMonthExecListByOrg.action?organizationId=";
	public final static String PLAN_EXECUTE_SEARCH_URL = "ultraplan/planExecute/planNotCycleExecListByOrg.action?organizationId=";
	public final static String DUTY_RALAY_URL = "dutyRelay/dutyRelayListAll.action?dutyOrgId=";
	public final static String ORGANIZATION_CALENDAR_URL = "ultraduty/calendar/orgCalenList.action";
	public final static String MY_ORGANIZATION_CALENDAR_URL = "ultraduty/calendar/dutyCalendarDetal.action?organizationId=";
	public final static String ORGANIZATION_INFO_URL = "dutyCheck/dutyCaseSearchList.action?organization=";
	public final static String MY_DEALED_SHEET_URL = "sheet/myDealedSheetQuery.action?organizationId=";
	public final static String DEALED_PLAN_EXEC_URL = "ultraplan/planExecute/listPlanExecByStatusAndOrg.action?status=3&organizationId=";
	public final static String DUTY_CHECK_URL="dutyCheck/dutyCheckAssembleList.action?organization=";
	
	public final static String FAULT_RECORD_NAME = "值班日志";
	public final static String WAITING_DEAL_SHEET_NAME = "我的待处理工单";
	public final static String PLAN_EXEC_NAME = "待处理作业计划";
	public final static String DUTY_WORK_NAME = "交接班记录查询";
	public final static String MONTH_PLAN_EXECUTE_SEARCH_NAME = "月作业计划执行情况";
	public final static String PLAN_EXECUTE_SEARCH_NAME = "非周期作业计划执行情况";
	public final static String DUTY_RALAY_NAME = "替班记录查询";
	public final static String ORGANIZATION_CALENDAR_NAME = "排班";
	public final static String MY_ORGANIZATION_CALENDAR_NAME = "我的排班";
	public final static String MY_DEALED_SHEET_NAME = "已处理工单";
	public final static String DEALED_PLAN_EXEC_NAME = "已处理作业计划";
	public final static String DUTY_CHECK_NAME = "日志质检";
	public final static String DUTY_WAITING_DEAL_SHEET_NAME = "值班室待处理工单";
	
	/** 值班平台树 */
	public final static String FORM_TREE = "FORM_TREE";
	/** 值班情况查询树 */
	public final static String MANAGE_TREE = "MANAGE_TREE";
	
	public final static String FAULT_RECORD_ID = "FAULT_RECORD_ID";
	public final static String WAITING_DEAL_SHEET_ID  = "WAITING_DEAL_SHEET_ID";
	public final static String DUTY_WAITING_DEAL_SHEET_ID  = "DUTY_WAITING_DEAL_SHEET_ID";
	public final static String PLAN_EXEC_ID  = "PLAN_EXEC_ID";
	public final static String DUTY_WORK_ID  = "DUTY_WORK_ID";
	public final static String MONTH_PLAN_EXECUTE_SEARCH_ID  = "MONTH_PLAN_EXECUTE_SEARCH_ID";
	public final static String PLAN_EXECUTE_SEARCH_ID  = "PLAN_EXECUTE_SEARCH_ID";
	public final static String DUTY_RALAY_ID  = "DUTY_RALAY_ID";
	public final static String ORGANIZATION_CALENDAR_ID  = "ORGANIZATION_CALENDAR_ID";
	public final static String MY_ORGANIZATION_CALENDAR_ID  = "MY_ORGANIZATION_CALENDAR_ID";
	public final static String MY_DEALED_SHEET_ID = "MY_DEALED_SHEET_ID";
	public final static String DEALED_PLAN_EXEC_ID  = "DEALED_PLAN_EXEC_ID";
	public final static String DUTY_CHECK_ID  = "DUTY_CHECK_ID";

	
	public final static String DUTY_FORM_CLASS_NAME = "orgForm";
	public final static String DUTY_MANAGE_CLASS_NAME = "orgManageForm";
	public final static String ORGANIZATION_CALENDAR_CLASS_NAME = "orgCalendarForm";
	public final static String DUTY_CHECK_CLASS_NAME="orgCheckForm";
	
	//案例库左侧树最后一级的开始年份
	public final static int START_YEAR= 2004;
	
	/** 当前位置 */
	public final static String CURRENT_POSITION="当前位置";
	/** 值班平台 */
	public final static String DUTY_PORTAL="值班平台";
	/** 值班质检 */
	public final static String DUTY_CHECK="值班质检";
	/** 值班情况查询 */
	public final static String DUTY_SEARCH="值班情况查询";
	/** 排班列表 */
	public final static String DUTY_CALENDAR_LIST="排班列表";
}	
